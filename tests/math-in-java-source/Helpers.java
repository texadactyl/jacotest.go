public class Helpers {

	MathLite ml = new MathLite();

    public void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(value);
    }

    public void printLabeledObjects(String label, Object value1, Object value2) {
        System.out.print(label);
        System.out.print(": value1=");
        System.out.print(value1);
        System.out.print(", value2=");
        System.out.println(value2);
    }

    public void printBracketedObject(String label, Object value) {
        System.out.print(label);
        System.out.print(" = {");
        System.out.print(value);
        System.out.println("}");
    }

    public int isItClose(String label, double arg1, double arg2, double tolerance) {
    	
    	System.out.print(label);
        if (ml.relErr(arg1, arg2) < tolerance) {
            printLabeledObjects(": Success (close enough)", arg1, arg2);
            return 0;
        }
        printLabeledObjects(": *** ERROR, not close enough", arg1, arg2);
        return 1;
    }
    
    public int isItClose(String label, double arg1, double arg2) {
    	
    	return isItClose(label, arg1, arg2, ml.EPSILON);

    }
    
    public int isItTrue(String label, boolean bool) {
        if (bool) {
            printLabeledString(label, "Success (true)");
            return 0;
        }
        printLabeledString("*** ERROR, ", label);
        return 1;
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
