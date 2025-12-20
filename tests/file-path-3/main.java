import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    private static boolean onWindows = System.getProperty("os.name").toLowerCase().contains("windows");

    private static void requireWindows() {
        if (!onWindows) {
            System.out.println("Skipping Windows-only root semantics tests");
        }
    }

    private static String rootToString(Path p) {
        return (p.getRoot() == null) ? "<null>" : p.getRoot().toString();
    }

    public static void main(String[] args) {
        int errorCount = 0;

        // ------------------------------------------------------------
        // POSIX / general tests (run on all OS)
        // ------------------------------------------------------------
        Path posixAbs = Paths.get("/a/b/c");
        errorCount += Checkers.checker("/a/b/c isAbsolute", true, posixAbs.isAbsolute());
        errorCount += Checkers.checker("/a/b/c getRoot", "/", rootToString(posixAbs));
        errorCount += Checkers.checker("/a/b/c getNameCount", 3, posixAbs.getNameCount());
        errorCount += Checkers.checker("/a/b/c getName(0)", "a", posixAbs.getName(0).toString());

        Path posixRel = Paths.get("a/b/c");
        errorCount += Checkers.checker("a/b/c isAbsolute", false, posixRel.isAbsolute());
        errorCount += Checkers.checker("a/b/c getRoot", "<null>", rootToString(posixRel));

        Path posixDot = Paths.get(".");
        errorCount += Checkers.checker(". isAbsolute", false, posixDot.isAbsolute());
        errorCount += Checkers.checker(". getRoot", "<null>", rootToString(posixDot));

        Path posixDotDot = Paths.get("..");
        errorCount += Checkers.checker(".. isAbsolute", false, posixDotDot.isAbsolute());
        errorCount += Checkers.checker(".. getRoot", "<null>", rootToString(posixDotDot));

        // ------------------------------------------------------------
        // Windows-only tests
        // ------------------------------------------------------------
        requireWindows();
        if (onWindows) {
            // Absolute drive paths
            Path p1 = Paths.get("C:\\foo");
            errorCount += Checkers.checker("C:\\foo isAbsolute", true, p1.isAbsolute());
            errorCount += Checkers.checker("C:\\foo getRoot", "C:\\", rootToString(p1));

            // Drive-relative paths
            Path p2 = Paths.get("C:");
            errorCount += Checkers.checker("C: isAbsolute", false, p2.isAbsolute());
            errorCount += Checkers.checker("C: getRoot", "C:", rootToString(p2));

            Path p3 = Paths.get("C:foo");
            errorCount += Checkers.checker("C:foo isAbsolute", false, p3.isAbsolute());
            errorCount += Checkers.checker("C:foo getRoot", "C:", rootToString(p3));
            errorCount += Checkers.checker("C:foo getNameCount", 1, p3.getNameCount());
            errorCount += Checkers.checker("C:foo getName(0)", "foo", p3.getName(0).toString());

            // Drive-rooted but drive-unspecified
            Path p4 = Paths.get("\\foo");
            errorCount += Checkers.checker("\\foo isAbsolute", true, p4.isAbsolute());
            errorCount += Checkers.checker("\\foo getRoot", "\\", rootToString(p4));
            errorCount += Checkers.checker("\\foo getNameCount", 1, p4.getNameCount());
            errorCount += Checkers.checker("\\foo getName(0)", "foo", p4.getName(0).toString());

            // UNC paths
            Path p5 = Paths.get("\\\\server\\share\\dir\\file.txt");
            errorCount += Checkers.checker("UNC isAbsolute", true, p5.isAbsolute());
            errorCount += Checkers.checker("UNC getRoot", "\\\\server\\share\\", rootToString(p5));
            errorCount += Checkers.checker("UNC getNameCount", 2, p5.getNameCount());
            errorCount += Checkers.checker("UNC getName(0)", "dir", p5.getName(0).toString());

            // Mixed separator normalization
            Path p7 = Paths.get("C:/a/b\\c");
            errorCount += Checkers.checker("mixed separators root", "C:\\", rootToString(p7));
            errorCount += Checkers.checker("mixed separators normalize", "C:\\a\\b\\c", p7.normalize().toString());
        }

        Checkers.theEnd(errorCount);
    }
}

