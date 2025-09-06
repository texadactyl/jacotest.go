import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        BigDecimal A = new BigDecimal("963963963963963963963963963963963963963963963963963963963.963");
        BigDecimal B = new BigDecimal("2.87654876548765487654876548765487654876548765487654");
        BigDecimal C, D;
        BigDecimal array[];
        BigInteger BI;
        RoundingMode rm = RoundingMode.valueOf("HALF_UP");
        int ii;
        long jj;
        boolean zz;
        String expected;
        
        C = A.add(B);
        expected = "963963963963963963963963963963963963963963963963963963966.83954876548765487654876548765487654876548765487654";      
        errorCount += Checkers.checker("A.add(B)", expected, C.toPlainString());
        
        ii = A.compareTo(B);
        errorCount += Checkers.checker("A.compareTo(B)", 1, ii);
        ii = A.compareTo(A);
        errorCount += Checkers.checker("A.compareTo(A)", 0, ii);
        ii = B.compareTo(A);
        errorCount += Checkers.checker("B.compareTo(A)", -1, ii);
        
        C = A.divide(B, rm);
        expected = "335111288753189383117212577810800663414238150377653296938.250";      
        errorCount += Checkers.checker("A.divide(B, rm)", expected, C.toPlainString());
        
        C = A.divideToIntegralValue(B);
        expected = "335111288753189383117212577810800663414238150377653296938";      
        errorCount += Checkers.checker("A.divideToIntegralValue(B)",  expected, C.toPlainString());
        
        array = A.divideAndRemainder(B);
        String expectedQ = "335111288753189383117212577810800663414238150377653296938";      
        String expectedR = "0.71804475666634227855232831541824534553677564996548";      
        errorCount += Checkers.checker("A.divideAndRemainder(B) - quotient",  expectedQ, array[0].toPlainString());
        errorCount += Checkers.checker("A.divideAndRemainder(B) - remainder", expectedR, array[1].toPlainString());
        
        zz = A.equals(B);
        errorCount += Checkers.checker("A.equals(B)", false, zz);
        zz = A.equals(A);
        errorCount += Checkers.checker("A.equals(A)", true, zz);
        zz = B.equals(A);
        errorCount += Checkers.checker("B.equals(A)", false, zz);
        
        ii = A.intValue();
        errorCount += Checkers.checker("A.intValue()", 1818589755, ii);
        
        jj = A.longValue();
        errorCount += Checkers.checker("A.longValue()", -5163478405204313541L, jj);
        
        D = A.max(B);
        zz = D.equals(A);
        errorCount += Checkers.checker("A.max(B)", true, zz);
        
        D = A.min(B);
        zz = D.equals(A);
        errorCount += Checkers.checker("A.min(B)", false, zz);
        
        C = A.movePointLeft(5);
        expected = "9639639639639639639639639639639639639639639639639639.63963963";
        errorCount += Checkers.checker("C = A.movePointLeft(5)", expected, C.toPlainString());
        
        C = C.movePointRight(5);
        expected = "963963963963963963963963963963963963963963963963963963963.963";
        errorCount += Checkers.checker("C = C.movePointRight(5)", expected, C.toPlainString());
        
        C = A.multiply(B);
        expected = "2772889350515126772889350515126772889350515126772880900900.89812801155038577412801155038577412801155038577412802";      
        errorCount += Checkers.checker("A.multiply(B, rm)", expected, C.toPlainString());
        
        ii = B.negate().signum();
        errorCount += Checkers.checker("B.negate().signum()", -1, ii);
        
        C = B.pow(5);
        expected = "196.9512332632041425648903952282597858571550912228881576467059003975279194556093059395958737271613605818429385286431679948696259759708157256485445979026081037557688179566936520311094050547107546448817694942476182957384867462249103941371639497016916093024";      
        errorCount += Checkers.checker("A.pow(5)", expected, C.toPlainString());
        
        ii = B.precision();
        errorCount += Checkers.checker("B.precision()", 51, ii);
        
        ii = B.scale();
        errorCount += Checkers.checker("B.scale()", 50, ii);
        
        C = B.scaleByPowerOfTen(3);
        expected = "2876.54876548765487654876548765487654876548765487654";      
        errorCount += Checkers.checker("B.scaleByPowerOfTen(3)", expected, C.toPlainString());
        
        ii = B.signum();
        errorCount += Checkers.checker("B.signum()", 1, ii);
        
        C = A.subtract(B);
        expected = "963963963963963963963963963963963963963963963963963963961.08645123451234512345123451234512345123451234512346";      
        errorCount += Checkers.checker("A.subtract(B)", expected, C.toPlainString());
        
        BI = A.toBigInteger();
        expected = "963963963963963963963963963963963963963963963963963963963";      
        errorCount += Checkers.checker("A.toBigInteger()", expected, BI.toString());
        
      
        Checkers.theEnd(errorCount);
    }
}

