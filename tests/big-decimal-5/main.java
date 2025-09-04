// Reference: https://en.wikipedia.org/wiki/Chudnovsky_algorithm

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class main {

    // Chudnovsky constants
    private static final BigDecimal CHUD = new BigDecimal("426880").multiply(sqrt(new BigDecimal("10005"), new MathContext(120)));
    
    // Other constants
    private static final int DEC_PLACES = 100;
    private static final int NDIGITS = DEC_PLACES + 10;  // DEC_PLACES + 10 extra
    private static final int NTERMS = 3; // 3–4 terms enough for 100 digits
    private static final double TOLERANCE = 0.0000001;

    // Compute square root using Newton-Raphson
    private static BigDecimal sqrt(BigDecimal A, MathContext mc) {
        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
        BigDecimal two = BigDecimal.valueOf(2);
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, mc).add(x0).divide(two, mc);
        }
        return x1;
    }

    // Factorial of n
    private static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    // Compute the Chudnovsky series sum through a number of terms (nterms).
    private static BigDecimal chudnovskyPi(int nterms, MathContext mc) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int k = 0; k < nterms; k++) {
            BigDecimal kBD = new BigDecimal(k);
            BigDecimal numerator = new BigDecimal(factorial(6 * k))
                    .multiply(new BigDecimal(545140134L).multiply(kBD).add(new BigDecimal(13591409L)));
            BigDecimal denominator = new BigDecimal(factorial(3 * k))
                    .multiply(new BigDecimal(factorial(k).pow(3)))
                    .multiply(new BigDecimal(-262537412640768000L).pow(k));
            sum = sum.add(numerator.divide(denominator, mc), mc);
        }
        return CHUD.divide(sum, mc);
    }

    public static void main(String[] args) {
        MathContext mc = new MathContext(NDIGITS, RoundingMode.HALF_UP);
        BigDecimal pi = chudnovskyPi(NTERMS, mc);
        BigDecimal pi100 = pi.setScale(DEC_PLACES, RoundingMode.HALF_UP);
        System.out.println(pi100.toPlainString());
        
        int errorCount = Checkers.withinTolerance("pi100 vs Math.PI", new BigDecimal(Math.PI), pi100, TOLERANCE);
        Checkers.theEnd(errorCount);

    }
}

