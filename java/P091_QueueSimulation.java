import java.util.*;

public class P091_QueueSimulation {
    static class Customer {
        final int id, arrive, service;
        Customer(int id, int arrive, int service) { this.id = id; this.arrive = arrive; this.service = service; }
    }

    public static void main(String[] args) {
        Random rnd = new Random(5);
        List<Customer> customers = new ArrayList<>();
        int t = 0;
        for (int i = 1; i <= 15; i++) {
            t += 1 + rnd.nextInt(4);
            customers.add(new Customer(i, t, 2 + rnd.nextInt(5)));
        }
        int tellers = 2;
        int[] freeAt = new int[tellers];
        Queue<Customer> line = new LinkedList<>(customers);
        int totalWait = 0, maxWait = 0, maxWaiter = -1;
        int[] served = new int[tellers];
        StringBuilder sb = new StringBuilder();
        while (!line.isEmpty()) {
            Customer c = line.poll();
            int k = 0;
            for (int i = 1; i < tellers; i++) if (freeAt[i] < freeAt[k]) k = i;
            int start = Math.max(c.arrive, freeAt[k]);
            int wait = start - c.arrive;
            totalWait += wait;
            if (wait > maxWait) { maxWait = wait; maxWaiter = c.id; }
            freeAt[k] = start + c.service;
            served[k]++;
            sb.append(String.format("c%-2d arr=%2d teller=%d start=%2d wait=%d%n", c.id, c.arrive, k, start, wait));
        }
        System.out.print(sb);
        System.out.printf("avg wait %.2f, max wait %d (customer %d), served %s, done at %d%n",
                totalWait / (double) customers.size(), maxWait, maxWaiter, Arrays.toString(served),
                Arrays.stream(freeAt).max().getAsInt());
    }
}
