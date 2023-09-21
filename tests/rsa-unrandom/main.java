/***

	RSA key generation, public key encryption, and private key decryption.
	This is fine for an academic exercisee but not for production.

 ***/

import java.math.BigInteger;
import java.util.HexFormat;

public class main {

	final static int keySize = 2048;

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

    public static void hexDump(String label, byte[] byteArray) {
        int lineLength = 16; // Number of bytes to display in one line
        int bytesRead = 0;
        HexFormat hex = HexFormat.of();
        String hs;

		System.out.println(label);
        for (int ii = 0; ii < byteArray.length; ii++) {
        
            if (bytesRead == 0) {
                hs = hex.toHexDigits(ii);
		        System.out.print(hs);
		        System.out.print(": ");
            }

            hs = hex.toHexDigits(byteArray[ii]);
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

    public static RSAKeyPair generateRSAKeyPair(int keySize) {
    
        // Generate two random prime numbers of half the key size
        BigInteger [] pq = generateRandomPrimes(keySize / 2);
        BigInteger p = pq[0];
        BigInteger q = pq[1];
        System.out.println("Primes (p, q): (" + p + ", " + q + ")");

        // Calculate n = modulus and phi
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Select a public exponent e (a small prime, commonly 65537)
        BigInteger e = BigInteger.valueOf(65537);

        // Calculate the private exponent d (modular multiplicative inverse of e modulo phi)
        BigInteger d = e.modInverse(phi);

        // Show key components
        System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
        System.out.println("Private Key (d, n): (" + d + ", " + n + ")");

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

    // Function to find the 2 greatest primes that are less than a given BigInteger (ceiling)
    public static BigInteger [] getTwoPrimes(BigInteger ceiling) {
        int counter = 0;
        BigInteger [] result = new BigInteger[2];
        ceiling = ceiling.subtract(BigInteger.ONE); // Start testing on ceiling less one

        while (ceiling.compareTo(BigInteger.ONE) > 0) {
            if (ceiling.isProbablePrime(10)) { // Check if N is prime
                // Found a prime
                counter += 1;
                if (counter > 1) { // if found both primes
                    result[1] = result[0];
                    result[0] = ceiling;
                    return result;
                }
                result[0] = ceiling;
            }
            ceiling = ceiling.subtract(BigInteger.ONE); // Decrement by 1
        }

        throw new AssertionError("getTwoPrimes failed");

    }

    private static BigInteger[] generateRandomPrimes(int bitLength) {
        BigInteger bigTwo = BigInteger.TWO;
        BigInteger ceiling = bigTwo.pow(bitLength);
        BigInteger[] primes = getTwoPrimes(ceiling);
        return primes;
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

