import java.util.*;

public class P047_BankSimulation {
    enum Kind { DEPOSIT, WITHDRAW, TRANSFER }

    static class Tx {
        final Kind kind;
        final String from, to;
        final long cents;
        Tx(Kind kind, String from, String to, long cents) {
            this.kind = kind; this.from = from; this.to = to; this.cents = cents;
        }
    }

    static class Bank {
        private final Map<String, Long> balances = new TreeMap<>();
        private final List<String> log = new ArrayList<>();
        private int failures;

        void open(String name, long cents) { balances.put(name, cents); }

        void apply(Tx tx) {
            try {
                switch (tx.kind) {
                    case DEPOSIT:
                        credit(tx.to, tx.cents);
                        break;
                    case WITHDRAW:
                        debit(tx.from, tx.cents);
                        break;
                    case TRANSFER:
                        debit(tx.from, tx.cents);
                        credit(tx.to, tx.cents);
                        break;
                }
                log.add(tx.kind + " ok");
            } catch (IllegalStateException e) {
                failures++;
                log.add(tx.kind + " failed: " + e.getMessage());
            }
        }

        private void credit(String who, long c) {
            if (!balances.containsKey(who)) throw new IllegalStateException("no account " + who);
            balances.put(who, balances.get(who) + c);
        }

        private void debit(String who, long c) {
            Long b = balances.get(who);
            if (b == null) throw new IllegalStateException("no account " + who);
            if (b < c) throw new IllegalStateException(String.format("%s has only $%d.%02d", who, b / 100, b % 100));
            balances.put(who, b - c);
        }

        String fmt(long c) { return String.format("$%,d.%02d", c / 100, c % 100); }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.open("alice", 150_000);
        bank.open("bob", 2_550);
        bank.open("carol", 0);
        Tx[] txs = {
                new Tx(Kind.DEPOSIT, null, "bob", 1_000),
                new Tx(Kind.TRANSFER, "alice", "carol", 99_999),
                new Tx(Kind.WITHDRAW, "bob", null, 5_000),
                new Tx(Kind.TRANSFER, "carol", "dave", 10),
                new Tx(Kind.WITHDRAW, "carol", null, 99_999),
                new Tx(Kind.DEPOSIT, null, "alice", 123_456_789),
        };
        for (Tx t : txs) bank.apply(t);
        bank.log.forEach(System.out::println);
        long total = 0;
        for (Map.Entry<String, Long> e : bank.balances.entrySet()) {
            System.out.println(e.getKey() + ": " + bank.fmt(e.getValue()));
            total += e.getValue();
        }
        System.out.println("total " + bank.fmt(total) + ", failures " + bank.failures);
    }
}
