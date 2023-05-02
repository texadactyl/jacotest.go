public class ProcDouble {

    public static int procDouble() {
        int errorCount = 0;
        int cc; // -1, 0, or +1
        String ss;
        double xx;
        byte bite;

        System.out.println("\n========== procDouble begins");

        Double dd = 5.0;
        dd = dd + 10.0;
        if (dd == 15.0) {
            System.out.print("Success: Unboxed Double = ");
            System.out.println(dd);
        } else {
            errorCount += 1;
            System.out.print("FAILED: Unboxed Double not = 15, observed = ");
            System.out.println(dd);
        }

        Double DD = 5.0;
        Double DD_DITTO = 5.0;
        Double DD_LARGER = 6.0;
        Double DD_SMALLER = 4.0;

        bite = DD.byteValue();
        if (bite == 0x05)
            System.out.println("Success: DD.byteValue()");
        else {
            errorCount += 1;
            System.out.print("FAILED: DD.byteValue() = 0x05, observed = ");
            System.out.println(bite);
        }

        cc = DD.compareTo(DD_DITTO);
        if (cc == 0)
            System.out.println("Success: DD : DD_DITTO is zero");
        else {
            errorCount += 1;
            System.out.print("FAILED: DD.compareTo(DD_DITTO)==0, observed = ");
            System.out.println(cc);
        }

        if (DD.equals(DD_DITTO))
            System.out.println("Success: DD equals DD_DITTO");
        else {
            errorCount += 1;
            System.out.print("FAILED: DD.compareTo(DD_DITTO)==0, observed = ");
            System.out.println(cc);
        }

        cc = DD.compareTo(DD_SMALLER);
        if (cc == 1)
            System.out.println("Success: DD.compareTo(DD_SMALLER)");
        else {
            errorCount += 1;
            System.out.print("FAILED: DD.compareTo(DD_SMALLER)==1, observed = ");
            System.out.println(cc);
        }

        cc = DD.compareTo(DD_LARGER);
        if (cc < 0)
            System.out.println("Success: DD.compareTo(DD_LARGER)");
        else {
            errorCount += 1;
            System.out.print("FAILED: DD.compareTo(DD_LARGER)==-1, observed = ");
            System.out.println(cc);
        }

        Long L42 = 42L;
        Integer I42 = 42;
        Byte B42 = 0x2a;
        double ds42 = L42.doubleValue();
        double di42 = I42.doubleValue();
        double db42 = B42.doubleValue();
        if ((ds42 == di42) && (ds42 == db42))
            System.out.println("Success: ds42 == di42 == db42");
        else {
            errorCount += 1;
            System.out.print("FAILED: ds42 == di42 == db42, observed: ds42==");
            System.out.print(ds42);
            System.out.print(", di42==");
            System.out.print(di42);
            System.out.print(", db42==");
            System.out.println(db42);
        }

        ss = DD.toString();
        if (ss.equals("5.0"))
            System.out.println("Success: DD.toString()");
        else {
            errorCount += 1;
            System.out.print("FAILED: DD.toString() = 5, observed = ");
            System.out.println(ss);
        }

        System.out.println("========== procDouble ends");

        // Return error count to caller
        return errorCount;
    }

}
