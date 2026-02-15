import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

public class main {

    // The Elliptic Curve Diffie-Hellman protocol. This is the actual mechanism that combines your private key with the other party's public key
    // to derive a shared secret that both parties can compute independently.
    final static String EC_DH_KEY_AGREEMENT = "ECDH";
    
    // Identify the specific elliptic curve to use. The "secp521r1" curve is a NIST-standardized curve with a 521-bit key size.
    final static String EC_GEN_PARM_SPEC = "secp521r1";
    
    // Specify the key pair generation algorithm: elliptic curve key pairs.
    final static String EC_KEY_PAIR = "EC";
    
    // Specify the secret key type.
    final static String SECRET_KEY_TYPE = "AES";
    
    // Secret key size in bits.
    final static int SECRET_KEY_BITSIZE = 256;

    // Specify the encryption/decryption algorithm.
    // Note that GCM is a stream cipher so no padding is performed.
    final static String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    
    // GCM tag length for authentication.
    final static int GCM_TAG_LENGTH = 128;
    
    // GCM IV size.
    final static int GCM_IV_SIZE = 12;
    

    public static void main(String[] args) throws Exception {
    
        int errorCount = 0;
    
        // TX: Generate key pair.
        KeyPairGenerator kptxGen = KeyPairGenerator.getInstance(EC_KEY_PAIR);
        kptxGen.initialize(new ECGenParameterSpec(EC_GEN_PARM_SPEC));
        KeyPair kptx = kptxGen.generateKeyPair();

        // RX: Generate key pair.
        KeyPairGenerator rxKeyPairGen = KeyPairGenerator.getInstance(EC_KEY_PAIR);
        rxKeyPairGen.initialize(new ECGenParameterSpec(EC_GEN_PARM_SPEC));
        KeyPair rxKeyPair = rxKeyPairGen.generateKeyPair();

        // Derive TX and RX shared secrets.
        byte[] txSharedSecret = deriveSharedSecret(kptx.getPrivate(), rxKeyPair.getPublic());
        byte[] rxSharedSecret = deriveSharedSecret(rxKeyPair.getPrivate(), kptx.getPublic());

        // Verify both parties derived the same shared secret.
        errorCount += Checkers.checker("Shared Secret", HexDump.bytesToHex(txSharedSecret), HexDump.bytesToHex(rxSharedSecret));

        // Derive a symmetric key from the TX (RX) shared secret.
        byte[] symmetricKeyBytes = Arrays.copyOf(txSharedSecret, SECRET_KEY_BITSIZE / 8);
        SecretKey symmetricKey = new SecretKeySpec(symmetricKeyBytes, SECRET_KEY_TYPE);

        // Original message to encrypt.
        String cleartext_1 = "Well! This is a fine mess you've gotten us into!";
        System.out.printf("Original cleartext message: %s\n", cleartext_1);

        // Generate a random IV.
        byte[] iv = generateIV(); 

        // Encrypt the original message.
        byte[] encryptedMessage = encryptMessage(symmetricKey, cleartext_1.getBytes(), iv);
        System.out.printf("Encrypted message: %s\n", HexDump.bytesToHex(encryptedMessage));

        // Decrypt the message.
        String cleartext_2 = decryptMessage(symmetricKey, encryptedMessage, iv);
        System.out.printf("Decrypted cleartext message: %s\n", cleartext_2);
        
        // How did we do?
        errorCount += Checkers.checker("TX msg = RX msg?", cleartext_1, cleartext_2);
        
        Checkers.theEnd(errorCount);
    }

    // Compare 2 byte arrays.
    public static boolean arraysEqual(byte[] A, byte[] B) {
        if (A.length != B.length)
            return false;
        for (int ix = 0; ix < A.length; ix++) {
            if (A[ix] != B[ix])
                return false;
        }
        return true;
    }

    // Derive a shared secret using ECDH
    public static byte[] deriveSharedSecret(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        KeyAgreement keyAgreement = KeyAgreement.getInstance(EC_DH_KEY_AGREEMENT);
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        return keyAgreement.generateSecret();
    }

    // Encrypt a message using AES-GCM
    public static byte[] encryptMessage(SecretKey key, byte[] message, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        AlgorithmParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        return cipher.doFinal(message);
    }

    // Decrypt a message using AES-GCM
    public static String decryptMessage(SecretKey key, byte[] encryptedMessage, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        AlgorithmParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        return new String(cipher.doFinal(encryptedMessage));
    }

    // Generate a random IV.
    // Note that the IV is in cleartext between transmitter and receiver.
    // Always generate a new random IV for each message.
    public static byte[] generateIV() {
        byte[] iv = new byte[GCM_IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}

