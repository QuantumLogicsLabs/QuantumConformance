public class P009_Methods {
    static int add(int a, int b) { return a + b; }
    static double add(double a, double b) { return a + b; }
    static String add(String a, String b) { return a + b; }
    static int sum(int... nums) {
        int t = 0;
        for (int n : nums) t += n;
        return t;
    }
    static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
    static void tryChange(int x) { x = 99; }

    public static void main(String[] args) {
        System.out.println(add(2, 3));
        System.out.println(add(2.5, 3));
        System.out.println(add("2", "3"));
        System.out.println(sum());
        System.out.println(sum(1, 2, 3, 4));
        System.out.println(sum(new int[]{10, 20}));
        int[] arr = {1, 2};
        swap(arr, 0, 1);
        System.out.println(arr[0] + "," + arr[1]);
        int v = 5;
        tryChange(v);
        System.out.println(v);
    }
}
