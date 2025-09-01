import java.util.HexFormat;

public class main {

    private static int throwFromFormatHex(byte[] bytes, int fromIndex, int toIndex) {
        String label = String.format("(bytes, %d, %d)", fromIndex, toIndex);
        HexFormat hex = HexFormat.ofDelimiter(":");
        try {
            String dummy = hex.formatHex(bytes, fromIndex, toIndex);
            System.out.printf("*** ERROR, HexFormat.formatHex(%s) failed to throw an IndexOutOfBoundsException\n", label);
            return 1;
        }catch(IndexOutOfBoundsException ex) {
            System.out.printf("Success :: HexFormat.formatHex((%s) succeeding in throwing an IndexOutOfBoundsException\n", label);
        } catch(Exception ex) {
            System.out.printf("*** ERROR, HexFormat.formatHex((%s) threw an unexpected Exception\n", label);
            return 1;
        }
        return 0;
    }

    private static int throwFromHexDigit(char arg) {
        char[] ch = { arg };
        String label = new String(ch);
        try {
            int dummy = HexFormat.fromHexDigit(arg);
            System.out.printf("*** ERROR, HexFormat.fromHexDigit(%s) failed to throw a NumberFormatException\n", label);
            return 1;
        }catch(NumberFormatException ex) {
            System.out.printf("Success :: HexFormat.fromHexDigit((%s) succeeding in throwing a NumberFormatException\n", label);
        } catch(Exception ex) {
            System.out.printf("*** ERROR, HexFormat.fromHexDigit((%s) threw an unexpected Exception\n", label);
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int errorCount = 0;
        String st;
        
	    HexFormat hex = HexFormat.ofDelimiter(":");
	    System.out.println("HexFormat.ofDelimiter(:) instantiated ok");
	    System.out.printf("hex fields starting with ofDelimiter(): %s\n", hex.toString());
	    
	    String delimiter = hex.delimiter();
	    System.out.printf("Delimiter: \"%s\"\n", delimiter);

	    byte bb = 0x7f;
	    st = hex.toHexDigits(bb);
	    errorCount += Checkers.checker("hex.toHexDigits(0x7f byte)", "7f", st);

	    char cc = 'Z';
	    st = hex.toHexDigits(cc);
	    errorCount += Checkers.checker("hex.toHexDigits(\'Z\' char)", "005a", st);
	    
	    int ii = 1048576;
	    st = hex.toHexDigits(ii);
	    errorCount += Checkers.checker("hex.toHexDigits(1048576 int)", "00100000", st);
	    
	    long jj = 21384572138457l;
	    st = hex.toHexDigits(jj);
	    errorCount += Checkers.checker("hex.toHexDigits(21384572138457 long)", "00001372fbd373d9", st);
	    st = hex.toHexDigits(jj, 4);
	    errorCount += Checkers.checker("hex.toHexDigits(21384572138457 long, outlen=4)", "73d9", st);
	    
        short ss = 32767;
        st = hex.toHexDigits(ss);
        errorCount += Checkers.checker("hex.toHexDigits(32767 short)", "7fff", st);
        
        String mary = "Mary had a little lamb";
        byte[] bytes = mary.getBytes();
        st = hex.formatHex(bytes);
        errorCount += Checkers.checker("hex.formatHex(bytes)", "4d:61:72:79:20:68:61:64:20:61:20:6c:69:74:74:6c:65:20:6c:61:6d:62", st);
        st = hex.formatHex(bytes, 3, 7);
        errorCount += Checkers.checker("hex.formatHex(bytes)", "79:20:68:61", st);
        
        errorCount += throwFromFormatHex(bytes, 2, 1);
        errorCount += throwFromFormatHex(bytes, -1, 17);
        errorCount += throwFromFormatHex(bytes, 3, 1776);
        
        ii = HexFormat.fromHexDigit('C');
        errorCount += Checkers.checker("HexFormat.fromHexDigit('C')", 12, ii);
        ii = HexFormat.fromHexDigit('F');
        errorCount += Checkers.checker("HexFormat.fromHexDigit('F')", 15, ii);
        ii = HexFormat.fromHexDigit('a');
        errorCount += Checkers.checker("HexFormat.fromHexDigit('a')", 10, ii);
        ii = HexFormat.fromHexDigit('5');
        errorCount += Checkers.checker("HexFormat.fromHexDigit('5')", 5, ii);
        
        errorCount += throwFromHexDigit('@');
        errorCount += throwFromHexDigit('~');
        errorCount += throwFromHexDigit('g');

        hex = HexFormat.of(); // no delimiter and lowercase characters
	    System.out.printf("hex fields starting with of(): %s\n", hex.toString());
        hex = hex.withPrefix("["); // add prefix
	    System.out.printf("hex fields withPrefix: %s\n", hex.toString());
        hex = hex.withSuffix("]"); // add suffix
	    System.out.printf("hex fields withSuffix: %s\n", hex.toString());
        hex = hex.withDelimiter("-"); // add delimiter
	    System.out.printf("hex fields withDelimiter: %s\n", hex.toString());
	    String prefix = hex.prefix();
	    String suffix = hex.suffix();
        errorCount += Checkers.checker("hex.prefix(\"ijk\")", "[", prefix);
        errorCount += Checkers.checker("hex.suffix(\"ijk\")", "]", suffix);
        
        char ch = hex.toHighHexDigit(79); // 0x4f
        errorCount += Checkers.checker("HexFormat.toHighHexDigit(79)", 52, ch);
        ch = hex.toLowHexDigit(79); // 0x4f
        errorCount += Checkers.checker("HexFormat.toLowHexDigit(79)", 102, ch);
	    
        String ijk = "ijk";
        bytes = ijk.getBytes();
        st = hex.formatHex(bytes);
        errorCount += Checkers.checker("hex.formatHex(\"ijk\")", "[69]-[6a]-[6b]", st);
        hex = hex.withUpperCase(); // use upper case
        st = hex.formatHex(bytes);
        errorCount += Checkers.checker("hex.formatHex(\"ijk\")", "[69]-[6A]-[6B]", st);
        hex = hex.withLowerCase(); // use lower case
        st = hex.formatHex(bytes);
        errorCount += Checkers.checker("hex.formatHex(\"ijk\")", "[69]-[6a]-[6b]", st);
        
        hex = HexFormat.ofDelimiter("<*>"); // a delimiter and lowercase characters
	    System.out.printf("hex fields starting with ofDelimiter(): %s\n", hex.toString());
        st = hex.formatHex(bytes);
        errorCount += Checkers.checker("hex.formatHex(\"ijk\") with delimiter=\"<*>\"", "69<*>6a<*>6b", st);
        
        System.out.printf("hex fields before comparisons: %s\n", hex.toString());
        HexFormat hex2 = HexFormat.of();
        HexFormat hex3 = HexFormat.of();
        System.out.printf("hex2 fields before comparisons: %s\n", hex2.toString());
        System.out.printf("hex3 fields before comparisons: %s\n", hex3.toString());
        if (hex.equals(hex2)) {
            System.out.println("*** ERROR, hex2 is not equal to hex but hex.equals(hex2) returned true");
            errorCount += 1;
        } else {
            System.out.println("As expected, hex2 is not equal to hex");
        }
        if (hex3.equals(hex2)) {
            System.out.println("As expected, hex3 = hex2");
        } else {
            System.out.println("*** ERROR, hex3 = hex2 but hex3.equals(hex2) returned false");
            errorCount += 1;
        }
        
        Checkers.theEnd(errorCount);
    }
}


