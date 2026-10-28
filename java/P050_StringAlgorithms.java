import java.util.*;

public class P050_StringAlgorithms {
    static boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) i++;
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) j--;
            if (Character.toLowerCase(s.charAt(i++)) != Character.toLowerCase(s.charAt(j--))) return false;
        }
        return true;
    }

    static boolean anagram(String a, String b) {
        int[] cnt = new int[26];
        for (char c : a.toCharArray()) cnt[c - 'a']++;
        for (char c : b.toCharArray()) cnt[c - 'a']--;
        for (int x : cnt) if (x != 0) return false;
        return true;
    }

    static int[] prefixFunction(String s) {
        int[] pi = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            int k = pi[i - 1];
            while (k > 0 && s.charAt(i) != s.charAt(k)) k = pi[k - 1];
            if (s.charAt(i) == s.charAt(k)) k++;
            pi[i] = k;
        }
        return pi;
    }

    static List<Integer> kmp(String text, String pat) {
        int[] pi = prefixFunction(pat + "\u0000" + text);
        List<Integer> out = new ArrayList<>();
        for (int i = pat.length() + 1; i < pi.length; i++)
            if (pi[i] == pat.length()) out.add(i - 2 * pat.length());
        return out;
    }

    static String longestPalSub(String s) {
        int best = 0, start = 0;
        for (int c = 0; c < s.length(); c++)
            for (int w = 0; w < 2; w++) {
                int l = c, r = c + w;
                while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) { l--; r++; }
                if (r - l - 1 > best) { best = r - l - 1; start = l + 1; }
            }
        return s.substring(start, start + best);
    }

    static String compress(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ) {
            int j = i;
            while (j < s.length() && s.charAt(j) == s.charAt(i)) j++;
            sb.append(s.charAt(i));
            if (j - i > 1) sb.append(j - i);
            i = j;
        }
        return sb.toString();
    }

    static String caesar(String s, int k) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) sb.append((char) ('A' + (c - 'A' + k + 26) % 26));
            else if (Character.isLowerCase(c)) sb.append((char) ('a' + (c - 'a' + k + 26) % 26));
            else sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama") + " " + isPalindrome("race a car"));
        System.out.println(anagram("listen", "silent") + " " + anagram("rat", "car"));
        System.out.println(Arrays.toString(prefixFunction("aabaaab")));
        System.out.println(kmp("abababcabab", "abab"));
        System.out.println(longestPalSub("forgeeksskeegfor") + " " + longestPalSub("babad"));
        System.out.println(compress("aaabccddddde"));
        String enc = caesar("Hello, World!", 3);
        System.out.println(enc + " -> " + caesar(enc, -3));
        String words = "  the sky   is blue ";
        List<String> parts = new ArrayList<>(Arrays.asList(words.trim().split("\\s+")));
        Collections.reverse(parts);
        System.out.println(String.join(" ", parts));
        System.out.println("Mississippi".replaceAll("ss", "SS").chars().filter(ch -> ch == 'S').count());
    }
}
