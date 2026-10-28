import java.util.*;

public class P063_ExpressionParser {
    interface Expr { double eval(Map<String, Double> env); String show(); }

    static class Num implements Expr {
        final double v;
        Num(double v) { this.v = v; }
        public double eval(Map<String, Double> env) { return v; }
        public String show() { return v == Math.floor(v) ? String.valueOf((long) v) : String.valueOf(v); }
    }

    static class Var implements Expr {
        final String name;
        Var(String n) { name = n; }
        public double eval(Map<String, Double> env) {
            Double v = env.get(name);
            if (v == null) throw new NoSuchElementException("unbound " + name);
            return v;
        }
        public String show() { return name; }
    }

    static class Bin implements Expr {
        final char op;
        final Expr l, r;
        Bin(char op, Expr l, Expr r) { this.op = op; this.l = l; this.r = r; }
        public double eval(Map<String, Double> env) {
            double a = l.eval(env), b = r.eval(env);
            switch (op) {
                case '+': return a + b;
                case '-': return a - b;
                case '*': return a * b;
                case '/': return a / b;
                case '^': return Math.pow(a, b);
            }
            throw new IllegalStateException();
        }
        public String show() { return "(" + l.show() + " " + op + " " + r.show() + ")"; }
    }

    static class Parser {
        final String s;
        int pos;
        Parser(String s) { this.s = s.replace(" ", ""); }

        Expr parse() {
            Expr e = sum();
            if (pos != s.length()) throw new IllegalArgumentException("unexpected '" + s.charAt(pos) + "' at " + pos);
            return e;
        }
        Expr sum() {
            Expr e = product();
            while (pos < s.length() && (s.charAt(pos) == '+' || s.charAt(pos) == '-')) e = new Bin(s.charAt(pos++), e, product());
            return e;
        }
        Expr product() {
            Expr e = power();
            while (pos < s.length() && (s.charAt(pos) == '*' || s.charAt(pos) == '/')) e = new Bin(s.charAt(pos++), e, power());
            return e;
        }
        Expr power() {
            Expr base = atom();
            if (pos < s.length() && s.charAt(pos) == '^') { pos++; return new Bin('^', base, power()); }
            return base;
        }
        Expr atom() {
            char c = s.charAt(pos);
            if (c == '(') { pos++; Expr e = sum(); pos++; return e; }
            if (c == '-') { pos++; return new Bin('-', new Num(0), atom()); }
            int start = pos;
            if (Character.isDigit(c)) {
                while (pos < s.length() && (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '.')) pos++;
                return new Num(Double.parseDouble(s.substring(start, pos)));
            }
            while (pos < s.length() && Character.isLetter(s.charAt(pos))) pos++;
            if (start == pos) throw new IllegalArgumentException("unexpected '" + c + "' at " + pos);
            return new Var(s.substring(start, pos));
        }
    }

    public static void main(String[] args) {
        Map<String, Double> env = new HashMap<>();
        env.put("x", 3.0);
        env.put("y", 0.5);
        String[] inputs = {"1 + 2 * 3", "(1 + 2) * 3", "2 ^ 3 ^ 2", "x * x - 4 * y", "-x + 10 / 4", "z + 1", "3 + * 4", "1.5 * (2 + x) / y"};
        for (String in : inputs) {
            try {
                Expr e = new Parser(in).parse();
                System.out.println(e.show() + " = " + e.eval(env));
            } catch (RuntimeException ex) {
                System.out.println(in + " -> " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
        }
    }
}
