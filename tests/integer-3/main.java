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
		Integer II = Integer.valueOf(i42);
		Integer IIW;
		String str;
		
		str = Integer.toBinaryString(i42);
		errCount += checker("toBinaryString", "101010", str);
		
		str = Integer.toHexString(i42);
		errCount += checker("toHexString", "2a", str);
		
		str = Integer.toOctalString(i42);
		errCount += checker("toOctalString", "52", str);
		
		str = II.toString();
		errCount += checker("toString - no arg", "42", str);
		
		str = Integer.toString(i42);
		errCount += checker("toString - i42", "42", str);
		
		str = Integer.toString(i42, 10);
		errCount += checker("toString - i42", "42", str);
		
		long longue = Integer.toUnsignedLong(i42);
		str = String.format("%d", longue);
		errCount += checker("toUnsignedString - no radix #1", "42", str);
		
		str = Integer.toUnsignedString(i42);
		errCount += checker("toUnsignedString - no radix #1", "42", str);
		
		str = Integer.toUnsignedString(-i42);
		errCount += checker("toUnsignedString - no radix #2", "4294967254", str);
		
		str = Integer.toUnsignedString(i42, 5);
		errCount += checker("toUnsignedString - radix=5 #1", "132", str);
		
		str = Integer.toUnsignedString(-i42, 16);
		errCount += checker("toUnsignedString - radix=16 #2", "ffffffd6", str);
		
		IIW = Integer.valueOf(42);
		str = String.format("%d", IIW.intValue());
		errCount += checker("Integer.valueOf(42)", "42", str);
		
		IIW = Integer.valueOf("42");
		str = String.format("%d", IIW.intValue());
		errCount += checker("Integer.valueOf(\"42\")", "42", str);
		
		IIW = Integer.valueOf("42", 16);
		str = String.format("%d", IIW.intValue());
		errCount += checker("Integer.valueOf(\"42\", 16)", "66", str);
		
		if (errCount == 0)
			System.out.println("Success!");
		else {
			String errMsg = String.format("error count = %d", errCount);
			throw new AssertionError(errMsg);
		}
	}

}
