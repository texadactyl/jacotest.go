import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import javax.crypto.KeyAgreement;

public class main {

    final static String EC_DH_KEY_AGREEMENT = "ECDH";
    final static String EC_GEN_PARM_SPEC = "secp521r1";
    final static String EC_KEY_PAIR = "EC";
    final static String SIG_ALGORITHM = "SHA256withECDSA";
    
    public static void main(String[] args) throws Exception {
    
        int errorCount = 0;
        String message = "Mary had a little lamb whose fleece was white as snow!";
        System.out.printf("Secret message as a String: %s\n", message);
        byte[] txClearTextBytes = message.getBytes();
        System.out.printf("Secret message as a byte array: %s\n", bytesToHex(txClearTextBytes));
       
        // TX: Generate key pair.
        KeyPair kptx = kpGenerator("TX");
        
        // RX: Generate key pair.
        KeyPair kprx = kpGenerator("RX");
              
        // TX: Sign the message
        Signature ecdsaSign = Signature.getInstance(SIG_ALGORITHM);
        ecdsaSign.initSign(kptx.getPrivate());
        ecdsaSign.update(txClearTextBytes);
        byte[] signature = ecdsaSign.sign();
        System.out.printf("TX Signature: %s\n", bytesToHex(signature));
        
        // TX: Generate a secret.
        // TX needs the RX public key to generate a secret.
        KeyAgreement txKeyAgreement = KeyAgreement.getInstance(EC_DH_KEY_AGREEMENT);
        txKeyAgreement.init(kptx.getPrivate());
        txKeyAgreement.doPhase(kprx.getPublic(), true);
        byte[] txSecret = txKeyAgreement.generateSecret();
        
        // RX: Verify the signature.
        // RX needs the TX public key to verify the secret.
        Signature ecdsaVerify = Signature.getInstance(SIG_ALGORITHM);
        ecdsaVerify.initVerify(kptx.getPublic());
        ecdsaVerify.update(txClearTextBytes);
        boolean isVerified = ecdsaVerify.verify(signature);
        errorCount += Checkers.checker("RX Signature verified", true, isVerified);
        
        // RX: Reproduce TX's secret.
        KeyAgreement rxKeyAgreement = KeyAgreement.getInstance(EC_DH_KEY_AGREEMENT);
        rxKeyAgreement.init(kprx.getPrivate());
        rxKeyAgreement.doPhase(kptx.getPublic(), true);
        byte[] rxSecret = rxKeyAgreement.generateSecret();
        
        // Verify that both parties share the same shared secret.
        errorCount += Checkers.checker("Shared Secret", bytesToHex(txSecret), bytesToHex(rxSecret));
        
        Checkers.theEnd(errorCount);
    }
    
    private static KeyPair kpGenerator(String label) throws Exception {
    
        // Instantiate an EC key pair generator.
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(EC_KEY_PAIR);
        
        // Instantiate an EC parameter spec generator.
        ECGenParameterSpec ecGps = new ECGenParameterSpec(EC_GEN_PARM_SPEC);
        
        // Initialize the key pair generator with the EC parameters.
        keyPairGen.initialize(ecGps);
        
        // Generate the EC key pair.
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        System.out.printf("%s Public Key: %s\n", label, keyPair.getPublic());
        System.out.printf("%s Private Key: %s\n", label, keyPair.getPrivate());
        
        return keyPair;
    }

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
