import java.util.*;

public class P039_TopoSort {
    static List<String> kahn(Map<String, List<String>> deps) {
        Map<String, Integer> indeg = new TreeMap<>();
        for (String k : deps.keySet()) indeg.putIfAbsent(k, 0);
        for (List<String> vs : deps.values())
            for (String v : vs) indeg.merge(v, 1, Integer::sum);
        PriorityQueue<String> ready = new PriorityQueue<>();
        for (Map.Entry<String, Integer> e : indeg.entrySet()) if (e.getValue() == 0) ready.add(e.getKey());
        List<String> order = new ArrayList<>();
        while (!ready.isEmpty()) {
            String u = ready.poll();
            order.add(u);
            for (String v : deps.getOrDefault(u, Collections.emptyList()))
                if (indeg.merge(v, -1, Integer::sum) == 0) ready.add(v);
        }
        if (order.size() != indeg.size()) throw new IllegalStateException("cycle detected");
        return order;
    }

    static boolean dfsCycle(int u, List<List<Integer>> g, int[] state) {
        state[u] = 1;
        for (int v : g.get(u)) {
            if (state[v] == 1) return true;
            if (state[v] == 0 && dfsCycle(v, g, state)) return true;
        }
        state[u] = 2;
        return false;
    }

    public static void main(String[] args) {
        Map<String, List<String>> deps = new HashMap<>();
        deps.put("shirt", List.of("tie", "belt"));
        deps.put("tie", List.of("jacket"));
        deps.put("pants", List.of("shoes", "belt"));
        deps.put("belt", List.of("jacket"));
        deps.put("socks", List.of("shoes"));
        deps.put("undershorts", List.of("pants", "shoes"));
        deps.put("watch", List.of());
        System.out.println(kahn(deps));
        deps.put("jacket", List.of("shirt"));
        try {
            kahn(deps);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < 4; i++) g.add(new ArrayList<>());
        g.get(0).add(1);
        g.get(1).add(2);
        g.get(2).add(3);
        System.out.println(dfsCycle(0, g, new int[4]));
        g.get(3).add(1);
        System.out.println(dfsCycle(0, g, new int[4]));
    }
}
