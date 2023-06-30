public class ProcInteger {

    public static int procInteger() {
        int errorCount = 0;
        int cc; // -1, 0, or +1
        byte bite; // 0 : 0xff
        String ss;

        System.out.println("\n========== procInteger begins");

        Integer ii = 5;
        ii = ii + 10;   // unboxes the Integer to a int
        if (ii == 15) {
            System.out.print("Success: Unboxed Integer = ");
            System.out.println(ii);
        } else {
            errorCount += 1;
            System.out.print("*** ERROR, Unboxed Integer not = 15, observed = ");
            System.out.println(ii);
        }

        Integer II = 5;
        Integer II_DITTO = 5;
        Integer II_LARGER = 6;
        Integer II_SMALLER = 4;

        bite = II.byteValue();
        if (bite == 0x05)
            System.out.println("Success: II.byteValue()");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, II.byteValue() = 0x05, observed = ");
            System.out.println(bite);
        }

        cc = II.compareTo(II_DITTO);
        if (cc == 0)
            System.out.println("Success: II : II_DITTO is zero");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, II.compareTo(II_DITTO)==0, observed = ");
            System.out.println(cc);
        }

        if (II.equals(II_DITTO))
            System.out.println("Success: II equals II_DITTO");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, II.compareTo(II_DITTO)==0, observed = ");
            System.out.println(cc);
        }

        cc = II.compareTo(II_SMALLER);
        if (cc == 1)
            System.out.println("Success: II.compareTo(II_SMALLER)");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, II.compareTo(II_SMALLER)==1, observed = ");
            System.out.println(cc);
        }

        cc = II.compareTo(II_LARGER);
        if (cc < 0)
            System.out.println("Success: II.compareTo(II_LARGER)");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, II.compareTo(II_LARGER)==-1, observed = ");
            System.out.println(cc);
        }

        int base16 = Integer.parseInt("1ef", 16); // 495 base 10
        int base8 = Integer.parseInt("757", 8);  // 495 base 10
        int base10 = 495;
        if ((base10 == base8) && (base10 == base16))
            System.out.println("Success: base 10 == base8 == base16");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, base 10 == base8 == base16, observed: base10==");
            System.out.print(base10);
            System.out.print(", base8==");
            System.out.print(base8);
            System.out.print(", base16==");
            System.out.println(base16);
        }

        ss = II.toString();
        if (ss.equals("5"))
            System.out.println("Success: II.toString()");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, II.toString() = 5, observed = ");
            System.out.println(ss);
        }

        System.out.println("========== procInteger ends");

        // Return error count to caller
        return errorCount;
    }

}
