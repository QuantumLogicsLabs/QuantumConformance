public class P048_GameOfLife {
    static boolean[][] step(boolean[][] g) {
        int R = g.length, C = g[0].length;
        boolean[][] n = new boolean[R][C];
        for (int r = 0; r < R; r++)
            for (int c = 0; c < C; c++) {
                int live = 0;
                for (int dr = -1; dr <= 1; dr++)
                    for (int dc = -1; dc <= 1; dc++) {
                        if (dr == 0 && dc == 0) continue;
                        int rr = (r + dr + R) % R, cc = (c + dc + C) % C;
                        if (g[rr][cc]) live++;
                    }
                n[r][c] = g[r][c] ? (live == 2 || live == 3) : live == 3;
            }
        return n;
    }

    static void show(boolean[][] g, int gen) {
        System.out.println("gen " + gen + ":");
        for (boolean[] row : g) {
            StringBuilder sb = new StringBuilder();
            for (boolean b : row) sb.append(b ? '#' : '.');
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        boolean[][] g = new boolean[8][8];
        int[][] glider = {{0, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}};
        for (int[] p : glider) g[p[0]][p[1]] = true;
        g[5][5] = g[5][6] = g[5][7] = true;
        for (int gen = 0; gen <= 4; gen++) {
            if (gen % 2 == 0) show(g, gen);
            g = step(g);
        }
        int alive = 0;
        for (int i = 0; i < 20; i++) g = step(g);
        for (boolean[] row : g) for (boolean b : row) if (b) alive++;
        System.out.println("alive after 25 generations: " + alive);
    }
}
