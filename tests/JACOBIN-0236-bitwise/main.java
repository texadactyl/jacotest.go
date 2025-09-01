public class main {

    public static void main(String[] args) throws Exception {

        String msg = "Perform various integer operations";
        System.out.println(msg);

        int errorCount = 0;

        int a = 10;
        int b = 25;
        System.out.println("a=10 and b=25");

        int c = a + b;
        if (c != 35) {
            System.out.print("*** ERROR, trying a + b. Expected 35. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok a + b == 35");

        c = a - b;
        System.out.print("c = a - b: ");
        System.out.println(c);
        if (c != -15) {
            System.out.println("*** ERROR, if(c != -15). Expected false. Observed true");
            errorCount += 1;
        } else
            System.out.println("ok a - b == -15");

        c = b / a;
        if (c != 2) {
            System.out.print("*** ERROR, trying b / a. Expected 2. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok b / a == 2");

        c = b % a;
        if (c != 5) {
            System.out.print("*** ERROR, trying b % a. Expected 5. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok b % a == 5");

        a = 60;
        b = 13;
        System.out.println("a=60 and b=13");

        c = b & a;
        if (c != 12) {
            System.out.print("*** ERROR, trying b & a. Expected 12. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok b % a == 12");

        c = b | a;
        if (c != 61) {
            System.out.print("*** ERROR, trying b | a. Expected 61. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok b | a == 61");

        c = b ^ a;
        if (c != 49) {
            System.out.print("*** ERROR, trying b ^ a. Expected 61. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok b ^ a == 49");

        c = ~a;
        System.out.println("c = unary bitwise complement operator on a");
        if (c > 0) {
            System.out.print("*** ERROR, trying ~a. Expected -61. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok ~a == -61");

        c = ~60;
        System.out.println("c = unary bitwise complement operator on 60");
        if (c > 0) {
            System.out.print("*** ERROR, trying ~60. Expected -61. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok ~60 == -61");

        c = a >>> 2;
        if (c != 15) {
            System.out.print("*** ERROR, trying a>>>2. Expected 15. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok a>>>2 == 15");

        boolean A = true;
        boolean B = false;
        System.out.println("A=true and B=false");
        if (A && B) {
            System.out.println("*** ERROR, trying A && B. Expected false. Observed true");
            errorCount += 1;
        } else
            System.out.println("ok A && B == false");

        if (!(A || B)) {
            System.out.println("*** ERROR, trying A || B. Expected true. Observed false");
            errorCount += 1;
        } else
            System.out.println("ok A || B == true");

        c = (a == 42) ? 1001 : 1002;
        if (c != 1002) {
            System.out.print("*** ERROR, trying c = (a == 42) ? 1001: 1002. Expected 1002. Observed ");
            System.out.println(c);
            errorCount += 1;
        } else
            System.out.println("ok c = (a == 42) ? 1001: 1002 ==>> 1002");

        Checkers.theEnd(errorCount);
    }
}
