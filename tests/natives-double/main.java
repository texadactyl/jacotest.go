public class main {

    public static void main(String[] args) {
        int errorCount = 0;
		double value = 3.14159265;
		System.out.printf("Starting double value: %.8f\n", value);

		long longBits = Double.doubleToLongBits(value);		
		System.out.printf("doubleToLongBits(value) longBits: %d\n", longBits);
		double decodedValue = Double.longBitsToDouble(longBits);
		errorCount += Checkers.withinTolerance("decodedValue == value", value, decodedValue);
		
		longBits = Double.doubleToRawLongBits(value);
		System.out.printf("doubleToRawLongBits(value) longBits: %d\n", longBits);
		decodedValue = Double.longBitsToDouble(longBits);
		errorCount += Checkers.withinTolerance("decodedValue == value", value, decodedValue);

		Checkers.theEnd(errorCount);
    }
}

