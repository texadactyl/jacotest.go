public class main {

    public static void main(String args[]) {

		ProcessHandle ph = ProcessHandle.current();
		System.out.println("1. ProcessHandle instantiated");
		ProcessHandle.Info info = ph.info();
		System.out.println("2. ProcessHandle.Info instantiated");
		String jvmPath = info.command().orElse("?");
		if (jvmPath.equals("?")) {
			throw new AssertionError("*** ERROR, parent: ProcessHandle.Info.command() failed.");
		}
	    System.out.print("3. parent: JVM executable: ");
	    System.out.println(jvmPath);
	    
	    Checkers.theEnd(0);

    }
}
