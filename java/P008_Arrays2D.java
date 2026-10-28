public class P008_Arrays2D {
    public static void main(String[] args) {
        int[][] m = new int[3][4];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                m[i][j] = i * j;
        for (int[] row : m) {
            StringBuilder sb = new StringBuilder();
            for (int v : row) sb.append(v).append(' ');
            System.out.println(sb.toString().trim());
        }
        int[][] jag = {{1}, {2, 3}, {4, 5, 6}};
        int s = 0;
        for (int i = 0; i < jag.length; i++) {
            for (int j = 0; j < jag[i].length; j++) s += jag[i][j];
        }
        System.out.println("sum " + s);
        int[][] tri = new int[4][];
        for (int i = 0; i < 4; i++) {
            tri[i] = new int[i + 1];
            tri[i][0] = tri[i][i] = 1;
            for (int j = 1; j < i; j++) tri[i][j] = tri[i - 1][j - 1] + tri[i - 1][j];
        }
        System.out.println(java.util.Arrays.deepToString(tri));
        char[][] grid = new char[2][3];
        for (char[] r : grid) java.util.Arrays.fill(r, '.');
        grid[1][2] = '#';
        for (char[] r : grid) System.out.println(new String(r));
    }
}
