import java.util.*;

public class P067_TextFormatting {
    static List<String> wrap(String text, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        for (String w : text.split(" ")) {
            if (cur.length() > 0 && cur.length() + 1 + w.length() > width) {
                lines.add(cur.toString());
                cur.setLength(0);
            }
            if (cur.length() > 0) cur.append(' ');
            cur.append(w);
        }
        if (cur.length() > 0) lines.add(cur.toString());
        return lines;
    }

    static String justify(String line, int width) {
        String[] ws = line.split(" ");
        if (ws.length == 1) return String.format("%-" + width + "s", line);
        int letters = 0;
        for (String w : ws) letters += w.length();
        int gaps = ws.length - 1, spaces = width - letters;
        StringBuilder sb = new StringBuilder(ws[0]);
        for (int i = 1; i < ws.length; i++) {
            int n = spaces / gaps + (i <= spaces % gaps ? 1 : 0);
            sb.append(" ".repeat(n)).append(ws[i]);
        }
        return sb.toString();
    }

    static String center(String s, int w) {
        int pad = w - s.length();
        return " ".repeat(pad / 2) + s + " ".repeat(pad - pad / 2);
    }

    public static void main(String[] args) {
        String text = "The quick brown fox jumps over the lazy dog while the cat watches from a sunny windowsill";
        List<String> lines = wrap(text, 24);
        String border = "+" + "-".repeat(26) + "+";
        System.out.println(border);
        for (String l : lines) System.out.println("| " + justify(l, 24) + " |");
        System.out.println(border);
        System.out.println("[" + center("title", 15) + "]");
        String[][] table = {{"Name", "Qty", "Price"}, {"Widget", "3", "4.50"}, {"Gadget", "12", "19.99"}, {"Thingamajig", "1", "250.00"}};
        int[] w = new int[3];
        for (String[] row : table) for (int i = 0; i < 3; i++) w[i] = Math.max(w[i], row[i].length());
        for (String[] row : table)
            System.out.println(String.format("%-" + w[0] + "s | %" + w[1] + "s | %" + w[2] + "s", row[0], row[1], row[2]));
        System.out.println(String.format("%s", String.join(",", Collections.nCopies(3, "ab"))));
        System.out.println("Tab\there" + "\r\n".trim().length());
        System.out.println("multi\nline".split("\n").length + " " + "a1b2c3".replaceAll("[0-9]", "#") + " " + "x-y_z".matches("[a-z_-]+"));
        System.out.println(String.valueOf(new char[]{'o', 'k'}) + " " + "UPPER".toLowerCase().toUpperCase().charAt(2) + " " + "  strip ".strip() + "|" + "ab".compareToIgnoreCase("AC"));
    }
}
