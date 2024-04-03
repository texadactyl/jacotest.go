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
	
    public static int isItTrue(String label, boolean bool, Object observed) {
        if (bool) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR, expected: %s, observed value: ", label);
        System.out.println(observed);
        return 1;
    }

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
        errorCount += isItTrue("File size = 60", fsize == 60, fsize);
        
        oneByte = fis.read();
        errorCount += isItTrue("1-byte read = 255", oneByte == 255, oneByte);
        
        oneByte = fis.read();
        errorCount += isItTrue("1-byte read = 49", oneByte == 49, oneByte);
        
        decabytes = new byte[10];
        nbytes = fis.read(decabytes);
        str = new String(decabytes);
        errorCount += isItTrue("decabytes read = 2345678901", str.equals("2345678901"), str);
        
        pentabytes = new byte[] { 'A', 'B', 'C', 'D', 'E'};
        nbytes = fis.read(pentabytes, 1, 2);
        str = new String(pentabytes);
        errorCount += isItTrue("pentabytes read = A23DE", str.equals("A23DE"), str);
        
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
        errorCount += isItTrue("centobytes nbytes = 46", nbytes == 46, nbytes);
        errorCount += isItTrue("centobytes starts with = 4567890", str.equals("4567890"), str);

        oneByte = fis.read();
        errorCount += isItTrue("1-byte read (EOF) 1 of 2 = -1", oneByte == -1, oneByte);

        oneByte = fis.read();
        errorCount += isItTrue("1-byte read (EOF) 2 of 2 = -1", oneByte == -1, oneByte);

        nbytes = fis.read(decabytes);
        str = new String(decabytes);
        errorCount += isItTrue("decabytes read at EOF, nbytes=-1", nbytes == -1, nbytes);
        
        skipped = fis.skip(-51);
        errorCount += isItTrue("skip(-51) = -51", skipped == -51, skipped);

        nbytes = fis.read(decabytes);
        str = new String(decabytes);
        errorCount += isItTrue("decabytes read at (EOF - 51), nbytes=10", nbytes == 10, nbytes);
        errorCount += isItTrue("decabytes read at (EOF - 51), string is 9012345678", str.equals("9012345678"), str);
        
        fis.close();
        
        fis = new FileInputStream(DATA);
        System.out.println("Closed and reopened the FileInputStream(pathString)");
        
        oneByte = fis.read();
        errorCount += isItTrue("1-byte read = 255", oneByte == 255, oneByte);
        
        decabytes = new byte[10];
        nbytes = fis.read(decabytes);
        str = new String(decabytes);
        errorCount += isItTrue("decabytes read at BOF, nbytes=10", nbytes == 10, nbytes);
        errorCount += isItTrue("decabytes read at BOF, string is 1234567890", str.equals("1234567890"), str);
        
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
       
       assert(errorCount == 0);
    }
}
