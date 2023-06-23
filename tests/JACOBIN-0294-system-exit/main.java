import java.io.*;
public class main {

    public static int cmd(String text) {
        System.out.print("cmd: Begin, text=");
        System.out.println(text);
        try {
            String line;
            Process process = Runtime.getRuntime().exec(text);
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
            System.out.print("cmd: End, status code = ");
            System.out.println(statusCode);
            return statusCode;
        }
        catch (Exception err) {
            System.out.print("cmd: ");
            System.out.println(err);
            err.printStackTrace();
        }
        return 86;
    }

    public static void main(String args[]) {

    	ProcessHandle ph = ProcessHandle.current();
		ProcessHandle.Info info = ph.info();
		String jvmPath = info.command().orElse("?");
		if (jvmPath.equals("?")) {
			System.out.println("*** ERROR, ProcessHandle.Info.command() failed.");
			System.exit(1);
		}
        System.out.print("JVM executable: ");
        System.out.println(jvmPath);
        File file = new File(jvmPath);
        String jvmName = file.getName();
        System.out.print("Argument count: ");
        System.out.println(args.length);
		if (args.length == 0) {
		    System.out.println("I am the PARENT process.");
		    String text;
		    if (jvmName.equals("java"))
		    	text = jvmPath.concat(" main 123"); // OpenJDK
		    else
		    	text = jvmPath.concat(" main.class 123"); // jacobin
		    int statusCode = cmd(text);
		    if (statusCode == 42) {
		    	System.out.println("The subprocess exited with 42 as expected.");
		    	System.exit(0);
		    } else {
		    	System.out.print("*** ERROR, expected 42 but the subprocess exited with status ");
		    	System.out.println(statusCode);
		    	System.exit(1);
		    }
		} else {
		    System.out.println("I am the CHILD process.");
			System.out.println("Next, I will System.exit(42).");
		    System.exit(42);
		}

    }
}
