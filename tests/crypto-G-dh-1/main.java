import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

public class main {

    final static String DH_KEY_AGREEMENT = "DiffieHellman";
    final static String DH_KEY_PAIR = "DiffieHellman";
    final static BigInteger DH_P = new BigInteger(
    "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1" +
    "29024E088A67CC74020BBEA63B139B22514A08798E3404DD" +
    "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245" +
    "E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7ED" +
    "EE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381" +
    "FFFFFFFFFFFFFFFF",
    16 );
    final static BigInteger DH_G = BigInteger.valueOf(2);
    final static int DH_L = 256;
    
    public static void main(String[] args) throws Exception {
    
        int errorCount = 0;
        
        // Generate key pair for transmitter.
        KeyPair kptx = kpGenerator("TX");
        
        // Generate key pair for receiver.
        KeyPair kprx = kpGenerator("RX");
        
        // TX: Generate a secret.
        KeyAgreement txKeyAgreement = KeyAgreement.getInstance(DH_KEY_AGREEMENT);
        txKeyAgreement.init(kptx.getPrivate());
        txKeyAgreement.doPhase(kprx.getPublic(), true);
        byte[] txSecret = txKeyAgreement.generateSecret();
        System.out.printf("tx Secret message as a byte array: %s\n", bytesToHex(txSecret));
               
        // RX: Reproduce TX's secret.
        KeyAgreement rxKeyAgreement = KeyAgreement.getInstance(DH_KEY_AGREEMENT);
        rxKeyAgreement.init(kprx.getPrivate());
        rxKeyAgreement.doPhase(kptx.getPublic(), true);
        byte[] rxSecret = rxKeyAgreement.generateSecret();
        System.out.printf("rx Secret message as a byte array: %s\n", bytesToHex(rxSecret));
        
        // Verify that both parties share the same shared secret.
        errorCount += Checkers.checker("Shared Secret", bytesToHex(txSecret), bytesToHex(rxSecret));
        
        Checkers.theEnd(errorCount);
    }
    
    private static KeyPair kpGenerator(String label) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(DH_KEY_PAIR);
        DHParameterSpec dhGps = new DHParameterSpec(DH_P, DH_G, DH_L);
        kpg.initialize(dhGps);
        KeyPair keyPair = kpg.generateKeyPair();
        System.out.printf("%s Private Key: %s\n", label, keyPair.getPrivate());
        System.out.printf("%s Public Key: %s\n", label, keyPair.getPublic());
        
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
