import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class main {

    public static int runner() {
        System.out.println("runner: Testing the use of import Calculator");
        System.out.println("runner: Next, instantiate the Calculator ...");
        int result = -1;
        try {
            Calculator obj = new Calculator();
            result = obj.add(100, 200);
        } catch (Exception ex) {
            String msg = String.format("runner: *** EXCEPTION during Calculator obj = new Calculator(), err: %s", ex.getMessage());
            throw new AssertionError(msg);
        }
        System.out.println("runner: Instantiated the Calculator");
        System.out.println("runner: add(100, 200) should yield 300 ...");
        if (result != 300) {
            String msg = String.format("runner: *** ERROR, expected middle.pkgcalc.Calculator result=300 but observed %d", result);
            throw new AssertionError(msg);
        }
        System.out.println("runner: Success with result == (100 + 200 = 300)");
        return 0;
    }
    
    public static void main(String args[]) {
    
        int ec;
        String cmdLine;

        if (args.length > 0) {
            // Invoked the second time i.e. with the RUNNER command-line argument.
            System.out.printf("\n\nmain RUNNER: args.length=%d, args[0]=%s\n", args.length, args[0]);
            System.out.println("main RUNNER: Next, will call function runner");
            int statusCode = runner();
            assert (statusCode == 0);
            System.out.println("main RUNNER: function runner returned 0");
            System.exit(0);
        }
        
        // Invoked the first time.
		System.out.println("\n======================================= main: Create a jar");
		String jvmPgmName = jj._getProgramName();
		System.out.printf("main: shell command line, jvmPgmName=%s\n", jvmPgmName);

        cmdLine = String.format("%s main 1", jvmPgmName);
        ec = Checkers.execCommand(cmdLine);

        cmdLine = String.format("%s main 2", jvmPgmName);
        ec = Checkers.execCommand(cmdLine);
        
        Checkers.theEnd(0);

    }

}

