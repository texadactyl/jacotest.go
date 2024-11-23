public class main {
    private static void fn() {
        int ii = Integer.parseInt("rubbish");
        throw new AssertionError("Missed NumberFormatException");
    }

    public static void main(String[] args) {
    
        int errorCount = 0;
        System.out.println("Catch an exception thrown in a called function plus use of jj._dumpStatics()");
        jj._dumpStatics("Here is a statics dump just before trying to call fn().\t");
        
        try {            
            fn();
            System.out.println("*** ERROR, failed to catch NumberFormatException");
            errorCount += 1;
        } catch (NumberFormatException ex) {
            System.out.println("Success :: Caught NumberFormatException");
        }
        
        jj._dumpStatics("Here is a statics dump just before the assertion-check.\t");
        assert (errorCount == 0);

   }
}

class jj {
   public static void _dumpStatics(String arg) {
        System.out.printf("J-function _dumpStatics: %s\nEMPTY! (not jacobin)\n", arg);
   }
}

