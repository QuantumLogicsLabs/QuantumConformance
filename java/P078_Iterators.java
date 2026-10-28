import java.util.*;

public class P078_Iterators {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) nums.add(i);
        ListIterator<Integer> it = nums.listIterator();
        while (it.hasNext()) {
            int v = it.next();
            if (v % 3 == 0) it.remove();
            else if (v % 4 == 0) it.set(v * 100);
            else if (v == 5) it.add(55);
        }
        System.out.println(nums);
        ListIterator<Integer> back = nums.listIterator(nums.size());
        StringBuilder sb = new StringBuilder();
        while (back.hasPrevious()) sb.append(back.previous()).append(back.hasPrevious() ? "," : "");
        System.out.println(sb);
        try {
            for (Integer n : nums) if (n == 2) nums.remove(n);
        } catch (ConcurrentModificationException e) {
            System.out.println("CME");
        }
        System.out.println(nums);
        Map<String, Integer> m = new TreeMap<>(Map.of("a", 1, "b", 2, "c", 3, "d", 4));
        Iterator<Map.Entry<String, Integer>> mi = m.entrySet().iterator();
        while (mi.hasNext()) {
            Map.Entry<String, Integer> e = mi.next();
            if (e.getValue() % 2 == 0) mi.remove();
            else e.setValue(e.getValue() * 10);
        }
        System.out.println(m);
        m.keySet().removeIf(k -> k.equals("a"));
        System.out.println(m);
        try {
            m.values().add(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("values() is a view without add");
        }
        Iterator<String> words = List.of("x", "y").iterator();
        words.next();
        words.next();
        try {
            words.next();
        } catch (NoSuchElementException e) {
            System.out.println("no more words");
        }
    }
}
