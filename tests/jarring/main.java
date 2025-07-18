import java.io.*;
import middle.calculator.Calculator;

public class main {

    public static int runner() {
        System.out.println("runner: Testing the use of import pkgcalc.Calculator");
        Calculator obj = new Calculator();
        int result = obj.add(100, 200);
        if (result != 300) {
            String msg = String.format("runner: *** ERROR, expected middle.pkgcalc.Calculator result=300 but observed %d", result);
            throw new AssertionError(msg);
        }
        System.out.println("runner: Success with result == (100 + 200 = 300)");
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

        // Get the JVM program name.
        System.out.printf("main: Argument count: %d\n", args.length);
        if (args.length > 0) {
            // %s -jar jarring.jar RUNNER
            System.out.println("main: Let's call function runner");
            int statusCode = runner();
            assert (statusCode == 0);
            System.out.println("main: RUNNER returned 0 (ok)");
            System.exit(0);
        }
        
		System.out.println("main: Create a jar");
		String jvmPgmName = jj._getProgramName();
		System.out.printf("main: jvmPgmName=%s\n", jvmPgmName);
        execCommand("jar --create --verbose --main-class=main --file=jarring.jar main.class middle/calculator/Calculator.class");
        
		System.out.println("main: Show the jar table of contents");
        execCommand("jar tf jarring.jar");
        
        System.out.println("main: Force the call of function runner");
        String text = String.format("%s -jar jarring.jar RUNNER", jvmPgmName);
        execCommand(text);

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

