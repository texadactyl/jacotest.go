import java.util.Optional;

public class main {

    public static void main(String[] args) {
    
        ProcessHandle ph = ProcessHandle.current();
		System.out.println("ProcessHandle ph = ProcessHandle.current() OK");
		ProcessHandle.Info info = ph.info();
		System.out.println("ProcessHandle.Info info = ph.info() OK");
		
		Optional<String> opt = info.command();
		System.out.printf("Optional info.command(): %s\n", opt.toString());
		
		String command = info.command().orElse("command?");
		System.out.printf("command: %s\n", command);

		String arguments = info.arguments().toString();
		System.out.printf("arguments: %s\n", arguments);

		String commandLine = info.commandLine().orElse("commandLine?");
		System.out.printf("commandLine: %s\n", commandLine);

		String user = info.user().orElse("user?");
		System.out.printf("user: %s\n", user);
		
    }
}


