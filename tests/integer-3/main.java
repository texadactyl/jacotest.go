public class main {

	public static void main(String args[]) {
		int errorCount = 0;
		int i42 = 42;
		int wint;
		Integer II = Integer.valueOf(i42);
		Integer IIW;
		String str;
		
		str = Integer.toBinaryString(i42);
		errorCount += Checkers.checker("toBinaryString", "101010", str);
		
		str = Integer.toHexString(i42);
		errorCount += Checkers.checker("toHexString", "2a", str);
		
		str = Integer.toOctalString(i42);
		errorCount += Checkers.checker("toOctalString", "52", str);
		
		str = II.toString();
		errorCount += Checkers.checker("toString - no arg", "42", str);
		
		str = Integer.toString(i42);
		errorCount += Checkers.checker("toString - i42", "42", str);
		
		str = Integer.toString(i42, 10);
		errorCount += Checkers.checker("toString - i42", "42", str);
		
		long longue = Integer.toUnsignedLong(i42);
		str = String.format("%d", longue);
		errorCount += Checkers.checker("toUnsignedString - no radix #1", "42", str);
		
		str = Integer.toUnsignedString(i42);
		errorCount += Checkers.checker("toUnsignedString - no radix #1", "42", str);
		
		str = Integer.toUnsignedString(-i42);
		errorCount += Checkers.checker("toUnsignedString - no radix #2", "4294967254", str);
		
		str = Integer.toUnsignedString(i42, 5);
		errorCount += Checkers.checker("toUnsignedString - radix=5 #1", "132", str);
		
		str = Integer.toUnsignedString(-i42, 16);
		errorCount += Checkers.checker("toUnsignedString - radix=16 #2", "ffffffd6", str);
		
		IIW = Integer.valueOf(42);
		str = String.format("%d", IIW.intValue());
		errorCount += Checkers.checker("Integer.valueOf(42)", "42", str);
		
		IIW = Integer.valueOf("42");
		str = String.format("%d", IIW.intValue());
		errorCount += Checkers.checker("Integer.valueOf(\"42\")", "42", str);
		
		IIW = Integer.valueOf("42", 16);
		str = String.format("%d", IIW.intValue());
		errorCount += Checkers.checker("Integer.valueOf(\"42\", 16)", "66", str);
		
		Checkers.theEnd(errorCount);
	}

}
