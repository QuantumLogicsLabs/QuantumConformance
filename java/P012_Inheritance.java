public class P012_Inheritance {
    static abstract class Shape {
        protected String name;
        Shape(String name) { this.name = name; }
        abstract double area();
        double perimeter() { return 0; }
        public String describe() {
            return String.format("%s area=%.2f perimeter=%.2f", name, area(), perimeter());
        }
    }

    static class Circle extends Shape {
        double r;
        Circle(double r) { super("Circle"); this.r = r; }
        double area() { return Math.PI * r * r; }
        @Override double perimeter() { return 2 * Math.PI * r; }
    }

    static class Rect extends Shape {
        double w, h;
        Rect(double w, double h) { this("Rect", w, h); }
        protected Rect(String n, double w, double h) { super(n); this.w = w; this.h = h; }
        double area() { return w * h; }
        @Override double perimeter() { return 2 * (w + h); }
    }

    static class Square extends Rect {
        Square(double s) { super("Square", s, s); }
        @Override public String describe() { return "[" + super.describe() + "]"; }
    }

    public static void main(String[] args) {
        Shape[] shapes = {new Circle(1), new Rect(2, 3), new Square(4)};
        double total = 0;
        for (Shape s : shapes) {
            System.out.println(s.describe());
            total += s.area();
        }
        System.out.printf("total %.3f%n", total);
        System.out.println(shapes[2] instanceof Rect);
        System.out.println(shapes[1] instanceof Square);
        System.out.println(shapes[0].getClass().getSimpleName());
    }
}
