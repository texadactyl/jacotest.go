// https://en.wikipedia.org/wiki/Speck_(cipher)

public class main {

    public static void main(String[] args) {
    
        long[] clearText = {0x0123456789ABCDEFL, 0x0FEDCBA987654321L};
        long[] key = {0x0A0B0C0D0E0F1011L, 0x1121314151617181L};
        long[] cipherText = new long[2];
        long[] decrypted = new long[2];
        System.out.printf("Cleartext: %016X %016X\n", clearText[0], clearText[1]);
        System.out.printf("Key::::::: %016X %016X\n", key[0], key[1]);

        Speck.encrypt(cipherText, clearText, key);
        System.out.printf("Encrypted: %016X %016X\n", cipherText[0], cipherText[1]);

        Speck.decrypt(decrypted, cipherText, key);
        System.out.printf("Decrypted: %016X %016X\n", decrypted[0], decrypted[1]);
        
        assert decrypted[0] == clearText[0] && decrypted[1] == clearText[1];
        
        System.out.println("Success!");
    }
}

