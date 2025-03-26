/*
Blum-Blum-Shub (BBS) Algorithm for PseudoRandom Number Generation
=================================================================
Assistance from ChatGPT.com
===========================

1. **Setup**:
   - Choose two large and distinct prime numbers p and q such that p = 3 mod 4 and q = 3 mod 4.
   - Compute n = p * q. The value n is public, but p and q are secret.
   
2. **Seed**:
   - Select a random seed x0 such that x0 is coprime to n i.e. gcd(x0, n) = 1.
   - x0 is also secret.

3. **Generation**:
   - Generate the sequence using the recurrence relation: x{i+1} = x{i}^2 mod n
   - Output a fixed number of least significant bits (often just the least significant bit) of x{i+1} at each step.

Security
========

The security of BBS relies on the hardness of factoring n. Without knowledge of p and q, it is computationally infeasible to predict future or past outputs of the generator.
BBS is considered strong because it is **provably secure** under the **quadratic residuosity assumption**, a problem believed to be computationally difficult.

Applications
============

- Cryptographic protocols (e.g., encryption, key generation).
- Simulations and randomized algorithms where strong security properties are needed.

Practicality
============

While the BBS generator is theoretically secure, it can be relatively slow in practice. For efficiency, other strong PRGs like those based on modern block ciphers (e.g., AES in counter mode) are often used in practical applications.

*/

import java.math.BigInteger;
import java.security.SecureRandom;

public class main {

    // Example usage
    public static void main(String[] args) {
        int bitLength = 512;
        BlumBlumShub bbs = new BlumBlumShub();

        // Setup with two primes to compute n
        bbs.setup(bitLength);

        // Seed the generator with a random seed
        BigInteger seed = new BigInteger("123456789");
        bbs.setState(seed);
        
        // Generate some random bits
        String str = "";
        for (int ix = 0; ix < 32; ix++) {
            str = str.concat(String.format("%d", bbs.nextBit()));
        }
        System.out.println("Random bits [32]: " + str);
        System.out.println("Random 32-bit number: " + bbs.nextBits(32));
    }
}

class BlumBlumShub {
    private BigInteger n; // Modulus (product of two primes)
    private BigInteger state; // Current state
    private SecureRandom random = new SecureRandom();

    /**
     * Setup: Generate two large primes p and q (p ≡ 3 mod 4, q ≡ 3 mod 4), 
     * and compute n = p * q.
     */
    public void setup(int bitLength) {
        BigInteger p, q;
        do {
            p = BigInteger.probablePrime(bitLength / 2, random);
        } while (!p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)));

        do {
            q = BigInteger.probablePrime(bitLength / 2, random);
        } while (!q.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)));

        n = p.multiply(q);
    }

    /**
     * Seed: Initialize the generator with a seed value.
     * The seed must be coprime to n.
     */
    public void setState(BigInteger seed) {
        if (n == null) {
            throw new IllegalStateException("Setup must be completed before seeding.");
        }
        if (!seed.gcd(n).equals(BigInteger.ONE)) {
            throw new IllegalArgumentException("Seed must be coprime to n.");
        }
        state = seed.mod(n);
    }

    /**
     * Generate the next pseudorandom number.
     * Uses the least significant bit (LSB) of the current state.
     */
    public int nextBit() {
        if (state == null) {
            throw new IllegalStateException("Seed must be initialized before generating numbers.");
        }
        state = state.modPow(BigInteger.TWO, n); // x_i+1 = (x_i)^2 mod n
        return state.testBit(0) ? 1 : 0; // Return the least significant bit
    }

    /**
     * Generate the next pseudorandom number with the specified bit length.
     */
    public BigInteger nextBits(int bitLength) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < bitLength; i++) {
            result = result.shiftLeft(1).add(BigInteger.valueOf(nextBit()));
        }
        return result;
    }
}

