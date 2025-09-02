import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    // TODO: needs qualitative tests
    
    public static void main(String[] args) throws IOException {
        Path treetop = Paths.get("../..");
        treetop = treetop.toAbsolutePath().normalize();
        Files.walkFileTree(treetop, new SFV());
        
        Checkers.theEnd(0);

    }
}
