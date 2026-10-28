public class P004_StringsBasic {
    public static void main(String[] args) {
        String s = "Hello, Java";
        System.out.println(s.length());
        System.out.println(s.charAt(4));
        System.out.println(s.indexOf("Java"));
        System.out.println(s.indexOf('z'));
        System.out.println(s.substring(7));
        System.out.println(s.substring(0, 5));
        System.out.println(s.toUpperCase());
        System.out.println(s.toLowerCase());
        System.out.println(s.contains("lo,"));
        System.out.println(s.startsWith("Hell"));
        System.out.println(s.endsWith("va"));
        System.out.println(s.replace('a', 'o'));
        System.out.println("  trim me  ".trim() + "|");
        System.out.println(s.equals("Hello, Java"));
        System.out.println(s.equalsIgnoreCase("HELLO, JAVA"));
        System.out.println("apple".compareTo("banana"));
        System.out.println("b".compareTo("a"));
        System.out.println("abc".compareTo("abcd"));
        System.out.println(s.lastIndexOf('a'));
        System.out.println("".isEmpty());
        System.out.println(String.valueOf(3.0) + String.valueOf(true));
        System.out.println("x".repeat(5));
        System.out.println(String.join("-", "a", "b", "c"));
        System.out.println("a,b,,c".split(",").length);
    }
}
