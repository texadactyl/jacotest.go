public class main {

    public static void main(String args[]) {
        String msg = "Testing basic statistical functions and a square root algorithm";
        System.out.println(msg);
        
        Helpers hh = new Helpers();

        int arraySize = 1000;
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
            z_elems[ndx] = Library.squareRoot(dblx);
        }
        double[] output = Library.meanStdev(x_elems);
        double x_mean = output[0];
        double x_stdev = output[1];
        output = Library.meanStdev(y_elems);
        double y_mean = output[0];
        double y_stdev = output[1];
        output = Library.meanStdev(z_elems);
        double z_mean = output[0];
        double z_stdev = output[1];

        double correl_x_y = Library.correlation(x_elems, y_elems);
        double correl_x_z = Library.correlation(x_elems, z_elems);

        long t2 = System.currentTimeMillis();
        double elapsedSeconds = (double) (t2 - t1) / 1000.0;

        hh.printLabeledString("array sizes", String.valueOf(x_elems.length));
        hh.printLabeledDouble("x-mean", x_mean);
        hh.printLabeledDouble("x-stdev", x_stdev);
        hh.printLabeledDouble("y-mean", y_mean);
        hh.printLabeledDouble("y-stdev", y_stdev);
        hh.printLabeledDouble("z-mean", z_mean);
        hh.printLabeledDouble("z-stdev", z_stdev);
        hh.printLabeledDouble("x - y correlation", correl_x_y);
        hh.printLabeledDouble("x - z correlation", correl_x_z);
        hh.printLabeledDouble("elapsed time (seconds)", elapsedSeconds);
    }

}
