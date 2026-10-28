public class P024_Formatting {
    public static void main(String[] args) {
        System.out.println(String.format("|%5d|%-5d|%05d|", 42, 42, 42));
        System.out.println(String.format("|%8.3f|%-8.2f|%.0f|", Math.PI, Math.E, 2.5));
        System.out.println(String.format("|%10s|%-10s|", "right", "left"));
        System.out.println(String.format("%x %X %o", 255, 255, 8));
        System.out.println(String.format("%,d", 1234567890));
        System.out.println(String.format("%e", 12345.678));
        System.out.println(String.format("%b %c %%", true, 'Z'));
        System.out.println(String.format("%s %s", null, 3.0));
        System.out.printf("%d items at $%.2f = $%,.2f%n", 1200, 3.5, 1200 * 3.5);
        System.out.printf("%3$s %1$s %2$s%n", "a", "b", "c");
        System.out.println(String.format("%+d %+d", 5, -5));
        System.out.println(String.format("[%6.2f]", -3.14159));
        System.out.println(Integer.toBinaryString(42) + " " + Integer.toHexString(255) + " " + Integer.toOctalString(8));
        System.out.println(Integer.toString(255, 16) + " " + Integer.parseInt("ff", 16) + " " + Integer.parseInt("-101", 2));
        System.out.println(Long.toBinaryString(5L) + " " + Integer.toBinaryString(-1).length());
        System.out.println(Double.toString(0.001) + " " + 1.0E7 + " " + 1234567.0 + " " + 0.0001);
        System.out.println(100.0f + " " + 1.1f + " " + (double) 1.1f);
        System.out.println(Math.round(2.5) + " " + Math.round(-2.5) + " " + Math.round(2.4f));
        System.out.println(Double.NaN + " " + (1.0 / 0) + " " + (-1.0 / 0) + " " + (0.0 == -0.0));
    }
}
