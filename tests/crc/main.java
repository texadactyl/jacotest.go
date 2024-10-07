import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.CRC32C; // CRC32 using an alternate polynomial called the Castagnoli polynomial

public class main {

    private static int cmpExpToObs(String label, String expected, String observed) {
        if (observed.equals(expected)) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=%s, observed=%s\n", label, expected, observed);
        return 1;
    }

    private static int cmpExpToObs(String label, long expected, long observed) {
        if (observed == expected) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=%d, observed=%d\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
    
        int errorCount = 0;
        int i32767 = 32767;
        String shex;
    
        String str = "Mary had a little lamb whose fleece was white as snow.";
        System.out.println(str);
        byte[] bb = str.getBytes();
        
        Adler32 ad32 = new Adler32();
        long crc_ad32 = ad32.getValue();
        errorCount += cmpExpToObs("Adler32 begin", 1L, crc_ad32); // Adler32 initialises to 1 !!
        ad32.update(bb, 0, bb.length);
        ad32.update(i32767);
        crc_ad32 = ad32.getValue();
        shex = String.format("%08x", crc_ad32);
        errorCount += cmpExpToObs("Adler32 full", "24bf1450", shex);
        ad32.reset();
        crc_ad32 = ad32.getValue();
        errorCount += cmpExpToObs("Adler32 reset", 1L, crc_ad32); // Adler32 resets to 1 !!
        
        
        CRC32 crc32 = new CRC32();
        long crc_crc32 = crc32.getValue();
        errorCount += cmpExpToObs("CRC32 begin", 0L, crc_crc32);
        crc32.update(bb, 0, bb.length);
        crc32.update(i32767);
        crc_crc32 = crc32.getValue();
        shex = String.format("%08x", crc_crc32);
        errorCount += cmpExpToObs("CRC32 full", "9354f2fb", shex);
        crc32.reset();
        crc_crc32 = crc32.getValue();
        errorCount += cmpExpToObs("CRC32 reset", 0L, crc_crc32);
        
        CRC32C crc32c = new CRC32C();
        long crc_crc32c = crc32c.getValue();
        errorCount += cmpExpToObs("CRC32-C begin", 0L, crc_crc32c);
        crc32c.update(bb, 0, bb.length);
        crc32c.update(i32767);
        crc_crc32c = crc32c.getValue();
        shex = String.format("%08x", crc_crc32c);
        errorCount += cmpExpToObs("CRC32-C full", "f99a974f", shex);
        crc32c.reset();
        crc_crc32c = crc32c.getValue();
        errorCount += cmpExpToObs("CRC32-C reset", 0L, crc_crc32c);
        
        assert errorCount == 0;
        
    }
}

