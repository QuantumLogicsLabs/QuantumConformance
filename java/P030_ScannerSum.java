import java.util.Scanner;

public class P030_ScannerSum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long total = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int v = sc.nextInt();
            total += v;
            max = Math.max(max, v);
        }
        sc.nextLine();
        String name = sc.nextLine();
        double d = sc.nextDouble();
        String word = sc.next();
        System.out.println("n=" + n + " total=" + total + " max=" + max);
        System.out.println("name=[" + name + "]");
        System.out.printf("d=%.2f word=%s%n", d, word);
        int lines = 0;
        sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) continue;
            lines++;
            System.out.println(lines + ": " + line.toUpperCase());
        }
    }
}
