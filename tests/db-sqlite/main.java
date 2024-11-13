import java.io.*;
import java.nio.file.*;
import org.jacotest.dbclient; // Force compilation of dbclient.java.

public class main {

    public static void cmd(String text) {
        System.out.printf("cmd: Begin, text=%s\n", text);
        String [] cmdArray = text.split(" +");
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
            int exitValue = process.waitFor();
            if (exitValue != 0) {
                String msg = String.format("*** ERROR, cmd: exit value = %d", exitValue);
                throw new AssertionError(msg);
            }
            System.out.println("cmd: End");
        }
        catch (Exception ex) {
            String msg = String.format("*** ERROR, cmd: %s", ex.getMessage());
            throw new AssertionError(msg);
        }
    }

    public static void main(String args[]) {

		String text;
        String nameSqliteJar = "sqlite-jdbc.jar";
        String nameDbclientJar = "dbclient.jar";
        String nameDbclientClass = "dbclient";
		System.out.println("Create and use a database client (sqlite) jar, exercising Runtime.getRuntime().exec()");

        // Get the process command full path.
    	ProcessHandle ph = ProcessHandle.current();
		System.out.println("ProcessHandle ph = ProcessHandle.current() OK");
		ProcessHandle.Info info = ph.info();
		System.out.println("ProcessHandle.Info info = ph.info() OK");
		String jvmPath = info.command().orElse("?");
		System.out.println("String jvmPath = info.command().orElse(\"?\") OK");
		System.out.printf("jvmPath: %s\n", jvmPath);
		if (jvmPath.equals("?")) {
			throw new AssertionError("*** ERROR, ProcessHandle.Info.command() failed");
		}

        // Construct path objects for Files.copy.
		Path pathSqliteJar = Paths.get(nameSqliteJar);
		Path pathDbclientJar = Paths.get(nameDbclientJar);

        // Clone the Sqlite jar to the dbclient jar.
		try {
		    Files.copy(pathSqliteJar, pathDbclientJar, StandardCopyOption.REPLACE_EXISTING);
		} catch(IOException ex) {
            String msg = String.format("*** ERROR, Files.copy: %s", ex.toString());
            throw new AssertionError(msg);
		}

        // Update the dbclient jar with dbclient.class and name it as the main class.

        text = String.format("jar --update --verbose --main-class org.jacotest.%s --file=%s org/jacotest/%s.class",
                nameDbclientClass, nameDbclientJar, nameDbclientClass);
        cmd(text);

        // Display directory of the dbclient jar.
        text = String.format("jar tf %s", nameDbclientJar);
        cmd(text);

        // Execute dbclient main().
        text = String.format("%s -jar %s", jvmPath, nameDbclientJar);
        cmd(text);

    }

}
