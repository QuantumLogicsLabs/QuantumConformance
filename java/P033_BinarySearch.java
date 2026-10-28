public class P033_BinarySearch {
    static int search(int[] a, int target) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == target) return mid;
            if (a[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -(lo + 1);
    }

    static int lowerBound(int[] a, int x) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (a[mid] < x) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    static long isqrt(long n) {
        long lo = 0, hi = Math.min(n, 3037000499L);
        while (lo < hi) {
            long mid = (lo + hi + 1) / 2;
            if (mid * mid <= n) lo = mid; else hi = mid - 1;
        }
        return lo;
    }

    static double cubeRoot(double x) {
        double lo = 0, hi = Math.max(1, x);
        for (int i = 0; i < 100; i++) {
            double mid = (lo + hi) / 2;
            if (mid * mid * mid < x) lo = mid; else hi = mid;
        }
        return lo;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 3, 3, 7, 9, 12, 15};
        for (int t : new int[]{7, 1, 15, 4, 100, -5}) System.out.print(search(a, t) + " ");
        System.out.println();
        System.out.println(lowerBound(a, 3) + " " + lowerBound(a, 8) + " " + lowerBound(a, 99));
        System.out.println(isqrt(1000000007L) + " " + isqrt(99) + " " + isqrt(Long.MAX_VALUE));
        System.out.printf("%.6f%n", cubeRoot(2));
    }
}
