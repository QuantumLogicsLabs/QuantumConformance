import java.util.*;

public class P080_Random {
    public static void main(String[] args) {
        Random r = new Random(42);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) sb.append(r.nextInt(100)).append(' ');
        System.out.println(sb.toString().trim());
        System.out.println(r.nextInt() + " " + r.nextLong() + " " + r.nextBoolean());
        System.out.printf("%.6f %.6f%n", r.nextDouble(), r.nextGaussian());
        Random a = new Random(7), b = new Random(7);
        boolean same = true;
        for (int i = 0; i < 100; i++) same &= a.nextInt(1000) == b.nextInt(1000);
        System.out.println("same sequence: " + same);
        List<Integer> deck = new ArrayList<>();
        for (int i = 1; i <= 10; i++) deck.add(i);
        Collections.shuffle(deck, new Random(123));
        System.out.println(deck);
        int[] hist = new int[6];
        Random dice = new Random(2024);
        for (int i = 0; i < 6000; i++) hist[dice.nextInt(6)]++;
        System.out.println(Arrays.toString(hist));
        double inside = 0;
        Random mc = new Random(1);
        int n = 200000;
        for (int i = 0; i < n; i++) {
            double x = mc.nextDouble(), y = mc.nextDouble();
            if (x * x + y * y <= 1) inside++;
        }
        System.out.printf("pi ~ %.4f%n", 4 * inside / n);
        System.out.println(new Random(99).ints(5, 0, 10).boxed().collect(java.util.stream.Collectors.toList()));
    }
}
