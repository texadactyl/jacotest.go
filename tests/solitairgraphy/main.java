// Documentation: https://www.schneier.com/academic/solitaire/

public class main {

	public static void expObs(String label, String expected, String observed) {
		System.out.print("*** ERROR, ");
		System.out.print(label);
		System.out.print(", expected ");
		System.out.print(expected);
		System.out.print(", observed ");
		System.out.println(observed);		
	}

	public static void main(String args[]) {
	
		System.out.println("Cryptography with a deck of cards - https://www.schneier.com/academic/solitaire/");

		// Effect: enciphers args[0] using args[1] as a key.  
		//   If args[0] is not supplied, enciphers the string "SOLITAIRE".
		//   If args[1] is not supplied, a default deck is used for the key.
		
		int errorCount = 0;
		
		SolitaireCipher scIn  = new SolitaireCipher(args.length > 1 ? args[1] : "");
		SolitaireCipher scOut = (SolitaireCipher)scIn.clone();
		String plaintext = args.length > 0 ? args[0] : "SOLITAIRE";
		String ciphertext = scIn.encrypt(plaintext);
		String outputtext = scOut.decrypt(ciphertext);

		System.out.print("Plaintext:  ");
		System.out.println(plaintext);
		System.out.print("Ciphertext: ");
		System.out.println(ciphertext);
		String expectedCiphertext = "WLVGBZAXIE";
		if (! ciphertext.equals(expectedCiphertext)) {
			expObs("Ciphertext", expectedCiphertext, ciphertext);
			errorCount++;
		}
		System.out.print("Outputtext: ");
		System.out.println(outputtext);
		if (outputtext.length() > plaintext.length())
			plaintext += "X";
		if (! outputtext.equals(plaintext)) {
			expObs("output plaintext", plaintext, outputtext);
			errorCount++;
		}

		assert (errorCount == 0);
 
	}

}
