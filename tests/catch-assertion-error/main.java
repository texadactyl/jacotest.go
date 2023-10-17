public class main {
	
	public static void main(String[] args) {
		int x = 1;
		int y = 2;
		System.out.println("x=1; y=2; assert x == y; .....");
		try {
			assert x == y;
			System.out.println("*** ERROR, failed to catch the AssertionError");
			System.exit(1);
		} catch (AssertionError ae) {
			System.out.println("Caught the Assertion Error");
			System.exit(0);
		}
	}


}

