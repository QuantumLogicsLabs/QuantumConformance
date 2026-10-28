public class P044_HashTableImpl {
    static class Table<K, V> {
        private static class Entry<K, V> {
            final K key;
            V value;
            Entry<K, V> next;
            Entry(K k, V v, Entry<K, V> n) { key = k; value = v; next = n; }
        }

        private Entry<K, V>[] buckets;
        private int size;

        @SuppressWarnings("unchecked")
        Table(int cap) { buckets = (Entry<K, V>[]) new Entry[cap]; }

        private int index(Object k, int len) { return (k.hashCode() & 0x7fffffff) % len; }

        V put(K k, V v) {
            int i = index(k, buckets.length);
            for (Entry<K, V> e = buckets[i]; e != null; e = e.next)
                if (e.key.equals(k)) { V old = e.value; e.value = v; return old; }
            buckets[i] = new Entry<>(k, v, buckets[i]);
            if (++size > buckets.length * 3 / 4) resize();
            return null;
        }

        V get(K k) {
            for (Entry<K, V> e = buckets[index(k, buckets.length)]; e != null; e = e.next)
                if (e.key.equals(k)) return e.value;
            return null;
        }

        @SuppressWarnings("unchecked")
        private void resize() {
            Entry<K, V>[] nb = (Entry<K, V>[]) new Entry[buckets.length * 2];
            for (Entry<K, V> head : buckets)
                for (Entry<K, V> e = head; e != null; e = e.next) {
                    int i = index(e.key, nb.length);
                    nb[i] = new Entry<>(e.key, e.value, nb[i]);
                }
            buckets = nb;
        }

        int capacity() { return buckets.length; }
    }

    public static void main(String[] args) {
        Table<String, Integer> t = new Table<>(4);
        String[] words = "it was the best of times it was the worst of times".split(" ");
        for (String w : words) {
            Integer c = t.get(w);
            t.put(w, c == null ? 1 : c + 1);
        }
        for (String w : new String[]{"it", "was", "the", "best", "worst", "times", "nope"})
            System.out.println(w + "=" + t.get(w));
        System.out.println("size " + t.size + " capacity " + t.capacity());
        System.out.println("hash of 'hello' = " + "hello".hashCode() + ", of 42 = " + Integer.valueOf(42).hashCode());
        System.out.println(Boolean.hashCode(true) + " " + Long.hashCode(1L << 40) + " " + Character.valueOf('A').hashCode());
        System.out.println(java.util.Objects.hash(1, "a", 2.5));
        System.out.println(java.util.Arrays.hashCode(new int[]{1, 2, 3}));
    }
}
