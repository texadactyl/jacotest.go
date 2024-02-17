public final class main {
	/**
	 * Return system time (in seconds)
	 */
	public final static double seconds() {
		return (System.currentTimeMillis() * 0.001);
	}

	public static void main(String[] args) {
		double t1 = seconds();
		int NLOOPS = 100000;
		int dividend = 6;
		int divisor = 0;
		int counterCatch = 0;
		int counterNoex = 0;
		int quotient;
		int ixFinally = 0;
		for (int ii = 0; ii < NLOOPS; ii++) {
			try {
				System.out.printf("try #%d\n", ii+1);
				int iparsed = Integer.parseInt("ABC");
				counterNoex += 1;
				System.out.printf("no-exception #%d\n", counterNoex);
			} catch (NumberFormatException ex) {
				counterCatch += 1;
				System.out.printf("catch #%d\n", counterCatch);
			} finally {
				if (ixFinally != ii) {
					String errMsg = String.format("Expected ixFinally to equal ii = %d but observed ixFinally = %d instead", ii, ixFinally);
					throw new AssertionError(errMsg);
				}
				ixFinally += 1;
				System.out.printf("finally #%d\n", ixFinally);
			}
		}
		double t2 = seconds();
		double et = t2 - t1;
		System.out.printf("Elapsed time = %f seconds\n", et);
		System.out.printf("Try count = %d\n", NLOOPS);
		System.out.printf("Catch count = %d (should = the Try count)\n", counterCatch);
		System.out.printf("Finally count = %d (should = the Try count)\n", ixFinally);
		System.out.printf("No-exception count (should = 0) = %d\n", counterNoex);
		if (counterNoex != 0 || counterCatch != NLOOPS || ixFinally != NLOOPS)
			throw new AssertionError("*** ERROR detected, look at the log");
		System.out.println("Success!");
	}
}
