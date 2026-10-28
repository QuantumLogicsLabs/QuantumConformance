import java.util.*;

public class P088_Records2 {
    static final class Money implements Comparable<Money> {
        private final long cents;
        private final String currency;

        private Money(long cents, String currency) {
            this.cents = cents;
            this.currency = Objects.requireNonNull(currency);
        }

        static Money of(String amount, String cur) {
            String[] p = amount.split("\\.");
            long whole = Long.parseLong(p[0]);
            long frac = p.length > 1 ? Long.parseLong((p[1] + "00").substring(0, 2)) : 0;
            return new Money(whole * 100 + (whole < 0 ? -frac : frac), cur);
        }

        Money plus(Money o) {
            if (!currency.equals(o.currency)) throw new IllegalArgumentException("currency mismatch: " + currency + " vs " + o.currency);
            return new Money(cents + o.cents, currency);
        }

        Money times(double factor) { return new Money(Math.round(cents * factor), currency); }

        Money[] split(int parts) {
            Money[] out = new Money[parts];
            long base = cents / parts, extra = cents % parts;
            for (int i = 0; i < parts; i++) out[i] = new Money(base + (i < extra ? 1 : 0), currency);
            return out;
        }

        public int compareTo(Money o) { return Long.compare(cents, o.cents); }
        @Override public boolean equals(Object o) {
            return o instanceof Money && ((Money) o).cents == cents && ((Money) o).currency.equals(currency);
        }
        @Override public int hashCode() { return Objects.hash(cents, currency); }
        @Override public String toString() { return String.format("%s %d.%02d", currency, cents / 100, Math.abs(cents % 100)); }
    }

    public static void main(String[] args) {
        Money a = Money.of("19.99", "USD"), b = Money.of("5.5", "USD"), c = Money.of("100", "EUR");
        System.out.println(a + " + " + b + " = " + a.plus(b));
        System.out.println(a.times(3) + " " + a.times(0.15) + " " + c.times(1.075));
        System.out.println(Arrays.toString(Money.of("10.00", "USD").split(3)));
        try {
            a.plus(c);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(a.equals(Money.of("19.99", "USD")) + " " + a.compareTo(b) + " " + (a.hashCode() == Money.of("19.990", "USD").hashCode()));
        List<Money> wallet = new ArrayList<>(List.of(b, a, Money.of("0.01", "USD"), Money.of("-3.25", "USD")));
        Collections.sort(wallet);
        System.out.println(wallet);
        Money total = wallet.stream().reduce(Money.of("0", "USD"), Money::plus);
        System.out.println("total " + total);
    }
}
