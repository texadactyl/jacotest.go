public class main {

    static int i42 = 42;
    static short s42 = 42;
    static long l42 = 42l;
    static float f42 = 42.0f;
    static double d42 = 42.0d;
    static char cc = 'A';
    static byte bb = 0x0a;
    
    final static int fi42 = 42;
    final static short fs42 = 42;
    final static long fl42 = 42l;
    final static float ff42 = 42.0f;
    final static double fd42 = 42.0d;
    final static char fcc = 'A';
    final static byte fbb = 0x0a;
    
    public static void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.println(value);
    }

    public static int isItTrue(String label, boolean bool) {
        if (bool) {
            printLabeledString("Success :: ", label);
            return 0;
        }
        printLabeledString("*** ERROR, not true that ", label);
        return 1;
    }
    
	public static void main(String[] args) {
		int errorCount = 0;
		errorCount += isItTrue("i42 = fi42", i42 == fi42);
		errorCount += isItTrue("s42 = fs42", s42 == fs42);
		errorCount += isItTrue("l42 = fl42", l42 == fl42);
		errorCount += isItTrue("f42 = ff42", f42 == ff42);
		errorCount += isItTrue("d42 = fd42", d42 == fd42);
		errorCount += isItTrue("cc = fcc", cc == fcc);
		errorCount += isItTrue("bb = fbb", bb == fbb);
		assert(errorCount == 0);
	}

}
