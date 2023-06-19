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
     	rptStr("s1Text", s1Text);
        rptInt("s1Text length", s1Text.length());
       
        char[] cText = s1Text.toCharArray();
        wstr = new String(cText);
     	rptStr("cText", wstr);
        rptInt("cText length", cText.length);
        if (cText.length != s1Text.length()) {
        	errorCount += 1;
        	System.out.println("*** ERROR, cText.length != s1Text.length");
        }

        s2Text = new String(cText);
     	rptStr("s2Text", s2Text);
        rptInt("s2Text length", s2Text.length());
        if (! s2Text.equals(s1Text)) {
        	errorCount += 1;
        	System.out.println("*** ERROR, s2Text != s1Text");
        }
        
        byte[] bText = s1Text.getBytes();
        wstr = new String(bText);
     	rptStr("bText", wstr);
        rptInt("bText length", bText.length);
        if (bText.length != s1Text.length()) {
        	errorCount += 1;
        	System.out.println("*** ERROR, bText, bText.length != s1Text.length");
        }

        s3Text = new String(bText);
      	rptStr("s3Text", s3Text);
        rptInt("s3Text length", s3Text.length());
        if (! s3Text.equals(s1Text)) {
        	errorCount += 1;
        	System.out.println("*** ERROR, s2Text != s1Text");
        }

        // Check the error count
        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
        	System.out.print("Number of errors = ");
        }
    }

}
