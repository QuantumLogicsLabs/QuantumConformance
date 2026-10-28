import java.util.*;

public class P035_DynamicProgramming {
    static int knapsack(int[] w, int[] v, int cap) {
        int[] dp = new int[cap + 1];
        for (int i = 0; i < w.length; i++)
            for (int c = cap; c >= w[i]; c--) dp[c] = Math.max(dp[c], dp[c - w[i]] + v[i]);
        return dp[cap];
    }

    static int lis(int[] a) {
        int[] tails = new int[a.length];
        int len = 0;
        for (int x : a) {
            int i = Arrays.binarySearch(tails, 0, len, x);
            if (i < 0) i = -(i + 1);
            tails[i] = x;
            if (i == len) len++;
        }
        return len;
    }

    static String lcs(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++)
            for (int j = 1; j <= b.length(); j++)
                dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
        StringBuilder sb = new StringBuilder();
        for (int i = a.length(), j = b.length(); i > 0 && j > 0; ) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) { sb.append(a.charAt(i - 1)); i--; j--; }
            else if (dp[i - 1][j] >= dp[i][j - 1]) i--;
            else j--;
        }
        return sb.reverse().toString();
    }

    static int editDistance(String s, String t) {
        int[][] d = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) d[i][0] = i;
        for (int j = 0; j <= t.length(); j++) d[0][j] = j;
        for (int i = 1; i <= s.length(); i++)
            for (int j = 1; j <= t.length(); j++)
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1),
                        d[i - 1][j - 1] + (s.charAt(i - 1) == t.charAt(j - 1) ? 0 : 1));
        return d[s.length()][t.length()];
    }

    static long coinWays(int[] coins, int amount) {
        long[] ways = new long[amount + 1];
        ways[0] = 1;
        for (int c : coins)
            for (int a = c; a <= amount; a++) ways[a] += ways[a - c];
        return ways[amount];
    }

    static Map<Integer, Long> memo = new HashMap<>();
    static long stairs(int n) {
        if (n <= 1) return 1;
        if (memo.containsKey(n)) return memo.get(n);
        long r = stairs(n - 1) + stairs(n - 2) + (n >= 3 ? stairs(n - 3) : 0);
        memo.put(n, r);
        return r;
    }

    public static void main(String[] args) {
        System.out.println(knapsack(new int[]{1, 3, 4, 5}, new int[]{1, 4, 5, 7}, 7));
        System.out.println(lis(new int[]{10, 9, 2, 5, 3, 7, 101, 18, 4, 19}));
        System.out.println(lcs("AGGTAB", "GXTXAYB"));
        System.out.println(editDistance("kitten", "sitting") + " " + editDistance("", "abc"));
        System.out.println(coinWays(new int[]{1, 2, 5, 10, 25, 50}, 300));
        System.out.println(stairs(50));
    }
}
