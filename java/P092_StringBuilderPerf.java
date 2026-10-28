import java.util.*;

public class P092_StringBuilderPerf {
    static String lookAndSay(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ) {
            int j = i;
            while (j < s.length() && s.charAt(j) == s.charAt(i)) j++;
            sb.append(j - i).append(s.charAt(i));
            i = j;
        }
        return sb.toString();
    }

    static String bigAdd(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int s = carry + (i >= 0 ? a.charAt(i--) - '0' : 0) + (j >= 0 ? b.charAt(j--) - '0' : 0);
            sb.append((char) ('0' + s % 10));
            carry = s / 10;
        }
        return sb.reverse().toString();
    }

    static String bigMul(String a, String b) {
        int[] r = new int[a.length() + b.length()];
        for (int i = a.length() - 1; i >= 0; i--)
            for (int j = b.length() - 1; j >= 0; j--) {
                int p = (a.charAt(i) - '0') * (b.charAt(j) - '0') + r[i + j + 1];
                r[i + j + 1] = p % 10;
                r[i + j] += p / 10;
            }
        StringBuilder sb = new StringBuilder();
        for (int d : r) if (!(sb.length() == 0 && d == 0)) sb.append(d);
        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        String s = "1";
        for (int i = 0; i < 12; i++) s = lookAndSay(s);
        System.out.println(s.length() + " " + s.substring(0, 20));
        String fa = "0", fb = "1";
        for (int i = 0; i < 200; i++) { String t = bigAdd(fa, fb); fa = fb; fb = t; }
        System.out.println("fib(200) = " + fa);
        String f = "1";
        for (int i = 2; i <= 50; i++) f = bigMul(f, String.valueOf(i));
        System.out.println("50! = " + f);
        StringBuilder big = new StringBuilder();
        for (int i = 0; i < 100000; i++) big.append((char) ('a' + i % 26));
        int count = 0;
        for (int i = 0; i < big.length(); i++) if (big.charAt(i) == 'z') count++;
        System.out.println(big.length() + " " + count + " " + big.substring(99990));
        List<String> parts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) parts.add(Integer.toString(i, 36));
        String joined = String.join("", parts);
        System.out.println(joined.length() + " " + joined.hashCode());
    }
}
