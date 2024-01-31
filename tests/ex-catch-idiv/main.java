// division by zero to throw exception
public final class main {
    public static void main(String[] args) {
		int n = 6;
		int x = 0;
		try {
			int y = n/x;
		} catch (ArithmeticException ex) {
			System.out.println("Successfully caught a divide by zero.");
			System.exit(0);
		}
		throw new AssertionError("*** ERROR, Failed to catch a divide by zero.");
	}
}