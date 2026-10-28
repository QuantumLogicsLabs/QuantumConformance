import java.util.*;
import java.util.stream.*;

public class P097_GradeBook {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int students = in.nextInt(), tests = in.nextInt();
        String[] names = new String[students];
        int[][] scores = new int[students][tests];
        for (int i = 0; i < students; i++) {
            names[i] = in.next();
            for (int j = 0; j < tests; j++) scores[i][j] = in.nextInt();
        }
        double[] avg = new double[students];
        for (int i = 0; i < students; i++) avg[i] = Arrays.stream(scores[i]).average().orElse(0);
        Integer[] order = new Integer[students];
        for (int i = 0; i < students; i++) order[i] = i;
        Arrays.sort(order, (x, y) -> Double.compare(avg[y], avg[x]));
        System.out.printf("%-6s %s %6s %s%n", "name", IntStream.rangeClosed(1, tests).mapToObj(t -> String.format("T%-3d", t)).collect(Collectors.joining()), "avg", "grade");
        for (int i : order) {
            StringBuilder row = new StringBuilder();
            for (int s : scores[i]) row.append(String.format("%-4d", s));
            char g = avg[i] >= 90 ? 'A' : avg[i] >= 80 ? 'B' : avg[i] >= 70 ? 'C' : avg[i] >= 60 ? 'D' : 'F';
            System.out.printf("%-6s %s %6.2f %c%n", names[i], row, avg[i], g);
        }
        for (int j = 0; j < tests; j++) {
            final int col = j;
            IntSummaryStatistics st = Arrays.stream(scores).mapToInt(r -> r[col]).summaryStatistics();
            System.out.printf("T%d: min %d max %d mean %.1f%n", j + 1, st.getMin(), st.getMax(), st.getAverage());
        }
        String top = names[order[0]];
        System.out.println("top student: " + top + (in.hasNext() ? " (note: " + in.nextLine().trim() + ")" : ""));
    }
}
