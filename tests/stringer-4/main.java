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
        System.out.printf("*** ERROR :: %s, expected=%v, observed=%v\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) throws Exception {
    
    	System.out.println("String manipulation and member functions - Part 4");
        String baseline = "abcdefghijklmnopqrstuvwxyz";
        String identical = baseline.toString();
        String upperCase = baseline.toUpperCase();
        int lenBaseline = baseline.length();
        String repeatedSubstring = "abcdefabcdefabcdef";
        String presplitString = "abc,def,ghi,jkl,mno,pqr,stu,vwx,yz$";
        String ws;
        String wsArray [];
        System.out.printf("baseline: %s\n", baseline);
        int errorCount = 0;
        int iresult;
        boolean zresult;

        // ===== boolean regionMatches(                    int toffset, String other, int ooffset, int len)
        // ===== boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)
        
        zresult = baseline.regionMatches(0, identical, 0, lenBaseline);
        errorCount += cmpExpToObs("baseline.regionMatches(0, identical, 0, lenBaseline)", true, zresult); 
        
        zresult = baseline.regionMatches(3, identical, 3, lenBaseline - 3);
        errorCount += cmpExpToObs("baseline.regionMatches(3, identical, 3, lenBaseline - 3)", true, zresult); 
        
        zresult = baseline.regionMatches(0, identical, 1, 8);
        errorCount += cmpExpToObs("baseline.regionMatches(0, identical, 1, 8)", false, zresult); 
        
        zresult = baseline.regionMatches(0, upperCase, 0, 8);
        errorCount += cmpExpToObs("baseline.regionMatches(0, upperCase, 0, 8)", false, zresult); 
        
        zresult = baseline.regionMatches(false, 0, identical, 0, lenBaseline);
        errorCount += cmpExpToObs("baseline.regionMatches(0, identical, 0, lenBaseline)", true, zresult); 
        
        zresult = baseline.regionMatches(false, 3, identical, 3, lenBaseline - 3);
        errorCount += cmpExpToObs("baseline.regionMatches(3, identical, 3, lenBaseline - 3)", true, zresult); 
        
        zresult = baseline.regionMatches(false, 0, identical, 1, 8);
        errorCount += cmpExpToObs("baseline.regionMatches(0, identical, 1, 8)", false, zresult); 
        
        zresult = baseline.regionMatches(true, 0, identical, 0, lenBaseline);
        errorCount += cmpExpToObs("baseline.regionMatches(0, identical, 0, lenBaseline)", true, zresult); 
        
        zresult = baseline.regionMatches(true, 3, identical, 3, lenBaseline - 3);
        errorCount += cmpExpToObs("baseline.regionMatches(3, identical, 3, lenBaseline - 3)", true, zresult); 
        
        zresult = baseline.regionMatches(true, 0, identical, 1, 8);
        errorCount += cmpExpToObs("baseline.regionMatches(0, identical, 1, 8)", false, zresult); 
        
        zresult = baseline.regionMatches(true, 0, upperCase, 0, lenBaseline);
        errorCount += cmpExpToObs("baseline.regionMatches(0, identical, 0, lenBaseline)", true, zresult); 
        
        zresult = baseline.regionMatches(true, 3, upperCase, 3, lenBaseline - 3);
        errorCount += cmpExpToObs("baseline.regionMatches(3, identical, 3, lenBaseline - 3)", true, zresult); 
        
        zresult = baseline.regionMatches(true, 0, upperCase, 1, 8);
        errorCount += cmpExpToObs("baseline.regionMatches(0, identical, 1, 8)", false, zresult); 
        
        // ===== isBlank, isEmpty
        
        zresult = baseline.isBlank();
        errorCount += cmpExpToObs("baseline.isBlank()", false, zresult); 
        zresult = baseline.isEmpty();
        errorCount += cmpExpToObs("baseline.isEmpty()", false, zresult); 
        ws = "  \t\t  \r\r  \n\n  ";
        zresult = ws.isBlank();
        errorCount += cmpExpToObs("ws.isBlank()", true, zresult); 
        zresult = ws.isEmpty();
        errorCount += cmpExpToObs("ws.isEmpty()", false, zresult); 
        
        // ===== lastIndexOf(character)
        
        ws = "abcdefabcdef";
        iresult = ws.lastIndexOf('a');
        errorCount += cmpExpToObs("ws.lastIndexOf('a')", 6, iresult);
        iresult = ws.lastIndexOf('b', 5);
        errorCount += cmpExpToObs("ws.lastIndexOf('b', 5)", 1, iresult);
        
        // ===== lastIndexOf(string)
        
        ws = "abcdefabcdef";
        iresult = ws.lastIndexOf("abc");
        errorCount += cmpExpToObs("ws.lastIndexOf(\"abc\")", 6, iresult);
        iresult = ws.lastIndexOf("bcd", 5);
        errorCount += cmpExpToObs("ws.lastIndexOf(\"bcd\", 5)", 1, iresult);
        
        // ===== replaceAll(stringRegex, stringReplaceSource)
        
        ws = repeatedSubstring.replaceAll("bcd", "123");
        zresult = ws.equals("a123efa123efa123ef");
        errorCount += cmpExpToObs("repeatedSubstring.replaceAll(\"bcd\", \"123\")", true, zresult);

        // replaceFirst(stringRegex, stringReplaceSource)
        
        ws = repeatedSubstring.replaceFirst("bcd", "123");
        zresult = ws.equals("a123efabcdefabcdef");
        errorCount += cmpExpToObs("repeatedSubstring.replaceFirst(\"bcd\", \"123\")", true, zresult);
        
        // String[] split(String regex) 
        
        wsArray = presplitString.split(",");
        iresult = wsArray.length;
        errorCount += cmpExpToObs("presplitString.split(\",\")", 9, iresult);
        errorCount += cmpExpToObs("wsArray[1] == \"def\"", "def", wsArray[1]);
        
        wsArray = presplitString.split(",", 0);
        iresult = wsArray.length;
        errorCount += cmpExpToObs("presplitString.split(\",\", 0)", 9, iresult);
        errorCount += cmpExpToObs("wsArray[1] == \"def\"", "def", wsArray[1]);
        
        wsArray = presplitString.split(",", -42);
        iresult = wsArray.length;
        errorCount += cmpExpToObs("presplitString.split(\",\", -42)", 9, iresult);
        errorCount += cmpExpToObs("wsArray[1] == \"def\"", "def", wsArray[1]);
        
        wsArray = presplitString.split(",", 3);
        iresult = wsArray.length;
        errorCount += cmpExpToObs("presplitString.split(\",\", +3)", 3, iresult);
        errorCount += cmpExpToObs("wsArray[1] == \"def\"", "def", wsArray[1]);
        errorCount += cmpExpToObs("wsArray[2] == \"ghi,jkl,mno,pqr,stu,vwx,yz$\"", "ghi,jkl,mno,pqr,stu,vwx,yz$", wsArray[2]);
        
        // ===== The End
        
		assert(errorCount == 0);
    }
}

