public class main {

    public static void main(String[] args) throws Exception {
    
        System.out.println("String manipulation and member functions - Part 2");

        int errorCount = 0;
        Helpers hh = new Helpers();

        String mary = "Mary had a little lamb,";
        String ss1 = mary;
        hh.printLabeledString("ss1, the initial String: ", ss1);
        
        System.out.println("sss = 21-character substring of ss1");
        String sss = ss1.substring(1, 22);
        errorCount += Checkers.checker("sss.length() = 21", 21, sss.length());

        System.out.println("Instantiate String from a byte []");
        byte [] bb = ss1.getBytes();
        String ss2 = new String(bb);
        errorCount += Checkers.checker("ss2.length() = 23", 23, ss2.length());
        errorCount += Checkers.checker("ss2 = ss1", true, ss2.equals(ss1));

        System.out.println("Instantiate String from a subset of a byte []");
        ss2 = new String(bb, 0, bb.length);
        errorCount += Checkers.checker("ss2.length() = 23", 23, ss2.length());
        errorCount += Checkers.checker("ss2 = ss1", true, ss2.equals(ss1));
        
        System.out.println("Instantiate String from a char []");
        char [] cc = ss1.toCharArray();
        ss2 = new String(cc);
        errorCount += Checkers.checker("ss2.length() = 23", 23, ss2.length());
        errorCount += Checkers.checker("ss2 = ss1", true, ss2.equals(ss1));
        
        sss = ss2.repeat(100);
        errorCount += Checkers.checker("sss.length() = 2300", 2300, sss.length());
        ss1 = "";
        for (int ix = 0; ix < 100; ix++) {
            ss1 = ss1.concat(ss2);
        }
        errorCount += Checkers.checker("ss1.length() = 2300", 2300, ss1.length());
        errorCount += Checkers.checker("ss1 = sss", true, sss.equals(ss1));
         
        Checkers.theEnd(errorCount);
    }
}

