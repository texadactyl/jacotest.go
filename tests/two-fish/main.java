// See https://www.schneier.com/academic/twofish/
// Do not make the data block any size but 16!
// Why? That's the way ECB-mode Two-fish rolls.  Read!
// Yes, one can layer a buffering higher level for practical applications.

import java.security.InvalidKeyException;

public class main {

    public static void showBytes(String label, byte[] argBytes) {
        String hexString = Twofish_Algorithm.toString(argBytes);
        System.out.print(label);
        System.out.print(" (");
        System.out.print(argBytes.length);
        System.out.print(" bytes) in hex: ");
        System.out.println(hexString);
    }

	private static int essai(int keysize, byte [] originalBytes) {
	
    	System.out.print("Trying key size ");
    	System.out.println(keysize);
    	Object key = null;
    	
		int originalBytesize = originalBytes.length;
		if (originalBytesize != 16) {
			System.out.print("*** ERROR: originalBytesize must be 16 but I saw: ");
			System.out.println(originalBytesize);
			return 1;
		}

 		byte[] kb = new byte[keysize];
        for (int ii = 0; ii < keysize; ii++)
            kb[ii] = (byte) ii;
        try {
        	key = Twofish_Algorithm.makeKey(kb);
        } catch (InvalidKeyException ike) {
        	System.out.print("*** ERROR : unexpected InvalidKeyException: ");
        	System.out.println(ike.getMessage());
        	ike.printStackTrace();
        	System.exit(1);
        }

		byte[] cipherBytes = Twofish_Algorithm.blockEncrypt(originalBytes, 0, key);
		byte[] clearBytes = Twofish_Algorithm.blockDecrypt(cipherBytes, 0, key);
        showBytes("cipherBytes", cipherBytes);
        showBytes("clearBytes", clearBytes);
    	if (Twofish_Algorithm.areEqual(originalBytes, clearBytes)) {
    		System.out.println("Ok");
    		return 0;
    	}
    	System.out.print("*** ERROR: originalBytes and clearBytes comparison failed for key size ");
    	System.out.println(keysize);
    	return 1;
    
	}

    public static void main(String[] args) {
    
    	System.out.println("Symmetric cipher Two Fish exercise");
    
    	int errorCount = 0;
    	
        String originalString = "1234567890123456";
        System.out.print("originalString (string): ");
        System.out.println(originalString);
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
