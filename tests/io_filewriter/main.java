import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class main {

    private static int getIntEntropy(int modulus) {
        int currentTime = (int) System.nanoTime();
        int hashCode = (int) Math.random() * Integer.MAX_VALUE;
        hashCode ^= currentTime;
        if (hashCode < 0)
        	hashCode = -hashCode;
        return hashCode % modulus;
    }

	private static int doRdAllCmp(String label, char[] expected, int N_ELEMENTS, String path) throws FileNotFoundException, IOException {
	
		System.out.printf("\n========== doRdAllCmp: %s, path=%s, N_ELEMENTS=%d\n", label, path, N_ELEMENTS);

        // Create a 2nd byte array.
    	char observed [] = new char[N_ELEMENTS];  	
        for (int ii = 0; ii < N_ELEMENTS; ii++) {
        	observed[ii] = 0;
        }
         
    	// Read observed from file all at once.
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        isr.read(observed, 0, N_ELEMENTS);
        isr.close();
        
        // Compare.
        for (int ii = 0; ii < N_ELEMENTS; ii++) {
        	//System.out.printf("DEBUG expected[%d]=%x, observed[%d]=%x\n", ii, (int) expected[ii], ii, (int) observed[ii]);
        	if (observed[ii] != expected[ii]) {
        		System.out.printf("*** ERROR, expected[%d]=%x, observed[%d]=%x\n", ii, (int) expected[ii], ii, (int) observed[ii]);
        		return 1;
        	}
        }
        
        return 0;
	}

    public static void main(String[] args) throws FileNotFoundException, IOException {
    
		int N_ELEMENTS = 64;
		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+=";
		int N_ALPHABET = alphabet.length();
    	int errorCounter = 0;
    	String FilePath = "./out.bin";
    	String wstr;
    	int index;

        int offset = 16;
        int length = 32;
        boolean earlyExit = true;
    	
    	// Create a random char array.
    	char chArray[] = new char[N_ELEMENTS];  	
        for (int ii = 0; ii < N_ELEMENTS; ii++) {
        	index = getIntEntropy(N_ALPHABET);
        	chArray[ii] = alphabet.charAt(index);
        }
    	
    	// Write the entire chArray to file all at once.
        File file = new File(FilePath);
        FileWriter fwr = new FileWriter(file);
        fwr.write(chArray, 0, N_ELEMENTS);
        fwr.close();
        
        // Read back and compare.
        errorCounter += doRdAllCmp("after write(array)", chArray, N_ELEMENTS, FilePath);
        
    	// Write the entire chArray to file, one by one.
    	fwr = new FileWriter(FilePath);
        for (int ii = 0; ii < N_ELEMENTS; ii++) {
        	fwr.write((int) chArray[ii]);
        }
        fwr.close();
        
        // Read back and compare.
        errorCounter += doRdAllCmp("after writing one int at a time", chArray, N_ELEMENTS, FilePath);
       
    	// Write a subset of chArray to a file.
    	fwr = new FileWriter(FilePath);
        fwr.write(chArray, offset, length);
        fwr.close();
    	char charSubArray[] = new char[length];  	
        for (int ii = 0; ii < length; ii++) {
        	charSubArray[ii] = chArray[offset + ii];
        }
        
        // Read back and compare.
        errorCounter += doRdAllCmp("after writing a subset", charSubArray, length, FilePath);
        
        // Whole string
        String str1 = "Mary had a little lamb whose fleece was white as snow.";
        int strlen =  str1.length();
    	fwr = new FileWriter(FilePath);
		fwr.write(str1, 0, strlen);
		fwr.close();
		byte [] bytes = new byte[strlen];
		for (int ii = 0; ii < strlen; ii++)
			bytes[ii] = '@';
		FileInputStream fis = new FileInputStream(FilePath);
		fis.read(bytes);
		String str2 = new String(bytes);
		if (!str2.equals(str1)) {
			System.out.printf("*** ERROR, str1=%s, str2=%s\n", str1, str2);
			errorCounter += 1;
		} else {
			System.out.printf("\n========== Whole string output and input is successful.\n");
		}
       
        assert(errorCounter == 0);
    }
}
