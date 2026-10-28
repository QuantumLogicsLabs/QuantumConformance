import java.util.*;

public class P066_Library {
    static abstract class Media {
        private static int nextId = 1;
        final int id;
        final String title;
        private String borrower;
        Media(String title) { this.id = nextId++; this.title = title; }
        abstract int loanDays();
        boolean available() { return borrower == null; }
        void borrow(String who) {
            if (!available()) throw new IllegalStateException(title + " already borrowed by " + borrower);
            borrower = who;
        }
        void giveBack() { borrower = null; }
        @Override public String toString() {
            return String.format("#%d %s '%s' (%d days)%s", id, getClass().getSimpleName(), title, loanDays(),
                    available() ? "" : " -> " + borrower);
        }
    }

    static class Book extends Media {
        final int pages;
        Book(String t, int pages) { super(t); this.pages = pages; }
        int loanDays() { return pages > 500 ? 28 : 21; }
    }

    static class Dvd extends Media {
        Dvd(String t) { super(t); }
        int loanDays() { return 7; }
    }

    static class Magazine extends Book {
        Magazine(String t) { super(t, 40); }
        @Override int loanDays() { return 3; }
    }

    public static void main(String[] args) {
        List<Media> catalog = new ArrayList<>();
        catalog.add(new Book("Dune", 612));
        catalog.add(new Book("Hobbit", 310));
        catalog.add(new Dvd("Alien"));
        catalog.add(new Magazine("Byte"));
        Map<String, List<Media>> loans = new TreeMap<>();
        String[][] requests = {{"sam", "Dune"}, {"ida", "Alien"}, {"sam", "Alien"}, {"ida", "Byte"}, {"tom", "Nope"}};
        for (String[] r : requests) {
            Optional<Media> m = catalog.stream().filter(x -> x.title.equals(r[1])).findFirst();
            if (!m.isPresent()) {
                System.out.println("no such title: " + r[1]);
                continue;
            }
            try {
                m.get().borrow(r[0]);
                loans.computeIfAbsent(r[0], k -> new ArrayList<>()).add(m.get());
            } catch (IllegalStateException e) {
                System.out.println("denied: " + e.getMessage());
            }
        }
        catalog.forEach(System.out::println);
        loans.forEach((who, items) -> {
            int max = 0;
            for (Media m : items) max = Math.max(max, m.loanDays());
            System.out.println(who + " has " + items.size() + " item(s), longest loan " + max + " days");
        });
        catalog.get(0).giveBack();
        long avail = catalog.stream().filter(Media::available).count();
        System.out.println("available now: " + avail + " " + (catalog.get(3) instanceof Book));
    }
}
