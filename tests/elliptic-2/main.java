import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

public class main {

    public static void main(String[] args) throws Exception {
    
        // TX: Generate key pair.
        KeyPairGenerator txKeyPairGen = KeyPairGenerator.getInstance("EC");
        txKeyPairGen.initialize(new ECGenParameterSpec("secp256r1"));
        KeyPair txKeyPair = txKeyPairGen.generateKeyPair();

        // RX: Generate key pair.
        KeyPairGenerator rxKeyPairGen = KeyPairGenerator.getInstance("EC");
        rxKeyPairGen.initialize(new ECGenParameterSpec("secp256r1"));
        KeyPair rxKeyPair = rxKeyPairGen.generateKeyPair();

        // Derive TX and RX shared secrets.
        byte[] txSharedSecret = deriveSharedSecret(txKeyPair.getPrivate(), rxKeyPair.getPublic());
        System.out.println("Shared secret (Sender): " + bytesToHex(txSharedSecret));
        byte[] rxSharedSecret = deriveSharedSecret(rxKeyPair.getPrivate(), txKeyPair.getPublic());
        System.out.println("Shared secret (Receiver): " + bytesToHex(rxSharedSecret));

        // Verify both parties derived the same shared secret.
        if (Arrays.equals(txSharedSecret, rxSharedSecret)) {
            System.out.println("Success :: The TX and RX shared secrets match");
        } else {
            throw new AssertionError("TX and RX shared secrets do not match!");
        }

        // Derive a 256-bit symmetric key from the TX (RX) shared secret.
        byte[] symmetricKeyBytes = Arrays.copyOf(txSharedSecret, 32); // Use first 32 bytes for AES-256
        SecretKey symmetricKey = new SecretKeySpec(symmetricKeyBytes, "AES");

        // Original message to encrypt.
        String cleartext_1 = "Well! This is a fine mess you've gotten into!";
        System.out.println("Original cleartext message: " + cleartext_1);

        // Encrypt the original message.
        byte[] iv = generateIV(); // Random IV
        byte[] encryptedMessage = encryptMessage(symmetricKey, cleartext_1.getBytes(), iv);
        System.out.println("Encrypted message: " + bytesToHex(encryptedMessage));

        // Decrypt the message.
        String cleartext_2 = decryptMessage(symmetricKey, encryptedMessage, iv);
        System.out.println("Decrypted cleartext message: " + cleartext_2);
        
        // How did we do?
        if(cleartext_2.equals(cleartext_1)) {
            System.out.println("Success :: The TX and RX decryptions match");
        } else {
            throw new AssertionError("The TX and RX decryptions do not match");
        }
    }

    // Derive a shared secret using ECDH
    public static byte[] deriveSharedSecret(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        return keyAgreement.generateSecret();
    }

    // Encrypt a message using AES-GCM
    public static byte[] encryptMessage(SecretKey key, byte[] message, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv); // 128-bit tag length
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        return cipher.doFinal(message);
    }

    // Decrypt a message using AES-GCM
    public static String decryptMessage(SecretKey key, byte[] encryptedMessage, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv); // 128-bit tag length
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        return new String(cipher.doFinal(encryptedMessage));
    }

    // Generate a random IV
    public static byte[] generateIV() {
        byte[] iv = new byte[12]; // Recommended size for GCM
        new SecureRandom().nextBytes(iv);
        return iv;
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

