import java.security.SecureRandom;

public class main {

    private static final int ROUNDS = 20;
    private static final int[] CONSTANTS = {
        0x61707865, 0x3320646e, 0x79622d32, 0x6b206574 // "expand 32-byte k"
    };
    private static final boolean debugging = true;
    private static String textOriginal = 
        "Mary had a little lamb whose fleece was white as snow. And, everywhere that Mary went the lamb was sure to go.";


    private static String byteArrayToString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    private static void copyIntArray(int[] source, int[] destination) {
        if (destination.length < source.length) {
            throw new IllegalArgumentException("Destination array is too small.");
        }
        for (int i = 0; i < source.length; i++) {
            destination[i] = source[i];
        }
    }
    
    private static void salsa20Core(int[] output, int[] input) {
        int[] x = new int[input.length];
        copyIntArray(input, x);
        
        for (int i = 0; i < ROUNDS; i += 2) {
            quarterRound(x, 0, 4, 8, 12);
            quarterRound(x, 5, 9, 13, 1);
            quarterRound(x, 10, 14, 2, 6);
            quarterRound(x, 15, 3, 7, 11);
            quarterRound(x, 0, 1, 2, 3);
            quarterRound(x, 5, 6, 7, 4);
            quarterRound(x, 10, 11, 8, 9);
            quarterRound(x, 15, 12, 13, 14);
        }

        for (int i = 0; i < 16; i++) {
            output[i] = x[i] + input[i];
        }
    }
    
    static int rounds = 0;

    private static void quarterRound(int[] x, int a, int b, int c, int d) {
        x[b] ^= Integer.rotateLeft(x[a] + x[d], 7);
        x[c] ^= Integer.rotateLeft(x[b] + x[a], 9);
        x[d] ^= Integer.rotateLeft(x[c] + x[b], 13);
        x[a] ^= Integer.rotateLeft(x[d] + x[c], 18);
        if (debugging && d == 14) {
            System.out.printf("DEBUG quarterRound a=%d, b=%d, c=%d, d=%d", a, b, c, d);
            for (int jj = 0; jj < x.length; jj++)
                System.out.printf(" %d", x[jj]);
            System.out.println();
        }
        rounds++;
    }

    private static int bytesToInt(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF) |
               ((bytes[offset + 1] & 0xFF) << 8) |
               ((bytes[offset + 2] & 0xFF) << 16) |
               ((bytes[offset + 3] & 0xFF) << 24);
    }

    private static void intToBytes(int value, byte[] bytes, int offset) {
        bytes[offset] = (byte) (value & 0xFF);
        bytes[offset + 1] = (byte) ((value >> 8) & 0xFF);
        bytes[offset + 2] = (byte) ((value >> 16) & 0xFF);
        bytes[offset + 3] = (byte) ((value >> 24) & 0xFF);
    }

    private static void salsa20Encrypt(byte[] output, byte[] input, byte[] nonce, byte[] key) {
        if (input.length != output.length) {
            throw new IllegalArgumentException("Input and output lengths must match.");
        }

        int[] state = new int[16];
        int[] block = new int[16];
        byte[] blockBytes = new byte[64];

        // Initialize state
        state[0] = CONSTANTS[0];
        state[1] = bytesToInt(key, 0);
        state[2] = bytesToInt(key, 4);
        state[3] = bytesToInt(key, 8);
        state[4] = bytesToInt(key, 12);
        state[5] = CONSTANTS[1];
        state[6] = bytesToInt(nonce, 0);
        state[7] = bytesToInt(nonce, 4);
        state[8] = 0; // Counter lower
        state[9] = 0; // Counter upper
        state[10] = CONSTANTS[2];
        state[11] = bytesToInt(key, 16);
        state[12] = bytesToInt(key, 20);
        state[13] = bytesToInt(key, 24);
        state[14] = bytesToInt(key, 28);
        state[15] = CONSTANTS[3];

        for (int i = 0; i < input.length; i += 64) {
            salsa20Core(block, state);
            for (int j = 0; j < 64 && i + j < input.length; j++) {
                blockBytes[j] = (byte) (block[j / 4] >>> (8 * (j % 4)));
                output[i + j] = (byte) (input[i + j] ^ blockBytes[j]);
            }
            if (++state[8] == 0) {
                state[9]++;
            }
        }
    }

    public static void main(String[] args) {
        int errorCount = 0;
        
        try {
            // Original text.
            if (debugging)
                textOriginal = "Mary had a little lamb";
            System.out.printf("Original textOriginal: %s\n", textOriginal);
            byte[] textOriginalBytes = textOriginal.getBytes();

            // Generate random key and nonce
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[32];
            byte[] nonce = new byte[8];
            random.nextBytes(key);
            random.nextBytes(nonce);
            
            // DEBUG
            if (debugging) {
                key = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8 };
                nonce = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
            }

            // Encrypt the textOriginal
            byte[] ciphertext = new byte[textOriginalBytes.length];
            salsa20Encrypt(ciphertext, textOriginalBytes, nonce, key);            
            System.out.printf("Ciphertext: %s\n", byteArrayToString(ciphertext));
            if (debugging) {
                String str = HexDump.dumpBytes("DEBUG Ciphertext", ciphertext, ciphertext.length, 16);
                System.out.print(str);
            }

            // Decrypt the ciphertext
            byte[] decrypted = new byte[ciphertext.length];
            salsa20Encrypt(decrypted, ciphertext, nonce, key); 
            String textDecrypted = new String(decrypted);          
            System.out.printf("Decrypted textOriginal: %s\n", textDecrypted);
            
            // Check final result.
            errorCount += Checkers.checker("Decrypted text match original clear text?", textOriginal, textDecrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Checkers.theEnd(errorCount);
    }
}

