// hacked from https://github.com/xtangle/completable-futures/tree/master/src

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ONE;

public class BrentSalamin {

    static final BigDecimal TWO = BigDecimal.valueOf(2);
    static final RoundingMode ROUND = RoundingMode.FLOOR;

    /**
     * Uses the Brent-Salamin formula to calculate digits of pi
     * See https://en.wikipedia.org/wiki/Gauss%E2%80%93Legendre_algorithm
     */
    BigDecimal computeImpl(int decimalDigits) {
        int precision = decimalDigits + 3;
        int n = numberOfIterations(decimalDigits);

        BigDecimal a = ONE;
        BigDecimal b = ONE.divide(sqrt(BigDecimal.valueOf(2), precision), precision, ROUND);
        BigDecimal t = BigDecimal.valueOf(0.25);
        BigDecimal p = ONE;

        BigDecimal a_i, b_i, t_i;

        for (int i = 1; i <= n; i++) {
            a_i = a.add(b).divide(TWO, precision, ROUND);
            b_i = sqrt(a.multiply(b), precision);
            t_i = t.subtract(p.multiply(a.subtract(a_i).pow(2)));

            a = a_i;
            b = b_i;
            t = t_i;
            p = p.add(p);
        }

        return (a.add(b).pow(2))
                .divide(BigDecimal.valueOf(4).multiply(t), precision, ROUND);
    }

    /**
     * Computes the square root of a BigDecimal to the given scale using the
     * Babylonian method (https://en.wikipedia.org/wiki/Methods_of_computing_square_roots)
     */
    static BigDecimal sqrt(BigDecimal a, final int scale) {
        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = new BigDecimal(Math.sqrt(a.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = a.divide(x0, scale, RoundingMode.HALF_UP)
                    .add(x0)
                    .divide(TWO, scale, RoundingMode.HALF_UP);
        }
        return x1;
    }

    /**
       Calulate the number of loop iterations as a function of the number of decimal digits.
     */
    private int numberOfIterations(int d) {
        int n = 1;
        long m = 2;
        long pow = 2;
        while (d > m) {
            pow *= 2;
            m += pow;
            n += 1;
        }
        return n;
    }

}
