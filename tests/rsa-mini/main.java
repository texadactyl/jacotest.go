// Java Program to Implement the RSA Algorithm
// Hacked from https://www.geeksforgeeks.org/java-program-to-implement-the-rsa-algorithm/

/***

    Public and Private Key simple cryptography
    ==========================================

    Description

        1. Consider two prime numbers p and q.
        2. Compute n = p * q.
        3. Compute PHI(n) = (p – 1) * (q – 1).
        4. Choose e such gcd(e , PHI(n) ) = 1.
        5. Calculate d such e*d mod PHI(n) = 1. d = (k*PHI(n) + 1) / e for some integer k.
        6. Public Key {e,n} Private Key {d,n}.
        7. Cipher text C = Pe mod n where P = plaintext.
        8. For Decryption D = Dd mod n where D will return the plaintext.

    Assume the following vectors of integers

        P = plaintext.
        C = ciphertext.
        D = decrypted ciphertext (should equal P).

    Example

        1. Suppose p = 53 and q = 59.
        2. Then, n = 53 * 59 = 3127.
        3. PHI(n) = 52 * 58 = 3016.
        4. e = 3.
        5. Choose k=2. Then, d = (2 * 3016 + 1) / 3 = 2011.
        6. Public key = {3, 3127}, Private key = {3, 2011}.
        7. C = (P ** e) modulo n = 1728.
        8. D = (C ** d) modulo n = 12.
        9. Assert that D = P i.e. 12 = 12.

The source code is working with long scalars. In a more realistic case it would be working with long arrays. But, the principle is the same.
    
***/

import java.math.BigInteger;

public class main {

    static final boolean debugging = true;

	public static void main(String args[]) {
	
		long p, q, n, z, d = 0, e;
		int errorCount = 0;

		// Chose prime numbers p and q.
		long [] primes = findLargestTwoPrimes(1000); // *** checker() calls are dependent on argument value.
		p = primes[0];
		q = primes[1];
		System.out.printf("Primes  = %d, %d\n", p, q);
		errorCount += Checkers.checker("p", 997, p); // ***** based on findLargestTwoPrimes(1000)
		errorCount += Checkers.checker("q", 991, q); // ***** based on findLargestTwoPrimes(1000)
		
		// The number to be encrypted and decrypted
		long plainText = 12;
		System.out.printf("Plaintext long scalar message = %d\n", plainText);
		
		double cDouble;
		long cLong;
		BigInteger biDecryptedCiphertext;

		// Compute n and PHI(n).
		n = p * q;
		z = (p - 1) * (q - 1); // PHI(n)

		System.out.printf("n = %d\n", n);
		System.out.printf("PHI(n) = %d\n", z);

        // Compute e.
		for (e = 2; e < z; e++) {
			// e is for public key exponent
			if (gcd(e, z) == 1) {
				break;
			}
		}
		System.out.printf("e = %d\n", e);
		
		// Compute d.
		for (int ix = 0; ix <= 9; ix++) {
			long x = 1 + (ix * z);

			// d is for private key exponent
			if (x % e == 0) {
				d = x / e;
				break;
			}
		}
		System.out.printf("d = %d\n", d);
		
		// Announce keys.
		System.out.printf("Public key  = {%d, %d}\n", e, n);
		System.out.printf("Private key = {%d, %d}\n", d, n);
		
		// Compute ciphertext, BigInteger C.
		//cDouble = (Math.pow((double) plainText, (double) e)) % n + 0.5;
        //cLong = (long) cDouble;   
		//if (debugging) System.out.printf("*DEBUG* cDouble: %f, cLong: %d\n", cDouble, cLong);
		cLong = safePower(plainText, e);
		System.out.printf("Ciphertext message = %d\n", cLong);
		BigInteger C = BigInteger.valueOf(cLong);

		// Compute decrypted ciphertext, BigInteger biDecryptedCiphertext.
		BigInteger N = BigInteger.valueOf(n);
		biDecryptedCiphertext = (C.pow((int)d)).mod(N);
		long decryptedCiphertext = biDecryptedCiphertext.longValue();
		System.out.printf("Decrypted ciphertext = %d\n", decryptedCiphertext);
		
		// Is the decrypted ciphertext = the plaintext ?
		errorCount += Checkers.checker("decryptedCiphertext == plainText?", decryptedCiphertext, plainText);

		Checkers.theEnd(errorCount);
	}

    // Find the largets two primes <= the given long argument.
    public static long[] findLargestTwoPrimes(long arg) {
        long first = -1, second = -1;
        
        for (long ix = arg; ix >= 2; ix--) {
            if (isPrime(ix)) {
                if (first == -1) {
                    first = ix;
                } else {
                    second = ix;
                    break;
                }
            }
        }
        return new long[]{first, second};
    }
    
    // Is the long argument prime?
    private static boolean isPrime(long arg) {
        if (arg < 2) return false;
        if (arg % 2 == 0) return arg == 2;
        for (long ix = 3; ix * ix <= arg; ix += 2) {
            if (arg % ix == 0) return false;
        }
        return true;
    }

    // Compute GCD of left and right.
	static long gcd(long left, long right) {
		if (left == 0)
			return right;
		else
			return gcd(right % left, left);
	}

    // Safely raise the given long base to the given long exponent power.
    public static long safePower(long base, long exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Negative exponent not supported for long results.");
        }
        if (base == 0) return (exponent == 0) ? 1 : 0; // Define 0^0 as 1
        if (base == 1 || exponent == 0) return 1;
        if (base == -1) return (exponent % 2 == 0) ? 1 : -1;

        long result = 1;
        long current = base;

        while (exponent > 0) {
            if ((exponent & 1) == 1) { // If the exponent is odd
                if (willMultiplyOverflow(result, current)) {
                    throw new ArithmeticException("Overflow detected in exponentiation.");
                }
                result *= current;
            }
            exponent >>= 1;
            if (exponent > 0) {
                if (willMultiplyOverflow(current, current)) {
                    throw new ArithmeticException("Overflow detected in exponentiation.");
                }
                current *= current;
            }
        }

        return result;
    }

    private static boolean willMultiplyOverflow(long left, long right) {
        if (left > 0 && right > 0 && left > Long.MAX_VALUE / right) return true;
        if (left > 0 && right < 0 && right < Long.MIN_VALUE / left) return true;
        if (left < 0 && right > 0 && left < Long.MIN_VALUE / right) return true;
        if (left < 0 && right < 0 && left < Long.MAX_VALUE / right) return true;
        return false;
    }
}

