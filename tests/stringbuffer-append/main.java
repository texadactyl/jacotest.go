
public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        StringBuffer sb1 = new StringBuffer();
        System.out.println("sb1 bytes: nil");
        errorCount += Checkers.checker("sb1.capacity(default)", 16, sb1.capacity());
        errorCount += Checkers.checker("sb1.length()", 0, sb1.length());
        
        sb1 = new StringBuffer(3);
        errorCount += Checkers.checker("sb1.capacity(3)", 3, sb1.capacity());
        errorCount += Checkers.checker("sb1.length()", 0, sb1.length());
        
        sb1.append("12345678");
        errorCount += Checkers.checker("sb1.append(\"12345678\")", "12345678", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(8)", 8, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(8)", 8, sb1.length());

        sb1.append(true);
        errorCount += Checkers.checker("sb1.append(true)", "12345678true", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(18)", 18, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(12)", 12, sb1.length());
        
        sb1.append('C');
        errorCount += Checkers.checker("sb1.append(C)", "12345678trueC", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(18)", 18, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(13)", 13, sb1.length());
        
        char charray [] = { 'a', 'b', 'c' };
        sb1.append(charray);
        errorCount += Checkers.checker("sb1.append('a', 'b', 'c')", "12345678trueCabc", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(18)", 18, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(16)", 16, sb1.length());
        
        double dd = 1.23;
        sb1.append(dd);
        errorCount += Checkers.checker("sb1.append(dd)", "12345678trueCabc1.23", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(38)", 38, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(20)", 20, sb1.length());
        
        int ii = 345;
        sb1.append(ii);
        errorCount += Checkers.checker("sb1.append(ii)", "12345678trueCabc1.23345", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(38)", 38, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(23)", 23, sb1.length());
        
        int jj = 81517;
        sb1.append(jj);
        errorCount += Checkers.checker("sb1.append(jj)", "12345678trueCabc1.2334581517", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(38)", 38, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(28)", 28, sb1.length());
        
        String str = "JKLMNOP";
        sb1.append(str);
        errorCount += Checkers.checker("sb1.append(JKLMNOP)", "12345678trueCabc1.2334581517JKLMNOP", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(38)", 38, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(35)", 35, sb1.length());
        
        Checkers.theEnd(errorCount);
    }
}
