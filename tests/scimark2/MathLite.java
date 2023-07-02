public class MathLite {

	public final double PI = 3.14159265358979323846;
	public final double EPSILON = 1.0e-6;
	public final int ITERATIONS = 20;

	public int abs(int arg) {
		if (arg < 0)
			return -arg;
		return arg;
	}

	public double abs(double arg) {
		if (arg < 0.0)
			return -arg;
		return arg;
	}

	// Taylor series: sin(x) = x - (x^3 / 3!) + (x^5 / 5!) - (x^7 / 7!) + ...
    public double sin(double radians) {
        double result = radians;
        double term = radians;
        double sign = -1.0;
        int counter = 0;

        for (int i = 3; counter < ITERATIONS; i += 2) {
            term *= radians * radians / (i * (i - 1));
            result += sign * term;
            sign = -sign;
            ++counter;
        }

        return result;
    }

	// Taylor series: cos(x) = 1 - x^2/2! + x^4/4! - x^6/6! + ...
    public double cos(double radians) {
        double result = 1.0;
        double term = 1.0;
        double sign = -1.0;
        int counter = 0;

        for (int i = 2; counter < ITERATIONS; i += 2) {
            term *= radians * radians / (i * (i - 1));
            result += sign * term;
            sign = -sign;
            ++counter;
        }

        return result;
    }

	// tan(x) = sin(x) / cos(x)
    public double tan(double radians) {
        return this.sin(radians) / this.cos(radians);
    }

	public double relErr(double standard, double arg) {
		double diff = this.abs(standard - arg);
		if (this.abs(standard) < EPSILON)
			return diff;
		return diff / standard;
	}

	// Newton's algorithm for the Square Root
	public double sqrt(double arg) {

		if (arg == Double.NaN || arg < 0.0)
			return Double.NaN;
		if (arg == Double.POSITIVE_INFINITY)
			return Double.POSITIVE_INFINITY;

        double estimate = 0.5 * arg;
        // repeatedly apply Newton update step until desired precision is achieved
        while (relErr(estimate, arg) > EPSILON) {
            estimate = ( arg / estimate + estimate) * 0.5;
        }
        return estimate;
        
	}
	
}
