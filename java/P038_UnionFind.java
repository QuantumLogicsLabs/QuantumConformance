import java.util.*;

public class P038_UnionFind {
    static class DSU {
        private final int[] parent, size;
        int sets;
        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            sets = n;
            for (int i = 0; i < n; i++) { parent[i] = i; size[i] = 1; }
        }
        int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (size[ra] < size[rb]) { int t = ra; ra = rb; rb = t; }
            parent[rb] = ra;
            size[ra] += size[rb];
            sets--;
            return true;
        }
    }

    public static void main(String[] args) {
        int[][] edges = {{0, 1, 4}, {0, 7, 8}, {1, 2, 8}, {1, 7, 11}, {2, 3, 7}, {2, 8, 2}, {2, 5, 4},
                {3, 4, 9}, {3, 5, 14}, {4, 5, 10}, {5, 6, 2}, {6, 7, 1}, {6, 8, 6}, {7, 8, 7}};
        Arrays.sort(edges, (a, b) -> a[2] != b[2] ? Integer.compare(a[2], b[2]) : Integer.compare(a[0], b[0]));
        DSU d = new DSU(9);
        int total = 0;
        List<String> used = new ArrayList<>();
        for (int[] e : edges) {
            if (d.union(e[0], e[1])) {
                total += e[2];
                used.add(e[0] + "-" + e[1]);
            }
        }
        System.out.println("MST weight " + total + " edges " + used);
        System.out.println("sets left " + d.sets);
        DSU s = new DSU(10);
        s.union(1, 2);
        s.union(3, 4);
        s.union(2, 4);
        System.out.println(s.find(1) == s.find(3));
        System.out.println(s.find(5) == s.find(1));
        System.out.println("sets " + s.sets);
    }
}
