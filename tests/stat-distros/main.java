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
 
    static int nloops = 10;
    static long defaultSeed = 42;

    private static int checker(String label, double observed, double expected) {
        double epsilon = 0.00001;
        if (Math.abs(expected - observed) < epsilon) {
            System.out.printf("	ok %s %8.5f\n", label, observed);
            return 0;
        }
        System.out.printf("	*** ERROR :: %s, observed %8.5f, expected %8.5f\n", label, observed, expected);
        return 1;
    }
 
    private static int checker(String label, long observed, long expected) {
        if (observed == expected) {
            System.out.printf("	ok %s %d\n", label, observed);
            return 0;
        }
        System.out.printf("	*** ERROR :: %s, observed %d, expected %d\n", label, observed, expected);
        return 1;
    }
 
    private static int checker(String label, String observed, String expected) {
        if (observed.equals(expected)) {
            System.out.printf("	ok %s %s\n", label, observed);
            return 0;
        }
        System.out.printf("	*** ERROR :: %s, observed %s, expected %s\n", label, observed, expected);
        return 1;
    }
 
    private static int checker(String label, boolean observed, boolean expected) {
        if (observed == expected) {
            if (observed)
                System.out.printf("	ok %s true\n", label);
            else
                System.out.printf("	ok %s false\n", label);
            return 0;
        }
        if (observed)
            System.out.printf("	*** ERROR :: %s, observed true, expected false\n", label);
        else
            System.out.printf("	*** ERROR :: %s, observed false, expected true\n", label);
        return 1;
    }
  
    public static void main(String[] args) {
    
        int errorCount = 0;
        EVDummy ev = new EVDummy();
    
        System.out.println("Generate pseudo-random numbers from different distributions\nuniformInt, uniformDouble, bernoulli, gaussian, discreteProbabilities, discreteFrequencies, and uniformLong");
		String jvmPgmName = jj._getProgramName();
		System.out.printf("jvmPgmName=%s\n", jvmPgmName);
		if (jvmPgmName.equals("java"))
		    ev = new EVOpenJDK();
		else
		    ev = new EVJacobin();

        StdRandom sr = new StdRandom();
        if (args.length > 0) nloops = Integer.parseInt(args[0]);
        if (args.length > 1) sr.setSeed(Long.parseLong(args[1]));
        else sr.setSeed(defaultSeed);
        System.out.printf("seed = %d\n", sr.getSeed());
        
        double[] probabilities = { 0.5, 0.3, 0.1, 0.1 };
        int[] frequencies = { 5, 3, 1, 1 };
        String[] alist = "A B C D E F G".split(" ");
        
        for (int ii = 0; ii < nloops; ii++) {
            System.out.printf("Loop %d\n", ii);
            errorCount += checker("uniformInt(100)", sr.uniformInt(100), ev.ui[ii]);
            errorCount += checker("sr.uniformDouble(10.0, 99.0)", sr.uniformDouble(10.0, 99.0), ev.ud[ii]);
            errorCount += checker("sr.bernoulli(0.5)", sr.bernoulli(0.5), ev.ber[ii]);
            errorCount += checker("sr.gaussian(9.0, 0.2)", sr.gaussian(9.0, 0.2), ev.gau[ii]);
            errorCount += checker("sr.discrete(probabilities)", sr.discrete(probabilities), ev.dpr[ii]);
            errorCount += checker("sr.discrete(frequencies)", sr.discrete(frequencies), ev.dfr[ii]);
            errorCount += checker("sr.uniformLong(100000000000L)", sr.uniformLong(100000000000L), ev.uj[ii]);
            sr.shuffle(alist);
            String whole = "";
            for (String str : alist) {
                whole = whole.concat(str);
            }
            errorCount += checker("strArray", whole, ev.strArray[ii]);
        }
        
        assert errorCount == 0;
        System.out.println("Success!");
    }

}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {
    public static String _getProgramName() {
        return "java";
    } 
}

class EVDummy {
    String myName = "Mother";
    int[] ui;
    double[] ud;
    boolean[] ber;
    double[] gau;
    int[] dpr;
    int[] dfr;
    long[] uj;
    String[] strArray;
}

class EVJacobin extends EVDummy {
    EVJacobin() {
        this.myName = "EVJacobin";
        this.ui = new int[] {5, 1, 15, 19, 96, 23, 40, 31, 45, 55};
        this.ud = new double[] {15.87404, 93.80874, 98.76166, 26.63714, 61.42120, 34.29364, 94.73277, 23.61458, 48.29320, 60.60515};
        this.ber = new boolean[] {false, false, true, true, false, false, true, true, true, true };
        this.gau = new double[] {8.91113, 8.83861, 9.01789, 9.03865, 9.11618, 8.90280, 9.00782, 9.14889, 9.15073, 9.09568};
        this.dpr = new int[] {0, 0, 1, 0, 1, 2, 3, 0, 0, 1};
        this.dfr = new int[] {2, 1, 1, 0, 0, 1, 2, 0, 0, 1};
        this.uj = new long[] {78730882092L, 38201194239L, 12959715377L, 19209992795L, 92649943915L, 10735306777L, 25477566989L, 18998320084L, 8559995490L, 13887303704L};
        this.strArray = new String[] {"AGCDBEF", "ADFCGEB", "GCDFAEB", "BECGFAD", "BAGCFED", "BEADFCG", "GDFBAEC", "CADEFGB", "EBAGDFC", "FDBCGEA"};
    }
}

class EVOpenJDK extends EVDummy {
    EVOpenJDK() {
        this.myName = "EVOpenJDK";
        this.ui = new int[] {30, 63, 23, 1, 7, 11, 22, 35, 41, 78};
        this.ud = new double[] {14.86521, 48.84770, 17.14545, 94.53755, 14.67953, 38.06423, 35.3478, 93.67535, 26.09975, 18.27227};
        this.ber = new boolean[] {true, false, true, false, false, false, false, true, false, true };
        this.gau = new double[] {9.05537, 8.91823, 9.20316, 9.28476, 9.26173, 8.93586, 9.19661, 9.13266, 8.87282, 9.07785};
        this.dpr = new int[] {0, 1, 0, 0, 0, 2, 0, 2, 1, 3};
        this.dfr = new int[] {0, 0, 1, 0, 0, 1, 1, 0, 3, 3};
        this.uj = new long[] {13281709636L, 5429620729L, 69111523770L, 18592916954L, 48422846408L, 63990802926L, 97678125691L, 29268664977L, 23454990537L, 4422716362L};
        this.strArray = new String[] {"BDAGFCE", "FDCABEG", "FCAEDGB", "FBGDACE", "BCGDAEF", "GCEFBAD", "FDABEGC", "GBCAFED", "GFBDAEC", "DEBCFGA"};
    }
}

