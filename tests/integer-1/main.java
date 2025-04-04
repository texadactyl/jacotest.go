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
		byte bite;
		boolean bewel;
		Integer II = Integer.valueOf(i42);
		Integer II2 = Integer.valueOf(i42+1);
		String str;
		
		wint = Integer.bitCount(i42);
		str = String.format("%d", wint);
		errCount += checker("bitCount", "3", str);

		bite = II.byteValue();
		str = String.format("%d", bite);
		errCount += checker("byteValue", "42", str);

		wint = Integer.compare(1, 2);
		str = String.format("%d", wint);
		errCount += checker("compare", "-1", str);

		wint = II.compareTo(II2);
		str = String.format("%d", wint);
		errCount += checker("compareTo", "-1", str);

		II2 = Integer.decode("86");
		str = II2.toString();
		errCount += checker("decode", "86", str);
		
		wint = Integer.divideUnsigned(45, 7);
		str = String.format("%d", wint);
		errCount += checker("divideUnsigned", "6", str);

		wint = Integer.divideUnsigned(-7, -45);
		str = String.format("%d", wint);
		errCount += checker("divideUnsigned", "1", str);

		double dd = II.doubleValue();
		str = String.format("%.0f", dd);
		errCount += checker("doubleValue + String.format", "42", str);
				
		if (II.equals(II2))
		    str = "true";
		else
		    str = "false";
		errCount += checker("II.equals(II2)", "false", str);
				
		if (II.equals(II))
		    str = "true";
		else
		    str = "false";
		errCount += checker("II.equals(II)", "true", str);
				
		wint = Integer.expand(65535, 0xff);
		str = String.format("%d", wint);
		errCount += checker("expand", "255", str);

		float ff = II.floatValue();
		str = String.format("%.0f", ff);
		errCount += checker("floatValue", "42", str);
				
		wint = Integer.highestOneBit(9);
		str = String.format("%d", wint);
		errCount += checker("highestOneBit(9)", "8", str);

		wint = II.intValue();
		str = String.format("%d", wint);
		errCount += checker("intValue", "42", str);
				
		long jj = II.longValue();
		str = String.format("%d", jj);
		errCount += checker("longValue + String.format", "42", str);
				
		wint = Integer.lowestOneBit(10);
		str = String.format("%d", wint);
		errCount += checker("lowestOneBit(10)", "2", str);

		wint = Integer.max(1, 2);
		str = String.format("%d", wint);
		errCount += checker("Integer.max(1, 2)", "2", str);

		wint = Integer.min(1, -2);
		str = String.format("%d", wint);
		errCount += checker("Integer.min(1, -2)", "-2", str);

		wint = Integer.lowestOneBit(0x0F << 5);
		str = String.format("%d", wint);
		errCount += checker("lowestOneBit(0x0F << 5)", "32", str);
				
		wint = Integer.numberOfLeadingZeros(0x0F << 5);
		str = String.format("%d", wint);
		errCount += checker("numberOfLeadingZeros(0x0F << 5)", "23", str);
				
		wint = Integer.numberOfTrailingZeros(0x0F << 5);
		str = String.format("%d", wint);
		errCount += checker("numberOfTrailingZeros(0x0F << 5)", "5", str);
				
		if (errCount == 0)
			System.out.println("Success!");
		else {
			String errMsg = String.format("error count = %d", errCount);
			throw new AssertionError(errMsg);
		}
	}

}
