public class main {

    public static void main(String[] args) throws Exception {

        String msg = "Bit shifting test cases";
        System.out.println(msg);

        int errorCount = 0;

        int a = -95;
        int b = a >> 4;
        if (b != -6) {
            System.out.print("*** ERROR, trying -95 >> 4. Expected -6. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying -95 >> 4 == -6");

        a = -100;
        b = a >> 2;
        if (b != -25) {
            System.out.print("*** ERROR, trying -100 >> 2. Expected -25. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying -100 >> 2 == -25");

        b = a << 3;
        if (b != -800) {
            System.out.print("*** ERROR, trying -100 << 3. Expected -800. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying -100 << 3 == -800");

        a = 100;
        b = a >> 2;
        if (b != 25) {
            System.out.print("*** ERROR, trying +100 >> 2. Expected 25. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying +100 >> 2 == 25");

        b = a << 3;
        if (b != 800) {
            System.out.print("*** ERROR, trying +100 << 3. Expected 800. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying +100 << 3 == 800");

        System.out.print("Error count = ");
        System.out.println(errorCount);
        assert (errorCount == 0);
    }
}
