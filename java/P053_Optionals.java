import java.util.*;

public class P053_Optionals {
    static Optional<Integer> parse(String s) {
        try {
            return Optional.of(Integer.parseInt(s.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    static Map<String, String> emails = Map.of("ann", "ann@x.org", "bo", "bo@y.com");

    static Optional<String> domainOf(String user) {
        return Optional.ofNullable(emails.get(user)).map(e -> e.substring(e.indexOf('@') + 1));
    }

    public static void main(String[] args) {
        for (String s : new String[]{"42", " 7 ", "x1", ""}) {
            Optional<Integer> o = parse(s);
            System.out.println("'" + s + "' -> " + o + " " + o.isPresent() + " " + o.orElse(-1) + " " + o.map(v -> v * 2).orElse(0));
        }
        System.out.println(domainOf("ann").orElse("?") + " " + domainOf("zed").orElse("?"));
        System.out.println(domainOf("bo").filter(d -> d.endsWith(".org")).isPresent());
        Optional<String> name = Optional.of("quantum");
        name.ifPresent(n -> System.out.println("hi " + n));
        Optional.empty().ifPresent(n -> System.out.println("never"));
        System.out.println(name.flatMap(n -> n.length() > 3 ? Optional.of(n.length()) : Optional.empty()).get());
        System.out.println(Optional.ofNullable(null).orElseGet(() -> "generated"));
        try {
            Optional.empty().orElseThrow(() -> new IllegalStateException("nothing here"));
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        try {
            Optional.empty().get();
        } catch (NoSuchElementException e) {
            System.out.println("NSEE " + e.getMessage());
        }
        System.out.println(Objects.requireNonNullElse(null, "default") + " " + Objects.equals(null, null) + " " + Objects.isNull(name));
        System.out.println(Objects.toString(null) + " " + Objects.toString(null, "dflt") + " " + Objects.hashCode(null));
    }
}
