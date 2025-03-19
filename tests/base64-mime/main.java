import java.util.Base64;

public class main {

    private static int checker(String label, String expected, String observed) {
        String expSubset = expected.substring(0, expected.length());
        String obsSubset = observed.substring(0, expected.length());
        if (expSubset.equals(obsSubset)) {
            System.out.printf("checker ok, %s: expected(%s) = observed(%s)\n", label, expSubset, obsSubset);
            return 0;
        }
        System.out.printf("checker *** ERROR, %s: expected(%s) != observed(%s)\n", label, expSubset, obsSubset);
        return 1;
    }

    public static void main(String args[]) {
    
    
        int errorCounter = 0;
        String wstr;
        String[] mary = {
            "Mary had a little lamb,",
            "Its fleece was white as snow (or black as coal).",
            "And everywhere that Mary went,",
            "The lamb was sure to go.",
            "He followed her to school one day,",
            "That was against the rule.",
            "It made the children laugh and play",
            "To see a lamb at school."
        };
        String original = "[";
        for (String line : mary) {
            original = String.format("%s %s", original, line);
        }
        original = original.concat("]");
        byte[] bbsrc = original.getBytes();
        Base64.Decoder de = Base64.getMimeDecoder();

        System.out.println("MIME base64 with padding");
        Base64.Encoder be = Base64.getMimeEncoder();
        String expected = "WyBNYXJ5IGhhZCBhIGxpdHRsZSBsYW1iLCBJdHMg";
        
        byte[] bbdst = be.encode(bbsrc);
        wstr = new String(bbdst);
        errorCounter += checker("encode #1", expected, wstr);
        
        int size = be.encode(bbsrc, bbdst);
        wstr = new String(bbdst);
        errorCounter += checker("encode #2", expected, wstr);
        
        wstr = be.encodeToString(bbsrc);
        errorCounter += checker("encode #3 (to string)", expected, wstr);

        byte[] bbsrc2 = bbdst;
        
        bbdst = de.decode(bbsrc2);
        wstr = new String(bbdst);
        errorCounter += checker("decode #1", original, wstr);

        size = de.decode(bbsrc2, bbdst);
        wstr = new String(bbdst);
        errorCounter += checker("decode #2", original.substring(0, 30), wstr);
        
        wstr = new String(bbsrc2);
        bbdst = de.decode(wstr);
        errorCounter += checker("decode #3 (from string)", original.substring(0, 30), new String(bbdst));
        
        System.out.println("MIME base64 without padding");
        be = Base64.getMimeEncoder().withoutPadding();
        expected = "WyBNYXJ5IGhhZCBhIGxpdHRsZSBsYW1iLCBJdHMg";
        
        bbdst = be.encode(bbsrc);
        wstr = new String(bbdst);
        errorCounter += checker("encode #1", expected, wstr);
        
        size = be.encode(bbsrc, bbdst);
        wstr = new String(bbdst);
        errorCounter += checker("encode #2", expected, wstr);
        
        wstr = be.encodeToString(bbsrc);
        errorCounter += checker("encode #3 (to string)", expected, wstr);

        bbsrc2 = bbdst;
        
        bbdst = de.decode(bbsrc2);
        wstr = new String(bbdst);
        errorCounter += checker("decode #1", original, wstr);

        size = de.decode(bbsrc2, bbdst);
        wstr = new String(bbdst);
        errorCounter += checker("decode #2", original.substring(0, 30), wstr);
        
        wstr = new String(bbsrc2);
        bbdst = de.decode(wstr);
        errorCounter += checker("decode #3 (from string)", original.substring(0, 30), new String(bbdst));
        
        assert errorCounter == 0;
        System.out.println("Success!");

    }
}
