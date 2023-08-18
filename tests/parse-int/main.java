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
			try {
				observedResult = Integer.parseInt(input, radix);
				System.out.println(" ***ERROR, did not throw an exception");
				return 1;
			} catch (NumberFormatException ex) {
				System.out.println(" threw NumberFormatException as expected");
				return 0;
			} catch (Exception ex) {
				System.out.println(" ***ERROR, threw an unexpected type of exception");
				ex.printStackTrace();
				return 1;
			}
		}
		
		try {
			observedResult = Integer.parseInt(input, radix);
		} catch (Exception ex) {
			System.out.println(" ***ERROR, threw an unexpected exception");
			ex.printStackTrace();
			return 1;
		}

		if (observedResult == expectedResult) {
			System.out.println(" --- ok");
			return 0;
		}
		
		System.out.print(" ***ERROR, observedResult: ");
		System.out.println(observedResult);
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
     	errorCount += testParseInt("2147483648", 10, 0,true);
     	errorCount += testParseInt("99", 8, 0, true);
     	errorCount += testParseInt("Kona", 10, 0, true);
     	errorCount += testParseInt("Kona", 27, 411787, false);
     	
     	assert errorCount == 0;
	}
	
}
