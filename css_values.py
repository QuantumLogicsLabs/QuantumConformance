#!/usr/bin/env python3
"""qrun's CSS value checks against css-tree's, on the same grammars.

The corpus:
  - every declaration in the stylesheets of examples/html (real-world CSS)
  - for every property MDN lists, values generated from its grammar (valid
    by construction) and mutations of them (a misspelt keyword, a number
    without its unit, an unknown unit, a negative number, one value too
    many), most of which are invalid

Each value is judged by `qrun --css-values` and by css-tree's lexer
(conformance/css_oracle.mjs), which is given the grammars qrun uses
(src/tools/gen_css_data.py --json) in place of its own, so that a
disagreement is a difference between the two matchers, not between two
versions of the data. Values whose meaning is only known when a page runs
(var(), env(), attr()) and values with vendor extensions (-webkit-box) are
left out: qrun accepts them on purpose.

usage:
    python conformance/css_values.py MDN_DATA_DIR CSS_TREE_DIR [--qrun PATH] [-v] [--seed N] [--per N]
        MDN_DATA_DIR  an installed mdn-data package (npm install mdn-data)
        CSS_TREE_DIR  an installed css-tree package (npm install css-tree)
"""
import argparse, collections, glob, json, os, random, re, subprocess, sys, tempfile

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)

# ── the value definition syntax (as src/css/CssGrammar.sa reads it) ──


class G:
    def __init__(self, kind, name="", kids=None):
        self.kind, self.name, self.kids = kind, name, kids or []
        self.min, self.max, self.comma, self.not_empty = 1, 1, False, False
        self.lo = self.hi = None


def plain(n):
    return n.min == 1 and n.max == 1 and not n.comma and not n.not_empty


