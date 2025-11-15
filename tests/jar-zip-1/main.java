import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import middle.calculator1.Calculator1;
import middle.calculator2.Calculator2;

public class main {

    public static int runner() {
        System.out.println("runner: Testing the use of Calculator1 from a jar and Calculator2 from a zip");
        int result1 = -1;
        int result2 = -1;
        Calculator1 obj1;
        Calculator2 obj2;
        try {
            obj1 = new Calculator1();
            obj2 = new Calculator2();
        } catch (Exception ex) {
            String msg = String.format("runner: *** EXCEPTION while trying to instantiate Calculator1 or Calculator2, err: %s", ex.getMessage());
            throw new AssertionError(msg);
        }
        result1 = obj1.add(100, 200);
        result2 = obj2.subtract(100, 200);

        System.out.println("runner: add(100, 200) should yield 300");
        System.out.println("runner: subtract(100, 200) should yield -100");
        if (result1 != 300) {
            String msg = String.format("runner: *** ERROR, expected Calculator1 result=300 but observed %d", result1);
            throw new AssertionError(msg);
        }
        if (result2 != -100) {
            String msg = String.format("runner: *** ERROR, expected Calculator2 result=-100 but observed %d", result2);
            throw new AssertionError(msg);
        }
        System.out.println("runner: End");
        return 0;
    }

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

    public static void main(String args[]) {

        String os = System.getProperty("os.name").toLowerCase();
        if (args.length > 0) {
            // Second time through: invoked with the RUNNER command-line argument.
            System.out.printf("\n\n======================================= main: args.length=%d, args[0]=%s\n", args.length, args[0]);
            System.out.println("======================================= main: Let's call function runner");
            int statusCode = runner();
            assert (statusCode == 0);
            System.out.println("======================================= main: RUNNER returned 0 (ok)");
            System.exit(0);
        }
        
        // First time through.
		String jvmPgmName = jj._getProgramName();
		System.out.printf("main: jvmPgmName=%s\n", jvmPgmName);

        execCommand("jar cfm jarA.jar MyManifest.mf main.class jj.class jjSubProcessObject.class middle/calculator1/Calculator1.class");        
		System.out.println("\n======================================= main: Show the jar1 table of contents");
        execCommand("jar tf jarA.jar");
        
        if (os.contains("windows")) {
            execCommand("tar -a -c -f zipB.zip middle/calculator2/Calculator2.class");
        } else {
            execCommand("zip zipB.zip middle/calculator2/Calculator2.class");
        }
        
        System.out.println("\n======================================= main: Launch JVM with jar to call function runner");
        String text = String.format("%s -jar jarA.jar RUNNER", jvmPgmName);
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
        Process process = null;
        int exitCode = 86;

        try {
            process = builder.start();
        } catch (IOException ex) {
            String errMsg = ex.getMessage();
            System.out.printf("J function _subProcess: Failed to start commandLine, err: %s\n", errMsg);
            return exitCode;
        }

        try {
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

        } catch (IOException | InterruptedException ex) {
            String errMsg = ex.getMessage();
            System.out.printf("J function _subProcess: Unexpected error after starting commandLine, err: %s\n", errMsg);
            ex.printStackTrace();
            return exitCode;
        }
        
        // No crashes!
        return exitCode;
    }
   
    public static String _getProgramName() {
        System.out.println("J-class function _getProgramName (not Jacobin)");
        return "java";
    }
   
}

