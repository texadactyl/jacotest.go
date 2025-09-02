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


    public static void main(String[] args) {
    
        String msg = "SciMark2: Benchmark measuring performance of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.";
        System.out.println(msg);
        System.out.println("URL: https://math.nist.gov/scimark2/index.html");
        Singleton sgl = Singleton.getInstance();
        Random R = new Random();
        int errorCount = 0;

        double curfew = Constants.CURFEW;

        // Pre-set default constants.
        int FFT_size = Constants.TINY_FFT_SIZE;
        int SOR_size = Constants.TINY_SOR_SIZE;
        int Sparse_size_M = Constants.TINY_SPARSE_SIZE_M;
        int Sparse_size_nz = Constants.TINY_SPARSE_SIZE_nz;
        int LU_size = Constants.TINY_LU_SIZE;

        // look for runtime options
        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("-h") ||
                    args[0].equalsIgnoreCase("-help")) {
                System.out.println("Usage: [ -large | -tiny ] [ curfew in seconds ]");
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
            if (args[current_arg].equalsIgnoreCase("-medium")) {
                FFT_size = Constants.MED_FFT_SIZE;
                SOR_size = Constants.MED_SOR_SIZE;
                Sparse_size_M = Constants.MED_SPARSE_SIZE_M;
                Sparse_size_nz = Constants.MED_SPARSE_SIZE_nz;
                LU_size = Constants.MED_LU_SIZE;

                current_arg++;
            }

            if (args.length > current_arg)
                curfew = Double.parseDouble(args[current_arg]);
        }

        // ========================================================= Run the benchmarks.

        // Take time t0.
        long t0 = System.currentTimeMillis();

        // Results in Hz.
        double[] result = new double[6];

        // Measure Hz in each function.
        result[1] = kernel.measureFFT(FFT_size, curfew, R);
        result[2] = kernel.measureSOR(SOR_size, curfew, R);
        result[3] = kernel.measureMonteCarlo(curfew);
        result[4] = kernel.measureSparseMatmult(Sparse_size_M, Sparse_size_nz, curfew, R);
        result[5] = kernel.measureLU(LU_size, curfew, R);
        result[0] = (result[1] + result[2] + result[3] + result[4] + result[5]) / 5;

        // tfinal = final time.
        // Compute elapsed time in seconds.
        long tfinal = System.currentTimeMillis();
        double elapsed_seconds = (tfinal - t0) / 1000.0;
        System.out.print("SciMark 2.0a, elapsed time in seconds = ");
        System.out.println(elapsed_seconds);

        //=================================================== Report results and values.

        System.out.print("FFT (");
        System.out.print(FFT_size);
        System.out.print("): ");
        System.out.print(result[1]);
        System.out.println(" Hz");

        System.out.print("SOR (");
        System.out.print(SOR_size);
        System.out.print("x");
        System.out.print(SOR_size);
        System.out.print("): ");
        System.out.print(result[2]);
        System.out.println(" Hz");

        System.out.print("Monte Carlo : ");
        System.out.print(result[3]);
        System.out.println(" Hz");

        System.out.print("Sparse matmult (N=");
        System.out.print(Sparse_size_M);
        System.out.print(", nz=");
        System.out.print(Sparse_size_nz);
        System.out.print("): ");
        System.out.print(result[4]);
        System.out.println(" Hz");

        System.out.print("LU (");
        System.out.print(LU_size);
        System.out.print("x");
        System.out.print(LU_size);
        System.out.print("): ");
        System.out.print(result[5]);
        System.out.println(" Hz");

        System.out.print("Composite Score: ");
        System.out.print(result[0]);
        System.out.println(" Hz");

        sgl.printFields();

        // Validate calculations.
        errorCount += Checkers.withinTolerance("FFT", -8989986044279245438L, sgl.valueFFT, 1.5);
        errorCount += Checkers.withinTolerance("FFT-inverse", 12423419474949865L, sgl.valueFFTi, 1.0);
        errorCount += Checkers.withinTolerance("LU", 4600112251643070182L, sgl.valueLU, 1.0);
        errorCount += Checkers.withinTolerance("SM", 11024028321273019L, sgl.valueSM, 1.0);
        errorCount += Checkers.withinTolerance("SOR", 0L, sgl.valueSOR, 1.0);
        
        Checkers.theEnd(errorCount);

    }

}
