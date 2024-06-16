/*
The Paillier cryptosystem, invented by and named after Pascal Paillier in 1999, is a probabilistic asymmetric algorithm for public key cryptography.

The scheme is an additive homomorphic cryptosystem. This means that, given only the public key and the ciphertext(m1) and ciphertext(m2), 
one can compute the ciphertext of (m1 + m2).

reference: https://www.sciencedirect.com/topics/computer-science/paillier-cryptosystem
*/

import java.math.BigInteger;

public class main {
    public static void main(String[] args) {
        int bitLength = 2048;
        PaillierCryptosystem paillier = new PaillierCryptosystem(bitLength);
        System.out.println("Instantiated PaillierCryptosystem paillier");

        // Plaintexts
        BigInteger plaintext1 = new BigInteger("42");
        BigInteger plaintext2 = new BigInteger("15");

        // Encryption
        BigInteger ciphertext1 = paillier.encrypt(plaintext1);
        System.out.print("Ciphertext1: ");
        System.out.println(ciphertext1);
        BigInteger ciphertext2 = paillier.encrypt(plaintext2);
        System.out.print("Ciphertext2: ");
        System.out.println(ciphertext2);

        // Homomorphic addition of ciphertexts
        BigInteger combinedCiphertext = paillier.add(ciphertext1, ciphertext2);
        System.out.print("Combined Ciphertext: ");
        System.out.println(combinedCiphertext);

        // Decryption of combined ciphertext
        BigInteger decryptedSum = paillier.decrypt(combinedCiphertext);
        int answer = decryptedSum.intValue();
        System.out.printf("Decrypted Sum: %d\n", answer);
        assert (answer == 57);
    }
}

