import java.math.BigDecimal;
import java.math.BigInteger;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        String str;

        BigDecimal dd = new BigDecimal("123.45");
        BigDecimal ddNeg = new BigDecimal("-123.45");
        BigInteger ii = new BigInteger("12345");
        BigInteger iiNeg = new BigInteger("-12345");
        
        // BigInteger Hashcode (%h|%H)
        str = String.format("%h", dd);
        errorCount += Checkers.checker("%h  BigInteger", "5d6e9", str);
        str = String.format("%H", ddNeg);
        errorCount += Checkers.checker("%H  BigInteger-neg", "FFFA291B", str);
        
        // BigInteger decimal int (%d)
        str = String.format("%d", ii);
        errorCount += Checkers.checker("%d   BigInteger", "12345", str);       
        str = String.format("%d", iiNeg);
        errorCount += Checkers.checker("%d   BigInteger-neg", "-12345", str);       
        
        // BigInteger octal int (%o)
        str = String.format("%o", ii);
        errorCount += Checkers.checker("%o   BigInteger", "30071", str);       
        str = String.format("%o", iiNeg);
        errorCount += Checkers.checker("%o   BigInteger-neg", "-30071", str);       
        
        // BigInteger hexidecimal in (%x)
        str = String.format("%06x", ii);
        errorCount += Checkers.checker("%06x   BigInteger", "003039", str);       
        str = String.format("%10X", iiNeg);
        errorCount += Checkers.checker("%10X   BigInteger-neg", "     -3039", str);       
        str = String.format("%010X", iiNeg);
        errorCount += Checkers.checker("%010X   BigInteger-neg", "-000003039", str);       
        
        // BigDecimal Hashcode (%h|%H)
        str = String.format("%h", dd);
        errorCount += Checkers.checker("%h  BigDecimal", "5d6e9", str);
        str = String.format("%H", ddNeg);
        errorCount += Checkers.checker("%H  BigDecimal-neg", "FFFA291B", str);
        
        // BigDecimal (%e|%E|%f)
        str = String.format("%18.6e", dd);
        errorCount += Checkers.checker("%18.6e  BigDecimal", "      1.234500e+02", str);
        str = String.format("%18.6E", dd);
        errorCount += Checkers.checker("%18.6E  BigDecimal", "      1.234500E+02", str);
        str = String.format("%18.6f", dd);
        errorCount += Checkers.checker("%18.6f  BigDecimal", "        123.450000", str);
        
        Checkers.theEnd(errorCount);
    }
}

