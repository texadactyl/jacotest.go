public class main {

    public static void main(String[] args) {
		float value = 1.23f;
		System.out.printf("Starting float value: %.8f\n", value);
		int intBits = Float.floatToIntBits(value);
		
		System.out.printf("intBits: %d\n", intBits);
		float decodedValue = Float.intBitsToFloat(intBits);
		System.out.printf("Final float value: %.8f\n", decodedValue);
		assert(decodedValue == value);
		
		intBits = Float.floatToRawIntBits(value);
		System.out.printf("intBits: %d\n", intBits);
		decodedValue = Float.intBitsToFloat(intBits);
		System.out.printf("Final float value: %.8f\n", decodedValue);
		assert(decodedValue == value);
    }
}

