import java.util.*;

public class P059_StateMachine {
    enum State {
        IDLE {
            State on(String ev) { return ev.equals("coin") ? READY : this; }
        },
        READY {
            State on(String ev) {
                switch (ev) {
                    case "push": return VENDING;
                    case "refund": return IDLE;
                    default: return this;
                }
            }
        },
        VENDING {
            State on(String ev) { return ev.equals("done") ? IDLE : this; }
        };

        abstract State on(String ev);
    }

    static boolean validIdentifier(String s) {
        int st = 0;
        for (char c : s.toCharArray()) {
            switch (st) {
                case 0:
                    st = (Character.isLetter(c) || c == '_') ? 1 : 2;
                    break;
                case 1:
                    if (!(Character.isLetterOrDigit(c) || c == '_')) st = 2;
                    break;
                default:
                    return false;
            }
        }
        return st == 1;
    }

    public static void main(String[] args) {
        State s = State.IDLE;
        String[] events = {"push", "coin", "coin", "push", "refund", "done", "coin", "refund"};
        StringBuilder trace = new StringBuilder(s.name());
        Map<State, Integer> visits = new EnumMap<>(State.class);
        for (String ev : events) {
            s = s.on(ev);
            visits.merge(s, 1, Integer::sum);
            trace.append(" -").append(ev).append("-> ").append(s);
        }
        System.out.println(trace);
        System.out.println(visits);
        for (String id : new String[]{"abc", "_x1", "9lives", "", "ok_name2", "bad-name"})
            System.out.print(id + "=" + validIdentifier(id) + " ");
        System.out.println();
        System.out.println(Arrays.toString(State.values()) + " " + State.valueOf("READY").ordinal());
    }
}
