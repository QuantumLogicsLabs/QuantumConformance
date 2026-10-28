import java.util.*;

public class P046_LRUCache {
    static class LRU<K, V> extends LinkedHashMap<K, V> {
        private final int cap;
        LRU(int cap) {
            super(16, 0.75f, true);
            this.cap = cap;
        }
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) { return size() > cap; }
    }

    static class ManualLRU {
        private final int cap;
        private final Map<Integer, Integer> map = new HashMap<>();
        private final Deque<Integer> order = new ArrayDeque<>();
        ManualLRU(int cap) { this.cap = cap; }
        int get(int k) {
            if (!map.containsKey(k)) return -1;
            order.remove(k);
            order.addFirst(k);
            return map.get(k);
        }
        void put(int k, int v) {
            if (map.containsKey(k)) order.remove(k);
            else if (map.size() == cap) map.remove(order.removeLast());
            map.put(k, v);
            order.addFirst(k);
        }
        public String toString() { return order.toString(); }
    }

    public static void main(String[] args) {
        LRU<String, Integer> c = new LRU<>(3);
        c.put("a", 1);
        c.put("b", 2);
        c.put("c", 3);
        c.get("a");
        c.put("d", 4);
        System.out.println(c);
        c.get("c");
        c.put("e", 5);
        System.out.println(c.keySet());
        ManualLRU m = new ManualLRU(2);
        m.put(1, 1);
        m.put(2, 2);
        System.out.print(m.get(1) + " ");
        m.put(3, 3);
        System.out.print(m.get(2) + " ");
        m.put(4, 4);
        System.out.print(m.get(1) + " " + m.get(3) + " " + m.get(4));
        System.out.println(" " + m);
    }
}
