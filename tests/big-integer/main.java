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
            System.out.printf("%s: Success, expected(%d) = observed(%d)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("%s: *** ERROR, expected(%d) != observed(%d)\n", label, expected, observed);
        return 1;

    }

    public static void main(String[] args) {
        int errorCount = 0;
        
        byte[] barr1 = new byte[] {0, 0, 0, 'A'};       
        byte[] barr2 = new byte[] {(byte) 255, (byte) 255, (byte) 255, (byte) 253};
        System.out.print("barr1: ");   
        System.out.printf("hex %02x%02x%02x%02x\n", barr1[0], barr1[1], barr1[2], barr1[3]);    
        System.out.print("barr2: ");   
        System.out.printf("hex %02x%02x%02x%02x\n", barr2[0], barr2[1], barr2[2], barr2[3]);    
        BigInteger bint1 = new BigInteger(barr1);
        BigInteger bint2 = new BigInteger(barr2);
        System.out.print("bint1: \"65 65\" should appear here: ");
        System.out.print(bint1); 
        System.out.print(" ");
        System.out.println(bint1);
        System.out.print("bint1: \"-3 -3\" should appear here: ");
        System.out.print(bint2); 
        System.out.print(" ");
        System.out.println(bint2);
        
        int ii = bint1.intValue();
        errorCount += checker("barr1 intValue", 65L, (long) ii);       
        long jj = bint1.longValue();
        errorCount += checker("barr1 longValue", 65L, jj);
        
        ii = bint2.intValue();
        errorCount += checker("barr2 intValue", -3L, (long) ii);       
        jj = bint2.longValue();
        errorCount += checker("barr2 longValue", -3L, jj);
        
        String str1 = "65";
        String str2 = "-3";
        bint1 = new BigInteger(str1);
        bint2 = new BigInteger(str2);
        
        jj = bint1.longValue();
        errorCount += checker("str1 longValue", 65L, jj);
        jj = bint2.longValue();
        errorCount += checker("str2 longValue", -3L, jj);
        
        BigInteger w1 = bint1.abs();
        jj = w1.longValue();
        errorCount += checker("w1=bint1.abs", 65L, jj);
        
        BigInteger w2 = bint2.abs();
        jj = w2.longValue();
        errorCount += checker("w2=bint1.abs", 3L, jj);
        
        BigInteger w3 = w1.add(w2);
        jj = w3.longValue();
        errorCount += checker("w3=w1+w2", 68L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        w3 = w1.subtract(w2);
        jj = w3.longValue();
        errorCount += checker("w3=w1-w2", 62L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        w3 = w1.and(w2);
        jj = w3.longValue();
        errorCount += checker("w3=w1&w2", 1L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        jj = (long) w1.bitLength();
        errorCount += checker("w1.bitLength", 7L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        jj = (long) w1.compareTo(w2);
        errorCount += checker("w1.compareTo(w2)", 1L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        jj = (long) w1.compareTo(w1);
        errorCount += checker("w1.compareTo(w1)", 0L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        jj = (long) w2.compareTo(w1);
        errorCount += checker("w2.compareTo(w1)", -1L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        w3 = w1.divide(w2);
        jj = w3.longValue();
        errorCount += checker("w1.divide(w2)", 21L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        BigInteger warray[] = w1.divideAndRemainder(w2);
        long jj1 = warray[0].longValue();
        long jj2 = warray[1].longValue();
        errorCount += checker("w1.divideAndRemainder(w2) quotient", 21L, jj1);
        errorCount += checker("w1.divideAndRemainder(w2) remainder", 2L, jj2);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        jj = w1.equals(w1)? 0L : 1L;
        errorCount += checker("w1.equals(w1)", 0L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        jj = w1.equals(w2)? 0L : 1L;
        errorCount += checker("w1.equals(w1)", 1L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 65L, jj);

        w1 = new BigInteger("27");
        w2 = new BigInteger("45");
        w3 = w1.gcd(w2);
        jj = w3.longValue();
        errorCount += checker("w1.gcd(w2)", 9L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 27L, jj);

        w1 = new BigInteger("43");
        w2 = new BigInteger("79");
        w3 = w1.modInverse(w2);
        jj = w3.longValue();
        errorCount += checker("w1.modInverse(w2)", 68L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 43L, jj);

        w1 = new BigInteger("23895");
        BigInteger ee = new BigInteger("15");
        BigInteger mm = new BigInteger("14189");
        BigInteger w4 = w1.modPow(ee, mm);
        jj = w4.longValue();
        errorCount += checker("23895.modPow(ee=15, mm=14189)", 344L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 23895L, jj);

        w1 = new BigInteger("23");
        w2 = new BigInteger("100");
        w3 = w1.multiply(w2);
        jj = w3.longValue();
        errorCount += checker("23 * 100", 2300L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 23L, jj);

        w3 = w1.negate();
        jj = w3.longValue();
        errorCount += checker("-23", -23L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 23L, jj);
       
        byte bb[] = w4.toByteArray();
        jj = bb[0] * 256 + bb[1];
        errorCount += checker("w4.toByteArray", 344L, jj);
        jj = w4.longValue();
        errorCount += checker("w4 again", 344L, jj);
       
        String str = w4.toString();
        BigInteger w5 = new BigInteger(str);
        jj = w5.longValue();
        errorCount += checker("w4.toString", 344L, jj);
       
        str = w4.toString(10);
        w5 = new BigInteger(str);
        jj = w5.longValue();
        errorCount += checker("w4.toString", 344L, jj);
       
        str = w4.toString(16);
        w5 = new BigInteger(str, 16);
        jj = w5.longValue();
        errorCount += checker("w4.toString/radix", 344L, jj);
       
        w1 = new BigInteger("23");
        w2 = new BigInteger("100");
        w3 = w1.xor(w2);
        jj = w3.longValue();
        errorCount += checker("23 xor 100", 115L, jj);
        jj = w1.longValue();
        errorCount += checker("w1 again", 23L, jj);

        assert(errorCount == 0);
        System.out.println("No errors encountered");
   }

}

