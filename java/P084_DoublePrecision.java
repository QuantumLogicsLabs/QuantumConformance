public class P084_DoublePrecision {
    public static void main(String[] args) {
        double[] vals = {0.1, 0.5, 1.0 / 3, 2.0 / 3, 100.0, 1e-3, 1e-4, 9.999999e6, 1e7, 12345678.9, 1e21, 1e-300 * 1e-300, -0.0, 3.0e-5};
        for (double v : vals) System.out.println(v);
        float[] fs = {0.1f, 1.0f / 3, 16777217f, 1e10f, 3.14159265f};
        for (float f : fs) System.out.println(f);
        System.out.println(0.1 + 0.7);
        System.out.println(1.1 * 1.1);
        System.out.println(Math.sqrt(2));
        System.out.println((float) Math.sqrt(2));
        System.out.println(1.0 - 0.9);
        System.out.println(Double.MIN_NORMAL + " " + Math.ulp(1.0));
        System.out.println((0.1 + 0.2 == 0.3) + " " + (Math.abs(0.1 + 0.2 - 0.3) < 1e-9));
        double sum = 0;
        for (int i = 0; i < 10; i++) sum += 0.1;
        System.out.println(sum);
        System.out.println((long) 1e18 + " " + (long) 9.223372036854776E18 + " " + (int) 1e10f);
        System.out.println(5 / 2 + 5 / 2.0 + 5 % 2.5);
        System.out.println(String.format("%.15f", 0.1) + " " + String.format("%.3e", 123456.789));
        System.out.println(Double.toString(123e-10) + " " + Double.valueOf("  42.5 ") + " " + Float.parseFloat("1.5e2"));
        System.out.println(Math.round(0.49999999999999994) + " " + Math.round(-0.5) + " " + Math.round(1e20));
        System.out.println(7.0 / 0 + " " + 0.0 / 0 + " " + Math.sqrt(-1) + " " + (Double.NaN == Double.NaN));
    }
}
