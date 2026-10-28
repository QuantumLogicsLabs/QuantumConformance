public class P011_ClassesBasic {
    static class Point {
        private final int x, y;
        private static int created = 0;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
            created++;
        }

        Point() { this(0, 0); }

        int getX() { return x; }
        int getY() { return y; }

        Point translate(int dx, int dy) { return new Point(x + dx, y + dy); }

        double distanceTo(Point o) {
            int dx = x - o.x, dy = y - o.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        @Override
        public String toString() { return "(" + x + ", " + y + ")"; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() { return 31 * x + y; }
    }

    public static void main(String[] args) {
        Point a = new Point(3, 4);
        Point b = new Point();
        Point c = a.translate(1, -1);
        System.out.println(a + " " + b + " " + c);
        System.out.println(a.distanceTo(b));
        System.out.println(a.equals(new Point(3, 4)));
        System.out.println(a == new Point(3, 4));
        System.out.println(a.hashCode());
        System.out.println("created: " + Point.created);
        Object o = c;
        System.out.println(o.toString());
        System.out.println(a.getX() + a.getY());
    }
}
