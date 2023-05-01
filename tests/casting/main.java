// Hacked from https://www.studytonight.com/java/type-casting-in-java.php

public class main {

	public static void printLabeledString(String label, String value) {
		System.out.print(label);
		System.out.println(value);
	}
	
	public static int isItTrue(String label, boolean bool) {
		if(bool) {
			printLabeledString("Success :: ", label);
			return 0;
		}
		printLabeledString("*** FAILED :: ", label);
		return 1;
	}

	public static void main(String[] args) throws Exception {
		int errorCount = 0;
		
		System.out.println("Casting");
		
		double dd = 120.04;  
		long ll = (long) dd;   
		int ii = (int) ll;
		System.out.println("Double value " + dd); 
		errorCount += isItTrue("ll == 120", ll == 120);
		errorCount += isItTrue("ii == 120", ii == 120);

		byte bb;  
		ii = 355;  
		dd = 423.150; 
		bb = (byte) ii; 
		System.out.println("Conversion of int to byte: i = " + ii + ", bb = " + bb);  
		errorCount += isItTrue("bb == 99", bb == 99);
		bb = (byte) dd;        
		System.out.println("Conversion of double to byte: dd = " + dd + ", bb = " + bb);
		errorCount += isItTrue("bb == -89", bb + 89 == 0);
        
		// Check the error count
		if(errorCount == 0) {
			System.out.println("No errors detected");
		} else {
			printLabeledString("Number of errors = ", String.valueOf(errorCount));
			System.exit(1);
		}
	}
}
