public class main {

    public static void main(String[] args) throws Exception {
    
    	System.out.println("String manipulation and member functions - Part 2");

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
        
		System.out.println("Instantiate String from a char []");
        char [] cc = ss1.toCharArray();
        ss2 = new String(cc);
        errorCount += hh.isItTrue("ss2.length() = 23", ss2.length() == 23);
        errorCount += hh.isItTrue("ss2 = ss1", ss2.equals(ss1));
        
		assert(errorCount == 0);
    }
}

