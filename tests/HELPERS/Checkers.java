import java.math.BigDecimal;
import java.math.BigInteger;

public class Checkers {

    public static double MAX_PERCENT = 0.0001;

    public static int withinTolerance(String label, long expected, long observed, double maxPercent) {
        if (expected == 0) {
            if (observed == 0) {
                System.out.printf("ok withinTolerance(%e) %s, expected = observed = 0\n", maxPercent, label);
                return 0;
            } else {
                System.out.printf("*** DISCREPANCY withinTolerance(%e) %s, expected = 0, observed = %d\n", maxPercent, label, observed);
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
            System.out.printf("*** DISCREPANCY withinTolerance(%e) %s, expected = %d, observed = %d, diffPct = %e\n", maxPercent, label, expected, observed, diffPct);
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
                System.out.printf("*** DISCREPANCY withinTolerance(%e) %s, expected = %e, observed = %e\n", maxPercent, label, expected, observed);
                return 1;
            }
        }
        double diffPct = Math.abs(100.0 * diff / expected);
        double tolerance = Math.abs(expected) * (maxPercent / 100.0);
        if (diff <= tolerance) {
            System.out.printf("ok withinTolerance(%e) %s ok expected = %e, observed = %e, diffPct = %e\n", maxPercent, label, expected, observed, diffPct);
            return 0;
        } else {
            System.out.printf("*** DISCREPANCY withinTolerance(%e) %s, expected = %e, observed = %e, diffPct = %e\n", maxPercent, label, expected, observed, diffPct);
            return 1;
        }
    }

    public static int checker(String label, boolean expected, boolean observed) {
        if (expected == observed) {
            System.out.printf("ok %s: expected = observed = %b\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY %s: expected = %b, observed = %b\n", label, expected, observed);
        return 1;
    }

    public static int checker(String label, int expected, int observed) {
        if (expected == observed) {
            System.out.printf("ok %s: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY %s: expected = %d, observed = %d\n", label, expected, observed);
        return 1;
    }

    public static int checker(String label, long expected, long observed) {
        if (expected == observed) {
            System.out.printf("ok %s: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY %s: expected = %d, observed = %d\n", label, expected, observed);
        return 1;
    }

    public static int checker(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("ok %s: expected = observed = %s\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY %s: expected = %s, observed = %s\n", label, expected, observed);
        System.out.print(HexDump.dumpBytes("expected bytes", expected.getBytes(), expected.length(), HexDump.COLUMN_SIZE));
        System.out.print(HexDump.dumpBytes("observed bytes", observed.getBytes(), observed.length(), HexDump.COLUMN_SIZE));
        return 1;
    }
    
    private static String rptBigDecimal(BigDecimal arg) {
        int prec = arg.precision();
        int scale = arg.scale();
        BigInteger bi = arg.unscaledValue();
        long bigInt = bi.longValue();
        return String.format("bigInt=%d, prec=%d, scale=%d", bigInt, prec, scale);
        
    }

    public static int checker(String label, BigDecimal expected, BigDecimal observed) {
        if (expected.equals(observed)) {
            System.out.printf("ok %s: %s\n", label, rptBigDecimal(observed));
            return 0;
        }
        System.out.printf("*** DISCREPANCY %s: expected(", label);
        System.out.print(rptBigDecimal(expected));
        System.out.print(") != observed(");
        System.out.print(rptBigDecimal(observed));
        System.out.println(")");
        return 1;
    }


    public static int checker(String label, BigInteger expected, BigInteger observed) {
        if (expected.equals(observed)) {
            System.out.printf("ok %s: expected = observed = %d\n", label, observed);
            return 0;
        }
        System.out.printf("*** DISCREPANCY %s: expected = %d, observed = %s\n", label, expected, observed);
        return 1;
    }
    
    public static void theEnd(int errorCount) {
        assert errorCount == 0;
        System.out.println("Success!");
        System.exit(0);
    }

}
