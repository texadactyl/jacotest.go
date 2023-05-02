public class Library {

    public static double absValue(double argument) {
        if (argument < 0.0) return -argument;
        else return argument;
    }

    public static double squareRoot(double argument) {
        // Babylonian method aka Heron's method
        // Faster variation of Newton-Raphson
        if (argument < 0) {
            throw new IllegalArgumentException("Cannot calculate the square root of a negative number");
        }

        double nextGuess = argument;
        double prevGuess = 0.0;
        double convergenceFactor = 0.00001; // The desired accuracy

        while (absValue(nextGuess - prevGuess) > convergenceFactor) {
            prevGuess = nextGuess;
            nextGuess = (nextGuess + argument / nextGuess) * 0.5;
        }

        return nextGuess;
    }

    public static double[] meanStdev(double[] values) {
        double sum = 0.0;
        double sumSq = 0.0;
        int numValues = values.length;
        // Calculate sum and sum of squares
        for (int ndx = 0; ndx < numValues; ndx++) {
            sum += values[ndx];
            sumSq += values[ndx] * values[ndx];
        }

        // Calculate mean and standard deviation
        double mean = sum / numValues;
        double variance = (sumSq / numValues) - (mean * mean);
        double stdDev = squareRoot(variance);

        // Return the result as an array
        return new double[]{mean, stdDev};
    }

    public static double covariance(double[] arg1, double[] arg2) {
        double sum1 = 0.0;
        double sum2 = 0.0;
        int arraySize = arg1.length;
        double dSize = (double) arraySize;
        for (int ndx = 0; ndx < arraySize; ++ndx) {
            sum1 += arg1[ndx];
            sum2 += arg2[ndx];
        }
        double mean1 = sum1 / dSize;
        double mean2 = sum2 / dSize;
        double wsum = 0.0;
        for (int ndx = 0; ndx < arraySize; ++ndx)
            wsum += (arg1[ndx] - mean1) * (arg2[ndx] - mean2);
        return wsum / dSize;
    }

    public static double correlation(double[] arg1, double[] arg2) {
        double stddev1, stddev2;
        double[] output = meanStdev(arg1);
        double stdev1 = output[1];
        output = meanStdev(arg2);
        double stdev2 = output[1];
        double cov = covariance(arg1, arg2);
        return cov / (stdev1 * stdev2);
    }

}

