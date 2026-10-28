import java.util.*;
import java.util.function.*;

public class P098_Anonymous2 {
    interface Counter { int next(); int peek(); }

    static Counter make(int start, int step) {
        return new Counter() {
            private int cur = start;
            public int next() { int v = cur; cur += step; return v; }
            public int peek() { return cur; }
            @Override public String toString() { return "Counter@" + cur; }
        };
    }

    static abstract class Template {
        final String run() { return "[" + before() + body() + after() + "]"; }
        String before() { return "<"; }
        abstract String body();
        String after() { return ">"; }
    }

    static List<Supplier<String>> closures() {
        List<Supplier<String>> out = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final int k = i;
            out.add(() -> "closure" + k);
        }
        for (String s : new String[]{"x", "y"}) out.add(() -> s + s);
        return out;
    }

    public static void main(String[] args) {
        Counter c = make(10, 5);
        c.next();
        c.next();
        System.out.println(c.next() + " " + c.peek() + " " + c);
        Template t1 = new Template() { String body() { return "one"; } };
        Template t2 = new Template() {
            String body() { return "two"; }
            @Override String after() { return "/>"; }
        };
        System.out.println(t1.run() + t2.run());
        for (Supplier<String> s : closures()) System.out.print(s.get() + " ");
        System.out.println();
        Map<String, BinaryOperator<Integer>> ops = new LinkedHashMap<>();
        ops.put("add", (a, b) -> a + b);
        ops.put("sub", new BinaryOperator<Integer>() {
            public Integer apply(Integer a, Integer b) { return a - b; }
        });
        ops.put("pow", (a, b) -> (int) Math.pow(a, b));
        ops.forEach((k, f) -> System.out.print(k + "=" + f.apply(2, 5) + " "));
        System.out.println();
        Object o = new Object() { @Override public String toString() { return "custom object"; } };
        System.out.println(o);
        Thread th = new Thread(() -> System.out.println("thread body ran"));
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        System.out.println("joined");
    }
}
