/*
    Mini version of fannkuch.java in Jacotest case benchmark-game-fannkuch-redux
*/

public class fannkuchmini implements Runnable {

    // Number of worker threads.
    private final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    // run() constants
    private final int NLOOPS = 1000;
	private final int EXP_ITERATIONS = 32;
    
    // Instantiate fannkuchmini object.
    public fannkuchmini() {
        System.out.format("fannkuchmini object instantiation: Number of worker threads = %d\n", NUM_THREADS);
    }

    public void execute() {
    
        Thread[] thArray = new Thread[NUM_THREADS];
        for (int i = 0; i < thArray.length; i++)
        {
            thArray[i] = new Thread(this);
            thArray[i].start();
            System.out.format("execute:Started name=%s, id=%d .....\n", thArray[i].getName(), thArray[i].threadId());
        }
        System.out.format("execute: Thread start count = %d\n", thArray.length);

        final long t1 = System.currentTimeMillis();
        for (Thread t : thArray)
        {
            try {
                System.out.format("execute: Try to join name=%s, id=%d .....\n", t.getName(), t.threadId());
                t.join();
            }
            catch (InterruptedException ie) {
                System.out.println("execute: Interrupted!\n");
                System.exit(86); 
            }
        }
        final long t2 = System.currentTimeMillis();
        
        double elapsedSeconds = (double)(t2 - t1) / 1000.0;
        System.out.format("execute: All threads joined, elapsed time (seconds): %.3f\n", elapsedSeconds);

    }

    // Thread.start() --> run()
    public void run() {
    
        double dblIn, dblOut;
    
        for ( int ix = 0; ix < NLOOPS; ++ix) {
            dblIn = ix;
            dblOut = exp(dblIn);
        }
    }

    // Taylor series: e^x = 1 + x + (x^2)/2! + (x^3)/3! + (x^4)/4! + ...
	public double exp(double arg) {
		double divisor = 1.0;
		double output;
		for (int ii = 0; ii < EXP_ITERATIONS; ii++)
			divisor *= 2.0;
		output = 1.0 + arg / divisor;
		for (int ii = 0; ii < EXP_ITERATIONS; ii++) {
			output *= output;
		}
		return output;
	}

 }
