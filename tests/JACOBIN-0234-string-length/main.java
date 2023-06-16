public class main {

    public static void main(String[] args) throws Exception {

        int errorCount = 0;
        String s1Text = "String conversion and length exercises";
        System.out.println(s1Text);

     	System.out.print("Starting text = ");
        System.out.println(s1Text);
       
        char[] cText = s1Text.toCharArray();
        if (s1Text.length() != cText.length) {
        	errorCount += 1;
        	System.out.println("*** ERROR, s1Text.length != cText.length");
        }
        String s2Text = new String(cText);
         if (! s2Text.equals(s1Text)) {
        	errorCount += 1;
        	System.out.println("*** ERROR, cText, s2Text != s1Text");
		 	System.out.print("s2Text = ");
		    System.out.println(s2Text);
        }
        
        byte[] bText = s1Text.getBytes();
        if (s1Text.length() != bText.length) {
        	errorCount += 1;
        	System.out.println("*** ERROR, bText, s1Text.length != bText.length");
        }
        s2Text = new String(bText);
         if (! s2Text.equals(s1Text)) {
        	errorCount += 1;
        	System.out.println("*** ERROR, s2Text != s1Text");
		 	System.out.print("s2Text = ");
		    System.out.println(s2Text);
        }

        // Check the error count
        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
        	System.out.print("Number of errors = ");
            System.out.println(errorCount);
            System.exit(1);
        }
    }

}
