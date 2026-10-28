import java.util.*;

public class P076_HashCodes {
    static class Key {
        final String a;
        final int b;
        Key(String a, int b) { this.a = a; this.b = b; }
        @Override public boolean equals(Object o) {
            if (!(o instanceof Key)) return false;
            Key k = (Key) o;
            return a.equals(k.a) && b == k.b;
        }
        @Override public int hashCode() { return Objects.hash(a, b); }
        @Override public String toString() { return a + b; }
    }

    static class NoHash {
        final int v;
        NoHash(int v) { this.v = v; }
        @Override public boolean equals(Object o) { return o instanceof NoHash && ((NoHash) o).v == v; }
    }

    public static void main(String[] args) {
        for (String s : new String[]{"", "a", "Aa", "BB", "hello world", "Quantum"}) System.out.print(s.hashCode() + " ");
        System.out.println();
        System.out.println(Integer.hashCode(-7) + " " + Long.hashCode(-1L) + " " + Double.hashCode(1.5) + " " + Boolean.hashCode(false));
        System.out.println(Arrays.hashCode(new String[]{"x", "y"}) + " " + List.of(1, 2, 3).hashCode() + " " + Set.of().hashCode());
        System.out.println(new Key("k", 1).hashCode() + " " + Objects.hash() + " " + Objects.hash((Object) null));
        Set<Key> keys = new HashSet<>();
        keys.add(new Key("x", 1));
        keys.add(new Key("x", 1));
        keys.add(new Key("x", 2));
        System.out.println(keys.size() + " " + keys.contains(new Key("x", 2)));
        Set<NoHash> nh = new HashSet<>();
        nh.add(new NoHash(1));
        System.out.println(new NoHash(1).equals(new NoHash(1)) + " " + nh.size());
        Map<String, Integer> m = new HashMap<>();
        for (String w : "one two three four five six seven eight nine ten".split(" ")) m.put(w, w.length());
        System.out.println(m);
        Set<Integer> nums = new HashSet<>(List.of(100, 3, 17, 42, 64, 5, 1000));
        System.out.println(nums);
        Map<Key, String> km = new HashMap<>();
        km.put(new Key("p", 9), "nine");
        System.out.println(km.get(new Key("p", 9)) + " " + km.containsKey(new Key("p", 8)));
    }
}
