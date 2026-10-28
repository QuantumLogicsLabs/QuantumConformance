import java.util.*;

public class P037_Dijkstra {
    static class Edge {
        final int to, w;
        Edge(int to, int w) { this.to = to; this.w = w; }
    }

    public static void main(String[] args) {
        int n = 6;
        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        int[][] es = {{0, 1, 7}, {0, 2, 9}, {0, 5, 14}, {1, 2, 10}, {1, 3, 15}, {2, 3, 11}, {2, 5, 2}, {3, 4, 6}, {4, 5, 9}};
        for (int[] e : es) {
            g.get(e[0]).add(new Edge(e[1], e[2]));
            g.get(e[1]).add(new Edge(e[0], e[2]));
        }
        long[] dist = new long[n];
        int[] prev = new int[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[0] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.add(new long[]{0, 0});
        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            int u = (int) top[1];
            if (top[0] > dist[u]) continue;
            for (Edge e : g.get(u)) {
                if (dist[u] + e.w < dist[e.to]) {
                    dist[e.to] = dist[u] + e.w;
                    prev[e.to] = u;
                    pq.add(new long[]{dist[e.to], e.to});
                }
            }
        }
        for (int v = 0; v < n; v++) {
            LinkedList<Integer> path = new LinkedList<>();
            for (int x = v; x != -1; x = prev[x]) path.addFirst(x);
            System.out.println(v + ": " + dist[v] + " via " + path);
        }
    }
}
