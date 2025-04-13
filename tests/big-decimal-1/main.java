import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class main {
    static int errorCounter = 0;

    public static void main(String[] args) {
        testAbs();
        testAdd();
        testByteValueExact();
        testCompareTo();
        testDivide();
        testDivideAndRemainder();
        testDivideToIntegralValue();
        testDoubleValue();
        testEquals();
        testFloatValue();
        testIntValue();
        testIntValueExact();
        testLongValue();
        testLongValueExact();
        testMax();
        testMin();
        testMovePointLeft();
        testMovePointRight();
        testMultiply();

        if (errorCounter > 0)
            System.out.printf("Error count = %d\n", errorCounter);
        assert errorCounter == 0;
        System.out.println("Success!");
    }

    static void check(String test, BigDecimal expected, BigDecimal observed) {
        if (expected.equals(observed)) {
            System.out.printf("%s OK\n", test);
        } else {
            System.out.printf("%s *** ERROR", test);
            System.out.printf(", Expected: %s", expected.toString());
            System.out.printf(", Observed: %s\n", observed.toString());
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

    static void testAbs() {
        check("abs(-5)", new BigDecimal("5"), new BigDecimal("-5").abs());
        check("abs(3)", new BigDecimal("3"), new BigDecimal("3").abs());
    }

    static void testAdd() {
        check("add(2+3)", new BigDecimal("5"), new BigDecimal("2").add(new BigDecimal("3")));
        check("add(-1+4)", new BigDecimal("3"), new BigDecimal("-1").add(new BigDecimal("4")));
    }

    static void testByteValueExact() {
        check("byteValueExact(127)", (byte)127, new BigDecimal("127").byteValueExact());
        check("byteValueExact(-128)", (byte)-128, new BigDecimal("-128").byteValueExact());
        try {
            check("byteValueExact(-129)", (byte)-129, new BigDecimal("-129").byteValueExact());
            errorCounter++;
            System.out.println("byteValueExact(-129): Failed to catch expected arithmetic exception  *** ERROR");
        } catch(ArithmeticException ex) {
            System.out.println("byteValueExact(-129): Caught expected arithmetic exception  OK");
        }
    }

    static void testCompareTo() {
        check("compareTo(3, 4)=-1", -1, new BigDecimal("3").compareTo(new BigDecimal("4")));
        check("compareTo(4, 4)=0", 0, new BigDecimal("4").compareTo(new BigDecimal("4")));
        check("compareTo(4, 3)=+1", 1, new BigDecimal("4").compareTo(new BigDecimal("3")));
    }

    static void testDivide() {
        check("divide(10/2)", new BigDecimal("5"), new BigDecimal("10").divide(new BigDecimal("2")));
        check("divide(6/3)", new BigDecimal("2"), new BigDecimal("6").divide(new BigDecimal("3")));
    }

    static void testDivideAndRemainder() {
        BigDecimal[] result1 = new BigDecimal("10").divideAndRemainder(new BigDecimal("3"));
        check("divideAndRemainder(10/3)[0]", new BigDecimal("3"), result1[0]);
        check("divideAndRemainder(10/3)[1]", new BigDecimal("1"), result1[1]);
        BigDecimal[] result2 = new BigDecimal("9").divideAndRemainder(new BigDecimal("2"));
        check("divideAndRemainder(9/2)[0]", new BigDecimal("4"), result2[0]);
        check("divideAndRemainder(9/2)[1]", new BigDecimal("1"), result2[1]);
    }

    static void testDivideToIntegralValue() {
        check("divideToIntegralValue(10/3)", new BigDecimal("3"), new BigDecimal("10").divideToIntegralValue(new BigDecimal("3")));
        check("divideToIntegralValue(9/2)", new BigDecimal("4"), new BigDecimal("9").divideToIntegralValue(new BigDecimal("2")));
    }

    static void testDoubleValue() {
        check("doubleValue(3.14)", 3.14, new BigDecimal("3.14").doubleValue());
        check("doubleValue(2.5)", 2.5, new BigDecimal("2.5").doubleValue());
    }

    static void testEquals() {
        check("equals(3.0 == 3.00)", false, new BigDecimal("3.0").equals(new BigDecimal("3.00")));
        check("equals(3.0 == 3.0)", true, new BigDecimal("3.0").equals(new BigDecimal("3.0")));
        check("equals(3.0 == 4.0)", false, new BigDecimal("3.0").equals(new BigDecimal("4.0")));
    }

    static void testFloatValue() {
        check("floatValue(1.23f)", 1.23f, new BigDecimal("1.23").floatValue());
        check("floatValue(0.5f)", 0.5f, new BigDecimal("0.5").floatValue());
    }

    static void testIntValue() {
        check("intValue(123)", 123, new BigDecimal("123").intValue());
        check("intValue(-45)", -45, new BigDecimal("-45").intValue());
    }

    static void testIntValueExact() {
        check("intValueExact(123)", 123, new BigDecimal("123").intValueExact());
        check("intValueExact(-1)", -1, new BigDecimal("-1").intValueExact());
    }

    static void testLongValue() {
        check("longValue(9876543210)", 9876543210L, new BigDecimal("9876543210").longValue());
        check("longValue(-50000)", -50000L, new BigDecimal("-50000").longValue());
    }

    static void testLongValueExact() {
        check("longValueExact(9876543210)", 9876543210L, new BigDecimal("9876543210").longValueExact());
        check("longValueExact(-1)", -1L, new BigDecimal("-1").longValueExact());
    }

    static void testMax() {
        check("max(3,4)", new BigDecimal("4"), new BigDecimal("3").max(new BigDecimal("4")));
        check("max(5,2)", new BigDecimal("5"), new BigDecimal("5").max(new BigDecimal("2")));
    }

    static void testMin() {
        check("min(3,4)", new BigDecimal("3"), new BigDecimal("3").min(new BigDecimal("4")));
        check("min(1,2)", new BigDecimal("1"), new BigDecimal("1").min(new BigDecimal("2")));
    }

    static void testMovePointLeft() {
        check("movePointLeft(5, 3)", new BigDecimal("0.005"), new BigDecimal("5").movePointLeft(3));
        check("movePointLeft(123, 1)", new BigDecimal("12.3"), new BigDecimal("123").movePointLeft(1));
        check("movePointLeft(5, 3)", new BigDecimal("0.005"), new BigDecimal("5").movePointLeft(3));
    }

    static void testMovePointRight() {
        check("movePointRight(1.23, 1)", new BigDecimal("12.3"), new BigDecimal("1.23").movePointRight(1));
        check("movePointRight(1, 3)", new BigDecimal("1000"), new BigDecimal("1").movePointRight(3));
    }

    static void testMultiply() {
        check("multiply(2*3)", new BigDecimal("6"), new BigDecimal("2").multiply(new BigDecimal("3")));
        check("multiply(-1*4)", new BigDecimal("-4"), new BigDecimal("-1").multiply(new BigDecimal("4")));
    }
} 

