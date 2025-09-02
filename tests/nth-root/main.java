// hacked from https://www.geeksforgeeks.org/n-th-root-number/
// Java program to calculate Nth root of a number
// This code is contributed by Anant Agarwal.

class main {

	static final double errorTolerance = 0.0001;
	
    static public void printLabeledObjects(String label, Object value1, Object value2) {
        System.out.print(label);
        System.out.print(": value1=");
        System.out.print(value1);
        System.out.print(", value2=");
        System.out.println(value2);
    }

    static void reporter(String label, Object value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(value);
    }

	// method returns Nth root of A
	static double nthRoot(long A, long N) {
		
		// initially guessing a random number between 0 and 9
		double xBase = Math.random() % 10;
	
		// smaller eps, denotes more accuracy
		double eps = errorTolerance;
	
		// initializing difference between two roots by INT_MAX
		double rootsDiff = 2147483647;
	
		// xCur denotes current value of x
		double xCur = 0.0;
		
		// Useful constants for the subsequent loop
		double dblA = (double) A;
		double dblN = (double) N;
		double nMinus1 = (double) N - 1.0;
	
		// loop until we reach desired accuracy
		long counter = 0;
		while (rootsDiff > eps) {
			// calculating current value from previous value by Newton's method
			xCur = (nMinus1 * xBase + dblA / Math.pow(xBase, nMinus1)) / dblN;
			rootsDiff = Math.abs(xCur - xBase);
			xBase = xCur;
			counter += 1;
		}
		reporter("nthRoot loop count", counter);
	
		return xCur;
	}
	
	// Entry point
	public static void main (String[] args) {
		long nth = 13l;
		long arg = 1234567l;
		reporter("Original argument", arg);
		reporter("Nth", nth);
	
		double root = nthRoot(arg, nth);
		reporter("Nth root of the original argument", root);
		double darg2 = Math.pow(root, nth);
		reporter("Final argument", darg2);
		
		int errorCount = Checkers.withinTolerance("Original argument and final argument - close?", (double) arg, darg2);		
		Checkers.theEnd(errorCount);
	
	}
}


