public class P006_Conditionals {
    static String grade(int score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else return "F";
    }

    static String dayName(int d) {
        switch (d) {
            case 1: return "Mon";
            case 2: return "Tue";
            case 3: return "Wed";
            case 6:
            case 7:
                return "Weekend";
            default:
                return "Other";
        }
    }

    public static void main(String[] args) {
        int[] scores = {95, 85, 72, 40};
        for (int s : scores) System.out.println(s + " -> " + grade(s));
        for (int d = 1; d <= 7; d++) System.out.print(dayName(d) + " ");
        System.out.println();
        int x = 7;
        String parity = (x % 2 == 0) ? "even" : "odd";
        System.out.println(parity);
        boolean a = true, b = false;
        System.out.println(a && b);
        System.out.println(a || b);
        System.out.println(!a);
        System.out.println(a ^ b);
        String cmd = "stop";
        switch (cmd) {
            case "go":
                System.out.println("going");
                break;
            case "stop":
                System.out.println("stopping");
            case "fall":
                System.out.println("fell through");
                break;
        }
    }
}
