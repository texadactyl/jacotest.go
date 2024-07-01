public final class main {


	private static void successCases(int idividend, int idivisor, int iexpectationDiv, int iexpectationMod) {
		long ldividend = idividend;
		long ldivisor = idivisor;
		System.out.printf("successCases: For int and long, dividend=%d, divisor=%d, expectationDiv=%d, expectationMod=%d\n", 
                          idividend, idivisor, iexpectationDiv, iexpectationMod);

		try {
			int observed = Math.floorDiv(idividend, idivisor);
			if (observed != iexpectationDiv) {
				String errMsg = String.format("*** ERROR, successCases: Math.floorDiv(II) expected %d, observed %d", iexpectationDiv, observed);
				throw new AssertionError(errMsg);
            }
		} catch (ArithmeticException ex) {
			throw new AssertionError("*** ERROR, successCases: Math.floorDiv(II) threw an ArithmeticException");
		}

		try {
			long observed = Math.floorDiv(ldividend, idivisor);
			if (observed != iexpectationDiv) {
				String errMsg = String.format("*** ERROR, successCases: Math.floorDiv(JI) expected %d, observed %d", iexpectationDiv, observed);
				throw new AssertionError(errMsg);
            }
		} catch (ArithmeticException ex) {
			throw new AssertionError("*** ERROR, successCases: Math.floorDiv(JI) threw an ArithmeticException");
		}

		try {
			long observed = Math.floorDiv(ldividend, ldivisor);
			if (observed != iexpectationDiv) {
				String errMsg = String.format("*** ERROR, successCases: Math.floorDiv(JJ) expected %d, observed %d", iexpectationDiv, observed);
				throw new AssertionError(errMsg);
            }
		} catch (ArithmeticException ex) {
			throw new AssertionError("*** ERROR, successCases: Math.floorDiv(JJ) threw an ArithmeticException");
		}
		try {
			int observed = Math.floorMod(idividend, idivisor);
			if (observed != iexpectationMod) {
				String errMsg = String.format("*** ERROR, successCases: Math.floorMod(II) expected %d, observed %d", iexpectationMod, observed);
				throw new AssertionError(errMsg);
            }
		} catch (ArithmeticException ex) {
			throw new AssertionError("*** ERROR, successCases: Math.floorMod(II) threw an ArithmeticException");
		}

		try {
			long observed = Math.floorMod(ldividend, idivisor);
			if (observed != iexpectationMod) {
				String errMsg = String.format("*** ERROR, successCases: Math.floorMod(JI) expected %d, observed %d", iexpectationMod, observed);
				throw new AssertionError(errMsg);
            }
		} catch (ArithmeticException ex) {
			throw new AssertionError("*** ERROR, successCases: Math.floorMod(JI) threw an ArithmeticException");
		}

		try {
			long observed = Math.floorMod(ldividend, ldivisor);
			if (observed != iexpectationMod) {
				String errMsg = String.format("*** ERROR, successCases: Math.floorMod(JJ) expected %d, observed %d", iexpectationMod, observed);
				throw new AssertionError(errMsg);
            }
		} catch (ArithmeticException ex) {
			throw new AssertionError("*** ERROR, successCases: Math.floorMod(JJ) threw an ArithmeticException");
		}
		System.out.println("successCases: ok");

	}

    private static void errorCases(int idividend, int idivisor) {
		long ldividend = idividend;
		long ldivisor = idivisor;
		System.out.printf("errorCases: For int and long, dividend=%d, divisor=%d\n", idividend, idivisor);

		try {
			int observed = Math.floorDiv(idividend, idivisor);
			throw new AssertionError("*** ERROR, errorCases: Math.floorDiv(II) failed to throw an ArithmeticException");
		} catch (ArithmeticException ex) { System.out.println("Success, errorCases: Math.floorDiv(II) threw an ArithmeticException"); }

		try {
			long observed = Math.floorDiv(ldividend, idivisor);
			throw new AssertionError("*** ERROR, errorCases: Math.floorDiv(JI) failed to throw an ArithmeticException");
		} catch (ArithmeticException ex) { System.out.println("Success, errorCases: Math.floorDiv(JI) threw an ArithmeticException"); }

		try {
			long observed = Math.floorDiv(ldividend, ldivisor);
			throw new AssertionError("*** ERROR, errorCases: Math.floorDiv(JJ) failed to throw an ArithmeticException");
		} catch (ArithmeticException ex) { System.out.println("Success, errorCases: Math.floorDiv(JJ) threw an ArithmeticException"); }

		try {
			int observed = Math.floorMod(idividend, idivisor);
			throw new AssertionError("*** ERROR, errorCases: Math.floorMod(II) failed to throw an ArithmeticException");
		} catch (ArithmeticException ex) { System.out.println("Success, errorCases: Math.floorMod(II) threw an ArithmeticException"); }

		try {
			long observed = Math.floorMod(ldividend, idivisor);
			throw new AssertionError("*** ERROR, errorCases: Math.floorMod(JI) failed to throw an ArithmeticException");
		} catch (ArithmeticException ex) { System.out.println("Success, errorCases: Math.floorMod(JI) threw an ArithmeticException"); }

		try {
			long observed = Math.floorMod(ldividend, ldivisor);
			throw new AssertionError("*** ERROR, errorCases: Math.floorMod(JJ) failed to throw an ArithmeticException");
		} catch (ArithmeticException ex) { System.out.println("Success, errorCases: Math.floorMod(JJ) threw an ArithmeticException"); }

		System.out.println("errorCases: ok");
    }

    public static void main(String[] args) {
		successCases(-4, 3, -2, 2); 		
		successCases(1057, -19, -56, -7);
		errorCases(137, 0);	
	}
}
