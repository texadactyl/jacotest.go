// hacked from https://github.com/xtangle/completable-futures/tree/master/src
// https://en.wikipedia.org/wiki/Gauss%E2%80%93Legendre_algorithm

import java.math.BigDecimal;
import java.math.RoundingMode;

public class main {

    static final int NDIGITS = 100000;

    private static int ckSubset(String str, int low, int high, String expected) {
        byte[] bb = str.getBytes();
        for (int ix = low; ix < high; ix++) {
            System.out.printf("ckSubset %d : %c\n", ix, bb[ix]);
        }
        String observed = str.substring(low, high);
        if (observed.equals(expected)) {
            System.out.printf("ckSubset Success expected=observed=%s\n", expected);
            return 0;
        }
        System.out.printf("ckSubset *** ERROR, expected=%s, observed=%s\n", expected, observed);
        return 1;
    }

    public static void main(String[] args) {
    
        int errorCount = 0;
    
        System.out.println("The first 100,000 digits of Pi");
        BrentSalamin brs = new BrentSalamin();
        BigDecimal piDec = brs.computeImpl(NDIGITS);
        String str = piDec.toString();
        System.out.println("Check Professor Feynman's famous 6 nines .....");
        errorCount += ckSubset(str, 763, 769, "999999"); // https://en.wikipedia.org/wiki/Six_nines_in_pi
        System.out.println("Check the last 8 digits .....");
        errorCount += ckSubset(str, NDIGITS - 8, NDIGITS, "54936246");
        
        assert errorCount == 0;
        System.out.println("Success!");
    }
}