class Parser:
    def __init__(self, s):
        self.s, self.i = s, 0

    def ws(self):
        while self.i < len(self.s) and self.s[self.i] == " ":
            self.i += 1

    def at(self, t):
        self.ws()
        return self.s.startswith(t, self.i)

    def combine(self, kind, parts):
        return parts[0] if len(parts) == 1 else G(kind, kids=parts)

    def alternatives(self):
        parts = [self.any_of()]
        while self.at("|") and not self.at("||"):
            self.i += 1
            parts.append(self.any_of())
        return self.combine("one", parts)

    def any_of(self):
        parts = [self.all_of()]
        while self.at("||"):
            self.i += 2
            parts.append(self.all_of())
        return self.combine("any", parts)

    def all_of(self):
        parts = [self.sequence()]
        while self.at("&&"):
            self.i += 2
            parts.append(self.sequence())
        return self.combine("all", parts)

    def term_ahead(self):
        self.ws()
        if self.i >= len(self.s):
            return False
        c = self.s[self.i]
        if c in "|])}":
            return False
        return not self.s.startswith("&&", self.i)

    def sequence(self):
        parts = []
        while self.term_ahead():
            before = self.i
            t = self.term()
            if t is None:
                if self.i == before:
                    self.i += 1
                continue
            parts.append(t)
        k = 0
        while k < len(parts):
            if parts[k].kind == "type" and parts[k].name == "function-token":
                m = k + 1
                if m < len(parts) and parts[m].kind == "type" and parts[m].name == "any-value":
                    m += 1
                if m < len(parts) and parts[m].kind == "token" and parts[m].name == ")":
                    parts[k:m + 1] = [G("anyfunc")]
            k += 1
        if not parts:
            return G("seq")
        return self.combine("seq", parts)

    def number(self):
        self.ws()
        m = re.match(r"[+-]?(inf|[0-9.]+)([a-zA-Z]*)", self.s[self.i:])
        if not m:
            return None
        self.i += m.end()
        if m.group(1) == "inf" or m.group(2):
            return None
        return float(m.group(1))

    def angle(self):
        s = self.s
        if s[self.i] == "'":
            end = s.find("'", self.i + 1)
            n = G("prop", s[self.i + 1:end])
            self.i = end + 1
        else:
            m = re.match(r"[^ >\[]+", s[self.i:])
            n = G("type", m.group(0))
            self.i += m.end()
            self.ws()
            if self.i < len(s) and s[self.i] == "[":
                self.i += 1
                n.lo = self.number()
                self.ws()
                if s[self.i] == ",":
                    self.i += 1
                n.hi = self.number()
                self.ws()
                if s[self.i] == "]":
                    self.i += 1
        while self.i < len(s) and s[self.i] != ">":
            self.i += 1
        self.i += 1
        return n

    def term(self):
        self.ws()
        s, c = self.s, self.s[self.i]
        if c == "[":
            self.i += 1
            inner = self.alternatives()
            if self.at("]"):
                self.i += 1
            n = G("seq", kids=[inner])
        elif c == "<":
            self.i += 1
            n = self.angle()
        elif c == "'":
            end = s.find("'", self.i + 1)
            text = s[self.i + 1:end]
            self.i = end + 1
            if text == "[":
                parts = []
                while self.term_ahead() and not self.at("']'"):
                    t = self.term()
                    if t:
                        parts.append(t)
                if self.at("']'"):
                    self.i += 3
                n = G("brackets", kids=[G("seq", kids=parts)])
            else:
                n = G("token", text)
        elif c == "(":
            self.i += 1
            inner = G("seq") if self.at(")") else self.alternatives()
            if self.at(")"):
                self.i += 1
            n = G("parens", kids=[inner])
        elif c in ",/:;={":
            self.i += 1
            n = G("token", c)
        elif re.match(r"[A-Za-z0-9_@-]", c):
            m = re.match(r"[A-Za-z0-9_@-]+", s[self.i:])
            word = m.group(0)
            self.i += m.end()
            if self.i < len(s) and s[self.i] == "(":
                self.i += 1
                inner = G("seq") if self.at(")") else self.alternatives()
                if self.at(")"):
                    self.i += 1
                n = G("func", word.lower(), [inner])
            else:
                n = G("keyword", word)
        else:
            self.i += 1
            return None
        return self.multipliers(n)

    def wrap(self, n):
        return n if plain(n) else G("seq", kids=[n])

    def multipliers(self, n):
        s = self.s
        while self.i < len(s):
            c = s[self.i]
            if c in "*+?":
                self.i += 1
                n = self.wrap(n)
                n.min, n.max = (1 if c == "+" else 0), (1 if c == "?" else None)
            elif c == "#":
                self.i += 1
                n = self.wrap(n)
                n.comma, n.min, n.max = True, 1, None
                m = re.match(r"\{(\d+)(,(\d*))?\}", s[self.i:])
                if m:
                    self.i += m.end()
                    n.min = int(m.group(1))
                    n.max = n.min if m.group(2) is None else (int(m.group(3)) if m.group(3) else None)
            elif c == "{" and re.match(r"\{\d", s[self.i:]):
                m = re.match(r"\{(\d+)(,(\d*))?\}", s[self.i:])
                self.i += m.end()
                n = self.wrap(n)
                n.min = int(m.group(1))
                n.max = n.min if m.group(2) is None else (int(m.group(3)) if m.group(3) else None)
            elif c == "!":
                self.i += 1
                if n.not_empty or not (n.min == 1 and n.max == 1 and not n.comma):
                    n = self.wrap(n)
                n.not_empty = True
            else:
                return n
        return n


# ── generating values ──

