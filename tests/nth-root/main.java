class main {

    // Smaller errorTolerance denotes more accuracy
    private static final double errorTolerance = 1e-4;

    public static double nthRoot(int m, int n) {
        if (n == 1) {
            return m;
        }

        double xPre = (int)(Math.random() * 10) + 1;

        // Initializing difference 
        // between two roots
        double delX = Integer.MAX_VALUE;

        // xK denotes current value of x
        double xK = 0;

        // Loop until we reach the 
        // desired accuracy
        while (delX > errorTolerance) {
            // Calculating the current value from 
            // the previous value by Newton's method
            xK = ((n - 1.0) * xPre + (double) m / Math.pow(xPre, n - 1)) / (double) n;
            delX = Math.abs(xK - xPre);
            xPre = xK;
        }

        return xK;
    }

    public static void main(String[] args) {
        int n = 13, m = 1234567;

        double nthRootValue = nthRoot(m, n);
        String label = String.format("n=%d, m=%d, nthRootValue=%e", n, m, nthRootValue);
        int errorCount = Checkers.withinTolerance(label, 2.941562D, nthRootValue, errorTolerance);
    }
}
