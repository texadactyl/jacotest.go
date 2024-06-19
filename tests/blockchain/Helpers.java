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
    
	public void expObs(String label, String expected, String observed) {
		System.out.print("*** ERROR, ");
		System.out.print(label);
		System.out.print(", expected ");
		System.out.print(expected);
		System.out.print(", observed ");
		System.out.println(observed);		
	}

    public void byebye(int errorCount) {
        if (errorCount == 0) {
            System.out.println("No errors detected");
            System.exit(0);
        } else {
            String msg = String.format("Number of errors = %d", errorCount);
            throw new AssertionError(msg);
        }
    }

    public static String hexStringFromBytes(byte[] bytes) {
        if (bytes.length == 0)
            return "";
        String hexString = "";
        String wstr;
        for (byte bb : bytes) {
            wstr = String.format("%02x", bb);
            hexString = hexString.concat(wstr);
        }
        return hexString;
    }

}
