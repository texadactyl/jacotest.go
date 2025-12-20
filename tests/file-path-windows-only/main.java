import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    private static boolean onWindows = System.getProperty("os.name").toLowerCase().contains("windows");

    private static void requireWindows() {
        if (!onWindows) {
            System.out.println("Skipping Windows root semantics tests");
            System.exit(0);
        }
    }

    private static String rootToString(Path p) {
        return (p.getRoot() == null) ? "<null>" : p.getRoot().toString();
    }

    public static void main(String[] args) {
        requireWindows();
        int errorCount = 0;

        // ------------------------------------------------------------
        // Absolute drive paths
        // ------------------------------------------------------------
        Path p1 = Paths.get("C:\\foo");
        errorCount += Checkers.checker("C:\\foo isAbsolute", true, p1.isAbsolute());
        errorCount += Checkers.checker("C:\\foo getRoot", "C:\\", rootToString(p1));

        // ------------------------------------------------------------
        // Drive-relative paths
        // ------------------------------------------------------------
        Path p2 = Paths.get("C:");
        errorCount += Checkers.checker("C: isAbsolute", false, p2.isAbsolute());
        errorCount += Checkers.checker("C: getRoot", "C:", rootToString(p2));

        Path p3 = Paths.get("C:foo");
        errorCount += Checkers.checker("C:foo isAbsolute", false, p3.isAbsolute());
        errorCount += Checkers.checker("C:foo getRoot", "C:", rootToString(p3));
        errorCount += Checkers.checker("C:foo getNameCount", 1, p3.getNameCount());
        errorCount += Checkers.checker("C:foo getName(0)", "foo", p3.getName(0).toString());

        // ------------------------------------------------------------
        // Drive-rooted but drive-unspecified (\foo)
        // ------------------------------------------------------------
        // HotSpot semantics: not absolute, getRoot = null
        Path p4 = Paths.get("\\foo");
        errorCount += Checkers.checker("\\foo isAbsolute", false, p4.isAbsolute());
        errorCount += Checkers.checker("\\foo getRoot", "<null>", rootToString(p4));
        errorCount += Checkers.checker("\\foo getNameCount", 1, p4.getNameCount());
        errorCount += Checkers.checker("\\foo getName(0)", "foo", p4.getName(0).toString());

        // ------------------------------------------------------------
        // UNC paths
        // ------------------------------------------------------------
        Path p5 = Paths.get("\\\\server\\share\\dir\\file.txt");
        errorCount += Checkers.checker("UNC isAbsolute", true, p5.isAbsolute());
        errorCount += Checkers.checker("UNC getRoot", "\\\\server\\share\\", rootToString(p5));
        errorCount += Checkers.checker("UNC getNameCount", 2, p5.getNameCount());
        errorCount += Checkers.checker("UNC getName(0)", "dir", p5.getName(0).toString());

        // ------------------------------------------------------------
        // Pure relative path
        // ------------------------------------------------------------
        Path p6 = Paths.get("foo");
        errorCount += Checkers.checker("foo isAbsolute", false, p6.isAbsolute());
        errorCount += Checkers.checker("foo getRoot", "<null>", rootToString(p6));

        // ------------------------------------------------------------
        // Mixed separator normalization
        // ------------------------------------------------------------
        Path p7 = Paths.get("C:/a/b\\c");
        errorCount += Checkers.checker("mixed separators root", "C:\\", rootToString(p7));
        errorCount += Checkers.checker("mixed separators normalize", "C:\\a\\b\\c", p7.normalize().toString());

        Checkers.theEnd(errorCount);
    }
}

