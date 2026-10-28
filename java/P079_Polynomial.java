import java.util.*;

public class P079_Polynomial {
    final TreeMap<Integer, Integer> terms = new TreeMap<>(Comparator.reverseOrder());

    P079_Polynomial term(int coef, int exp) {
        terms.merge(exp, coef, Integer::sum);
        if (terms.get(exp) == 0) terms.remove(exp);
        return this;
    }

    P079_Polynomial plus(P079_Polynomial o) {
        P079_Polynomial r = copy();
        o.terms.forEach((e, c) -> r.term(c, e));
        return r;
    }

    P079_Polynomial times(P079_Polynomial o) {
        P079_Polynomial r = new P079_Polynomial();
        for (Map.Entry<Integer, Integer> a : terms.entrySet())
            for (Map.Entry<Integer, Integer> b : o.terms.entrySet())
                r.term(a.getValue() * b.getValue(), a.getKey() + b.getKey());
        return r;
    }

    P079_Polynomial derivative() {
        P079_Polynomial r = new P079_Polynomial();
        terms.forEach((e, c) -> { if (e > 0) r.term(c * e, e - 1); });
        return r;
    }

    long eval(long x) {
        long s = 0;
        for (Map.Entry<Integer, Integer> t : terms.entrySet()) s += t.getValue() * (long) Math.pow(x, t.getKey());
        return s;
    }

    P079_Polynomial copy() {
        P079_Polynomial r = new P079_Polynomial();
        r.terms.putAll(terms);
        return r;
    }

    @Override public String toString() {
        if (terms.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> t : terms.entrySet()) {
            int c = t.getValue(), e = t.getKey();
            sb.append(sb.length() == 0 ? (c < 0 ? "-" : "") : (c < 0 ? " - " : " + "));
            int a = Math.abs(c);
            if (a != 1 || e == 0) sb.append(a);
            if (e > 0) sb.append('x');
            if (e > 1) sb.append('^').append(e);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        P079_Polynomial p = new P079_Polynomial().term(3, 2).term(-1, 1).term(5, 0);
        P079_Polynomial q = new P079_Polynomial().term(1, 1).term(-2, 0);
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("p + q = " + p.plus(q));
        System.out.println("p * q = " + p.times(q));
        System.out.println("p' = " + p.derivative() + ", (p*q)' = " + p.times(q).derivative());
        System.out.println("p(4) = " + p.eval(4) + ", q(2) = " + q.eval(2));
        P079_Polynomial zero = q.plus(new P079_Polynomial().term(-1, 1).term(2, 0));
        System.out.println("zero = " + zero + ", x^10 at 3 = " + new P079_Polynomial().term(1, 10).eval(3));
        P079_Polynomial pow = new P079_Polynomial().term(1, 0);
        P079_Polynomial xp1 = new P079_Polynomial().term(1, 1).term(1, 0);
        for (int i = 0; i < 6; i++) pow = pow.times(xp1);
        System.out.println("(x+1)^6 = " + pow);
    }
}