SAMPLES = {
    "length": ["0", "10px", "1.5em", "2rem", "50vw", "3ch"],
    "percentage": ["50%", "0%", "100%"],
    "number": ["0", "1.5", "2", "0.25"],
    "integer": ["0", "1", "3"],
    "angle": ["45deg", "0.25turn", "1rad"],
    "time": ["200ms", "1s"],
    "frequency": ["10hz", "2khz"],
    "resolution": ["2dppx", "96dpi", "2x"],
    "flex": ["1fr", "2fr"],
    "decibel": ["3db"],
    "semitones": ["2st"],
    "dimension": ["10px", "3em"],
    "zero": ["0"],
    "string": ['"text"', "'a b'"],
    "ident": ["foo", "bar"],
    "custom-ident": ["foo", "my-name"],
    "dashed-ident": ["--foo"],
    "custom-property-name": ["--foo"],
    "hex-color": ["#3a7", "#33aa77", "#33aa7780"],
    "any-value": ["foo"],
    "declaration-value": ["foo"],
    "an+b": ["2n+1", "odd", "3"],
    "an-plus-b": ["2n+1"],
    "urange": ["U+0025-00FF", "U+4??"],
    "ident-token": ["foo"],
    "string-token": ['"text"'],
    "number-token": ["2"],
    "dimension-token": ["10px"],
    "percentage-token": ["5%"],
    "hash-token": ["#foo"],
    "url-token": ["url(a.png)"],
    "function-token": ["foo("],
    "selector": [".a"],
    "forgiving-selector-list": [".a"],
    "forgiving-relative-selector-list": ["> .a"],
}
NUMERIC = {"length", "percentage", "number", "integer", "angle", "time", "frequency", "resolution", "flex",
           "dimension", "decibel", "semitones"}


class Generator:
    def __init__(self, data, rng):
        self.types, self.props, self.rng = data["types"], data["properties"], rng
        self.cache = {}

    def grammar(self, text):
        if text not in self.cache:
            self.cache[text] = Parser(text).alternatives()
        return self.cache[text]

    def number_in(self, n, base):
        lo, hi = n.lo, n.hi
        choices = SAMPLES[base]
        good = []
        for v in choices:
            m = re.match(r"[+-]?[0-9.]+", v)
            x = float(m.group(0)) if m else 0
            if (lo is None or x >= lo) and (hi is None or x <= hi):
                good.append(v)
        if good:
            return self.rng.choice(good)
        x = lo if lo is not None else (hi if hi is not None else 1)
        unit = re.sub(r"^[+-]?[0-9.]+", "", choices[0])
        return ("%g" % x) + unit

    def gen(self, n, depth):
        """A list of tokens (strings; a function or block is a nested list)."""
        if n.min == 1 and n.max == 1 and not n.comma:
            out = self.once(n, depth)
        else:
            hi = n.max if n.max is not None else n.min + 2
            hi = min(hi, n.min + 2)
            if depth > 10:
                hi = max(n.min, min(hi, 1))
            count = self.rng.randint(n.min, max(n.min, hi))
            out = []
            for k in range(count):
                if k and n.comma:
                    out.append(",")
                out += self.once(n, depth)
        if n.not_empty and not out:
            for _ in range(8):
                out = self.once(n, depth)
                if out:
                    break
        return out

    def simplest(self, kids):
        def cost(g, d=0):
            if d > 3:
                return 9
            if g.kind == "keyword":
                return 0
            if g.kind in ("seq", "all"):
                return sum(cost(k, d + 1) for k in g.kids) + 1
            if g.kind in ("one", "any"):
                return min(cost(k, d + 1) for k in g.kids) + 1
            return 3
        return min(kids, key=cost)

    def once(self, n, depth):
        k = n.kind
        if k == "keyword":
            return [n.name]
        if k == "token":
            return [n.name]
        if k == "seq":
            out = []
            for c in n.kids:
                out += self.gen(c, depth + 1)
            return out
        if k == "one":
            c = self.simplest(n.kids) if depth > 8 else self.rng.choice(n.kids)
            return self.gen(c, depth + 1)
        if k in ("all", "any"):
            kids = list(n.kids)
            if k == "any":
                kids = self.rng.sample(kids, self.rng.randint(1, len(kids)))
            self.rng.shuffle(kids)
            out = []
            for c in kids:
                out += self.gen(c, depth + 1)
            return out
        if k == "func":
            return [[n.name + "("] + self.gen(n.kids[0], depth + 1) + [")"]]
        if k == "parens":
            return [["("] + self.gen(n.kids[0], depth + 1) + [")"]]
        if k == "brackets":
            return [["["] + self.gen(n.kids[0], depth + 1) + ["]"]]
        if k == "anyfunc":
            return [["foo(", ")"]]
        if k == "prop":
            g = self.props.get(n.name)
            return self.gen(self.grammar(g), depth + 1) if g else ["foo"]
        if k == "type":
            name = n.name
            if name in SAMPLES:
                if name in NUMERIC and (n.lo is not None or n.hi is not None):
                    return [self.number_in(n, name)]
                return [self.rng.choice(SAMPLES[name])]
            g = self.types.get(name)
            if g is None or depth > 14:
                return ["foo"] if g is None else self.gen(self.simplest([self.grammar(g)]), depth + 1)
            return self.gen(self.grammar(g), depth + 1)
        return []


