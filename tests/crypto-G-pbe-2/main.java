import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;

/**
 * PBECipherTest
 * Updated to use printf/format exclusively for string construction to avoid 
 * INVOKEDYNAMIC concatenation.
 */
public class main {

    static final String[] PBKDF2_ALGO = {
        "PBEWithHMACSHA1AndAES_128", "PBEWithHMACSHA1AndAES_256",
        "PBEWithHMACSHA224AndAES_128", "PBEWithHMACSHA224AndAES_256",
        "PBEWithHMACSHA256AndAES_128", "PBEWithHMACSHA256AndAES_256",
        "PBEWithHMACSHA384AndAES_128", "PBEWithHMACSHA384AndAES_256",
        "PBEWithHMACSHA512AndAES_128", "PBEWithHMACSHA512AndAES_256",
        "PBEWithHMACSHA512/224AndAES_128", "PBEWithHMACSHA512/224AndAES_256",
        "PBEWithHMACSHA512/256AndAES_128", "PBEWithHMACSHA512/256AndAES_256",
    };

    static final int[] PBKDF2_ITERATIONS = {
         65_536, 65_536, 65_536, 65_536, 100_000, 100_000, 100_000,
        100_000, 100_000, 100_000, 100_000, 100_000, 100_000, 100_000,
    };

    static final String[] LEGACY_ALGO = {
        "PBEWithMD5AndDES", "PBEWithMD5AndTripleDES", "PBEWithSHA1AndDESede",
        "PBEWithSHA1AndRC2_128", "PBEWithSHA1AndRC2_40", 
        "PBEWithSHA1AndRC4_128", "PBEWithSHA1AndRC4_40",
    };

    static final int[] LEGACY_SALT_BYTES = { 8, 8, 8, 8, 8, 8, 8 };
    static final int LEGACY_ITERATIONS = 1_024;

    static final CipherEntry[] CIPHER_TABLE = buildCipherTable();

    static CipherEntry[] buildCipherTable() {
        CipherEntry[] table = new CipherEntry[LEGACY_ALGO.length + PBKDF2_ALGO.length];
        int idx = 0;
        for (int i = 0; i < LEGACY_ALGO.length; i++) {
            table[idx++] = new CipherEntry(LEGACY_ALGO[i], false, LEGACY_SALT_BYTES[i], 0, LEGACY_ITERATIONS);
        }
        for (int i = 0; i < PBKDF2_ALGO.length; i++) {
            table[idx++] = new CipherEntry(PBKDF2_ALGO[i], true, 16, 16, PBKDF2_ITERATIONS[i]);
        }
        return table;
    }

    static final String CLEAR_TEXT = "The quick brown fox jumps over the lazy dog.";
    static final String PASSWORD   = "S3cur3P@ssw0rd!";
    static final int    COL_ALGO   = 38;
    static final int    COL_SALT   = 6;
    static final int    COL_ITER   = 8;
    static final int    COL_IV     = 4;
    static final int    COL_RESULT = 6;

    public static void main(String[] args) throws Exception {
        boolean ok = false;
        int errorCount = 0;
        String result;
        String detail;
        
        SecureRandom rng = new SecureRandom();
        printHeader();

        int pass = 0, skip = 0;

        for (CipherEntry entry : CIPHER_TABLE) {
             try {
                ok = doSomeCiphering(entry, rng);
            } catch (Exception ex) {
                debugRow(entry);
                ex.printStackTrace();
                ok = false;
                errorCount += 1;
                continue;
            }
            if (ok) { 
                result = "PASS"; 
                pass++; 
                detail = "";
            } else { 
                result = "FAIL"; 
                errorCount += 1;
                detail = "plaintext mismatch"; 
            }
            printRow(entry, result, detail);
        }

        printFooter(pass, errorCount, skip);
        //Checkers.theEnd(errorCount);
        System.exit(errorCount);
    }

