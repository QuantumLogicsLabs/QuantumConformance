import java.util.*;

public class P040_Backtracking {
    static int queens(int n, int row, boolean[] cols, boolean[] d1, boolean[] d2) {
        if (row == n) return 1;
        int count = 0;
        for (int c = 0; c < n; c++) {
            if (cols[c] || d1[row + c] || d2[row - c + n - 1]) continue;
            cols[c] = d1[row + c] = d2[row - c + n - 1] = true;
            count += queens(n, row + 1, cols, d1, d2);
            cols[c] = d1[row + c] = d2[row - c + n - 1] = false;
        }
        return count;
    }

    static void permute(String prefix, String rest, List<String> out) {
        if (rest.isEmpty()) { out.add(prefix); return; }
        for (int i = 0; i < rest.length(); i++)
            permute(prefix + rest.charAt(i), rest.substring(0, i) + rest.substring(i + 1), out);
    }

    static void subsets(int[] a, int i, Deque<Integer> cur, List<List<Integer>> out) {
        if (i == a.length) { out.add(new ArrayList<>(cur)); return; }
        subsets(a, i + 1, cur, out);
        cur.addLast(a[i]);
        subsets(a, i + 1, cur, out);
        cur.removeLast();
    }

    static boolean sudokuOk(int[][] b, int r, int c, int v) {
        for (int i = 0; i < 4; i++) if (b[r][i] == v || b[i][c] == v) return false;
        int br = r / 2 * 2, bc = c / 2 * 2;
        for (int i = br; i < br + 2; i++) for (int j = bc; j < bc + 2; j++) if (b[i][j] == v) return false;
        return true;
    }

    static boolean solve(int[][] b) {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                if (b[r][c] == 0) {
                    for (int v = 1; v <= 4; v++)
                        if (sudokuOk(b, r, c, v)) {
                            b[r][c] = v;
                            if (solve(b)) return true;
                            b[r][c] = 0;
                        }
                    return false;
                }
        return true;
    }

    public static void main(String[] args) {
        for (int n = 1; n <= 8; n++)
            System.out.print(queens(n, 0, new boolean[n], new boolean[2 * n], new boolean[2 * n]) + " ");
        System.out.println();
        List<String> perms = new ArrayList<>();
        permute("", "abcd", perms);
        System.out.println(perms.size() + " " + perms.subList(0, 6));
        List<List<Integer>> subs = new ArrayList<>();
        subsets(new int[]{1, 2, 3}, 0, new ArrayDeque<>(), subs);
        System.out.println(subs);
        int[][] board = {{1, 0, 0, 0}, {0, 0, 3, 0}, {0, 4, 0, 0}, {0, 0, 0, 2}};
        System.out.println(solve(board));
        for (int[] row : board) System.out.println(Arrays.toString(row));
    }
}
