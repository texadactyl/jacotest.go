
public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        StringBuilder sb1 = new StringBuilder();
        errorCount += Checkers.checker("sb1.capacity(default)", 16, sb1.capacity());
        errorCount += Checkers.checker("sb1.length()", 0, sb1.length());
        
        sb1 = new StringBuilder(3);
        errorCount += Checkers.checker("sb1.capacity(3)", 3, sb1.capacity());
        errorCount += Checkers.checker("sb1.length()", 0, sb1.length());
        
        sb1.append("abcdefgh");
        errorCount += Checkers.checker("sb1.append(\"abcdefgh\")", "abcdefgh", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(8)", 8, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(8)", 8, sb1.length());
        
        StringBuilder sb2 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        System.out.println("Bytes: ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        errorCount += Checkers.checker("sb2.capacity(42)", 42, sb2.capacity());
        errorCount += Checkers.checker("sb2.length(26)", 26, sb2.length());
        errorCount += Checkers.checker("charAt(2)", "C", String.valueOf(sb2.charAt(2)));
        errorCount += Checkers.checker("substring(13)", "NOPQRSTUVWXYZ", sb2.substring(13));
        errorCount += Checkers.checker("substring(13, 16)", "NOP", sb2.substring(13, 16));
        errorCount += Checkers.checker("dynamic 1", "NOPQRSTUVWXYZ NOP", String.format("%s %s", sb2.substring(13), sb2.substring(13, 16)));
        errorCount += Checkers.checker("dynamic 2", "NOPQRSTUVWXYZ 4.0 NOP", String.format("%s %.1f %s", sb2.substring(13), Math.sqrt(16.0), sb2.substring(13, 16)));
        errorCount += Checkers.checker("dynamic 3", 29, sb2.length() + Math.getExponent(Math.sqrt(16.3)) + 1);
        errorCount += Checkers.checker("dynamic 4", "\"29\"", String.format("\"%d\"", sb2.length() + Math.getExponent(Math.sqrt(16.3)) + 1));

        Checkers.theEnd(errorCount);
    }
}
