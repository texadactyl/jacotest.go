import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import javax.crypto.KeyAgreement;

public class main {

    private static class KP {
        PrivateKey privateKey;
        PublicKey publicKey;
    }

    private static KP kpGenerator(String label) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC");
        keyPairGen.initialize(new ECGenParameterSpec("secp256r1")); // Standard ECC curve
        KeyPair keyPair = keyPairGen.generateKeyPair();
        KP kp = new KP();
        kp.privateKey = keyPair.getPrivate();
        kp.publicKey = keyPair.getPublic();
        System.out.println(label + " Private Key: " + kp.privateKey);
        System.out.println(label + " Public Key: " + kp.publicKey);
        return kp;
    }

    public static void main(String[] args) throws Exception {

        // Generate key pair for transmitter.
        KP kptx = kpGenerator("TX");

        // Generate key pair for receiver.
        KP kprx = kpGenerator("RX");

        // Here is the shared secret message.
        String message = "There's no place called \"Home\"";
        byte[] txClearTextBytes = message.getBytes();

        // TX: Sign the message
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
        ecdsaSign.initSign(kptx.privateKey);
        ecdsaSign.update(txClearTextBytes);
        byte[] signature = ecdsaSign.sign();
        System.out.println("TX Signature: " + bytesToHex(signature));

        // TX: Generate a secret using ECDH key agreement.
        KeyAgreement txKeyAgreement = KeyAgreement.getInstance("ECDH");
        txKeyAgreement.init(kptx.privateKey);
        txKeyAgreement.doPhase(kprx.publicKey, true);
        byte[] txSecret = txKeyAgreement.generateSecret();
        System.out.println("TX shared secret: " + bytesToHex(txSecret));

        // RX: Verify the signature.
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
        ecdsaVerify.initVerify(kprx.publicKey);
        ecdsaVerify.update(txClearTextBytes);
        boolean isVerified = ecdsaVerify.verify(signature);
        System.out.println("RX Signature verified: " + isVerified);

        // RX: Generate a secret using ECDH key agreement.
        KeyAgreement rxKeyAgreement = KeyAgreement.getInstance("ECDH");
        rxKeyAgreement.init(kprx.privateKey);
        rxKeyAgreement.doPhase(kptx.publicKey, true);
        byte[] rxSecret = rxKeyAgreement.generateSecret();
        System.out.println("RX shared secret: " + bytesToHex(rxSecret));

        // Verify both parties derived the same shared secret
        boolean result = MessageDigest.isEqual(txSecret, rxSecret);
        if (result) {
            System.out.println("Success :: The TX and RX shared secrets match");
        } else {
            throw new AssertionError("The TX and RX shared secrets do not match");
        }
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
}
