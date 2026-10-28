import java.util.*;
import java.util.function.*;

public class P062_GenericStack {
    static class ArrayStack<T> implements Iterable<T> {
        private T[] items;
        private int n;

        @SuppressWarnings("unchecked")
        ArrayStack() { items = (T[]) new Object[1]; }

        void push(T t) {
            if (n == items.length) items = Arrays.copyOf(items, 2 * n);
            items[n++] = t;
        }

        T pop() {
            if (n == 0) throw new EmptyStackException();
            T t = items[--n];
            items[n] = null;
            return t;
        }

        T peek() { return n == 0 ? null : items[n - 1]; }
        boolean isEmpty() { return n == 0; }

        <R> ArrayStack<R> map(Function<? super T, ? extends R> f) {
            ArrayStack<R> out = new ArrayStack<>();
            for (int i = 0; i < n; i++) out.push(f.apply(items[i]));
            return out;
        }

        public Iterator<T> iterator() {
            return new Iterator<T>() {
                int i = n;
                public boolean hasNext() { return i > 0; }
                public T next() { return items[--i]; }
            };
        }
    }

    static int evalRPN(String expr) {
        ArrayStack<Integer> st = new ArrayStack<>();
        for (String tok : expr.split(" ")) {
            switch (tok) {
                case "+": st.push(st.pop() + st.pop()); break;
                case "*": st.push(st.pop() * st.pop()); break;
                case "-": { int b = st.pop(), a = st.pop(); st.push(a - b); break; }
                case "/": { int b = st.pop(), a = st.pop(); st.push(a / b); break; }
                default: st.push(Integer.parseInt(tok));
            }
        }
        return st.pop();
    }

    static boolean balanced(String s) {
        ArrayStack<Character> st = new ArrayStack<>();
        String open = "([{", close = ")]}";
        for (char c : s.toCharArray()) {
            if (open.indexOf(c) >= 0) st.push(c);
            else if (close.indexOf(c) >= 0) {
                if (st.isEmpty() || open.indexOf(st.pop()) != close.indexOf(c)) return false;
            }
        }
        return st.isEmpty();
    }

    public static void main(String[] args) {
        ArrayStack<String> s = new ArrayStack<>();
        for (String w : "to be or not to be".split(" ")) s.push(w);
        StringBuilder sb = new StringBuilder();
        for (String w : s) sb.append(w).append(' ');
        System.out.println(sb.toString().trim());
        System.out.println(s.map(String::length).pop() + " " + s.peek());
        System.out.println(evalRPN("3 4 + 2 * 7 -") + " " + evalRPN("100 7 / 3 -"));
        for (String t : new String[]{"([]{()})", "([)]", "((", ""}) System.out.print(balanced(t) + " ");
        System.out.println();
        try {
            new ArrayStack<Integer>().pop();
        } catch (EmptyStackException e) {
            System.out.println("empty: " + e.getMessage());
        }
    }
}
