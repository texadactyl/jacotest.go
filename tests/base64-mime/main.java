import java.util.Base64;

public class main {

    public static void main(String args[]) {
    
        System.out.println("\nMIME base64 with padding");
    
        int errorCount = 0;
        String observed;
        String src = "Mary had a little lamb whose fleece was white as snow.";
        System.out.println(src);
        byte[] bbsrc = src.getBytes();
        
        // Set up encoder and decoder.
        Base64.Decoder de = Base64.getMimeDecoder();
        Base64.Encoder be = Base64.getMimeEncoder();
        
        // I expect the encoded output to be this:
        String expected = "TWFyeSBoYWQgYSBsaXR0bGUgbGFtYiB3aG9zZSBmbGVlY2Ugd2FzIHdoaXRlIGFzIHNub3cu";
        
        // Return the encoded source string and compare to the expected output.
        byte[] bbout = be.encode(bbsrc);
        observed = new String(bbout);
        errorCount += Checkers.checker("encode #1 returned bytes", expected, observed);
        
        // Put the encoded source string in the second argument.
        // Compare to the expected output.
        int size = be.encode(bbsrc, bbout);
        observed = new String(bbout);
        errorCount += Checkers.checker("encode #2 set 2nd argument", expected, observed);
        errorCount += Checkers.checker("encode #2 returned size", 72, size);
        
        // Encode to a string.
        observed = be.encodeToString(bbsrc);
        errorCount += Checkers.checker("encode #3 (encodeToString)", expected, observed);

        // Make a second copy of the encoded byte array.
        byte[] bbsrc2 = new byte[bbout.length];
        System.arraycopy(bbout, 0, bbsrc2, 0, bbout.length);
        
        // Return the decoded byte array and compare to the expected output.
        bbout = de.decode(bbsrc2);
        observed = new String(bbout);
        errorCount += Checkers.checker("decode #1", src, observed);

        // Put the decoded byte array in the second argument.
        // Compare to the expected output.
        size = de.decode(bbsrc2, bbout);
        observed = new String(bbout);
        errorCount += Checkers.checker("decode #2", src, observed);
        errorCount += Checkers.checker("decode #2 returned size", 54, size);
        
        observed = new String(bbsrc2);
        bbout = de.decode(observed);
        errorCount += Checkers.checker("decode #3 (from string)", src, new String(bbout));
        
        System.out.println("\nMIME base64 without padding");
        
        be = Base64.getMimeEncoder().withoutPadding();
        expected = "TWFyeSBoYWQgYSBsaXR0bGUgbGFtYiB3aG9zZSBmbGVlY2Ugd2FzIHdoaXRlIGFzIHNub3cu";
        
        bbout = be.encode(bbsrc);
        observed = new String(bbout);
        errorCount += Checkers.checker("encode #1", expected, observed);
        
        size = be.encode(bbsrc, bbout);
        observed = new String(bbout);
        errorCount += Checkers.checker("encode #2", expected, observed);
        
        observed = be.encodeToString(bbsrc);
        errorCount += Checkers.checker("encode #3", expected, observed);

        bbsrc2 = new byte[bbout.length];
        System.arraycopy(bbout, 0, bbsrc2, 0, bbout.length);
        
        bbout = de.decode(bbsrc2);
        observed = new String(bbout);
        errorCount += Checkers.checker("decode #1", src, observed);

        size = de.decode(bbsrc2, bbout);
        observed = new String(bbout);
        errorCount += Checkers.checker("decode #2", src, observed);
        
        observed = new String(bbsrc2);
        bbout = de.decode(observed);
        errorCount += Checkers.checker("decode #3", src, new String(bbout));
        
        Checkers.theEnd(errorCount);

    }
}
