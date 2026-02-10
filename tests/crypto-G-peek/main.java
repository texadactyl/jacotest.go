import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.NamedParameterSpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class main {
    
    final static boolean FLAG_AES = false;
        
    public static void main(String[] args) throws Exception {
    
        NamedParameterSpec paramSpec;
        KeyGenerator aesGen;
        SecretKey aesKey;
    
        // ===== Generate various keys =====
        
        if (FLAG_AES) {
            aesGen = KeyGenerator.getInstance("AES");
            aesGen.init(256);
            aesKey = aesGen.generateKey();
        }
        
        KeyPairGenerator dhGen = KeyPairGenerator.getInstance("DiffieHellman");
        dhGen.initialize(1024);
        KeyPair dhKeys = dhGen.generateKeyPair();
        
        KeyPairGenerator dsaGen = KeyPairGenerator.getInstance("DSA");
        dsaGen.initialize(2048);
        KeyPair dsaKeys = dsaGen.generateKeyPair();
        
        KeyPairGenerator ecGen = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecGps = new ECGenParameterSpec("secp521r1");
        ecGen.initialize(ecGps);
        KeyPair ecKeys = ecGen.generateKeyPair();
        
        KeyPairGenerator eddsaGen = KeyPairGenerator.getInstance("EdDSA");
        paramSpec = new NamedParameterSpec("Ed25519");
        eddsaGen.initialize(paramSpec);
        KeyPair eddsaKeys = eddsaGen.generateKeyPair();
        
        KeyPairGenerator rsaGen = KeyPairGenerator.getInstance("RSA");
        rsaGen.initialize(4096);
        KeyPair rsaKeys = rsaGen.generateKeyPair();
        
        KeyPairGenerator x25519Gen = KeyPairGenerator.getInstance("X25519");
        KeyPair x25519Keys = x25519Gen.generateKeyPair();

        // ===== View all keys =====
        
        if (FLAG_AES) {
            System.out.println("\n=== AES Key ===");
            printKeyInfo(aesKey);
        }
        
        System.out.println("\n=== DH Public Key ===");
        printKeyInfo(dhKeys.getPublic());
        
        System.out.println("\n=== DH Private Key ===");
        printKeyInfo(dhKeys.getPrivate());

        System.out.println("\n=== DSA Public Key ===");
        printKeyInfo(dsaKeys.getPublic());
        
        System.out.println("\n=== DSA Private Key ===");
        printKeyInfo(dsaKeys.getPrivate());

        System.out.println("\n=== EC Public Key ===");
        printKeyInfo(ecKeys.getPublic());
        
        System.out.println("\n=== EC Private Key ===");
        printKeyInfo(ecKeys.getPrivate());

        System.out.println("\n=== EdDSA Public Key ===");
        printKeyInfo(eddsaKeys.getPublic());
        
        System.out.println("\n=== EdDSA Private Key ===");
        printKeyInfo(eddsaKeys.getPrivate());

        System.out.println("=== RSA Public Key ===");
        printKeyInfo(rsaKeys.getPublic());
        
        System.out.println("\n=== RSA Private Key ===");
        printKeyInfo(rsaKeys.getPrivate());
        
        System.out.println("\n=== X26619 Public Key ===");
        printKeyInfo(x25519Keys.getPublic());
        
        System.out.println("\n=== X26619 Private Key ===");
        printKeyInfo(x25519Keys.getPrivate());

    }
    
    private static void printKeyInfo(Key key) {
        byte[] encoded = key.getEncoded();
        System.out.printf("Algorithm: %s\n",  key.getAlgorithm());
        System.out.printf("Format: %s\n",  key.getFormat());
        System.out.printf("Length: %s\n",  encoded.length + " bytes");
        System.out.printf("Hex (first 64 bytes): %s\n",  HexDump.bytesToHex(encoded, 64));
        System.out.printf("Base64: %s\n",  Base64.getEncoder().encodeToString(encoded));
    }
}

