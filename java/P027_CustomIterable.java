import java.util.Iterator;
import java.util.NoSuchElementException;

public class P027_CustomIterable {
    static class Range implements Iterable<Integer> {
        private final int lo, hi, step;
        Range(int lo, int hi, int step) { this.lo = lo; this.hi = hi; this.step = step; }
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                int cur = lo;
                public boolean hasNext() { return step > 0 ? cur < hi : cur > hi; }
                public Integer next() {
                    if (!hasNext()) throw new NoSuchElementException("done");
                    int v = cur;
                    cur += step;
                    return v;
                }
            };
        }
    }

    static class Fib implements Iterable<Long> {
        final int count;
        Fib(int count) { this.count = count; }
        public Iterator<Long> iterator() {
            return new Iterator<Long>() {
                long a = 0, b = 1;
                int n = 0;
                public boolean hasNext() { return n < count; }
                public Long next() {
                    long r = a;
                    long t = a + b;
                    a = b;
                    b = t;
                    n++;
                    return r;
                }
            };
        }
    }

    public static void main(String[] args) {
        for (int i : new Range(0, 10, 3)) System.out.print(i + " ");
        System.out.println();
        for (int i : new Range(5, 0, -2)) System.out.print(i + " ");
        System.out.println();
        StringBuilder sb = new StringBuilder();
        for (long f : new Fib(15)) sb.append(f).append(',');
        System.out.println(sb);
        Iterator<Integer> it = new Range(0, 1, 1).iterator();
        System.out.println(it.next());
        try {
            it.next();
        } catch (NoSuchElementException e) {
            System.out.println("exhausted: " + e.getMessage());
        }
        new Range(1, 4, 1).forEach(x -> System.out.print("[" + x + "]"));
        System.out.println();
    }
}
