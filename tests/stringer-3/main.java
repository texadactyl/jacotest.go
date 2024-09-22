public class main {

    private static int cmpExpToObs(String label, String expected, String observed) {
        if (observed.equals(expected)) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=%s, observed=%s\n", label, expected, observed);
        return 1;
    }

    private static int cmpExpToObs(String label, int expected, int observed) {
        if (observed == expected) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=%d, observed=%d\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) throws Exception {
    
    	System.out.println("String manipulation and member functions - Part 3");
        final String start = "abcdefghijklmnopqrstuvwxyz";
        System.out.printf("start:    %s\n", start);
        int errorCount = 0;

        // ===== getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) testing
        
        char [] chars = start.toCharArray();
        start.getChars(3, 6, chars, 9);
        String observed = new String(chars);
        String expected = "abcdefghidefmnopqrstuvwxyz";
        System.out.printf("expected: %s\n", expected);
        errorCount += cmpExpToObs("after getChars(3, 6, chars, 9)", expected, observed);       
        try {
            start.getChars(-1, 6, chars, 9);
            System.out.println("*** ERROR, failed to catch StringIndexOutOfBoundsException on getChars(-1, 6, chars, 9)");
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on getChars(-1, 6, chars, 9)");
        }
        try {
            start.getChars(8192, 6, chars, 9);
            System.out.println("*** ERROR, failed to catch StringIndexOutOfBoundsException on getChars(8192, 6, chars, 9)");
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on getChars(8192, 6, chars, 9)");
        }
        try {
            start.getChars(3, 2, chars, 9);
            System.out.println("*** ERROR, failed to catch StringIndexOutOfBoundsException on start.getChars(3, 2, chars, 9)");
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on start.getChars(3, 2, chars, 9)");
        }
        try {
            start.getChars(3, 8192, chars, 9);
            System.out.println("*** ERROR, failed to catch StringIndexOutOfBoundsException on start.getChars(3, 8192, chars, 9)");
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on start.getChars(3, 8192, chars, 9)");
        }
        
        // ===== indexOf(int ch .....) testing
        
        int lowerCaseD = 100; // 'd'
        int ixOut = start.indexOf(lowerCaseD);
        errorCount += cmpExpToObs("indexOf(lowerCaseD)", 3, ixOut);
        ixOut = start.indexOf('?');
        errorCount += cmpExpToObs("indexOf('?')", -1, ixOut);
        ixOut = start.indexOf(lowerCaseD, 2);
        errorCount += cmpExpToObs("indexOf(lowerCaseD, 2)", 3, ixOut);
        ixOut = start.indexOf(lowerCaseD, -42);
        errorCount += cmpExpToObs("indexOf(lowerCaseD, -42)", 3, ixOut);
        ixOut = start.indexOf(lowerCaseD, 8192);
        errorCount += cmpExpToObs("indexOf(lowerCaseD, 8192)", -1, ixOut);
        ixOut = start.indexOf(lowerCaseD, 2, 10);
        errorCount += cmpExpToObs("indexOf(lowerCaseD, 2, 10)", 3, ixOut);
        try {
            ixOut = start.indexOf(lowerCaseD, 3, 2);
            System.out.printf("*** ERROR, failed to catch StringIndexOutOfBoundsException on start.indexOf(lowerCaseD, 3, 2), ixOut=%d\n", ixOut);
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on start.indexOf(lowerCaseD, 3, 2)");
        }
        try {
            ixOut = start.indexOf(lowerCaseD, -1, 10);
            System.out.printf("*** ERROR, failed to catch StringIndexOutOfBoundsException on start.indexOf(lowerCaseD, -1, 10), ixOut=%d\n", ixOut);
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on start.indexOf(lowerCaseD, -1, 10)");
        }
        try {
            ixOut = start.indexOf(lowerCaseD, 1, -10);
            System.out.printf("*** ERROR, failed to catch StringIndexOutOfBoundsException on start.indexOf(lowerCaseD, 1, -10), ixOut=%d\n", ixOut);
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on start.indexOf(lowerCaseD, 1, -10)");
        }
        
        // ===== indexOf(String str .....) testing
        
        String arg = "defgh";
        ixOut = start.indexOf(arg);
        errorCount += cmpExpToObs("indexOf(arg=defgh)", 3, ixOut);
        ixOut = start.indexOf("???");
        errorCount += cmpExpToObs("indexOf(???)", -1, ixOut);
        ixOut = start.indexOf(arg, 2);
        errorCount += cmpExpToObs("indexOf(arg, 2)", 3, ixOut);
        ixOut = start.indexOf(arg, -42);
        errorCount += cmpExpToObs("indexOf(arg, -42)", 3, ixOut);
        ixOut = start.indexOf(arg, 8192);
        errorCount += cmpExpToObs("indexOf(arg, 8192)", -1, ixOut);
        ixOut = start.indexOf(arg, 2, 10);
        errorCount += cmpExpToObs("indexOf(arg, 2, 10)", 3, ixOut);
        try {
            ixOut = start.indexOf(arg, 3, 2);
            System.out.printf("*** ERROR, failed to catch StringIndexOutOfBoundsException on start.indexOf(arg, 3, 2), ixOut=%d\n", ixOut);
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on start.indexOf(arg, 3, 2)");
        }
        try {
            ixOut = start.indexOf(arg, -1, 10);
            System.out.printf("*** ERROR, failed to catch StringIndexOutOfBoundsException on start.indexOf(arg, -1, 10), ixOut=%d\n", ixOut);
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on start.indexOf(arg, -1, 10)");
        }
        try {
            ixOut = start.indexOf(arg, 1, -10);
            System.out.printf("*** ERROR, failed to catch StringIndexOutOfBoundsException on start.indexOf(arg, 1, -10), ixOut=%d\n", ixOut);
            errorCount += 1;
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Successfully caught StringIndexOutOfBoundsException on start.indexOf(arg, 1, -10)");
        }
        
        // ===== The End
        
		assert(errorCount == 0);
    }
}

