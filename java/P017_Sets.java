import java.util.*;

public class P017_Sets {
    public static void main(String[] args) {
        Set<Integer> hs = new HashSet<>();
        for (int x : new int[]{5, 3, 5, 1, 3, 9}) hs.add(x);
        System.out.println(hs + " " + hs.size());
        System.out.println(hs.contains(9) + " " + hs.add(9) + " " + hs.add(10));
        TreeSet<String> ts = new TreeSet<>(Arrays.asList("pear", "apple", "fig", "kiwi"));
        System.out.println(ts);
        System.out.println(ts.first() + " " + ts.last());
        System.out.println(ts.ceiling("b") + " " + ts.floor("j") + " " + ts.higher("kiwi"));
        System.out.println(ts.headSet("kiwi") + " " + ts.tailSet("kiwi"));
        System.out.println(ts.descendingSet());
        Set<String> a = new TreeSet<>(List.of("a", "b", "c", "d"));
        Set<String> b = new TreeSet<>(List.of("c", "d", "e"));
        Set<String> union = new TreeSet<>(a);
        union.addAll(b);
        Set<String> inter = new TreeSet<>(a);
        inter.retainAll(b);
        Set<String> diff = new TreeSet<>(a);
        diff.removeAll(b);
        System.out.println(union + " " + inter + " " + diff);
        Set<Character> seen = new LinkedHashSet<>();
        for (char c : "programming".toCharArray()) seen.add(c);
        System.out.println(seen);
        TreeSet<Integer> nums = new TreeSet<>(Comparator.reverseOrder());
        nums.addAll(List.of(4, 8, 1, 6));
        System.out.println(nums + " " + nums.pollFirst() + " " + nums);
    }
}
