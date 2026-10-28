import java.util.*;

public class P036_GraphBFS {
    static int shortest(char[][] grid, int sr, int sc, int tr, int tc) {
        int R = grid.length, C = grid[0].length;
        int[][] dist = new int[R][C];
        for (int[] row : dist) Arrays.fill(row, -1);
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sr, sc});
        dist[sr][sc] = 0;
        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            if (cur[0] == tr && cur[1] == tc) return dist[tr][tc];
            for (int k = 0; k < 4; k++) {
                int nr = cur[0] + dr[k], nc = cur[1] + dc[k];
                if (nr < 0 || nc < 0 || nr >= R || nc >= C || grid[nr][nc] == '#' || dist[nr][nc] >= 0) continue;
                dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                q.add(new int[]{nr, nc});
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] maze = {
                "S..#......",
                ".#.#.####.",
                ".#...#....",
                ".####.#.#.",
                "......#.#T"
        };
        char[][] g = new char[maze.length][];
        for (int i = 0; i < maze.length; i++) g[i] = maze[i].toCharArray();
        System.out.println("maze distance: " + shortest(g, 0, 0, 4, 9));
        Map<String, List<String>> adj = new TreeMap<>();
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"B", "D"}, {"C", "D"}, {"D", "E"}, {"F", "G"}};
        for (String[] e : edges) {
            adj.computeIfAbsent(e[0], k -> new ArrayList<>()).add(e[1]);
            adj.computeIfAbsent(e[1], k -> new ArrayList<>()).add(e[0]);
        }
        Set<String> seen = new HashSet<>();
        int components = 0;
        for (String start : adj.keySet()) {
            if (seen.contains(start)) continue;
            components++;
            List<String> order = new ArrayList<>();
            Deque<String> q = new ArrayDeque<>();
            q.add(start);
            seen.add(start);
            while (!q.isEmpty()) {
                String u = q.removeFirst();
                order.add(u);
                for (String v : adj.get(u)) if (seen.add(v)) q.addLast(v);
            }
            System.out.println("component " + components + ": " + order);
        }
    }
}
