/*
    Read and encrypt file A, storing ciphertext in file B.
    Then, read and decrypt file B, storing cleartext in file C.
    At the end, files A and C are equal.

    Constant memory usage governed by CHUNK_SIZE
    Handles arbitrarily large files
    Correctly handles short final chunks
    Uses authenticated AES-GCM streaming
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class main {

    private static final boolean debugging = false;

    private static final String PASSWORD =
            "Mary had a little lamb";

    private static final byte[] SALT = {
            0x46, 0x69, 0x78, 0x65,
            0x64, 0x53, 0x61, 0x6c,
            0x74, 0x31, 0x32, 0x33,
            0x34, 0x35
    };

    private static final String ALGO_CIPHER = "AES/GCM/NoPadding";
    private static final String ALGO_KEY = "AES";
    private static final String ALGO_SECKEY_FACTORY = "PBKDF2WithHmacSHA256";
    private static final int CHUNK_LENGTH = 4096;
    private static final int IV_LENGTH = 12;
    private static final int KEY_LENGTH = 256;
    private static final int PBE_KEY_SPEC_ITERATIONS = 65536;
    private static final int TAG_LENGTH = 128;

    private static int errorCount = 0;

    /**
     * TIFF Big-Endian prefix used to disguise the output file.
     */
    private static final byte[] TIFF_PREFIX = {
            (byte) 0x4d, (byte) 0x4d, (byte) 0x00, (byte) 0x2a,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08,
            (byte) 0x00, (byte) 0x08,

            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x04,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20,

            (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x04,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,

            (byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x03,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x08, (byte) 0xff, (byte) 0xff,

            (byte) 0x01, (byte) 0x03, (byte) 0x00, (byte) 0x03,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0xff,

            (byte) 0x01, (byte) 0x06, (byte) 0x00, (byte) 0x03,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0xff,

            (byte) 0x01, (byte) 0x11, (byte) 0x00, (byte) 0x03,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x6e, (byte) 0xff, (byte) 0xff,

            (byte) 0x01, (byte) 0x16, (byte) 0x00, (byte) 0x03,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0xff,

            (byte) 0x01, (byte) 0x17, (byte) 0x00, (byte) 0x03,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x20, (byte) 0xff, (byte) 0xff,

            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x4d, (byte) 0x4d
    };

    public static void main(String[] args)
            throws Exception {

        String fileA = "cleartext.1";
        String fileB = "ciphertext.x";
        String fileC = "cleartext.2";

        encryptFile(fileA, fileB);

        decryptFile(fileB, fileC);

        Checkers.theEnd(errorCount);
    }

    /**
     * Phase 1:
     * Encrypt cleartext.1 -> ciphertext.x
     */
    private static void encryptFile(String inputFile, String outputFile)
            throws Exception {

        CRC32 crc = new CRC32();
        SecureRandom random = new SecureRandom();

        File input = new File(inputFile);
        long clearLength = input.length();

        byte[] iv = new byte[IV_LENGTH];
        random.nextBytes(iv);

        byte[] sizeBytes = longToBytes(clearLength);

        SecretKey key = deriveKey(PASSWORD);

        Cipher cipher = Cipher.getInstance(ALGO_CIPHER);
        GCMParameterSpec gcmSpec =
                new GCMParameterSpec(TAG_LENGTH, iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);

        fos.write(TIFF_PREFIX);
        fos.write(iv);
        fos.write(sizeBytes);

        crc.update(TIFF_PREFIX);
        crc.update(iv);
        crc.update(sizeBytes);

        byte[] inBuf = new byte[CHUNK_LENGTH];

        while (true) {

            int bytesRead = fis.read(inBuf);

            if (bytesRead < 0) {
                break;
            }

            crc.update(inBuf, 0, bytesRead);

            byte[] encryptedChunk =
                    cipher.update(inBuf, 0, bytesRead);

            if (encryptedChunk != null &&
                    encryptedChunk.length > 0) {

                fos.write(encryptedChunk);
            }
        }

        byte[] finalBytes = cipher.doFinal();

        if (finalBytes != null && finalBytes.length > 0) {
            fos.write(finalBytes);
        }

        long crcValue = crc.getValue();
        byte[] crcBytes = intToBytes((int) crcValue);

        fos.write(crcBytes);

        fis.close();
        fos.close();
    }

    /**
     * Phase 2:
     * Decrypt ciphertext.x -> cleartext.2
     */
    private static void decryptFile(String inputFile, String outputFile)
            throws Exception {

        File input = new File(inputFile);
        long totalLength = input.length();
        long encryptedLength =
                totalLength -
                TIFF_PREFIX.length -
                IV_LENGTH -
                8 -
                4;

        FileInputStream fis = new FileInputStream(inputFile);

        byte[] prefix = new byte[TIFF_PREFIX.length];
        readFully(fis, prefix);

        byte[] iv = new byte[IV_LENGTH];
        readFully(fis, iv);

        byte[] sizeBytes = new byte[8];
        readFully(fis, sizeBytes);

        SecretKey key = deriveKey(PASSWORD);

        Cipher cipher = Cipher.getInstance(ALGO_CIPHER);

        GCMParameterSpec gcmSpec =
                new GCMParameterSpec(TAG_LENGTH, iv);

        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

        FileOutputStream fos =
                new FileOutputStream(outputFile);

        CRC32 crc = new CRC32();

        crc.update(prefix);
        crc.update(iv);
        crc.update(sizeBytes);

        byte[] inBuf = new byte[CHUNK_LENGTH];

        long remaining = encryptedLength;
        
        long clearByteCount = 0;

        while (remaining > 0) {

            int wanted = (int) Math.min(CHUNK_LENGTH, remaining);
            int bytesRead = fis.read(inBuf, 0, wanted);
            if (bytesRead < 0) {
                throw new IOException(
                        "Unexpected EOF in encrypted data");
            }

            remaining -= bytesRead;

            byte[] clearChunk = cipher.update(inBuf, 0, bytesRead);

            if (clearChunk != null && clearChunk.length > 0) {
                crc.update(clearChunk);
                fos.write(clearChunk);
                clearByteCount += clearChunk.length;
            }
        }

        byte[] finalClear = cipher.doFinal();
        if (finalClear != null && finalClear.length > 0) {
            crc.update(finalClear);
            fos.write(finalClear);
            clearByteCount += finalClear.length;
        }

        byte[] crcBytes = new byte[4];
        readFully(fis, crcBytes);
        long storedCRC = bytesToUnsignedInt(crcBytes);
        long computedCRC = crc.getValue();
        errorCount += Checkers.checker("data crc32", computedCRC, storedCRC);

        long fileByteCount = bytesToLong(sizeBytes);
        errorCount += Checkers.checker("data length", clearByteCount, fileByteCount);

        fis.close();
        fos.close();
    }

    /**
     * Read exactly buffer.length bytes.
     */
    private static void readFully(
            FileInputStream fis,
            byte[] buffer)
            throws IOException {

        int offset = 0;

        while (offset < buffer.length) {

            int count =
                    fis.read(
                            buffer,
                            offset,
                            buffer.length - offset);

            if (count < 0) {

                throw new IOException(
                        "Unexpected EOF");
            }

            offset += count;
        }
    }

    /**
     * Derive AES-256 key from password.
     */
    private static SecretKey deriveKey(String password) throws Exception {

        PBEKeySpec spec = new PBEKeySpec(
                        password.toCharArray(),
                        SALT,
                        PBE_KEY_SPEC_ITERATIONS,
                        KEY_LENGTH);

        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGO_SECKEY_FACTORY);

        byte[] keyBytes = factory
                        .generateSecret(spec)
                        .getEncoded();

        return new SecretKeySpec(keyBytes, ALGO_KEY);
    }

    /**
     * Convert long -> 8-byte big-endian array.
     */
    private static byte[] longToBytes(long value) {

        byte[] bytes = new byte[8];

        bytes[0] = (byte) (value >>> 56);
        bytes[1] = (byte) (value >>> 48);
        bytes[2] = (byte) (value >>> 40);
        bytes[3] = (byte) (value >>> 32);
        bytes[4] = (byte) (value >>> 24);
        bytes[5] = (byte) (value >>> 16);
        bytes[6] = (byte) (value >>> 8);
        bytes[7] = (byte) value;

        return bytes;
    }

    /**
     * Convert 8-byte big-endian array -> long.
     */
    private static long bytesToLong(byte[] bytes) {

        return
                (((long) bytes[0] & 0xffL) << 56) |
                        (((long) bytes[1] & 0xffL) << 48) |
                        (((long) bytes[2] & 0xffL) << 40) |
                        (((long) bytes[3] & 0xffL) << 32) |
                        (((long) bytes[4] & 0xffL) << 24) |
                        (((long) bytes[5] & 0xffL) << 16) |
                        (((long) bytes[6] & 0xffL) << 8) |
                        (((long) bytes[7] & 0xffL));
    }

    /**
     * Convert int -> 4-byte big-endian array.
     */
    private static byte[] intToBytes(int value) {

        byte[] bytes = new byte[4];

        bytes[0] = (byte) (value >>> 24);
        bytes[1] = (byte) (value >>> 16);
        bytes[2] = (byte) (value >>> 8);
        bytes[3] = (byte) value;

        return bytes;
    }

    /**
     * Convert unsigned 4-byte big-endian array -> long.
     */
    private static long bytesToUnsignedInt(byte[] bytes) {

        return
                (((long) bytes[0] & 0xffL) << 24) |
                        (((long) bytes[1] & 0xffL) << 16) |
                        (((long) bytes[2] & 0xffL) << 8) |
                        (((long) bytes[3] & 0xffL));
    }
}
