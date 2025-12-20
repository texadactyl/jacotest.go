import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        String homedir = System.getProperty("user.home");
        Path treetop = Paths.get(homedir);
        Path absPath = treetop.toAbsolutePath();
        Path normPath = absPath.normalize();
        String str = normPath.toString();
        System.out.println(str);
        errorCount += Checkers.checker("homedir >> path >> absPath >> normPath", homedir, str);
        
        Checkers.theEnd(errorCount);
    }
}
