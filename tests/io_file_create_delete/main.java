import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Thread;

public class main {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
    
    	String FilePath = "./x.x";
        File file = new File(FilePath);
        if (!file.createNewFile()) {
        	throw new AssertionError("*** ERROR, file.createNewFile() failed");
        }
        Thread.sleep(500);
        if (!file.delete()) {
        	throw new AssertionError("*** ERROR, file.delete() failed");
        }
    }
}
