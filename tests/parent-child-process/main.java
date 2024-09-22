// NOTE: A CLASSPATH technique is used even though this is not necessary in POSIX systems.
//       This was done in order to accomodate Windows testing environments where
//       a subprocess does *NOT* inherit the current working directory from the parent process.

import java.io.*;
public class main {

    public static int commander(String cmd) {
        System.out.print("commander: Begin, cmd=");
        System.out.println(cmd);
        String [] cmdArray = cmd.split(" +");
        try {
            String line;
            Process process = Runtime.getRuntime().exec(cmdArray);
            BufferedReader bri = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));
            BufferedReader bre = new BufferedReader
                    (new InputStreamReader(process.getErrorStream()));
            while ((line = bri.readLine()) != null) {
                System.out.println(line);
            }
            bri.close();
            while ((line = bre.readLine()) != null) {
                System.out.println(line);
            }
            bre.close();
            int statusCode = process.waitFor();
            System.out.print("commander: End, status code = ");
            System.out.println(statusCode);
            return statusCode;
        }
        catch (Exception err) {
            System.out.print("commander: ");
            System.out.println(err);
            err.printStackTrace();
        	return 86;
        }
    }

    public static void main(String args[]) {

		if (args.length == 0) {
			//Parent Process
			System.out.println("parent: System.exit(nn) exercise");
		    System.out.print("parent: Argument count: ");
		    System.out.println(args.length);
			String cwd = System.getProperty("user.dir");
		    System.out.print("parent: I am here: ");
		    System.out.println(cwd);
			ProcessHandle ph = ProcessHandle.current();
			ProcessHandle.Info info = ph.info();
			String jvmPath = info.command().orElse("?");
			if (jvmPath.equals("?")) {
				throw new AssertionError("*** ERROR, parent: ProcessHandle.Info.command() failed.");
			}
		    System.out.print("parent: JVM executable: ");
		    System.out.println(jvmPath);
		    File file = new File(jvmPath);
		    String jvmName = file.getName().toLowerCase();
		    String cmdLine;
		    String className;
		    if (jvmName.equals("java") || jvmName.equals("java.exe"))
		    	className = "main"; // openjdk
		    else
		    	className = "main.class"; // jacobin
		    // Make use of a CLASSPATH that consists of one element: the parent's current working directory (cwd).
		    String cmdLnArray[] = new String[] {jvmName, "-cp", cwd, className, "123"};
		    cmdLine = String.join(" ", cmdLnArray);
		    int statusCode = commander(cmdLine);
		    assert (statusCode == 42);
		    
		} else {
			// Child process
			System.out.println("child: System.exit(nn) exercise");
		    System.out.print("child: Argument count: ");
		    System.out.println(args.length);
			String cwd = System.getProperty("user.dir");
		    System.out.print("child: I am here: ");
		    System.out.println(cwd);
			System.out.println("child: Next, I will System.exit(42).");
		    System.exit(42);
		}

    }
}
