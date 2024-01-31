import java.lang.NumberFormatException;

public class main {
    public static void main(String[] args) {
    
    	System.out.println("Try-Catch-Finally exercise");
    	boolean finny = false;
		try {
			double dud = Double.valueOf("foobar");
			throw new AssertionError("*** ERROR, I did not catch an exception from Double.valueOf(\"foobar\")!");
		} catch (NumberFormatException ex) {
			System.out.println("Caught the Number Format Exception!");
		} catch (Exception ex) {
			throw new AssertionError("Caught an unexpected exception!");
		} finally {
			System.out.println("Finally!");
			finny = true;
		}
		if (! finny) {
			throw new AssertionError("*** ERROR, I did not pass through finally!");
		}
    }
}
