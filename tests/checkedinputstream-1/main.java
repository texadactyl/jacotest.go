import java.util.zip.CheckedInputStream;
import java.util.zip.CRC32;
import java.io.FileInputStream;

public class main {

    private static int errorCount = 0;

    public static void main(String[] args) {
        System.out.println("Use class CheckedInputStream to compute the checksum of a file");
        String fpath = "./input.datatxt";
        long observedCksum = getChecksum(fpath);
        if (observedCksum < 0L)
            Checkers.theEnd(1);
        long expectedCksum;
        expectedCksum = 3313628986L;
        errorCount += Checkers.checker("Checksum", expectedCksum, observedCksum);
        Checkers.theEnd(errorCount);
    }
    
    private static long getChecksum(String fpath) {
    
        CRC32 checksum = new CRC32();
        int bytesRead;
        
        try  {
            CheckedInputStream cis = new CheckedInputStream(new FileInputStream(fpath), checksum);
            byte[] buffer = new byte[32768];
            while ((bytesRead = cis.read(buffer)) != -1) {
            }
            return cis.getChecksum().getValue();
        } catch (Throwable ex) {
            System.out.printf("getChecksum(%s) throwable, errMsg:\n%s", fpath, ex.getMessage());
        }
        return -1L;       
    }
}

