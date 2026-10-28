import java.util.*;

public class P054_ComparableSort {
    static class Student implements Comparable<Student> {
        final String name;
        final int grade;
        final double gpa;
        Student(String name, int grade, double gpa) { this.name = name; this.grade = grade; this.gpa = gpa; }
        public int compareTo(Student o) { return name.compareTo(o.name); }
        public String toString() { return name + "/" + grade + "/" + gpa; }
    }

    public static void main(String[] args) {
        List<Student> s = new ArrayList<>(List.of(
                new Student("Maya", 11, 3.7), new Student("Leo", 12, 3.9), new Student("Ava", 11, 3.9),
                new Student("Zed", 10, 2.8), new Student("Kai", 12, 3.7), new Student("Ivy", 10, 3.95)));
        Collections.sort(s);
        System.out.println(s);
        s.sort(Comparator.comparingDouble((Student x) -> x.gpa).reversed().thenComparing(x -> x.name));
        System.out.println(s);
        s.sort(Comparator.comparingInt((Student x) -> x.grade).thenComparing(Comparator.reverseOrder()));
        System.out.println(s);
        Student[] arr = s.toArray(new Student[0]);
        Arrays.sort(arr, (a, b) -> Double.compare(a.gpa, b.gpa) != 0 ? Double.compare(a.gpa, b.gpa) : b.name.compareTo(a.name));
        System.out.println(Arrays.toString(arr));
        Integer[] nums = {5, -3, 12, 0, -8, 7};
        Arrays.sort(nums, Collections.reverseOrder());
        System.out.println(Arrays.toString(nums));
        Arrays.sort(nums, Comparator.comparingInt(Math::abs));
        System.out.println(Arrays.toString(nums));
        String[] ws = {"banana", "Apple", "cherry", "apple", "Banana"};
        Arrays.sort(ws);
        System.out.println(Arrays.toString(ws));
        Arrays.sort(ws, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.toString(ws));
        List<String> nullable = new ArrayList<>(Arrays.asList("b", null, "a"));
        nullable.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        System.out.println(nullable);
    }
}
