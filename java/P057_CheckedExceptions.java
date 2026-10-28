import java.util.*;

public class P057_CheckedExceptions {
    static class ValidationException extends Exception {
        final List<String> problems;
        ValidationException(List<String> problems) {
            super(problems.size() + " problem(s)");
            this.problems = problems;
        }
    }

    static class ParseException extends Exception {
        final int line;
        ParseException(String msg, int line, Throwable cause) {
            super(msg + " at line " + line, cause);
            this.line = line;
        }
    }

    static Map<String, Integer> parseConfig(String[] lines) throws ParseException {
        Map<String, Integer> out = new LinkedHashMap<>();
        for (int i = 0; i < lines.length; i++) {
            String l = lines[i].trim();
            if (l.isEmpty() || l.startsWith("#")) continue;
            String[] kv = l.split("=", 2);
            if (kv.length != 2) throw new ParseException("missing '='", i + 1, null);
            try {
                out.put(kv[0].trim(), Integer.parseInt(kv[1].trim()));
            } catch (NumberFormatException e) {
                throw new ParseException("bad number", i + 1, e);
            }
        }
        return out;
    }

    static void validate(Map<String, Integer> cfg) throws ValidationException {
        List<String> problems = new ArrayList<>();
        if (!cfg.containsKey("port")) problems.add("port is required");
        else if (cfg.get("port") < 1024) problems.add("port must be >= 1024");
        if (cfg.getOrDefault("workers", 1) <= 0) problems.add("workers must be positive");
        if (!problems.isEmpty()) throw new ValidationException(problems);
    }

    static void run(String... lines) {
        try {
            Map<String, Integer> cfg = parseConfig(lines);
            validate(cfg);
            System.out.println("ok " + cfg);
        } catch (ParseException e) {
            System.out.println("parse error: " + e.getMessage() + (e.getCause() != null ? " (" + e.getCause().getMessage() + ")" : ""));
        } catch (ValidationException e) {
            System.out.println("invalid: " + e.getMessage() + " " + e.problems);
        }
    }

    public static void main(String[] args) {
        run("# server", "port = 8080", "workers=4");
        run("port=80", "workers = 0");
        run("workers=2", "port 9000");
        run("port=abc");
        run();
        Exception e = new Exception("top", new RuntimeException("middle", new Error("bottom")));
        for (Throwable t = e; t != null; t = t.getCause()) System.out.println(t.getClass().getName() + ": " + t.getMessage());
    }
}
