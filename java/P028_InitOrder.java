public class P028_InitOrder {
    static StringBuilder log = new StringBuilder();

    static class Base {
        static { log.append("Base.static "); }
        int x = init("Base.field ");
        { log.append("Base.block "); }
        Base() {
            log.append("Base() ");
            hook();
        }
        void hook() { log.append("Base.hook "); }
        static int init(String s) { log.append(s); return 1; }
    }

    static class Derived extends Base {
        static { log.append("Derived.static "); }
        int y = init("Derived.field ");
        Derived() {
            super();
            log.append("Derived(y=" + y + ") ");
        }
        @Override void hook() { log.append("Derived.hook(y=" + y + ") "); }
    }

    static final int CONST = compute();
    static int compute() { return 6 * 7; }

    public static void main(String[] args) {
        log.append("main ");
        new Derived();
        System.out.println(log.toString().trim());
        log.setLength(0);
        new Derived();
        System.out.println(log.toString().trim());
        System.out.println(CONST);
    }
}
