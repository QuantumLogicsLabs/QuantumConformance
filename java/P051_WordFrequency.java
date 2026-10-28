import java.util.*;
import java.util.stream.*;

public class P051_WordFrequency {
    public static void main(String[] args) {
        String text = "It was the best of times, it was the worst of times, it was the age of wisdom, "
                + "it was the age of foolishness, it was the epoch of belief, it was the epoch of incredulity";
        Map<String, Integer> freq = new HashMap<>();
        for (String w : text.toLowerCase().split("[^a-z]+")) {
            if (w.isEmpty()) continue;
            freq.merge(w, 1, Integer::sum);
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort((a, b) -> b.getValue() != a.getValue().intValue()
                ? b.getValue() - a.getValue() : a.getKey().compareTo(b.getKey()));
        for (int i = 0; i < 5; i++)
            System.out.printf("%-8s %2d %s%n", entries.get(i).getKey(), entries.get(i).getValue(),
                    "*".repeat(entries.get(i).getValue()));
        System.out.println("distinct: " + freq.size());
        Map<Integer, List<String>> byLen = freq.keySet().stream()
                .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));
        byLen.values().forEach(Collections::sort);
        System.out.println(byLen);
        String longest = freq.keySet().stream().max(Comparator.comparingInt(String::length)
                .thenComparing(Comparator.reverseOrder())).orElse("");
        System.out.println("longest: " + longest);
        int[] letters = new int[26];
        for (char c : text.toCharArray()) if (Character.isLetter(c)) letters[Character.toLowerCase(c) - 'a']++;
        StringBuilder top = new StringBuilder();
        for (int k = 0; k < 3; k++) {
            int bi = 0;
            for (int i = 1; i < 26; i++) if (letters[i] > letters[bi]) bi = i;
            top.append((char) ('a' + bi)).append('=').append(letters[bi]).append(' ');
            letters[bi] = -1;
        }
        System.out.println(top.toString().trim());
    }
}
