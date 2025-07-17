//import java.lang.AssertionError;

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
        if (exitCode != 0)
            ++errorCount;
        System.out.printf("Exit code: %d\n", exitCode);
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
        if (exitCode == 0)
            ++errorCount;
        System.out.printf("Exit code: %d\n", exitCode);
        System.out.printf("Stdout: %s\n", obj.stdout);
        System.out.printf("Stderr: %s\n", obj.stderr);
        
        assert errorCount == 0;
        System.out.println("Success!");
    }
    
}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

   public static int _subProcess(Object obj) {
        System.out.println("J-class function _subProcess (not Jacobin)");
        return main.subtest;
   }
   
}

class jjSubProcessObject {
	String[] commandLine; // input
	String[] classpath; // input; empty means use existing
	String stdout; // output
	String stderr; // output
}

