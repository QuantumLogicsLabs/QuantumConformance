import java.util.*;
import java.util.function.*;

public class P083_EventSystem {
    static class EventBus {
        private final Map<String, List<Consumer<String>>> handlers = new LinkedHashMap<>();
        private final List<String> deadLetters = new ArrayList<>();

        Runnable subscribe(String topic, Consumer<String> h) {
            handlers.computeIfAbsent(topic, k -> new ArrayList<>()).add(h);
            return () -> handlers.get(topic).remove(h);
        }

        int publish(String topic, String payload) {
            List<Consumer<String>> hs = handlers.getOrDefault(topic, List.of());
            if (hs.isEmpty()) deadLetters.add(topic + ":" + payload);
            for (Consumer<String> h : new ArrayList<>(hs)) h.accept(payload);
            return hs.size();
        }
    }

    static class Counter implements Consumer<String> {
        int count;
        final StringBuilder seen = new StringBuilder();
        public void accept(String s) { count++; seen.append(s.charAt(0)); }
    }

    public static void main(String[] args) {
        EventBus bus = new EventBus();
        List<String> log = new ArrayList<>();
        Counter counter = new Counter();
        Runnable unsubLog = bus.subscribe("order", p -> log.add("logged " + p));
        bus.subscribe("order", counter);
        bus.subscribe("order", p -> { if (p.contains("big")) log.add("ALERT " + p); });
        bus.subscribe("user", counter.andThen(p -> log.add("user " + p.toUpperCase())));
        System.out.println(bus.publish("order", "apple"));
        System.out.println(bus.publish("order", "big-tv"));
        unsubLog.run();
        System.out.println(bus.publish("order", "cable"));
        System.out.println(bus.publish("user", "dana"));
        System.out.println(bus.publish("payment", "x"));
        log.forEach(System.out::println);
        System.out.println(counter.count + " " + counter.seen + " " + bus.deadLetters);
        BiFunction<Integer, Integer, Integer> add = Integer::sum;
        Function<Integer, String> stars = "*"::repeat;
        System.out.println(add.andThen(stars).apply(2, 3));
        Supplier<Map<String, Integer>> fresh = TreeMap::new;
        Map<String, Integer> tm = fresh.get();
        tm.put("b", 1);
        tm.put("a", 2);
        System.out.println(tm);
    }
}
