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
            match = expected.equals(observed);
        }

        if (match) {
            System.out.printf("ok %s ::: expected = observed = %s\n", label, observedStr);
            return 0;
        } else {
            System.out.printf("*** DISCREPANCY detected in checker(%s) ::: expected (%s) = %s, observed (%s) = %s\n",
                    label, expectedType, expectedStr, observedType, observedStr);
            return 1;
        }
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
    
    public static int withinTolerance(String testName, long expected, long observed, long tol) {
        long diff = Math.abs(expected - observed);
        if (diff <= tol) {
            System.out.printf("ok %s within tolerance%n", testName);
            return 0;
        }

        double diffPct = expected != 0 ? (double) diff / Math.abs(expected) : 0.0;

        System.out.printf(
            "*** DISCREPANCY detected in withinTolerance(%s) ::: expected = %d, observed = %d, diff = %d, diffPct = %.6e%n",
            testName, expected, observed, diff, diffPct
        );

        return 1;
    }

    public static int withinTolerance(String label, double expected, double observed) {
        return withinTolerance(label, expected, observed, MAX_PERCENT);
    }
    
    public static int withinTolerance(String testName, double expected, double observed, double tol) {
        double diff = Math.abs(expected - observed);
        if (diff <= tol) {
            System.out.printf("ok %s within tolerance%n", testName);
            return 0;
        }

        // Compute number of decimal digits based on tolerance
        int digits = (int) Math.ceil(-Math.log10(tol)) + 1;
        if (digits < 6) digits = 6;  // minimum digits for clarity
        String format = "%." + digits + "e";

        double diffPct = expected != 0 ? diff / Math.abs(expected) : 0;

        System.out.printf(
            "*** DISCREPANCY detected in withinTolerance(%s) ::: expected = " + format +
            ", observed = " + format + ", diffPct = " + format + "%n",
            testName, expected, observed, diffPct
        );

        return 1;
    }

    public static int withinTolerance(String label, float expected, float observed) {
        return withinTolerance(label, expected, observed, MAX_PERCENT_F);
    }
    
    public static int withinTolerance(String testName, float expected, float observed, float tol) {
        float diff = Math.abs(expected - observed);
        if (diff <= tol) {
            System.out.printf("ok %s within tolerance%n", testName);
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
            testName, expected, observed, diffPct
        );

        return 1;
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
