import java.security.*;
import java.security.interfaces.*;

public class main {

    public static void main(String[] args) throws Exception {
        String[] algoArray = {
            "RSA", "DH", "EC", "DSA", "Ed25519", "Ed448", "X25519", "X448"
        };

        int[] keySizeArray = {
            2048, 0, 256, 1024, 0, 0, 0, 0
        };

        int errorCount = 0;

        byte[] message =
            "The quick brown fox jumps over the lazy dog".getBytes();

        for (int i = 0; i < algoArray.length; i++) {
            String alg = algoArray[i];
            int keySize = keySizeArray[i];

            System.out.println(
                String.format("=== Testing algorithm: %s ===", alg));

            // Create KeyPairGenerator
            KeyPairGenerator kpg =
                KeyPairGenerator.getInstance(alg);
            System.out.println(
                String.format("getInstance(String) OK"));

            if (keySize > 0) {
                kpg.initialize(keySize);
                System.out.println(
                    String.format("initialize(int) OK"));

                SecureRandom random = new SecureRandom();
                kpg.initialize(keySize, random);
                System.out.println(
                    String.format("initialize(int, SecureRandom) OK"));
            }

            // generateKeyPair
            KeyPair kp = kpg.generateKeyPair();
            if (kp == null) {
                System.out.println(
                    String.format("*** ERROR: generateKeyPair returned null"));
                errorCount++;
                continue;
            }
            System.out.println(
                String.format("generateKeyPair() OK"));

            // getAlgorithm
            System.out.println(
                String.format("getAlgorithm() = %s", kpg.getAlgorithm()));

            // getProvider
            System.out.println(
                String.format("getProvider() = %s",
                    kpg.getProvider().getName()));

            // Derived key size if possible
            PublicKey pub = kp.getPublic();

            switch (alg) {
                case "RSA":
                    System.out.println(
                        String.format("Derived key size = %d",
                            ((RSAPublicKey) pub)
                                .getModulus()
                                .bitLength()));
                    break;

                case "EC":
                    System.out.println(
                        String.format("Derived key size = %d",
                            ((ECPublicKey) pub)
                                .getParams()
                                .getOrder()
                                .bitLength()));
                    break;

                case "DSA":
                    System.out.println(
                        String.format("Derived key size = %d",
                            ((DSAPublicKey) pub)
                                .getParams()
                                .getP()
                                .bitLength()));
                    break;

                case "DH":
                case "Ed25519":
                case "Ed448":
                case "X25519":
                case "X448":
                    System.out.println(
                        String.format(
                            "Key size cannot be derived for %s keys",
                            alg));
                    break;

                default:
                    System.out.println(
                        String.format(
                            "*** ERROR: Unknown key type (%s)", alg));
                    errorCount++;
                    continue;
            }

            /* ===============================
             * Signature tests (where valid)
             * =============================== */
            try {
                Signature sig = null;

                if (alg.equals("RSA")) {
                    sig = Signature.getInstance("SHA256withRSA");
                } else if (alg.equals("DSA")) {
                    sig = Signature.getInstance("SHA256withDSA");
                } else if (alg.equals("EC")) {
                    sig = Signature.getInstance("SHA256withECDSA");
                } else if (alg.equals("Ed25519")) {
                    sig = Signature.getInstance("Ed25519");
                }

                if (sig != null) {
                    // Sign
                    sig.initSign(kp.getPrivate());
                    sig.update(message);
                    byte[] signature = sig.sign();

                    System.out.println(
                        String.format(
                            "sign() OK, signature length = %d",
                            signature.length));

                    // Verify
                    sig.initVerify(kp.getPublic());
                    sig.update(message);
                    boolean ok = sig.verify(signature);
                    Checkers.checker(String.format("%s Signature Verify", alg), true, ok);

                 } else {
                    System.out.println(
                        String.format(
                            "Signature not applicable for %s", alg));
                }

            } catch (NoSuchAlgorithmException e) {
                System.out.println(
                    String.format(
                        "Signature algorithm not supported: %s",
                        e.getMessage()));
            }

            System.out.println();
        }

        // Final summary
        Checkers.theEnd(errorCount);
    }
}

