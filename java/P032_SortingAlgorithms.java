import java.util.Arrays;

public class P032_SortingAlgorithms {
    static void bubble(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++)
                if (a[j] > a[j + 1]) { int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t; swapped = true; }
            if (!swapped) break;
        }
    }

    static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i - 1;
            while (j >= 0 && a[j] > key) a[j + 1] = a[j--];
            a[j + 1] = key;
        }
    }

    static void mergeSort(int[] a, int lo, int hi, int[] tmp) {
        if (hi - lo < 2) return;
        int mid = (lo + hi) >>> 1;
        mergeSort(a, lo, mid, tmp);
        mergeSort(a, mid, hi, tmp);
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) tmp[k++] = a[i] <= a[j] ? a[i++] : a[j++];
        while (i < mid) tmp[k++] = a[i++];
        while (j < hi) tmp[k++] = a[j++];
        System.arraycopy(tmp, lo, a, lo, hi - lo);
    }

    static void quick(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = a[(lo + hi) / 2], i = lo, j = hi;
        while (i <= j) {
            while (a[i] < p) i++;
            while (a[j] > p) j--;
            if (i <= j) { int t = a[i]; a[i] = a[j]; a[j] = t; i++; j--; }
        }
        quick(a, lo, j);
        quick(a, i, hi);
    }

    static void heapSort(int[] a) {
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) sift(a, i, n);
        for (int end = n - 1; end > 0; end--) {
            int t = a[0]; a[0] = a[end]; a[end] = t;
            sift(a, 0, end);
        }
    }

    static void sift(int[] a, int i, int n) {
        while (true) {
            int l = 2 * i + 1, r = l + 1, big = i;
            if (l < n && a[l] > a[big]) big = l;
            if (r < n && a[r] > a[big]) big = r;
            if (big == i) return;
            int t = a[i]; a[i] = a[big]; a[big] = t;
            i = big;
        }
    }

    static int[] countingSort(int[] a, int max) {
        int[] cnt = new int[max + 1];
        for (int v : a) cnt[v]++;
        int[] out = new int[a.length];
        int k = 0;
        for (int v = 0; v <= max; v++) while (cnt[v]-- > 0) out[k++] = v;
        return out;
    }

    public static void main(String[] args) {
        int[] base = {38, 27, 43, 3, 9, 82, 10, 3, 55, 0, 71};
        int[] a = base.clone(); bubble(a); System.out.println("bubble    " + Arrays.toString(a));
        a = base.clone(); insertion(a); System.out.println("insertion " + Arrays.toString(a));
        a = base.clone(); mergeSort(a, 0, a.length, new int[a.length]); System.out.println("merge     " + Arrays.toString(a));
        a = base.clone(); quick(a, 0, a.length - 1); System.out.println("quick     " + Arrays.toString(a));
        a = base.clone(); heapSort(a); System.out.println("heap      " + Arrays.toString(a));
        System.out.println("counting  " + Arrays.toString(countingSort(base, 82)));
        System.out.println("original  " + Arrays.toString(base));
    }
}
