import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class main {
	final static String IN_ZIP_FILE = "input.zip";
    public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Try FileInputStream instantiation .....");
        FileInputStream fs = new FileInputStream(IN_ZIP_FILE);
    }
}
