import java.util.*;
import java.util.function.*;

public class P019_Lambdas {
    interface MathOp { int apply(int a, int b); }

    static int operate(int a, int b, MathOp op) { return op.apply(a, b); }

    public static void main(String[] args) {
        MathOp add = (a, b) -> a + b;
        MathOp mul = (a, b) -> a * b;
        System.out.println(operate(6, 7, add) + " " + operate(6, 7, mul));
        Function<Integer, Integer> sq = x -> x * x;
        Function<Integer, Integer> inc = x -> x + 1;
        System.out.println(sq.andThen(inc).apply(5) + " " + sq.compose(inc).apply(5));
        BiFunction<String, Integer, String> rep = (s, n) -> s.repeat(n);
        System.out.println(rep.apply("ab", 3));
        Supplier<List<String>> sup = ArrayList::new;
        List<String> l = sup.get();
        l.add("x");
        System.out.println(l);
        Consumer<String> printer = s -> System.out.println(">" + s);
        printer.accept("hello");
        Predicate<Integer> even = n -> n % 2 == 0;
        Predicate<Integer> pos = n -> n > 0;
        System.out.println(even.test(4) + " " + even.and(pos).test(-2) + " " + even.or(pos).test(-2) + " " + even.negate().test(3));
        UnaryOperator<String> up = String::toUpperCase;
        BinaryOperator<Integer> mx = Math::max;
        System.out.println(up.apply("quiet") + " " + mx.apply(3, 9));
        Runnable r = () -> System.out.println("run!");
        r.run();
        int base = 10;
        IntBinaryOperator addBase = (x, y) -> x + y + base;
        System.out.println(addBase.applyAsInt(1, 2));
        Comparator<String> byLen = Comparator.comparingInt(String::length);
        List<String> words = new ArrayList<>(List.of("ccc", "a", "bb", "dddd"));
        words.sort(byLen.reversed());
        System.out.println(words);
        Function<Integer, Function<Integer, Integer>> adder = x -> y -> x + y;
        System.out.println(adder.apply(3).apply(4));
    }
}
