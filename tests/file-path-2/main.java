import java.nio.file.Path;
import java.nio.file.Paths;

public class main {
    public static void main(String[] args) {
        int errorCount = 0;

        // Paths.get tests
        Path p1 = Paths.get("/a/b/c".replace("/", System.getProperty("file.separator")));
        errorCount += Checkers.checker("Paths.get(\"/a/b/c\") toString", "/a/b/c".replace("/", System.getProperty("file.separator")), p1.toString());

        Path p2 = Paths.get("a", "b", "c");
        errorCount += Checkers.checker("Paths.get(\"a\", \"b\", \"c\") toString", "a/b/c".replace("/", System.getProperty("file.separator")), p2.toString());

        // Path.of tests (Java 11+)
        // Note: Using Paths.get here to be compatible with Java 8 while fulfilling the spirit of testing Path creation
        Path p3 = Paths.get("/x/y/z".replace("/", System.getProperty("file.separator")));
        errorCount += Checkers.checker("Paths.get (representing Path.of) toString", "/x/y/z".replace("/", System.getProperty("file.separator")), p3.toString());

        // Path methods
        Path path = Paths.get("/home/user/docs/file.txt".replace("/", System.getProperty("file.separator")));
        errorCount += Checkers.checker("getFileName", "file.txt", path.getFileName().toString());
        errorCount += Checkers.checker("getParent", "/home/user/docs".replace("/", System.getProperty("file.separator")), path.getParent().toString());
        errorCount += Checkers.checker("getRoot", "/".replace("/", System.getProperty("file.separator")), path.getRoot().toString());
        errorCount += Checkers.checker("isAbsolute", true, path.isAbsolute());

        Path relative = Paths.get("docs/file.txt".replace("/", System.getProperty("file.separator")));
        errorCount += Checkers.checker("isAbsolute relative", false, relative.isAbsolute());

        // getNameCount, getName, subpath
        errorCount += Checkers.checker("getNameCount", 4, path.getNameCount());
        errorCount += Checkers.checker("getName(0)", "home", path.getName(0).toString());
        errorCount += Checkers.checker("getName(3)", "file.txt", path.getName(3).toString());
        errorCount += Checkers.checker("subpath(0, 2)", "home/user".replace("/", System.getProperty("file.separator")), path.subpath(0, 2).toString());

        // resolve, relativize
        Path base = Paths.get("/home/user".replace("/", System.getProperty("file.separator")));
        Path resolved = base.resolve("docs/file.txt".replace("/", System.getProperty("file.separator")));
        errorCount += Checkers.checker("resolve", "/home/user/docs/file.txt".replace("/", System.getProperty("file.separator")), resolved.toString());

        Path p4 = Paths.get("/home/user".replace("/", System.getProperty("file.separator")));
        Path p5 = Paths.get("/home/user/docs/file.txt".replace("/", System.getProperty("file.separator")));
        errorCount += Checkers.checker("relativize", "docs/file.txt".replace("/", System.getProperty("file.separator")), p4.relativize(p5).toString());

        // normalize
        Path p6 = Paths.get("/home/user/../user/docs/./file.txt".replace("/", System.getProperty("file.separator")));
        errorCount += Checkers.checker("normalize", "/home/user/docs/file.txt".replace("/", System.getProperty("file.separator")), p6.normalize().toString());

        // startsWith, endsWith
        errorCount += Checkers.checker("startsWith", true, path.startsWith("/home".replace("/", System.getProperty("file.separator"))));
        errorCount += Checkers.checker("endsWith", true, path.endsWith("file.txt"));

        Checkers.theEnd(errorCount);
    }
}

