import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    private static boolean onWindows = System.getProperty("os.name").toLowerCase().contains("windows");

    private static void requireWindows() {
        if (!onWindows) {
            System.out.println("Not on Windows so skipping Windows root semantics tests");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        requireWindows();
        Path p = Paths.get("\\foo");
        System.out.println("Path: " + p);
        System.out.println("isAbsolute: " + p.isAbsolute());
    }
}
