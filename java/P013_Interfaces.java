import java.util.ArrayList;
import java.util.List;

public class P013_Interfaces {
    interface Animal {
        String sound();
        default String speak() { return name() + " says " + sound(); }
        String name();
        static Animal of(String n, String s) {
            return new Animal() {
                public String sound() { return s; }
                public String name() { return n; }
            };
        }
    }

    interface Pet { int LEGS = 4; boolean friendly(); }

    static class Dog implements Animal, Pet {
        public String sound() { return "Woof"; }
        public String name() { return "Dog"; }
        public boolean friendly() { return true; }
    }

    static class Cat implements Animal {
        public String sound() { return "Meow"; }
        public String name() { return "Cat"; }
        @Override public String speak() { return "The cat ignores you"; }
    }

    public static void main(String[] args) {
        List<Animal> zoo = new ArrayList<>();
        zoo.add(new Dog());
        zoo.add(new Cat());
        zoo.add(Animal.of("Cow", "Moo"));
        for (Animal a : zoo) System.out.println(a.speak());
        Pet p = new Dog();
        System.out.println(p.friendly() + " " + Pet.LEGS);
        System.out.println(zoo.get(0) instanceof Pet);
        System.out.println(zoo.get(1) instanceof Pet);
    }
}
