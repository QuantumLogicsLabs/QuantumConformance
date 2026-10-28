import java.util.*;

public class P045_Trie {
    static class Trie {
        private final Map<Character, Trie> kids = new TreeMap<>();
        private boolean word;
        private int passing;

        void insert(String s) {
            Trie t = this;
            for (char c : s.toCharArray()) {
                t = t.kids.computeIfAbsent(c, k -> new Trie());
                t.passing++;
            }
            t.word = true;
        }

        Trie node(String prefix) {
            Trie t = this;
            for (int i = 0; i < prefix.length() && t != null; i++) t = t.kids.get(prefix.charAt(i));
            return t;
        }

        boolean contains(String s) { Trie t = node(s); return t != null && t.word; }
        int countPrefix(String p) { Trie t = node(p); return t == null ? 0 : t.passing; }

        void collect(String prefix, List<String> out) {
            if (word) out.add(prefix);
            for (Map.Entry<Character, Trie> e : kids.entrySet()) e.getValue().collect(prefix + e.getKey(), out);
        }

        List<String> complete(String prefix) {
            List<String> out = new ArrayList<>();
            Trie t = node(prefix);
            if (t != null) t.collect(prefix, out);
            return out;
        }
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        for (String w : new String[]{"car", "card", "care", "careful", "cat", "dog", "do", "dot"}) t.insert(w);
        System.out.println(t.contains("car") + " " + t.contains("ca") + " " + t.contains("careful"));
        System.out.println(t.countPrefix("car") + " " + t.countPrefix("do") + " " + t.countPrefix("x"));
        System.out.println(t.complete("car"));
        System.out.println(t.complete("d"));
        System.out.println(t.complete(""));
        System.out.println(t.complete("z"));
    }
}
