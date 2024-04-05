import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class main {

	private static int readTheLines(String label, BufferedReader br) throws IOException {
		System.out.printf("\n===== %s\n", label);
		
        String nextLine = br.readLine();
        int lineCounter = 0;
        String lastLine = null;
        
        while (nextLine != null) {
        	lineCounter += 1;
        	lastLine = nextLine;
            System.out.println(lastLine);
            nextLine = br.readLine();
        }
        
        if (lineCounter != 5) {
        	System.out.printf("readTheLines: *** ERROR, line counter, expected: 5, observed: %d\n", lineCounter);
        	return 1;
        }
        
        if (!lastLine.equals("Line 5")) {
        	System.out.printf("readTheLines: *** ERROR, last line string, expected: 'Line 5', observed: '%s'\n", lastLine);
        	return 1;
        }
        
        System.out.println("readTheLines: ok");
        return 0;
      
	}

	private static int readTheInts(String label, BufferedReader br) throws IOException {
		System.out.printf("\n===== %s\n", label);
		
        int nextInt = br.read();
        int intCounter = 0;
        int lastInt = -1;
        
        while (nextInt != -1) {
        	intCounter += 1;
        	lastInt = nextInt;
            //System.out.println(lastInt);
            nextInt = br.read();
        }
        
        if (intCounter != 58) {
        	System.out.printf("readTheInts: *** ERROR, Int counter, expected: 58, observed: %d\n", intCounter);
        	return 1;
        }
        
        if (lastInt != '\n') {
        	System.out.printf("readTheInts: *** ERROR, last Int string, expected: 5, observed: '%d'\n", lastInt);
        	return 1;
        }
        
        System.out.println("readTheInts: ok");
        return 0;
      
	}

	private static char readTheChars(String label, BufferedReader br) throws IOException {
		System.out.printf("\n===== %s\n", label);
		
        if (!br.ready()) {
         	System.out.println("readTheChars: *** ERROR, BufferedReader should be ready");
        	return 1;
        }
       
		char[] cb = new char[8192];
		for (int ii = 0; ii < cb.length; ii++) {
			cb[ii] = (char) (ii % 256);
		}
		
        int nchars = br.read(cb, 0, cb.length);
        
        if (nchars != 58) {
        	System.out.printf("readTheChars: *** ERROR, char buffer size, expected: 58, observed: %d\n", nchars);
        	return 1;
        }
        
        char lastChar = cb[nchars - 1];
        if (lastChar != '\n') {
        	System.out.printf("readTheChars: *** ERROR, last char, expected: \n, observed: '%c'\n", lastChar);
        	return 1;
        }
        
        nchars = br.read(cb, 0, cb.length);
        if (nchars != -1) {
         	System.out.println("readTheChars: *** ERROR, failed to detect EOF");
        	return 1;
        }
       
        nchars = br.read(cb, 0, cb.length);
        if (nchars != -1) {
         	System.out.println("readTheChars: *** ERROR, failed to detect EOF");
        	return 1;
        }
        
        if (br.ready()) {
         	System.out.println("readTheChars: *** ERROR, BufferedReader closed; should not be ready");
        	return 1;
        }
       
        System.out.println("readTheChars: ok");
        return 0;
      
	}

    public static void main(String[] args) throws FileNotFoundException, IOException {
    
    	int errorCounter = 0;
    	String FilePath = "./data.txt";
    
        File file = new File(FilePath);
        FileReader fr = new FileReader(file);
        
        BufferedReader br = new BufferedReader(fr);
        errorCounter += readTheLines("1. readTheLines", br);
        br.close();
        
        fr = new FileReader(FilePath);
        br = new BufferedReader(fr);
        errorCounter += readTheInts("2. readTheInts", br);
        
        fr = new FileReader(FilePath);
        br = new BufferedReader(fr);
        errorCounter += readTheChars("3. readTheChars", br);
        
        assert(errorCounter == 0);
    }
}
