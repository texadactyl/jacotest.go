import java.io.*;
public class main {

    public static int commander(String cmd, String dirstr) {
        System.out.print("commander: Begin, cmd=");
        System.out.print(cmd);
        System.out.print(", dirstr=");
        System.out.println(dirstr);
        try {
            String line;
            String [] nullstr = {};
            File dir = new File(dirstr);
            Process process = Runtime.getRuntime().exec(cmd, nullstr, dir);
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
		    System.out.print("parent: Argument count: ");
		    System.out.println(args.length);
			String cwd = System.getProperty("user.dir");
		    System.out.print("parent: I am here: ");
		    System.out.println(cwd);
			ProcessHandle ph = ProcessHandle.current();
			ProcessHandle.Info info = ph.info();
			String jvmPath = info.command().orElse("?");
			if (jvmPath.equals("?")) {
				System.out.println("*** ERROR, parent: ProcessHandle.Info.command() failed.");
				System.exit(1);
			}
		    System.out.print("parent: JVM executable: ");
		    System.out.println(jvmPath);
		    File file = new File(jvmPath);
		    String jvmName = file.getName();
		    String text;
		    if (jvmName.equals("java"))
		    	text = jvmPath.concat(" main 123"); // OpenJDK
		    else
		    	text = jvmPath.concat(" main.class 123"); // jacobin
		    int statusCode = commander(text, cwd);
		    if (statusCode == 42) {
		    	System.out.println("parent: The child process exited with 42 as expected.");
		    	System.exit(0);
		    } else {
		    	System.out.print("*** ERROR, parent: expected 42 but the child process exited with status ");
		    	System.out.println(statusCode);
		    	System.exit(1);
		    }
		} else {
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
