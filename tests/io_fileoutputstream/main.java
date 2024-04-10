import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class main {

    private static long getLongEntropy() {
        long currentTime = System.nanoTime();
        long hashCode = (long) Math.random() * Integer.MAX_VALUE;
        return currentTime ^ hashCode;
    }

	private static int doRdAllCmp(String label, byte[] expected, int nbytes, String path) throws FileNotFoundException, IOException {
	
		System.out.printf("\n========== doRdAllCmp: %s, path=%s, nbytes=%d\n", label, path, nbytes);

        // Create a 2nd byte array.
    	byte observed [] = new byte[nbytes];  	
        for (int ii = 0; ii < nbytes; ii++) {
        	observed[ii] = 0;
        }
         
    	// Read observed from file all at once.
        FileInputStream fis = new FileInputStream(path);
        fis.read(observed);
        fis.close();
        
        // Compare.
        for (int ii = 0; ii < nbytes; ii++) {
        	//System.out.printf("DEBUG expected[%d]=%x, observed[%d]=%x\n", ii, expected[ii], ii, observed[ii]);
        	if (observed[ii] != expected[ii]) {
        		System.out.printf("*** ERROR, expected[%d]=%x, observed[%d]=%x\n", ii, expected[ii], ii, observed[ii]);
        		return 1;
        	}
        }
        
        return 0;
	}

    public static void main(String[] args) throws FileNotFoundException, IOException {
    
		int NBYTES = 10000;
    	int errorCounter = 0;
    	String FilePath = "./out.bin";
    	String wstr;

        int offset = 16;
        int length = 32;
    	
    	// Create a random byte array.
    	byte bytes_1[] = new byte[NBYTES];  	
        for (int ii = 0; ii < NBYTES; ii++) {
        	bytes_1[ii] = (byte) getLongEntropy();
        }
    	
    	// Write the entire bytes_1 to file all at once.
    	File file = new File(FilePath);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes_1);
        fos.close();
        
        // Read back and compare.
        errorCounter += doRdAllCmp("after write(array)", bytes_1, NBYTES, FilePath);
       
    	// Write the entire bytes_1 to file, one by one.
    	file = new File(FilePath);
        fos = new FileOutputStream(file);
        for (int ii = 0; ii < NBYTES; ii++) {
        	fos.write((int) bytes_1[ii]);
        }
        fos.close();
        
        // Read back and compare.
        errorCounter += doRdAllCmp("after writing one int at a time", bytes_1, NBYTES, FilePath);
       
    	// Write a subset of bytes_1 to a file.
        fos = new FileOutputStream(FilePath);
        fos.write(bytes_1, offset, length);
        fos.close();
    	byte bytes_3[] = new byte[length];  	
        for (int ii = 0; ii < length; ii++) {
        	bytes_3[ii] = bytes_1[offset + ii];
        }
        
        // Read back and compare.
        errorCounter += doRdAllCmp("after writing a subset", bytes_3, length, FilePath);
       
        assert(errorCounter == 0);
    }
}
