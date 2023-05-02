
public class main {
    public static void main(String[] args) {
        int IMAX = 3;
        int JMAX = 4;
        int KMAX = 5;

        String msg = "Testing 2D and 3D arrays of type int, float, double, and String";
        System.out.println(msg);

        // 2D arrays
        int[][] i2d = new int[IMAX][JMAX];
        float[][] f2d = new float[IMAX][JMAX];
        double[][] d2d = new double[IMAX][JMAX];
        String[][] s2d = new String[IMAX][JMAX];

        // 3D arrays
        int[][][] i3d = new int[IMAX][JMAX][KMAX];
        float[][][] f3d = new float[IMAX][JMAX][KMAX];
        double[][][] d3d = new double[IMAX][JMAX][KMAX];
        String[][][] s3d = new String[IMAX][JMAX][KMAX];

        for (int ii = 0; ii < IMAX; ++ii) {
            for (int jj = 0; jj < JMAX; ++jj) {
                i2d[ii][jj] = ii + jj + 1;
                f2d[ii][jj] = (float) i2d[ii][jj];
                d2d[ii][jj] = (double) i2d[ii][jj];
                s2d[ii][jj] = String.valueOf(i2d[ii][jj]);
                for (int kk = 0; kk < KMAX; ++kk) {
                    i3d[ii][jj][kk] = ii + jj + kk + 10;
                    f3d[ii][jj][kk] = (float) i2d[ii][jj];
                    d3d[ii][jj][kk] = (double) i2d[ii][jj];
                    s3d[ii][jj][kk] = String.valueOf(i2d[ii][jj]);
                }
            }
        }

        for (int ii = 0; ii < IMAX; ++ii) {
            for (int jj = 0; jj < JMAX; ++jj) {
                System.out.printf("2D ii=%d, jj=%d: %d %f %f %s\n", ii, jj, i2d[ii][jj], f2d[ii][jj], d2d[ii][jj], s2d[ii][jj]);
                for (int kk = 0; kk < KMAX; ++kk) {
                    System.out.printf("3D ii=%d, jj=%d, kk=%d: %d %f %f %s\n", ii, jj, kk, i3d[ii][jj][kk], f3d[ii][jj][kk], d3d[ii][jj][kk], s3d[ii][jj][kk]);
                }
            }
        }
    }
}
