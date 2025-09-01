public class main {

    public static void main(String[] args) {
    
        int IMAX = 3;
        int JMAX = 4;
        int KMAX = 5;
        int errorCount = 0;

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

		// Fill arrays
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

		// Check arrays
        for (int ii = 0; ii < IMAX; ++ii) {
            for (int jj = 0; jj < JMAX; ++jj) {
                // DEBUGGING System.out.printf("2D ii=%d, jj=%d: %d %f %f %s\n", ii, jj, i2d[ii][jj], f2d[ii][jj], d2d[ii][jj], s2d[ii][jj]);
                if (i2d[ii][jj] != ii + jj + 1) {
                	System.out.print("*** ERROR in 2D when ii=");
                	System.out.print(ii);
               		System.out.print(", jj=");
                	System.out.println(jj);
                	errorCount += 1;
                }
                for (int kk = 0; kk < KMAX; ++kk) {
                    // DEBUGGING System.out.printf("3D ii=%d, jj=%d, kk=%d: %d %f %f %s\n", ii, jj, kk, i3d[ii][jj][kk], f3d[ii][jj][kk], d3d[ii][jj][kk], s3d[ii][jj][kk]);
		            if (i3d[ii][jj][kk] != ii + jj + kk + 10) {
		            	System.out.print("*** ERROR in 3D when ii=");
		            	System.out.print(ii);
		           		System.out.print(", jj=");
		            	System.out.print(jj);
		           		System.out.print(", kk=");
		            	System.out.println(kk);
		            	errorCount += 1;
		            }
		    	}
            }
        }

        Checkers.theEnd(errorCount);

    }
    
}
