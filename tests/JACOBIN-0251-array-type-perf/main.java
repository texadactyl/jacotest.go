public class main {

	final static int NELEMS = 1000000;
	final static int NLOOPS = 1000000;

	public static void reporter(String label, long t1, long t2) {	
    	double et = (double) (t2 - t1) / 1000.0;
    	System.out.print(label); 
   		System.out.print(" element assignment time = "); 
    	System.out.print(et); 
    	System.out.println(" seconds"); 
    	assert (et >= 0.0);
	    System.gc();
	}
    	
    public static void main(String[] args) {
    
    	System.out.println("Array performance by variable type");
    	System.out.print("Element allocation size: ");
    	System.out.println(NELEMS);
    	System.out.print("Loop count: ");
    	System.out.println(NLOOPS);
    	
    	long t1, t2;
    	
		byte [] byteArray = new byte [NELEMS];
		t1 = System.currentTimeMillis();			
		for (int jj = 0; jj < NELEMS; ++jj)
			byteArray[jj] = 0x00;
		t2 = System.currentTimeMillis();
		byteArray = null;
		reporter("byte array", t1, t2);
    	
    	int [] intArray = new int [NELEMS];
 		t1 = System.currentTimeMillis();			
		for (int jj = 0; jj < NELEMS; ++jj)
			intArray[jj] = 0;
		t2 = System.currentTimeMillis();
		intArray = null;
		reporter("int array", t1, t2);
    	
     	char [] charArray = new char [NELEMS];
 		t1 = System.currentTimeMillis();			
		for (int jj = 0; jj < NELEMS; ++jj)
			charArray[jj] = '*';
		t2 = System.currentTimeMillis();
		charArray = null;
		reporter("char array", t1, t2);
   	
    	float [] floatArray = new float [NELEMS];
 		t1 = System.currentTimeMillis();			
		for (int jj = 0; jj < NELEMS; ++jj)
			floatArray[jj] = 0.0f;
		t2 = System.currentTimeMillis();
		floatArray = null;
		reporter("float array", t1, t2);
    	
    	double [] doubleArray = new double [NELEMS];
 		t1 = System.currentTimeMillis();			
		for (int jj = 0; jj < NELEMS; ++jj)
			doubleArray[jj] = 0.0;
		t2 = System.currentTimeMillis();
		doubleArray = null;
		reporter("double array", t1, t2);
    	
    	String [] stringArray = new String [NELEMS];
 		t1 = System.currentTimeMillis();			
		for (int jj = 0; jj < NELEMS; ++jj)
			stringArray[jj] = "xy";
		t2 = System.currentTimeMillis();
		stringArray = null;
		reporter("String array", t1, t2);

     }
}
