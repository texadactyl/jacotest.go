public class main {

    public static int isItTrue(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("Success :: %s=%s\n", label, expected);
            return 0;
        }
        System.out.printf("*** ERROR, %s expected=%s, observed=%s\n", label, expected, observed);
        return 1;
    }
    
    public static void main(String[] args) {
    
    	int errorCount = 0;
		String beetlejuice = "Beetlejuice";
		byte bb = 0x42; // "66"
		char cc = 'A';
		char [] ca = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
		double dd = 3.0d;
		float ff = 5.0f;
		int ii = 7;
		long jj = 11;
		short ss = 13;
		boolean zz = true;
    
    	System.out.println("Set a String array element to various String.valueOf arguments.");
    	String str;
    	
        str = String.valueOf(bb);
        errorCount += isItTrue("bb", "66", str);
        
        str = String.valueOf(cc);
        errorCount += isItTrue("cc", "A", str);
        
        str = String.valueOf(ca);
        errorCount += isItTrue("ca", "ABCDEFGH", str);
        
        str = String.valueOf(ca, 2, 3);
        errorCount += isItTrue("ca subarray", "CDE", str);
        
        str = String.valueOf(dd);
        errorCount += isItTrue("dd", "3.0", str);
        
        str = String.valueOf(ff);
        errorCount += isItTrue("ff", "5.0", str);
        
        str = String.valueOf(ii);
        errorCount += isItTrue("ii", "7", str);
        
        str = String.valueOf(jj);
        errorCount += isItTrue("jj", "11", str);
        
        str = String.valueOf(ss);
        errorCount += isItTrue("ss", "13", str);
        
        str = String.valueOf(zz);
        errorCount += isItTrue("zz true", "true", str);
        
		zz = false;
        str = String.valueOf(zz);
        errorCount += isItTrue("zz false", "false", str);
        
    	System.out.println("Did not crash!");
        assert (errorCount == 0);
		System.out.printf("Error count = %d\n", errorCount);

    }
    
}
