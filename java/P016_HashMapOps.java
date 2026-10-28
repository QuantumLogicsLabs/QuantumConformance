import java.util.*;

public class P016_HashMapOps {
    public static void main(String[] args) {
        Map<String, Integer> ages = new HashMap<>();
        ages.put("alice", 30);
        ages.put("bob", 25);
        ages.put("carol", 35);
        ages.put("bob", 26);
        System.out.println(ages.get("bob") + " " + ages.get("zed"));
        System.out.println(ages.getOrDefault("zed", -1));
        System.out.println(ages.containsKey("alice") + " " + ages.containsValue(35));
        System.out.println(ages.size());
        ages.remove("carol");
        ages.putIfAbsent("dave", 40);
        ages.putIfAbsent("alice", 99);
        ages.merge("alice", 1, Integer::sum);
        ages.compute("bob", (k, v) -> v == null ? 0 : v * 2);
        ages.computeIfAbsent("eve", k -> k.length());
        TreeMap<String, Integer> sorted = new TreeMap<>(ages);
        System.out.println(sorted);
        for (Map.Entry<String, Integer> e : sorted.entrySet())
            System.out.println(e.getKey() + "=" + e.getValue());
        Map<Integer, String> byNum = new HashMap<>();
        for (int i = 0; i < 5; i++) byNum.put(i * i, "sq" + i);
        System.out.println(byNum);
        System.out.println(byNum.keySet());
        System.out.println(byNum.values());
        Map<Character, Integer> freq = new TreeMap<>();
        for (char c : "mississippi".toCharArray()) freq.put(c, freq.getOrDefault(c, 0) + 1);
        System.out.println(freq);
        Map<String, List<Integer>> multi = new TreeMap<>();
        multi.computeIfAbsent("even", k -> new ArrayList<>()).add(2);
        multi.computeIfAbsent("odd", k -> new ArrayList<>()).add(1);
        multi.computeIfAbsent("even", k -> new ArrayList<>()).add(4);
        System.out.println(multi);
        Map<String, Integer> linked = new LinkedHashMap<>();
        linked.put("z", 1);
        linked.put("a", 2);
        linked.put("m", 3);
        System.out.println(linked);
        linked.forEach((k, v) -> System.out.print(k + v + " "));
        System.out.println();
    }
}
