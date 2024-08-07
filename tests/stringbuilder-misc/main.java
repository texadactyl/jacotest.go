
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
        char charray [] = { 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
 
        sb1.append(charray, 5, 3);
        errorCount += checker("sb1.append(charray, 5, 3)", "ABCDEFGHIJKLMNOPQRSTUVWXYZxyz", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(29)", 29, sb1.length());
        
        sb1.append(charray, 0, 2);
        errorCount += checker("sb1.append(charray, 0, 2)", "ABCDEFGHIJKLMNOPQRSTUVWXYZxyzst", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(31)", 31, sb1.length());
        
        sb1.insert(2, charray, 2, 2);
        errorCount += checker("sb1.insert(2, charray, 2, 2)", "ABuvCDEFGHIJKLMNOPQRSTUVWXYZxyzst", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(33)", 33, sb1.length());
        
        sb1.insert(0, charray, 2, 2);
        errorCount += checker("sb1.insert(0, charray, 2, 2)", "uvABuvCDEFGHIJKLMNOPQRSTUVWXYZxyzst", sb1.toString());
        errorCount += checker("sb1.capacity(42)", 42, sb1.capacity());
        errorCount += checker("sb1.length(35)", 35, sb1.length());
        
        sb1 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZxyz");        
        StringBuilder sb2 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        System.out.printf("sb1: %s\n", sb1);
        System.out.printf("sb2: %s\n", sb2);
        
        errorCount += checker("sb1.compareTo(sb2) #1", +1, Integer.signum(sb1.compareTo(sb2)));        
        errorCount += checker("sb2.compareTo(sb1) #1", -1, Integer.signum(sb2.compareTo(sb1)));
        
        sb2 = new StringBuilder("ABCDEFGHIJKLmNOPQRSTUVWXYZxyz");
        errorCount += checker("sb1.compareTo(sb2) #2", -1, Integer.signum(sb1.compareTo(sb2)));        
        errorCount += checker("sb2.compareTo(sb1) #2", +1, Integer.signum(sb2.compareTo(sb1)));
        
        sb2 = sb1;
        errorCount += checker("sb1.compareTo(sb2) equal", 0, Integer.signum(sb1.compareTo(sb2)));
        
        sb1.setCharAt(2, 'c');
        errorCount += checker("sb1.setCharAt(2, 'c')", "ABcDEFGHIJKLMNOPQRSTUVWXYZxyz", sb1.toString());
        sb1.setLength(5);
        errorCount += checker("sb1.setLength(5)", "ABcDE", sb1.toString());
        errorCount += checker("sb1.length(5)", 5, sb1.length());
        sb1.setLength(7);
        String str = sb1.toString();
        byte[] bb = str.getBytes();
        if (bb[5] != 0 || bb[6] != 0) {
            System.out.println("********** ERROR, The last 2 bytes of sb1 should be 0x00");
            errorCount++;
        }
        errorCount += checker("sb1.length(7)", 7, sb1.length());
        
        sb1 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZxyz");   
        
        try {
            sb1.append(charray, -3, 2);
            System.out.println("********** ERROR, failed to catch exception 1");
            errorCount++;
        } catch(IndexOutOfBoundsException ex) {
            System.out.println("Successfully caught exception 1");
        }
                 
        try {
            sb1.append(charray, 3, -2);
            System.out.println("********** ERROR, failed to catch exception 2");
            errorCount++;
        } catch(IndexOutOfBoundsException ex) {
            System.out.println("Successfully caught exception 2");
        }
                 
        try {
            sb1.append(charray, 8192, 2);
            System.out.println("********** ERROR, failed to catch exception 3");
            errorCount++;
        } catch(IndexOutOfBoundsException ex) {
            System.out.println("Successfully caught exception 3");
        }
                 
        assert(errorCount == 0);
        System.out.printf("No errors\n");
    }
}
