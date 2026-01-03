import java.util.zip.CheckedInputStream;
import java.util.zip.CRC32;
import java.io.FileInputStream;

public class main {

    private static int errorCount = 0;

    public static void main(String[] args) {
        System.out.println("Use class CheckedInputStream to compute the checksum of a file");
        String fpath = "./data.txt";
        long cksum = getCheckSum(fpath);
        errorCount += Checkers.checker("checksum", 1469659264, cksum);
        Checkers.theEnd(errorCount);
    }
    
    private static long getCheckSum(String fpath) {
    
        CRC32 checksum = new CRC32();
        int bytesRead;
        
        try  {
            CheckedInputStream cis = new CheckedInputStream(new FileInputStream(fpath), checksum);
            byte[] buffer = new byte[32768];
            while ((bytesRead = cis.read(buffer)) != -1) {
            }
            return cis.getChecksum().getValue();
        } catch (Exception ex) {
            System.out.printf("CheckedInputStream(%s) error, errMsg:\n%s", fpath, ex.getMessage());
        }
        return 1;       
    }
}

