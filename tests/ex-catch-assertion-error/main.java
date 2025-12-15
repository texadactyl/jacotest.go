public class main {
	
	public static void main(String[] args) {
		int x = 1;
		int y = 2;
		System.out.println("x=1; y=2; assert x == y; .....");
		try {
			assert x == y;
			throw new AssertionError("*** ERROR, failed to catch the AssertionError!");
		} catch (AssertionError ae) {
			System.out.println("Caught the Assertion Error");
			ae.printStackTrace();
			Checkers.theEnd(0);
		}
	}


}

