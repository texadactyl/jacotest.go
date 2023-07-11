public class Helpers {

	public final double EpsilonD = 1.0e-6d;
	public final float  EpsilonF = 1.0e-6f;

    public void printResult(String label, double angleInDegrees, double computedValue) {
        System.out.print(label);
        System.out.print(" ");
        System.out.print(angleInDegrees);
        System.out.print(" = ");
        System.out.println(computedValue);
    }

    public double relErr(double standard, double arg) {
        double diff = standard - arg;
        diff = diff < 0.0 ? -diff : diff;
        double divisor = standard < 0.0 ? -standard : standard;
        if (divisor < EpsilonD)
            return diff;
        return diff / divisor;
    }

    public float relErr(float standard, float arg) {
        float diff = standard - arg;
        diff = diff < 0.0 ? -diff : diff;
        float divisor = standard < 0.0 ? -standard : standard;
        if (divisor < EpsilonF)
            return diff;
        return diff / divisor;
    }

	// Check (I)I
	public int checker(String label, int argument, int calculatedValue, int expectedValue) {
    	System.out.print(label);
    	System.out.print("-i(");
    	System.out.print(argument);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (expectedValue == calculatedValue) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (J)J
	public int checker(String label, long argument, long calculatedValue, long expectedValue) {
    	System.out.print(label);
    	System.out.print("-j(");
    	System.out.print(argument);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (expectedValue == calculatedValue) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (II)I
	public int checker(String label, int arg1, int arg2, int calculatedValue, int expectedValue) {
    	System.out.print(label);
    	System.out.print("-i(");
    	System.out.print(arg1);
    	System.out.print(", ");
    	System.out.print(arg2);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (expectedValue == calculatedValue) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (JJ)J
	public int checker(String label, long arg1, long arg2, long calculatedValue, long expectedValue) {
    	System.out.print(label);
    	System.out.print("-j(");
    	System.out.print(arg1);
    	System.out.print(", ");
    	System.out.print(arg2);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (expectedValue == calculatedValue) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (JI)I
	public int checker(String label, long arg1, int arg2, int calculatedValue, int expectedValue) {
    	System.out.print(label);
    	System.out.print("-j(");
    	System.out.print(arg1);
    	System.out.print(", ");
    	System.out.print(arg2);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (expectedValue == calculatedValue) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (D)D
	public int checker(String label, double argument, double calculatedValue, double expectedValue) {
    	System.out.print(label);
    	System.out.print("-d(");
    	System.out.print(argument);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (relErr(expectedValue, calculatedValue) < EpsilonD) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (D)I
	public int checker(String label, double argument, int calculatedValue, int expectedValue) {
    	System.out.print(label);
    	System.out.print("-i(");
    	System.out.print(argument);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (expectedValue == calculatedValue) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (DD)D
	public int checker(String label, double arg1, double arg2, double calculatedValue, double expectedValue) {
    	System.out.print(label);
    	System.out.print("-d(");
    	System.out.print(arg1);
    	System.out.print(", ");
    	System.out.print(arg2);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (relErr(expectedValue, calculatedValue) < EpsilonD) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (DDD)D
	public int checker(String label, double arg1, double arg2, double arg3, double calculatedValue, double expectedValue) {
    	System.out.print(label);
    	System.out.print("-d(");
    	System.out.print(arg1);
    	System.out.print(", ");
    	System.out.print(arg2);
    	System.out.print(", ");
    	System.out.print(arg3);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (relErr(expectedValue, calculatedValue) < EpsilonD) {
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	// Check (F)F
	public int checker(String label, float argument, float calculatedValue, float expectedValue) {
    	System.out.print(label);
    	System.out.print("-f(");
    	System.out.print(argument);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (relErr((double)expectedValue, (double)calculatedValue) < EpsilonD) { // JACOBIN-312
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}
	
	// Check (FFF)F
	public int checker(String label, float arg1, float arg2, float arg3, float calculatedValue, float expectedValue) {
    	System.out.print(label);
    	System.out.print("-f(");
    	System.out.print(arg1);
    	System.out.print(", ");
    	System.out.print(arg2);
    	System.out.print(", ");
    	System.out.print(arg3);
    	System.out.print(")=");
    	System.out.print(calculatedValue);
		if (relErr((double)expectedValue, (double)calculatedValue) < EpsilonD) { // JACOBIN-312
			System.out.println(" ok");
			return 0;
		}
		System.out.print(" *** ERROR, expected ");
    	System.out.println(expectedValue);
		return 1;
	}

	public void byebye(int errorCount) {
        if (errorCount == 0) {
            System.out.println("No errors detected");
            System.exit(0);
        } else {
            System.out.print("Number of errors = ");
            System.out.println(errorCount);
			System.exit(1);
        }
	}

}
