import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    private static String os;

    private static String mapsep(String arg) {       
        if (os.contains("windows")) {
            return arg.replace('/', '\\');
        }
        return arg;
    }
    
    public static void main(String[] args) {
        int errorCount = 0;
        os = System.getProperty("os.name").toLowerCase();

        // Paths.get tests
        Path p1A = Paths.get(mapsep("/a/b/c"));
        errorCount += Checkers.checker("Paths.get(\"/a/b/c\") toString", mapsep("/a/b/c"), p1A.toString());

        Path p1B = Paths.get(mapsep("C:/a/b/c"));
        errorCount += Checkers.checker("Paths.get(\"C:/a/b/c\") toString", mapsep("C:/a/b/c"), p1B.toString());

        Path p2 = Paths.get("a", "b", "c");
        errorCount += Checkers.checker("Paths.get(\"a\", \"b\", \"c\") toString", mapsep("a/b/c"), p2.toString());

        // Path.of tests (Java 11+)
        // Note: Using Paths.get here to be compatible with Java 8 while fulfilling the spirit of testing Path creation
        Path p3 = Paths.get(mapsep("/x/y/z"));
        errorCount += Checkers.checker("Paths.get (representing Path.of) toString", mapsep("/x/y/z"), p3.toString());

        // Path methods
        Path path = Paths.get(mapsep("/home/user/docs/file.txt"));
        errorCount += Checkers.checker("getFileName", "file.txt", path.getFileName().toString());
        errorCount += Checkers.checker("getParent", mapsep("/home/user/docs"), path.getParent().toString());
        errorCount += Checkers.checker("getRoot", mapsep("/"), path.getRoot().toString());
        errorCount += Checkers.checker("isAbsolute", true, path.isAbsolute());

        if (! os.contains("windows")) {
            System.out.println("Not running on Windows; isAbsolute() test is next .....");
            Path relative = Paths.get(mapsep("docs/file.txt"));
            errorCount += Checkers.checker("isAbsolute relative", false, relative.isAbsolute());
        }

        // getNameCount, getName, subpath
        errorCount += Checkers.checker("getNameCount", 4, path.getNameCount());
        errorCount += Checkers.checker("getName(0)", "home", path.getName(0).toString());
        errorCount += Checkers.checker("getName(3)", "file.txt", path.getName(3).toString());
        errorCount += Checkers.checker("subpath(0, 2)", mapsep("home/user"), path.subpath(0, 2).toString());

        // resolve, relativize
        Path base = Paths.get(mapsep("/home/user"));
        Path resolved = base.resolve(mapsep("docs/file.txt"));
        errorCount += Checkers.checker("resolve", mapsep("/home/user/docs/file.txt"), resolved.toString());

        Path p4 = Paths.get(mapsep("/home/user"));
        Path p5 = Paths.get(mapsep("/home/user/docs/file.txt"));
        errorCount += Checkers.checker("relativize", mapsep("docs/file.txt"), p4.relativize(p5).toString());

        // normalize
        Path p6 = Paths.get(mapsep("/home/user/../user/docs/./file.txt"));
        errorCount += Checkers.checker("normalize", mapsep("/home/user/docs/file.txt"), p6.normalize().toString());

        // startsWith, endsWith
        errorCount += Checkers.checker("startsWith", true, path.startsWith(mapsep("/home")));
        errorCount += Checkers.checker("endsWith", true, path.endsWith("file.txt"));

        Checkers.theEnd(errorCount);
    }
}

