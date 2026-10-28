public class P021_Enums {
    enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        EARTH(5.976e+24, 6.37814e6),
        JUPITER(1.9e+27, 7.1492e7);

        private final double mass, radius;
        static final double G = 6.67300E-11;

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        double surfaceGravity() { return G * mass / (radius * radius); }
    }

    enum Op {
        PLUS("+") { int eval(int a, int b) { return a + b; } },
        TIMES("*") { int eval(int a, int b) { return a * b; } };

        final String sym;
        Op(String s) { sym = s; }
        abstract int eval(int a, int b);
    }

    enum Level { LOW, MEDIUM, HIGH }

    public static void main(String[] args) {
        for (Planet p : Planet.values())
            System.out.printf("%s %.2f%n", p, p.surfaceGravity());
        for (Op op : Op.values())
            System.out.println("6 " + op.sym + " 7 = " + op.eval(6, 7));
        Level l = Level.valueOf("MEDIUM");
        System.out.println(l + " " + l.ordinal() + " " + l.name() + " " + l.compareTo(Level.HIGH));
        switch (l) {
            case LOW: System.out.println("low"); break;
            case MEDIUM: System.out.println("medium"); break;
            default: System.out.println("high");
        }
        System.out.println(Level.HIGH == Level.valueOf("HIGH"));
        java.util.EnumMap<Level, Integer> counts = new java.util.EnumMap<>(Level.class);
        counts.put(Level.HIGH, 3);
        counts.put(Level.LOW, 1);
        System.out.println(counts);
        try {
            Level.valueOf("EXTREME");
        } catch (IllegalArgumentException e) {
            System.out.println("no such level");
        }
    }
}
