import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        String cwd = System.getProperty("user.home");
        Path treetop = Paths.get(cwd);
        Path absPath = treetop.toAbsolutePath();
        Path normPath = absPath.normalize();
        String str = normPath.toString();
        System.out.println(str);
        errorCount += Checkers.checker("cwd >> path >> absPath >> normPath", cwd, str);
        
        Checkers.theEnd(errorCount);
    }
}
