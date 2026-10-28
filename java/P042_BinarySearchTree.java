import java.util.*;
import java.util.function.Consumer;

public class P042_BinarySearchTree {
    static class BST<K extends Comparable<K>> {
        class Node {
            K key;
            Node left, right;
            Node(K k) { key = k; }
        }
        Node root;
        int size;

        boolean insert(K k) {
            if (root == null) { root = new Node(k); size++; return true; }
            Node cur = root;
            while (true) {
                int c = k.compareTo(cur.key);
                if (c == 0) return false;
                if (c < 0) {
                    if (cur.left == null) { cur.left = new Node(k); size++; return true; }
                    cur = cur.left;
                } else {
                    if (cur.right == null) { cur.right = new Node(k); size++; return true; }
                    cur = cur.right;
                }
            }
        }

        boolean contains(K k) {
            Node cur = root;
            while (cur != null) {
                int c = k.compareTo(cur.key);
                if (c == 0) return true;
                cur = c < 0 ? cur.left : cur.right;
            }
            return false;
        }

        Node delete(Node n, K k) {
            if (n == null) return null;
            int c = k.compareTo(n.key);
            if (c < 0) n.left = delete(n.left, k);
            else if (c > 0) n.right = delete(n.right, k);
            else {
                if (n.left == null) return n.right;
                if (n.right == null) return n.left;
                Node m = n.right;
                while (m.left != null) m = m.left;
                n.key = m.key;
                n.right = delete(n.right, m.key);
            }
            return n;
        }

        void inorder(Node n, Consumer<K> f) {
            if (n == null) return;
            inorder(n.left, f);
            f.accept(n.key);
            inorder(n.right, f);
        }

        int height(Node n) { return n == null ? 0 : 1 + Math.max(height(n.left), height(n.right)); }

        List<List<K>> levels() {
            List<List<K>> out = new ArrayList<>();
            Queue<Node> q = new LinkedList<>();
            if (root != null) q.add(root);
            while (!q.isEmpty()) {
                List<K> lvl = new ArrayList<>();
                for (int i = q.size(); i > 0; i--) {
                    Node n = q.remove();
                    lvl.add(n.key);
                    if (n.left != null) q.add(n.left);
                    if (n.right != null) q.add(n.right);
                }
                out.add(lvl);
            }
            return out;
        }
    }

    public static void main(String[] args) {
        BST<Integer> t = new BST<>();
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80, 30, 65}) t.insert(v);
        List<Integer> in = new ArrayList<>();
        t.inorder(t.root, in::add);
        System.out.println(in + " size " + t.size + " height " + t.height(t.root));
        System.out.println(t.levels());
        System.out.println(t.contains(65) + " " + t.contains(66));
        t.root = t.delete(t.root, 50);
        t.root = t.delete(t.root, 20);
        StringBuilder sb = new StringBuilder();
        t.inorder(t.root, k -> sb.append(k).append(' '));
        System.out.println(sb.toString().trim());
        System.out.println(t.levels());
        BST<String> words = new BST<>();
        for (String w : "the quick brown fox jumps over the lazy dog".split(" ")) words.insert(w);
        List<String> ws = new ArrayList<>();
        words.inorder(words.root, ws::add);
        System.out.println(ws);
    }
}
