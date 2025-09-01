public class main {

	final static int NBYTES = 1000;
	final static int NLOOPS = 100;
	final static double MAX_ASSIGN_PCT = 20.0; // max pct for byte array assignment

	public static double roundedPct(long inputMsecs, double secsOverall) {	
		double pct;
		if (secsOverall < 0.001) { // Divisor too small?
    		System.out.println("*** ERROR, from roundedPct: Overall elapsed time in seconds is too small - not useful for computing percentages!"); 
    		return -42.0;
		}
		pct = ((double) inputMsecs) * 0.1 / secsOverall;
		return pct;
	}

    public static void main(String[] args) {
    
    	System.out.println("Timings for allocation, assignment, deletion, and gc");
    	System.out.print("Byte allocation size: ");
    	System.out.println(NBYTES);
    	System.out.print("Loop count: ");
    	System.out.println(NLOOPS);
    	
		double secsOverall = 0.0;
    	long t1, t2, t3, t4, t5;
    	long et_allocate = 0, et_assign = 0, et_null = 0, et_gc = 0;
    	
    	long t1_overall = System.currentTimeMillis();
    	for (int ii = 0; ii < NLOOPS; ++ii) {
    		t1 = System.currentTimeMillis();
    		
    		// Allocate
			byte [] bites = new byte [NBYTES];
			t2 = System.currentTimeMillis();
			
			// Assign in loop
			for (int jj = 0; jj < NBYTES; ++jj)
				bites[jj] = 0x00;
			t3 = System.currentTimeMillis();
			
			// Set null
			bites = null;
			t4 = System.currentTimeMillis();
			
			// Collect garbage
		    System.gc();
			t5 = System.currentTimeMillis();
			
			// Increment
			et_allocate += t2 - t1;
			et_assign += t3 - t2;
			et_null += t4 - t3;
			et_gc += t5 - t4;
        }        
    	long t2_overall = System.currentTimeMillis();
    	
    	secsOverall = (double) (t2_overall - t1_overall) / 1000.0;
    	System.out.printf("Overall elapsed time = %.0f seconds\n", secsOverall); 
    	
    	double pct_allocate = roundedPct(et_allocate, secsOverall);
    	System.out.printf("Pct spent in new byte [...] = %.2f\n", pct_allocate); 
    	
    	double pct_assign = roundedPct(et_assign, secsOverall);
    	System.out.printf("Pct spent in loop-byte-assignment = %.2f\n", pct_assign); 
    	
    	double pct_null = roundedPct(et_null, secsOverall);
    	System.out.printf("Pct spent in setting byte array null = %.2f\n", pct_null); 
    	
    	double pct_gc = roundedPct(et_gc, secsOverall);
    	System.out.printf("Pct spent in gc = %.2f\n", pct_gc); 

    	double pct_other = 100.0 - pct_allocate - pct_assign - pct_null - pct_gc;
    	System.out.printf("Pct spent in other = %.2f\n", pct_other); 

    	Checkers.theEnd(0);
    	
    }
}
