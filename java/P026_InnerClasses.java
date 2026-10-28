import java.util.*;

public class P026_InnerClasses {
    private int counter = 0;
    private final String label;

    P026_InnerClasses(String label) { this.label = label; }

    class Incrementer {
        final int step;
        Incrementer(int step) { this.step = step; }
        void bump() { counter += step; }
        String owner() { return label + ":" + P026_InnerClasses.this.counter; }
    }

    static class Node {
        int val;
        Node next;
        Node(int v, Node n) { val = v; next = n; }
    }

    interface Greeter { String greet(String who); }

    Greeter make(String prefix) {
        class Local implements Greeter {
            public String greet(String who) { return prefix + ", " + who + " from " + label; }
        }
        return new Local();
    }

    public static void main(String[] args) {
        P026_InnerClasses outer = new P026_InnerClasses("outer");
        P026_InnerClasses.Incrementer a = outer.new Incrementer(2);
        Incrementer b = outer.new Incrementer(5);
        a.bump();
        b.bump();
        a.bump();
        System.out.println(a.owner() + " " + b.owner());
        Node list = new Node(1, new Node(2, new Node(3, null)));
        int sum = 0;
        for (Node n = list; n != null; n = n.next) sum += n.val;
        System.out.println("sum " + sum);
        System.out.println(outer.make("Hi").greet("Ann"));
        Comparator<String> anon = new Comparator<String>() {
            @Override
            public int compare(String x, String y) { return y.compareTo(x); }
        };
        List<String> names = new ArrayList<>(List.of("bo", "al", "cy"));
        names.sort(anon);
        System.out.println(names);
        Runnable r = new Runnable() {
            int calls = 0;
            public void run() { calls++; System.out.println("anon run " + calls); }
        };
        r.run();
        r.run();
    }
}
