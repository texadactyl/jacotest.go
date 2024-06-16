import java.math.BigInteger;
// original: import java.security.SecureRandom;
import java.util.Random;

public class PaillierCryptosystem {
    private BigInteger n, g, lambda, mu;
    private int bitLength;

    public PaillierCryptosystem(int bitLength) {
        this.bitLength = bitLength;
        generateKeys();
    }

    private void generateKeys() {
        // original: SecureRandom rnd = new SecureRandom();
        Random rnd = new Random();
        // original: BigInteger p = new BigInteger(bitLength / 2, 100, rnd);
        BigInteger p = new BigInteger(bitLength / 2, rnd);
        // original: BigInteger q = new BigInteger(bitLength / 2, 100, rnd);
        BigInteger q = new BigInteger(bitLength / 2, rnd);
        n = p.multiply(q);
        g = n.add(BigInteger.ONE);
        lambda = lcm(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
        mu = lambda.modInverse(n);
    }

    private BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(a.gcd(b));
    }

    public BigInteger[] getPublicKey() {
        return new BigInteger[]{n, g};
    }

    public BigInteger encrypt(BigInteger plaintext) {
        // original: SecureRandom rnd = new SecureRandom();
        Random rnd = new Random();
        BigInteger r = new BigInteger(bitLength, rnd);
        BigInteger nSquared = n.multiply(n);
        BigInteger gm = g.modPow(plaintext, nSquared);
        BigInteger rn = r.modPow(n, nSquared);
        return gm.multiply(rn).mod(nSquared);
    }

    public BigInteger decrypt(BigInteger ciphertext) {
        BigInteger nSquared = n.multiply(n);
        BigInteger lambdaCT = ciphertext.modPow(lambda, nSquared).subtract(BigInteger.ONE).divide(n);
        return lambdaCT.multiply(mu).mod(n);
    }

    public BigInteger add(BigInteger c1, BigInteger c2) {
        BigInteger nSquared = n.multiply(n);
        return c1.multiply(c2).mod(nSquared);
    }
}