def clean_commas(tokens):
    """The comma rule of CSS Values 4, 2.6: no comma at either end of a list, nor two in a row."""
    out = []
    for t in tokens:
        if isinstance(t, list):
            inner = clean_commas(t[1:-1])
            out.append([t[0]] + inner + [t[-1]])
        elif t == ",":
            if out and out[-1] != "," and out[-1] != "/":
                out.append(",")
        else:
            out.append(t)
    while out and out[-1] == ",":
        out.pop()
    return out


def render(tokens):
    parts = []
    for t in tokens:
        if isinstance(t, list):
            inner = render(t[1:-1])
            parts.append(t[0] + inner + t[-1])
        elif t == ",":
            if parts:
                parts[-1] += ","
            else:
                parts.append(",")
        else:
            parts.append(t)
    return " ".join(parts)


def mutations(tokens, rng, keywords):
    """Variants of a value, most of them invalid."""
    flat = [t for t in tokens if not isinstance(t, list)]
    out = []
    words = [k for k, t in enumerate(tokens) if isinstance(t, str) and re.fullmatch(r"[a-z][a-z-]{3,}", t)]
    if words:
        k = rng.choice(words)
        w = tokens[k]
        i = rng.randrange(len(w) - 1)
        typo = w[:i] + w[i + 1] + w[i] + w[i + 2:]
        if typo not in keywords:
            out.append(tokens[:k] + [typo] + tokens[k + 1:])
    lengths = [k for k, t in enumerate(tokens) if isinstance(t, str) and re.fullmatch(r"[0-9.]+(px|em|rem|vw|ch)", t)]
    if lengths:
        k = rng.choice(lengths)
        num = re.match(r"[0-9.]+", tokens[k]).group(0)
        if float(num) != 0:
            out.append(tokens[:k] + [num] + tokens[k + 1:])
        out.append(tokens[:k] + [num + "pz"] + tokens[k + 1:])
    nums = [k for k, t in enumerate(tokens) if isinstance(t, str) and re.fullmatch(r"[0-9.]+[a-z%]*", t) and
            float(re.match(r"[0-9.]+", t).group(0)) != 0]
    if nums:
        k = rng.choice(nums)
        out.append(tokens[:k] + ["-" + tokens[k]] + tokens[k + 1:])
    out.append(tokens + [rng.choice(["foo", "10px", "red", "auto"])])
    return out


# ── the real-world corpus ──

def example_declarations():
    found = []
    for path in glob.glob(os.path.join(ROOT, "examples", "html", "**", "*.sa"), recursive=True):
        with open(path, encoding="utf-8") as f:
            text = f.read()
        sheets = re.findall(r"<style[^>]*>(.*?)</style>", text, re.S)
        sheets += re.findall(r'style="([^"]*)"', text)
        for sheet in sheets:
            sheet = re.sub(r"/\*.*?\*/", "", sheet, flags=re.S)
            for block in re.findall(r"\{([^{}]*)\}", sheet) + ([sheet] if "{" not in sheet else []):
                for decl in block.split(";"):
                    if ":" not in decl:
                        continue
                    name, value = decl.split(":", 1)
                    name, value = name.strip().lower(), " ".join(value.split())
                    value = re.sub(r"\s*!important$", "", value)
                    if re.fullmatch(r"[a-z-]+", name) and value:
                        found.append((name, value, "examples"))
    return found


