import java.util.*;

public class P086_Graphs2 {
    static final int INF = 1_000_000;

    static int[][] floyd(int[][] w) {
        int n = w.length;
        int[][] d = new int[n][];
        for (int i = 0; i < n; i++) d[i] = w[i].clone();
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (d[i][k] + d[k][j] < d[i][j]) d[i][j] = d[i][k] + d[k][j];
        return d;
    }

    static int[] bellmanFord(int n, int[][] edges, int src) {
        int[] d = new int[n];
        Arrays.fill(d, INF);
        d[src] = 0;
        for (int i = 0; i < n - 1; i++)
            for (int[] e : edges)
                if (d[e[0]] != INF && d[e[0]] + e[2] < d[e[1]]) d[e[1]] = d[e[0]] + e[2];
        for (int[] e : edges)
            if (d[e[0]] != INF && d[e[0]] + e[2] < d[e[1]]) throw new IllegalStateException("negative cycle");
        return d;
    }

    static List<List<Integer>> sccs;
    static int timer;
    static int[] low, num;
    static boolean[] onStack;
    static Deque<Integer> stack;

    static void tarjan(int u, List<List<Integer>> g) {
        low[u] = num[u] = ++timer;
        stack.push(u);
        onStack[u] = true;
        for (int v : g.get(u)) {
            if (num[v] == 0) { tarjan(v, g); low[u] = Math.min(low[u], low[v]); }
            else if (onStack[v]) low[u] = Math.min(low[u], num[v]);
        }
        if (low[u] == num[u]) {
            List<Integer> comp = new ArrayList<>();
            int v;
            do { v = stack.pop(); onStack[v] = false; comp.add(v); } while (v != u);
            Collections.sort(comp);
            sccs.add(comp);
        }
    }

    public static void main(String[] args) {
        int[][] w = {{0, 3, INF, 7}, {8, 0, 2, INF}, {5, INF, 0, 1}, {2, INF, INF, 0}};
        for (int[] row : floyd(w)) System.out.println(Arrays.toString(row));
        int[][] edges = {{0, 1, 6}, {0, 2, 7}, {1, 2, 8}, {1, 3, 5}, {1, 4, -4}, {2, 3, -3}, {2, 4, 9}, {3, 1, -2}, {4, 0, 2}, {4, 3, 7}};
        System.out.println(Arrays.toString(bellmanFord(5, edges, 0)));
        try {
            bellmanFord(3, new int[][]{{0, 1, 1}, {1, 2, -2}, {2, 0, -1}}, 0);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        int n = 8;
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        int[][] es = {{0, 1}, {1, 2}, {2, 0}, {2, 3}, {3, 4}, {4, 5}, {5, 3}, {6, 5}, {6, 7}, {7, 6}};
        for (int[] e : es) g.get(e[0]).add(e[1]);
        sccs = new ArrayList<>();
        low = new int[n];
        num = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (num[i] == 0) tarjan(i, g);
        sccs.sort(Comparator.comparingInt(c -> c.get(0)));
        System.out.println(sccs);
    }
}
