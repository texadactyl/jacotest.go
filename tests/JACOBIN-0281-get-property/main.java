public class main {

	public static int reporter(String property) {
        String pv = System.getProperty(property);
        if (pv == null || pv.equals(""))
        	pv = "null";
        if (pv.equals("\n"))
            pv = "\\n";
        System.out.print(property);
        System.out.printf(": \"%s\"\n", pv);
        return 0;
	}

    public static void main(String args[]) {

	System.out.println("Exercise System.getProperty()");
		int errorCount = 0;
        errorCount += reporter("user.name");
        errorCount += reporter("user.home");
        errorCount += reporter("user.dir");
        errorCount += reporter("file.separator");
        errorCount += reporter("java.class.path");
        errorCount += reporter("java.home");
        errorCount += reporter("java.vendor");
        errorCount += reporter("java.vendor.url");
        errorCount += reporter("java.version");
        errorCount += reporter("line.separator");
        errorCount += reporter("os.arch");
        errorCount += reporter("os.name");
        errorCount += reporter("os.version");
        errorCount += reporter("path.separator");

        Checkers.theEnd(errorCount);
    }

}

