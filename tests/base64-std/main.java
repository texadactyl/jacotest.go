import java.util.Base64;

public class main {

    public static void main(String args[]) {
    
    
        int errorCount = 0;
        String wstr;
    
        String original = "Mary had a little lamb whose fleece was white as snow!!";
        byte[] bbsrc = original.getBytes();
        Base64.Decoder de = Base64.getDecoder();

        System.out.println("\nStandard base64 with padding");
        Base64.Encoder be = Base64.getEncoder();
        String encoded = "TWFyeSBoYWQgYSBsaXR0bGUgbGFtYiB3aG9zZSBmbGVlY2Ugd2FzIHdoaXRlIGFzIHNub3chIQ==";
        
        byte[] bbdst = be.encode(bbsrc);
        wstr = new String(bbdst);
        errorCount += Checkers.checker("encode #1", encoded, wstr);
        
        int size = be.encode(bbsrc, bbdst);
        wstr = new String(bbdst);
        errorCount += Checkers.checker("encode #2", encoded, wstr);
        
        wstr = be.encodeToString(bbsrc);
        errorCount += Checkers.checker("encode #3 (to string)", encoded, wstr);

        byte[] bbsrc2 = bbdst;
        
        bbdst = de.decode(bbsrc2);
        wstr = new String(bbdst);
        errorCount += Checkers.checker("decode #1", original, wstr);

        size = de.decode(bbsrc2, bbdst);
        wstr = new String(bbdst);
        errorCount += Checkers.checker("decode #2", original, wstr);
        
        wstr = new String(bbsrc2);
        bbdst = de.decode(wstr);
        errorCount += Checkers.checker("decode #3 (from string)", original, new String(bbdst));
        
        //----------------------------------------------------------------------------------------------------------
        
        System.out.println("\nStandard base64 without padding");   
        be = Base64.getEncoder().withoutPadding();
        encoded = "TWFyeSBoYWQgYSBsaXR0bGUgbGFtYiB3aG9zZSBmbGVlY2Ugd2FzIHdoaXRlIGFzIHNub3chIQ";
        
        bbdst = be.encode(bbsrc);
        wstr = new String(bbdst);
        errorCount += Checkers.checker("encode #1", encoded, wstr);
        
        size = be.encode(bbsrc, bbdst);
        wstr = new String(bbdst);
        errorCount += Checkers.checker("encode #2", encoded, wstr);
        
        wstr = be.encodeToString(bbsrc);
        errorCount += Checkers.checker("encode #3 (to string)", encoded, wstr);

        bbsrc2 = bbdst;
        
        bbdst = de.decode(bbsrc2);
        wstr = new String(bbdst);
        errorCount += Checkers.checker("decode #1", original, wstr);

        size = de.decode(bbsrc2, bbdst);
        wstr = new String(bbdst);
        errorCount += Checkers.checker("decode #2", original, wstr);
        
        wstr = new String(bbsrc2);
        bbdst = de.decode(wstr);
        errorCount += Checkers.checker("decode #3 (from string)", original, new String(bbdst));
        
        Checkers.theEnd(errorCount);

    }
}
