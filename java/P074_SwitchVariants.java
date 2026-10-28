public class P074_SwitchVariants {
    enum Color { RED, GREEN, BLUE, YELLOW }

    static String classify(int n) {
        String r;
        switch (n % 4) {
            case 0:
                r = "zero";
                break;
            case 1:
            case -1:
                r = "one";
                break;
            case 2: {
                String tmp = "t";
                r = tmp + "wo";
                break;
            }
            default:
                r = "three";
        }
        return r;
    }

    static int daysIn(int month, boolean leap) {
        switch (month) {
            case 2:
                return leap ? 29 : 28;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }

    static String colorTemp(Color c) {
        switch (c) {
            case RED:
            case YELLOW:
                return "warm";
            case BLUE:
                return "cool";
        }
        return "neutral";
    }

    public static void main(String[] args) {
        for (int i = -3; i <= 5; i++) System.out.print(classify(i) + " ");
        System.out.println();
        int total = 0;
        for (int m = 1; m <= 12; m++) total += daysIn(m, false);
        System.out.println(total + " " + daysIn(2, true));
        for (Color c : Color.values()) System.out.print(c + ":" + colorTemp(c) + " ");
        System.out.println();
        String s = "";
        for (char ch : "a1b2!".toCharArray()) {
            switch (Character.getType(ch) == Character.DECIMAL_DIGIT_NUMBER ? 'd' : Character.isLetter(ch) ? 'l' : 'o') {
                case 'd': s += "D"; break;
                case 'l': s += "L"; break;
                default: s += "?";
            }
        }
        System.out.println(s);
        int count = 0;
        loop:
        for (int i = 0; ; i++) {
            switch (i % 3) {
                case 0: count += 1; break;
                case 1: count += 10; continue loop;
                case 2: if (i > 10) break loop; count += 100;
            }
        }
        System.out.println(count);
        long big = 3_000_000_000L;
        switch ((int) (big % 7)) {
            case 0: System.out.println("div by 7"); break;
            default: System.out.println("rem " + big % 7);
        }
    }
}
