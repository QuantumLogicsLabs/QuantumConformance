import java.util.*;

public class P077_Scheduler {
    static class Job {
        final String name;
        final int arrival, burst, priority;
        int remaining, finish = -1;
        Job(String n, int a, int b, int p) { name = n; arrival = a; burst = b; priority = p; remaining = b; }
    }

    static List<Job> jobs() {
        return new ArrayList<>(List.of(new Job("A", 0, 7, 3), new Job("B", 2, 4, 1), new Job("C", 4, 1, 4),
                new Job("D", 5, 4, 2), new Job("E", 9, 2, 1)));
    }

    static void report(String title, List<Job> js) {
        double wait = 0, turn = 0;
        StringBuilder sb = new StringBuilder(title).append(": ");
        for (Job j : js) {
            int t = j.finish - j.arrival;
            turn += t;
            wait += t - j.burst;
            sb.append(j.name).append('=').append(j.finish).append(' ');
        }
        System.out.println(sb + String.format("avgWait=%.2f avgTurn=%.2f", wait / js.size(), turn / js.size()));
    }

    public static void main(String[] args) {
        List<Job> fcfs = jobs();
        int t = 0;
        for (Job j : fcfs) { t = Math.max(t, j.arrival) + j.burst; j.finish = t; }
        report("FCFS", fcfs);

        List<Job> srtf = jobs();
        StringBuilder gantt = new StringBuilder();
        for (t = 0; srtf.stream().anyMatch(j -> j.remaining > 0); t++) {
            final int now = t;
            Job cur = srtf.stream().filter(j -> j.arrival <= now && j.remaining > 0)
                    .min(Comparator.comparingInt((Job j) -> j.remaining).thenComparingInt(j -> j.arrival)).orElse(null);
            if (cur == null) { gantt.append('-'); continue; }
            gantt.append(cur.name);
            if (--cur.remaining == 0) cur.finish = t + 1;
        }
        System.out.println(gantt);
        report("SRTF", srtf);

        List<Job> rr = jobs();
        Deque<Job> q = new ArrayDeque<>();
        int quantum = 2, idx = 0;
        t = 0;
        StringBuilder g2 = new StringBuilder();
        while (idx < rr.size() || !q.isEmpty()) {
            while (idx < rr.size() && rr.get(idx).arrival <= t) q.add(rr.get(idx++));
            if (q.isEmpty()) { t = rr.get(idx).arrival; continue; }
            Job j = q.poll();
            int run = Math.min(quantum, j.remaining);
            for (int k = 0; k < run; k++) g2.append(j.name);
            t += run;
            j.remaining -= run;
            while (idx < rr.size() && rr.get(idx).arrival <= t) q.add(rr.get(idx++));
            if (j.remaining > 0) q.add(j); else j.finish = t;
        }
        System.out.println(g2);
        report("RR", rr);
    }
}
