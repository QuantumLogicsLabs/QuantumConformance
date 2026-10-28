public class P022_Exceptions {
    static class InsufficientFundsException extends Exception {
        final double shortBy;
        InsufficientFundsException(double shortBy) {
            super("short by " + shortBy);
            this.shortBy = shortBy;
        }
    }

    static class Account {
        double balance;
        Account(double b) { balance = b; }
        void withdraw(double amt) throws InsufficientFundsException {
            if (amt <= 0) throw new IllegalArgumentException("bad amount: " + amt);
            if (amt > balance) throw new InsufficientFundsException(amt - balance);
            balance -= amt;
        }
    }

    static int parse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        } finally {
            System.out.println("parsed " + s);
        }
    }

    public static void main(String[] args) {
        Account acc = new Account(100);
        try {
            acc.withdraw(30);
            acc.withdraw(100);
        } catch (InsufficientFundsException e) {
            System.out.println("caught: " + e.getMessage() + " / " + e.shortBy);
        }
        try {
            acc.withdraw(-5);
        } catch (IllegalArgumentException | InsufficientFundsException e) {
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        System.out.println(parse("42") + " " + parse("4x2"));
        try {
            int[] a = new int[2];
            a[2] = 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("AIOOBE: " + e.getMessage());
        }
        try {
            String s = null;
            s.length();
        } catch (NullPointerException e) {
            System.out.println("NPE");
        }
        try {
            Object o = "str";
            Integer i = (Integer) o;
        } catch (ClassCastException e) {
            System.out.println("CCE");
        }
        try {
            System.out.println(10 / 0);
        } catch (ArithmeticException e) {
            System.out.println(e);
        }
        try {
            try {
                throw new IllegalStateException("inner");
            } finally {
                System.out.println("inner finally");
            }
        } catch (RuntimeException e) {
            RuntimeException wrapped = new RuntimeException("outer", e);
            System.out.println(wrapped.getMessage() + " <- " + wrapped.getCause().getMessage());
        }
        System.out.println("balance " + acc.balance);
    }
}
