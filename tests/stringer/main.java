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
		
		System.out.println("String method tests");
		
		String ss1 = "\n  123:Alice456Bob7890Charley's Angels:Greece:Italy:France--!  ";
		printLabeledString("ss1: ", ss1);
		errorCount += isItTrue("ss1.length() = 63", ss1.length() == 63);
		String ss2 = ss1.trim(); 
		printLabeledString("ss2 = ss1 trimmed: ", ss2);
		errorCount += isItTrue("ss2.length() = 58", ss2.length() == 58);
		errorCount += isItTrue("ss2.substring(4, 9) = Alice", ss2.substring(4, 9).equals("Alice"));
		ss2 = ss2.replace(':', ' ');
		errorCount += isItTrue("ss2.length() after replace(':', ' ') = 58", ss2.length() == 58);
		errorCount += isItTrue("ss2.endsWith(--!)", ss2.endsWith("--!"));
		errorCount += isItTrue("! ss2.endsWith(--?)", ! ss2.endsWith("--?"));
		String ss3 = ss2.repeat(2);
		printLabeledString("ss3 length: ", String.valueOf(ss3.length()));
		errorCount += isItTrue("ss3.length() = 116", ss3.length() == 116);
		String [] ssa = ss2.split(" ");
		printLabeledString("ssa length: ", String.valueOf(ssa.length));
		for(int ndx = 0; ndx < ssa.length; ++ndx) {
			System.out.printf("\tssa[%d] = %s\n", ndx, ssa[ndx]);
		}
		errorCount += isItTrue("ssa.length = 6", ssa.length == 6);
		
		// Check the error count
		if(errorCount == 0) {
			System.out.println("No errors detected");
		} else {
			printLabeledString("Number of errors = ", String.valueOf(errorCount));
			System.exit(1);
		}
	}
}

