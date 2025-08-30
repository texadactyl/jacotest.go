import java.math.BigDecimal;
import java.math.BigInteger;

public class main {

    private static int checker(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("ok, %s: expected(%s) = observed(%s)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("*** ERROR, %s: expected(%s) != observed(%s)\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        String str;

        BigDecimal dd = new BigDecimal("123.45");
        BigDecimal ddNeg = new BigDecimal("-123.45");
        BigInteger ii = new BigInteger("12345");
        BigInteger iiNeg = new BigInteger("-12345");
        
        // BigInteger Hashcode (%h|%H)
        str = String.format("%h", dd);
        errorCount += checker("%h  BigInteger", "5d6e9", str);
        str = String.format("%H", ddNeg);
        errorCount += checker("%H  BigInteger-neg", "FFFA291B", str);
        
        // BigInteger decimal int (%d)
        str = String.format("%d", ii);
        errorCount += checker("%d   BigInteger", "12345", str);       
        str = String.format("%d", iiNeg);
        errorCount += checker("%d   BigInteger-neg", "-12345", str);       
        
        // BigInteger octal int (%o)
        str = String.format("%o", ii);
        errorCount += checker("%o   BigInteger", "30071", str);       
        str = String.format("%o", iiNeg);
        errorCount += checker("%o   BigInteger-neg", "-30071", str);       
        
        // BigInteger hexidecimal in (%x)
        str = String.format("%06x", ii);
        errorCount += checker("%06x   BigInteger", "003039", str);       
        str = String.format("%10X", iiNeg);
        errorCount += checker("%10X   BigInteger-neg", "     -3039", str);       
        str = String.format("%010X", iiNeg);
        errorCount += checker("%010X   BigInteger-neg", "-000003039", str);       
        
        // BigDecimal Hashcode (%h|%H)
        str = String.format("%h", dd);
        errorCount += checker("%h  BigDecimal", "5d6e9", str);
        str = String.format("%H", ddNeg);
        errorCount += checker("%H  BigDecimal-neg", "FFFA291B", str);
        
        // BigDecimal (%e|%E|%f)
        str = String.format("%18.6e", dd);
        errorCount += checker("%18.6e  BigDecimal", "      1.234500e+02", str);
        str = String.format("%18.6E", dd);
        errorCount += checker("%18.6E  BigDecimal", "      1.234500E+02", str);
        str = String.format("%18.6f", dd);
        errorCount += checker("%18.6f  BigDecimal", "        123.450000", str);
        
        assert(errorCount == 0);
        System.out.println("Success!");
    }
}

