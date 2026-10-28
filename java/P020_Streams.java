import java.util.*;
import java.util.stream.*;

public class P020_Streams {
    public static void main(String[] args) {
        List<Integer> nums = List.of(5, 2, 8, 1, 9, 3, 8);
        System.out.println(nums.stream().filter(n -> n > 3).map(n -> n * 10).collect(Collectors.toList()));
        System.out.println(nums.stream().mapToInt(Integer::intValue).sum());
        System.out.println(nums.stream().distinct().sorted().collect(Collectors.toList()));
        System.out.println(nums.stream().reduce(0, Integer::sum));
        System.out.println(nums.stream().max(Integer::compare).get());
        System.out.println(nums.stream().count());
        System.out.println(IntStream.rangeClosed(1, 5).map(i -> i * i).boxed().collect(Collectors.toList()));
        System.out.println(IntStream.range(0, 5).mapToObj(Integer::toString).collect(Collectors.joining(",", "[", "]")));
        List<String> words = Arrays.asList("apple", "banana", "avocado", "blueberry", "cherry");
        Map<Character, List<String>> byFirst = words.stream()
                .collect(Collectors.groupingBy(w -> w.charAt(0), TreeMap::new, Collectors.toList()));
        System.out.println(byFirst);
        System.out.println(words.stream().anyMatch(w -> w.startsWith("c")) + " " + words.stream().allMatch(w -> w.length() > 4));
        System.out.println(words.stream().map(String::length).collect(Collectors.toList()));
        System.out.println(words.stream().sorted(Comparator.reverseOrder()).limit(2).collect(Collectors.toList()));
        OptionalDouble avg = nums.stream().mapToInt(i -> i).average();
        System.out.printf("%.3f%n", avg.getAsDouble());
        System.out.println(Stream.of("x", "y", "z").skip(1).findFirst().orElse("none"));
        System.out.println(words.stream().filter(w -> w.length() > 20).findAny().isPresent());
        Map<Boolean, Long> parts = nums.stream().collect(Collectors.partitioningBy(n -> n % 2 == 0, Collectors.counting()));
        System.out.println(parts);
        int[] arr = {3, 1, 2};
        System.out.println(Arrays.stream(arr).map(x -> x * 2).sum());
        System.out.println(Stream.iterate(1, x -> x * 3).limit(5).collect(Collectors.toList()));
    }
}
