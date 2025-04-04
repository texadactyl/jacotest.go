public class main {

	private static int checker(String label, String expectedValue, String observedValue) {
		if (observedValue.equals(expectedValue)) {
			System.out.printf("checker [%s]: OK expected=%s, observed=%s\n", label, expectedValue, observedValue);
			return 0;
		}
		System.out.printf("checker [%s]: *** ERROR, expected=%s, observed=%s\n", label, expectedValue, observedValue);
		return 1;
	}
	
	public static void main(String args[]) {
		int errCount = 0;
		int i42 = 42;
		int wint;
		short wshort;
		byte bite;
		boolean bewel;
		Integer II = Integer.valueOf(i42);
		Integer II2 = Integer.valueOf(i42+1);
		String str;
						
		wint = Integer.parseInt("42");
		str = String.format("%d", wint);
		errCount += checker("Integer.parseInt(\"42\")", "42", str);
				
		wint = Integer.parseInt("42", 16);
		str = String.format("%d", wint);
		errCount += checker("Integer.parseInt(\"42\", 16)", "66", str);
				
		wint = Integer.parseUnsignedInt("1047568");
		str = String.format("%d", wint);
		errCount += checker("Integer.parseInt(\"42\")", "1047568", str);
				
		wint = Integer.parseUnsignedInt("1047568", 16);
		str = String.format("%d", wint);
		errCount += checker("Integer.parseInt(\"42\", 16)", "17069416", str);
				
		wint = Integer.remainderUnsigned(1047568, 13);
		str = String.format("%d", wint);
		errCount += checker("Integer.parseInt(\"42\", 16)", "2", str);
				
		wint = Integer.reverse(1047568);
		str = String.format("%d", wint);
		errCount += checker("Integer.reverse(1047568)", "138407936", str);
				
		wint = Integer.reverseBytes(1047568);
		str = String.format("%d", wint);
		errCount += checker("Integer.reverseBytes(1047568)", "284954368", str);
				
		wint = Integer.rotateLeft(1047568, 3);
		str = String.format("%d", wint);
		errCount += checker("Integer.rotateLeft(1047568, 3)", "8380544", str);
				
		wint = Integer.rotateRight(1047568, 3);
		str = String.format("%d", wint);
		errCount += checker("Integer.rotateRight(1047568, 3)", "130946", str);
				
		wshort = II.shortValue();
		str = String.format("%d", wshort);
		errCount += checker("II.shortValue()", "42", str);
				
		wint = Integer.signum(-42);
		str = String.format("%d", wint);
		errCount += checker("Integer.signum(-42)", "-1", str);
				
		wint = Integer.signum(42);
		str = String.format("%d", wint);
		errCount += checker("Integer.signum(+42)", "1", str);
				
		wint = Integer.signum(0);
		str = String.format("%d", wint);
		errCount += checker("Integer.signum(0)", "0", str);
				
		wint = Integer.sum(-3, 100);
		str = String.format("%d", wint);
		errCount += checker("Integer.sum(-3, 100)", "97", str);
				
		if (errCount == 0)
			System.out.println("Success!");
		else {
			String errMsg = String.format("error count = %d", errCount);
			throw new AssertionError(errMsg);
		}
	}

}
