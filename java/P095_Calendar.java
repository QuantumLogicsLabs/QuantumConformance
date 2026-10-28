public class P095_Calendar {
    static boolean leap(int y) { return (y % 4 == 0 && y % 100 != 0) || y % 400 == 0; }

    static int daysInMonth(int y, int m) {
        int[] d = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return m == 2 && leap(y) ? 29 : d[m - 1];
    }

    // 0 = Sunday (Zeller-style via Sakamoto)
    static int dayOfWeek(int y, int m, int d) {
        int[] t = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        if (m < 3) y--;
        return (y + y / 4 - y / 100 + y / 400 + t[m - 1] + d) % 7;
    }

    static long daysSinceEpoch(int y, int m, int d) {
        long days = 0;
        for (int yy = 1970; yy < y; yy++) days += leap(yy) ? 366 : 365;
        for (int mm = 1; mm < m; mm++) days += daysInMonth(y, mm);
        return days + d - 1;
    }

    static void printMonth(int y, int m) {
        String[] names = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String title = names[m - 1] + " " + y;
        System.out.println(" ".repeat((20 - title.length()) / 2) + title);
        System.out.println("Su Mo Tu We Th Fr Sa");
        StringBuilder sb = new StringBuilder();
        int start = dayOfWeek(y, m, 1);
        sb.append("   ".repeat(start));
        for (int d = 1; d <= daysInMonth(y, m); d++) {
            sb.append(String.format("%2d", d));
            if ((start + d) % 7 == 0) { System.out.println(sb); sb.setLength(0); }
            else sb.append(' ');
        }
        if (sb.length() > 0) System.out.println(sb.toString().trim().isEmpty() ? "" : sb.toString().replaceAll("\\s+$", ""));
    }

    public static void main(String[] args) {
        printMonth(2024, 2);
        printMonth(2026, 10);
        String[] dn = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        System.out.println(dn[dayOfWeek(1969, 7, 20)] + " " + dn[dayOfWeek(2000, 1, 1)] + " " + dn[dayOfWeek(2026, 10, 27)]);
        System.out.println(daysSinceEpoch(2000, 1, 1) + " " + daysSinceEpoch(2026, 10, 27));
        int count = 0;
        for (int y = 1901; y <= 2000; y++) for (int m = 1; m <= 12; m++) if (dayOfWeek(y, m, 1) == 0) count++;
        System.out.println("Sundays on the 1st in the 20th century: " + count);
        System.out.println(leap(1900) + " " + leap(2000) + " " + leap(2024) + " " + leap(2026));
    }
}
