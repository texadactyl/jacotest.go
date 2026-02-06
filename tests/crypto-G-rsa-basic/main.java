import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;

public class main {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidParameterException {
        int errorCount = 0;

        String alg = "RSA";
        int keySize = 2048;

        System.out.println(String.format("Testing algorithm: %s", alg));

        // Create KeyPairGenerator
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(alg);
        System.out.println(String.format("getInstance(String) Ok"));

        if (keySize > 0) {
            kpg.initialize(keySize);
            System.out.println(String.format("initialize(int) Ok"));

            SecureRandom random = new SecureRandom();
            kpg.initialize(keySize, random);
            System.out.println(String.format("initialize(int, SecureRandom) Ok"));
        }

        // generateKeyPair
        KeyPair kp = kpg.generateKeyPair();
        if (kp == null) {
            System.out.println(String.format("*** ERROR: generateKeyPair returned null"));
            errorCount++;
        } else {
            System.out.println(String.format("generateKeyPair() Ok"));
        }

        // getAlgorithm
        Checkers.checker("Algorithm", "RSA", kpg.getAlgorithm());

        // getProvider
        System.out.println(String.format("Provider info: %s", kpg.getProvider().getName()));

        // Derive the Public key size.
        RSAPublicKey pub = (RSAPublicKey)kp.getPublic();
        BigInteger modulus = pub.getModulus();
        int derivedKeySize = modulus.bitLength();       
        Checkers.checker("Public Key Size", keySize, derivedKeySize);

        // Derive the Private key size.
        RSAPrivateKey prv = (RSAPrivateKey)kp.getPrivate();
        BigInteger exponent = prv.getPrivateExponent();
        System.out.print("Private key exponent: ");
        System.out.println(exponent);

        // Final summary
        Checkers.theEnd(errorCount);
    }
}

