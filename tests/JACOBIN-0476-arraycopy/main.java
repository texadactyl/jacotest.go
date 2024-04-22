public class main {
    public static void main(String[] args) {
    
    	// Initialise arrays.
    	int NELEMS = 20;
		int input[] = new int[NELEMS];
		int output[] = new int[NELEMS];
		for (int ii = 0; ii < NELEMS; ii++) {
			input[ii] = ii + 1; // 1, 2, 3, .....
			output[ii] = -42;
		}
		
		// Copy all of input to output and verify.
		System.arraycopy(output, 0, input, 0, NELEMS);
		for (int ii = 0; ii < NELEMS; ii++) {
			assert input[ii] == output[ii];
		}
		System.out.println("Whole array copy ok");
		
		// Copy a subset of input to output and verify.
		int offsetSource = 11;
		int offsetTarget = 12;
		int length = 5;
		for (int ii = 0; ii < NELEMS; ii++) {
			input[ii] = ii + NELEMS + 1;
		}
		System.arraycopy(input, offsetSource, output, offsetTarget, length);
		int jj = offsetTarget;
		int count = 0;
		for (int ii = offsetSource; count < length; ii++) {
			assert input[ii] == output[jj];
			count++;
			jj++;
		}
		System.out.println("Subset array copy ok");
    }
}
