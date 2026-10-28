import java.util.Arrays;
import java.util.Comparator;

public class P043_MinHeap {
    static class Heap<T> {
        private Object[] data = new Object[2];
        private int n;
        private final Comparator<? super T> cmp;

        Heap(Comparator<? super T> cmp) { this.cmp = cmp; }

        @SuppressWarnings("unchecked")
        private T at(int i) { return (T) data[i]; }

        void push(T v) {
            if (n == data.length) data = Arrays.copyOf(data, n * 2);
            data[n] = v;
            int i = n++;
            while (i > 0 && cmp.compare(at(i), at((i - 1) / 2)) < 0) {
                swap(i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
        }

        T pop() {
            T top = at(0);
            data[0] = data[--n];
            data[n] = null;
            int i = 0;
            while (true) {
                int l = 2 * i + 1, r = l + 1, m = i;
                if (l < n && cmp.compare(at(l), at(m)) < 0) m = l;
                if (r < n && cmp.compare(at(r), at(m)) < 0) m = r;
                if (m == i) break;
                swap(i, m);
                i = m;
            }
            return top;
        }

        private void swap(int a, int b) { Object t = data[a]; data[a] = data[b]; data[b] = t; }
        boolean isEmpty() { return n == 0; }
        int size() { return n; }
    }

    static class Task {
        final String name;
        final int priority;
        Task(String name, int priority) { this.name = name; this.priority = priority; }
        public String toString() { return name + "(" + priority + ")"; }
    }

    public static void main(String[] args) {
        Heap<Integer> h = new Heap<>(Comparator.naturalOrder());
        for (int v : new int[]{15, 3, 99, -4, 8, 3, 42, 0}) h.push(v);
        StringBuilder sb = new StringBuilder();
        while (!h.isEmpty()) sb.append(h.pop()).append(' ');
        System.out.println(sb.toString().trim());
        Heap<Task> tasks = new Heap<>(Comparator.comparingInt((Task t) -> -t.priority).thenComparing(t -> t.name));
        tasks.push(new Task("email", 2));
        tasks.push(new Task("deploy", 9));
        tasks.push(new Task("coffee", 9));
        tasks.push(new Task("lunch", 5));
        System.out.println("size " + tasks.size());
        while (!tasks.isEmpty()) System.out.print(tasks.pop() + " ");
        System.out.println();
    }
}
