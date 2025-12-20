import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    private static boolean onPosix() {
        String os = System.getProperty("os.name").toLowerCase();
        return !(os.contains("windows"));
    }

    private static String rootToString(Path p) {
        return (p.getRoot() == null) ? "<null>" : p.getRoot().toString();
    }

    public static void main(String[] args) {
        int errorCount = 0;

        // ------------------------------------------------------------
        // Absolute POSIX paths
        // ------------------------------------------------------------
        Path p1 = Paths.get("/a/b/c");
        errorCount += Checkers.checker("/a/b/c isAbsolute", true, p1.isAbsolute());
        errorCount += Checkers.checker("/a/b/c getRoot", "/", rootToString(p1));
        errorCount += Checkers.checker("/a/b/c getNameCount", 3, p1.getNameCount());
        errorCount += Checkers.checker("/a/b/c getName(0)", "a", p1.getName(0).toString());
        errorCount += Checkers.checker("/a/b/c getName(2)", "c", p1.getName(2).toString());

        // ------------------------------------------------------------
        // Relative paths
        // ------------------------------------------------------------
        Path p2 = Paths.get("foo/bar");
        errorCount += Checkers.checker("foo/bar isAbsolute", false, p2.isAbsolute());
        errorCount += Checkers.checker("foo/bar getRoot", "<null>", rootToString(p2));
        errorCount += Checkers.checker("foo/bar getNameCount", 2, p2.getNameCount());
        errorCount += Checkers.checker("foo/bar getName(0)", "foo", p2.getName(0).toString());

        // ------------------------------------------------------------
        // Current and parent directory
        // ------------------------------------------------------------
        Path p3 = Paths.get(".");
        errorCount += Checkers.checker(". isAbsolute", false, p3.isAbsolute());
        errorCount += Checkers.checker(". getRoot", "<null>", rootToString(p3));

        Path p4 = Paths.get("..");
        errorCount += Checkers.checker(".. isAbsolute", false, p4.isAbsolute());
        errorCount += Checkers.checker(".. getRoot", "<null>", rootToString(p4));

        // ------------------------------------------------------------
        // Path normalization
        // ------------------------------------------------------------
        Path p5 = Paths.get("/x/./y/../z");
        errorCount += Checkers.checker("/x/./y/../z isAbsolute", true, p5.isAbsolute());
        errorCount += Checkers.checker("/x/./y/../z getRoot", "/", rootToString(p5));
        errorCount += Checkers.checker("/x/./y/../z normalize", "/x/z", p5.normalize().toString());

        // ------------------------------------------------------------
        // Mixed relative and absolute
        // ------------------------------------------------------------
        Path p6 = Paths.get("/a/b", "c/d");
        errorCount += Checkers.checker("/a/b + c/d isAbsolute", true, p6.isAbsolute());
        errorCount += Checkers.checker("/a/b + c/d getRoot", "/", rootToString(p6));
        errorCount += Checkers.checker("/a/b + c/d normalize", "/a/b/c/d", p6.normalize().toString());

        // ------------------------------------------------------------
        // Single root path
        // ------------------------------------------------------------
        Path p7 = Paths.get("/");
        errorCount += Checkers.checker("/ isAbsolute", true, p7.isAbsolute());
        errorCount += Checkers.checker("/ getRoot", "/", rootToString(p7));
        errorCount += Checkers.checker("/ getNameCount", 0, p7.getNameCount());

        Checkers.theEnd(errorCount);
    }
}

