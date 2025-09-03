import java.math.BigDecimal;
import java.math.RoundingMode;

public class main {

    private static int errorCount = 0;
    
    private static void trial(BigDecimal dividend, BigDecimal divisor, int scale, String exp1, String exp2) {
        BigDecimal quotient = dividend.divide(divisor, scale, RoundingMode.HALF_UP);
        errorCount += Checkers.checker("dividend.divide(divisor, scale=3, RoundingMode.HALF_UP)", exp1, quotient.toPlainString());
        
        quotient = dividend.divide(divisor, RoundingMode.HALF_UP);
        errorCount += Checkers.checker("dividend.divide(divisor, <no scale>, RoundingMode.HALF_UP)", exp2, quotient.toPlainString());
    }
    
    public static void main(String[] args) { 
        BigDecimal quotient;  
        BigDecimal dividend;
        int scale = 3;

        dividend = BigDecimal.valueOf(6.0);
        trial(dividend, BigDecimal.valueOf(1.9), 3, "3.158", "3.2");        
        trial(dividend, BigDecimal.valueOf(2.1), 3, "2.857", "2.9");        
        trial(dividend, BigDecimal.valueOf(2.39), 3, "2.510", "2.5");        

        dividend = BigDecimal.valueOf(123.45678);
        trial(dividend, BigDecimal.valueOf(-1.9), 3, "-64.977", "-64.97725");        
        trial(dividend, BigDecimal.valueOf(-2.1), 3, "-58.789", "-58.78894");        
        trial(dividend, BigDecimal.valueOf(-2.39), 3, "-51.656", "-51.65556");        

        dividend = BigDecimal.valueOf(0.12345678);
        trial(dividend, BigDecimal.valueOf(1.9), 3, "0.065", "0.06497725");        
        trial(dividend, BigDecimal.valueOf(-2.1), 3, "-0.059", "-0.05878894");        
        trial(dividend, BigDecimal.valueOf(2.98765), 3, "0.041", "0.04132237");        
    }
}

