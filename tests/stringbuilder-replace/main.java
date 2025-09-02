
public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        StringBuilder sb1 = new StringBuilder("ABCDEFGH");
        String str = "123";
 
        StringBuilder sb2 = sb1.replace(3, 6, str);
        errorCount += Checkers.checker("sb1.replace(3, 6, str)", "ABC123GH", sb2.toString());
        errorCount += Checkers.checker("sb1.capacity(24)", 24, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(8)", 8, sb1.length());
        
        sb2 = sb1.replace(0, 6, str);
        errorCount += Checkers.checker("sb1.replace(0, 6, str)", "123GH", sb2.toString());
        errorCount += Checkers.checker("sb1.capacity(24)", 24, sb1.capacity());
        errorCount += Checkers.checker("sb1.length(5)", 5, sb1.length());
        
        
        sb1 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZxyz");   
        
        try {
            sb1.replace(-3, 6, str);
            System.out.println("********** ERROR, failed to catch exception 1");
            errorCount++;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught exception 1");
        }
                 
        try {
            sb1.replace(3, -6, str);
            System.out.println("********** ERROR, failed to catch exception 2");
            errorCount++;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught exception 2");
        }
                 
        try {
            sb1.replace(33, 6, str);
            System.out.println("********** ERROR, failed to catch exception 3");
            errorCount++;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught exception 3");
        }
                 
        Checkers.theEnd(errorCount);
    }
}
