import java.util.*;

public class P093_Interpreter {
    static Map<String, Integer> vars = new TreeMap<>();
    static List<String> output = new ArrayList<>();

    static int value(String tok) {
        if (Character.isDigit(tok.charAt(0)) || tok.charAt(0) == '-') return Integer.parseInt(tok);
        Integer v = vars.get(tok);
        if (v == null) throw new IllegalStateException("undefined variable " + tok);
        return v;
    }

    static void run(String[] prog) {
        Map<String, Integer> labels = new HashMap<>();
        for (int i = 0; i < prog.length; i++) if (prog[i].endsWith(":")) labels.put(prog[i].substring(0, prog[i].length() - 1), i);
        int pc = 0, steps = 0;
        while (pc < prog.length) {
            if (++steps > 10_000) throw new IllegalStateException("too many steps");
            String[] t = prog[pc].trim().split("\\s+");
            pc++;
            switch (t[0]) {
                case "set": vars.put(t[1], value(t[2])); break;
                case "add": vars.put(t[1], value(t[1]) + value(t[2])); break;
                case "mul": vars.put(t[1], value(t[1]) * value(t[2])); break;
                case "mod": vars.put(t[1], value(t[1]) % value(t[2])); break;
                case "div": vars.put(t[1], value(t[1]) / value(t[2])); break;
                case "print": output.add(t[1] + "=" + value(t[1])); break;
                case "jnz": if (value(t[1]) != 0) pc = labels.get(t[2]); break;
                case "jmp": pc = labels.get(t[1]); break;
                default:
                    if (!t[0].endsWith(":")) throw new IllegalArgumentException("bad instruction: " + t[0]);
            }
        }
    }

    public static void main(String[] args) {
        String[] factorial = {"set n 10", "set acc 1", "loop:", "mul acc n", "add n -1", "jnz n loop", "print acc"};
        run(factorial);
        String[] gcd = {"set a 1071", "set b 462", "top:", "set t b", "set r a", "mod r b", "set a t", "set b r", "jnz b top", "print a"};
        run(gcd);
        String[] collatz = {"set x 27", "set steps 0", "again:", "set e x", "mod e 2", "jnz e odd", "div x 2", "jmp next",
                "odd:", "mul x 3", "add x 1", "next:", "add steps 1", "set c x", "add c -1", "jnz c again", "print steps"};
        run(collatz);
        output.forEach(System.out::println);
        System.out.println(vars);
        for (String[] bad : new String[][]{{"print zz"}, {"frob x"}, {"l:", "jmp l"}}) {
            try {
                run(bad);
            } catch (RuntimeException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }
}
