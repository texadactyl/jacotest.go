import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class main extends OutputStream {

    static final String pathInputFile = "main.java";
    static final int maxWrite = 10;

    private final FileOutputStream fos;

    main(File f) throws IOException {
        fos = new FileOutputStream(f);
    }

    @Override
    public void write(int b) throws IOException {      // the abstract one
        fos.write(b);
    }

    @Override
    public void write(byte[] bites) throws IOException {
        fos.write(bites);
    }

    @Override
    public void write(byte[] bites, int offset, int length) throws IOException {
        fos.write(bites, offset, length);
    }

    @Override
    public void flush() throws IOException {
        fos.flush();
    }

    @Override
    public void close() throws IOException {
        fos.close();
    }

    public static void main(String[] args) throws IOException {
        byte[] buffer = new byte[256];
        int errorCount = 0;

        File tfHandle = File.createTempFile("jacotest", null);

        byte[] finalBites = "That's all, folks".getBytes();

        try (FileInputStream fis = new FileInputStream(pathInputFile);
             main out = new main(tfHandle)) {

            int nbytes;
            while ((nbytes = fis.read(buffer, 0, maxWrite)) != -1) {
                out.write(buffer, 0, nbytes);          // write(byte[],int,int)
            }

            out.write(finalBites);                     // write(byte[])
            out.write('!');                            // write(int)
        }

        // Expected = original input + the final string + the single byte (!).
        byte[] input = Files.readAllBytes(Path.of(pathInputFile));
        byte[] expected = new byte[input.length + finalBites.length + 1];
        System.arraycopy(input, 0, expected, 0, input.length);
        System.arraycopy(finalBites, 0, expected, input.length, finalBites.length);
        expected[expected.length - 1] = '!';

        // actual = all the bytes that were written to the temp file.
        byte[] actual = Files.readAllBytes(tfHandle.toPath());

        // Compare expected byte array to actual byte array.
        errorCount += Checkers.checker("two byte arrays", expected, actual);
        
        // The End.
        tfHandle.delete();
        Checkers.theEnd(errorCount);
    }
    
}

