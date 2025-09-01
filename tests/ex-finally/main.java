import java.lang.NumberFormatException;

public class main {
    public static void main(String[] args) {
    
    	System.out.println("Try-Catch-Finally exercise");
    	boolean finny = false;
    	Double DBL = Double.valueOf(42.0);
    	double dbl = DBL.doubleValue();
    	if (dbl != 42.0) {
    	    String errMsg = String.format("*** ERROR, expected dbl=42.0, observed: %d\n", dbl);
    	    throw new AssertionError(errMsg);
    	}
    	System.out.println("Success :: Double.valueOf(42.0)");
    	DBL = Double.valueOf("42.0");
    	dbl = DBL.doubleValue();
    	if (dbl != 42.0) {
    	    String errMsg = String.format("*** ERROR, expected dbl=42.0 based on \"42.0\", observed: %d\n", dbl);
    	    throw new AssertionError(errMsg);
    	}
    	System.out.println("Success :: Double.valueOf(\"42.0\")");
		try {
			double dud = Double.valueOf("foobar");
			throw new AssertionError("*** ERROR, I did not catch an exception from Double.valueOf(\"foobar\")!");
		} catch (NumberFormatException ex) {
			System.out.println("Caught the Number Format Exception with Double.valueOf(\"foobar\")!"); // <============ not getting here
		} catch (Exception ex) {
			throw new AssertionError("Caught an unexpected exception!");
		} finally {
			System.out.println("Finally!");
			finny = true;
		}
		if (! finny) {
			throw new AssertionError("*** ERROR, I did not pass through finally!");
		}
		
		Checkers.theEnd(0);
    }
}
