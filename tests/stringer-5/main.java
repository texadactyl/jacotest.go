public class main {

    private static int cmpExpToObs(String label, String expected, String observed) {
        if (observed.equals(expected)) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=%s, observed=%s\n", label, expected, observed);
        return 1;
    }

    private static int cmpExpToObs(String label, int expected, int observed) {
        if (observed == expected) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=%d, observed=%d\n", label, expected, observed);
        return 1;
    }

    private static int cmpExpToObs(String label, boolean expected, boolean observed) {
        if (observed == expected) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=%B, observed=%B\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) throws Exception {
    
    	System.out.println("String manipulation and member functions - Part 5");
        String baseline = "abcdefghijklmnopqrstuvwxyz";
        String junkline = " \t \t abcdefghijklmnopqrstuvwxyz \t \t ";
        int lenBaseline = baseline.length();
        String ws;
        System.out.printf("baseline: %s\n", baseline);
        int errorCount = 0;
        int iresult;
        boolean zresult;

        // ===== strip()
        
        ws = junkline.strip();
        iresult = ws.length();
        errorCount += cmpExpToObs("junkline.strip() length=26", 26, iresult); 
        zresult = ws.equals(baseline);
        errorCount += cmpExpToObs("junkline.strip() equals baseline", true, zresult); 
                
        // ===== stripLeading()
        
        ws = junkline.stripLeading();
        iresult = ws.length();
        errorCount += cmpExpToObs("junkline.strip() length=31", 31, iresult); 
        zresult = ws.equals("abcdefghijklmnopqrstuvwxyz \t \t ");
        errorCount += cmpExpToObs("junkline.strip() equals abcdefghijklmnopqrstuvwxyz TAB TAB ", true, zresult); 
                
        // ===== stripTrailing()
        
        ws = junkline.stripTrailing();
        iresult = ws.length();
        errorCount += cmpExpToObs("junkline.strip() length=31", 31, iresult); 
        zresult = ws.equals(" \t \t abcdefghijklmnopqrstuvwxyz");
        errorCount += cmpExpToObs("junkline.strip() equals  TAB TAB abcdefghijklmnopqrstuvwxyz", true, zresult); 
        
        // ===== The End
        
		assert(errorCount == 0);
    }
}

