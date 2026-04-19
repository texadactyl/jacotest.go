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

    private static final String PBE_ALGO = "PBKDF2WithHmacSHA256";    // key derivation
    private static final String KEY_ALGO = "AES";                     // for SecretKeySpec
    private static final String CIPHER_ALGO = "AES/CBC/PKCS5Padding"; // for Cipher.getInstance()

    private static class MyEncryptionOutput {
        byte[] ivBytes;
        byte[] cipherText;
    }

    // Make a secret key specification.
    private static SecretKeySpec makeSecretKeySpec(char[] password, byte[] salt, int iterations, int keySize) throws Exception {
        // Instantiate a PBE spec using the input parameters.
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keySize);
        // Generate a secret key based on the PBE spec.
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBE_ALGO);
        SecretKey secretKey = skf.generateSecret(spec);
        // Return a secret key spec based on the secret key and chosen key algorithm.
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGO);
    }

    // Perform encryption.
    private static MyEncryptionOutput encrypt(SecretKeySpec secretKeySpec, byte[] clearText) throws Exception {
        MyEncryptionOutput myEncryptionOutput = new MyEncryptionOutput();
        Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        myEncryptionOutput.ivBytes  = cipher.getIV();
        myEncryptionOutput.cipherText = cipher.doFinal(clearText);
        return myEncryptionOutput;
    }

    // Perform decryption.
    private static byte[] decrypt(SecretKeySpec secretKeySpec, byte[] ivBytes, byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
        return cipher.doFinal(cipherText);
    }

    // Generate and return secure random salt.
    private static byte[] getSalt() throws Exception {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return salt;
    }

    // Show a labeled byte array.
    private static void showBytes(String label, byte[] argBytes) {
        System.out.print(label);
        System.out.print(":\t");
        System.out.println(Base64.getEncoder().encodeToString(argBytes));
    }

    // Labeled printing.
    private static void labeledPrint(String label, String string) {
        System.out.print(label);
        System.out.printf("[%d bytes]:\t", string.length());
        System.out.println(string);
    }

    // MAIN
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

        SecretKeySpec secretKeySpec = makeSecretKeySpec(password, salt, iterations, keySize);
        System.out.printf("SecretKeySpec algo: %s, encoded: %s, encfmt: %s\n", secretKeySpec.getAlgorithm(), HexDump.bytesToHex(secretKeySpec.getEncoded()), secretKeySpec.getFormat());

        MyEncryptionOutput eo = encrypt(secretKeySpec, msgBytes);
        showBytes("Ciphertext (base 64)", eo.cipherText);
        showBytes("\tAccompanying IV", eo.ivBytes);

        byte[] clearText = decrypt(secretKeySpec, eo.ivBytes, eo.cipherText);
        showBytes("Cleartext (base 64)", clearText);

        String decryptedString = new String(clearText);
        labeledPrint("Cleartext (string)", decryptedString);

        if (decryptedString.equals(originalString)) {
            System.out.println("SUCCESS: round-trip matches.");
        } else {
            throw new AssertionError("*** ERROR, decryptedString != originalString");
        }
    }
}
