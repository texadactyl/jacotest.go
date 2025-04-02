// See https://www.schneier.com/academic/twofish/
// Do not make the data block any size but 16!
// Why? That's the way ECB-mode Two-fish rolls.  Read!
// Yes, one can layer a buffering higher level for practical applications.

import java.security.InvalidKeyException;

public class main {

    private static void showBytes(String label, byte[] argBytes) {
    
        String hexString = Twofish_Algorithm.toString(argBytes);
        System.out.printf("%-15s", label);
        System.out.println(hexString);
    }

    public static String compareNibbles(byte A, byte B) {
        int highA = (A >> 4) & 0xF; // Extract high nibble
        int lowA = A & 0xF;         // Extract low nibble
        int highB = (B >> 4) & 0xF; // Extract high nibble
        int lowB = B & 0xF;         // Extract low nibble

        char highDiff = (highA != highB) ? '*' : ' ';
        char lowDiff = (lowA != lowB) ? '*' : ' ';
        String result = String.format("%c%c", highDiff, lowDiff);
        System.out.printf("DEBUG result=%s, highA=%d, lowA=%d, highB=%d, lowB=%d\n", result, highA, lowA, highB, lowB);

        return result;
    }

	private static int tryTwoFish(int keysize, byte [] originalBytes) {
	
    	System.out.print("\ntryTwoFish: key size ");
    	System.out.println(keysize);
    	Object key = null;
    	
		int originalBytesize = originalBytes.length;
		if (originalBytesize != 16) {
			System.out.print("tryTwoFish *** ERROR, originalBytesize must be 16 but I saw: ");
			System.out.println(originalBytesize);
			return 1;
		}

 		byte[] kb = new byte[keysize];
        for (int ii = 0; ii < keysize; ii++)
            kb[ii] = (byte) ii;
        try {
        	key = Twofish_Algorithm.makeKey(kb);
        } catch (InvalidKeyException ike) {
        	String msg = String.format("tryTwoFish *** ERROR, unexpected InvalidKeyException: %s", ike.getMessage());
        	ike.printStackTrace();
        	throw new AssertionError(msg);
        }

        showBytes("originalBytes", originalBytes);
		byte[] cipherBytes = Twofish_Algorithm.blockEncrypt(originalBytes, 0, key);
		byte[] clearBytes = Twofish_Algorithm.blockDecrypt(cipherBytes, 0, key);
        showBytes("cipherBytes", cipherBytes);
        showBytes("clearBytes", clearBytes);
    	if (Twofish_Algorithm.areEqual(originalBytes, clearBytes)) {
    		System.out.println("tryTwoFish ok, clearBytes = originalBytes");
    		return 0;
    	}
    	System.out.printf("%15s", " ");
    	for (int ix = 0; ix < originalBytes.length; ix++ ) {
    	    if (clearBytes[ix] != originalBytes[ix]) {
    	        //System.out.print(compareNibbles(clearBytes[ix], originalBytes[ix]));
    	        compareNibbles(originalBytes[ix], clearBytes[ix]);
    	    } else
    	        System.out.print("  ");
    	}
    	System.out.println();
    	System.out.print("tryTwoFish *** ERROR, originalBytes and clearBytes comparison failed for key size ");
    	System.out.println(keysize);
    	return 1;
    
	}

    public static void main(String[] args) {
    
    	System.out.println("Symmetric cipher Two Fish exercise");
    	Twofish_Properties.list();
    
    	int errorCount = 0;
    	
        String originalString = "1234567890123456";
        System.out.print("originalString (string): ");
        System.out.println(originalString);
        byte[] originalBytes = originalString.getBytes();
        showBytes("originalBytes", originalBytes);
        
        errorCount += tryTwoFish(8, originalBytes);
        errorCount += tryTwoFish(16, originalBytes);
        errorCount += tryTwoFish(24, originalBytes);
        errorCount += tryTwoFish(32, originalBytes);
        
        System.out.print("\nError count = ");
        System.out.println(errorCount);
        assert(errorCount == 0);
        System.out.println("Success!");

    }

}
