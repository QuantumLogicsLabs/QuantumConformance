import java.util.*;

public class P090_StaticInit {
    static final Map<String, Integer> UNITS;
    static final int[] SQUARES = new int[10];
    static int counter;

    static {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("kb", 1 << 10);
        m.put("mb", 1 << 20);
        m.put("gb", 1 << 30);
        UNITS = Collections.unmodifiableMap(m);
        for (int i = 0; i < SQUARES.length; i++) SQUARES[i] = i * i;
    }

    static class Lazy {
        static { System.out.println("Lazy loaded"); }
        static final String VALUE = make();
        static String make() { counter++; return "lazy-value"; }
    }

    static class Constants {
        static final int COMPILE_TIME = 7;
        static { System.out.println("Constants loaded"); }
    }

    final int id;
    final List<String> tags = new ArrayList<>();
    {
        id = ++counter;
        tags.add("init#" + id);
    }

    P090_StaticInit() { tags.add("ctor"); }
    P090_StaticInit(String extra) { this(); tags.add(extra); }

    static long parse(String s) {
        String unit = s.replaceAll("[0-9]", "").toLowerCase();
        long n = Long.parseLong(s.replaceAll("[^0-9]", ""));
        return n * UNITS.getOrDefault(unit, 1);
    }

    public static void main(String[] args) {
        System.out.println(UNITS + " " + Arrays.toString(SQUARES));
        System.out.println(parse("4KB") + " " + parse("3gb") + " " + parse("12"));
        try {
            UNITS.put("tb", 0);
        } catch (UnsupportedOperationException e) {
            System.out.println("immutable");
        }
        System.out.println("before lazy, counter=" + counter);
        System.out.println(Constants.COMPILE_TIME);
        System.out.println(Lazy.VALUE + " counter=" + counter);
        P090_StaticInit a = new P090_StaticInit(), b = new P090_StaticInit("extra");
        System.out.println(a.id + " " + a.tags + " " + b.id + " " + b.tags);
    }
}
