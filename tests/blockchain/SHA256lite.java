// Hacked from chatGPT

/***

Padding: The message is padded so that its length is a multiple of 512 bits.

Message Schedule: The padded message is divided into 512-bit blocks, and each block is further divided into sixteen 32-bit words.
                  These words are used to create a schedule of 64 words.

Compression Function: For each block, the algorithm applies a series of bitwise operations and modular additions
                      using a set of constants to update a set of eight hash values.

Final Hash: After processing all blocks, the final hash value is obtained by concatenating the updated hash values.

***/

import java.util.Arrays;

public class SHA256lite {

    private static final int[] K = {
        0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b,
        0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01,
        0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7,
        0xc19bf174, 0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
        0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152,
        0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147,
        0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc,
        0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
        0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819,
        0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08,
        0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f,
        0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
        0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    private static final int H0 = 0x6a09e667;
    private static final int H1 = 0xbb67ae85;
    private static final int H2 = 0x3c6ef372;
    private static final int H3 = 0xa54ff53a;
    private static final int H4 = 0x510e527f;
    private static final int H5 = 0x9b05688c;
    private static final int H6 = 0x1f83d9ab;
    private static final int H7 = 0x5be0cd19;

    private static byte[] padMessage(byte[] message) {
        int originalLength = message.length * 8;
        int paddingLength = 448 - (originalLength + 1) % 512;
        if (paddingLength < 0) {
            paddingLength += 512;
        }
        paddingLength += 1 + 64;
        int newLength = originalLength + paddingLength;
        
        byte[] paddedMessage = Arrays.copyOf(message, newLength / 8);
        paddedMessage[message.length] = (byte) 0x80;
        for (int i = message.length + 1; i < paddedMessage.length - 8; i++) {
            paddedMessage[i] = 0;
        }
        
        for (int i = 0; i < 8; i++) {
            paddedMessage[paddedMessage.length - 1 - i] = (byte) (originalLength >>> (8 * i));
        }
        
        return paddedMessage;
    }

    private static int rotr(int x, int n) {
        return (x >>> n) | (x << (32 - n));
    }

    private static int ch(int x, int y, int z) {
        return (x & y) ^ (~x & z);
    }

    private static int maj(int x, int y, int z) {
        return (x & y) ^ (x & z) ^ (y & z);
    }

    private static int sigma0(int x) {
        return rotr(x, 2) ^ rotr(x, 13) ^ rotr(x, 22);
    }

    private static int sigma1(int x) {
        return rotr(x, 6) ^ rotr(x, 11) ^ rotr(x, 25);
    }

    private static int delta0(int x) {
        return rotr(x, 7) ^ rotr(x, 18) ^ (x >>> 3);
    }

    private static int delta1(int x) {
        return rotr(x, 17) ^ rotr(x, 19) ^ (x >>> 10);
    }

    public static String computeSHA256(String inHexString) {
    
        byte[] message = new byte[inHexString.length()];
        message = inHexString.getBytes();
    
        byte[] paddedMessage = padMessage(message);
        int[] H = {H0, H1, H2, H3, H4, H5, H6, H7};
        
        int numBlocks = paddedMessage.length / 64;
        for (int i = 0; i < numBlocks; i++) {
            int[] W = new int[64];
            for (int j = 0; j < 16; j++) {
                W[j] = ((paddedMessage[i * 64 + 4 * j] & 0xFF) << 24) |
                       ((paddedMessage[i * 64 + 4 * j + 1] & 0xFF) << 16) |
                       ((paddedMessage[i * 64 + 4 * j + 2] & 0xFF) << 8) |
                       (paddedMessage[i * 64 + 4 * j + 3] & 0xFF);
            }
            for (int j = 16; j < 64; j++) {
                W[j] = delta1(W[j - 2]) + W[j - 7] + delta0(W[j - 15]) + W[j - 16];
            }
            
            int a = H[0];
            int b = H[1];
            int c = H[2];
            int d = H[3];
            int e = H[4];
            int f = H[5];
            int g = H[6];
            int h = H[7];
            
            for (int j = 0; j < 64; j++) {
                int T1 = h + sigma1(e) + ch(e, f, g) + K[j] + W[j];
                int T2 = sigma0(a) + maj(a, b, c);
                h = g;
                g = f;
                f = e;
                e = d + T1;
                d = c;
                c = b;
                b = a;
                a = T1 + T2;
            }
            
            H[0] += a;
            H[1] += b;
            H[2] += c;
            H[3] += d;
            H[4] += e;
            H[5] += f;
            H[6] += g;
            H[7] += h;
        }
        
        byte[] digest = new byte[32];
        for (int i = 0; i < 8; i++) {
            digest[4 * i] = (byte) (H[i] >>> 24);
            digest[4 * i + 1] = (byte) (H[i] >>> 16);
            digest[4 * i + 2] = (byte) (H[i] >>> 8);
            digest[4 * i + 3] = (byte) H[i];
        }
        
        return Helpers.hexStringFromBytes(digest);
    }

    public static void tester(String[] args) {
        String message = "hello world";
        String hashOut = computeSHA256(message);
        System.out.printf("SHA-256 hash: %s\n", hashOut);
    }
}

