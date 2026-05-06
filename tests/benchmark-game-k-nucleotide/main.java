/* k-nucleotide
   The Computer Language Benchmarks Game
   https://salsa.debian.org/benchmarksgame-team/benchmarksgame/
   
   Naive transliteration from bearophile's program 
   contributed by Isaac Gouy
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class main {

    final static String FILE_INPUT = "sample.fasta";

    static ArrayList<String> seqLines() throws IOException {
        final var in = new BufferedReader(new FileReader(FILE_INPUT));
        String line;
        while ((line = in.readLine()) != null) {
            if (line.startsWith(">THREE")) break;
        }
        final var lines = new ArrayList<String>();
        while ((line = in.readLine()) != null) {
            if (line.startsWith(">")) break;
            lines.add(line);
        }
        return lines;
    }

    static HashMap<String, Integer> baseCounts(int bases, String seq) {
        var counts = new HashMap<String, Integer>();
        final int size = seq.length() + 1 - bases;
        for (int i = 0; i < size; i++) {
            var nucleo = seq.substring(i, i + bases);
            Integer v;
            if ((v = counts.get(nucleo)) != null) {
                counts.put(nucleo, v + 1);
            } else {
                counts.put(nucleo, 1);
            }
        }
        return counts;
    }

    static List<String> sortedFreq(int bases, String seq) {
        final int size = seq.length() + 1 - bases;

        // Count directly into parallel arrays, no Map iteration needed
        String[] keys = new String[size];
        int[] vals = new int[size];
        int uniqueCount = 0;

        for (int i = 0; i < size; i++) {
            String sub = seq.substring(i, i + bases);
            // Linear search for existing key
            int found = -1;
            for (int k = 0; k < uniqueCount; k++) {
                if (keys[k].equals(sub)) {
                    found = k;
                    break;
                }
            }
            if (found >= 0) {
                vals[found]++;
            } else {
                keys[uniqueCount] = sub;
                vals[uniqueCount] = 1;
                uniqueCount++;
            }
        }

        // Insertion sort descending by value
        for (int i = 1; i < uniqueCount; i++) {
            String curKey = keys[i];
            int curVal = vals[i];
            int j = i - 1;
            while (j >= 0 && vals[j] < curVal) {
                keys[j + 1] = keys[j];
                vals[j + 1] = vals[j];
                j--;
            }
            keys[j + 1] = curKey;
            vals[j + 1] = curVal;
        }

        // Build output list
        List<String> output = new ArrayList<>(uniqueCount);
        for (int i = 0; i < uniqueCount; i++) {
            output.add(String.format("%s %.3f", keys[i], 100.0 * vals[i] / size));
        }
        return output;
    }

    static int specificCount(String code, String seq) {
        return baseCounts(code.length(), seq).getOrDefault(code, 0);
    }

    public static void main(String[] args) throws Exception {
        int errorCount = 0;
        
        StringBuilder sb = new StringBuilder();
        for (String line : seqLines()) {
            sb.append(line.toUpperCase());
        }
        final String seq = sb.toString();

        for (int i = 1; i <= 2; i++) {
            for (String s : sortedFreq(i, seq)) {
                System.out.println(s);
            }
            System.out.println();
        }

        // jj._traceVerbose(true);
        
        String[] codes = {
            "GGT", "GGTA", "GGTATT",
            "GGTATTTTAATT", "GGTATTTTAATTTATAGT"
        };
        
        int expected[] = {54, 24, 4, 0, 0};
        
        int ix = 0;
        int sc = -1;
        for (String code : codes) {
            sc = specificCount(code, seq);
            //System.out.printf("%d\t%s\n", sc, code);
            errorCount += Checkers.checker(code, expected[ix++], sc);
        }
        
        Checkers.theEnd(errorCount);
    }

}

class jj {
    public static void _traceVerbose(boolean flag) { // Hotspot
        System.out.println("Hotspot execution of jj._traceVerbose(flag)");
    }
}

