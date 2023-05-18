// Documentation: https://www.schneier.com/academic/solitaire/

public class main {

  public static void main(String args[]) {

    // Effect: enciphers args[0] using args[1] as a key.  
    //   If args[0] is not supplied, enciphers the string "SOLITAIRE".
    //   If args[1] is not supplied, a default deck is used for the key.
    
    int errorCount = 0;
    
    SolitaireCipher scIn  = new SolitaireCipher(args.length > 1 ? args[1] : "");
    SolitaireCipher scOut = (SolitaireCipher)scIn.clone();
    String plaintext = args.length > 0 ? args[0] : "SOLITAIRE";
    String ciphertext = scIn.encrypt(plaintext);
    String outputtext = scOut.decrypt(ciphertext);

    System.out.println("Plaintext:  " + plaintext);
    System.out.println("Ciphertext: " + ciphertext);
    String expectedCiphertext = "WLVGBZAXIE";
    if (! ciphertext.equals(expectedCiphertext)) {
    	System.out.printf("*** ERROR: Expected ciphertext %s but instead saw %s\n", expectedCiphertext, ciphertext);
    	errorCount++;
    }
    System.out.println("Outputtext: " + outputtext);
    if (outputtext.length() > plaintext.length())
    	plaintext += "X";
    if (! outputtext.equals(plaintext)) {
    	System.out.printf("*** ERROR: Expected outputtext %s but instead saw %s\n", plaintext, outputtext);
    	errorCount++;
    }

    if (errorCount == 0) {
        System.out.println("No errors detected");
    } else {
        System.out.print("Number of errors = ");
        System.out.println(errorCount);
        System.exit(1);
    }
 
  }

}
