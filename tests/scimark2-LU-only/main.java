/* 
    Extract from scimark2.
    N = 1 through 4 ===> Success. N > 4 ===> panic. 
    Panic text: go panic because of: interface conversion: interface {} is int, not int64

    Commenting out the LU calls and the check on its return value ==> success for several values of N > 4.
*/

public class main {

    static int N = 5; 
        
 	public static void main(String[] args) {

        Stopwatch Q = new Stopwatch();
        int maxSeconds = 2;
 	    double[][] data = new double[N][N];
        for (int ix = 0; ix < N; ix++) {
            for (int jx = 0; jx < N; jx++) {
                data[ix][jx] = ix * 1000 + jx;
            }
        }
        double[][] lu = new double[N][N];
        int[] pivot = new int[N];
        System.out.printf("measureLU: N=%d\n", N);

        int cycles = 1;
        int ret;
        while (true) {
            Q.start();
            for (int ix = 0; ix < cycles; ix++) {
                CopyMatrix(lu, data);
                ret = LU.factor(lu, pivot);
                if (ret != 0) {
                    String errMsg = String.format("LU.factor(lu, pivot) returned nonzero, cycle=%d", ix);
                    throw new AssertionError(errMsg);
                }
            }
            Q.stop();
            System.out.printf("measureLU: cycles=%d\n", cycles);
            if (Q.read() >= maxSeconds) break;

            cycles *= 2;
        }

        System.out.printf("measureLU: lu nrows=%d, ncols=%d\n", lu.length, lu[0].length);
        Singleton values = Singleton.getInstance();
        values.printMatrix(data);
        values.valueLU = values.sumMatrix(lu);
        System.out.println("measureLU: Singleton.valueLU done");
        values.printFields();
        long expected = 4559692312573116416L;
        if (values.valueLU != expected) {
            System.out.printf("LU output (%d) does not match expected value (%d)\n",
                values.valueLU, expected);
            System.exit(1);
        }
        System.out.println("Success!");

    }

    private static void CopyMatrix(double[][] dest, double[][] src) {
        int M = src.length;
        int N = src[0].length;

        int remainder = N & 3;         // N mod 4;

        for (int i = 0; i < M; i++) {
            double[] destVec = dest[i];
            double[] srcVec = src[i];
            System.arraycopy(srcVec, 0, destVec, 0, remainder);
            for (int j = remainder; j < N; j += 4) {
                destVec[j] = srcVec[j];
                destVec[j + 1] = srcVec[j + 1];
                destVec[j + 2] = srcVec[j + 2];
                destVec[j + 3] = srcVec[j + 3];
            }
        }
    }

}
