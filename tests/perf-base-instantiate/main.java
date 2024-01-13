import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.time.*;
import javax.crypto.*;

public class main {

    final static int MAX_LOOPS = 1000000;

    public static void runner(byte [] bbuf, char [] cbuf) throws Exception {
   		ByteArrayInputStream a1 = new  ByteArrayInputStream(bbuf);
        BufferedInputStream a2 = new BufferedInputStream(a1);
        String a3 = "abc";
        StringWriter a4 = new StringWriter();
        CharArrayReader a5 = new CharArrayReader(cbuf);
        StreamTokenizer a6 = new StreamTokenizer(a5);
        BigDecimal a7 = new BigDecimal(42.0);
        BigInteger a8 = new BigInteger("42");
        DateFormat a9 = DateFormat.getDateInstance();
        char a10 = CharacterIterator.DONE;
        Collator a11 = Collator.getInstance();
        CollationKey[] a12 = new CollationKey[42];
        Locale a13 = Locale.ENGLISH;
        NumberFormat a14 = NumberFormat.getInstance(a13);
        Month a15 = Month.APRIL;
        ZonedDateTime a16 = ZonedDateTime.now();
        Cipher a17 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        CipherInputStream a18 = new CipherInputStream(a2, a17);
        BitSet a19 = new BitSet();
        Random a20 = new Random();
        
    }
    
    public static void main(String args[]) throws Exception {

        System.out.println("Import and instantiate performance test");
    	System.out.print("Loop count: ");
    	System.out.println(MAX_LOOPS);

    	byte [] bbuf = new byte [1024];
    	char [] cbuf = new char[1024];
    	for (int ndx = 0; ndx < 1024; ++ndx ) {
    		bbuf[ndx] = 0x20;
    		cbuf[ndx] = 'A';
    	}
    	
    	long t1 = System.currentTimeMillis();
        for (int ndx = 0; ndx < MAX_LOOPS; ++ndx) {
            runner(bbuf, cbuf);
        }
        long t2 = System.currentTimeMillis();
    	double secs_overall = (double) (t2 - t1) / 1000.0;
    	System.out.print("Overall elapsed time = "); 
    	System.out.print(secs_overall); 
    	System.out.println(" seconds"); 
        
    }

}

