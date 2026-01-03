import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Checkers {

    public static double MAX_PERCENT = 0.0001;
    public static double MAX_PERCENT_F = 0.0001f;
    
    // ===== boolean =====

    public static int checker(String label, boolean expected, boolean observed) {
        if (expected == observed) {
            System.out.printf("ok %s ::: expected = observed = %b\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %b, observed = %b\n", label, expected, observed);
        return 1;
    }

    // ===== short =====

    public static int checker(String label, short expected, short observed) {
        if (expected == observed) {
            System.out.printf("ok %s ::: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %d, observed = %d\n", label, expected, observed);
        return 1;
    }

    // ===== int =====

    public static int checker(String label, int expected, int observed) {
        if (expected == observed) {
            System.out.printf("ok %s ::: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %d, observed = %d\n", label, expected, observed);
        return 1;
    }

    // ===== long =====

    public static int checker(String label, long expected, long observed) {
        if (expected == observed) {
            System.out.printf("ok %s ::: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %d, observed = %d\n", label, expected, observed);
        return 1;
    }

    // ===== long array =====

    public static int checker(String label, long[] expected, long[] observed) {
        int errorCount = 0;
        if (observed.length != expected.length) {
            System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected length = %d, observed length = %d\n", label, expected.length, observed.length);
            return 1;
        }
        for (int ix = 0; ix < expected.length; ++ix) {
            if (expected[ix] != observed[ix]) {
                System.out.printf("*** DISCREPANCY at index %d detected in checker(%s) ::: expected = %d, observed = %d\n", ix, label, expected[ix], observed[ix]);
                return 1;
            }
        }
        System.out.printf("ok %s ::: expected = observed\n", label);
        return 0;
    }

    // ===== String =====

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
    
    // ===== BigInteger =====

    public static int checker(String label, BigInteger expected, BigInteger observed) {
        if (expected.equals(observed)) {
            System.out.printf("ok %s ::: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %d, observed = %s\n", label, expected, observed);
        return 1;
    }
    
    // ===== Object =====

    public static int checker(String label, Object expected, Object observed) {
        // Handle nulls explicitly
        if (expected == null && observed == null) {
            System.out.printf("ok %s ::: expected = observed = null\n", label);
            return 0;
        }
        if (expected == null) {
            System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = null, observed = %s\n",
                    label, String.valueOf(observed));
            return 1;
        }
        if (observed == null) {
            System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected = %s, observed = null\n",
                    label, String.valueOf(expected));
            return 1;
        }

        boolean match = false;
        String expectedStr = String.valueOf(expected);
        String observedStr = String.valueOf(observed);
        String expectedType = "Object";
        String observedType = "Object";

        // Infer types for better reporting
        if (expected instanceof Number) {
            expectedType = expected instanceof Double || expected instanceof Float ? "double" : "long";
        } else if (expected instanceof Boolean) {
            expectedType = "boolean";
        }

        if (observed instanceof Number) {
            observedType = observed instanceof Double || observed instanceof Float ? "double" : "long";
        } else if (observed instanceof Boolean) {
            observedType = "boolean";
        }

        // Compare numbers specially
        if (expected instanceof Number && observed instanceof Number) {
            if (expected instanceof Double || expected instanceof Float ||
                observed instanceof Double || observed instanceof Float) {
                double e = ((Number) expected).doubleValue();
                double o = ((Number) observed).doubleValue();
                match = Double.compare(e, o) == 0;
                expectedStr = String.format("%e", e);
                observedStr = String.format("%e", o);
            } else {
                long e = ((Number) expected).longValue();
                long o = ((Number) observed).longValue();
                match = e == o;
                expectedStr = Long.toString(e);
                observedStr = Long.toString(o);
            }
        } else {
            if (expected instanceof Boolean && expected instanceof Boolean) {
                boolean expValue = ((Boolean) expected).booleanValue();
                boolean obsValue = ((Boolean) observed).booleanValue();
                if (expValue == obsValue) {
                    System.out.printf("ok %s ::: expected = observed = %b\n", label, obsValue);
                    return 0;
                }
                System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected (%b) = %s, observed (%b) = %s\n", label, expValue, obsValue);
                return 1;
            }
        }

        if (match) {
            System.out.printf("ok %s ::: expected = observed = %s\n", label, observedStr);
            return 0;
        } 
        System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected (%s) = %s, observed (%s) = %s\n",
                    label, expectedType, expectedStr, observedType, observedStr);
        return 1;
   }
    
    // ===== Unconditional fail, pass =====

    public static int fail(String label) {
        System.out.printf("*** DISCREPANCY %s\n", label);
        return 1;
    }

    public static void pass(String label) {
        System.out.printf("ok %s\n", label);
    }

    // ===== withinTolerance =====

    public static int withinTolerance(String label, long expected, long observed) {
        return withinTolerance(label, expected, observed, MAX_PERCENT);
    }
    
    public static int withinTolerance(String label, long expected, long observed, long tol) {
        long diff = Math.abs(expected - observed);
        if (diff <= tol) {
            System.out.printf("ok %s within tolerance%n", label);
            return 0;
        }

        double diffPct = expected != 0 ? (double) diff / Math.abs(expected) : 0.0;

        System.out.printf(
            "*** DISCREPANCY detected in withinTolerance(%s) ::: expected = %d, observed = %d, diff = %d, diffPct = %.6e%n",
            label, expected, observed, diff, diffPct
        );

        return 1;
    }

    public static int withinTolerance(String label, double expected, double observed) {
        return withinTolerance(label, expected, observed, MAX_PERCENT);
    }
    
    public static int withinTolerance(String label,
                                      double expected,
                                      double observed,
                                      double tol) {

        if (tol < 0.0) {
            System.out.printf(
                "*** ERROR, test label %s passed a negative tolerance (%g)%n",
                label, tol
            );
            return 1;
        }

        // Handle NaN exactly like Java comparisons
        if (Double.isNaN(expected) || Double.isNaN(observed)) {
            if (Double.isNaN(expected) && Double.isNaN(observed)) {
                System.out.printf("ok %s ::: both values are NaN%n", label);
                return 0;
            }
            System.out.printf(
                "*** DISCREPANCY detected in withinTolerance(%s) ::: expected = %s, observed = %s%n",
                label, expected, observed
            );
            return 1;
        }

        // Handle infinities
        if (Double.isInfinite(expected) || Double.isInfinite(observed)) {
            if (expected == observed) {
                System.out.printf("ok %s ::: both values are %s%n", label, expected);
                return 0;
            }
            System.out.printf(
                "*** DISCREPANCY detected in withinTolerance(%s) ::: expected = %s, observed = %s%n",
                label, expected, observed
            );
            return 1;
        }

        double diff = Math.abs(expected - observed);

        // Absolute tolerance for near-zero values
        if (Math.abs(expected) < tol) {
            if (diff <= tol) {
                System.out.printf("ok %s ::: within absolute tolerance%n", label);
                return 0;
            }
        } else {
            // Relative tolerance otherwise
            if (diff <= tol * Math.abs(expected)) {
                System.out.printf("ok %s ::: within relative tolerance%n", label);
                return 0;
            }
        }

        // Diagnostics (truthful and readable)
        int sig = Math.max(6, (int) Math.ceil(-Math.log10(tol)) + 1);
        String fmt = "%." + sig + "g";

        double relDiff = diff / Math.abs(expected);

        System.out.printf(
            "*** DISCREPANCY detected in withinTolerance(%s) ::: expected = " + fmt +
            ", observed = " + fmt +
            ", absDiff = " + fmt +
            ", relDiff = " + fmt + "%n",
            label,
            expected,
            observed,
            diff,
            relDiff
        );

        return 1;
    }

    public static int withinTolerance(String label, float expected, float observed) {
        return withinTolerance(label, expected, observed, MAX_PERCENT_F);
    }
    
    public static int withinTolerance(String label, float expected, float observed, float tol) {

        if (tol < 0.0f) {
            System.out.printf("*** ERROR, test label %s passed a negative error tolerance (%f)\n", label, tol);
            return 1;
        }
        
        float diff = Math.abs(expected - observed);
        if (diff <= tol) {
            System.out.printf("ok %s within tolerance%n", label);
            return 0;
        }

        // Compute number of decimal digits based on tolerance
        int digits = (int) Math.ceil(-Math.log10(tol)) + 1;
        if (digits < 6) digits = 6;  // minimum digits for clarity
        String format = "%." + digits + "e";

        float diffPct = expected != 0 ? diff / Math.abs(expected) : 0;

        System.out.printf(
            "*** DISCREPANCY detected in withinTolerance(%s) ::: expected = " + format +
            ", observed = " + format + ", diffPct = " + format + "%n",
            label, expected, observed, diffPct
        );

        return 1;
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

    // ===== Report the contents of a BigDecimal =====

    private static String rptBigDecimal(BigDecimal arg) {
        int prec = arg.precision();
        int scale = arg.scale();
        BigInteger bi = arg.unscaledValue();
        long bigInt = bi.longValue();
        return String.format("bigInt=%d, prec=%d, scale=%d", bigInt, prec, scale);
        
    }


    // ===== Windows? =====
    public boolean IsWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("windows");
    }

    // ===== That's all, folks! =====

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
