public class P055_StaticNested {
    static int instances;

    static class Counter {
        private static int total;
        private int mine;
        Counter() { instances++; }
        Counter inc() { mine++; total++; return this; }
        static int total() { return total; }
        int mine() { return mine; }
    }

    static class Config {
        private final String host;
        private final int port;
        private final boolean tls;

        private Config(Builder b) { host = b.host; port = b.port; tls = b.tls; }

        static Builder builder() { return new Builder(); }

        static class Builder {
            private String host = "localhost";
            private int port = 80;
            private boolean tls;
            Builder host(String h) { host = h; return this; }
            Builder port(int p) { port = p; return this; }
            Builder tls(boolean t) { tls = t; if (t && port == 80) port = 443; return this; }
            Config build() { return new Config(this); }
        }

        @Override public String toString() { return (tls ? "https" : "http") + "://" + host + ":" + port; }
    }

    static final class Singleton {
        private static Singleton inst;
        private int hits;
        private Singleton() {}
        static Singleton get() {
            if (inst == null) inst = new Singleton();
            inst.hits++;
            return inst;
        }
    }

    public static void main(String[] args) {
        Counter a = new Counter(), b = new Counter();
        a.inc().inc().inc();
        b.inc();
        System.out.println(a.mine() + " " + b.mine() + " " + Counter.total() + " " + instances);
        System.out.println(Config.builder().build());
        System.out.println(Config.builder().host("example.com").tls(true).build());
        System.out.println(Config.builder().port(8080).host("api").build());
        Singleton.get();
        Singleton.get();
        System.out.println(Singleton.get().hits + " " + (Singleton.get() == Singleton.get()));
    }
}
