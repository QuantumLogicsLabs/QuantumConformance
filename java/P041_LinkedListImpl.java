import java.util.Iterator;

public class P041_LinkedListImpl {
    static class MyList<T> implements Iterable<T> {
        private class Node {
            T val;
            Node next;
            Node(T v) { val = v; }
        }
        private Node head, tail;
        private int size;

        void addLast(T v) {
            Node n = new Node(v);
            if (head == null) head = tail = n;
            else { tail.next = n; tail = n; }
            size++;
        }

        void addFirst(T v) {
            Node n = new Node(v);
            n.next = head;
            head = n;
            if (tail == null) tail = n;
            size++;
        }

        T removeFirst() {
            if (head == null) throw new IllegalStateException("empty");
            T v = head.val;
            head = head.next;
            if (head == null) tail = null;
            size--;
            return v;
        }

        void reverse() {
            Node prev = null, cur = head;
            tail = head;
            while (cur != null) {
                Node nx = cur.next;
                cur.next = prev;
                prev = cur;
                cur = nx;
            }
            head = prev;
        }

        T middle() {
            Node slow = head, fast = head;
            while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
            return slow == null ? null : slow.val;
        }

        public Iterator<T> iterator() {
            return new Iterator<T>() {
                Node cur = head;
                public boolean hasNext() { return cur != null; }
                public T next() { T v = cur.val; cur = cur.next; return v; }
            };
        }

        @Override public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (T t : this) {
                if (sb.length() > 1) sb.append(" -> ");
                sb.append(t);
            }
            return sb.append("]").toString();
        }
    }

    public static void main(String[] args) {
        MyList<Integer> l = new MyList<>();
        for (int i = 1; i <= 5; i++) l.addLast(i * i);
        l.addFirst(0);
        System.out.println(l + " size " + l.size + " middle " + l.middle());
        l.reverse();
        System.out.println(l);
        System.out.println(l.removeFirst() + " " + l.removeFirst() + " " + l);
        MyList<String> e = new MyList<>();
        try {
            e.removeFirst();
        } catch (IllegalStateException ex) {
            System.out.println("error: " + ex.getMessage() + " " + e + " " + e.middle());
        }
    }
}
