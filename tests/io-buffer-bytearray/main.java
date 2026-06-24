import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class main {

    private static String FILE_PATH = "file.txt";

    public static void main(String[] args) {
        System.out.println("=== Starting Buffered I/O Stream Tests ===");

        try {
            testBufferedOutputStreamAndInputStream();
            testMarkAndReset();
            testExceptionHandlingAndClose();          
            Checkers.theEnd(0);
        } catch (Throwable t) {
            System.out.println("*** ERROR, Test execution failed with an unexpected error.");
            t.printStackTrace();
        }
        
    }

    /**
     * Tests basic writing, flushing, and reading capabilities using 
     * BufferedOutputStream and BufferedInputStream.
     */
    private static void testBufferedOutputStreamAndInputStream() throws IOException {
        System.out.println("[Test 1] Basic Read/Write and Buffering");

        byte[] testData = new byte[] { 65, 66, 67, 68, 69, 70 }; // "ABCDEF"
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // Initialize BufferedOutputStream with a small buffer size (3 bytes)
        BufferedOutputStream bos = new BufferedOutputStream(baos, 3);

        // Write first 2 bytes - should remain in the buffer (not yet in baos)
        bos.write(testData, 0, 2);
        if (baos.size() != 0) {
            throw new RuntimeException("Data leaked to underlying stream before buffer filled or flushed.");
        }

        // Write 2 more bytes - forces internal buffer to exceed 3 bytes, causing an implicit flush
        bos.write(testData, 2, 2);
        if (baos.size() == 0) {
            throw new RuntimeException("Underlying stream is empty; implicit flush failed to trigger.");
        }

        // Write remainder and force manual flush
        bos.write(testData, 4, 2);
        bos.flush();

        byte[] resultData = baos.toByteArray();
        if (resultData.length != 6) {
            throw new RuntimeException("Flushed data size does not match total written data.");
        }

        // Read back the data using BufferedInputStream
        ByteArrayInputStream bais = new ByteArrayInputStream(resultData);
        BufferedInputStream bis = new BufferedInputStream(bais, 3);

        // Test single byte read
        int firstByte = bis.read();
        if (firstByte != 65) {
            throw new RuntimeException("Read mismatch on first byte.");
        }

        // Test bulk array read
        byte[] readBuffer = new byte[5];
        int bytesRead = bis.read(readBuffer, 0, 5);
        if (bytesRead != 5) {
            throw new RuntimeException("Failed to read expected bulk byte count.");
        }

        if (readBuffer[0] != 66 || readBuffer[4] != 70) {
            throw new RuntimeException("Bulk data contents do not match expected sequence.");
        }

        bos.close();
        bis.close();
        System.out.println(" -> Pass: Basic Read/Write and buffer sizing verified.");
    }

    /**
     * Tests the stream marking and rewriting mechanism (mark, markSupported, reset).
     */
    private static void testMarkAndReset() throws IOException {
        System.out.println("[Test 2] Mark and Reset Operations");

        byte[] source = new byte[] { 10, 20, 30, 40, 50 };
        ByteArrayInputStream bais = new ByteArrayInputStream(source);
        BufferedInputStream bis = new BufferedInputStream(bais);

        if (!bis.markSupported()) {
            throw new RuntimeException("BufferedInputStream must support mark/reset operations.");
        }

        // Read elements: 10, 20
        int r1 = bis.read();
        int r2 = bis.read();
        if (r1 != 10 || r2 != 20) {
            throw new RuntimeException("Prefetch read mismatch.");
        }

        // Mark current position (holding '30') with a lookahead limit of 2 bytes
        bis.mark(2);

        int r3 = bis.read(); // Should be 30
        int r4 = bis.read(); // Should be 40
        if (r3 != 30 || r4 != 40) {
            throw new RuntimeException("Post-mark read mismatch.");
        }

        // Reset to mark position
        bis.reset();

        // Read again - should pull 30 and 40 again
        int r3Retry = bis.read();
        int r4Retry = bis.read();
        if (r3Retry != 30 || r4Retry != 40) {
            throw new RuntimeException("Reset failed to restore stream state correctly.");
        }

        bis.close();
        System.out.println(" -> Pass: Mark and reset capabilities successfully rolled back the stream context.");
    }

    /**
     * Validates that stream methods correctly reject operations and throw IOException 
     * once the wrapper stream has been formally closed.
     */
    private static void testExceptionHandlingAndClose() throws IOException {
        System.out.println("[Test 3] Post-Close Stream Restrictions");

        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] { 1, 2, 3 });
        BufferedInputStream bis = new BufferedInputStream(bais);
        bis.close();

        // 1. Ensure reading from a closed BufferedInputStream throws IOException
        try {
            bis.read();
            throw new RuntimeException("Failed to raise IOException when reading a closed BufferedInputStream.");
        } catch (IOException expected) {
            // Success: Exception caught safely
        }

        // 2. Create a FileOutputStream.
        FileOutputStream strictStream = new FileOutputStream(FILE_PATH);

        // 3. Create a BufferedOutputStream based on the FileOutputStream.
        BufferedOutputStream bos = new BufferedOutputStream(strictStream);
        bos.close();

        // Ensure writing to a closed BufferedOutputStream eventually throws IOException
        try {
            // Write and flush.
            for (int i = 0; i < 3; i++) {
                bos.write(100);
            }
            bos.flush();
            throw new AssertionError("Failed to raise IOException when writing or flushing to a closed BufferedOutputStream.");
        } catch (IOException expected) {
            System.out.println("Success: IOException caught safely");
        }

        System.out.println(" -> Pass: Stream closure behaviors match strict standards.");
    }

}
