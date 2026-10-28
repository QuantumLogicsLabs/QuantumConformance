import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class P064_Collectors {
    static class Emp {
        final String name, dept;
        final int age;
        final double salary;
        Emp(String n, String d, int a, double s) { name = n; dept = d; age = a; salary = s; }
        String getDept() { return dept; }
        double getSalary() { return salary; }
    }

    public static void main(String[] args) {
        List<Emp> es = List.of(
                new Emp("Ann", "eng", 34, 120_000), new Emp("Bob", "eng", 28, 95_000),
                new Emp("Cid", "ops", 45, 80_000), new Emp("Dee", "sales", 39, 70_000),
                new Emp("Eve", "ops", 23, 60_000), new Emp("Fay", "eng", 51, 150_000));
        Map<String, Double> avg = es.stream().collect(Collectors.groupingBy(Emp::getDept, TreeMap::new, Collectors.averagingDouble(Emp::getSalary)));
        System.out.println(avg);
        Map<String, List<String>> names = es.stream().collect(Collectors.groupingBy(Emp::getDept, TreeMap::new,
                Collectors.mapping(e -> e.name, Collectors.toList())));
        System.out.println(names);
        Map<String, Optional<Emp>> oldest = es.stream().collect(Collectors.groupingBy(Emp::getDept, TreeMap::new,
                Collectors.maxBy(Comparator.comparingInt(e -> e.age))));
        oldest.forEach((d, e) -> System.out.print(d + ":" + e.get().name + " "));
        System.out.println();
        DoubleSummaryStatistics st = es.stream().mapToDouble(Emp::getSalary).summaryStatistics();
        System.out.printf("n=%d min=%.0f max=%.0f avg=%.2f sum=%.0f%n", st.getCount(), st.getMin(), st.getMax(), st.getAverage(), st.getSum());
        Map<String, Emp> byName = es.stream().collect(Collectors.toMap(e -> e.name, Function.identity()));
        System.out.println(byName.get("Dee").dept);
        Map<Boolean, List<String>> young = es.stream().collect(Collectors.partitioningBy(e -> e.age < 35,
                Collectors.mapping(e -> e.name, Collectors.toList())));
        System.out.println(young);
        System.out.println(es.stream().map(e -> e.dept).distinct().sorted().collect(Collectors.joining("|")));
        Set<String> depts = es.stream().map(Emp::getDept).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(depts);
        System.out.println(es.stream().collect(Collectors.summingInt(e -> e.age)));
        Map<String, Double> total = es.stream().collect(Collectors.toMap(Emp::getDept, Emp::getSalary, Double::sum, TreeMap::new));
        System.out.println(total);
        List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3), List.of(), List.of(4, 5, 6));
        System.out.println(nested.stream().flatMap(List::stream).map(x -> x * x).collect(Collectors.toList()));
        System.out.println(IntStream.rangeClosed(1, 20).filter(x -> x % 3 == 0).boxed().collect(Collectors.toList()));
        System.out.println(Stream.of("a", "bb", "cc", "ddd").collect(Collectors.groupingBy(String::length, Collectors.counting())));
    }
}
