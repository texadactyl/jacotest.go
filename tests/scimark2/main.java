import java.util.Properties;

/**
 * SciMark2: A Java numerical benchmark measuring performance
 * of computational kernels for FFTs, Monte Carlo simulation,
 * sparse matrix computations, Jacobi SOR, and dense LU matrix
 * factorizations.
 * <p>
 * Modifications
 * -------------
 * <p>
 * 2022-07-25  texadactyl     Added Stopwatch calls.
 **/


public class main {

  /* Benchmark 5 kernels with individual Mflops.
	 "results[0]" has the average Mflop rate.

  */


    public static void main(String args[]) {
    
        String msg = "SciMark2: Benchmark measuring performance of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.";
        System.out.println(msg);

        double min_time = Constants.RESOLUTION_DEFAULT;

        int FFT_size = Constants.FFT_SIZE;
        int SOR_size = Constants.SOR_SIZE;
        int Sparse_size_M = Constants.SPARSE_SIZE_M;
        int Sparse_size_nz = Constants.SPARSE_SIZE_nz;
        int LU_size = Constants.LU_SIZE;

        // look for runtime options

        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("-h") ||
                    args[0].equalsIgnoreCase("-help")) {
                System.out.println("Usage: [-large] [minimum_time]");
                return;
            }

            int current_arg = 0;
            if (args[current_arg].equalsIgnoreCase("-large")) {
                FFT_size = Constants.LG_FFT_SIZE;
                SOR_size = Constants.LG_SOR_SIZE;
                Sparse_size_M = Constants.LG_SPARSE_SIZE_M;
                Sparse_size_nz = Constants.LG_SPARSE_SIZE_nz;
                LU_size = Constants.LG_LU_SIZE;

                current_arg++;
            }

            if (args.length > current_arg)
                min_time = Double.valueOf(args[current_arg]).doubleValue();
        }


        // run the benchmark

        Stopwatch sw = new Stopwatch();
        sw.start();

        double res[] = new double[6];
        Random R = new Random(Constants.RANDOM_SEED);

        res[1] = kernel.measureFFT(FFT_size, min_time, R);
        res[2] = kernel.measureSOR(SOR_size, min_time, R);
        res[3] = kernel.measureMonteCarlo(min_time, R);
        res[4] = kernel.measureSparseMatmult(Sparse_size_M,
                Sparse_size_nz,
                min_time,
                R);
        res[5] = kernel.measureLU(LU_size, min_time, R);
        res[0] = (res[1] + res[2] + res[3] + res[4] + res[5]) / 5;

        double elapsed_seconds = sw.stop();


        // print out results

        System.out.print(String.format("SciMark 2.0a, elasped time in seconds = "));
        System.out.println(elapsed_seconds);
        System.out.println("URL: https://math.nist.gov/scimark2/index.html");
        System.out.print("Composite Score: ");
        System.out.println(res[0]);
        System.out.print("FFT (");
        System.out.print(FFT_size);
        System.out.print("): ");
        if (res[1] == 0.0)
            System.out.println("*** ERROR, INVALID NUMERICAL RESULT - FFT!");
        else
            System.out.println(res[1]);

        System.out.print("SOR (");
        System.out.print(SOR_size);
        System.out.print("x");
        System.out.print(SOR_size);
        System.out.print("): ");
        System.out.println(res[2]);
        
        System.out.print("Monte Carlo : ");
        System.out.println(res[3]);
        
        System.out.print("Sparse matmult (N=");
        System.out.print(Sparse_size_M);
        System.out.print(", nz=");
        System.out.print(Sparse_size_nz);
        System.out.print("): ");
        System.out.println(res[4]);
        
        System.out.print("LU (");
        System.out.print(LU_size);
        System.out.print("x");
        System.out.print(LU_size);
        System.out.print("): ");
        if (res[5] == 0.0)
            System.out.println("*** ERROR, INVALID NUMERICAL RESULT - LU!");
        else
            System.out.println(res[5]);

    }

}