def skipped(value):
    return re.search(r"\b(var|env|attr|if)\(", value) or re.search(r"(^|[\s(,])-(webkit|moz|ms|o)-", value)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("mdn_data")
    ap.add_argument("css_tree")
    ap.add_argument("--qrun", default=os.path.join(ROOT, "build", "qrun.exe"))
    ap.add_argument("-v", action="store_true")
    ap.add_argument("--seed", type=int, default=1)
    ap.add_argument("--per", type=int, default=6, help="generated values per property")
    args = ap.parse_args()

    with tempfile.TemporaryDirectory() as tmp:
        grammars = os.path.join(tmp, "grammars.json")
        gen = subprocess.run([sys.executable, "-I", os.path.join("src", "tools", "gen_css_data.py"), args.mdn_data,
                              "--json", grammars], cwd=ROOT, capture_output=True, text=True)
        if gen.returncode:
            print(gen.stdout, gen.stderr)
            return 2
        with open(grammars, encoding="utf-8") as f:
            data = json.load(f)

        rng = random.Random(args.seed)
        g = Generator(data, rng)
        cases = list(example_declarations())
        for name in sorted(data["properties"]):
            if name.startswith("-"):
                continue
            grammar = g.grammar(data["properties"][name])
            keywords = set(re.findall(r"(?<![<'(-])\b[a-z][a-z-]+\b(?![(>'])", data["properties"][name]))
            seen = set()
            for _ in range(args.per):
                tokens = clean_commas(g.gen(grammar, 0))
                value = render(tokens)
                if not value or value in seen:
                    continue
                seen.add(value)
                cases.append((name, value, "generated"))
                for m in mutations(tokens, rng, keywords):
                    mv = render(clean_commas(m))
                    if mv not in seen:
                        seen.add(mv)
                        cases.append((name, mv, "mutated"))
        cases = [c for c in cases if not skipped(c[1]) and "\t" not in c[1] and "\n" not in c[1]]
        # one of each
        unique, keys = [], set()
        for c in cases:
            if (c[0], c[1]) not in keys:
                keys.add((c[0], c[1]))
                unique.append(c)
        cases = unique

        batch = os.path.join(tmp, "batch.txt")
        with open(batch, "w", encoding="utf-8", newline="\n") as f:
            for name, value, _ in cases:
                f.write("%s\t%s\n" % (name, value))
        q = subprocess.run([args.qrun, "--css-values", batch], capture_output=True)
        o = subprocess.run(["node", os.path.join(HERE, "css_oracle.mjs"), args.css_tree, grammars, batch],
                           capture_output=True)
    ours = q.stdout.decode("utf-8").splitlines()
    theirs = o.stdout.decode("utf-8").splitlines()
    if len(ours) != len(cases) or len(theirs) != len(cases):
        print("a checker failed: qrun %d, css-tree %d results for %d values" % (len(ours), len(theirs), len(cases)))
        print(q.stderr.decode("utf-8", "replace")[-2000:], o.stderr.decode("utf-8", "replace")[-2000:])
        return 2

    stats = collections.Counter()
    disagreements = []
    for (name, value, source), a, b in zip(cases, ours, theirs):
        va, vb = a.split("\t")[0], b.split("\t")[0]
        stats[(source, va, vb)] += 1
        if va != vb:
            disagreements.append((name, value, source, a, b))
    total = len(cases)
    agree = total - len(disagreements)
    print("CSS values: %d judged, qrun and css-tree agree on %d (%.2f%%)" % (total, agree, 100.0 * agree / total))
    for source in ("examples", "generated", "mutated"):
        n = sum(v for k, v in stats.items() if k[0] == source)
        if not n:
            continue
        print("  %-9s %5d: both ok %d, both error %d, only qrun rejects %d, only css-tree rejects %d" % (
            source, n, stats[(source, "ok", "ok")], stats[(source, "error", "error")],
            stats[(source, "error", "ok")], stats[(source, "ok", "error")]))
    if args.v:
        for name, value, source, a, b in disagreements:
            print("%s: %s [%s]\n    qrun:     %s\n    css-tree: %s" % (name, value, source, a, b))
    return 1 if disagreements else 0


if __name__ == "__main__":
    sys.exit(main())
