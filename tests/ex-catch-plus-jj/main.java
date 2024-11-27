public class main {
    private static void fn() {
        int ii = Integer.parseInt("rubbish");
        throw new AssertionError("Missed NumberFormatException");
    }

    public static void main(String[] args) {
    
        int errorCount = 0;
        System.out.println("Catch an exception thrown in a called function plus use of jj._dumpStatics()");
        jj._dumpStatics("Here is a statics dump just BEFORE trying to call fn().", 3, "");
        
        try {            
            fn();
            System.out.println("*** ERROR, failed to catch NumberFormatException");
            errorCount += 1;
        } catch (NumberFormatException ex) {
            System.out.println("Success :: Caught NumberFormatException");
        }
        
        jj._dumpStatics("Here is a statics dump just AFTER the assertion-check.", 3, "");
        assert (errorCount == 0);

   }
}

class jj {
   public static void _dumpStatics(String from, int selection, String className) {
        System.out.printf("J-class function _dumpStatics: from=\"%s\", selection=%d, className=\"%s\": EMPTY (not Jacobin)\n", from, selection, className);
   }
}

