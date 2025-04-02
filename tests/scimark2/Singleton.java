public class Singleton {

    private static final Singleton INSTANCE = new Singleton();
    private Singleton() {} // Private constructor to prevent instantiation

    // Singleton public mutable fields.
    public long valueFFT, valueFFTi, valueSOR, valueSM, valueLU;
    public double valueMC;

    // Member functions .....

    // Get the singleton object instance.
    public static Singleton getInstance() {
        return INSTANCE;
    }

    // Simple checksum of a vector of doubles.
    public long sumVector(double[] data) {
        long checksum = 0;

        for (double value : data) {
            checksum ^= Double.doubleToLongBits(value); // XOR each double's raw bits
        }

        return checksum;
    }

    public long sumMatrix(double[][] data) {

        int nrows = data.length;
        int ncols = data[0].length;
        long checksum = 0;
        int ix = 0, jx = 0;

        try {
            for (ix = 0; ix < nrows; ix++) {
                for (jx = 0; jx < ncols; jx++) {
                    checksum ^= Double.doubleToLongBits(data[ix][jx]);
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.printf("IndexOutOfBoundsException at row %d, col %d\n", ix, jx);
            System.exit(1);
        } catch (NullPointerException ex) {
            System.out.printf("NullPointerException at row %d, col %d\n", ix, jx);
            System.exit(1);
        } catch (VirtualMachineError ex) {
            System.out.printf("VirtualMachineError at row %d, col %d\n", ix, jx);
            System.exit(1);
        }

        return checksum;
    }

    public void printMatrix(double[][] data) {

        int nrows = data.length;
        int ncols = data[0].length;
        System.out.printf("sumMatrix: nrows=%d, ncols=%d\n", nrows, ncols);
        int ix = 0, jx = 0;

        try {
            for (ix = 0; ix < nrows; ix++) {
                for (jx = 0; jx < ncols; jx++) {
                    System.out.printf("%8.1f  ", data[ix][jx]);
                }
                System.out.println();
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.printf("IndexOutOfBoundsException at row %d, col %d\n", ix, jx);
            System.exit(1);
        } catch (NullPointerException ex) {
            System.out.printf("NullPointerException at row %d, col %d\n", ix, jx);
            System.exit(1);
        } catch (VirtualMachineError ex) {
            System.out.printf("VirtualMachineError at row %d, col %d\n", ix, jx);
            System.exit(1);
        }

    }

    // Print the object fields.
    public void printFields() {
        System.out.println("Singleton checksum values:");
        System.out.printf("\tFFT: %d, FFTi: %d, SOR: %d, MC: %f, SM: %d, LU: %d\n",
                valueFFT, valueFFTi, valueSOR, valueMC, valueSM, valueLU );
    }
}
