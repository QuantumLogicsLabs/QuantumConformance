import java.util.*;

public class P018_Deques {
    public static void main(String[] args) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack + " peek=" + stack.peek());
        System.out.println(stack.pop() + " " + stack);
        Queue<String> q = new LinkedList<>();
        q.offer("a");
        q.offer("b");
        q.add("c");
        System.out.println(q.peek() + " " + q.poll() + " " + q);
        Deque<Integer> dq = new ArrayDeque<>();
        dq.offerFirst(2);
        dq.offerLast(3);
        dq.addFirst(1);
        dq.addLast(4);
        System.out.println(dq + " " + dq.peekFirst() + " " + dq.peekLast());
        System.out.println(dq.pollFirst() + " " + dq.pollLast() + " " + dq);
        Stack<Character> st = new Stack<>();
        for (char c : "abc".toCharArray()) st.push(c);
        StringBuilder sb = new StringBuilder();
        while (!st.isEmpty()) sb.append(st.pop());
        System.out.println(sb);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int x : new int[]{7, 2, 9, 4, 1}) pq.add(x);
        StringBuilder out = new StringBuilder();
        while (!pq.isEmpty()) out.append(pq.poll()).append(' ');
        System.out.println(out.toString().trim());
        PriorityQueue<String> maxq = new PriorityQueue<>((x, y) -> y.length() - x.length());
        maxq.addAll(List.of("aa", "a", "aaaa", "aaa"));
        System.out.println(maxq.poll() + " " + maxq.size());
        LinkedList<Integer> ll = new LinkedList<>(List.of(1, 2, 3));
        ll.addFirst(0);
        ll.removeLast();
        System.out.println(ll + " " + ll.getFirst() + " " + ll.getLast());
        System.out.println(q.poll() + " " + q.poll() + " " + q.poll());
    }
}
