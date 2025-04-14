import java.math.BigDecimal;
import java.math.BigInteger;

public class main {

    static int errorCounter = 0;

    public static void main(String[] args) {

        // Test negate()
        check("negate()", new BigDecimal("-5"), new BigDecimal("5").negate());
        check("negate()", new BigDecimal("-10"), new BigDecimal("10").negate());

        // Test plus()
        check("plus()", new BigDecimal("-5"), new BigDecimal("-5").plus());
        check("plus()", new BigDecimal("10"), new BigDecimal("10").plus());

        // Test pow()
        check("pow(3)", new BigDecimal("125"), new BigDecimal("5").pow(3));
        check("pow(2)", new BigDecimal("25"), new BigDecimal("5").pow(2));

        // Test precision()
        check("precision()", 1, new BigDecimal("5").precision());
        check("precision()", 3, new BigDecimal("123").precision());

        // Test remainder()
        check("remainder()", new BigDecimal("1"), new BigDecimal("10").remainder(new BigDecimal("3")));
        check("remainder()", new BigDecimal("2"), new BigDecimal("7").remainder(new BigDecimal("5")));

        // Test scale()
        check("scale()", 0, new BigDecimal("5").scale());
        check("scale()", 2, new BigDecimal("5.00").scale());

        // Test scaleByPowerOfTen()
        check("scaleByPowerOfTen(3)", new BigDecimal("5E+3"), new BigDecimal("5").scaleByPowerOfTen(3));
        check("scaleByPowerOfTen(-3)", new BigDecimal("0.005"), new BigDecimal("5").scaleByPowerOfTen(-3));

        // Test setScale()
        check("setScale(2)", new BigDecimal("5.00"), new BigDecimal("5").setScale(2));
        check("setScale(1)", new BigDecimal("5.0"), new BigDecimal("5").setScale(1));

        // Test shortValueExact()
        check("shortValueExact()", (short) 5, new BigDecimal("5").shortValueExact());
        check("shortValueExact()", (short) 10, new BigDecimal("10").shortValueExact());

        // Test signum()
        check("signum()", 1, new BigDecimal("5").signum());
        check("signum()", 0, new BigDecimal("0").signum());

        // Test stripTrailingZeros()
        check("stripTrailingZeros()", new BigDecimal("5"), new BigDecimal("5.000").stripTrailingZeros());
        check("stripTrailingZeros()", new BigDecimal("5"), new BigDecimal("5").stripTrailingZeros());
        check("stripTrailingZeros()", new BigDecimal("3.1416"), new BigDecimal("0.31416e+1").stripTrailingZeros());
        check("stripTrailingZeros()", new BigDecimal("-3.1416"), new BigDecimal("-3141600e-6").stripTrailingZeros());
        check("stripTrailingZeros()", new BigDecimal("3.1416"), new BigDecimal("3141600e-6").stripTrailingZeros());

        // Test subtract()
        check("subtract()", new BigDecimal("2"), new BigDecimal("5").subtract(new BigDecimal("3")));
        check("subtract()", new BigDecimal("1"), new BigDecimal("5").subtract(new BigDecimal("4")));

        // Test toBigInteger()
        check("toBigInteger()", new BigInteger("5"), new BigDecimal("5").toBigInteger());
        check("toBigInteger()", new BigInteger("123456789"), new BigDecimal("123456789").toBigInteger());

        // Test toBigIntegerExact()
        check("toBigIntegerExact()", new BigInteger("5"), new BigDecimal("5").toBigIntegerExact());
        check("toBigIntegerExact()", new BigInteger("1000"), new BigDecimal("1000").toBigIntegerExact());

        // Test toEngineeringString()
        check("toEngineeringString()", "5000", new BigDecimal("5000").toEngineeringString());
        check("toEngineeringString()", "0.0005", new BigDecimal("0.0005").toEngineeringString());

        // Test toPlainString()
        check("toPlainString()", "5", new BigDecimal("5").toPlainString());
        check("toPlainString()", "123456789", new BigDecimal("123456789").toPlainString());

        // Test toString()
        check("toString()", "5", new BigDecimal("5").toString());
        check("toString()", "123.456", new BigDecimal("123.456").toString());

        // Test unscaledValue()
        check("unscaledValue()", new BigInteger("5"), new BigDecimal("5").unscaledValue());
        check("unscaledValue()", new BigInteger("123456789"), new BigDecimal("123456789").unscaledValue());

        // Test valueOf(Double)
        check("valueOfDouble()", new BigDecimal("5.0"), BigDecimal.valueOf(5.0));
        check("valueOfDouble()", new BigDecimal("5.123"), BigDecimal.valueOf(5.123));
        check("valueOfDouble()", new BigDecimal("-5.0"), BigDecimal.valueOf(-5.0));
        check("valueOfDouble()", new BigDecimal("-5.123"), BigDecimal.valueOf(-5.123));

        // Test valueOf(Long)
        check("valueOfLong()", new BigDecimal("5"), BigDecimal.valueOf(5L));
        check("valueOfLong()", new BigDecimal("123456789"), BigDecimal.valueOf(123456789L));

        // Test valueOf(Long, Integer)
        check("valueOfLongInt()", new BigDecimal("5"), BigDecimal.valueOf(5L, 0));
        check("valueOfLongInt()", new BigDecimal("12345"), BigDecimal.valueOf(12345L, 0));

        // Final assertion for errorCounter
        if (errorCounter > 0)
            System.out.printf("Error count = %d\n", errorCounter);
        assert errorCounter == 0;
        System.out.println("Success!");
    }

