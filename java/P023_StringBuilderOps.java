public class P023_StringBuilderOps {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("hello");
        sb.append(' ').append("world").append(123).append(4.5).append(true);
        System.out.println(sb);
        System.out.println(sb.length());
        sb.insert(0, ">> ");
        System.out.println(sb);
        sb.reverse();
        System.out.println(sb);
        sb.reverse();
        sb.setCharAt(3, 'H');
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
        sb.delete(0, 3);
        System.out.println(sb);
        sb.replace(0, 5, "HOWDY");
        System.out.println(sb);
        System.out.println(sb.indexOf("world") + " " + sb.charAt(1));
        sb.setLength(5);
        System.out.println(sb + "|");
        StringBuilder pal = new StringBuilder("racecar");
        System.out.println(pal.toString().equals(pal.reverse().toString()));
        String joined = "";
        for (int i = 0; i < 5; i++) joined += i;
        System.out.println(joined);
        System.out.println(new StringBuilder().append(1).append('+').append(1).append('=').append(1 + 1));
    }
}
