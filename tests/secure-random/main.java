import java.security.SecureRandom;
import java.util.HexFormat;

class main {

    private static void GimmeFive(String label, SecureRandom sr) {
        byte [] bb = new byte[8];
        sr.nextBytes(bb);
        System.out.printf("<%s>  int=%d, long=%d, float=%f, double=%f, bytes=%s\n", label, sr.nextInt(), sr.nextLong(), sr.nextFloat(), sr.nextDouble(), HexFormat.of().formatHex(bb));
        
        /*for(int ix = 0; ix < 5; ix++) {
            sr.nextBytes(bb);
            System.out.printf("<%s  %d>  int=%d, long=%d, float=%f, double=%f, bytes=%s\n", label, ix, sr.nextInt(), sr.nextLong(), sr.nextFloat(), sr.nextDouble(), HexFormat.of().formatHex(bb));
        }*/
    }

    public static void main(String[] args) {
        SecureRandom sr1 = new SecureRandom();
        GimmeFive("sr1 = new SecureRandom()", sr1);
        
        byte[] bseed8 = SecureRandom.getSeed(8);
        System.out.printf("bseed8: %s\n", HexFormat.of().formatHex(bseed8));
        
        sr1.setSeed(12345L); // Seed with a long value.
        GimmeFive("sr1.setSeed(12345L)", sr1);
        
        byte[] seedBytes = {10, 20, 30, 40};
        sr1.setSeed(seedBytes); // Seed with a byte array.
        GimmeFive("sr1.setSeed(seedBytes)", sr1);
        
        System.out.printf("Algorithm: %s\n", sr1.getAlgorithm());
        System.out.printf("toString: %s\n", sr1.toString());
        
        SecureRandom sr2 = new SecureRandom(seedBytes);
        GimmeFive("sr2 = new SecureRandom(seedBytes)", sr1);
        
        bseed8 = sr2.generateSeed(8);
        GimmeFive("sr2.generateSeed(8)", sr2);
        
        System.out.println("Success!");
    }
}

