import java.util.ArrayList;
import java.util.List;

public class P014_Generics {
    static class Box<T> {
        private T value;
        Box(T value) { this.value = value; }
        T get() { return value; }
        void set(T v) { value = v; }
        <R> Box<R> map(java.util.function.Function<T, R> f) { return new Box<>(f.apply(value)); }
        public String toString() { return "Box(" + value + ")"; }
    }

    static class Pair<A, B> {
        final A first;
        final B second;
        Pair(A a, B b) { first = a; second = b; }
        public String toString() { return "<" + first + ", " + second + ">"; }
    }

    static <T extends Comparable<T>> T max(List<T> list) {
        T best = list.get(0);
        for (T t : list) if (t.compareTo(best) > 0) best = t;
        return best;
    }

    static <T> void printAll(List<? extends T> items) {
        for (T t : items) System.out.print(t + ";");
        System.out.println();
    }

    static double sum(List<? extends Number> nums) {
        double s = 0;
        for (Number n : nums) s += n.doubleValue();
        return s;
    }

    public static void main(String[] args) {
        Box<Integer> b = new Box<>(21);
        Box<String> s = b.map(x -> "v" + (x * 2));
        System.out.println(b + " " + s);
        b.set(5);
        System.out.println(b.get() + 1);
        Pair<String, Integer> p = new Pair<>("age", 30);
        System.out.println(p);
        List<Integer> nums = new ArrayList<>(List.of(4, 9, 2, 7));
        System.out.println(max(nums));
        System.out.println(max(List.of("pear", "apple", "zebra", "mango")));
        printAll(nums);
        List<Double> ds = List.of(1.5, 2.5);
        System.out.println(sum(ds) + sum(nums));
    }
}
