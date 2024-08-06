
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
        
        StringBuilder sb1 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
 
        sb1.deleteCharAt(3);
        errorCount += checker("sb1.delete(3)", "ABCEFGHIJKLMNOPQRSTUVWXYZ", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(25)", 25, sb1.length());
        
        sb1.delete(5, 5);
        errorCount += checker("sb1.delete(5, 5)", "ABCEFGHIJKLMNOPQRSTUVWXYZ", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(25)", 25, sb1.length());
                 
        sb1.delete(5, 10);
        errorCount += checker("sb1.delete(5, 10)", "ABCEFLMNOPQRSTUVWXYZ", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(20)", 20, sb1.length());
        
        sb1.deleteCharAt(0);
        errorCount += checker("sb1.delete(0)", "BCEFLMNOPQRSTUVWXYZ", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(19)", 19, sb1.length());
        
        sb1.deleteCharAt(18);
        errorCount += checker("sb1.delete(0)", "BCEFLMNOPQRSTUVWXY", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(18)", 18, sb1.length());
        
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
                 
        assert(errorCount == 0);
        System.out.printf("No errors\n");
    }
}
