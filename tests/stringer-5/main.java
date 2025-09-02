public class main {

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
        errorCount += Checkers.checker("junkline.strip() length=26", 26, iresult); 
        zresult = ws.equals(baseline);
        errorCount += Checkers.checker("junkline.strip() equals baseline", true, zresult); 
                
        // ===== stripLeading()
        
        ws = junkline.stripLeading();
        iresult = ws.length();
        errorCount += Checkers.checker("junkline.strip() length=31", 31, iresult); 
        zresult = ws.equals("abcdefghijklmnopqrstuvwxyz \t \t ");
        errorCount += Checkers.checker("junkline.strip() equals abcdefghijklmnopqrstuvwxyz TAB TAB ", true, zresult); 
                
        // ===== stripTrailing()
        
        ws = junkline.stripTrailing();
        iresult = ws.length();
        errorCount += Checkers.checker("junkline.strip() length=31", 31, iresult); 
        zresult = ws.equals(" \t \t abcdefghijklmnopqrstuvwxyz");
        errorCount += Checkers.checker("junkline.strip() equals  TAB TAB abcdefghijklmnopqrstuvwxyz", true, zresult); 
        
        Checkers.theEnd(errorCount);
    }
}

