// See https://www.schneier.com/academic/twofish/
// Do not make the data block any size but 16!
// Why? That's the way ECB-mode Two-fish rolls.  Read!
// Yes, one can layer a buffering higher level for practical applications.

import java.security.InvalidKeyException;

public class main {

    private static void showBytes(String label, byte[] argBytes) {
    
        String hexString = Twofish_Algorithm.toString(argBytes);
        System.out.print(label);
        System.out.print(" (");
        System.out.print(argBytes.length);
        System.out.print(" bytes) in hex: ");
        System.out.println(hexString);
    }

	private static int tryTwoFish(int keysize, byte [] originalBytes) {
	
    	System.out.print("Trying key size ");
    	System.out.println(keysize);
    	Object key = null;
    	
		int originalBytesize = originalBytes.length;
		if (originalBytesize != 16) {
			System.out.print("*** ERROR, originalBytesize must be 16 but I saw: ");
			System.out.println(originalBytesize);
			return 1;
		}

 		byte[] kb = new byte[keysize];
        for (int ii = 0; ii < keysize; ii++)
            kb[ii] = (byte) ii;
        try {
        	key = Twofish_Algorithm.makeKey(kb);
        } catch (InvalidKeyException ike) {
        	String msg = String.format("*** ERROR, unexpected InvalidKeyException: %s", ike.getMessage());
        	ike.printStackTrace();
        	throw new AssertionError(msg);
        }

		byte[] cipherBytes = Twofish_Algorithm.blockEncrypt(originalBytes, 0, key);
		byte[] clearBytes = Twofish_Algorithm.blockDecrypt(cipherBytes, 0, key);
        showBytes("cipherBytes", cipherBytes);
        showBytes("clearBytes", clearBytes);
    	if (Twofish_Algorithm.areEqual(originalBytes, clearBytes)) {
    		System.out.println("Ok, Twofish_Algorithm.areEqual");
    		return 0;
    	}
    	System.out.print("*** ERROR, originalBytes and clearBytes comparison failed for key size ");
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
        
        System.out.print("Error count = ");
        System.out.println(errorCount);
        assert(errorCount == 0);

    }

}
