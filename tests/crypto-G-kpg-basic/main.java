import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;

public class main {

    public static void main(String[] args) {
        String[] algoArray = { "RSA", "DSA", "EC", "Ed25519", "Ed448", "X25519", "X448" };
        int[] keySizeArray = { 2048,  1024,  256,      0,       0,        0,        0   };
        int errorCount = 0;

        for (int i = 0; i < algoArray.length; i++) {
            String alg = algoArray[i];
            int keySize = keySizeArray[i];

            System.out.println(String.format("=== Testing algorithm: %s ===", alg));

            try {
                // Create KeyPairGenerator
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(alg);
                System.out.println(String.format("getInstance(String) OK"));

                if (keySize > 0) {
                    kpg.initialize(keySize);
                    System.out.println(String.format("initialize(int) OK"));

                    SecureRandom random = new SecureRandom();
                    kpg.initialize(keySize, random);
                    System.out.println(String.format("initialize(int, SecureRandom) OK"));
                }

                // generateKeyPair
                KeyPair kp = kpg.generateKeyPair();
                if (kp == null) {
                    System.out.println(String.format("*** ERROR: generateKeyPair returned null"));
                    errorCount++;
                } else {
                    System.out.println(String.format("generateKeyPair() OK"));
                }

                // getAlgorithm
                System.out.println(String.format("getAlgorithm() = %s", kpg.getAlgorithm()));

                // getProvider
                System.out.println(String.format("getProvider() = %s", kpg.getProvider().getName()));

                // Derived key size if possible
                PublicKey pub = kp.getPublic();
                int derivedSize = 0;

                if (pub instanceof RSAKey) {
                    derivedSize = ((RSAKey) pub).getModulus().bitLength();
                    System.out.println(String.format("Derived key size = %d", derivedSize));
                } else if (pub instanceof ECKey) {
                    derivedSize = ((ECKey) pub).getParams().getOrder().bitLength();
                    System.out.println(String.format("Derived key size = %d", derivedSize));
                } else if (pub instanceof DSAKey) {
                    derivedSize = ((DSAKey) pub).getParams().getP().bitLength();
                    System.out.println(String.format("Derived key size = %d", derivedSize));
                } else if (pub instanceof EdECKey) {
                    System.out.println("Key size cannot be derived for EdEC key");
                } else if (alg.startsWith("X")) {
                    System.out.println("Key size cannot be derived for XDH key");
                } else {
                    System.out.printf("*** ERROR, Unknown key type (%s), cannot derive key size\n", alg);
                    errorCount += 1;
                }

            } catch (NoSuchAlgorithmException e) {
                System.out.println(String.format("*** ERROR: NoSuchAlgorithmException for %s", alg));
                errorCount++;
            } catch (InvalidParameterException e) {
                System.out.println(String.format("*** ERROR: InvalidParameterException for %s", alg));
                errorCount++;
            } catch (Exception e) {
                System.out.println(String.format("*** ERROR: Unexpected exception for %s: %s", alg, e));
                errorCount++;
            }

            System.out.println();
        }

        // Final summary
        Checkers.theEnd(errorCount);
    }
}

