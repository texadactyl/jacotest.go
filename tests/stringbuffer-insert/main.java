
public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        StringBuffer sb1 = new StringBuffer(3);
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
        
        sb1.insert(3, 'C');
        errorCount += Checkers.checker("sb1.insert(3, C)", "123C45678true", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(18)", 18, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(13)", 13, sb1.length());
        
        char charray [] = { 'x', 'y', 'z' };
        sb1.insert(3, charray);
        errorCount += Checkers.checker("sb1.insert(3, 'x', 'y', 'z')", "123xyzC45678true", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(18)", 18, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(16)", 16, sb1.length());
        
        double dd = 1.23;
        sb1.insert(3, dd);
        errorCount += Checkers.checker("sb1.insert(3, dd)", "1231.23xyzC45678true", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(38)", 38, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(20)", 20, sb1.length());
        
        int ii = 345;
        sb1.insert(3, ii);
        errorCount += Checkers.checker("sb1.insert(3, ii)", "1233451.23xyzC45678true", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(38)", 38, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(23)", 23, sb1.length());
        
        int jj = 81517;
        sb1.insert(3, jj);
        errorCount += Checkers.checker("sb1.insert(3, jj)", "123815173451.23xyzC45678true", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(38)", 38, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(28)", 28, sb1.length());
        
        String str = "JKLMNOP";
        sb1.insert(3, str);
        errorCount += Checkers.checker("sb1.insert(3, JKLMNOP)", "123JKLMNOP815173451.23xyzC45678true", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(38)", 38, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(35)", 35, sb1.length());
        
        str = "QRSTU";
        sb1.insert(0, str);
        errorCount += Checkers.checker("sb1.insert(0, QRSTU)", "QRSTU123JKLMNOP815173451.23xyzC45678true", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(78)", 78, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(40)", 40, sb1.length());
        
        str = "_THE_END";
        sb1.insert(40, str);
        errorCount += Checkers.checker("sb1.insert(35, _THE_END)", "QRSTU123JKLMNOP815173451.23xyzC45678true_THE_END", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(78)", 78, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(48)", 48, sb1.length());
        
        Checkers.theEnd(errorCount);
    }
}
