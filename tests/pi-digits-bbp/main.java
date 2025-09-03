// Design: https://mathworld.wolfram.com/BBPFormula.html

/*

The BBP (named after Bailey-Borwein-Plouffe) is a formula for calculating pi discovered by Simon Plouffe in 1995.

*/

import java.math.BigDecimal;
import java.math.RoundingMode;

public class main {

    private static final long MAX_N = 100;
    private static final int SCALE = 50; // number of decimal places
    private static final double tolerance = 0.0001;
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal TWO = BigDecimal.valueOf(2);
    private static final BigDecimal FOUR = BigDecimal.valueOf(4);
    private static final BigDecimal FIVE = BigDecimal.valueOf(5);
    private static final BigDecimal SIX = BigDecimal.valueOf(6);
    private static final BigDecimal SIXTEEN = BigDecimal.valueOf(16);
    private static final BigDecimal EXPECTED = BigDecimal.valueOf(3.141592653589793);

    public static void main(String[] args) {

        BigDecimal pi = BigDecimal.ZERO;
        BigDecimal eightN;
        BigDecimal term;

        for (int nn = 0; nn < MAX_N; nn++) {
            eightN = BigDecimal.valueOf(8 * nn);
            term = FOUR.divide(eightN.add(ONE), SCALE, RoundingMode.HALF_UP);
            term = term.subtract(TWO.divide(eightN.add(FOUR), SCALE, RoundingMode.HALF_UP));
            term = term.subtract(ONE.divide(eightN.add(FIVE), SCALE, RoundingMode.HALF_UP));
            term = term.subtract(ONE.divide(eightN.add(SIX), SCALE, RoundingMode.HALF_UP));
            term = term.divide(SIXTEEN.pow(nn), SCALE, RoundingMode.HALF_UP);
            pi = pi.add(term);
        }

        System.out.printf("Pi calculated with the Bailey-Borwein-Plouffe algorithm: %s\n", pi.toPlainString());
        
        int errorCount = Checkers.withinTolerance("Pi", EXPECTED, pi, tolerance);
        errorCount += Checkers.withinTolerance("Pi", EXPECTED, pi, BigDecimal.valueOf(tolerance));
        Checkers.theEnd(errorCount);
    }
}

