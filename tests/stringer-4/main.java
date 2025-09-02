public class main {

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

        zresult = baseline.regionMatches(0, identical, 0, lenBaseline);
        errorCount += Checkers.checker("baseline.regionMatches(0, identical, 0, lenBaseline)", true, zresult); 
        
        zresult = baseline.regionMatches(3, identical, 3, lenBaseline - 3);
        errorCount += Checkers.checker("baseline.regionMatches(3, identical, 3, lenBaseline - 3)", true, zresult); 
        
        zresult = baseline.regionMatches(0, identical, 1, 8);
        errorCount += Checkers.checker("baseline.regionMatches(0, identical, 1, 8)", false, zresult); 
        
        zresult = baseline.regionMatches(0, upperCase, 0, 8);
        errorCount += Checkers.checker("baseline.regionMatches(0, upperCase, 0, 8)", false, zresult); 
        
        zresult = baseline.regionMatches(false, 0, identical, 0, lenBaseline);
        errorCount += Checkers.checker("baseline.regionMatches(0, identical, 0, lenBaseline)", true, zresult); 
        
        zresult = baseline.regionMatches(false, 3, identical, 3, lenBaseline - 3);
        errorCount += Checkers.checker("baseline.regionMatches(3, identical, 3, lenBaseline - 3)", true, zresult); 
        
        zresult = baseline.regionMatches(false, 0, identical, 1, 8);
        errorCount += Checkers.checker("baseline.regionMatches(0, identical, 1, 8)", false, zresult); 
        
        zresult = baseline.regionMatches(true, 0, identical, 0, lenBaseline);
        errorCount += Checkers.checker("baseline.regionMatches(0, identical, 0, lenBaseline)", true, zresult); 
        
        zresult = baseline.regionMatches(true, 3, identical, 3, lenBaseline - 3);
        errorCount += Checkers.checker("baseline.regionMatches(3, identical, 3, lenBaseline - 3)", true, zresult); 
        
        zresult = baseline.regionMatches(true, 0, identical, 1, 8);
        errorCount += Checkers.checker("baseline.regionMatches(0, identical, 1, 8)", false, zresult); 
        
        zresult = baseline.regionMatches(true, 0, upperCase, 0, lenBaseline);
        errorCount += Checkers.checker("baseline.regionMatches(0, identical, 0, lenBaseline)", true, zresult); 
        
        zresult = baseline.regionMatches(true, 3, upperCase, 3, lenBaseline - 3);
        errorCount += Checkers.checker("baseline.regionMatches(3, identical, 3, lenBaseline - 3)", true, zresult); 
        
        zresult = baseline.regionMatches(true, 0, upperCase, 1, 8);
        errorCount += Checkers.checker("baseline.regionMatches(0, identical, 1, 8)", false, zresult); 
        
        // ===== isBlank, isEmpty
        
        zresult = baseline.isBlank();
        errorCount += Checkers.checker("baseline.isBlank()", false, zresult); 
        zresult = baseline.isEmpty();
        errorCount += Checkers.checker("baseline.isEmpty()", false, zresult); 
        ws = "  \t\t  \r\r  \n\n  ";
        zresult = ws.isBlank();
        errorCount += Checkers.checker("ws.isBlank()", true, zresult); 
        zresult = ws.isEmpty();
        errorCount += Checkers.checker("ws.isEmpty()", false, zresult); 
        
        // ===== lastIndexOf(character)
        
        ws = "abcdefabcdef";
        iresult = ws.lastIndexOf('a');
        errorCount += Checkers.checker("ws.lastIndexOf('a')", 6, iresult);
        iresult = ws.lastIndexOf('b', 5);
        errorCount += Checkers.checker("ws.lastIndexOf('b', 5)", 1, iresult);
        
        // ===== lastIndexOf(string)
        
        ws = "abcdefabcdef";
        iresult = ws.lastIndexOf("abc");
        errorCount += Checkers.checker("ws.lastIndexOf(\"abc\")", 6, iresult);
        iresult = ws.lastIndexOf("bcd", 5);
        errorCount += Checkers.checker("ws.lastIndexOf(\"bcd\", 5)", 1, iresult);
        
        // ===== replaceAll(stringRegex, stringReplaceSource)
        
        ws = repeatedSubstring.replaceAll("bcd", "123");
        zresult = ws.equals("a123efa123efa123ef");
        errorCount += Checkers.checker("repeatedSubstring.replaceAll(\"bcd\", \"123\")", true, zresult);

        // replaceFirst(stringRegex, stringReplaceSource)
        
        ws = repeatedSubstring.replaceFirst("bcd", "123");
        zresult = ws.equals("a123efabcdefabcdef");
        errorCount += Checkers.checker("repeatedSubstring.replaceFirst(\"bcd\", \"123\")", true, zresult);
        
        // String[] split(String regex) 
        
        wsArray = presplitString.split(",");
        iresult = wsArray.length;
        errorCount += Checkers.checker("presplitString.split(\",\")", 9, iresult);
        errorCount += Checkers.checker("wsArray[1] == \"def\"", "def", wsArray[1]);
        
        wsArray = presplitString.split(",", 0);
        iresult = wsArray.length;
        errorCount += Checkers.checker("presplitString.split(\",\", 0)", 9, iresult);
        errorCount += Checkers.checker("wsArray[1] == \"def\"", "def", wsArray[1]);
        
        wsArray = presplitString.split(",", -42);
        iresult = wsArray.length;
        errorCount += Checkers.checker("presplitString.split(\",\", -42)", 9, iresult);
        errorCount += Checkers.checker("wsArray[1] == \"def\"", "def", wsArray[1]);
        
        wsArray = presplitString.split(",", 3);
        iresult = wsArray.length;
        errorCount += Checkers.checker("presplitString.split(\",\", +3)", 3, iresult);
        errorCount += Checkers.checker("wsArray[1] == \"def\"", "def", wsArray[1]);
        errorCount += Checkers.checker("wsArray[2] == \"ghi,jkl,mno,pqr,stu,vwx,yz$\"", "ghi,jkl,mno,pqr,stu,vwx,yz$", wsArray[2]);
        
        // ===== The End
        
		Checkers.theEnd(errorCount);
    }
}

