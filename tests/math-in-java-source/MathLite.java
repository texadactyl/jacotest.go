public class MathLite {

	public final double PI = 3.14159265358979323846;
    public final double TWO_PI = 3.14159265358979323846 * 2.0;
	public final double RADdivDEG = PI / 180.0;
	public final double EPSILON = 1.0e-6;
	public final int ITERATIONS = 20;
	public final int EXP_ITERATIONS = 32;

    // Relative Error i.e. how close is the argument to the standard?
    public double relErr(double standard, double arg, double errorTolerance) {
        double diff = this.abs(standard - arg);
        if (this.abs(standard) < errorTolerance)
            return diff;
        return diff / standard;
    }
    public double relErr(double standard, double arg) {
        return relErr(double standard, double arg, EPSILON);
    }

    public int abs(int arg) {
		if (arg < 0)
			return -arg;
		return arg;
	}

	// Absolute Value
	public double abs(double arg) {
		if (arg < 0.0)
			return -arg;
		return arg;
	}

	// Convert degrees to radians
	public double toRadians(double degrees) {
		return degrees * RADdivDEG;
	}

	// Convert radians to degrees
	public double toDegrees(double radians) {
		return radians / RADdivDEG;
	}

    // Map radians to 0 : 2 * PI
    private double mapToTwoPi(double arg) {
        return arg % this.TWO_PI;
    }

	// Taylor series: sin(x) = x - (x^3 / 3!) + (x^5 / 5!) - (x^7 / 7!) + ...
    public double sin(double radians) {
        double xx = this.mapToTwoPi(radians);
        double result = xx;
        double term = xx;
        double sign = -1.0;
        int counter = 0;

        for (int i = 3; counter < ITERATIONS; i += 2) {
            term *= xx * xx / (i * (i - 1));
            result += sign * term;
            sign = -sign;
            ++counter;
        }

        return result;
    }

	// Taylor series: cos(x) = 1 - x^2/2! + x^4/4! - x^6/6! + ...
    public double cos(double radians) {
        double xx = this.mapToTwoPi(radians);
        double result = 1.0;
        double term = 1.0;
        double sign = -1.0;
        int counter = 0;

        for (int i = 2; counter < ITERATIONS; i += 2) {
            term *= xx * xx / (i * (i - 1));
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

	// Taylor series: atan(x) = = x - (x^3)/3 + (x^5)/5 - (x^7)/7 + ...
    public double atan(double arg) {

        double result = 0.0;
        double power = arg;
        double divisor = 1.0;

        for (int n = 1; n <= ITERATIONS; n++) {
            if (n % 2 == 0) {
                result -= power / divisor;
            } else {
                result += power / divisor;
            }
            power *= arg * arg;
            divisor += 2.0;
        }

        return result;
        
    }

	// Arcsine
    public double asin(double arg) {

        double numerator = arg;
        double denominator = this.sqrt(1.0 - arg * arg);
        return this.atan(numerator / denominator);
        
	}

    // Arccosine
    public double acos(double arg) {

        if (arg < 0)
            return PI - this.asin(sqrt(1.0 - arg * arg));
        return this.asin(this.sqrt(1.0 - arg * arg));

    }

    /* atan2(x, y)
		For y with a positive sign and finite nonzero x, the exact mathematical value of atan2 is equal to:
			If x > 0, atan(abs(y/x))
			If x < 0, π - atan(abs(y/x))
	*/
    public double atan2(double arg1, double arg2) {
		if (this.abs(arg1) < EPSILON)
			return 0.0;
		if (arg1 == Double.NaN || arg2 == Double.NaN)
			return Double.NaN;
		if (this.abs(arg2) < EPSILON)
			return PI * 0.5;
		if (arg1 > 0.0)
			return this.atan(this.abs(arg2 / arg1));
		return PI - this.atan(this.abs(arg2 / arg1));
	}
	
	// Babylon algorithm for the Square Root
    public double sqrt(double x) {
        if (x < 0)
            return Double.NaN;

        double guess = x / 2.0;
        double prevGuess;

        do {
            prevGuess = guess;
            guess = (guess + x / guess) / 2.0;
        } while (this.abs(guess - prevGuess) > EPSILON);

        return guess;
    }

	// (base)**(exponent)
    public double pow(double base, int exponent) {
        if (exponent == 0) {
            return 1.0;
        }
        double result = 1.0;
        if (exponent > 0) {
            for (int i = 1; i <= exponent; i++) {
                result *= base;
            }
        } else {
            for (int i = 1; i <= -exponent; i++) {
                result /= base;
            }
        }
        return result;
    }
    
    public double floor(double arg) {
        long floorLong = (long) arg; // Cast the argument to a long

        if (arg < 0 && arg != floorLong) {
            floorLong--; // Adjust the floor value for negative non-integer arguments
        }

        return (double) floorLong;
    }

    public long round(double arg) {
        long roundedLong = (long) arg; // Cast the argument to a long

        if (arg > 0 && arg != roundedLong) {
            roundedLong++; // Adjust the floor value for negative non-integer arguments
        }

        return roundedLong;
    }

    // Taylor series: e^x = 1 + x + (x^2)/2! + (x^3)/3! + (x^4)/4! + ...
	public double exp(double arg) {
		double divisor = 1.0;
		for (int ii = 0; ii < EXP_ITERATIONS; ii++)
			divisor *= 2.0;
		System.out.println("divisor=" + divisor);
		arg = 1.0 + arg / divisor;
		for (int ii = 0; ii < EXP_ITERATIONS; ii++) {
			arg *= arg;
		}
		return arg;
	}
    
}
