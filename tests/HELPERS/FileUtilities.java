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
            throw new IOException(String.format("FileUtilities.copyFile: Error in '%s' or '%s': %s", sourcePath, destPath, ex.getMessage()), ex);
       }

    }

    public static byte[] getAllBytes(String pathInputFile) throws IOException {
 		int outPos = 0;
		FileInputStream fis = null;

		try {
        	fis = new FileInputStream(pathInputFile);
		} catch (IOException ex) {
            // Add context with source and destination paths
            throw new IOException(String.format("FileUtilities.getPrintableBytes: Error in new FileInputStream(%s): %s", pathInputFile, ex.getMessage()), ex);
        }
        	
        byte[] raw = fis.readAllBytes();
		return raw;
    }

    public static byte[] getPrintableBytes(String pathInputFile) throws IOException {
 		int outPos = 0;
		FileInputStream fis = null;

		try {
        	fis = new FileInputStream(pathInputFile);
		} catch (IOException ex) {
            // Add context with source and destination paths
            throw new IOException(String.format("FileUtilities.getPrintableBytes: Error in new FileInputStream(%s): %s", pathInputFile, ex.getMessage()), ex);
        }
        	
        byte[] raw = fis.readAllBytes();
	   	byte[] buf = new byte[raw.length];

		for (byte bb : raw) {
			int unsigned = bb & 0xFF;
			if ((unsigned >= 32 && unsigned != 127) || bb == '\n' || bb == '\r' || bb == '\t') {
				if (outPos < raw.length) {
				    buf[outPos++] = bb;
				} else {
				    break;
				}
			}
		}
		
		return java.util.Arrays.copyOf(buf, outPos);
    }

}
