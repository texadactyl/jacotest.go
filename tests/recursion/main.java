// hacked from https://www.javatpoint.com/recursion-in-java

public class main {

    static long factorial(long nn) {      
		if (nn == 1l)     
			return 1l;      
		else
            return(nn * factorial(nn - 1l));      
    }      
  
    static int printFibo(int count, int n1, int n2) {
    	if (count > 0) {    
			int n3 = n1 + n2;      
			System.out.print(" ");     
			System.out.print(n3);     
			n1 = n2;      
			n2 = n3;      
			return printFibo(count - 1, n1, n2);
		}
		return n2;
    }        

	public static void main(String[] args) { 

		System.out.println("Recursion tests");
			
		int errorCount = 0;
		
		long factoStart = 11;
		long factoResult = -1l;
		
		int fibo1 = 0, fibo2 = 1;
		int fiboCount = 15; 
		int fiboResult = -1;  
	
		System.out.print("Factorial of ");
		System.out.print(factoStart);
		System.out.print(" = ");
		factoResult = factorial(factoStart);
		System.out.println(factoResult);
		if (factoResult != 39916800) {
            System.out.print("*** ERROR, factorial test. Expected 39916800, observed ");
            System.out.println(factoResult);
            errorCount += 1;
		}
		
		System.out.print("Fibonacci sequence of ");
		System.out.print(fiboCount);
      	System.out.print(" elements: ");   
		System.out.print(fibo1);	// printing 0 and 1      
		System.out.print(" ");	    // printing 0 and 1      
		System.out.print(fibo2);	// printing 0 and 1      
      	fiboResult = printFibo(fiboCount - 2, fibo1, fibo2); // and then the rest
		System.out.print("\nEnding element = ");
		System.out.println(fiboResult);
		if (fiboResult != 377) {
            System.out.print("*** ERROR, factorial test. Expected 377, observed ");
            System.out.println(factoResult);
            errorCount += 1;
		}

        System.out.print("Error count = ");
        System.out.println(errorCount);
        assert (errorCount == 0);

	}  

}  
