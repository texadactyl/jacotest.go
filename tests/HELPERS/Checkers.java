import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class Checkers {

    public static double MAX_PERCENT = 0.0001;
    public static double MAX_PERCENT_F = 0.0001f;

    public static int withinTolerance(String label, long expected, long observed) {
        return withinTolerance(label, expected, observed, MAX_PERCENT);
    }
    
    public static int withinTolerance(String label, long expected, long observed, double maxPercent) {
        if (expected == 0) {
            if (observed == 0) {
                System.out.printf("ok withinTolerance(%e) %s, expected = observed = 0\n", maxPercent, label);
                return 0;
            } else {
                System.out.printf("*** DISCREPANCY detected in withinTolerance(%e) %s ::: expected = 0, observed = %d\n", maxPercent, label, observed);
                return 1;
            }
        }
        long diff = Math.abs(expected - observed);
        double diffPct = Math.abs(100.0 * diff / expected);
        double tolerance = Math.abs(expected) * (maxPercent / 100.0);
        if (diff <= tolerance) {
            System.out.printf("ok withinTolerance(%e) %s, expected = %d, observed = %d\n", maxPercent, label, expected, observed);
            return 0;
        } else {
            System.out.printf("*** DISCREPANCY detected in withinTolerance(%e) %s ::: expected = %d, observed = %d, diffPct = %e\n", maxPercent, label, expected, observed, diffPct);
            return 1;
        }
    }

    public static int withinTolerance(String label, double expected, double observed) {
        return withinTolerance(label, expected, observed, MAX_PERCENT);
    }
    
    public static int withinTolerance(String label, double expected, double observed, double maxPercent) {
        double diff = Math.abs(expected - observed);
        if (expected < maxPercent) {
            if (diff < maxPercent) {
                System.out.printf("ok withinTolerance(%e) %s ok expected = %e, observed = %e\n", maxPercent, label, expected, observed);
                return 0;
            } else {
                System.out.printf("*** DISCREPANCY detected in withinTolerance(%e) %s ::: expected = %e, observed = %e\n", maxPercent, label, expected, observed);
                return 1;
            }
        }
        double diffPct = Math.abs(100.0 * diff / expected);
        double tolerance = Math.abs(expected) * (maxPercent / 100.0);
        if (diff <= tolerance) {
            System.out.printf("ok withinTolerance(%e) %s ok expected = %e, observed = %e, diffPct = %e\n", maxPercent, label, expected, observed, diffPct);
            return 0;
        } else {
            System.out.printf("*** DISCREPANCY detected in withinTolerance(%e) %s ::: expected = %e, observed = %e, diffPct = %e\n", maxPercent, label, expected, observed, diffPct);
            return 1;
        }
    }

    public static int withinTolerance(String label, float expected, float observed) {
        return withinTolerance(label, expected, observed, MAX_PERCENT_F);
    }
    
    public static int withinTolerance(String label, float expected, float observed, float maxPercent) {
        float diff = Math.abs(expected - observed);
        if (expected < maxPercent) {
            if (diff < maxPercent) {
                System.out.printf("ok withinTolerance(%e) %s ok expected = %e, observed = %e\n", maxPercent, label, expected, observed);
                return 0;
            } else {
                System.out.printf("*** DISCREPANCY detected in withinTolerance(%e) %s ::: expected = %e, observed = %e\n", maxPercent, label, expected, observed);
                return 1;
            }
        }
        float diffPct = Math.abs(100.0f * diff / expected);
        float tolerance = Math.abs(expected) * (maxPercent / 100.0f);
        if (diff <= tolerance) {
            System.out.printf("ok withinTolerance(%e) %s ok expected = %e, observed = %e, diffPct = %e\n", maxPercent, label, expected, observed, diffPct);
            return 0;
        } else {
            System.out.printf("*** DISCREPANCY detected in withinTolerance(%e) %s ::: expected = %e, observed = %e, diffPct = %e\n", maxPercent, label, expected, observed, diffPct);
            return 1;
        }
    }

    public static int checker(String label, boolean expected, boolean observed) {
        if (expected == observed) {
            System.out.printf("ok %s ::: expected = observed = %b\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %b, observed = %b\n", label, expected, observed);
        return 1;
    }

    public static int checker(String label, int expected, int observed) {
        if (expected == observed) {
            System.out.printf("ok %s ::: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %d, observed = %d\n", label, expected, observed);
        return 1;
    }

    public static int checker(String label, long expected, long observed) {
        if (expected == observed) {
            System.out.printf("ok %s ::: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %d, observed = %d\n", label, expected, observed);
        return 1;
    }

    public static int checker(String label, String expected, String observed) {
        if (observed.toLowerCase().equals("ignore")) {
            System.out.printf("ok %s ::: observed = ignore\n", label);
            return 0;
        }
        if (expected.equals(observed)) {
            System.out.printf("ok %s ::: expected = observed = %s\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %s, observed = %s\n", label, expected, observed);
        System.out.print(HexDump.dumpBytes("expected String", expected.getBytes(), expected.length(), HexDump.COLUMN_SIZE));
        System.out.print(HexDump.dumpBytes("observed String", observed.getBytes(), observed.length(), HexDump.COLUMN_SIZE));
        return 1;
    }
    
    private static String rptBigDecimal(BigDecimal arg) {
        int prec = arg.precision();
        int scale = arg.scale();
        BigInteger bi = arg.unscaledValue();
        long bigInt = bi.longValue();
        return String.format("bigInt=%d, prec=%d, scale=%d", bigInt, prec, scale);
        
    }

    public static int withinTolerance(String label,
                                      BigDecimal expected,
                                      BigDecimal observed,
                                      double maxPercentDouble) {

        BigDecimal maxPercent = BigDecimal.valueOf(maxPercentDouble);
        return withinTolerance(label, expected, observed, maxPercent);
    }

    public static int withinTolerance(String label,
                                      BigDecimal expected,
                                      BigDecimal observed,
                                      BigDecimal maxPercent) {

        // |expected - observed|
        BigDecimal absDiff = expected.subtract(observed).abs();

        // Handle case where expected is very small
        if (expected.abs().compareTo(maxPercent) < 0) {
            if (absDiff.compareTo(maxPercent) <= 0) {   // changed < to <=
                System.out.printf("ok withinTolerance(%s) %s ::: ok expected = %s, observed = %s, absDiff = %s%n",
                        maxPercent.toPlainString(), label, expected.toPlainString(), observed.toPlainString(), absDiff.toPlainString());
                return 0;
            } else {
                System.out.printf("*** DISCREPANCY withinTolerance(%s) ::: %s expected = %s, observed = %s, absDiff = %s%n",
                        maxPercent.toPlainString(), label, expected.toPlainString(), observed.toPlainString(), absDiff.toPlainString());
                return 1;
            }
        }

        // relative difference = |expected - observed| / |expected|
        BigDecimal relDiff = absDiff.divide(expected.abs(),
                                         Math.max(18, expected.scale() + observed.scale()),
                                         RoundingMode.HALF_UP);

        if (relDiff.compareTo(maxPercent) <= 0) {   // changed < to <=
            System.out.printf("ok withinTolerance(%s) %s ::: expected = %s, observed = %s, relDiff = %s%n",
                    maxPercent.toPlainString(), label, expected.toPlainString(), observed.toPlainString(), relDiff.toPlainString());
            return 0;
        } else {
            System.out.printf("*** DISCREPANCY withinTolerance(%s) %s ::: expected = %s, observed = %s, relDiff = %s%n",
                    maxPercent.toPlainString(), label, expected.toPlainString(), observed.toPlainString(), relDiff.toPlainString());
            return 1;
        }
    }

    public static int checker(String label, BigInteger expected, BigInteger observed) {
        if (expected.equals(observed)) {
            System.out.printf("ok %s ::: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %d, observed = %s\n", label, expected, observed);
        return 1;
    }
    
    public static int checkArraysEqual(String label, Object src, Object dst) {
        boolean ok;
        if (src instanceof Object[] && dst instanceof Object[]) {
            ok = Arrays.deepEquals((Object[]) src, (Object[]) dst);
        } else {
            ok = Arrays.deepEquals(new Object[]{src}, new Object[]{dst});
        }
        if (ok) {
            System.out.printf("ok checkArraysEqual(%s) ::: dst = src\n", label);
            return 0;
        } else {
            System.out.printf("*** DISCREPANCY detected in checkArraysEqual(%s) ::: dst != src\n", label);
            return 1;
        }
    }


    public static void theEnd(int errorCount) {
        if (errorCount == 0) {
            System.out.println("\n========");
            System.out.println("Success!");
            System.out.println("========");
            System.exit(0);
        }
        String errMsg = String.format("*** Test case diagnosed %d error(s)", errorCount);
        throw new AssertionError(errMsg);
    }

}
