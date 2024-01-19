import java.io.*;
import middle.calculator.Calculator;

public class main {

    public static int runner() {
        System.out.println("Testing the use of import pkgcalc.Calculator");
        Calculator obj = new Calculator();
        int result = obj.add(100, 200);
        if (result != 300) {
            String msg = String.format("runner: *** ERROR, expected middle.pkgcalc.Calculator result=300 but observed %d", result);
            throw new AssertionError(msg);
        }
        System.out.println("runner: Success with result == (100 + 200 = 300)");
        return 0;
    }

    public static void cmd(String text) {
        System.out.print("cmd: Begin, text=");
        System.out.println(text);
        try {
            String line;
            Process process = Runtime.getRuntime().exec(text);
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
        catch (Exception err) {
            String msg = String.format("*** ERROR, cmd: %s", err.getMessage());
            throw new AssertionError(msg);
        }
    }

    public static void main(String args[]) {

		System.out.println("Create and use a jar, exercising Runtime.getRuntime().exec()");
    	ProcessHandle ph = ProcessHandle.current();
		ProcessHandle.Info info = ph.info();
		String jvmPath = info.command().orElse("?");
		if (jvmPath.equals("?")) {
			throw new AssertionError("*** ERROR, ProcessHandle.Info.command() failed");
		}
        System.out.print("JVM executable: ");
        System.out.println(jvmPath);
        System.out.print("Argument count: ");
        System.out.println(args.length);
        if (args.length > 0) {
            int statusCode = runner();
            assert (statusCode == 0);
            System.exit(0);
        }
        cmd("jar --create --verbose --main-class=main --file=jarring.jar main.class middle/calculator/Calculator.class");
        cmd("jar tf jarring.jar");
        String text = String.format("%s -jar jarring.jar RUNNER", jvmPath);
        cmd(text);

    }

}

