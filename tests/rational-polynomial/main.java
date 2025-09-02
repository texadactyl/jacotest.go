// This wacked file was borrowed from jacotest case taylor-series.

import java.math.BigInteger;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        BigRational half  = new BigRational(1, 2);
        BigRational three = new BigRational(3, 1);

        RationalPolynomial p = new RationalPolynomial(half,  1);
        RationalPolynomial q = new RationalPolynomial(three, 2);
        RationalPolynomial r = p.plus(q);
        RationalPolynomial s = p.times(q);
        RationalPolynomial t = r.times(r);
        RationalPolynomial u = t.minus(q.times(q));
        RationalPolynomial v = t.dividedBy(q);
        RationalPolynomial w = v.times(q);

        System.out.printf("p(x)                   = %s\n", p.toString());
        System.out.printf("q(x)                   = %s\n", q.toString());
        System.out.printf("r(x) = p(x) + q(x)     = %s\n", r.toString());
        System.out.printf("s(x) = p(x) * q(x)     = %s\n", s.toString());
        System.out.printf("t(x) = r(x) * r(x)     = %s\n", t.toString());
        System.out.printf("u(x) = t(x) - q^2(x)   = %s\n", u.toString());
        System.out.printf("v(x) = t(x) / q(x)     = %s\n", v.toString());
        System.out.printf("w(x) = v(x) * q(x)     = %s\n", w.toString());
        System.out.printf("t(3)                   = %s\n", t.evaluate(three).toString());
        System.out.printf("t'(x)                  = %s\n", t.differentiate().toString());
        System.out.printf("t''(x)                 = %s\n", t.differentiate().differentiate().toString());
        System.out.printf("f(x) = int of t(x)     = %s\n", t.integrate().toString());
        System.out.printf("integral(t(x), 1/2..3) = %s\n", t.integrate(half, three).toString());

        // =================== Result ===============================
        
        errorCount += Checkers.checker("p(x)", "1/2 x + 0", p.toString());
        errorCount += Checkers.checker("q(x)", "3 x^2", q.toString());
        errorCount += Checkers.checker("r(x)", "3 x^2 + 1/2 x", r.toString());
        errorCount += Checkers.checker("s(x)", "3/2 x^3", s.toString());
        errorCount += Checkers.checker("t(x)", "9 x^4 + 3 x^3 + 1/4 x^2", t.toString());
        errorCount += Checkers.checker("u(x)", "3 x^3 + 1/4 x^2", u.toString());
        errorCount += Checkers.checker("v(x)", "3 x^2 + 1 x + 1/12", v.toString());
        errorCount += Checkers.checker("w(x)", "9 x^4 + 3 x^3 + 1/4 x^2", w.toString());
        errorCount += Checkers.checker("t(3)", "3249/4", t.evaluate(three).toString());
        errorCount += Checkers.checker("t'(x)", "36 x^3 + 9 x^2 + 1/2 x", t.differentiate().toString());
        errorCount += Checkers.checker("t''(x)", "108 x^2 + 18 x + 1/2", t.differentiate().differentiate().toString());
        errorCount += Checkers.checker("f(x) = int of t(x)", "9/5 x^5 + 3/4 x^4 + 1/12 x^3", t.integrate().toString());
        errorCount += Checkers.checker("integral(t(x), 1/2..3)", "96055/192", t.integrate(half, three).toString());
        
        Checkers.theEnd(errorCount);
   }
   
}

class RationalPolynomial {
    public final static RationalPolynomial ZERO = new RationalPolynomial(BigRational.ZERO, 0);

    private BigRational[] coef;   // coefficients
    private int deg;              // degree of polynomial (0 for the zero polynomial)

    // Form: a * x^b
    public RationalPolynomial(BigRational a, int b) {
        coef = new BigRational[b+1];
        for (int i = 0; i < b; i++)
            coef[i] = BigRational.ZERO;
        coef[b] = a;
        deg = 0;
        for (int i = 0; i < coef.length; i++)
            if (coef[i].compareTo(BigRational.ZERO) != 0) deg = i;
    }

    // return the degree of this polynomial (0 for the zero polynomial)
    public int degree() {
        int d = 0;
        for (int i = 0; i < coef.length; i++)
            if (coef[i].compareTo(BigRational.ZERO) != 0) d = i;
        return d;
    }

