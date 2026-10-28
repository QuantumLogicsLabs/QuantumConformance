public class P010_Recursion {
    static long fact(int n) { return n <= 1 ? 1 : n * fact(n - 1); }
    static int fib(int n) { return n < 2 ? n : fib(n - 1) + fib(n - 2); }
    static int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }
    static void hanoi(int n, char from, char to, char via, StringBuilder out) {
        if (n == 0) return;
        hanoi(n - 1, from, via, to, out);
        out.append(from).append("->").append(to).append(' ');
        hanoi(n - 1, via, to, from, out);
    }
    static int ackermann(int m, int n) {
        if (m == 0) return n + 1;
        if (n == 0) return ackermann(m - 1, 1);
        return ackermann(m - 1, ackermann(m, n - 1));
    }
    static String reverse(String s) {
        return s.isEmpty() ? "" : reverse(s.substring(1)) + s.charAt(0);
    }

    public static void main(String[] args) {
        System.out.println(fact(20));
        System.out.println(fib(20));
        System.out.println(gcd(1071, 462));
        StringBuilder sb = new StringBuilder();
        hanoi(3, 'A', 'C', 'B', sb);
        System.out.println(sb.toString().trim());
        System.out.println(ackermann(2, 3));
        System.out.println(reverse("recursion"));
    }
}
