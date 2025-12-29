import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Thread;

public class main {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
    
        System.out.println("Begin.");
    	String FilePath = "./x.x";
        File file = new File(FilePath);
        System.out.println("File object instantiated.");
        if (!file.createNewFile()) {
        	throw new AssertionError("*** ERROR, file.createNewFile() failed");
        }
        System.out.println("File created, now go to sleep.");
        Thread.sleep(10);
        System.out.println("Woke up!");
        if (!file.delete()) {
        	throw new AssertionError("*** ERROR, file.delete() failed");
        }
        System.out.println("File deleted.");
        
        Checkers.theEnd(0);
    }
}
