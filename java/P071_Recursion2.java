import java.util.*;

public class P071_Recursion2 {
    static long power(long b, int e) {
        if (e == 0) return 1;
        long half = power(b, e / 2);
        return e % 2 == 0 ? half * half : half * half * b;
    }

    static int digitSum(long n) { return n < 10 ? (int) n : (int) (n % 10) + digitSum(n / 10); }

    static String toBinary(int n) { return n < 2 ? String.valueOf(n) : toBinary(n / 2) + (n % 2); }

    static boolean isPal(String s, int i, int j) { return i >= j || (s.charAt(i) == s.charAt(j) && isPal(s, i + 1, j - 1)); }

    static void combinations(int n, int k, int start, List<Integer> cur, List<String> out) {
        if (cur.size() == k) { out.add(cur.toString()); return; }
        for (int i = start; i <= n; i++) {
            cur.add(i);
            combinations(n, k, i + 1, cur, out);
            cur.remove(cur.size() - 1);
        }
    }

    static int floodFill(char[][] g, int r, int c) {
        if (r < 0 || c < 0 || r >= g.length || c >= g[0].length || g[r][c] != '.') return 0;
        g[r][c] = '~';
        return 1 + floodFill(g, r + 1, c) + floodFill(g, r - 1, c) + floodFill(g, r, c + 1) + floodFill(g, r, c - 1);
    }

    static long catalan(int n, long[] memo) {
        if (n <= 1) return 1;
        if (memo[n] != 0) return memo[n];
        long s = 0;
        for (int i = 0; i < n; i++) s += catalan(i, memo) * catalan(n - 1 - i, memo);
        return memo[n] = s;
    }

    static int depth = 0;
    static void deep(int n) { depth = Math.max(depth, n); if (n < 3000) deep(n + 1); }

    public static void main(String[] args) {
        System.out.println(power(3, 13) + " " + power(2, 62) + " " + digitSum(9876543210L) + " " + toBinary(37));
        System.out.println(isPal("level", 0, 4) + " " + isPal("levels", 0, 5));
        List<String> combos = new ArrayList<>();
        combinations(5, 3, 1, new ArrayList<>(), combos);
        System.out.println(combos.size() + " " + combos);
        char[][] g = {"..#..".toCharArray(), ".##..".toCharArray(), "#...#".toCharArray(), "..#..".toCharArray()};
        System.out.println(floodFill(g, 0, 0) + " " + floodFill(g, 0, 3) + " " + floodFill(g, 3, 0));
        for (char[] row : g) System.out.println(new String(row));
        long[] memo = new long[20];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) sb.append(catalan(i, memo)).append(' ');
        System.out.println(sb.toString().trim());
        deep(0);
        System.out.println("depth " + depth);
    }
}
