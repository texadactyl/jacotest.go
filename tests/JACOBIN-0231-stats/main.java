public class main {

    public static void main(String args[]) {
        String msg = "Testing basic statistical functions and a square root algorithm";
        System.out.println(msg);

        int arraySize = 1000000;
        double[] x_elems = new double[arraySize];
        double[] y_elems = new double[arraySize];
        double[] z_elems = new double[arraySize];
        System.out.println("Library lib will be instantiated .....");
        Library lib = new Library();
        System.out.println("Library lib was instantiated"); // <------------- Never gets here

        long t1 = System.currentTimeMillis();

        for (int ndx = 0; ndx < arraySize; ndx++) {
            double dblx = (double) (ndx % 256);
            x_elems[ndx] = dblx;
            y_elems[ndx] = -dblx;
            z_elems[ndx] = Math.sin(dblx);
        }
        double[] output = lib.meanStdev(x_elems);
        double x_mean = output[0];
        double x_stdev = output[1];
        output = lib.meanStdev(y_elems);
        double y_mean = output[0];
        double y_stdev = output[1];
        output = lib.meanStdev(z_elems);
        double z_mean = output[0];
        double z_stdev = output[1];

        double correl_x_y = lib.correlation(x_elems, y_elems);
        double correl_x_z = lib.correlation(x_elems, z_elems);

        long t2 = System.currentTimeMillis();
        double elapsedSeconds = (double) (t2 - t1) / 1000.0;

        System.out.printf("array sizes: %d\n", x_elems.length);
        System.out.printf("x-mean: %f\n", x_mean);
        System.out.printf("x-stdev: %f\n", x_stdev);
        System.out.printf("y-mean: %f\n", y_mean);
        System.out.printf("y-stdev: %f\n", y_stdev);
        System.out.printf("z-mean: %f\n", z_mean);
        System.out.printf("z-stdev: %f\n", z_stdev);
        System.out.printf("x - y correlation: %f\n", correl_x_y);
        System.out.printf("x - z correlation: %f\n", correl_x_z);
        System.out.printf("elapsed time: %.3f seconds\n", elapsedSeconds);
    }

}
