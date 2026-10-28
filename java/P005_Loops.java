public class P005_Loops {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1; i <= 100; i++) sum += i;
        System.out.println("sum=" + sum);
        int n = 0;
        while (n < 5) {
            n += 2;
        }
        System.out.println("n=" + n);
        int k = 10;
        do {
            k--;
        } while (k > 10);
        System.out.println("k=" + k);
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 10; i < j; i += 2, j -= 2) {
            sb.append(i).append(':').append(j).append(' ');
        }
        System.out.println(sb.toString().trim());
        outer:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 3) continue outer;
                if (i == 3) break outer;
                System.out.print(i * 10 + j + " ");
            }
        }
        System.out.println();
        int count = 0;
        for (;;) {
            if (++count >= 7) break;
        }
        System.out.println("count=" + count);
    }
}
