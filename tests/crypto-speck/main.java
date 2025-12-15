// https://en.wikipedia.org/wiki/Speck_(cipher)

import java.util.HexFormat;


public class main {

    private static HexFormat hf = HexFormat.of();

    public static String longTo16HexDigits(long value) {
        return hf.toHexDigits(value, 16);
    }

    public static void main(String[] args) {
    
        int errorCount = 0;
    
        long[] clearText = {0x0123456789ABCDEFL, 0x0FEDCBA987654321L};
        long[] key = {0x0A0B0C0D0E0F1011L, 0x1121314151617181L};
        long[] cipherText = new long[2];
        long[] decrypted = new long[2];
        System.out.printf("Cleartext: %s %s\n", longTo16HexDigits(clearText[0]), longTo16HexDigits(clearText[1]));
        System.out.printf("Key::::::: %s %s\n", longTo16HexDigits(key[0]), longTo16HexDigits(key[1]));

        Speck.encrypt(cipherText, clearText, key);
        System.out.printf("Encrypted: %s %s\n", longTo16HexDigits(cipherText[0]), longTo16HexDigits(cipherText[1]));

        Speck.decrypt(decrypted, cipherText, key);
        System.out.printf("Decrypted: %s %s\n", longTo16HexDigits(decrypted[0]), longTo16HexDigits(decrypted[1]));
        
        errorCount += Checkers.checker("Decrypted text match original clear text?", clearText, decrypted);
        
        Checkers.theEnd(errorCount);
    }
}

