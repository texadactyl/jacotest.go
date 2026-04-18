import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class main {

    private static final String PBE_ALGO   = "PBKDF2WithHmacSHA256";   // key derivation
    private static final String CIPHER_ALGO = "AES/CBC/PKCS5Padding"; // encryption

    private static class EncryptionOutput {
        byte[] ivBytes;
        byte[] cipherText;
    }

    private static SecretKeySpec makeSecretKeySpec(char[] password, byte[] salt,
                                                   int keySize, int iterations) throws Exception {
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBE_ALGO);
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keySize);
        SecretKey secretKey = skf.generateSecret(spec);
        spec.clearPassword();
        return new SecretKeySpec(secretKey.getEncoded(), "AES"); // wrap as AES key
    }

    private static EncryptionOutput encrypt(SecretKeySpec secretKeySpec,
                                            byte[] clearText) throws Exception {
        EncryptionOutput encryptionOutput = new EncryptionOutput();
        Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); // JVM generates a fresh IV
        encryptionOutput.ivBytes  = cipher.getIV();      // grab it directly
        encryptionOutput.cipherText = cipher.doFinal(clearText);
        return encryptionOutput;
    }

    private static byte[] decrypt(SecretKeySpec secretKeySpec,
                                  byte[] ivBytes, byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
        return cipher.doFinal(cipherText);
    }

    private static byte[] getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return salt;
    }

    private static void showBytes(String label, byte[] argBytes) {
        System.out.print(label);
        System.out.print(":\t");
        System.out.println(Base64.getEncoder().encodeToString(argBytes));
    }

    private static void labeledPrint(String label, String string) {
        System.out.print(label);
        System.out.printf("[%d bytes]:\t", string.length());
        System.out.println(string);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Exercise Password-based Encryption/Decryption");

        int keySize    = 256;
        int iterations = 65536;
        char[] password      = "This is a huge secret!".toCharArray();
        String originalString = "Mary had a little lamb.";

        labeledPrint("Cleartext (string)", originalString);
        byte[] msgBytes = originalString.getBytes();
        showBytes("Cleartext (base 64)", msgBytes);

        byte[] salt = getSalt();
        System.out.println("getSalt() ok");

        SecretKeySpec secretKeySpec = makeSecretKeySpec(password, salt, keySize, iterations);
        System.out.println("SecretKeySpec instantiation ok");

        EncryptionOutput eo = encrypt(secretKeySpec, msgBytes);
        showBytes("Ciphertext (base 64)", eo.cipherText);

        byte[] clearText = decrypt(secretKeySpec, eo.ivBytes, eo.cipherText);
        showBytes("Cleartext (base 64)", clearText);

        String decryptedString = new String(clearText, StandardCharsets.UTF_8);
        labeledPrint("Cleartext (string)", decryptedString);

        if (decryptedString.equals(originalString)) {
            System.out.println("SUCCESS: round-trip matches.");
        } else {
            throw new AssertionError("*** ERROR, decryptedString != originalString");
        }
    }
}
