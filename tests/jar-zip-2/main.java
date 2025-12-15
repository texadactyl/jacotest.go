import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import middle.calculator1.Calculator1;
import middle.calculator2.Calculator2;
import middle.calculator3.Calculator3;
import middle.calculator4.Calculator4;
import middle.calculator5.Calculator5;
import middle.calculator6.Calculator6;
import middle.calculator7.Calculator7;
import middle.calculator8.Calculator8;

public class main {

    public static int runner() {
        int errorCount = 0;
        
        System.out.println("runner: Testing the use of 8 calculators");
        int observed = 42;
        
        Calculator1 obj1 = new Calculator1();
        Calculator2 obj2 = new Calculator2();
        Calculator3 obj3 = new Calculator3();
        Calculator4 obj4 = new Calculator4();
        Calculator5 obj5 = new Calculator5();
        Calculator6 obj6 = new Calculator6();
        Calculator7 obj7 = new Calculator7();
        Calculator8 obj8 = new Calculator8();

        for (int ix = 1; ix < 9; ix++) {
        
            // Instantiate the selected calculator and use it.
            try {
                switch (ix) {
                    case 1: observed = obj1.add(0); break;
                    case 2: observed = obj2.add(0); break;
                    case 3: observed = obj3.add(0); break;
                    case 4: observed = obj4.add(0); break;
                    case 5: observed = obj5.add(0); break;
                    case 6: observed = obj6.add(0); break;
                    case 7: observed = obj7.add(0); break;
                    case 8: observed = obj8.add(0); break;
                }
            } catch (Exception ex) {
                String msg = String.format("runner: *** ERROR, exception while trying to instantiate one of the calculators, err: %s", ex.getMessage());
                ++errorCount;
                return errorCount;
            }
            
            // Success?
            int expected = ix;
            errorCount += Checkers.checker("runner", expected, observed);
        }

        System.out.println("runner: End");
        return errorCount;
    }

    public static void execCommand(String text) {
        System.out.printf("======================================= execCommand: Command line: %s\n", text);
        jjSubProcessObject obj = new jjSubProcessObject();
        obj.classpath = new String[] {};
        obj.commandLine = text.split(" +");
        int exitCode = jj._subProcess(obj);
        System.out.printf("execCommand: Exit code: %d\n", exitCode);
        System.out.printf("execCommand: Stdout: %s\n", obj.stdout);
        System.out.printf("execCommand: Stderr: %s\n", obj.stderr);
        if (exitCode != 0)
            System.exit(86);
    }

    public static void main(String args[]) {
    
        int errorCount = 0;

        String os = System.getProperty("os.name").toLowerCase();
        if (args.length > 0) {
            // Second time through: invoked with the RUNNER command-line argument.
            System.out.printf("\n\n======================================= main: args.length=%d, args[0]=%s\n", args.length, args[0]);
            System.out.println("======================================= main: Let's call function runner");
            errorCount = runner();
            if (errorCount == 0)
                System.out.println("======================================= main: RUNNER returned 0 (ok)");
            else
                System.out.printf("======================================= main: RUNNER returned %d (oops)", errorCount);
            Checkers.theEnd(errorCount);
        }
        
        // First time through.
		String jvmPgmName = jj._getProgramName();
		System.out.printf("main: jvmPgmName=%s\n", jvmPgmName);

        // Build primary jar.
        execCommand("jar cfm jarA.jar MyManifest.mf main.class jj.class jjSubProcessObject.class middle/calculator1/Calculator1.class");
        
        // Build Class-Path files: zipB.zip jarC.jar zipD.zip jarE.jar.
        // Directory trees middle/calculator4/ and middle/calculator8/  are already available.  
        execCommand("jar tf jarA.jar");       
        execCommand("7z a zipB.zip middle/calculator2/Calculator2.class");
        execCommand("jar cf jarC.jar middle/calculator3/Calculator3.class");
        // Pre-existing: middle/calculator4/Calculator4.class
        execCommand("7z a zipD.zip middle/calculator5/Calculator5.class middle/calculator6/Calculator6.class");
        execCommand("jar cf jarE.jar middle/calculator7/Calculator7.class");
        // Pre-existing: middle/calculator8/Calculator8.class
        
        // Execute primary jar.
        String text = String.format("%s -jar jarA.jar RUNNER", jvmPgmName);
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

