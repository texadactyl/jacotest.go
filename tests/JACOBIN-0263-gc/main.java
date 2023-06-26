public class main {

	final static int NBYTES = 10000;
	final static int NLOOPS = 1000;

    public static void main(String[] args) {
    
    	System.out.println("Allocation, deletion, gc, and timings");
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
    	
    	double secs_overall = (double) (t2_overall - t1_overall) / 1000.0;
    	System.out.print("Overall elapsed time = "); 
    	System.out.print(secs_overall); 
    	System.out.println(" seconds"); 
    	
    	double pct_allocate = (double) et_allocate * 0.1 / secs_overall;
    	System.out.print("% spent in new byte [...] = "); 
    	System.out.println(pct_allocate); 
    	
    	double pct_assign = (double) et_assign * 0.1 / secs_overall;
    	System.out.print("% spent in loop-byte-assignment = "); 
    	System.out.println(pct_assign); 
    	
    	double pct_null = (double) et_null * 0.1 / secs_overall;
    	System.out.print("% spent in setting byte array null = "); 
    	System.out.println(pct_null); 
    	
    	double pct_gc = (double) et_gc * 0.1 / secs_overall;
    	System.out.print("% spent in gc = "); 
    	System.out.println(pct_gc); 
    	
    	if (pct_assign < 20.0)
    		System.exit(0);
    	else {
    		System.out.println("*** ERROR, excessive time spent in loop-byte-assignment");
    		System.exit(1);
    	}
    }
}
