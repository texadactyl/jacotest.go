import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        String homedir = System.getProperty("user.home");
        Path treetop = Paths.get(homedir);
        Path ABSPATH_FILE_ALLOCMB = treetop.toAbsolutePath();
        Path normPath = ABSPATH_FILE_ALLOCMB.normalize();
        String str = normPath.toString();
        System.out.println(str);
        errorCount += Checkers.checker("homedir >> path >> ABSPATH_FILE_ALLOCMB >> normPath", homedir, str);
        
        Checkers.theEnd(errorCount);
    }
}
