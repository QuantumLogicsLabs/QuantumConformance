import java.util.Arrays;

public class P049_MatrixOps {
    static long[][] mul(long[][] a, long[][] b, long mod) {
        int n = a.length, m = b[0].length, k = b.length;
        long[][] c = new long[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                long s = 0;
                for (int t = 0; t < k; t++) s = (s + a[i][t] * b[t][j]) % mod;
                c[i][j] = s;
            }
        return c;
    }

    static long[][] pow(long[][] a, long e, long mod) {
        int n = a.length;
        long[][] r = new long[n][n];
        for (int i = 0; i < n; i++) r[i][i] = 1;
        while (e > 0) {
            if ((e & 1) != 0) r = mul(r, a, mod);
            a = mul(a, a, mod);
            e >>= 1;
        }
        return r;
    }

    static double det(double[][] m) {
        int n = m.length;
        double[][] a = new double[n][];
        for (int i = 0; i < n; i++) a[i] = m[i].clone();
        double d = 1;
        for (int c = 0; c < n; c++) {
            int p = c;
            for (int r = c + 1; r < n; r++) if (Math.abs(a[r][c]) > Math.abs(a[p][c])) p = r;
            if (Math.abs(a[p][c]) < 1e-12) return 0;
            if (p != c) { double[] t = a[p]; a[p] = a[c]; a[c] = t; d = -d; }
            d *= a[c][c];
            for (int r = c + 1; r < n; r++) {
                double f = a[r][c] / a[c][c];
                for (int k = c; k < n; k++) a[r][k] -= f * a[c][k];
            }
        }
        return d;
    }

    static int[][] transpose(int[][] m) {
        int[][] t = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++) t[j][i] = m[i][j];
        return t;
    }

    public static void main(String[] args) {
        long[][] fib = {{1, 1}, {1, 0}};
        System.out.println("fib(90) mod 1e9+7 = " + pow(fib, 90, 1_000_000_007L)[0][1]);
        System.out.println("fib(10) = " + pow(fib, 10, Long.MAX_VALUE)[0][1]);
        System.out.printf("det = %.4f%n", det(new double[][]{{2, -3, 1}, {2, 0, -1}, {1, 4, 5}}));
        System.out.printf("det = %.4f%n", det(new double[][]{{1, 2}, {2, 4}}));
        System.out.println(Arrays.deepToString(transpose(new int[][]{{1, 2, 3}, {4, 5, 6}})));
        long[][] id = mul(new long[][]{{1, 2}, {3, 4}}, new long[][]{{1, 0}, {0, 1}}, 1000);
        System.out.println(Arrays.deepToString(id));
    }
}
