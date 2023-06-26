import java.math.MathContext;
import java.math.BigDecimal;

public class main {

	final static int NBYTES = 10000;
	final static int NLOOPS = 3;
	final static double MAX_ASSIGN_PCT = 2.0; // max pct for byte array assignment
	static double secs_overall = 0.0;
	static MathContext mc = new MathContext(4);

	public static double roundedPct(long inputMsecs) {		
		double pct = ((double) inputMsecs) * 0.1 / secs_overall;
		BigDecimal bd = new BigDecimal(pct, mc);
		return bd.doubleValue();
	}

    public static void main(String[] args) {
    
    	System.out.println("Timings for allocation, assignment, deletion, and gc");
    	System.out.print("Byte allocation size: ");
    	System.out.println(NBYTES);
    	System.out.print("Loop count: ");
    	System.out.println(NLOOPS);
    	
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
    	
    	secs_overall = (double) (t2_overall - t1_overall) / 1000.0;
    	System.out.print("Overall elapsed time = "); 
    	System.out.print(secs_overall); 
    	System.out.println(" seconds"); 
    	
    	double pct_allocate = roundedPct(et_allocate);
    	System.out.print("% spent in new byte [...] = "); 
    	System.out.println(pct_allocate); 
    	
    	double pct_assign = roundedPct(et_assign);
    	System.out.print("% spent in loop-byte-assignment = "); 
    	System.out.println(pct_assign); 
    	
    	double pct_null = roundedPct(et_null);
    	System.out.print("% spent in setting byte array null = "); 
    	System.out.println(pct_null); 
    	
    	double pct_gc = roundedPct(et_gc);
    	System.out.print("% spent in gc = "); 
    	System.out.println(pct_gc); 
    	
    	if (pct_assign < MAX_ASSIGN_PCT) {
    		System.out.println("Success!");
    		System.exit(0);
    	} else {
    		System.out.print("*** ERROR, excessive time (");
    		System.out.print(pct_assign);
    		System.out.print(" %) spent in byte-assignment. Expecting it to be < ");
    		System.out.print(MAX_ASSIGN_PCT);
    		System.out.println("%");
    		System.exit(1);
    	}
    }
}
