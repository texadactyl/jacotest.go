// hacked from https://introcs.cs.princeton.edu/java/92symbolic/BigRational.java.html
// This is a file borrowed from jacotest case taylor-series.

import java.math.BigInteger;

public class main {

    public static int checker(String label, String strExpected, BigRational brObserved) {
        String strObserved = brObserved.toString();
        System.out.printf("checker: %s ", label);
        if ( strObserved.equals(strExpected) ) {
            System.out.printf(" ok, value = %s\n", strObserved);
            return 0;
        }
        System.out.printf(" ********** ERROR, expected = %s, observed = %s\n", strExpected, strObserved);
        return 1;
    }

    public static int checker(String label, String strExpected, int intObserved) {
        String strObserved = String.format("%d", intObserved);
        System.out.printf("checker: %s ", label);
        if ( strObserved.equals(strExpected) ) {
            System.out.printf(" ok, value = %s\n", strObserved);
            return 0;
        }
        System.out.printf(" ********** ERROR, expected = %s, observed = %s\n", strExpected, strObserved);
        return 1;
    }

    public static int checker(String label, boolean expected, boolean observed) {
        System.out.printf("checker: %s ", label);
        if ( observed == expected ) {
            System.out.print(" ok, value = ");
            System.out.println(expected);
            return 0;
        }
        System.out.print(" ********** ERROR, expected = ");
        System.out.print(expected);
        System.out.print(", observed = ");
        System.out.println(observed);
        return 1;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        BigRational x, y, z;
        int ii;

        // 1/2 + 1/3 = 5/6
        x = new BigRational(1, 2);
        y = new BigRational(1, 3);
        z = x.plus(y);
        errorCount += checker("1/2 + 1/3", "5/6", z);

        // 8/9 + 1/9 = 1
        x = new BigRational(8, 9);
        y = new BigRational(1, 9);
        z = x.plus(y);
        errorCount += checker("8/9 + 1/9", "1", z);

        // 1/200000000 + 1/300000000 = 1/120000000
        x = new BigRational(1, 200000000);
        y = new BigRational(1, 300000000);
        z = x.plus(y);
        errorCount += checker("1/200000000 + 1/300000000", "1/120000000", z);

        // 1073741789/20 + 1073741789/30 = 1073741789/12
        x = new BigRational(1073741789, 20);
        y = new BigRational(1073741789, 30);
        z = x.plus(y);
        errorCount += checker("1073741789/20 + 1073741789/30", "1073741789/12", z);

        //  4/17 * 17/4 = 1
        x = new BigRational(4, 17);
        y = new BigRational(17, 4);
        z = x.times(y);
        errorCount += checker("4/17 * 17/4", "1", z);

        // 3037141/3247033 * 3037547/3246599 = 841/961
        x = new BigRational(3037141, 3247033);
        y = new BigRational(3037547, 3246599);
        z = x.times(y);
        errorCount += checker("3037141/3247033 * 3037547/3246599", "841/961", z);

        // -1/200000000 + 1/300000000 = -1/600000000
        x = new BigRational(-1, 200000000);
        y = new BigRational(1, 300000000);
        z = x.plus(y);
        errorCount += checker("-1/200000000 + 1/300000000", "-1/600000000", z);
        
        // 1/6 - -4/-8 = -1/3
        x = new BigRational( 1,  6);
        y = new BigRational(-4, -8);
        z = x.minus(y);
        errorCount += checker("(1/6) - (-4/-8)", "-1/3", z);

        // (90, 6) dividedBy (21, 7) = (5, 1)
        x = new BigRational(90, 6);
        y = new BigRational(21, 7);
        z = x.dividedBy(y);
        errorCount += checker("(90, 6) dividedBy (21, 7)", "5", z);

        // (8192, 17) dividedBy (-14, 4096) = (-16777216/119)
        x = new BigRational(8192, 17);
        y = new BigRational(-14, 4096);
        z = x.dividedBy(y);
        errorCount += checker("(8192, 17) dividedBy (-14, 4096)", "-16777216/119", z);

        // 0
        z = new BigRational(0,  5);
        errorCount += checker("0 / 5", "0", z);
        
        try {
            BigRational rubbish = z.reciprocal();   // Try to divide-by-zero.
            System.out.println("*** ERROR, failed to catch divide by zero attempt");
            ++errorCount;
        } catch(Exception ex) {
            System.out.println("ok, caught divide by zero attempt");
        }

        // recipricol(3 / 4) --> 4 / 3
        x = new BigRational( 3,  4);
        z = x.reciprocal();
        errorCount += checker("recipricol(3 / 4)", "4/3", z);
        
        // (-1, 2).compareTo(1, 3) --> -1
        x = new BigRational(-1, 2);
        y = new BigRational(1, 3);
        ii = x.compareTo(y);
        errorCount += checker("(-1, 2).compareTo(1, 3)", "-1", ii);

        // (1, -2).compareTo(1, 3) --> -1
        x = new BigRational(1, -2);
        y = new BigRational(1, 3);
        ii = x.compareTo(y);
        errorCount += checker("(1, -2).compareTo(1, 3)", "-1", ii);

        // (1, 2).compareTo(1, 3) --> +1
        x = new BigRational(1, 2);
        y = new BigRational(1, 3);
        ii = x.compareTo(y);
        errorCount += checker("(1, 2).compareTo(1, 3)", "1", ii);

        // (1, 3).compareTo(1, 2) --> -1
        ii = y.compareTo(x);
        errorCount += checker("(1, 3).compareTo(1, 2)", "-1", ii);
        
        // (1, 3).compareTo(1, 3) --> 0
        ii = y.compareTo(y);
        errorCount += checker("(1, 3).compareTo(1, 3)", "0", ii);
        
        // (-0, -42).isZero() --> true
        z = new BigRational(-0, -42);
        errorCount += checker("(-0, -42).isZero()", true, z.isZero());
        
        // (1, 2).isZero() --> false
        z = new BigRational(1, 2);
        errorCount += checker("(1, 2).isZero()", false, z.isZero());
        
        // (1, 2).isNegative() --> false
        z = new BigRational(1, 2);
        errorCount += checker("(1, 2).isNegative()", false, z.isNegative());
        
        // (1, 2).isPositive() --> true
        z = new BigRational(1, 2);
        errorCount += checker("(1, 2).isPositive()", true, z.isPositive());
        
        // (-1, 2).isZero() --> false
        z = new BigRational(-1, 2);
        errorCount += checker("(-1, 2).isZero()", false, z.isZero());
        
        // (-1, 2).isNegative() --> true
        z = new BigRational(-1, 2);
        errorCount += checker("(-1, 2).isNegative()", true, z.isNegative());
        
        // (-1, 2).isPositive() --> false
        z = new BigRational(-1, 2);
        errorCount += checker("(-1, 2).isPositive()", false, z.isPositive());
        
        // (1, 2).negate() --> (-1, 2)
        x = new BigRational(1, 2);
        z = x.negate();
        errorCount += checker("(1, 2).negate()", "-1/2", z);

        // (1, -2).negate() --> (1, 2)
        x = new BigRational(1, -2);
        z = x.negate();
        errorCount += checker("(1, -2).negate()", "1/2", z);

        // (0, 42).negate() --> 0
        x = new BigRational(0, 42);
        z = x.negate();
        errorCount += checker("(0, 42).negate()", "0", z);

        // (1, -2).abs() --> (1, 2)
        x = new BigRational(1, -2);
        z = x.abs();
        errorCount += checker("(1, -2).abs()", "1/2", z);

        // (1, 2).abs() --> (1, 2)
        x = new BigRational(1, 2);
        z = x.abs();
        errorCount += checker("(1, 2).abs()", "1/2", z);

        // =================== Result ===============================
        
        assert(errorCount == 0);
        
        System.out.println("Success!");

    }

}


