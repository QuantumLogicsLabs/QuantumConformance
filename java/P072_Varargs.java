import java.util.*;

public class P072_Varargs {
    static String describe(Object... xs) {
        if (xs == null) return "null array";
        return xs.length + ":" + Arrays.toString(xs);
    }

    static int max(int first, int... rest) {
        int m = first;
        for (int r : rest) m = Math.max(m, r);
        return m;
    }

    static <T> List<T> listOf(T... items) { return new ArrayList<>(Arrays.asList(items)); }

    static String fmt(String pattern, Object... args) { return String.format(pattern, args); }

    static double avg(double... ds) {
        if (ds.length == 0) return Double.NaN;
        double s = 0;
        for (double d : ds) s += d;
        return s / ds.length;
    }

    public static void main(String[] args) {
        System.out.println(describe());
        System.out.println(describe(1, "two", 3.0));
        System.out.println(describe((Object[]) null));
        System.out.println(describe((Object) new String[]{"x"}).startsWith("1:"));
        System.out.println(describe(new String[]{"a", "b"}));
        System.out.println(max(4) + " " + max(4, 9, 2) + " " + max(-1, new int[]{-5, -3}));
        List<String> l = listOf("x", "y", "z");
        l.add("w");
        System.out.println(l + " " + listOf().size() + " " + listOf(1, 2).get(1));
        System.out.println(fmt("%s-%s-%s", "a", 1, true) + " " + fmt("plain"));
        System.out.println(avg() + " " + avg(1, 2, 3, 4) + " " + avg(2.5));
        System.out.println(String.join("+", "a", "b") + " " + String.join("", new ArrayList<String>()) + "|");
        System.out.println(Arrays.asList(1, 2, 3).size() + " " + List.of().isEmpty() + " " + Math.max(1, 2));
    }
}
