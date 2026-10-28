import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class P100_Capstone {
    enum Status { TODO, DOING, DONE }

    interface Identified { int id(); }

    static abstract class Item implements Identified, Comparable<Item> {
        private static int nextId = 1;
        private final int id = nextId++;
        public int id() { return id; }
        abstract int weight();
        public int compareTo(Item o) { return Integer.compare(o.weight(), weight()); }
    }

    static class Task extends Item {
        final String title;
        Status status = Status.TODO;
        final int priority;
        final Set<String> tags;
        Task(String title, int priority, String... tags) {
            this.title = title;
            this.priority = priority;
            this.tags = new TreeSet<>(Arrays.asList(tags));
        }
        int weight() { return priority * 10 + (status == Status.DOING ? 5 : 0); }
        @Override public String toString() { return "#" + id() + " " + title + " [" + status + ", p" + priority + "]"; }
    }

    static class Board {
        private final List<Task> tasks = new ArrayList<>();
        private final Deque<String> history = new ArrayDeque<>();

        Task add(String title, int p, String... tags) {
            Task t = new Task(title, p, tags);
            tasks.add(t);
            history.push("add " + t.id());
            return t;
        }

        void move(int id, Status s) {
            Task t = tasks.stream().filter(x -> x.id() == id).findFirst()
                    .orElseThrow(() -> new NoSuchElementException("task " + id));
            if (t.status == Status.DONE) throw new IllegalStateException("task " + id + " is already done");
            t.status = s;
            history.push("move " + id + " " + s);
        }

        Map<Status, List<String>> columns() {
            Map<Status, List<String>> m = new EnumMap<>(Status.class);
            for (Status s : Status.values()) m.put(s, new ArrayList<>());
            for (Task t : tasks) m.get(t.status).add(t.title);
            return m;
        }
    }

    public static void main(String[] args) {
        Board b = new Board();
        b.add("write parser", 3, "compiler", "java");
        b.add("fix lexer bug", 5, "compiler", "bug");
        b.add("update docs", 1, "docs");
        b.add("add tests", 4, "java", "tests");
        b.add("release", 2);
        BiConsumer<Integer, Status> safeMove = (id, s) -> {
            try {
                b.move(id, s);
            } catch (RuntimeException e) {
                System.out.println("! " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        };
        safeMove.accept(2, Status.DOING);
        safeMove.accept(2, Status.DONE);
        safeMove.accept(2, Status.TODO);
        safeMove.accept(4, Status.DOING);
        safeMove.accept(9, Status.DONE);
        System.out.println(b.columns());
        List<Task> sorted = new ArrayList<>(b.tasks);
        Collections.sort(sorted);
        sorted.forEach(System.out::println);
        Map<String, Long> tagCounts = b.tasks.stream().flatMap(t -> t.tags.stream())
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
        System.out.println(tagCounts);
        System.out.println(b.tasks.stream().filter(t -> t.status != Status.DONE).mapToInt(t -> t.priority).sum());
        StringBuilder h = new StringBuilder();
        Iterator<String> it = b.history.descendingIterator();
        while (it.hasNext()) h.append(it.next()).append(it.hasNext() ? "; " : "");
        System.out.println(h);
        Optional<Task> urgent = b.tasks.stream().filter(t -> t.status == Status.TODO).max(Comparator.comparingInt(t -> t.priority));
        System.out.println("next up: " + urgent.map(t -> t.title).orElse("nothing"));
    }
}
