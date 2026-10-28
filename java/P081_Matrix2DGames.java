import java.util.*;

public class P081_Matrix2DGames {
    static int[][] rotate(int[][] m) {
        int n = m.length;
        int[][] r = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) r[j][n - 1 - i] = m[i][j];
        return r;
    }

    static List<Integer> spiral(int[][] m) {
        List<Integer> out = new ArrayList<>();
        int top = 0, bot = m.length - 1, left = 0, right = m[0].length - 1;
        while (top <= bot && left <= right) {
            for (int j = left; j <= right; j++) out.add(m[top][j]);
            top++;
            for (int i = top; i <= bot; i++) out.add(m[i][right]);
            right--;
            if (top <= bot) { for (int j = right; j >= left; j--) out.add(m[bot][j]); bot--; }
            if (left <= right) { for (int i = bot; i >= top; i--) out.add(m[i][left]); left++; }
        }
        return out;
    }

    static int islands(char[][] g) {
        int count = 0;
        boolean[][] seen = new boolean[g.length][g[0].length];
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++) {
                if (g[i][j] != '1' || seen[i][j]) continue;
                count++;
                Deque<int[]> st = new ArrayDeque<>();
                st.push(new int[]{i, j});
                seen[i][j] = true;
                while (!st.isEmpty()) {
                    int[] p = st.pop();
                    for (int[] d : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                        int r = p[0] + d[0], c = p[1] + d[1];
                        if (r >= 0 && c >= 0 && r < g.length && c < g[0].length && g[r][c] == '1' && !seen[r][c]) {
                            seen[r][c] = true;
                            st.push(new int[]{r, c});
                        }
                    }
                }
            }
        return count;
    }

    static int[][] magic(int n) {
        int[][] m = new int[n][n];
        int i = 0, j = n / 2;
        for (int k = 1; k <= n * n; k++) {
            m[i][j] = k;
            int ni = (i - 1 + n) % n, nj = (j + 1) % n;
            if (m[ni][nj] != 0) { ni = (i + 1) % n; nj = j; }
            i = ni;
            j = nj;
        }
        return m;
    }

    public static void main(String[] args) {
        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(Arrays.deepToString(rotate(m)));
        System.out.println(spiral(m) + " " + spiral(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}}));
        String[] map = {"11000", "11010", "00100", "00011", "10011"};
        char[][] g = new char[map.length][];
        for (int i = 0; i < map.length; i++) g[i] = map[i].toCharArray();
        System.out.println("islands: " + islands(g));
        int[][] sq = magic(5);
        for (int[] row : sq) {
            StringBuilder sb = new StringBuilder();
            for (int v : row) sb.append(String.format("%3d", v));
            System.out.println(sb);
        }
        int diag = 0;
        for (int i = 0; i < 5; i++) diag += sq[i][i];
        System.out.println("diagonal sum " + diag);
    }
}
