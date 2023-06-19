public class Helpers {

    public void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.print(" = ");
        System.out.println(value);
    }

    public void printBracketedObject(String label, Object value) {
        System.out.print(label);
        System.out.print(" = {");
        System.out.print(value);
        System.out.println("}");
    }

    public int isItTrue(String label, boolean bool) {
        if (bool) {
            printLabeledString("Success :: ", label);
            return 0;
        }
        printLabeledString("*** ERROR, ", label);
        return 1;
    }
    
	public static void expObs(String label, String expected, String observed) {
		System.out.print("*** ERROR, expected ");
		System.out.print(expected);
		System.out.print(", observed ");
		System.out.println(observed);		
	}

    public void byebye(int errorCount) {
        if (errorCount == 0) {
            System.out.println("No errors detected");
            System.exit(0);
        } else {
            printLabeledString("Number of errors = ", String.valueOf(errorCount));
            System.exit(1);
        }
    }

}
