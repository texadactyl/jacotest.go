
public class main {

    public static int checker(String label, String expected, String observed) {
        System.out.printf("checker %s ", label);
        if (expected.equals(observed)) {
           System.out.printf(" ok, expected = %s = observed\n", expected);
            return 0;
        }
        System.out.printf(" ********** ERROR, expected = %s, observed = %s\n", expected, observed);
        return 1;
    }

    public static int checker(String label, int expected, int observed) {
        System.out.printf("checker %s ", label);
        if (expected == observed) {
            System.out.printf(" ok, expected = %d = observed\n", expected);
            return 0;
        }
        System.out.printf(" ********** ERROR, expected = %d, observed = %d\n", expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        
        StringBuffer sb1 = new StringBuffer();
        errorCount += checker("sb1.capacity(default)", 16, sb1.capacity());
        errorCount += checker("sb1.length()", 0, sb1.length());
        
        sb1 = new StringBuffer(3);
        errorCount += checker("sb1.capacity(3)", 3, sb1.capacity());
        errorCount += checker("sb1.length()", 0, sb1.length());
        
        sb1.append("abcdefgh");
        errorCount += checker("sb1.append(\"abcdefgh\")", "abcdefgh", sb1.toString());
        errorCount += checker("sb1.capacity(8)", 8, sb1.capacity());
        errorCount += checker("sb1.length(8)", 8, sb1.length());
        
        StringBuffer sb2 = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        System.out.println("Bytes: ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        errorCount += checker("sb2.capacity(42)", 42, sb2.capacity());
        errorCount += checker("sb2.length(26)", 26, sb2.length());
        errorCount += checker("charAt(2)", "C", String.valueOf(sb2.charAt(2)));
        errorCount += checker("substring(13)", "NOPQRSTUVWXYZ", sb2.substring(13));
        errorCount += checker("substring(13, 16)", "NOP", sb2.substring(13, 16));
        errorCount += checker("dynamic 1", "NOPQRSTUVWXYZ NOP", String.format("%s %s", sb2.substring(13), sb2.substring(13, 16)));
        errorCount += checker("dynamic 2", "NOPQRSTUVWXYZ 4.0 NOP", String.format("%s %.1f %s", sb2.substring(13), Math.sqrt(16.0), sb2.substring(13, 16)));
        errorCount += checker("dynamic 3", 29, sb2.length() + Math.getExponent(Math.sqrt(16.3)) + 1);
        errorCount += checker("dynamic 4", "\"29\"", String.format("\"%d\"", sb2.length() + Math.getExponent(Math.sqrt(16.3)) + 1));

        assert(errorCount == 0);
        System.out.printf("No errors\n");
    }
}
