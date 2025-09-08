import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtilities {

    public static void copyFile(String sourcePath, String destPath) throws IOException {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(sourcePath);
            fos = new FileOutputStream(destPath);

            byte[] buffer = new byte[4096]; // 4 KB buffer
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();

       } catch (IOException ex) {
            // Add context with source and destination paths
            throw new IOException(
                String.format("Error copying file from '%s' to '%s': %s", sourcePath, destPath, ex.getMessage()),
                ex
            );
        }

    }

}
