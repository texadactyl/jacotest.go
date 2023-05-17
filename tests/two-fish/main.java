// See https://www.schneier.com/academic/twofish/
// Do not make the data block any size but 16!
// Why? That's the way ECB-mode Two-fish rolls.  Read!
// Yes, one can layer a buffering higher level for practical applications.

import java.security.InvalidKeyException;

public class main {

	static Twofish_Algorithm tfa = new Twofish_Algorithm();

    public static void showBytes(String label, byte[] argBytes) {
        String hexString = tfa.toString(argBytes);
        System.out.printf("%s (%d bytes) in hex: %s\n", label, argBytes.length, hexString);
    }

	private static int essai(int keysize, byte [] originalBytes) {
	
    	System.out.println("Trying key size " + keysize);
    	Object key = null;
    	
		int originalBytesize = originalBytes.length;
		if (originalBytesize != 16) {
			System.out.printf("*** ERROR: originalBytesize must be 16 but I saw: %d\n", originalBytesize);
			return 1;
		}

 		byte[] kb = new byte[keysize];
        for (int ii = 0; ii < keysize; ii++)
            kb[ii] = (byte) ii;
        try {
        	key = tfa.makeKey(kb);
        } catch (InvalidKeyException ike) {
        	System.out.printf("*** ERROR : unexpected InvalidKeyException.\n%s\n", ike.getMessage());
        	ike.printStackTrace();
        	System.exit(1);
        }

		byte[] cipherBytes = tfa.blockEncrypt(originalBytes, 0, key);
		byte[] clearBytes = tfa.blockDecrypt(cipherBytes, 0, key);
        showBytes("cipherBytes", cipherBytes);
        showBytes("clearBytes", clearBytes);
    	if (tfa.areEqual(originalBytes, clearBytes)) {
    		System.out.println("Ok");
    		return 0;
    	}
    	System.out.println("*** ERROR: originalBytes and clearBytes comparison failed for key size " + keysize);
    	return 1;
    
	}

    public static void main(String[] args) {
    
    	int errorCount = 0;
    	
        String originalString = "1234567890123456";
        System.out.printf("originalString (string) [%d bytes]: %s\n", originalString.length(), originalString);
        byte[] originalBytes = originalString.getBytes();
        showBytes("originalBytes", originalBytes);
        
        errorCount += essai(8, originalBytes);
        errorCount += essai(16, originalBytes);
        errorCount += essai(24, originalBytes);
        errorCount += essai(32, originalBytes);
        
        System.out.print("Error count = ");
        System.out.println(errorCount);
        System.exit(errorCount);

    }

}
