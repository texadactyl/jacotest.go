public class main {

    public static void main(String[] args) {
		double value = 3.14159265;
		System.out.printf("Starting double value: %.8f\n", value);
		long longBits = Double.doubleToLongBits(value);
		
		System.out.printf("longBits: %d\n", longBits);
		double decodedValue = Double.longBitsToDouble(longBits);
		System.out.printf("Final double value: %.8f\n", decodedValue);
		assert(decodedValue == value);
		
		longBits = Double.doubleToRawLongBits(value);
		System.out.printf("longBits: %d\n", longBits);
		decodedValue = Double.longBitsToDouble(longBits);
		System.out.printf("Final double value: %.8f\n", decodedValue);
		assert(decodedValue == value);
    }
}

