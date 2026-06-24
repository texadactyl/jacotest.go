import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

public class main {

    final static int MAX_LOOPS = 10000;

    public static void runner(byte [] bbuf, char [] cbuf) throws Exception {
   		ByteArrayInputStream a1 = new  ByteArrayInputStream(bbuf);
        BufferedInputStream a2 = new BufferedInputStream(a1);
        String a3 = "abc";
  		ByteArrayOutputStream b1 = new  ByteArrayOutputStream();
        BufferedOutputStream b2 = new BufferedOutputStream(b1);
        b2.write(a3.getBytes(), 0, a3.length());
        BigInteger a8 = new BigInteger("42");
        BigDecimal a7 = new BigDecimal(a8);
        Locale a13 = Locale.getDefault();
        Cipher a17 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        CipherInputStream a18 = new CipherInputStream(a2, a17);
        BitSet a19 = new BitSet(64);
        for (int ndx = 0; ndx < 64; ++ndx) {
        	a19.set(ndx);
        }
        byte[] bb = SecureRandom.getSeed(16);
        SecureRandom a20 = new SecureRandom(bb);
        
    }
    
    public static void main(String args[]) throws Exception {

        System.out.println("Instantiate performance test");
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
    	
    	Checkers.theEnd(0);
        
    }

}

