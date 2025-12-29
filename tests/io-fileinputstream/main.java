import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileDescriptor;
import java.nio.channels.FileChannel;
import java.lang.UnsupportedOperationException;

public class main {

	private static String DATA = "./data.bintext";
	private static boolean doChannel = false; // true is not supported by jacobin
	private static boolean doFD = false; // true is not supported by jacobin
	
    public static void main(String[] args) throws IOException, FileNotFoundException {
    	int errorCount = 0;
    	int fsize;
    	int oneByte;
    	int nbytes;
    	long skipped;
    	byte[] decabytes;
    	byte[] pentabytes;
    	byte[] centobytes;
    	String str;
    	
        File file = new File(DATA);
        FileInputStream fis = new FileInputStream(file);
        System.out.println("Opened the FileInputStream(File)");
        fsize = fis.available();
        errorCount += Checkers.checker("File size = 60", 60, fsize);
        
        oneByte = fis.read();
        errorCount += Checkers.checker("1-byte read = 255", 255, oneByte);
        
        oneByte = fis.read();
        errorCount += Checkers.checker("1-byte read = 49", 49, oneByte);
        
        decabytes = new byte[10];
        nbytes = fis.read(decabytes);
        str = new String(decabytes);
        errorCount += Checkers.checker("decabytes read = 2345678901", "2345678901", str);
        
        pentabytes = new byte[] { 'A', 'B', 'C', 'D', 'E'};
        nbytes = fis.read(pentabytes, 1, 2);
        str = new String(pentabytes);
        errorCount += Checkers.checker("pentabytes read = A23DE", "A23DE", str);
        
        centobytes = new byte[100];
        for (int ii = 0; ii < centobytes.length; ii++) {
        	switch(ii) {
        		case 91: centobytes[ii] = 'f'; break;
        		case 92: centobytes[ii] = 'i'; break;
        		case 93: 
        		case 94: centobytes[ii] = 'l'; break;
        		case 95: centobytes[ii] = 'e'; break;
        		case 96: centobytes[ii] = 'r'; break;
        		default: centobytes[ii] = '-';
        	}
        }
        nbytes = fis.read(centobytes);
        str = new String(centobytes, 0, 7);
        errorCount += Checkers.checker("centobytes nbytes = 46", 46, nbytes);
        errorCount += Checkers.checker("centobytes starts with = 4567890", "4567890", str);

        oneByte = fis.read();
        errorCount += Checkers.checker("1-byte read (EOF) 1 of 2 = -1", -1, oneByte);

        oneByte = fis.read();
        errorCount += Checkers.checker("1-byte read (EOF) 2 of 2 = -1", -1, oneByte);

        nbytes = fis.read(decabytes);
        str = new String(decabytes);
        errorCount += Checkers.checker("decabytes read at EOF, nbytes=-1", -1, nbytes);
        
        skipped = fis.skip(-51);
        errorCount += Checkers.checker("skip(-51) = -51", -51, skipped);

        nbytes = fis.read(decabytes);
        str = new String(decabytes);
        errorCount += Checkers.checker("decabytes read at (EOF - 51), nbytes=10", 10, nbytes);
        errorCount += Checkers.checker("decabytes read at (EOF - 51), string is 9012345678", "9012345678", str);
        
        fis.close();
        
        fis = new FileInputStream(DATA);
        System.out.println("Closed and reopened the FileInputStream(pathString)");
        
        oneByte = fis.read();
        errorCount += Checkers.checker("1-byte read = 255", 255, oneByte);
        
        decabytes = new byte[10];
        nbytes = fis.read(decabytes);
        str = new String(decabytes);
        errorCount += Checkers.checker("decabytes read at BOF, nbytes=10", 10, nbytes);
        errorCount += Checkers.checker("decabytes read at BOF, string is 1234567890", "1234567890", str);
        
         if (doChannel) {
		    try {
		    	FileChannel fc = fis.getChannel();
		    	System.out.println(fc);
		    }
		    catch(UnsupportedOperationException ex) {
		    	System.out.println(ex.getMessage());
		    }
        }
        
        if (doFD) {
		    try {
		    	FileDescriptor fc = fis.getFD();
		    	System.out.println(fc);
		    }
		    catch(UnsupportedOperationException ex) {
		    	System.out.println(ex.getMessage());
		    }
        }
        
       fis.close();
       System.out.println("Closed the FileInputStream");
       
       Checkers.theEnd(errorCount);
    }
}
