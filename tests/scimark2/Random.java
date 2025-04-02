
public class Random {

    private final int nvalues = 10;
    private final double[] values = new double[nvalues];
    private int ixvalues = 0;

    Random() {
        for (int ix = 0; ix < nvalues; ix++) {
            values[ix] = (double)(ix + 1) / 100.0;
        }
    }

    /**
     * Returns the next random number in the sequence.
     */
    public double nextDouble() {
        ixvalues++;
        if (ixvalues == nvalues)
            ixvalues = 0;
        try {
            Thread.sleep(1);
        } catch(InterruptedException ex) {
            System.out.println("\nInterruptedException!");
            System.exit(1);
        }
        return values[ixvalues];
    }

}
