public class main {

    public static void main(String[] args) throws Exception {

        String msg = "Test the use of minus signs in integer operations";
        System.out.println(msg);

        int errorCount = 0;
        int a = 60;
        int b = 13;
        int c = -47;
        System.out.print("a: ");
        System.out.println(a);
        System.out.print("b: ");
        System.out.println(b);
        System.out.print("c should be -47 : ");
        System.out.println(c);
        if (c > 0) {
            System.out.print("FAILED Expected c < 0. Observed ");
            System.out.println(c);
            ++errorCount;
        } else
            System.out.println("Success c < 0");
        System.out.println("");

        c = b - a;
        System.out.print("c should be 13 - 60 = -47 : ");
        System.out.println(c);
        if (c != -47) {
            System.out.print("FAILED Expected b - a == -47. Observed ");
            System.out.println(c);
            ++errorCount;
        } else
            System.out.println("Success b - a == -47");
        System.out.println("");

        c = ~60;
        System.out.print("c = ~60 ==> ");
        System.out.println(c);
        if (c < 0) {
            System.out.println("Success trying c = ~60 ==> -61");
        } else {
            System.out.print("FAILED trying c = ~60. Expected -61. Observed ");
            System.out.println(c);
            ++errorCount;
        }
        System.out.println("");

        System.out.print("Error count = ");
        System.out.println(errorCount);
        if (errorCount > 0) {
            System.out.println("Going to thrrow an Exception next .....");
            throw new Exception("Test case failure !!");
        }
    }
}
