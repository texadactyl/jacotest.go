public class main {

    public static void main(String[] args) {
        int errorCount = 0;
		float value = 1.23f;
		System.out.printf("Starting float value: %.8f\n", value);

		int intBits = Float.floatToIntBits(value);		
		System.out.printf("Float.floatToIntBits(value) intBits: %d\n", intBits);
		float decodedValue = Float.intBitsToFloat(intBits);
		System.out.printf("Final float value: %.8f\n", decodedValue);
		errorCount += Checkers.withinTolerance("decodedValue == value", value, decodedValue);
		
		intBits = Float.floatToRawIntBits(value);
		System.out.printf("Float.floatToRawIntBits(value) intBits: %d\n", intBits);
		decodedValue = Float.intBitsToFloat(intBits);
		System.out.printf("Final float value: %.8f\n", decodedValue);
		errorCount += Checkers.withinTolerance("decodedValue == value", value, decodedValue);
		
		Checkers.theEnd(errorCount);
    }
}

