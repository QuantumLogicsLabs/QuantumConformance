import java.util.*;

public class P094_Abstract2 {
    static abstract class Employee {
        private static int seq = 100;
        protected final String name;
        protected final int id;
        Employee(String name) { this.name = name; this.id = seq++; }
        abstract double monthlyPay();
        String role() { return getClass().getSimpleName(); }
        final String payslip() { return String.format("%d %-7s %-10s %10.2f", id, name, role(), monthlyPay()); }
    }

    static class Salaried extends Employee {
        final double annual;
        Salaried(String n, double annual) { super(n); this.annual = annual; }
        double monthlyPay() { return annual / 12; }
    }

    static class Hourly extends Employee {
        final double rate;
        final int hours;
        Hourly(String n, double rate, int hours) { super(n); this.rate = rate; this.hours = hours; }
        double monthlyPay() {
            int overtime = Math.max(0, hours - 160);
            return rate * (hours - overtime) + rate * 1.5 * overtime;
        }
    }

    static class Manager extends Salaried {
        final List<Employee> reports = new ArrayList<>();
        Manager(String n, double annual) { super(n, annual); }
        @Override double monthlyPay() { return super.monthlyPay() + 100 * reports.size(); }
        @Override String role() { return "Mgr(" + reports.size() + ")"; }
    }

    public static void main(String[] args) {
        Manager boss = new Manager("Grace", 150000);
        Employee a = new Salaried("Alan", 96000), b = new Hourly("Linus", 42.5, 172), c = new Hourly("Ada", 55, 120);
        boss.reports.addAll(List.of(a, b, c));
        List<Employee> all = new ArrayList<>(List.of(boss, a, b, c));
        all.forEach(e -> System.out.println(e.payslip()));
        double payroll = 0;
        for (Employee e : all) payroll += e.monthlyPay();
        System.out.printf("payroll %.2f%n", payroll);
        all.sort(Comparator.comparingDouble(Employee::monthlyPay).reversed());
        StringBuilder sb = new StringBuilder();
        for (Employee e : all) sb.append(e.name).append(e instanceof Salaried ? "(S) " : "(H) ");
        System.out.println(sb.toString().trim());
    }
}