    static boolean doSomeCiphering(CipherEntry entry, SecureRandom rng) throws Exception {
    
        byte[] plaintext = {};
    
        try {
            byte[] salt = new byte[entry.saltBytes];
            rng.nextBytes(salt);

            PBEKeySpec pbeKeySpec = new PBEKeySpec(PASSWORD.toCharArray());
            SecretKeyFactory skf = SecretKeyFactory.getInstance(entry.algorithm);
            SecretKey pbeKey = skf.generateSecret(pbeKeySpec);

            Cipher cipher = Cipher.getInstance(entry.algorithm);
            AlgorithmParameterSpec paramSpec = buildParamSpec(entry, salt, rng);

            cipher.init(Cipher.ENCRYPT_MODE, pbeKey, paramSpec);
            byte[] ciphertext = cipher.doFinal(CLEAR_TEXT.getBytes());

            AlgorithmParameters actualParams = cipher.getParameters();
            cipher.init(Cipher.DECRYPT_MODE, pbeKey, actualParams);
            plaintext = cipher.doFinal(ciphertext);

        } catch (Exception ex) {
            debugRow(entry);
            ex.printStackTrace();
            System.exit(1); 
        }

        return CLEAR_TEXT.equals(new String(plaintext));
    }

    static AlgorithmParameterSpec buildParamSpec(CipherEntry entry, byte[] salt, SecureRandom rng) {
        if (!entry.isPbkdf2) {
            return new PBEParameterSpec(salt, entry.iterations);
        }
        byte[] iv = new byte[entry.ivBytes];
        rng.nextBytes(iv);
        return new PBEParameterSpec(salt, entry.iterations, new IvParameterSpec(iv));
    }

    static void printHeader() {
        System.out.println();
        System.out.printf("PBE Cipher Round-Trip Test  (JDK %s)%n", System.getProperty("java.version"));
        System.out.printf("Clear-text : \"%s\"%n", CLEAR_TEXT);
        System.out.printf("Password   : \"%s\"%n", PASSWORD);
        System.out.println();
        
        String headerFormat = String.format("%%-%ds  %%%ds  %%%ds  %%%ds  %%-%ds  %%s%%n", 
                                            COL_ALGO, COL_SALT, COL_ITER, COL_IV, COL_RESULT);
        System.out.printf(headerFormat, "Algorithm", "Salt", "Iters", "IV", "Result", "Note");
        System.out.println("-".repeat(100));
    }

    static void debugRow(CipherEntry entry) {
        System.out.printf("***** DEBUG algo:  %s,  salt: %d,   iv: %d\n",
                          entry.algorithm,
                          entry.saltBytes,
                          entry.ivBytes);
    }

    static void printRow(CipherEntry entry, String result, String detail) {
        String rowFormat = String.format("%%-%ds  %%%dd  %%%dd  %%%dd  %%-%ds  %%s%%n", 
                                         COL_ALGO, COL_SALT, COL_ITER, COL_IV, COL_RESULT);
        System.out.printf(rowFormat,
                          entry.algorithm,
                          entry.saltBytes,
                          entry.iterations,
                          entry.ivBytes,
                          result,
                          detail);
    }

    static void printFooter(int pass, int fail, int skip) {
        System.out.println("-".repeat(100));
        System.out.printf("Results:  %d PASS  |  %d FAIL  |  %d SKIP%n", pass, fail, skip);
        printIterationTable();
        printSaltTable();
    }

    static void printIterationTable() {
        System.out.println("\nPBKDF2 Iteration-Count Table (recommended minimums)");
        System.out.println("-".repeat(55));
        System.out.printf("%-38s  %s%n", "Algorithm", "Iterations");
        System.out.println("-".repeat(55));
        for (int i = 0; i < PBKDF2_ALGO.length; i++) {
            System.out.printf("%-38s  %d%n", PBKDF2_ALGO[i], PBKDF2_ITERATIONS[i]);
        }
        System.out.println("-".repeat(55));
    }

    static void printSaltTable() {
        System.out.println("\nSalt-Length Table (bytes)");
        System.out.println("-".repeat(50));
        System.out.printf("%-38s  %s%n", "Algorithm", "Salt (bytes)");
        System.out.println("-".repeat(50));
        for (int i = 0; i < LEGACY_ALGO.length; i++) {
            System.out.printf("%-38s  %d%n", LEGACY_ALGO[i], LEGACY_SALT_BYTES[i]);
        }
        for (int i = 0; i < PBKDF2_ALGO.length; i++) {
            System.out.printf("%-38s  %d%n", PBKDF2_ALGO[i], 16);
        }
        System.out.println("-".repeat(50));
    }
}

class CipherEntry {
    String algorithm;
    boolean isPbkdf2;
    int saltBytes;
    int ivBytes;
    int iterations;
    
    CipherEntry(String algorithm, boolean isPbkdf2, int saltBytes, int ivBytes, int iterations) {
        this.algorithm = algorithm;
        this.isPbkdf2 = isPbkdf2;
        this.saltBytes = saltBytes;
        this.ivBytes = ivBytes;
        this.iterations = iterations;
    }
}


