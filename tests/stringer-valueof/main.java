public class main {

    public static void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.println(value);
    }

    public static int isItTrue(String label, boolean bool) {
        if (bool) {
            printLabeledString("Success :: ", label);
            return 0;
        }
        printLabeledString("*** ERROR, ", label);
        return 1;
    }
    
    public static void main(String[] args) {
    
    	int errorCount = 0;
    
    	System.out.println("Set a String array element to various String.valueOf arguments.");
        String[][] s2d = new String[3][4];
        System.out.println("String.valueOf(42) .....");
        s2d[0][0] = String.valueOf(42);
        errorCount += isItTrue("42 --> \"42\"", s2d[0][0].equals("42"));
        System.out.println("String.valueOf(42L) .....");
        s2d[0][0] = String.valueOf(42L);
        errorCount += isItTrue("42L --> \"42\"", s2d[0][0].equals("42"));
        System.out.println("String.valueOf(4.2F) .....");
        s2d[0][0] = String.valueOf(4.2F);
        errorCount += isItTrue("4.2F --> \"4.2\"", s2d[0][0].equals("4.2"));
        System.out.println("String.valueOf(4.2D) .....");
        s2d[0][0] = String.valueOf(4.2D);
        errorCount += isItTrue("4.2D --> \"4.2\"", s2d[0][0].equals("4.2"));
        System.out.println("String.valueOf(\"42\") .....");
        s2d[0][0] = String.valueOf("42");
        errorCount += isItTrue("\"42\" --> \"42\"", s2d[0][0].equals("42"));
        System.out.println("String.valueOf('A') .....");
        s2d[0][0] = String.valueOf('A');
        errorCount += isItTrue("'A' --> \"A\"", s2d[0][0].equals("A"));
    	System.out.println("Did not crash!");

        if (errorCount == 0) {
            System.out.println("No errors detected");
            System.exit(0);
        } else {
            printLabeledString("Number of errors = ", String.valueOf(errorCount));
            System.exit(1);
        }

    }
    
}
