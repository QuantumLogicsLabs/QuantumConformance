import java.util.*;

public class P070_Interfaces2 {
    interface Visitor<R> {
        R visitCircle(Circle c);
        R visitSquare(Square s);
    }

    interface Shape {
        <R> R accept(Visitor<R> v);
    }

    static class Circle implements Shape {
        final double r;
        Circle(double r) { this.r = r; }
        public <R> R accept(Visitor<R> v) { return v.visitCircle(this); }
    }

    static class Square implements Shape {
        final double side;
        Square(double s) { side = s; }
        public <R> R accept(Visitor<R> v) { return v.visitSquare(this); }
    }

    interface Walker { default String move() { return "walk"; } }
    interface Swimmer { default String move() { return "swim"; } }

    static class Duck implements Walker, Swimmer {
        public String move() { return Walker.super.move() + "+" + Swimmer.super.move(); }
    }

    interface Validator<T> {
        boolean validate(T t);
        default Validator<T> and(Validator<T> o) { return t -> validate(t) && o.validate(t); }
        static <T> Validator<T> not(Validator<T> v) { return t -> !v.validate(t); }
    }

    public static void main(String[] args) {
        List<Shape> shapes = List.of(new Circle(1), new Square(2), new Circle(0.5));
        Visitor<Double> area = new Visitor<Double>() {
            public Double visitCircle(Circle c) { return Math.PI * c.r * c.r; }
            public Double visitSquare(Square s) { return s.side * s.side; }
        };
        Visitor<String> name = new Visitor<String>() {
            public String visitCircle(Circle c) { return "circle(" + c.r + ")"; }
            public String visitSquare(Square s) { return "square(" + s.side + ")"; }
        };
        for (Shape s : shapes) System.out.printf("%s -> %.3f%n", s.accept(name), s.accept(area));
        System.out.println(new Duck().move());
        Validator<String> nonEmpty = s -> !s.isEmpty();
        Validator<String> shortStr = s -> s.length() < 5;
        Validator<String> both = nonEmpty.and(shortStr);
        Validator<String> longStr = Validator.not(shortStr);
        for (String s : new String[]{"", "abc", "abcdef"})
            System.out.println("'" + s + "' " + both.validate(s) + " " + longStr.validate(s));
    }
}
