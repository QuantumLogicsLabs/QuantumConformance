import java.util.ArrayList;
import java.util.List;

public class P034_Primes {
    static boolean[] sieve(int n) {
        boolean[] composite = new boolean[n + 1];
        composite[0] = composite[1] = true;
        for (int i = 2; (long) i * i <= n; i++)
            if (!composite[i])
                for (int j = i * i; j <= n; j += i) composite[j] = true;
        return composite;
    }

    static List<Long> factor(long n) {
        List<Long> fs = new ArrayList<>();
        for (long p = 2; p * p <= n; p++)
            while (n % p == 0) { fs.add(p); n /= p; }
        if (n > 1) fs.add(n);
        return fs;
    }

    static long modPow(long b, long e, long m) {
        long r = 1;
        b %= m;
        while (e > 0) {
            if ((e & 1) == 1) r = r * b % m;
            b = b * b % m;
            e >>= 1;
        }
        return r;
    }

    public static void main(String[] args) {
        boolean[] c = sieve(100);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i <= 100; i++) if (!c[i]) { sb.append(i).append(' '); count++; }
        System.out.println(sb.toString().trim());
        System.out.println("count=" + count);
        boolean[] big = sieve(1_000_000);
        int bigCount = 0;
        for (boolean b : big) if (!b) bigCount++;
        System.out.println("primes below a million: " + bigCount);
        System.out.println(factor(360) + " " + factor(9_999_999_967L) + " " + factor(600851475143L));
        System.out.println(modPow(2, 100, 1_000_000_007) + " " + modPow(3, 0, 7));
    }
}
