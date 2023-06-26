public class main {
    public static void main(String[] args) {
    
    	System.out.println("Try-Throw-Catch exercise");
    	
    	int errorCount = 0;
    	boolean finny = false;
		try {
			double dud = Double.valueOf("foobar");
			System.out.print("*** ERROR, I should not get here! dud=");
			System.out.println(dud);
			++errorCount;
		} catch (Exception ex) {
			System.out.println("Caught the throw!");
			System.out.println(ex);
		} finally {
			System.out.println("Finally!");
			finny = true;
		}
		if (! finny) {
			System.out.println("*** ERROR, I did not pass through finally!");
			++errorCount;
		}
		
		if (errorCount == 0) {
            System.out.println("No errors detected");
            System.exit(0);
        } else {
            System.out.print("Number of errors = ");
            System.out.println(String.valueOf(errorCount));
			System.exit(1);
        }
    }
}
