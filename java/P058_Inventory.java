import java.util.*;
import java.util.stream.*;

public class P058_Inventory {
    enum Category { ELECTRONICS, GROCERY, TOYS }

    static class Item {
        final String sku, name;
        final Category cat;
        int qty;
        final double price;
        Item(String sku, String name, Category cat, int qty, double price) {
            this.sku = sku; this.name = name; this.cat = cat; this.qty = qty; this.price = price;
        }
        double value() { return qty * price; }
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item("E1", "Laptop", Category.ELECTRONICS, 5, 999.99));
        items.add(new Item("E2", "Phone", Category.ELECTRONICS, 12, 599.5));
        items.add(new Item("G1", "Apples", Category.GROCERY, 200, 0.45));
        items.add(new Item("G2", "Bread", Category.GROCERY, 30, 2.99));
        items.add(new Item("T1", "Puzzle", Category.TOYS, 0, 14.0));
        items.add(new Item("T2", "Kite", Category.TOYS, 7, 22.25));

        System.out.printf("%-4s %-8s %-12s %5s %10s%n", "SKU", "Name", "Category", "Qty", "Value");
        for (Item it : items)
            System.out.printf("%-4s %-8s %-12s %5d %10.2f%n", it.sku, it.name, it.cat, it.qty, it.value());

        Map<Category, Double> byCat = new EnumMap<>(Category.class);
        for (Item it : items) byCat.merge(it.cat, it.value(), Double::sum);
        byCat.forEach((k, v) -> System.out.printf("%s: %.2f%n", k, v));

        List<String> outOfStock = items.stream().filter(i -> i.qty == 0).map(i -> i.name).collect(Collectors.toList());
        System.out.println("out of stock: " + outOfStock);
        Item priciest = Collections.max(items, Comparator.comparingDouble(i -> i.price));
        System.out.println("priciest: " + priciest.name);
        double total = items.stream().mapToDouble(Item::value).sum();
        System.out.printf("total inventory: %,.2f%n", total);
        Map<Category, Long> counts = items.stream().collect(Collectors.groupingBy(i -> i.cat, TreeMap::new, Collectors.counting()));
        System.out.println(counts);
        items.removeIf(i -> i.qty == 0);
        items.forEach(i -> i.qty += 1);
        System.out.println(items.stream().map(i -> i.sku + ":" + i.qty).collect(Collectors.joining(", ")));
    }
}
