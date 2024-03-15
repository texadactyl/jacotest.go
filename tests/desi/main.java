// hacked from http://www.pracspedia.com/INS/DES-java.html

public class main {

	private static int[] stringToIntArray(String arg) {
		int outBits[] = new int[64];
		char cc;
		String str;
		int ii;
		
		for(int i=0 ; i < 16 ; i++) {
		
			// For every character in the 16-nibble input, we get its binary value
			// by first parsing it into an int and then converting to a binary string.
			
			cc = arg.charAt(i);
			str = String.valueOf(cc);
			ii = Integer.parseInt(str, 16);
			String binstr = Integer.toBinaryString(ii);
			
			// Java does not add padding zeros, i.e. 5 is returned as 111 but
			// we require 0111. Hence, this while loop adds padding 0's to the
			// binary value.
			while(binstr.length() < 4) {
				binstr = String.format("0%s", binstr);
			}
			
			// Add the 4 bits we have extracted into the array of bits.
			for(int j=0 ; j < 4 ; j++) {
				cc = binstr.charAt(j);
				str = String.valueOf(cc);
				outBits[(4*i)+j] = Integer.parseInt(str);
			}
		}
		
		return outBits;
	}

	private static int cmpIntArrays(int arg1[], int arg2[]) {
		int errorCount = 0;
		if (arg1.length != arg2.length) {
			System.out.print("*** Error, arg1.length=");
			System.out.print(arg1.length);
			System.out.print(" but arg2.length=");
			System.out.println(arg2.length);
			return 1;
		}
		if (arg1.length != 64) {
			System.out.print("*** Error, arg1.length=");
			System.out.print(arg1.length);
			System.out.println(" but must be 64");
			return 1;
		}
		for(int ix = 0; ix < arg1.length; ix++) {
			if(arg1[ix] != arg2[ix]) {
				System.out.print("*** Error, disagreement at index ");
				System.out.println(ix);
				errorCount++;
			}
		}
		if (errorCount > 0) {
			System.out.print("arg1: ");
			for(int ix = 0; ix < arg1.length; ix++)
				System.out.print(arg1[ix]);
			System.out.println();
			System.out.print("arg2: ");
			for(int ix = 0; ix < arg2.length; ix++)
				System.out.print(arg2[ix]);
			System.out.println();
		}
		return errorCount;
	}

	public static void main(String args[]) {
		
		String input = "0123456789ABCDEF";
		String key = "0123456789ABCDEF";

		int inputBits[] = new int[64];
		int keyBits[] = new int[64];

		// inputBits will store the 64 bits of the input as a an int array of
		// size 64. This program uses int arrays to store bits, for the sake
		// of simplicity. For efficient programming, use long data type. But
		// it increases program complexity which is unnecessary for this
		// context.
		//
		// Ditto for the key.
		
		inputBits = stringToIntArray(input);
		keyBits = stringToIntArray(key);
			
		// permute(int[] inputBits, int[] keyBits, boolean isDecrypt)
		// method is used here. This allows encryption and decryption to be
		// done in the same method, reducing code.
		DES des = new DES();
		System.out.println("+++ ENCRYPTION +++");
		int cipherBits[] = des.permute(inputBits, keyBits, false);
		System.out.println("+++ DECRYPTION +++");
		int inputBits2[] = des.permute(cipherBits, keyBits, true);
		
		assert cmpIntArrays(inputBits2, inputBits) == 0;
		
	}

}
