public class P075_Ternary {
    static String fizz(int i) { return i % 15 == 0 ? "FizzBuzz" : i % 3 == 0 ? "Fizz" : i % 5 == 0 ? "Buzz" : String.valueOf(i); }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 20; i++) sb.append(fizz(i)).append(i < 20 ? "," : "\n");
        System.out.print(sb);
        int a = 5, b = 9;
        int max = a > b ? a : b;
        System.out.println(max + " " + (a < b ? "less" : "not less"));
        Object o = true ? 1 : "one";
        System.out.println(o + " " + o.getClass().getSimpleName());
        Object widened = true ? 1 : 2.0;
        System.out.println(widened);
        Integer n = null;
        System.out.println(n == null ? "none" : n.toString());
        char c = false ? 'x' : 'y';
        System.out.println(c);
        System.out.println(true ? 'a' : 0);
        int i = 0;
        boolean left = (i++ > 0) && (i++ > 0);
        boolean right = (i++ >= 0) || (i++ > 0);
        System.out.println(left + " " + right + " " + i);
        boolean nonShort = (i++ > 100) & (i++ > 100);
        System.out.println(nonShort + " " + i);
        int x = 10;
        x += x++ + ++x;
        System.out.println(x);
        int y = 5;
        y = y-- - --y;
        System.out.println(y);
        int[] arr = {0, 0, 0};
        int k = 0;
        arr[k++] = k++;
        System.out.println(arr[0] + " " + arr[1] + " " + k);
    }
}
