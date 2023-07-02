public class main {

	private static final double PI = 3.14159265358979323846;
	private static final double epsilon = 1.0e-6;
    private static final double angleInDegrees = 45.0;
	private static final double expectedSine = 0.7071067811865475;
	private static final double expectedCosine = 0.7071067811865475;
	private static final double expectedTangent = 1.0;
	private static final int iterations = 20;

    public static void printResult(String label, double angleInDegrees, double computedValue) {
        System.out.print(label);
        System.out.print(" ");
        System.out.print(angleInDegrees);
        System.out.print(" = ");
        System.out.println(computedValue);
    }

	public static double absValue(double arg) {
		if (arg < 0.0)
			return -arg;
		return arg;
	}

	public static double relErr(double standard, double arg) {
		double diff = absValue(standard - arg);
		if (absValue(standard) < epsilon)
			return diff;
		return diff / standard;
	}

	public static int checker(String label, double expectedValue, double calculatedValue) {
		if (relErr(expectedValue, calculatedValue) < epsilon) {
			System.out.print(label);
			System.out.println(" checked ok");
			return 0;
		}
		System.out.print("*** ERROR, ");
		System.out.print(label);
		System.out.println(" expected and calculated values are too far apart");
		return 1;
	}

    public static void main(String[] args) {
    
        double angleInRadians = angleInDegrees * PI / 180.0;
        int errorCount = 0;

        printResult("The expected SINE of", angleInDegrees, expectedSine);
        printResult("The expected COSINE of", angleInDegrees, expectedCosine);
        printResult("The expected TANGENT of", angleInDegrees, expectedTangent);

        double calculatedSine = getSine(angleInRadians, iterations);
        double calculatedCosine = getCosine(angleInRadians, iterations);
        double calculatedTangent = getTangent(angleInRadians, iterations);

        printResult("The calculated SINE of", angleInDegrees, calculatedSine);
        printResult("The calculated COSINE of", angleInDegrees, calculatedCosine);
        printResult("The calculated TANGENT of", angleInDegrees, calculatedTangent);

        errorCount += checker("sine", expectedSine, calculatedSine);
        errorCount += checker("cosine", expectedCosine, calculatedCosine);
        errorCount += checker("tangent", expectedTangent, calculatedTangent);

        if (errorCount == 0) {
            System.out.println("No errors detected");
            System.exit(0);
        } else {
            System.out.print("Number of errors = ");
            System.out.println(String.valueOf(errorCount));
			System.exit(1);
        }

    }

	// sin(x) = x - (x^3 / 3!) + (x^5 / 5!) - (x^7 / 7!) + ...
    private static double getSine(double angle, int iterations) {
        double result = angle;
        double term = angle;
        double sign = -1.0;
        int counter = 0;

        for (int i = 3; counter < iterations; i += 2) {
            term *= angle * angle / (i * (i - 1));
            result += sign * term;
            sign = -sign;
            ++counter;
        }

        return result;
    }

	// cos(x) = 1 - x^2/2! + x^4/4! - x^6/6! + ...
    private static double getCosine(double angle, int iterations) {
        double result = 1.0;
        double term = 1.0;
        double sign = -1.0;
        int counter = 0;

        for (int i = 2; counter < iterations; i += 2) {
            term *= angle * angle / (i * (i - 1));
            result += sign * term;
            sign = -sign;
            ++counter;
        }

        return result;
    }

	// tan(x) = sin(x) / cos(x)
    private static double getTangent(double angle, int iterations) {
        return getSine(angle, iterations) / getCosine(angle, iterations);
    }

}
