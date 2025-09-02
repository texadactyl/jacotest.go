public class main {

	private static int testParseInt(String input, int radix, int expectedResult, boolean flagException) {
		int observedResult;
		System.out.print("parseIntTest input: ");
		System.out.print(input);
		System.out.print(", radix: ");
		System.out.print(radix);
		System.out.print(", expectedResult: ");
		System.out.print(expectedResult);
		System.out.print(", flagException: ");
		System.out.print(flagException);
		
		if (flagException) {
			// Expecting a NumberFormatException
			try {
				observedResult = Integer.parseInt(input, radix);
				System.out.println(" ***ERROR, did not throw an exception");
				return 1;
			} catch (NumberFormatException ex) {
				System.out.println(" --- ok, threw Number Format Exception as expected");
				return 0;
			}
		}
		
		// Not expecting an exception
		try {
			observedResult = Integer.parseInt(input, radix);
		} catch (NumberFormatException ex) {
			System.out.printf("\n***ERROR, unexpected NumberFormatxception\n");
			return 1;
		}
		if (observedResult == expectedResult) {
			System.out.println(" --- ok, good comparison");
			return 0;
		}
		
		System.out.printf(" ***ERROR, comparison failed, expectedResult: %d, observedResult: %d\n", expectedResult, observedResult);
		return 1;
		
	}

    public static void main(String args[]) {
    	int errorCount = 0;
    	System.out.println("Integer parseInt testing");
		errorCount += testParseInt("0", 10, 0, false);
     	errorCount += testParseInt("473", 10, 473, false);
     	errorCount += testParseInt("+42", 10, 42, false);
     	errorCount += testParseInt("-0", 10, 0, false);
     	errorCount += testParseInt("-ff", 16, -255, false);
     	errorCount += testParseInt("1100110", 2, 102, false);
     	errorCount += testParseInt("2147483647", 10, 2147483647, false);
     	errorCount += testParseInt("-2147483648", 10, -2147483648, false);
     	errorCount += testParseInt("2147483648", 10, 0, true);
     	errorCount += testParseInt("99", 8, 0, true);
     	errorCount += testParseInt("Kona", 10, 0, true);
     	errorCount += testParseInt("100000", 27, 14348907, false);
     	
     	Checkers.theEnd(errorCount);
	}
	
}
