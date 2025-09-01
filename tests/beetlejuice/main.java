public class main {
	private static int NLOOPS = 1000;
	
    public static void main(String[] args) {
		String beetlejuice = "---";
		String theRealBeetlejuice = "Beetlejuice!";
		String etl = "etl";
		byte[] bb = {66, 101, 101, 116, 108, 101, 106, 117, 105, 99, 101, 33};
		
		for (int ii = 0; ii < NLOOPS; ii++) { 
			beetlejuice = new String(bb, 2, 3);
			if (!beetlejuice.equals(etl)) {
				String errMsg = String.format("*** ERROR, expected: %s, observed: %s", etl, beetlejuice);
				throw new AssertionError(errMsg);
			}
			beetlejuice = new String(bb);
			if (!beetlejuice.equals(theRealBeetlejuice)) {
				String errMsg = String.format("*** ERROR, expected: %s, observed: %s", theRealBeetlejuice, beetlejuice);
				throw new AssertionError(errMsg);
			}
		}
		System.out.println(beetlejuice);
		System.out.println(beetlejuice);
		System.out.println(beetlejuice);
		
		Checkers.theEnd(0);
	}
}
