import java.math.BigDecimal;

public class main {

	final static int NBYTES = 10000;
	final static int NLOOPS = 3;
	final static double MAX_ASSIGN_PCT = 20.0; // max pct for byte array assignment

	public static double roundedPct(long inputMsecs, double secsOverall) {	
		double pct;
		if (secsOverall < 0.001) { // Divisor too small?
    		System.out.println("WARNING from roundedPct: Overall elapsed time in seconds is too small - not useful for computing percentages!"); 
    		return -42.0;
		}
		pct = ((double) inputMsecs) * 0.1 / secsOverall;
		BigDecimal bd = new BigDecimal(pct);
		return bd.doubleValue();
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
    	System.out.print("Overall elapsed time = "); 
    	System.out.print(secsOverall); 
    	System.out.println(" seconds"); 
    	
    	double pct_allocate = roundedPct(et_allocate, secsOverall);
    	System.out.print("% spent in new byte [...] = "); 
    	System.out.println(pct_allocate); 
    	
    	double pct_assign = roundedPct(et_assign, secsOverall);
    	System.out.print("% spent in loop-byte-assignment = "); 
    	System.out.println(pct_assign); 
    	
    	double pct_null = roundedPct(et_null, secsOverall);
    	System.out.print("% spent in setting byte array null = "); 
    	System.out.println(pct_null); 
    	
    	double pct_gc = roundedPct(et_gc, secsOverall);
    	System.out.print("% spent in gc = "); 
    	System.out.println(pct_gc); 
    	System.out.println("End");
    	
    }
}
