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
		Integer II = Integer.valueOf(i42);;
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
		
		str = Integer.toUnsignedString(i42);
		errCount += checker("toUnsignedString - no radix #1", "42", str);
		
		str = Integer.toUnsignedString(-i42);
		errCount += checker("toUnsignedString - no radix #2", "4294967254", str);
		
		str = Integer.toUnsignedString(i42, 5);
		errCount += checker("toUnsignedString - radix=5 #1", "132", str);
		
		str = Integer.toUnsignedString(-i42, 16);
		errCount += checker("toUnsignedString - radix=16 #2", "ffffffd6", str);
		
		byte bb = II.byteValue();
		str = String.format("%d", bb);
		errCount += checker("byteValue", "42", str);

		II = Integer.decode("42");
		str = II.toString();
		errCount += checker("decode + toString with no arg", "42", str);
		
		double dd = II.doubleValue();
		str = String.format("%.0f", dd);
		errCount += checker("doubleValue + String.format", "42", str);
				
		float ff = II.floatValue();
		str = String.format("%.0f", ff);
		errCount += checker("floatValue + String.format", "42", str);
				
		long jj = II.longValue();
		str = String.format("%d", jj);
		errCount += checker("longValue + String.format", "42", str);
				
		short ss = (short) II.longValue();
		str = String.format("%d", ss);
		errCount += checker("shortValue + String.format", "42", str);
				
		if (errCount == 0)
			System.out.println("Success!");
		else {
			String errMsg = String.format("error count = %d", errCount);
			throw new AssertionError(errMsg);
		}
	}

}
