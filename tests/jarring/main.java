import java.io.*;
import middle.calculator.Calculator;

public class main {

    public static int runner() {
        System.out.println("Testing the use of import pkgcalc.Calculator");
        Calculator obj = new Calculator();
        int result = obj.add(100, 200);
        if (result != 300) {
            System.out.println("runner: *** FAILED *** middle.pkgcalc.Calculator did not get a result of 300");
            System.out.print("runner: ************** observed a result of ");
            System.out.println(result);
            return 1;
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
            process.waitFor();
            System.out.println("cmd: End");
        }
        catch (Exception err) {
            System.out.print("cmd: ");
            System.out.println(err);
            err.printStackTrace();
        }
    }

    public static void main(String args[]) {

		System.out.println("Create and use a jar, exercising Runtime.getRuntime().exec()");
    	ProcessHandle ph = ProcessHandle.current();
		ProcessHandle.Info info = ph.info();
		String jvmPath = info.command().orElse("?");
		if (jvmPath.equals("?")) {
			System.out.println("*** ERROR, ProcessHandle.Info.command() failed.");
			System.exit(1);
		}
        System.out.print("JVM executable: ");
        System.out.println(jvmPath);
        System.out.print("Argument count: ");
        System.out.println(args.length);
        if (args.length > 0) {
            System.exit(runner());
        }
        cmd("jar --create --verbose --main-class=main --file=jarring.jar main.java main.class uno");
        cmd("jar tf jarring.jar");
        String text = jvmPath + " -jar " + "jarring.jar RUNNER";
        cmd(text);

    }

}

