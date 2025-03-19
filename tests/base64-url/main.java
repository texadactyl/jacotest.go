import java.util.Base64;

public class main {

    private static int checker(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("checker ok, %s: expected(%s) = observed(%s)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("checker *** ERROR, %s: expected(%s) != observed(%s)\n", label, expected, observed);
        return 1;
    }

    public static void main(String args[]) {
    
    
        int errorCounter = 0;
        String wstr;
    
        String original = "Mary%20had%2Da%3Clittle%3Elamb%21whose%3Efleece was white as snow%3F%5B!%5D";
        byte[] bbsrc = original.getBytes();
        Base64.Decoder de = Base64.getUrlDecoder();

        System.out.println("URL base64 with padding");
        Base64.Encoder be = Base64.getUrlEncoder();
        String encoded = "TWFyeSUyMGhhZCUyRGElM0NsaXR0bGUlM0VsYW1iJTIxd2hvc2UlM0VmbGVlY2Ugd2FzIHdoaXRlIGFzIHNub3clM0YlNUIhJTVE";
        
        byte[] bbdst = be.encode(bbsrc);
        wstr = new String(bbdst);
        errorCounter += checker("encode #1", encoded, wstr);
        
        int size = be.encode(bbsrc, bbdst);
        wstr = new String(bbdst);
        errorCounter += checker("encode #2", encoded, wstr);
        
        wstr = be.encodeToString(bbsrc);
        errorCounter += checker("encode #3 (to string)", encoded, wstr);

        byte[] bbsrc2 = bbdst;
        
        bbdst = de.decode(bbsrc2);
        wstr = new String(bbdst);
        errorCounter += checker("decode #1", original, wstr);

        size = de.decode(bbsrc2, bbdst);
        wstr = new String(bbdst);
        errorCounter += checker("decode #2", original, wstr);
        
        wstr = new String(bbsrc2);
        bbdst = de.decode(wstr);
        errorCounter += checker("decode #3 (from string)", original, new String(bbdst));
        
        //----------------------------------------------------------------------------------------------------------
        
        System.out.println("URL base64 without padding");   
        be = Base64.getUrlEncoder().withoutPadding();
        encoded = "TWFyeSUyMGhhZCUyRGElM0NsaXR0bGUlM0VsYW1iJTIxd2hvc2UlM0VmbGVlY2Ugd2FzIHdoaXRlIGFzIHNub3clM0YlNUIhJTVE";
        
        bbdst = be.encode(bbsrc);
        wstr = new String(bbdst);
        errorCounter += checker("encode #1", encoded, wstr);
        
        size = be.encode(bbsrc, bbdst);
        wstr = new String(bbdst);
        errorCounter += checker("encode #2", encoded, wstr);
        
        wstr = be.encodeToString(bbsrc);
        errorCounter += checker("encode #3 (to string)", encoded, wstr);

        bbsrc2 = bbdst;
        
        bbdst = de.decode(bbsrc2);
        wstr = new String(bbdst);
        errorCounter += checker("decode #1", original, wstr);

        size = de.decode(bbsrc2, bbdst);
        wstr = new String(bbdst);
        errorCounter += checker("decode #2", original, wstr);
        
        wstr = new String(bbsrc2);
        bbdst = de.decode(wstr);
        errorCounter += checker("decode #3 (from string)", original, new String(bbdst));
        
        assert errorCounter == 0;
        System.out.println("Success!");

    }
}
