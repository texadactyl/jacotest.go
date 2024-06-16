// Miller-Rabin test
// Algorithm description and pseudocode:
// https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test#Miller%E2%80%93Rabin_test

/*
    Input #1: n > 2, an odd integer to be tested for primality
    Input #2: k, the number of rounds of testing to perform
    Output: "composite" if n is found to be composite, "probably prime" otherwise

    let s > 0 and d odd > 0 such that n - 1 = 2^s*d  # by factoring out powers of 2 from n - 1
    repeat k times:
        a := random(2, n - 2)  # n is always a probable prime to base 1 and n - 1
        x := a^d mod n
        repeat s times:
            y := x^2 mod n
            if y = 1 and x != 1 and x != n - 1 then # nontrivial square root of 1 modulo n
                return "composite"
            x := y
        if y != 1 then
            return "composite"
    return "probably prime"
 */

import java.math.BigInteger;
import java.util.Random;

public class main {

    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static boolean isProbablyPrime(BigInteger n, int k) {
        if (n.compareTo(BigInteger.TWO) == 0) {
            return true; // n = 2
        }
        if (n.compareTo(BigInteger.TWO) < 0) {
            throw new AssertionError("n < 2");
        }
        if (k < 2) {
            throw new AssertionError("k < 2");
        }
        if (n.mod(TWO).equals(BigInteger.ZERO)) {
            return false; // n is a factor of 2
        }

        // Factor out powers of 2 from n - 1
        // such that n - 1 = (2^s) * d
        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(TWO).equals(BigInteger.ZERO)) {
            d = d.divide(TWO);
            s++;
        }

        BigInteger nminus1 = n.subtract(BigInteger.ONE);
        for (int ii = 0; ii < k; ii++) {
            // a := random(2, n - 2)
            BigInteger a = generateRandomInRange(TWO, nminus1);
            if (!useThePower(a, d, n, s)) {
                return false;
            }
        }

        return true;
    }

    private static boolean useThePower(BigInteger a, BigInteger d, BigInteger n, int thePower) {
        BigInteger x = a.modPow(d, n); // x := a^d mod n
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
            return true;
        }
        BigInteger nminus1 = n.subtract(BigInteger.ONE);

        for (int ii = 1; ii < thePower; ii++) {
            x = x.modPow(TWO, n); // y := x^2 mod n
            if (x.equals(BigInteger.ONE)) {
                return false;
            }
            if (x.equals(nminus1)) {
                return true;
            }
        }

        return false;
    }

    private static BigInteger generateRandomInRange(BigInteger min, BigInteger max) {
        Random random = new Random();
        BigInteger randomBigInt = new BigInteger(max.bitLength(), random);
        while (randomBigInt.compareTo(min) < 0 || randomBigInt.compareTo(max) > 0) {
            randomBigInt = new BigInteger(max.bitLength(), random);
        }
        return randomBigInt;
    }

    public static void main(String[] args) {
        int k = 42; // Number of iterations.
        BigInteger n; // Number to test.
        String nstr = "123456789011"; // Use this value if there is no command line argument.
        if (args.length == 1) {
            nstr = args[0]; // Use number from command line.
        }
        try {
            n = new BigInteger(nstr);
        } catch (NumberFormatException ex) {
            throw new AssertionError("*** ERROR, Expected a numeric parameter!");
        }
        boolean isPrime = isProbablyPrime(n, k);
        String str = isPrime ? "probably prime" : "definitely composite";
        System.out.printf("%s is %s\n", nstr, str);
        if (!isPrime) {
            throw new AssertionError("*** ERROR, Expected a probable prime result!");
        }
        assert(str.equals("probably prime"));
    }
}
