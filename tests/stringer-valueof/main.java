public class main {

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
        errorCount += Checkers.checker("bb", "66", str);
        
        str = String.valueOf(cc);
        errorCount += Checkers.checker("cc", "A", str);
        
        str = String.valueOf(ca);
        errorCount += Checkers.checker("ca", "ABCDEFGH", str);
        
        str = String.valueOf(ca, 2, 3);
        errorCount += Checkers.checker("ca subarray", "CDE", str);
        
        str = String.valueOf(dd);
        errorCount += Checkers.checker("dd", "3.0", str);
        
        str = String.valueOf(ff);
        errorCount += Checkers.checker("ff", "5.0", str);
        
        str = String.valueOf(ii);
        errorCount += Checkers.checker("ii", "7", str);
        
        str = String.valueOf(jj);
        errorCount += Checkers.checker("jj", "11", str);
        
        str = String.valueOf(ss);
        errorCount += Checkers.checker("ss", "13", str);
        
        str = String.valueOf(zz);
        errorCount += Checkers.checker("zz true", "true", str);
        
		zz = false;
        str = String.valueOf(zz);
        errorCount += Checkers.checker("zz false", "false", str);
        
    	Checkers.theEnd(errorCount);

    }
    
}
