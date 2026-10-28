import java.util.*;

public class P015_ArrayListOps {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("delta");
        list.add("alpha");
        list.add("charlie");
        list.add(1, "bravo");
        System.out.println(list + " size=" + list.size());
        System.out.println(list.get(2));
        list.set(0, "DELTA");
        System.out.println(list.indexOf("charlie") + " " + list.contains("echo"));
        list.remove("alpha");
        list.remove(0);
        System.out.println(list);
        list.addAll(Arrays.asList("x", "y", "z"));
        Collections.sort(list);
        System.out.println(list);
        Collections.reverse(list);
        System.out.println(list);
        list.sort(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()));
        System.out.println(list);
        List<Integer> nums = new ArrayList<>();
        for (int i = 10; i > 0; i--) nums.add(i * 3 % 7);
        System.out.println(nums);
        nums.remove(Integer.valueOf(6));
        nums.remove(0);
        System.out.println(nums + " max=" + Collections.max(nums) + " min=" + Collections.min(nums));
        nums.removeIf(n -> n % 2 == 0);
        System.out.println(nums);
        System.out.println(nums.isEmpty() + " " + new ArrayList<>().isEmpty());
        List<Integer> sub = new ArrayList<>(List.of(1, 2, 3, 4, 5)).subList(1, 4);
        System.out.println(sub);
        Iterator<Integer> it = nums.iterator();
        while (it.hasNext()) if (it.next() == 3) it.remove();
        System.out.println(nums);
        nums.clear();
        System.out.println(nums.size());
    }
}
