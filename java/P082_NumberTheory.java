import java.util.*;

public class P082_NumberTheory {
    static long[] extGcd(long a, long b) {
        if (b == 0) return new long[]{a, 1, 0};
        long[] r = extGcd(b, a % b);
        return new long[]{r[0], r[2], r[1] - (a / b) * r[2]};
    }

    static long modInverse(long a, long m) {
        long[] r = extGcd(a, m);
        if (r[0] != 1) throw new ArithmeticException(a + " has no inverse mod " + m);
        return ((r[1] % m) + m) % m;
    }

    static int phi(int n) {
        int r = n;
        for (int p = 2; p * p <= n; p++)
            if (n % p == 0) {
                while (n % p == 0) n /= p;
                r -= r / p;
            }
        if (n > 1) r -= r / n;
        return r;
    }

    static boolean perfect(int n) {
        int s = 1;
        for (int d = 2; d * d <= n; d++) if (n % d == 0) s += d + (d * d == n ? 0 : n / d);
        return n > 1 && s == n;
    }

    static long collatzLen(long n) {
        long len = 1;
        while (n != 1) { n = n % 2 == 0 ? n / 2 : 3 * n + 1; len++; }
        return len;
    }

    static String toRoman(int n) {
        int[] v = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] s = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < v.length; i++) while (n >= v[i]) { n -= v[i]; sb.append(s[i]); }
        return sb.toString();
    }

    static String toBase(long n, int base) {
        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (n == 0) return "0";
        StringBuilder sb = new StringBuilder();
        boolean neg = n < 0;
        n = Math.abs(n);
        while (n > 0) { sb.append(digits.charAt((int) (n % base))); n /= base; }
        return (neg ? "-" : "") + sb.reverse();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(extGcd(240, 46)) + " " + modInverse(3, 11) + " " + modInverse(17, 3120));
        try {
            modInverse(6, 9);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 12; i++) sb.append(phi(i)).append(' ');
        System.out.println(sb.toString().trim());
        List<Integer> perf = new ArrayList<>();
        for (int i = 1; i < 10000; i++) if (perfect(i)) perf.add(i);
        System.out.println(perf);
        long best = 0, arg = 0;
        for (long i = 1; i < 30000; i++) {
            long l = collatzLen(i);
            if (l > best) { best = l; arg = i; }
        }
        System.out.println("longest collatz below 30000: " + arg + " (" + best + ")");
        System.out.println(toRoman(1994) + " " + toRoman(2024) + " " + toRoman(3888));
        System.out.println(toBase(255, 2) + " " + toBase(-255, 16) + " " + toBase(123456789, 36) + " " + Long.toString(123456789, 36));
    }
}
