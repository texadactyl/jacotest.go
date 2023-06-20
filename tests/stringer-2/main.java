public class main {

    public static void main(String[] args) throws Exception {
    
        int errorCount = 0;
        Helpers hh = new Helpers();

        String ss1 = "Mary had a little lamb,";
        hh.printLabeledString("ss1, the initial String: ", ss1);
        
		System.out.println("sss = 21-character substring of ss1");
        String sss = ss1.substring(1, 22);
        errorCount += hh.isItTrue("sss.length() = 21", sss.length() == 21);

		System.out.println("Instantiate String from a byte []");
        byte [] bb = ss1.getBytes();
        String ss2 = new String(bb);
        errorCount += hh.isItTrue("ss2.length() = 23", ss2.length() == 23);
        errorCount += hh.isItTrue("ss2 = ss1", ss2.equals(ss1));

		System.out.println("Instantiate String from a subset of a byte []");
        ss2 = new String(bb, 0, bb.length);
        errorCount += hh.isItTrue("ss2.length() = 23", ss2.length() == 23);
        errorCount += hh.isItTrue("ss2 = ss1", ss2.equals(ss1));
        
		System.out.println("Instantiate String from a subset of a byte [] using a character set");
        ss2 = new String(bb, 0, bb.length, "UTF-8");
        errorCount += hh.isItTrue("ss2.length() = 23", ss2.length() == 23);
        errorCount += hh.isItTrue("ss2 = ss1", ss2.equals(ss1));
        
		System.out.println("Instantiate String from a byte [] using a character set");
        ss2 = new String(bb, "UTF-8");
        errorCount += hh.isItTrue("ss2.length() = 23", ss2.length() == 23);
        errorCount += hh.isItTrue("ss2 = ss1", ss2.equals(ss1));
        
		System.out.println("Instantiate String from a char []");
        char [] cc = ss1.toCharArray();
        ss2 = new String(cc);
        errorCount += hh.isItTrue("ss2.length() = 23", ss2.length() == 23);
        errorCount += hh.isItTrue("ss2 = ss1", ss2.equals(ss1));
        
		System.out.println("Try method offsetByCodePoints");
        int wint = ss1.offsetByCodePoints(3, 6);
        errorCount += hh.isItTrue("ss1.offsetByCodePoints(3, 6) = 9", wint == 9);
        
		System.out.println("Build a String from a StringBuffer");
        StringBuffer wbuf = new StringBuffer(ss1);
        ss2 = new String(wbuf);
        errorCount += hh.isItTrue("ss2.length() = 23", ss2.length() == 23);
        errorCount += hh.isItTrue("ss2 = ss1", ss2.equals(ss1));
        
		System.out.println("Build a String from a StringBuilder");
        StringBuilder wbld = new StringBuilder(ss1);
        ss2 = new String(wbld);
        errorCount += hh.isItTrue("ss2.length() = 23", ss2.length() == 23);
        errorCount += hh.isItTrue("ss2 = ss1", ss2.equals(ss1));
        
		hh.byebye(errorCount);
    }
}

