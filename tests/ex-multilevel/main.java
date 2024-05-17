public class main {
     public static void main(String[] args) {
     	int ii;
     	try {
            ii = 1 / 0;
        } catch (ArithmeticException ex1) {
        	System.out.println("Caught ArithmeticException level #1");
        	try {
        		ii = 1 / 0;
        	} catch (ArithmeticException ex2) {
            	System.out.println("Caught ArithmeticException level #2");
		    	try {
		    		ii = 1 / 0;
		    	} catch (ArithmeticException ex3) {
		        	System.out.println("Caught ArithmeticException level #3");
		        }
				finally {
					System.out.println("Finally level #3");
				}
            }
			finally {
				System.out.println("Finally level #2");
			}
        }
        finally {
        	System.out.println("Finally level #1");
        }

    }
}
