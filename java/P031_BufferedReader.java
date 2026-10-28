import java.io.*;
import java.util.*;

public class P031_BufferedReader {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());
        Map<String, Integer> stock = new TreeMap<>();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();
            String item = st.nextToken();
            switch (cmd) {
                case "add":
                    stock.merge(item, Integer.parseInt(st.nextToken()), Integer::sum);
                    break;
                case "take": {
                    int want = Integer.parseInt(st.nextToken());
                    int have = stock.getOrDefault(item, 0);
                    if (want > have) out.println("cannot take " + want + " " + item);
                    else stock.put(item, have - want);
                    break;
                }
                case "show":
                    out.println(item + " -> " + stock.getOrDefault(item, 0));
                    break;
            }
        }
        out.println(stock);
        String line;
        int extra = 0;
        while ((line = br.readLine()) != null) extra += line.split("\\s+").length;
        out.println("extra words: " + extra);
        out.flush();
    }
}
