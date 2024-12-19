public class main {

    // Check observed string to expected string.
    private static int checker(String label, int expected, int observed) {
        if (observed == expected) {
            System.out.printf("Success :: %s (%d)\n", label, expected);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=%d, observed=%d\n", label, expected, observed);
        return 1;
    }


    public static void main(String args[]) {
        int base, inker, sum, decker, diff;
        int errorCount = 0;
        
    	base = Integer.MAX_VALUE;
  	    base += 3;
  	    errorCount += checker("1. MAX_VALUE += 3", -2147483646, base);
  	    
  	    base = Integer.MAX_VALUE;
  	    inker = 3;
  	    sum = base + inker;
   	    errorCount += checker("2. MAX_VALUE + inker(=+3)", -2147483646, sum);
 	    
  	    base = Integer.MAX_VALUE;
  	    inker = -3;
  	    sum = base - inker;
   	    errorCount += checker("3. MAX_VALUE - inker(=-3)", -2147483646, sum);
   	    
    	base = Integer.MIN_VALUE;
  	    base -= 3;
  	    errorCount += checker("4. MIN_VALUE -= 3", 2147483645, base);
  	    
  	    base = Integer.MIN_VALUE;
  	    decker = 3;
  	    diff = base - decker;
   	    errorCount += checker("5. MAX_VALUE - decker(=+3)", 2147483645, diff);
  	    
  	    base = Integer.MIN_VALUE;
  	    decker = -3;
  	    diff = base + decker;
   	    errorCount += checker("6. MAX_VALUE + decker(=-3)", 2147483645, diff);
  	    
  	    assert(errorCount == 0);
    }
}