class BigRational implements Comparable<BigRational> {

    public final static BigRational ZERO = new BigRational(0);
    public final static BigRational ONE  = new BigRational(1);
    public final static BigRational TWO  = new BigRational(2);

    private BigInteger num;   // the numerator
    private BigInteger den;   // the denominator (always a positive integer)
    
    public int hashCode() { // dummy function to satisfy implements Comparable<BigRational> requirement
        return 42;
    }

    // create and initialize a new BigRational object
    public BigRational(int numerator, int denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    // create and initialize a new BigRational object
    public BigRational(int numerator) {
        this(numerator, 1);
    }

    // create and initialize a new BigRational object from a string, e.g., "-343/1273"
    public BigRational(String s) {
        String[] tokens = s.split("/");
        if (tokens.length == 2)
            init(new BigInteger(tokens[0]), new BigInteger(tokens[1]));
        else if (tokens.length == 1)
            init(new BigInteger(tokens[0]), BigInteger.ONE);
        else
            throw new IllegalArgumentException("For input string: \"" + s + "\"");
    }

    // create and initialize a new BigRational object
    public BigRational(BigInteger numerator, BigInteger denominator) {
        init(numerator, denominator);
    }

    private void init(BigInteger numerator, BigInteger denominator) {

        // deal with x / 0
        if (denominator.equals(BigInteger.ZERO)) {
           throw new ArithmeticException("Denominator is zero");
        }

        // reduce fraction (if num = 0, will always yield den = 0)
        BigInteger g = numerator.gcd(denominator);
        num = numerator.divide(g);
        den = denominator.divide(g);

        // to ensure invariant that denominator is positive
        if (den.compareTo(BigInteger.ZERO) < 0) {
            den = den.negate();
            num = num.negate();
        }
    }

    // return string representation of (this)
    public String toString() {
        if (den.equals(BigInteger.ONE)) 
            return num.toString();
        else                            
            return String.format("%s/%s", num.toString(), den.toString());
    }

    // return { -1, 0, + 1 } if a < b, a = b, or a > b
    public int compareTo(BigRational b) {
        BigRational a = this;
        return a.num.multiply(b.den).compareTo(a.den.multiply(b.num));
    }

    // is this BigRational negative, zero, or positive?
    public boolean isZero()     { return num.signum() == 0; }
    public boolean isPositive() { return num.signum() >  0; }
    public boolean isNegative() { return num.signum() <  0; }

    // return a * b
    public BigRational times(BigRational b) {
        BigRational a = this;
        return new BigRational(a.num.multiply(b.num), a.den.multiply(b.den));
    }

    // return a + b
    public BigRational plus(BigRational b) {
        BigRational a = this;
        BigInteger numerator   = a.num.multiply(b.den).add(b.num.multiply(a.den));
        BigInteger denominator = a.den.multiply(b.den);
        return new BigRational(numerator, denominator);
    }

    // return -a
    public BigRational negate() {
        return new BigRational(num.negate(), den);
    }

    // return |a|
    public BigRational abs() {
        if (isNegative()) return negate();
        else return this;
    }

    // return a - b
    public BigRational minus(BigRational b) {
        BigRational a = this;
        return a.plus(b.negate());
    }

    // return 1 / a
    public BigRational reciprocal() {
        return new BigRational(den, num);
    }

    // return a / b
    public BigRational dividedBy(BigRational b) {
        BigRational a = this;
        return a.times(b.reciprocal());
    }
    
}

