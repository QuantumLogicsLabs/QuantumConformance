import java.util.*;

public class P068_Elevator {
    enum Dir { UP, DOWN, IDLE }

    static class Elevator {
        int floor;
        Dir dir = Dir.IDLE;
        final TreeSet<Integer> stops = new TreeSet<>();
        final List<String> log = new ArrayList<>();
        int travelled;

        void request(int f) { if (f != floor) stops.add(f); }

        void tick() {
            if (stops.isEmpty()) { dir = Dir.IDLE; return; }
            if (dir == Dir.IDLE) dir = stops.first() > floor ? Dir.UP : Dir.DOWN;
            Integer next = dir == Dir.UP ? stops.ceiling(floor) : stops.floor(floor);
            if (next == null) {
                dir = dir == Dir.UP ? Dir.DOWN : Dir.UP;
                next = dir == Dir.UP ? stops.ceiling(floor) : stops.floor(floor);
            }
            floor += Integer.signum(next - floor);
            travelled++;
            if (stops.remove(floor)) log.add("stop@" + floor + "(" + dir + ")");
        }
    }

    public static void main(String[] args) {
        Elevator e = new Elevator();
        e.floor = 3;
        int[][] schedule = {{0, 7}, {0, 1}, {2, 5}, {6, 2}, {9, 9}, {9, 0}};
        int si = 0;
        for (int t = 0; t < 30; t++) {
            while (si < schedule.length && schedule[si][0] == t) e.request(schedule[si++][1]);
            e.tick();
            if (t % 5 == 4) System.out.println("t=" + t + " floor=" + e.floor + " dir=" + e.dir + " pending=" + e.stops);
        }
        System.out.println(e.log);
        System.out.println("travelled " + e.travelled + " floors");
    }
}
