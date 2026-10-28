import java.util.*;

public class P089_Huffman {
    static abstract class Node implements Comparable<Node> {
        final int freq;
        final int order;
        Node(int f, int o) { freq = f; order = o; }
        public int compareTo(Node o) { return freq != o.freq ? Integer.compare(freq, o.freq) : Integer.compare(order, o.order); }
    }

    static class Leaf extends Node {
        final char ch;
        Leaf(char c, int f, int o) { super(f, o); ch = c; }
    }

    static class Internal extends Node {
        final Node left, right;
        Internal(Node l, Node r, int o) { super(l.freq + r.freq, o); left = l; right = r; }
    }

    static void codes(Node n, String prefix, Map<Character, String> out) {
        if (n instanceof Leaf) {
            out.put(((Leaf) n).ch, prefix.isEmpty() ? "0" : prefix);
            return;
        }
        Internal in = (Internal) n;
        codes(in.left, prefix + "0", out);
        codes(in.right, prefix + "1", out);
    }

    public static void main(String[] args) {
        String text = "abracadabra alakazam";
        Map<Character, Integer> freq = new TreeMap<>();
        for (char c : text.toCharArray()) freq.merge(c, 1, Integer::sum);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int order = 0;
        for (Map.Entry<Character, Integer> e : freq.entrySet()) pq.add(new Leaf(e.getKey(), e.getValue(), order++));
        while (pq.size() > 1) {
            Node a = pq.poll(), b = pq.poll();
            pq.add(new Internal(a, b, order++));
        }
        Node root = pq.poll();
        Map<Character, String> code = new TreeMap<>();
        codes(root, "", code);
        code.forEach((c, s) -> System.out.println("'" + c + "' x" + freq.get(c) + " -> " + s));
        StringBuilder bits = new StringBuilder();
        for (char c : text.toCharArray()) bits.append(code.get(c));
        System.out.println(bits.length() + " bits vs " + text.length() * 8);
        StringBuilder decoded = new StringBuilder();
        Node cur = root;
        for (int i = 0; i < bits.length(); i++) {
            cur = bits.charAt(i) == '0' ? ((Internal) cur).left : ((Internal) cur).right;
            if (cur instanceof Leaf) {
                decoded.append(((Leaf) cur).ch);
                cur = root;
            }
        }
        System.out.println(decoded + " " + decoded.toString().equals(text));
    }
}
