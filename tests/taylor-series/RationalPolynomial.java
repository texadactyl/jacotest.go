// hacked from https://introcs.cs.princeton.edu/java/92symbolic/RationalPolynomial.java.html

/******************************************************************************
 *  Compilation:  javac RationalPolynomial.java
 *  Execution:    java RationalPolynomial
 *  Dependencies: BigRational
 *
 *  A polynomial with arbitrary precision rational coefficients.
 *
 ******************************************************************************/

public class RationalPolynomial {
    public final static RationalPolynomial ZERO = new RationalPolynomial(BigRational.ZERO, 0);

    private BigRational[] coef;   // coefficients
    private int deg;              // degree of polynomial (0 for the zero polynomial)

    // Form: a * x^b
    public RationalPolynomial(BigRational a, int b) {
        coef = new BigRational[b+1];
        for (int i = 0; i < b; i++)
            coef[i] = BigRational.ZERO;
        coef[b] = a;
        deg = degree();
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
    public RationalPolynomial divides(RationalPolynomial b) {
        RationalPolynomial a = this;
        if ((b.deg == 0) && (b.coef[0].compareTo(BigRational.ZERO) == 0))
            throw new ArithmeticException("call divides() with denominator that is the zero polynomial");

        if (a.deg < b.deg) return ZERO;

        BigRational coefficient = a.coef[a.deg].divides(b.coef[b.deg]);
        int exponent = a.deg - b.deg;
        RationalPolynomial c = new RationalPolynomial(coefficient, exponent);
        return c.plus( (a.minus(b.times(c)).divides(b)) );
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
            integral.coef[i+1] = coef[i].divides(new BigRational(i + 1));
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
        if (deg ==  0) return "" + coef[0];
        if (deg ==  1) return coef[1] + " x + " + coef[0];
        String s = coef[deg] + " x^" + deg;
        for (int i = deg-1; i >= 0; i--) {
            int cmp = coef[i].compareTo(BigRational.ZERO);
            if      (cmp == 0) continue;
            else if (cmp  > 0) s = s + " + " + ( coef[i]);
            else if (cmp  < 0) s = s + " - " + (coef[i].negate());
            if      (i == 1) s = s + " x";
            else if (i >  1) s = s + " x^" + i;
        }
        return s;
    }

    // test client
    public static void main(String[] args) {
        BigRational half  = new BigRational(1, 2);
        BigRational three = new BigRational(3, 1);

        RationalPolynomial p = new RationalPolynomial(half,  1);
        RationalPolynomial q = new RationalPolynomial(three, 2);
        RationalPolynomial r = p.plus(q);
        RationalPolynomial s = p.times(q);
        RationalPolynomial t = r.times(r);
        RationalPolynomial u = t.minus(q.times(q));
        RationalPolynomial v = t.divides(q);
        RationalPolynomial w = v.times(q);

        System.out.println("p(x)                      = " + p);
        System.out.println("q(x)                      = " + q);
        System.out.println("r(x) = p(x) + q(x)        = " + r);
        System.out.println("s(x) = p(x) * q(x)        = " + s);
        System.out.println("t(x) = r(x) * r(x)        = " + t);
        System.out.println("u(x) = t(x) - q^2(x)      = " + u);
        System.out.println("v(x) = t(x) / q(x)        = " + v);
        System.out.println("w(x) = v(x) * q(x)        = " + w);
        System.out.println("t(3)                   = " + t.evaluate(three));
        System.out.println("t'(x)                  = " + t.differentiate());
        System.out.println("t''(x)                 = " + t.differentiate().differentiate());
        System.out.println("f(x) = int of t(x)     = " + t.integrate());
        System.out.println("integral(t(x), 1/2..3) = " + t.integrate(half, three));
   }

}
