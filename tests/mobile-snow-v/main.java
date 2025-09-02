/* Snow-V Cryptography & Security -- thanks to ChatGPT.com

The SNOW-V algorithm is a cryptographic stream cipher, part of the SNOW family of ciphers, used primarily for high-speed encryption and decryption in communication systems. 
It was developed as an enhancement of earlier versions, such as SNOW 2.0, to provide stronger security and better performance. 
SNOW-V was one of the candidates in the 5G encryption standards competition and is designed to meet the security and efficiency requirements for 5G mobile networks.

SNOW-V is based on Linear Feedback Shift Registers (LFSRs), key components for generating pseudorandom sequences. 
These sequences form the keystream, which is XORed with plaintext to produce ciphertext (and vice versa for decryption).

The FSM (Finite State Machine) in SNOW-V is based on nonlinear functions and interacts closely with the LFSR. 

SNOW-V was standardized by the 3rd Generation Partnership Project (3GPP) as part of its toolbox of security algorithms for 5G networks. 
Specifically, SNOW-V is included in the 5G confidentiality and integrity protection standards 
and is designed to meet the stringent performance and security requirements of modern mobile networks.

Implementations:
* Mobile Communications: Securing data in 5G networks.
* IoT Devices: Providing lightweight encryption for resource-constrained devices.
* High-Speed Networks: Ensuring fast and secure data transmission.

*/

import java.util.Arrays;

public class main {

    private static final int KEY_SIZE = 256 / 8;  // Key size in bytes (256 bits)
    private static final int IV_SIZE = 128 / 8;   // IV size in bytes (128 bits)
    private static final byte FILLER_BYTE = 0x42; // Filler byte (not secure!)

    // Realistic SNOW-V keystream generator (simplified for illustration)
    private static byte[] generateKeystream(byte[] key, byte[] iv, int length) {
        if (key.length != KEY_SIZE || iv.length != IV_SIZE) {
            throw new IllegalArgumentException("Invalid key or IV size.");
        }

        byte[] keystream = new byte[length];
        int[] lfsr = new int[16];
        int[] fsm = new int[3];

        // Initialize LFSR with key and IV
        for (int i = 0; i < 8; i++) {
            lfsr[i] = (key[i * 4] & 0xFF) << 24 |
                      (key[i * 4 + 1] & 0xFF) << 16 |
                      (key[i * 4 + 2] & 0xFF) << 8 |
                      (key[i * 4 + 3] & 0xFF);
        }
        //System.out.printf("generateKeystream: initialised lfsr (%d): %s\n", lfsr.length, Arrays.toString(lfsr));
        for (int i = 0; i < 4; i++) {
            lfsr[8 + i] = (iv[i * 4] & 0xFF) << 24 |
                          (iv[i * 4 + 1] & 0xFF) << 16 |
                          (iv[i * 4 + 2] & 0xFF) << 8 |
                          (iv[i * 4 + 3] & 0xFF);
        }

        // Fill the rest of LFSR with a predefined constant or zero
        Arrays.fill(lfsr, 12, 16, 0x9E3779B9); // Example constant
        //System.out.printf("generateKeystream: filled lfsr (%d): %s\n", lfsr.length, Arrays.toString(lfsr));

        // Warm-up phase: advance the state 32 times to initialize
        System.out.printf("generateKeystream: BEFORE advanceState lfsr (%d): %s\n", lfsr.length, Arrays.toString(lfsr));
        System.out.printf("generateKeystream: BEFORE advanceState  fsm (%d): %s\n", fsm.length, Arrays.toString(fsm));
        for (int i = 0; i < 32; i++) {
            advanceState(lfsr, fsm);
        }
        System.out.printf("generateKeystream: AFTER  advanceState lfsr (%d): %s\n", lfsr.length, Arrays.toString(lfsr));
        System.out.printf("generateKeystream: AFTER  advanceState  fsm (%d): %s\n", fsm.length, Arrays.toString(fsm));

        // Generate keystream
        for (int i = 0; i < length; i++) {
            keystream[i] = (byte) generateKeystreamByte(lfsr, fsm);
        }

        return keystream;
    }

    private static void advanceState(int[] lfsr, int[] fsm) {
        // Realistic FSM and LFSR update logic
        int t1 = fsm[1] ^ lfsr[15];
        int t2 = Integer.rotateLeft(fsm[2], 7) + fsm[0];

        fsm[0] = fsm[1];
        fsm[1] = fsm[2];
        fsm[2] = (t1 ^ t2) & 0xFFFFFFFF; // Ensure 32-bit values

        int lfsrInput = lfsr[0] ^ fsm[2];
        System.arraycopy(lfsr, 1, lfsr, 0, lfsr.length - 1);
        lfsr[lfsr.length - 1] = lfsrInput;
    }

    private static int generateKeystreamByte(int[] lfsr, int[] fsm) {
        return lfsr[0] ^ fsm[2]; // Output function combining LFSR and FSM
    }

    public static byte[] encrypt(byte[] plaintext, byte[] key, byte[] iv) {
        //System.out.printf("encrypt: key (%d): %s\n", key.length, bytesToHex(key));
        //System.out.printf("encrypt: iv (%d): %s\n", iv.length, bytesToHex(iv));
        if (key.length != KEY_SIZE || iv.length != IV_SIZE) {
            throw new IllegalArgumentException("Invalid key or IV size.");
        }

        byte[] keystream = generateKeystream(key, iv, plaintext.length);
        //System.out.printf("encrypt: keystream (%d): %s\n", keystream.length, bytesToHex(keystream));
        byte[] ciphertext = new byte[plaintext.length];

        for (int i = 0; i < plaintext.length; i++) {
            ciphertext[i] = (byte) (plaintext[i] ^ keystream[i]);
        }

        return ciphertext;
    }

    public static byte[] decrypt(byte[] ciphertext, byte[] key, byte[] iv) {
        // Decryption is identical to encryption in a stream cipher
        return encrypt(ciphertext, key, iv);
    }

    // Utility method to convert byte array to hex string.
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    // Compare 2 byte arrays.
    public static boolean arraysEqual(byte[] A, byte[] B) {
        if (A.length != B.length)
            return false;
        for (int ix = 0; ix < A.length; ix++) {
            if (A[ix] != B[ix])
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
    
        System.out.println("main: Snow V cryptography as used in mobile networks.");
        
        byte[] key = new byte[KEY_SIZE];
        byte[] iv = new byte[IV_SIZE];

        // This key and IV would be replaced with secure random values in the real world, possibly through a TLS handshake.
        Arrays.fill(key, FILLER_BYTE);
        Arrays.fill(iv, FILLER_BYTE);

        // Define input.
        String plaintextString1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/";
        byte[] plaintext = plaintextString1.getBytes();
        System.out.printf("main: Plaintext (%d): %s\n", plaintext.length, plaintextString1);

        // Encrypt.
        byte[] ciphertext = encrypt(plaintext, key, iv);
        System.out.printf("main: Ciphertext (%d): %s\n", ciphertext.length, bytesToHex(ciphertext));

        // Decrypt.
        byte[] decrypted = decrypt(ciphertext, key, iv);
        String plaintextString2 = new String(decrypted);
        System.out.printf("main: Decrypted (%d): %s\n", decrypted.length, plaintextString2);
        
       int errorCount = Checkers.checker("arraysEqual(plaintext, decrypted)", true, arraysEqual(plaintext, decrypted));
        Checkers.theEnd(errorCount);
        
    }
}

