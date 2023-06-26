// hacked from https://introcs.cs.princeton.edu/java/92symbolic/Taylor.java

/******************************************************************************
 *  Compilation:  javac Taylor.java
 *  Execution:    java Taylor N
 *  Dependencies: RationalPolynomial.java, BigRational
 *
 *  Taylor series of
 *
 *     e^x     = 1 + x + x^2/2! + x^3/3! + x^4/4! + ...
 *   sin x     = x - x^3/3! + x^5/5! - x^7/7! + ...
 *   e^x sin x = x + x^2 + x^3/3 -x^5/30 -x^6/90 + ...
 *
 *  % java Taylor 5
 *  e^x = 1/24 x^4 + 1/6 x^3 + 1/2 x^2 + 1 x + 1
 *  sin(x) = -1/6 x^3 + 1 x
 *  e^x sin(x) = -1/24 x^5 + 1/3 x^3 + 1 x^2 + 1 x
 *
 ******************************************************************************/

public class main {

    // number of terms in the series
	static final int NTERMS = 10;

    // return n!
    public static int factorial(int arg) {
        if (arg == 0) return 1;
        return arg * factorial(arg - 1);
    }

    public static void printLabeledString(String label, Object value) {
        System.out.print(label);
        System.out.println(value);
    }

    public static void main(String[] args) {
    
    	System.out.println("Taylor series exercise");

        // Taylor series for e^x
        RationalPolynomial tsExp = RationalPolynomial.ZERO;
        for (int ii = 0; ii < NTERMS; ii++) {
            BigRational coef = new BigRational(1, factorial(ii));
            RationalPolynomial term = new RationalPolynomial(coef, ii);
            tsExp = tsExp.plus(term);
        }
        printLabeledString("e^x = ", tsExp);

        // Taylor series for sin(x)
        RationalPolynomial tsSine = RationalPolynomial.ZERO;
        for (int ii = 0; ii < NTERMS; ii++) {
            BigRational coef;
            if      (ii % 4 == 0) coef = BigRational.ZERO;
            else if (ii % 4 == 1) coef = new BigRational(ii, factorial(ii));
            else if (ii % 4 == 2) coef = BigRational.ZERO;
            else                  coef = new BigRational(-1, factorial(ii));
            RationalPolynomial term = new RationalPolynomial(coef, ii);
            tsSine = tsSine.plus(term);
        }
        printLabeledString("sin(x) = ", tsSine);

        printLabeledString("e^x * sin(x) = ", tsExp.times(tsSine).truncate(NTERMS));

   }

}

