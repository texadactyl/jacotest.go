import java.util.HexFormat;
public class main {

    public static int isItTrue(String label, boolean bool, String observed) {
        if (bool) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR, test: %s, observed value: %s\n", label, observed);
        return 1;
    }

    public static void main(String[] args) throws Exception {
        int errorCount = 0;
		HexFormat hex = HexFormat.ofDelimiter(":");
		System.out.println("HexFormat.ofDelimiter() instantiated ok");

        int ii = 65537;
        short ss = (short) ii;
        errorCount += isItTrue("ii = 65537 and (ss <-- ii) == 1", ss == 1, String.valueOf(ss));
		char cc = (char) ii;
		String st = hex.toHexDigits(cc);
		errorCount += isItTrue("ii = 65537 and (cc <-- ii) == 0x01", cc == 0x01, st);
		byte bb = (byte) ii;
		st = hex.toHexDigits(bb);
		errorCount += isItTrue("ii = 65537 and (bb <-- ii) == 0x01", bb == 0x01, st);

        // Check the error count
        assert (errorCount == 0);
    }
}
