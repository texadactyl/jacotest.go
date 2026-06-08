/*
Ref: https://www.sciencedirect.com/topics/engineering/shannon-entropy
Hacked from: https://rosettacode.org/wiki/Entropy#Java

Shannon entropy is defined as the average rate at which information is produced by a stochastic source of data, 
where higher entropy indicates greater information content from new values. 
It is mathematically represented as S(X) = −sum [ p(xi) * log2(p(xi)) ], 
with p(xi) being the probability of obtaining a value xi.

Mathematics developed by Claude Shannon in 1948, it quantifies how much information is needed, on average, to describe an event.
*/

import java.lang.Math;
import java.util.Map;
import java.util.HashMap;

public class main {

    static final double logOfTwo = Math.log(2);

    public static double getShannonEntropy(String argStr) {
        int nchar = 0;
        Map<Character, Integer> occ = new HashMap<>();

        for (int ix = 0; ix < argStr.length(); ++ix) {
            char cx = argStr.charAt(ix);
            if (occ.containsKey(cx)) {
                occ.put(cx, occ.get(cx) + 1);
            } else {
                occ.put(cx, 1);
            }
            ++nchar;
        }

        double entropy = 0.0;
        for (Map.Entry<Character, Integer> entry : occ.entrySet()) {
            double p = (double) entry.getValue() / nchar;
            entropy -= p * log2(p);
        }
        
        return entropy;
    }

    private static double log2(double arg) {
        return Math.log(arg) / logOfTwo;
    }

    public static void main(String[] args) {
        String[] sstr = {
                "",
                "1223334444",
                "1223334444555555555",
                "122333",
                "1227774444",
                "aaBBcccDDDD",
                "1234567890abcdefghijklmnopqrstuvwxyz",
                "Rosetta Code",
        };

        for (String ss : sstr) {
            double entropy = getShannonEntropy(ss);
            System.out.printf("Shannon entropy of %40s: %.12f%n", ss, entropy);
        }
        return;
    }
}

