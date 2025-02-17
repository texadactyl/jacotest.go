import java.math.BigInteger;

public class main {

    public static String cvtLongToHex(long arg) {
        String str = Long.toHexString(arg);
        int strlen = str.length();
    	int padCount = 16 - strlen;
    	for (int ii = 0; ii < padCount; ii++) {
    		str = String.format("0%s", str);
    	}
        return str;
    }
    
    private static int checker(String label, long expected, long observed) {
        if (expected == observed) {
            System.out.printf("Success, %s: expected(%d) = observed(%d)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("*** ERROR, %s: expected(%d) != observed(%d)\n", label, expected, observed);
        return 1;
    }

    private static int checker(String label, double expected, double observed) {
        if (expected == observed) {
            System.out.printf("Success, %s: expected(%f) = observed(%f)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("*** ERROR, %s: expected(%f) != observed(%f)\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        
        int ii;
        long jj;
        BigInteger bintResult;
        BigInteger bint4 = new BigInteger("16384");
        BigInteger bintzero = new BigInteger("0");
        
        byte[] barr1 = new byte[] {0, 0, 0, 'A'};       
        BigInteger bint1 = new BigInteger(barr1);
        System.out.printf("barr1 hex: %02x%02x%02x%02x, dec: ", barr1[0], barr1[1], barr1[2], barr1[3]);    
        System.out.println(bint1);
        
        byte[] barr2 = new byte[] {(byte) 255, (byte) 255, (byte) 255, (byte) 253};
        BigInteger bint2 = new BigInteger(barr2);
        System.out.printf("barr2 hex: %02x%02x%02x%02x, dec: ", barr2[0], barr2[1], barr2[2], barr2[3]);    
        System.out.println(bint2);
        
        double dd = bint1.doubleValue();
        errorCount += checker("barr1 doubleValue", 65.0D, dd);       
        float ff = bint1.floatValue();
        errorCount += checker("barr1 floatValue", 65.0D, (double)ff);
        
        bintResult = bint1.max(bint2);
        jj = bintResult.longValue();
        errorCount += checker("barr3 doubleValue", 65L, jj);   

        bintResult = bint1.min(bint2);    
        jj = bintResult.longValue();
        errorCount += checker("barr3 doubleValue", -3L, jj);       
        
        bintResult = bint4.mod(bint1);    
        jj = bintResult.longValue();
        errorCount += checker("16384.mod(65)", 4L, jj);       
        
        bintResult = bint4.not();    
        jj = bintResult.longValue();
        errorCount += checker("16384.not()", -16385L, jj);       
        
        bintResult = bint4.or(bint1);    
        jj = bintResult.longValue();
        errorCount += checker("16384.or(65)", 16449L, jj);       
        
        bintResult = bint4.remainder(bint1);    
        jj = bintResult.longValue();
        errorCount += checker("16384.remainder(65)", 4L, jj);       
        
        ii = bint4.signum();    
        errorCount += checker("16384.signum()", 1L, (long)ii);               
        ii = bint2.signum();    
        errorCount += checker("-3.signum()", -1L, (long)ii);       
        ii = bintzero.signum();    
        errorCount += checker("0.signum()", 0L, (long)ii);       
        
        bintResult = bint1.sqrt();    
        jj = bintResult.longValue();
        errorCount += checker("65.sqrt()", 8L, jj);       
        
        bintResult = BigInteger.valueOf(42L);    
        jj = bintResult.longValue();
        errorCount += checker("BigInteger.valueOf(42L)", 42L, jj);       
        
 
        assert(errorCount == 0);
        System.out.println("No errors encountered");
   }

}