    // return c = a + b
    public RationalPolynomial plus(RationalPolynomial b) {
        RationalPolynomial a = this;
        RationalPolynomial c = new RationalPolynomial(BigRational.ZERO, a.deg > b.deg ? a.deg : b.deg);
        for (int i = 0; i <= a.deg; i++) c.coef[i] = c.coef[i].plus(a.coef[i]);
        for (int i = 0; i <= b.deg; i++) c.coef[i] = c.coef[i].plus(b.coef[i]);
        c.deg = c.degree();
        return c;
    }

    // return c = a - b
    public RationalPolynomial minus(RationalPolynomial b) {
        RationalPolynomial a = this;
        RationalPolynomial c = new RationalPolynomial(BigRational.ZERO, a.deg > b.deg ? a.deg : b.deg);
        for (int i = 0; i <= a.deg; i++) c.coef[i] = c.coef[i].plus(a.coef[i]);
        for (int i = 0; i <= b.deg; i++) c.coef[i] = c.coef[i].minus(b.coef[i]);
        c.deg = c.degree();
        return c;
    }

    // return (a * b)
    public RationalPolynomial times(RationalPolynomial b) {
        RationalPolynomial a = this;
        RationalPolynomial c = new RationalPolynomial(BigRational.ZERO, a.deg + b.deg);
        for (int i = 0; i <= a.deg; i++)
            for (int j = 0; j <= b.deg; j++)
                c.coef[i+j] = c.coef[i+j].plus(a.coef[i].times(b.coef[j]));
        c.deg = c.degree();
        return c;
    }

    // return (a / b)
    public RationalPolynomial dividedBy(RationalPolynomial b) {
        RationalPolynomial a = this;
        if ((b.deg == 0) && (b.coef[0].compareTo(BigRational.ZERO) == 0))
            throw new ArithmeticException("call dividedBy() with denominator that is the zero polynomial");

        if (a.deg < b.deg) return ZERO;

        BigRational coefficient = a.coef[a.deg].dividedBy(b.coef[b.deg]);
        int exponent = a.deg - b.deg;
        RationalPolynomial c = new RationalPolynomial(coefficient, exponent);
        return c.plus( (a.minus(b.times(c)).dividedBy(b)) );
    }

    // truncate to degree d
    public RationalPolynomial truncate(int d) {
        RationalPolynomial p = new RationalPolynomial(BigRational.ZERO, d);
        for (int i = 0; i <= d; i++)
            p.coef[i] = coef[i];
        p.deg = p.degree();
        return p;
    }

    // use Horner's method to compute and return the polynomial evaluated at x
    public BigRational evaluate(BigRational x) {
        BigRational p = BigRational.ZERO;
        for (int i = deg; i >= 0; i--)
            p = coef[i].plus(x.times(p));
        return p;
    }

    // differentiate this polynomial and return it
    public RationalPolynomial differentiate() {
        if (deg == 0) return ZERO;
        RationalPolynomial deriv = new RationalPolynomial(BigRational.ZERO, deg-1);
        for (int i = 0; i < deg; i++)
            deriv.coef[i] = coef[i+1].times(new BigRational(i + 1));
        deriv.deg = deriv.degree();
        return deriv;
    }

    // return antiderivative
    public RationalPolynomial integrate() {
        RationalPolynomial integral = new RationalPolynomial(BigRational.ZERO, deg + 1);
        for (int i = 0; i <= deg; i++)
            integral.coef[i+1] = coef[i].dividedBy(new BigRational(i + 1));
        integral.deg = integral.degree();
        return integral;
    }


    // return integral from a to b
    public BigRational integrate(BigRational a, BigRational b) {
        RationalPolynomial integral = integrate();
        return integral.evaluate(b).minus(integral.evaluate(a));
    }


    // convert to string representation
    public String toString() {
        if (deg ==  0) return coef[0].toString();
        if (deg ==  1) return String.format("%s x + %s", coef[1].toString(), coef[0].toString());
        String s = String.format("%s x^%d", coef[deg].toString(), deg);
        for (int i = deg-1; i >= 0; i--) {
            int cmp = coef[i].compareTo(BigRational.ZERO);
            if      (cmp == 0) continue;
            else if (cmp  > 0) s = String.format("%s + %s", s, coef[i].toString());
            else if (cmp  < 0) s = String.format("%s - %s", s, coef[i].negate().toString());
            if      (i == 1) s = String.format("%s x", s);
            else if (i >  1) s = String.format("%s x^%d", s, i);
        }
        return s;
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

