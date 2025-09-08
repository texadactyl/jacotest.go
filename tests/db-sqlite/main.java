import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.jacotest.dbclient; // Force compilation of dbclient.java.

public class main {

    // TODO: needs qualitative tests


    public static void execCommand(String text) {
        System.out.printf("execCommand: Command line: %s\n", text);
        jjSubProcessObject obj = new jjSubProcessObject();
        obj.classpath = new String[] {};
        obj.commandLine = text.split(" +");
        int exitCode = jj._subProcess(obj);
        System.out.printf("execCommand: Exit code: %d\n", exitCode);
        System.out.printf("execCommand: Stdout: %s\n", obj.stdout);
        System.out.printf("execCommand: Stderr: %s\n", obj.stderr);
        assert exitCode == 0;
    }

    public static void main(String args[])  throws IOException {

		String text;
        String nameSqliteJar = "sqlite-jdbc.jar";
        String nameDbclientJar = "dbclient.jar";
        String nameDbclientClass = "dbclient";
		System.out.println("Create and use a database client (sqlite) jar");

        // Get the JVM program name.
		String jvmPgmName = jj._getProgramName();
		System.out.printf("jvmPgmName: %s\n", jvmPgmName);

        // Construct path objects for Files.copy.
        String pathSqliteJar = new File(nameSqliteJar).getAbsolutePath();
		String pathDbclientJar = new File(nameDbclientJar).getAbsolutePath();

        // Clone the Sqlite jar to the dbclient jar.
		FileUtilities.copyFile(pathSqliteJar, pathDbclientJar);

        // Update the dbclient jar with dbclient.class and name it as the main class.

        text = String.format("jar --update --verbose --main-class org.jacotest.%s --file=%s org/jacotest/%s.class",
                nameDbclientClass, nameDbclientJar, nameDbclientClass);
        execCommand(text);

        // Display directory of the dbclient jar.
        text = String.format("jar tf %s", nameDbclientJar);
        execCommand(text);

        // Execute dbclient main().
        text = String.format("%s -jar %s", jvmPgmName, nameDbclientJar);
        execCommand(text);
        
        Checkers.theEnd(0);

    }

}

class jjSubProcessObject {
	String[] commandLine; // input
	String[] classpath; // input; empty means use existing
	String stdout; // output
	String stderr; // output
}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

    public static int _subProcess(jjSubProcessObject obj) {
        System.out.println("J function _subProcess (not Jacobin)");
        ProcessBuilder builder = new ProcessBuilder(obj.commandLine);
        int exitCode = -86;

        try {
            Process process = builder.start();

            // Capture stdout
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = stdOut.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            // Capture stderr
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errors = new StringBuilder();
            while ((line = stdErr.readLine()) != null) {
                errors.append(line).append(System.lineSeparator());
            }

            // Wait for the process to exit
            exitCode = process.waitFor();

            System.out.println("J function _subProcess: Exit Code: " + exitCode);
            System.out.println("J function _subProcess: Standard Output:");
            System.out.println(output);

            System.out.println("J function _subProcess: Standard Error:");
            System.out.println(errors);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        // No crashes!
        return exitCode;
    }
   
    public static String _getProgramName() {
        System.out.println("J-class function _getProgramName (not Jacobin)");
        return "java";
    }
   
}