    // Helper methods for comparing expected and observed values

    static String rptBigDecimal(BigDecimal arg) {
        double dbl = arg.doubleValue();
        int prec = arg.precision();
        int scale = arg.scale();
        BigInteger bi = arg.unscaledValue();
        long bigInt = bi.longValue();
        return String.format("double=%f, bigInt=%d, prec=%d, scale=%d", dbl,bigInt, prec, scale);
    }

    static void check(String test, BigDecimal expected, BigDecimal observed) {
        double dblExpected = expected.doubleValue();
        double dblObserved = observed.doubleValue();
        if (dblExpected == dblObserved) {
            System.out.printf("%s OK %s\n", test, rptBigDecimal(expected));
        } else {
            System.out.printf("%s *** ERROR", test);
            System.out.printf(", Expected: %s", rptBigDecimal(expected));
            System.out.printf(" ----- Observed: %s\n", rptBigDecimal(observed));
            errorCounter++;
        }
    }

    static void check(String test, BigInteger expected, BigInteger observed) {
        if (expected.equals(observed)) {
            System.out.printf("%s OK\n", test);
        } else {
            System.out.printf("%s *** ERROR", test);
            System.out.printf(", Expected: %s", expected.toString());
            System.out.printf(", Observed: %s\n", observed.toString());
            errorCounter++;
        }
    }

    static void check(String test, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("%s OK\n", test);
        } else {
            System.out.printf("%s *** ERROR", test);
            System.out.printf(", Expected: %s", expected);
            System.out.printf(", Observed: %s\n", observed);
            errorCounter++;
        }
    }

    static void check(String test, byte expected, byte observed) {
        if (expected == observed) {
            System.out.printf("%s OK\n", test);
        } else {
            System.out.printf("%s *** ERROR", test);
            System.out.printf(", Expected: 0x%02x", expected);
            System.out.printf(", Observed: 0x%02x\n", observed);
            errorCounter++;
        }
    }

    static void check(String test, int expected, int observed) {
        if (expected == observed) {
            System.out.printf("%s OK\n", test);
        } else {
            System.out.printf("%s *** ERROR", test);
            System.out.printf(", Expected: %d", expected);
            System.out.printf(", Observed: %d\n", observed);
            errorCounter++;
        }
    }

    static void check(String test, double expected, double observed) {
        if (expected == observed) {
            System.out.printf("%s OK\n", test);
        } else {
            System.out.printf("%s *** ERROR", test);
            System.out.printf(", Expected: %g", expected);
            System.out.printf(", Observed: %g\n", observed);
            errorCounter++;
        }
    }

    static void check(String test, boolean expected, boolean observed) {
        if (expected == observed) {
            System.out.printf("%s OK\n", test);
        } else {
            System.out.printf("%s *** ERROR", test);
            System.out.print(", Expected: ");
            System.out.print(expected);
            System.out.print(", Observed: ");
            System.out.println(observed);
            errorCounter++;
        }
    }

}

