public class main {

    public static void rptInt(String label, int value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(value);
    }

    public static void rptStr(String label, String value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(value);
    }

    public static void main(String[] args) throws Exception {

        String s1Text, s2Text, s3Text, wstr;
        int errorCount = 0;
        System.out.println("String conversion and length exercises");

        s1Text = "Mary had a little lamb";
     	rptStr("s1Text, a String", s1Text);
        rptInt("s1Text length", s1Text.length());
       
        System.out.println("Let's try to make a char array from s1Text .....");
        char[] cText = s1Text.toCharArray();
        System.out.println("and then back to a String, wstr .....");
        wstr = new String(cText);
     	rptStr("wstr from cText", wstr);
        rptInt("cText length", cText.length);
        rptInt("wstr length", wstr.length());
        if (cText.length != wstr.length()) {
        	errorCount += 1;
        	System.out.println("*** ERROR, cText.length != wstr.length()");
        }

        System.out.println("Let's try to make a byte array from s1Text .....");
        byte[] bText = s1Text.getBytes();
        System.out.println("and then back to a String, wstr .....");
        wstr = new String(bText);
     	rptStr("wstr from bText", wstr);
        rptInt("bText length", bText.length);
        rptInt("wstr length", wstr.length());
        if (bText.length != wstr.length()) {
        	errorCount += 1;
        	System.out.println("*** ERROR, bText.length != wstr.length");
        }

        Checkers.theEnd(errorCount);
    }

}
