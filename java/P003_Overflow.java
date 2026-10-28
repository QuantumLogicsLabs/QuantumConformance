public class P003_Overflow {
    public static void main(String[] args) {
        int max = Integer.MAX_VALUE;
        System.out.println(max);
        System.out.println(max + 1);
        System.out.println(Integer.MIN_VALUE - 1);
        long big = Long.MAX_VALUE;
        System.out.println(big);
        System.out.println(big + 1);
        int m = 100000;
        System.out.println(m * m);
        System.out.println((long) m * m);
        short s = (short) 40000;
        System.out.println(s);
        byte bt = (byte) 200;
        System.out.println(bt);
        char c = (char) 65;
        System.out.println(c);
        System.out.println((int) 'z');
        System.out.println((int) 3.99);
        System.out.println((int) -3.99);
        System.out.println((long) 1e19);
        System.out.println((int) Double.NaN);
        System.out.println(Math.abs(Integer.MIN_VALUE));
    }
}
