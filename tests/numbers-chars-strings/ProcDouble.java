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
            System.out.print("*** ERROR, Unboxed Double not = 15, observed = ");
            System.out.println(dd);
        }

        Double DD = 5.0;
        Double DD_DITTO = 5.0;
        Double DD_LARGER = 6.0;
        Double DD_SMALLER = 4.0;
        System.out.printf("DD = %f\n", DD);
        System.out.printf("DD_DITTO = %f\n", DD_DITTO);
        System.out.printf("DD_LARGER = %f\n", DD_LARGER);
        System.out.printf("DD_SMALLER = %f\n", DD_SMALLER);

        bite = DD.byteValue();
        if (bite == 0x05)
            System.out.println("Success: DD.byteValue()");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, DD.byteValue() = 0x05, observed = ");
            System.out.println(bite);
        }

        cc = DD.compareTo(DD_DITTO);
        if (cc == 0)
            System.out.println("Success: DD.compareTo(DD_DITTO) returned zero");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, DD.compareTo(DD_DITTO) should be 0, observed ");
            System.out.println(cc);
        }

        if (DD.equals(DD_DITTO))
            System.out.println("Success: DD.equals(DD_DITTO)");
        else {
            errorCount += 1;
            System.out.println("*** ERROR, DD.equals(DD_DITTO) is true, observed false");
        }

        if (! DD.equals(DD_SMALLER))
            System.out.println("Success: Not true that DD.equals(DD_SMALLER)");
        else {
            errorCount += 1;
            System.out.println("*** ERROR, DD.equals(DD_SMALLER) is false, observed true");
        }

        cc = DD.compareTo(DD_SMALLER);
        if (cc > 0)
            System.out.println("Success: DD.compareTo(DD_SMALLER)");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, DD.compareTo(DD_SMALLER) should be > 0, observed ");
            System.out.println(cc);
        }

        cc = DD.compareTo(DD_LARGER);
        if (cc < 0)
            System.out.println("Success: DD.compareTo(DD_LARGER)");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, DD.compareTo(DD_LARGER) should be < 0, observed ");
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
            System.out.print("*** ERROR, ds42 == di42 == db42, observed: ds42==");
            System.out.print(ds42);
            System.out.print(", di42==");
            System.out.print(di42);
            System.out.print(", db42==");
            System.out.println(db42);
        }

        ss = DD.toString();
        double dd1 = DD.doubleValue();
        double dd2 = Double.parseDouble(ss);
        if (dd1 == dd2)
            System.out.println("Success: DD.toString()");
        else {
            errorCount += 1;
            System.out.print("*** ERROR, DD.toString() = 5, observed = ");
            System.out.println(ss);
        }

        System.out.println("========== procDouble ends");

        // Return error count to caller
        return errorCount;
    }

}
