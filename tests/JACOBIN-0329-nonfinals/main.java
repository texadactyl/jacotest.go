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

	public static void main(String[] args) {
		int errorCount = 0;
		errorCount += Checkers.checker("i42 = fi42", i42, fi42);
		errorCount += Checkers.checker("s42 = fs42", (int) s42, (int) fs42);
		errorCount += Checkers.checker("l42 = fl42", l42, fl42);
		errorCount += Checkers.withinTolerance("f42 = ff42", (double) f42, (double) ff42);
		errorCount += Checkers.withinTolerance("d42 = fd42", d42, fd42);
		errorCount += Checkers.checker("cc = fcc", cc, fcc);
		errorCount += Checkers.checker("bb = fbb", bb, fbb);
		
		Checkers.theEnd(errorCount);
	}

}
