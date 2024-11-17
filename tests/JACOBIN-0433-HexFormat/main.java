import java.util.HexFormat;
public class main {

    private static int cmpExpToObs(String label, String expected, String observed) {
        if (observed.equals(expected)) {
            System.out.printf("Success :: %s :: expected=\"%s\", observed=\"%s\"\n", label, expected, observed);
            return 0;
        }
        System.out.printf("*** ERROR :: %s :: expected=\"%s\", observed=\"%s\"\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) throws Exception {
    int errorCount = 0;
    String st;
    
	HexFormat hex = HexFormat.ofDelimiter(":");
	System.out.println("HexFormat.ofDelimiter(:) instantiated ok, here are some fields:");
	System.out.println(hex.toString());
	
	String delimiter = hex.delimiter();
	System.out.printf("Delimiter: \"%s\"\n", delimiter);

	byte bb = 0x7f;
	st = hex.toHexDigits(bb);
	errorCount += cmpExpToObs("hex.toHexDigits(0x7f byte)", "7f", st);

	char cc = 'Z';
	st = hex.toHexDigits(cc);
	errorCount += cmpExpToObs("hex.toHexDigits(\'Z\' char)", "005a", st);
	
	int ii = 1048576;
	st = hex.toHexDigits(ii);
	errorCount += cmpExpToObs("hex.toHexDigits(1048576 int)", "00100000", st);
	
	long jj = 21384572138457l;
	st = hex.toHexDigits(jj);
	errorCount += cmpExpToObs("hex.toHexDigits(21384572138457 long)", "00001372fbd373d9", st);
	st = hex.toHexDigits(jj, 4);
	errorCount += cmpExpToObs("hex.toHexDigits(21384572138457 long, outlen=4)", "73d9", st);
	
    short ss = 32767;
    st = hex.toHexDigits(ss);
    errorCount += cmpExpToObs("hex.toHexDigits(32767 short)", "7fff", st);
    
    // Check the error count
    assert (errorCount == 0);
    }
}
