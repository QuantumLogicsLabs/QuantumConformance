public class P056_TryWithResources {
    static class Res implements AutoCloseable {
        final String name;
        final boolean failClose;
        Res(String name, boolean failClose) {
            this.name = name;
            this.failClose = failClose;
            System.out.println("open " + name);
        }
        void use(boolean fail) {
            System.out.println("use " + name);
            if (fail) throw new IllegalStateException("use failed: " + name);
        }
        @Override
        public void close() {
            System.out.println("close " + name);
            if (failClose) throw new RuntimeException("close failed: " + name);
        }
    }

    static int returnsInFinally() {
        int x = 1;
        try {
            return x;
        } finally {
            x = 2;
            System.out.println("finally sees x=" + x);
        }
    }

    public static void main(String[] args) {
        try (Res a = new Res("A", false); Res b = new Res("B", false)) {
            a.use(false);
            b.use(false);
        }
        System.out.println("--");
        try (Res a = new Res("C", false)) {
            a.use(true);
        } catch (IllegalStateException e) {
            System.out.println("caught " + e.getMessage());
        }
        System.out.println("--");
        try (Res a = new Res("D", true)) {
            a.use(true);
        } catch (RuntimeException e) {
            System.out.println("caught " + e.getMessage() + " suppressed=" + e.getSuppressed().length
                    + " " + e.getSuppressed()[0].getMessage());
        }
        System.out.println("--");
        try (Res a = new Res("E", true)) {
            a.use(false);
        } catch (RuntimeException e) {
            System.out.println("caught " + e.getMessage());
        } finally {
            System.out.println("outer finally");
        }
        System.out.println(returnsInFinally());
    }
}
