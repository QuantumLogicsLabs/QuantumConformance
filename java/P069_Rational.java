public class P069_Rational {
    static final class Rational implements Comparable<Rational> {
        final long num, den;

        Rational(long n, long d) {
            if (d == 0) throw new ArithmeticException("zero denominator");
            if (d < 0) { n = -n; d = -d; }
            long g = gcd(Math.abs(n), d);
            num = n / g;
            den = d / g;
        }

        static long gcd(long a, long b) { return b == 0 ? (a == 0 ? 1 : a) : gcd(b, a % b); }

        Rational plus(Rational o) { return new Rational(num * o.den + o.num * den, den * o.den); }
        Rational minus(Rational o) { return plus(new Rational(-o.num, o.den)); }
        Rational times(Rational o) { return new Rational(num * o.num, den * o.den); }
        Rational div(Rational o) { return new Rational(num * o.den, den * o.num); }
        double value() { return (double) num / den; }

        public int compareTo(Rational o) { return Long.compare(num * o.den, o.num * den); }
        @Override public boolean equals(Object o) {
            return o instanceof Rational && ((Rational) o).num == num && ((Rational) o).den == den;
        }
        @Override public int hashCode() { return Long.hashCode(num) * 31 + Long.hashCode(den); }
        @Override public String toString() { return den == 1 ? Long.toString(num) : num + "/" + den; }
    }

    public static void main(String[] args) {
        Rational a = new Rational(1, 2), b = new Rational(3, -4), c = new Rational(10, 20);
        System.out.println(a + " " + b + " " + c + " " + a.equals(c) + " " + (a.hashCode() == c.hashCode()));
        System.out.println(a.plus(b) + " " + a.minus(b) + " " + a.times(b) + " " + a.div(b));
        Rational h = new Rational(0, 1);
        for (int i = 1; i <= 10; i++) h = h.plus(new Rational(1, i));
        System.out.println("H10 = " + h + " ~ " + h.value());
        System.out.println(a.compareTo(b) + " " + b.compareTo(a) + " " + a.compareTo(c));
        java.util.List<Rational> rs = new java.util.ArrayList<>(java.util.List.of(new Rational(2, 3), new Rational(1, 7), new Rational(-5, 2), new Rational(4, 6)));
        java.util.Collections.sort(rs);
        System.out.println(rs + " distinct=" + new java.util.HashSet<>(rs).size());
        try {
            a.div(new Rational(0, 5));
        } catch (ArithmeticException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
