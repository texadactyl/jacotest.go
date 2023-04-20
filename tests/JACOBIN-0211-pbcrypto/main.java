// Issue JACOBIN-211 "Handle invokedynamic bytecode"

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

    public static class EncryptionOutput {
        byte[] ivBytes;
        byte[] cipherText;
    }
    
    public static SecretKeySpec makeSecretKeySpec(char[] password, byte[] salt, int keySize, int iterations) throws Exception {
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keySize);
        SecretKey secretKey = skf.generateSecret(spec);
        
        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }

    public static EncryptionOutput encrypt(SecretKeySpec secretKeySpec, byte[] clearText) throws Exception {
        EncryptionOutput encryptionOutput = new EncryptionOutput();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        AlgorithmParameters params = cipher.getParameters();
        encryptionOutput.ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        encryptionOutput.cipherText = cipher.doFinal(clearText);

        return encryptionOutput;
    }

    public static byte[] decrypt(SecretKeySpec secretKeySpec, byte[] ivBytes, byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
        byte[] clearText = cipher.doFinal(cipherText);
 
        return clearText;

    }

    public static byte[] getSalt() throws Exception {

        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        
        return salt;
    }
    
    public static void showBytes(String label, byte[] argBytes) {
        String b64 = Base64.getEncoder().encodeToString(argBytes);
        System.out.println(label + ":\t" + b64);
    }

    public static void main(String []args) throws Exception {

        String msg = "Exercise Password-based Encryption/Decryption";
        System.out.println(msg);

        int keySize = 256;
        int iterations = 65536;
        char[] password = "This is a huge secret!".toCharArray();
        String originalString = "Mary had a little lamb.";
        System.out.printf("Cleartext (string) [%d bytes]:\t%s\n", originalString.length(), originalString);
        byte[] msgBytes = originalString.getBytes();
        showBytes("Cleartext (base 64)", msgBytes);
   
        // Make the key.
        byte[] salt = getSalt();
        SecretKeySpec secretKeySpec = makeSecretKeySpec(password, salt, keySize, iterations);
        
        // Perform encryption.
        EncryptionOutput eo = encrypt(secretKeySpec, msgBytes);
        showBytes("Ciphertext (base 64)", eo.cipherText);
        
        // Perform decryption.
        byte[] clearText = decrypt(secretKeySpec, eo.ivBytes, eo.cipherText);
        showBytes("Cleartext (base 64)", clearText);
        String decryptedString = new String(clearText, StandardCharsets.UTF_8);
        
        // Show that we got back the original message cleartext.
        System.out.printf("Cleartext (string) [%d bytes]:\t%s\n", decryptedString.length(), decryptedString);
        if(decryptedString.equals(originalString)) {
        	System.out.println("Success!");
        	System.exit(0);
        }
        System.out.println("*** FAILED *** decryptedString != originalString");
        System.exit(1);
    }

}
