public class P052_BitManipulation {
    static int popcount(int x) {
        int c = 0;
        while (x != 0) { x &= x - 1; c++; }
        return c;
    }

    static String bits(int x, int width) {
        StringBuilder sb = new StringBuilder();
        for (int i = width - 1; i >= 0; i--) sb.append((x >>> i) & 1);
        return sb.toString();
    }

    public static void main(String[] args) {
        int a = 0b1011_0110, b = 0x5A;
        System.out.println(a + " " + b);
        System.out.println((a & b) + " " + (a | b) + " " + (a ^ b) + " " + (~a));
        System.out.println((a << 3) + " " + (a >> 2) + " " + (-a >> 2) + " " + (-a >>> 28));
        System.out.println((1 << 31) + " " + (1 << 32) + " " + (1L << 32) + " " + (1L << 63));
        System.out.println(popcount(a) + " " + popcount(-1) + " " + Integer.bitCount(a));
        System.out.println(bits(a, 8) + " " + bits(-6, 8));
        int x = 0;
        x |= 1 << 3;
        x |= 1 << 5;
        x &= ~(1 << 3);
        x ^= 1;
        System.out.println(x + " " + ((x >> 5) & 1));
        int lowest = 40 & -40;
        System.out.println("lowest set bit of 40: " + lowest);
        System.out.println("power of two: " + ((64 & 63) == 0) + " " + ((96 & 95) == 0));
        long mask = 0xFFFF_FFFFL;
        System.out.println(mask + " " + (-1 & mask) + " " + Long.toHexString(-1L));
        int s = 0;
        for (int sub = 0b1011; sub > 0; sub = (sub - 1) & 0b1011) s += sub;
        System.out.println("subset sum " + s);
        int p = 5, q = 9;
        p ^= q; q ^= p; p ^= q;
        System.out.println(p + " " + q);
        byte by = (byte) 0xF0;
        System.out.println(by + " " + (by & 0xFF) + " " + (by >> 2) + " " + (by >>> 2) + " " + ((by & 0xFF) >>> 2));
        char c = 'A';
        c += 2;
        c <<= 1;
        System.out.println((int) c);
        int sh = 33;
        System.out.println((8 << sh) + " " + (8L << 65));
    }
}
