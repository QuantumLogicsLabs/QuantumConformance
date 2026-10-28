import java.util.*;
import java.util.function.*;

public class P096_GenericCache {
    static class Memo<K, V> {
        private final Map<K, V> cache = new HashMap<>();
        private final Function<K, V> fn;
        int hits, misses;
        Memo(Function<K, V> fn) { this.fn = fn; }
        V get(K k) {
            if (cache.containsKey(k)) { hits++; return cache.get(k); }
            misses++;
            V v = fn.apply(k);
            cache.put(k, v);
            return v;
        }
    }

    static Memo<Integer, Long> fibMemo;

    static class Graph<T> {
        private final Map<T, Set<T>> adj = new LinkedHashMap<>();
        Graph<T> edge(T a, T b) {
            adj.computeIfAbsent(a, k -> new LinkedHashSet<>()).add(b);
            adj.computeIfAbsent(b, k -> new LinkedHashSet<>()).add(a);
            return this;
        }
        List<T> path(T from, T to) {
            Map<T, T> parent = new HashMap<>();
            Deque<T> q = new ArrayDeque<>(List.of(from));
            parent.put(from, null);
            while (!q.isEmpty()) {
                T u = q.poll();
                if (u.equals(to)) break;
                for (T v : adj.getOrDefault(u, Set.of()))
                    if (!parent.containsKey(v)) { parent.put(v, u); q.add(v); }
            }
            if (!parent.containsKey(to)) return List.of();
            LinkedList<T> p = new LinkedList<>();
            for (T x = to; x != null; x = parent.get(x)) p.addFirst(x);
            return p;
        }
    }

    public static void main(String[] args) {
        fibMemo = new Memo<>(n -> n < 2 ? (long) n : fibMemo.get(n - 1) + fibMemo.get(n - 2));
        System.out.println(fibMemo.get(80) + " hits=" + fibMemo.hits + " misses=" + fibMemo.misses);
        Memo<String, Integer> vowels = new Memo<>(s -> (int) s.chars().filter(c -> "aeiou".indexOf(c) >= 0).count());
        for (String w : "banana apple banana kiwi apple banana".split(" ")) System.out.print(vowels.get(w) + " ");
        System.out.println("hits=" + vowels.hits);
        Graph<String> g = new Graph<String>().edge("home", "park").edge("park", "lake").edge("home", "shop")
                .edge("shop", "school").edge("school", "lake").edge("island", "boat");
        System.out.println(g.path("home", "lake") + " " + g.path("lake", "shop") + " " + g.path("home", "boat"));
        Graph<Integer> ring = new Graph<>();
        for (int i = 0; i < 10; i++) ring.edge(i, (i + 1) % 10);
        System.out.println(ring.path(2, 8));
    }
}
