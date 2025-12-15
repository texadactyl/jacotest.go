public class main {
    private static void fn() {
        int ii = Integer.parseInt("rubbish");
        throw new AssertionError("Missed NumberFormatException");
    }

    public static void main(String[] args) {
    
        int errorCount = 0;
        System.out.println("Catch an exception thrown in a called function");
        
        try {            
            fn();
            System.out.println("*** ERROR, failed to catch NumberFormatException");
            errorCount += 1;
        } catch (NumberFormatException ex) {
            System.out.println("Success :: Caught NumberFormatException");
            ex.printStackTrace();
        }
        
        Checkers.theEnd(0);

   }
}

