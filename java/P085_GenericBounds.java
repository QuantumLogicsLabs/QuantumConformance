import java.util.*;
import java.util.function.*;

public class P085_GenericBounds {
    interface Shape extends Comparable<Shape> {
        double area();
        default int compareTo(Shape o) { return Double.compare(area(), o.area()); }
    }

    static class Sq implements Shape {
        final double s;
        Sq(double s) { this.s = s; }
        public double area() { return s * s; }
        public String toString() { return "Sq" + (int) s; }
    }

    static class Circ implements Shape {
        final double r;
        Circ(double r) { this.r = r; }
        public double area() { return Math.PI * r * r; }
        public String toString() { return "Circ" + (int) r; }
    }

    static <T extends Comparable<? super T>> T maxOf(Collection<? extends T> xs) {
        Iterator<? extends T> it = xs.iterator();
        T best = it.next();
        while (it.hasNext()) {
            T x = it.next();
            if (x.compareTo(best) > 0) best = x;
        }
        return best;
    }

    static void fill(List<? super Integer> sink, int n) { for (int i = 0; i < n; i++) sink.add(i); }

    static <K, V extends Comparable<V>> K argMax(Map<K, V> m) {
        K best = null;
        V bv = null;
        for (Map.Entry<K, V> e : m.entrySet())
            if (bv == null || e.getValue().compareTo(bv) > 0) { bv = e.getValue(); best = e.getKey(); }
        return best;
    }

    static <A, B, C> Function<A, C> compose(Function<A, B> f, Function<B, C> g) { return a -> g.apply(f.apply(a)); }

    static <T> Map<Boolean, List<T>> split(List<T> xs, Predicate<? super T> p) {
        Map<Boolean, List<T>> m = new TreeMap<>();
        m.put(true, new ArrayList<>());
        m.put(false, new ArrayList<>());
        for (T x : xs) m.get(p.test(x)).add(x);
        return m;
    }

    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>(List.of(new Sq(3), new Circ(2), new Sq(1), new Circ(1)));
        Collections.sort(shapes);
        System.out.println(shapes + " max=" + maxOf(shapes));
        System.out.println(maxOf(List.of(3, 9, 4)) + " " + maxOf(Set.of("kiwi")));
        List<Number> sink = new ArrayList<>();
        fill(sink, 4);
        List<Object> objs = new ArrayList<>(List.of("start"));
        fill(objs, 2);
        System.out.println(sink + " " + objs);
        Map<String, Double> scores = new LinkedHashMap<>();
        scores.put("ann", 81.5);
        scores.put("bo", 92.0);
        scores.put("cy", 77.25);
        System.out.println(argMax(scores) + " " + argMax(new HashMap<String, Integer>()));
        Function<String, Integer> len = String::length;
        Function<String, String> f = compose(len, n -> "#".repeat(n));
        System.out.println(f.apply("four"));
        System.out.println(split(List.of(1, 2, 3, 4, 5, 6, 7), x -> x % 3 == 0));
    }
}
