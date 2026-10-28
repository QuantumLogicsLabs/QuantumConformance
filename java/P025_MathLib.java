public class P025_MathLib {
    public static void main(String[] args) {
        System.out.println(Math.sqrt(144) + " " + Math.cbrt(27) + " " + Math.pow(2, 10));
        System.out.println(Math.abs(-7) + " " + Math.abs(-7.5) + " " + Math.max(3, 8) + " " + Math.min(-1.5, 2));
        System.out.println(Math.floor(-2.5) + " " + Math.ceil(-2.5) + " " + Math.rint(2.5) + " " + Math.rint(3.5));
        System.out.println(Math.hypot(3, 4) + " " + Math.signum(-3.2));
        System.out.printf("%.4f %.4f %.4f%n", Math.sin(Math.PI / 6), Math.cos(0), Math.atan2(1, 1));
        System.out.printf("%.4f %.4f %.4f%n", Math.log(Math.E), Math.log10(1000), Math.exp(1));
        System.out.println(Math.floorDiv(-7, 2) + " " + Math.floorMod(-7, 2) + " " + (-7 / 2) + " " + (-7 % 2));
        System.out.println(Integer.MAX_VALUE + " " + Integer.MIN_VALUE + " " + Long.MIN_VALUE);
        System.out.println(Integer.bitCount(255) + " " + Integer.highestOneBit(100) + " " + Integer.numberOfTrailingZeros(8));
        System.out.println(Integer.reverse(1) + " " + Integer.signum(-9) + " " + Integer.compare(3, 7));
        System.out.println(Character.isDigit('7') + " " + Character.isLetter('x') + " " + Character.isUpperCase('Q'));
        System.out.println(Character.toUpperCase('m') + " " + Character.getNumericValue('8') + " " + (char) ('a' + 2));
        System.out.println(Character.isWhitespace(' ') + " " + Character.isLetterOrDigit('_'));
        System.out.println(Double.parseDouble("3.25") * 2 + " " + Long.parseLong("9000000000") + " " + Boolean.parseBoolean("TRUE"));
        System.out.println(Double.compare(1.5, 1.5) + " " + Double.isNaN(0.0 / 0) + " " + Double.MAX_VALUE);
        System.out.println(Integer.valueOf(127) == Integer.valueOf(127));
        System.out.println(Integer.valueOf(1000).equals(1000));
        System.out.println(Math.toDegrees(Math.PI) + " " + Math.toRadians(180));
        System.out.println(Math.addExact(2, 3));
        try {
            Math.multiplyExact(Integer.MAX_VALUE, 2);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
