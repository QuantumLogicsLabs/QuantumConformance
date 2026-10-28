public class P029_Polymorphism {
    static class A {
        String name = "A.name";
        static String kind() { return "A.kind"; }
        String who() { return "A"; }
        String call() { return "A.call->" + who(); }
        void over(Object o) { System.out.println("A.over(Object)"); }
        void over(String s) { System.out.println("A.over(String)"); }
    }

    static class B extends A {
        String name = "B.name";
        static String kind() { return "B.kind"; }
        @Override String who() { return "B"; }
        @Override void over(Object o) { System.out.println("B.over(Object)"); }
    }

    static void describe(A a) { System.out.println("describe A"); }
    static void describe(B b) { System.out.println("describe B"); }
    static void num(int x) { System.out.println("int"); }
    static void num(long x) { System.out.println("long"); }
    static void num(double x) { System.out.println("double"); }
    static void num(Integer x) { System.out.println("Integer"); }
    static void boxed(Object o) { System.out.println("Object " + o); }

    public static void main(String[] args) {
        A ab = new B();
        System.out.println(ab.who() + " " + ab.call());
        System.out.println(ab.name + " " + ((B) ab).name);
        System.out.println(A.kind() + " " + B.kind());
        ab.over("s");
        ab.over(1);
        Object s = "str";
        ab.over(s);
        describe(ab);
        describe(new B());
        byte b = 1;
        short sh = 2;
        char c = 'c';
        num(b);
        num(sh);
        num(c);
        num(3L);
        num(2.5f);
        num(Integer.valueOf(4));
        boxed(5);
        boxed('q');
    }
}
