import java.util.*;

public class P099_TextAdventure {
    static class Room {
        final String name, desc;
        final Map<String, Room> exits = new TreeMap<>();
        final List<String> items = new ArrayList<>();
        Room(String n, String d) { name = n; desc = d; }
        void connect(String dir, Room other, String back) { exits.put(dir, other); other.exits.put(back, this); }
    }

    static class Player {
        Room at;
        final Set<String> bag = new TreeSet<>();
        int moves;
    }

    static String act(Player p, String cmd) {
        String[] w = cmd.split(" ", 2);
        switch (w[0]) {
            case "look":
                return p.at.name + ": " + p.at.desc + " Exits " + p.at.exits.keySet() + (p.at.items.isEmpty() ? "" : " Items " + p.at.items);
            case "go": {
                Room next = p.at.exits.get(w.length > 1 ? w[1] : "");
                if (next == null) return "You can't go that way.";
                if (next.name.equals("Vault") && !p.bag.contains("key")) return "The vault is locked.";
                p.at = next;
                p.moves++;
                return "You enter the " + next.name + ".";
            }
            case "take":
                if (w.length > 1 && p.at.items.remove(w[1])) { p.bag.add(w[1]); return "Taken: " + w[1]; }
                return "No such item.";
            case "inv":
                return "You carry " + (p.bag.isEmpty() ? "nothing" : String.join(", ", p.bag)) + ".";
            default:
                return "Unknown command '" + w[0] + "'.";
        }
    }

    public static void main(String[] args) {
        Room hall = new Room("Hall", "A dusty hall.");
        Room lib = new Room("Library", "Shelves of old books.");
        Room garden = new Room("Garden", "Overgrown and quiet.");
        Room vault = new Room("Vault", "Gold glitters everywhere!");
        hall.connect("north", lib, "south");
        hall.connect("east", garden, "west");
        lib.connect("down", vault, "up");
        garden.items.add("key");
        garden.items.add("rose");
        lib.items.add("map");
        Player p = new Player();
        p.at = hall;
        String[] script = {"look", "go north", "go down", "take map", "go south", "go east", "look", "take key", "take sword",
                "inv", "go west", "go north", "go down", "look", "dance"};
        for (String s : script) System.out.println("> " + s + "\n" + act(p, s));
        System.out.println("Moves: " + p.moves + ", treasure found: " + p.at.name.equals("Vault"));
    }
}
