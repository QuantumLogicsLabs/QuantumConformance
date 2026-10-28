import java.util.Arrays;

public class P007_Arrays {
    public static void main(String[] args) {
        int[] a = {5, 3, 9, 1, 7};
        System.out.println(Arrays.toString(a));
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.binarySearch(a, 7));
        int[] b = new int[4];
        System.out.println(Arrays.toString(b));
        Arrays.fill(b, 9);
        System.out.println(Arrays.toString(b));
        int[] c = Arrays.copyOf(a, 7);
        System.out.println(Arrays.toString(c));
        int[] d = Arrays.copyOfRange(a, 1, 3);
        System.out.println(Arrays.toString(d));
        String[] names = new String[3];
        System.out.println(Arrays.toString(names));
        double[] ds = {1.5, 2.0};
        System.out.println(Arrays.toString(ds));
        boolean[] flags = new boolean[2];
        System.out.println(Arrays.toString(flags));
        char[] cs = {'d', 'a', 'c'};
        Arrays.sort(cs);
        System.out.println(new String(cs));
        System.out.println(Arrays.equals(new int[]{1, 2}, new int[]{1, 2}));
        int[] e = a.clone();
        e[0] = 100;
        System.out.println(a[0] + " " + e[0]);
        int total = 0;
        for (int v : a) total += v;
        System.out.println("total " + total + " len " + a.length);
    }
}
