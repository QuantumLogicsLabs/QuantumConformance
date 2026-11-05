#!/usr/bin/env python3
"""The html5lib tokenizer tests against qrun's HTML tokenizer.

The tests are JSON (html5lib-tests/tokenizer/*.test). This script writes every
test case (once per initial state) into a batch file, runs
`qrun --html-tokens BATCH`, and compares each case's tokens, and the codes and
positions of its parse errors, with the expected ones.

Cases whose input holds a lone surrogate are skipped: qrun reads UTF-8, which
cannot carry one.

Known test-data disagreements (counted apart, not as failures): for a
processing instruction cut off by the end of the file after its data began,
e.g. "<?a a", the tests place eof-in-processing-instruction at the space
after the target (column 4). The standard detects that error in the
processing instruction data state when it consumes the end of the file, and
every other eof-in-* case in the same files is placed there (as is "<?a"
itself, column 4 = its end). qrun reports the end of the file (column 6).

usage:
    python conformance/html_tokenizer.py TESTS_DIR [--qrun PATH] [-v] [-k SUBSTR]
        TESTS_DIR   html5lib-tests/tokenizer (a checkout of github.com/html5lib/html5lib-tests)
        --qrun      default: build/qrun.exe next to this folder
        -v          show each failure
        -k          only test files whose name contains SUBSTR
"""
import argparse, glob, json, os, re, subprocess, sys, tempfile

HERE = os.path.dirname(os.path.abspath(__file__))
STATES = {"Data state": 0, "RCDATA state": 1, "RAWTEXT state": 2, "Script data state": 3,
          "PLAINTEXT state": 4, "CDATA section state": 5}


def unescape(s):
    return re.sub(r"\\u([0-9A-Fa-f]{4})", lambda m: chr(int(m.group(1), 16)), s)


def deep_unescape(x):
    if isinstance(x, str):
        return unescape(x)
    if isinstance(x, list):
        return [deep_unescape(v) for v in x]
    if isinstance(x, dict):
        return {unescape(k): deep_unescape(v) for k, v in x.items()}
    return x


def merge_chars(tokens):
    out = []
    for t in tokens:
        if t[0] == "Character" and out and out[-1][0] == "Character":
            out[-1] = ["Character", out[-1][1] + t[1]]
        else:
            out.append(list(t))
    return out


def normalize(tokens):
    out = []
    for t in merge_chars(tokens):
        if t[0] == "StartTag" and len(t) == 4 and not t[3]:
            t = t[:3]
        out.append(t)
    return out


def has_surrogate(s):
    return any(0xD800 <= ord(c) <= 0xDFFF for c in s)


def known_pi_eof(text, want, got):
    """The one disagreement described at the top: a lone eof-in-processing-instruction,
    placed by the tests after the target and by qrun at the end of the file."""
    if not re.fullmatch(r"<\?[A-Za-z]+\s+[^>]*", text) or len(want) != 1 or len(got) != 1:
        return False
    (wc, wl, wcol), (gc, gl, gcol) = want[0], got[0]
    return wc == gc == "eof-in-processing-instruction" and gl == 1 and gcol == len(text) + 1


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("tests")
    ap.add_argument("--qrun", default=os.path.join(HERE, "..", "build", "qrun.exe"))
    ap.add_argument("-v", action="store_true")
    ap.add_argument("-k", default="")
    args = ap.parse_args()

    cases = []
    for path in sorted(glob.glob(os.path.join(args.tests, "*.test"))):
        name = os.path.basename(path)
        if args.k not in name:
            continue
        with open(path, encoding="utf-8") as f:
            data = json.load(f)
        for test in data.get("tests", []):
            text, output = test["input"], test["output"]
            errors = test.get("errors", [])
            if test.get("doubleEscaped"):
                text, output = unescape(text), deep_unescape(output)
            for state in test.get("initialStates", ["Data state"]):
                cases.append((name, test["description"], state, test.get("lastStartTag"), text, output, errors))

    batch, skipped = [], 0
    runnable = []
    for case in cases:
        text = case[4]
        if has_surrogate(text):
            skipped += 1
            continue
        hexed = text.encode("utf-8").hex() or "-"
        batch.append("%d %s %s" % (STATES[case[2]], case[3] or "-", hexed))
        runnable.append(case)

    with tempfile.TemporaryDirectory() as tmp:
        bpath = os.path.join(tmp, "batch.txt")
        with open(bpath, "w", encoding="ascii", newline="\n") as f:
            f.write("\n".join(batch) + "\n")
        p = subprocess.run([args.qrun, "--html-tokens", bpath], capture_output=True)
    lines = p.stdout.decode("utf-8").splitlines()
    if p.returncode != 0 or len(lines) != len(runnable):
        print("qrun failed (exit %d, %d results for %d cases)" % (p.returncode, len(lines), len(runnable)))
        print(p.stderr.decode("utf-8", "replace"))
        return 2

    passed = failed = error_mismatch = known = 0
    for case, line in zip(runnable, lines):
        name, desc, state, last, text, output, errors = case
        got = json.loads(line)
        tokens_ok = normalize(got["tokens"]) == normalize(output)
        want_errors = sorted((e["code"], e["line"], e["col"]) for e in errors)
        got_errors = sorted(tuple(e) for e in got["errors"])
        errors_ok = want_errors == got_errors
        if tokens_ok and errors_ok:
            passed += 1
            continue
        if tokens_ok and known_pi_eof(text, want_errors, got_errors):
            known += 1
            continue
        failed += 1
        if tokens_ok:
            error_mismatch += 1
        if args.v:
            print("FAIL %s: %s (%s)" % (name, desc, state))
            print("  input   %r" % text)
            if not tokens_ok:
                print("  want    %s" % json.dumps(normalize(output), ensure_ascii=False))
                print("  got     %s" % json.dumps(normalize(got["tokens"]), ensure_ascii=False))
            if not errors_ok:
                print("  errors want %s" % want_errors)
                print("  errors got  %s" % got_errors)
    print("tokenizer: %d passed, %d failed (%d only in errors), %d known test-data disagreements, "
          "%d skipped (lone surrogates)" % (passed, failed, error_mismatch, known, skipped))
    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main())
