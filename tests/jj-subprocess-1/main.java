import java.io.*;

public class main {

    static int subtest = 0;

    // Main entry point.
    public static void main(String[] args) {
    
        int errorCount = 0;
        
        jjSubProcessObject obj = new jjSubProcessObject();
        obj.classpath = new String[] {};
        String os = System.getProperty("os.name").toLowerCase();
        System.out.printf("O/S: %s\n", os);
        
        // Outcome: success
        if (os.contains("windows")) {
            obj.commandLine = new String[] { "cmd.exe", "/c", "dir" };
        } else {
            obj.commandLine = new String[] { "sh", "-c", "ls -l" };
        }
        int exitCode = jj._subProcess(obj);
        errorCount += Checkers.checker("list directory exit code", 0, exitCode);
        System.out.printf("Stdout: %s\n", obj.stdout);
        System.out.printf("Stderr: %s\n", obj.stderr);
        
        // Outcome: failure (file not found)
        ++subtest;
        if (os.contains("windows")) {
            obj.commandLine = new String[] { "SAURON_IS_NICE.exe", "/c", "dir" };
        } else {
            obj.commandLine = new String[] { "sauron_is_nice", "-c", "ls -l" };
        }
        exitCode = jj._subProcess(obj);
        errorCount += Checkers.checker("looking for SAURON_IS_NICE executable", false, exitCode == 0);
        System.out.printf("Stdout: %s\n", obj.stdout);
        System.out.printf("Stderr: %s\n", obj.stderr);
        
        Checkers.theEnd(errorCount);
    }
    
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
            System.out.printf("J function _subProcess: Cannot start commandLine, err: %s\n", errMsg);
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
   
}

class jjSubProcessObject {
	String[] commandLine; // input
	String[] classpath; // input; empty means use existing
	String stdout; // output
	String stderr; // output
}

