import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

public class main {

    private static final String algorithms[] = {
        "MD5", "SHA-1", "SHA-224", 
        "SHA-256", "SHA-384", "SHA-512", 
        "SHA-512/224", "SHA-512/256"};
    
    private static final String ba1ExpByAlgo[] = { 
        "9e107d9d372bb6826bd81d3542a419d6", 
        "2fd4e1c67a2d28fced849ee1bb76e7391b93eb12", 
        "730e109bd7a8a32b1cb9d9a09aa2325d2430587ddbc0c38bad911525", 
        "d7a8fbb307d7809469ca9abcb0082e4f8d5651e46d3cdb762d02d0bf37c9e592", 
        "ca737f1014a48f4c0b6dd43cb177b0afd9e5169367544c494011e3317dbf9a509cb1e5dc1e85a941bbee3d7f2afbc9b1", 
        "07e547d9586f6a73f73fbac0435ed76951218fb7d0c8d788a309d785436bbb642e93a252a954f23912547d1e8a3b5ed6e1bfd7097821233fa0538f3db854fee6", 
        "944cd2847fb54558d4775db0485a50003111c8e5daa63fe722c6aa37", 
        "dd9d67b371519c339ed8dbd25af90e976a1eeefd4ad3d889005e532fc5bef04d"};
    private static final String ba2ExpByAlgo[] = { 
        "5e3da17bf96438a78b07f4eb06b95d0c", 
        "b4a71950a616ff17c235e9731c58d38fc703ff23", 
        "7ccb3f16fb1a4702eab510499d2e84a33704c0af69b1621ac3f8d8e6", 
        "7bb6463b30f9e301fed333cdf8960ca9497b602ccd8eeb46ae42693fdea15a4d", 
        "8a50545d5deed1b56516abcd8bcd3f645b675e502c8a1704e9863f8856117fb3fa98f85a40957730fa16525ccebc10ae", 
        "48de047982747abfd050fde4218cdbd9227e06a53c5f999c65dce9ffcd28c7a6b00fbf181ace8b00b3af5042095cffec30d63c3906728dd714defd6f31f53209", 
        "c4141ec2c7aa91c653c9e73e7b1524dbff1a19513e38c24bb3785975", 
        "30541367fd4c2c8654282936dd7f490b3293ae2324892d51ee9cc96235f9f2e4"};
    private static final String ba3ExpByAlgo[] = { 
        "829e3a5e00797b429b3fe23fdc936a90", 
        "e7cf7736d3186b49b7788f2e1ef9ef031f35e70f", 
        "fca1a00b20f4cf8c1709c20926dc4fcddd6d023abafb56ff0a4431ca", 
        "d3796b05d2224f0c499c39d1fd8d3d9688ce465a880ce4a2b6f2cc07363c854d", 
        "1a5f4a11d02c0cc67339ba45773860b77494c8781c2b7292a9de7ff7e5e9e657da47f53f1f7577f7f6cce0a0f85773ad", 
        "c26c7059b0d4c77711c490fefcb14649bf0352348c27b80e02c88f80cb6c810eeb4b9554a8c9c435b72a5638018d68b432ea4244e6244dfdd8bad12aa1f04bc3", 
        "e5ff25da43e87033c87301400fbe676d3207c41a8ae574ae6af4ed92", 
        "c463018e7ab8e700da16589295d6cef74453d8edbd29a98d7ab72585363bc8e8"};
    
    private static final byte inBytes1[] = "The quick brown fox jumps over the lazy dog".getBytes();
    private static final byte inBytes2[] = { 0x00, 0x01, 0x02, 0x7F, (byte)0x80, (byte)0xFE, (byte)0xFF };
    
    private static int tryIt(int ix, String ba1Expected, String ba2Expected, String ba3Expected) {
    
        int errorCount = 0;
        String observed;
        MessageDigest md;
        String label;
        
        try {
        
            md = MessageDigest.getInstance(algorithms[ix]);
            byte[] ba1Bytes = md.digest(inBytes1);
            label = String.format("ba1Bytes with %s", algorithms[ix]);
            errorCount += Checkers.checker(label, ba1Expected, HexDump.bytesToHex(ba1Bytes));
            
            md = MessageDigest.getInstance(algorithms[ix]);
            byte[] ba2Bytes = md.digest(inBytes2);
            label = String.format("ba2Bytes with %s", algorithms[ix]);
            errorCount += Checkers.checker(label, ba2Expected, HexDump.bytesToHex(ba2Bytes));
            
            md = MessageDigest.getInstance(algorithms[ix]);
            md.update(inBytes1);
            byte[] ba3Bytes = md.digest(inBytes2);
            label = String.format("ba3Bytes with %s", algorithms[ix]);
            errorCount += Checkers.checker(label, ba3Expected, HexDump.bytesToHex(ba3Bytes));
        
        } catch (NoSuchAlgorithmException ex) {
            String errMsg = String.format("*** ERROR, NoSuchAlgorithmException thrown, algo=%s\n", algorithms[ix]);
            throw new AssertionError(errMsg);
        } catch (Exception ex) {
            String errMsg = String.format("*** ERROR, Unexpected Exception thrown, algo=%s\n%s", algorithms[ix], ex.getMessage());
            throw new AssertionError(errMsg);
        }
        
        return errorCount;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        
        for (int ix = 0; ix < algorithms.length; ix++) {
            String ba1Expected = ba1ExpByAlgo[ix];
            String ba2Expected = ba2ExpByAlgo[ix];
            String ba3Expected = ba3ExpByAlgo[ix];
            errorCount += tryIt(ix, ba1Expected, ba2Expected, ba3Expected);
        }
            
        Checkers.theEnd(errorCount);
    }

}

