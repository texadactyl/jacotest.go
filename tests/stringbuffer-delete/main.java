
public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        StringBuffer sb1 = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
 
        sb1.deleteCharAt(3);
        errorCount += Checkers.checker("sb1.delete(3)", "ABCEFGHIJKLMNOPQRSTUVWXYZ", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(25)", 25, sb1.length());
        
        sb1.delete(5, 5);
        errorCount += Checkers.checker("sb1.delete(5, 5)", "ABCEFGHIJKLMNOPQRSTUVWXYZ", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(25)", 25, sb1.length());
                 
        sb1.delete(5, 10);
        errorCount += Checkers.checker("sb1.delete(5, 10)", "ABCEFLMNOPQRSTUVWXYZ", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(20)", 20, sb1.length());
        
        sb1.deleteCharAt(0);
        errorCount += Checkers.checker("sb1.delete(0)", "BCEFLMNOPQRSTUVWXYZ", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(19)", 19, sb1.length());
        
        sb1.deleteCharAt(18);
        errorCount += Checkers.checker("sb1.delete(0)", "BCEFLMNOPQRSTUVWXY", sb1.toString());
        errorCount += Checkers.checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(18)", 18, sb1.length());
        
        try {
            sb1.deleteCharAt(-42);
            System.out.println("********** ERROR, failed to catch exception 1");
            errorCount++;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught exception 1");
        }
                 
        try {
            sb1.delete(5, 3);
            System.out.println("********** ERROR, failed to catch exception 2");
            errorCount++;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught exception 2");
        }
                 
        Checkers.theEnd(errorCount);
    }
}
