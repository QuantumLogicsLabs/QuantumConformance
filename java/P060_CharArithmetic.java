public class P060_CharArithmetic {
    public static void main(String[] args) {
        char c = 'a';
        System.out.println(c + 1);
        System.out.println((char) (c + 1));
        System.out.println("" + c + 1);
        System.out.println(c + 'b');
        System.out.println('a' + "b" + 'c');
        c++;
        System.out.println(c);
        c += 5;
        System.out.println(c);
        char[] word = "hello".toCharArray();
        for (int i = 0; i < word.length; i++) word[i] = Character.toUpperCase(word[i]);
        System.out.println(word);
        System.out.println(String.valueOf(word) + " " + word.length);
        int[] counts = new int[128];
        for (char ch : "banana bandana".toCharArray()) counts[ch]++;
        for (char ch = 'a'; ch <= 'z'; ch++) if (counts[ch] > 0) System.out.print(ch + "" + counts[ch] + " ");
        System.out.println();
        System.out.println(Character.getNumericValue('Z') + " " + ('9' - '0') + " " + (char) ('0' + 7));
        System.out.println((int) Character.MAX_VALUE + " " + Character.isDigit('٣'));
        String s = "Zebra";
        System.out.println(s.charAt(0) < s.charAt(1));
        StringBuilder alpha = new StringBuilder();
        for (char x = 'z'; x >= 'u'; x -= 2) alpha.append(x);
        System.out.println(alpha);
        System.out.println('\t' + "|" + '\\' + '\'' + "\"" + "A");
        char unicode = 'é';
        System.out.println(unicode + " " + (int) unicode + " " + "café".length());
        switch (word[1]) {
            case 'E': System.out.println("E matched"); break;
            default: System.out.println("no match");
        }
    }
}
