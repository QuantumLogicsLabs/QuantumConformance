import java.util.*;

public class P061_Wrappers {
    public static void main(String[] args) {
        Integer a = 127, b = 127, c = 128, d = 128;
        System.out.println((a == b) + " " + (c == d) + " " + c.equals(d));
        Long l = 5L;
        System.out.println(l.equals(5) + " " + l.equals(5L) + " " + (l == 5));
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) list.add(i * 100);
        int sum = 0;
        for (Integer x : list) sum += x;
        System.out.println(sum);
        Integer boxed = null;
        try {
            int unboxed = boxed;
            System.out.println(unboxed);
        } catch (NullPointerException e) {
            System.out.println("unboxing null");
        }
        Double dd = 2.5;
        double prim = dd * 2;
        System.out.println(prim + " " + dd.intValue() + " " + Double.valueOf("1e3"));
        Character ch = 'x';
        System.out.println(Character.isLetter(ch) + " " + (char) (ch + 1));
        Boolean flag = Boolean.valueOf("yes");
        System.out.println(flag + " " + Boolean.TRUE + " " + Boolean.logicalXor(true, false));
        System.out.println(Integer.sum(3, 4) + " " + Integer.max(3, 4) + " " + Long.min(-1L, 2L));
        System.out.println(Integer.toString(42).length() + " " + Integer.valueOf(65).byteValue() + " " + Short.MAX_VALUE + " " + Byte.MIN_VALUE);
        Object[] mixed = {1, 2L, 3.0, 4.0f, 'c', "s", true, null};
        for (Object o : mixed) System.out.print((o == null ? "null" : o.getClass().getSimpleName()) + " ");
        System.out.println();
        Map<Character, Integer> m = new HashMap<>();
        m.put('a', 1);
        m.put('a', m.get('a') + 1);
        System.out.println(m);
        System.out.println(Integer.parseInt("+15") + Integer.valueOf("-5") + " " + Integer.decode("0x1F") + " " + Integer.compareUnsigned(-1, 1));
        System.out.println(Double.MIN_VALUE + " " + Float.MAX_VALUE + " " + Double.POSITIVE_INFINITY);
    }
}
