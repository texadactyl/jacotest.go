public class main {

    public static void main(String args[]) {

		System.out.println("Exercise ProcessHandle");
    	ProcessHandle ph = ProcessHandle.current();
		ProcessHandle.Info info = ph.info();
		String command = info.command().orElse("?");
		String commandLine = info.commandLine().orElse("?");
		String user = info.user().orElse("?");
        System.out.printf("command: %s\n", command);
        System.out.printf("commandLine: %s\n", commandLine);
        System.out.printf("user: %s\n", user);

		if ( command.equals("?") || commandLine.equals("?") || user.equals("?") ) {
			throw new AssertionError("*** ERROR, ProcessHandle.info() failed");
		}
        
        System.out.println("Success!");
        
    }

}

