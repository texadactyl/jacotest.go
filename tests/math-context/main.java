/*
    RoundingMode values:
    
    UP(0),
    DOWN(1),
    CEILING(2),
    FLOOR(3),
    HALF_UP(4),
    HALF_DOWN(5),
    HALF_EVEN(6),
    UNNECESSARY(7);
*/

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class main {

    public static MathContext createMathContext(int precision, RoundingMode rm) {
        String mcString = String.format("precision=%d roundingMode=%s", precision, rm.name());
        return new MathContext(mcString);
    }

    public static void main(String[] args) {
        int errorCode = 0;
        
        // Choose rounding mode
        RoundingMode rm = RoundingMode.DOWN;
        int numericVal = rm.ordinal();
        System.out.printf("RoundingMode: %s%n", rm);
        System.out.printf("Numeric value: %d%n", numericVal);
        errorCode += Checkers.checker("rm.ordinal() DOWN", 1, numericVal);

        // Build MathContext
        int precision = 6;
        MathContext mc = createMathContext(precision, rm);
        System.out.printf("Created MathContext: %s%n", mc);

        // BigDecimal operation
        BigDecimal a = new BigDecimal("123.456789");
        BigDecimal b = new BigDecimal("3.14159");
        BigDecimal result1 = a.divide(b, mc);
        System.out.printf("Result using MathContext: %s%n", result1);
        errorCode += Checkers.withinTolerance("a.divide(b, mc)", BigDecimal.valueOf(39.2975), result1, 0.001);

        // Round-trip MathContext
        MathContext mcFromString = new MathContext(mc.toString());
        System.out.printf("Round-tripped MathContext: %s%n", mcFromString);

        // Verify it works the same
        BigDecimal result2 = a.divide(b, mcFromString);
        System.out.printf("Result using round-tripped MathContext: %s%n", result2);
        assert(result2.equals(result1));
        System.out.println("ok round-trip result!");
        errorCode += Checkers.withinTolerance("a.divide(b, mcFromString)", BigDecimal.valueOf(39.2975), result2, 0.001);
        
        Checkers.theEnd(errorCode);
    }
}

