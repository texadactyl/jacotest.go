import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

    public static int _subProcess(jjSubProcessObject obj) {
        System.out.println("_subProcess: J function _subProcess (not Jacobin)");
        ProcessBuilder builder = new ProcessBuilder(obj.commandLine);
        Process process = null;
        int exitCode = 86;

        try {
            process = builder.start();
        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            System.out.printf("_subProcess: J function _subProcess: Failed to start commandLine, err: %s\n", errMsg);
            return 86;
        }

        try {
            // Capture stdout
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String stdoutBuffer = new String();
            String line;
            while ((line = stdoutReader.readLine()) != null) {
                stdoutBuffer.concat(line).concat(System.lineSeparator());
            }

            // Capture stderr
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String stderrBuffer = new String();
            while ((line = stderrReader.readLine()) != null) {
                stderrBuffer.concat(line).concat(System.lineSeparator());
            }

            // Wait for the process to exit
            exitCode = process.waitFor();

            System.out.println("_subProcess: J function _subProcess: Exit Code = " + exitCode);
            System.out.println("_subProcess: J function _subProcess: Standard Output:");
            System.out.println(Checkers.replaceEmptyString(stdoutBuffer));

            System.out.println("_subProcess: J function _subProcess: Standard Error:");
            System.out.println(Checkers.replaceEmptyString(stderrBuffer));

        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            System.out.printf("_subProcess: J function _subProcess: Unexpected error after ProcessBuilder.start() succeeded, err: %s\n", errMsg);
            ex.printStackTrace();
            return 86;
        }
        
        // No crashes!
        if (exitCode == -1)
            System.out.println("!!!!! jj._subProcess: Saw exit code -1 !!!!!");
        return exitCode;
    }
   
    public static String _getProgramName() {
        System.out.println("_getProgramName: J-class function _getProgramName (not Jacobin)");
        return "java";
    }
   
}

