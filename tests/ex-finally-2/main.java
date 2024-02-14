public final class main {
    public static void main(String[] args) {
		int NLOOPS = 100;
		int dividend = 6;
		int divisor = 0;
		int counterCatch = 0;
		int counterNoex = 0;
		int quotient;
		int jj = 0;
		for (int ii = 0; ii < NLOOPS; ii++) {
			try {
				System.out.printf("try #%d\n", ii+1);
				quotient = dividend / divisor;
				counterNoex += 1;
				System.out.printf("no-exception #%d\n", counterNoex);
			} catch (Exception ex) {
				counterCatch += 1;
				System.out.printf("catch #%d\n", counterCatch);
			} finally {
				if (jj != ii) {
					String errMsg = String.format("Expected the finally jj counter to equal ii = %d but observed jj = %d instead", ii, jj);
					throw new AssertionError(errMsg);
				}
				jj += 1;
			}
		}
		System.out.printf("Catch count = %d\n", counterCatch);
		System.out.printf("No-exception count = %d\n", counterNoex);
	}
}
