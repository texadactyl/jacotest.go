/***

	RSA key generation, public key encryption, and private key decryption.
	This is fine for an academic exercise but not for production.

 ***/

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HexFormat;

public class main {

	final static int keySize = 1024;

    public static void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(value);
    }

    public static void main(String[] args) {
    
        // Generate an RSA key pair
        RSAKeyPair keyPair = generateRSAKeyPair(keySize);

        // Cleartext message to be encrypted
        String clearText = "Mary had a little lamb whose fleece was white as snow!";

        // Encrypt the clearText using the public key
        byte[] cipherText = encrypt(clearText, keyPair);

        // Decrypt the cipherText using the private key
        String decryptedText = decrypt(cipherText, keyPair);

		// Show everything
        printLabeledString("Original Message", clearText);
        hexDump("Encrypted Message", cipherText);
        printLabeledString("Decrypted Message", decryptedText);
        
        // Success?
        assert decryptedText.equals(clearText);
        System.out.println("Success!");
        
    }

    private static void printPair(String label, BigInteger arg1, BigInteger arg2) {
        System.out.printf("%s: (", label);
        System.out.print(arg1);System.out.print(", ");
        System.out.print(arg2);
        System.out.println(")");
    }

    public static RSAKeyPair generateRSAKeyPair(int keySize) {
    
        SecureRandom rand = new SecureRandom();

        // Generate two random prime numbers of half the key size
        System.out.print("rand: ");
        System.out.println(rand);
        BigInteger p = generateRandomPrime(keySize / 2, rand);
        BigInteger q = generateRandomPrime(keySize / 2, rand);
        //System.out.println("Primes (p, q): (" + p + ", " + q + ")");
        printPair("Primes (p, q)", p, q);

        // Calculate n = modulus and phi
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Select a public exponent e (a small prime, commonly 65537)
        BigInteger e = BigInteger.valueOf(65537);

        // Calculate the private exponent d (modular multiplicative inverse of e modulo phi)
        BigInteger d = e.modInverse(phi);

        // Show key components
        //System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
        printPair("Public Key (e, n)", e, n);
        //System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
        printPair("Private Key (d, n)", d, n);

        // Return key pair to caller
        return new RSAKeyPair(n, e, d);
    }

    public static byte[] encrypt(String plaintext, RSAKeyPair keyPair) {
        BigInteger message = new BigInteger(plaintext.getBytes());
        return message.modPow(keyPair.getPublicKey(), keyPair.n).toByteArray();
    }

    public static String decrypt(byte[] cipherText, RSAKeyPair keyPair) {
        BigInteger encryptedMessage = new BigInteger(cipherText);
        BigInteger decryptedMessage = encryptedMessage.modPow(keyPair.getPrivateKey(), keyPair.n);
        return new String(decryptedMessage.toByteArray());
    }

    private static BigInteger generateRandomPrime(int bitLength, SecureRandom rand) {
        BigInteger prime;
        do {
            prime = BigInteger.probablePrime(bitLength, rand);
        } while (!prime.isProbablePrime(13));
        System.out.print("generateRandomPrime: Selected prime: ");
        System.out.println(prime);
        return prime;
    }

    public static void hexDump(String label, byte[] byteArray) {
        int lineLength = 16; // Number of bytes to display in one line
        int bytesRead = 0;
        String hs;

		System.out.println(label);
        for (int ii = 0; ii < byteArray.length; ii++) {
        
            if (bytesRead == 0) {
                hs = String.format("%08x", ii);
		        System.out.print(hs);
		        System.out.print(": ");
            }

            hs = String.format("%02x", byteArray[ii]);
            System.out.print(hs);
            System.out.print(" ");

            bytesRead++;

            if (bytesRead == lineLength || ii == byteArray.length - 1) {
            
                // Print a newline character at the end of each line or when we reach the end of the array
                System.out.println();
                bytesRead = 0;
                
            }
        }
    }

    static class RSAKeyPair {
        private final BigInteger n; // Modulus
        private final BigInteger e; // Public exponent
        private final BigInteger d; // Private exponent

        public RSAKeyPair(BigInteger n, BigInteger e, BigInteger d) {
            this.n = n;
            this.e = e;
            this.d = d;
        }

        public BigInteger getPublicKey() {
            return this.e;
        }

        public BigInteger getPrivateKey() {
            return this.d;
        }
    }
}

