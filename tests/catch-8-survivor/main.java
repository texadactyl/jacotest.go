public class main {

    public static void main(String[] args) {
        int counter = 0;
        int iparsed;
        String fmt = "[%d] Caught NumberFormatException as expected\n";
        String errMsg;
        
	    try {
            iparsed = Integer.parseInt("ABC");
            errMsg = String.format("[%d] Failed to catch NumberFormatException", ++counter);
            throw new AssertionError(errMsg);
		} catch (NumberFormatException ex1) {
			System.out.printf(fmt, ++counter);
	        try {
                iparsed = Integer.parseInt("ABC");
                errMsg = String.format("[%d] Failed to catch NumberFormatException", ++counter);
                throw new AssertionError(errMsg);
		    } catch (NumberFormatException ex2) {
			    System.out.printf(fmt, ++counter);
                try {
                    iparsed = Integer.parseInt("ABC");
                    errMsg = String.format("[%d] Failed to catch NumberFormatException", ++counter);
                    throw new AssertionError(errMsg);
	            } catch (NumberFormatException ex3) {
		            System.out.printf(fmt, ++counter);
                    try {
                        iparsed = Integer.parseInt("ABC");
                        errMsg = String.format("[%d] Failed to catch NumberFormatException", ++counter);
                        throw new AssertionError(errMsg);
	                } catch (NumberFormatException ex4) {
		                System.out.printf(fmt, ++counter);
                        try {
                            iparsed = Integer.parseInt("ABC");
                            errMsg = String.format("[%d] Failed to catch NumberFormatException", ++counter);
                            throw new AssertionError(errMsg);
	                    } catch (NumberFormatException ex5) {
		                    System.out.printf(fmt, ++counter);
                            try {
                                iparsed = Integer.parseInt("ABC");
                                errMsg = String.format("[%d] Failed to catch NumberFormatException", ++counter);
                                throw new AssertionError(errMsg);
	                        } catch (NumberFormatException ex6) {
		                        System.out.printf(fmt, ++counter);
                                try {
                                    iparsed = Integer.parseInt("ABC");
                                    errMsg = String.format("[%d] Failed to catch NumberFormatException", ++counter);
                                    throw new AssertionError(errMsg);
	                            } catch (NumberFormatException ex7) {
		                            System.out.printf(fmt, ++counter);
                                    try {
                                        iparsed = Integer.parseInt("ABC");
                                        errMsg = String.format("[%d] Failed to catch NumberFormatException", ++counter);
                                        throw new AssertionError(errMsg);
	                                } catch (NumberFormatException ex8) {
		                                System.out.printf(fmt, ++counter);
	                                }
	                            }
	                        }
	                    }
	                }
	            }
		    }
		}
		
		Checkers.theEnd(0);
    }
}
