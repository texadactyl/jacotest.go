public class ProcString {

    public static int procString() {
        int errorCount = 0;
        String SS1 = "Mary had a little lamb";
        String SS1_FLIPPED = "mARY HaD a LIttLe lAmB";
        char[] helloArray = {'h', 'e', 'l', 'l', 'o', '.'};
        String SS2 = new String(helloArray);
        String palindrome = "Dot saw I was Tod";
        int ii, jj;
        String ss;
        float pi = 3.14159265f;
        char ch;

        System.out.println("\n========== procString begins");

        ii = palindrome.length();
        if (ii == 17)
            System.out.println("Success: palindrome.length() == 17");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, palindrome.length() == 17, observed ");
            System.out.println(ii);
        }

        ss = SS1.concat(SS2);
        ii = SS1.length() + SS2.length();
        jj = ss.length();
        System.out.print("SS1.length() = ");
        System.out.println(SS1.length());
        System.out.print("SS2.length() = ");
        System.out.println(SS2.length());
        System.out.print("SS1 SS2 sum of lengths = ");
        System.out.println(ii);
        System.out.print("SS1 SS2 concatenated length = ");
        System.out.println(jj);
        if (ii == jj)
            System.out.println("Success: SS1.concat(SS2) length makes sense");
        else {
            errorCount += 1;
            System.out.println("*** ERROR, SS1.concat(SS2) length does not make sense");
        }

        ss = String.format("%s %%%% %d %d %02x %.2f %f %s", SS1, 42L, 42, 0x2a, pi, pi, SS2);
        System.out.print("Formatted string data: ");
        System.out.println(ss);
        System.out.print("Formatted string length: ");
        System.out.println(ss.length());
        if (ss.length() == 55)
            System.out.println("Success: Formatted string length makes sense");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, Formatted string length does not make sense, observed: ");
            System.out.println(ss.length());
        }

        ch = SS1.charAt(3);
        if (ch == 'y')
            System.out.println("Success: SS1.charAt(3) == 'y'");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, SS1.charAt(3) should == 'y', observed: ");
            System.out.println(ch);
        }

        ii = SS1.compareToIgnoreCase(SS1_FLIPPED);
        if (ii == 0)
            System.out.println("Success: SS1.compareToIgnoreCase( SS1_FLIPPED )");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, SS1.compareToIgnoreCase( SS1_FLIPPED ), observed: ");
            System.out.println(ii);
        }

        System.out.println("========== procString ends");

        // Return error count to caller
        return errorCount;
    }

}
