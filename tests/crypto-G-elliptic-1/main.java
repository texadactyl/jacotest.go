import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import javax.crypto.KeyAgreement;

public class main {

    // The Elliptic Curve Diffie-Hellman protocol. This is the actual mechanism that combines your private key with the other party's public key
    // to derive a shared secret that both parties can compute independently.
    final static String EC_DH_KEY_AGREEMENT = "ECDH";
    
    // Identify the specific elliptic curve to use. The "secp521r1" curve is a NIST-standardized curve with a 521-bit key size.
    final static String EC_GEN_PARM_SPEC = "secp521r1";
    
    // Specify the key pair generation algorithm: elliptic curve key pairs.
    final static String EC_KEY_PAIR = "EC";
    
    // Specify the digital signature algorithm used in signature signing and verification (authentication).
    final static String SIG_ALGORITHM = "SHA256withECDSA";
    

    public static void main(String[] args) throws Exception {
    
        int errorCount = 0;

        // Generate key pair for transmitter.
        KP kptx = kpGenerator("TX");

        // Generate key pair for receiver.
        KP kprx = kpGenerator("RX");

        // Here is the shared secret message.
        String message = "Mary had a little lamb whose fleece was white as snow!";
        System.out.printf("Secret message as a String: %s\n", message);
        byte[] txClearTextBytes = message.getBytes();
        System.out.printf("Secret message as a byte array: %s\n", bytesToHex(txClearTextBytes));

        // TX: Sign the message
        Signature ecdsaSign = Signature.getInstance(SIG_ALGORITHM);
        ecdsaSign.initSign(kptx.privateKey);
        ecdsaSign.update(txClearTextBytes);
        byte[] signature = ecdsaSign.sign();
        System.out.printf("TX Signature: %s\n", bytesToHex(signature));

        // TX: Generate a secret.
        KeyAgreement txKeyAgreement = KeyAgreement.getInstance(EC_DH_KEY_AGREEMENT);
        txKeyAgreement.init(kptx.privateKey);
        txKeyAgreement.doPhase(kprx.publicKey, true);
        byte[] txSecret = txKeyAgreement.generateSecret();

        // RX: Verify the signature.
        Signature ecdsaVerify = Signature.getInstance(SIG_ALGORITHM);
        ecdsaVerify.initVerify(kptx.publicKey);
        ecdsaVerify.update(txClearTextBytes);
        boolean isVerified = ecdsaVerify.verify(signature);
        errorCount += Checkers.checker("RX Signature verified", true, isVerified);

        // RX: Reproduce TX's secret.
        KeyAgreement rxKeyAgreement = KeyAgreement.getInstance(EC_DH_KEY_AGREEMENT);
        rxKeyAgreement.init(kprx.privateKey);
        rxKeyAgreement.doPhase(kptx.publicKey, true);
        byte[] rxSecret = rxKeyAgreement.generateSecret();

        // Verify both parties share the same shared secret
        errorCount += Checkers.checker("Shared Secret", bytesToHex(txSecret), bytesToHex(rxSecret));
        
        Checkers.theEnd(errorCount);
    }

    // Utility method to convert byte array to hex string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    private static KP kpGenerator(String label) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(EC_KEY_PAIR);
        keyPairGen.initialize(new ECGenParameterSpec(EC_GEN_PARM_SPEC));
        KeyPair keyPair = keyPairGen.generateKeyPair();
        KP kp = new KP();
        kp.privateKey = keyPair.getPrivate();
        kp.publicKey = keyPair.getPublic();
        System.out.printf("%s Public Key: %s\n", label, kp.publicKey);
        System.out.printf("%s Private Key: %s\n", label, kp.privateKey);
        return kp;
    }

    private static class KP {
        PrivateKey privateKey;
        PublicKey publicKey;
    }

}
