// Hacked from https://www.studytonight.com/java/type-casting-in-java.php

public class main {

    public static void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.println(value);
    }

    public static int isItTrue(String label, boolean bool, String observed) {
        if (bool) {
            printLabeledString("Success :: ", label);
            return 0;
        }
        printLabeledString("*** FAILED :: ", label);
        printLabeledString("*** observed value :: ", observed);
        return 1;
    }

    public static void main(String[] args) throws Exception {
        int errorCount = 0;

        System.out.println("Widening and Narrowing Casting");

        byte bb = 0x7e;
        System.out.print("Byte value ");
        System.out.println(bb);
        char cc = (char) bb;
        short ss = (short) cc;
        int ii = (int) ss;
        long ll = (long) ii;
        float ff = (float) ll;
        double dd = (double) ff;
        errorCount += isItTrue("cc == 126", cc == 126, String.valueOf(cc));
        errorCount += isItTrue("ll == 126", cc == 126, String.valueOf(ll));
        errorCount += isItTrue("dd == 126", dd == 126.0, String.valueOf(dd));

        dd = 65537.04;
        System.out.print("Double value ");
        System.out.println("dd");
        ff = (float) dd;
        ll = (long) ff;
        ii = (int) ll;
        ss = (short) ii;
        cc = (char) ii;
        bb = (byte) cc;
        errorCount += isItTrue("ll == 65537", ll == 65537, String.valueOf(ll));
        errorCount += isItTrue("ii == 65537", ii == 65537, String.valueOf(ii));
        errorCount += isItTrue("ss == 1", ss == 1, String.valueOf(ss));
        errorCount += isItTrue("cc == 1", cc == 1, String.valueOf(cc));
        errorCount += isItTrue("bb == 1", bb == 1, String.valueOf(bb));

        // Check the error count
        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            printLabeledString("Number of errors = ", String.valueOf(errorCount));
            System.exit(1);
        }
    }
}
