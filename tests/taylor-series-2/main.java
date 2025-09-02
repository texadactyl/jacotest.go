/******************************************************************************
 *  Taylor series of
 *
 *     e^x     = 1 + x + x^2/2! + x^3/3! + x^4/4! + ...
 *   sin x     = x - x^3/3! + x^5/5! - x^7/7! + ...
 *   e^x sin x = x + x^2 + x^3/3 -x^5/30 -x^6/90 + ...
 *
 ******************************************************************************/

public class main {

    // number of NTERMS in the series
	static final long NTERMS = 100;
	static final double X = 0.1;
	static final double TOLERANCE = 0.001;

    // Compute a factorial of the given long integer.
    // Recursive!
	private static double factorial(long arg) {
		if (arg == 0) {
			return 1.0d;
		} else {
			return ((double) arg) * factorial(arg - 1);
		}
	}

    public static void main(String[] args) {
    
    	System.out.println("Taylor series exercise without the rational library functions");
    	double term;
    	double fak;
    	int errorCount = 0;

        // Taylor series for e^X
		double tsExp = 1.0;
		for (long ii = 1; ii <= NTERMS; ii++) {
			term = Math.pow(X, (double) ii) / factorial(ii);
			tsExp += term;
		}

		// Taylor series for sin(X)
		double tsSine = 0.0;
		double sign = 1.0;
		for (long ii = 1; ii <= NTERMS; ii++) {
			fak = factorial(ii + ii + 1);
			term = Math.pow(X, (double) (ii + ii + 1)) / fak;
			tsSine += sign * term;
			//System.out.printf("DEBUG tsSine=%f, fak=%f, term = %f, sign=%f\n", tsSine, fak, term, sign);
			sign = -sign;
		}

        System.out.printf("sin(x) = %f\n", tsSine);
        errorCount += Checkers.withinTolerance("sin(x)", tsSine, 0.000167, TOLERANCE);

        System.out.printf("e^x = %f\n", tsExp);
        errorCount += Checkers.withinTolerance("exp(x)", tsExp, 1.105171, TOLERANCE);
        
        Checkers.theEnd(errorCount);

   }

}

