import java.util.*;
import java.util.stream.*;

public class P065_IntStreams {
    static boolean isPrime(int n) {
        return n > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(d -> n % d == 0);
    }

    public static void main(String[] args) {
        System.out.println(IntStream.range(0, 50).filter(P065_IntStreams::isPrime).boxed().collect(Collectors.toList()));
        System.out.println(IntStream.of(3, 1, 4, 1, 5, 9, 2, 6).sorted().distinct().mapToObj(String::valueOf).collect(Collectors.joining(" ")));
        System.out.println(IntStream.rangeClosed(1, 10).reduce(1, (a, b) -> a * b));
        System.out.println(LongStream.rangeClosed(1, 20).reduce(1L, (a, b) -> a * b));
        IntSummaryStatistics s = IntStream.of(4, 8, 15, 16, 23, 42).summaryStatistics();
        System.out.println(s.getMin() + " " + s.getMax() + " " + s.getAverage() + " " + s.getSum() + " " + s.getCount());
        System.out.println(IntStream.iterate(1, x -> x * 2).limit(11).sum());
        int[] squares = IntStream.range(1, 6).map(x -> x * x).toArray();
        System.out.println(Arrays.toString(squares));
        System.out.println(Arrays.stream(squares).max().getAsInt() + " " + IntStream.empty().max().isPresent());
        System.out.println(DoubleStream.of(1.5, 2.5, 3.0).map(Math::floor).sum());
        System.out.println("hello".chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining("-")));
        System.out.println(IntStream.rangeClosed(1, 5).mapToObj(i -> "*".repeat(i)).collect(Collectors.joining("\n")));
        List<Integer> evens = Stream.iterate(0, i -> i + 2).limit(6).collect(Collectors.toList());
        System.out.println(evens + " " + evens.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println(IntStream.range(0, 5).mapToObj(i -> IntStream.rangeClosed(0, i).mapToObj(Integer::toString).collect(Collectors.joining(","))).collect(Collectors.toList()));
        System.out.println(Stream.of(5, 3, 8).sorted(Collections.reverseOrder()).map(String::valueOf).reduce("", String::concat));
        System.out.println(IntStream.range(0, 20).skip(15).peek(x -> {}).count());
    }
}
