// https://introcs.cs.princeton.edu/java/22library/sr.java.html

/******************************************************************************
 *  A library of static methods to generate pseudo-random numbers from
 *  different distributions (bernoulli, uniform, gaussian, discrete,
 *  and exponential). Also includes a method for shuffling an array.
 *
 *
 *  %  java StdRandom 5
 *  seed = 1316600602069
 *  59 16.81826  true 8.83954  0
 *  32 91.32098  true 9.11026  0
 *  35 10.11874  true 8.95396  3
 *  92 32.88401  true 8.87089  0
 *  72 92.55791  true 9.46241  0
 *
 *  % java StdRandom 5
 *  seed = 1316600616575
 *  96 60.17070  true 8.72821  0
 *  79 32.01607  true 8.58159  0
 *  81 59.49065  true 9.10423  1
 *  96 51.65818  true 9.02102  0
 *  99 17.55771  true 8.99762  0
 *
 *  % java StdRandom 5 1316600616575
 *  seed = 1316600616575
 *  96 60.17070  true 8.72821  0
 *  79 32.01607  true 8.58159  0
 *  81 59.49065  true 9.10423  1
 *  96 51.65818  true 9.02102  0
 *  99 17.55771  true 8.99762  0
 *
 *
 *  Remark
 *  ------
 *    - Relies on randomness of nextDouble() method in java.util.Random
 *      to generate pseudo-random numbers in [0, 1).
 *
 *    - This library allows you to set and get the pseudo-random number seed.
 *
 *    - See http://www.honeylocust.com/RngPack/ for an industrial
 *      strength random number generator in Java.
 *
 ******************************************************************************/

 public final class main {
 
    private static int checker(int ii, String label, double observed, double expected) {
        double epsilon = 0.00001;
        if (Math.abs(expected - observed) < epsilon) {
            System.out.printf("%8.5f ", observed);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, observed %8.5f, expected %8.5f\n%d) ", label, observed, expected, ii);
        return 1;
    }
 
    private static int checker(int ii, String label, long observed, long expected) {
        if (observed == expected) {
            System.out.printf("%d ", observed);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, observed %d, expected %d\n%d) ", label, observed, expected, ii);
        return 1;
    }
 
    private static int checker(int ii, String label, String observed, String expected) {
        if (observed.equals(expected)) {
            System.out.printf("%s ", observed);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, observed %s, expected %s\n%d) ", label, observed, expected, ii);
        return 1;
    }
 
    private static int checker(int ii, String label, boolean observed, boolean expected) {
        if (observed == expected) {
            System.out.printf("%b ", observed);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, observed %b, expected %b\n%d) ", label, observed, expected, ii);
        return 1;
    }
 
    public static void main(String[] args) {
    
        int nloops = 10;
        long defaultSeed = 42;
        int errorCount = 0;
    
        System.out.println("Generate pseudo-random numbers from different distributions\nuniformInt, uniformDouble, bernoulli, gaussian, discreteProbabilities, discreteFrequencies, and uniformLong");
        StdRandom sr = new StdRandom();
        if (args.length > 0) nloops = Integer.parseInt(args[0]);
        if (args.length > 1) sr.setSeed(Long.parseLong(args[1]));
        else sr.setSeed(defaultSeed);
        System.out.printf("seed = %d\n", sr.getSeed());
        
        double[] probabilities = { 0.5, 0.3, 0.1, 0.1 };
        int[] frequencies = { 5, 3, 1, 1 };
        String[] alist = "A B C D E F G".split(" ");
        
        int[] ui = {30, 63, 23, 1, 7, 11, 22, 35, 41, 78};
        double[] ud = {14.86521, 48.84770, 17.14545, 94.53755, 14.67953, 38.06423, 35.3478, 93.67535, 26.09975, 18.27227};
        boolean[] ber = {true, false, true, false, false, false, false, true, false, true };
        double[] gau = {9.05537, 8.91823, 9.20316, 9.28476, 9.26173, 8.93586, 9.19661, 9.13266, 8.87282, 9.07785};
        int[] dpr = {0, 1, 0, 0, 0, 2, 0, 2, 1, 3};
        int[] dfr = {0, 0, 1, 0, 0, 1, 1, 0, 3, 3};
        long[] uj = {13281709636L, 5429620729L, 69111523770L, 18592916954L, 48422846408L, 63990802926L, 97678125691L, 29268664977L, 23454990537L, 4422716362L};
        String[] strArray = {"BDAGFCE", "FDCABEG", "FCAEDGB", "FBGDACE", "BCGDAEF", "GCEFBAD", "FDABEGC", "GBCAFED", "GFBDAEC", "DEBCFGA"};

        for (int ii = 0; ii < nloops; ii++) {
            System.out.printf("%d) ",   ii);
            errorCount += checker(ii, "uniformInt(100)", sr.uniformInt(100), ui[ii]);
            errorCount += checker(ii, "sr.uniformDouble(10.0, 99.0)", sr.uniformDouble(10.0, 99.0), ud[ii]);
            errorCount += checker(ii, "sr.bernoulli(0.5)", sr.bernoulli(0.5), ber[ii]);
            errorCount += checker(ii, "sr.gaussian(9.0, 0.2)", sr.gaussian(9.0, 0.2), gau[ii]);
            errorCount += checker(ii, "sr.discrete(probabilities)", sr.discrete(probabilities), dpr[ii]);
            errorCount += checker(ii, "sr.discrete(frequencies)", sr.discrete(frequencies), dfr[ii]);
            errorCount += checker(ii, "sr.uniformLong(100000000000L)", sr.uniformLong(100000000000L), uj[ii]);
            sr.shuffle(alist);
            String whole = "";
            for (String str : alist) {
                whole = whole.concat(str);
            }
            errorCount += checker(ii, "strArray", whole, strArray[ii]);
            System.out.println();
        }
        
        assert errorCount == 0;
        System.out.println("Success!");
    }

}
