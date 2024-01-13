public class Helpers {

    public void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.print(" : ");
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
    
    public void byebye(int errorCount) {
        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            String msg = String.format("Number of errors = %d", errorCount);
            throw new AssertionError(msg);
        }
    }

}
