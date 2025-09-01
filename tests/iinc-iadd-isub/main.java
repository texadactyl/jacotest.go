public class main {

    public static void main(String args[]) {
        int base, inker, sum, decker, diff;
        int errorCount = 0;
        
    	base = Integer.MAX_VALUE;
  	    base += 3;
  	    errorCount += Checkers.checker("1. MAX_VALUE += 3", -2147483646, base);
  	    
  	    base = Integer.MAX_VALUE;
  	    inker = 3;
  	    sum = base + inker;
   	    errorCount += Checkers.checker("2. MAX_VALUE + inker(=+3)", -2147483646, sum);
 	    
  	    base = Integer.MAX_VALUE;
  	    inker = -3;
  	    sum = base - inker;
   	    errorCount += Checkers.checker("3. MAX_VALUE - inker(=-3)", -2147483646, sum);
   	    
    	base = Integer.MIN_VALUE;
  	    base -= 3;
  	    errorCount += Checkers.checker("4. MIN_VALUE -= 3", 2147483645, base);
  	    
  	    base = Integer.MIN_VALUE;
  	    decker = 3;
  	    diff = base - decker;
   	    errorCount += Checkers.checker("5. MAX_VALUE - decker(=+3)", 2147483645, diff);
  	    
  	    base = Integer.MIN_VALUE;
  	    decker = -3;
  	    diff = base + decker;
   	    errorCount += Checkers.checker("6. MAX_VALUE + decker(=-3)", 2147483645, diff);
  	    
  	    Checkers.theEnd(errorCount);
    }
}
