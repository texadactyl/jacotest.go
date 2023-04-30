
public class main {

    public static void main(String[] args)    {
        int IMAX = 3;
        int JMAX = 4;
        
        System.out.println("Testing 2D arrays of type int, float, and double");

        int[][] i2d = new int[IMAX][JMAX];
        float[][] f2d = new float[IMAX][JMAX];
        double[][] d2d = new double[IMAX][JMAX];
 
        for(int ii = 0; ii < IMAX; ++ii) {
            for(int jj = 0; jj < JMAX; ++jj) {
                i2d[ii][jj] = ii + jj + 1;
                System.out.print("ii="); System.out.print(ii); System.out.print(", jj="); System.out.print(jj); System.out.print(", int="); System.out.print(i2d[ii][jj]);
                f2d[ii][jj] = (float) i2d[ii][jj];
                System.out.print(", float="); System.out.print(f2d[ii][jj]);
                d2d[ii][jj] = (double) i2d[ii][jj];
                System.out.print(", double="); System.out.println(d2d[ii][jj]);
            }                
        }
        
		if(i2d[2][1] == 4 && f2d[2][1] == 4.0 && d2d[2][1] == 4.0) {
			System.out.println("Success :: i2d[2][1] == 4 && f2d[2][1] == 4.0 && d2d[2][1] == 4.0");
			return;
		}
		System.out.println("***FAILED");
     }
} 
