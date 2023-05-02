public class main {

    public static void main(String[] args) throws Exception {

        String msg = "Four bit shifting test cases";
        System.out.println(msg);

        int errorCount = 0;

        int a = -100;
        int b = a >> 2;
        if (b != -25) {
            System.out.print("FAILED trying -100 >> 2. Expected -25. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying -100 >> 2 == -25");

        b = a << 3;
        if (b != -800) {
            System.out.print("FAILED trying -100 << 3. Expected -800. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying -100 << 3 == -800");

        a = 100;
        b = a >> 2;
        if (b != 25) {
            System.out.print("FAILED trying +100 >> 2. Expected 25. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying +100 >> 2 == 25");

        b = a << 3;
        if (b != 800) {
            System.out.print("FAILED trying +100 << 3. Expected 800. Observed ");
            System.out.println(b);
            errorCount += 1;
        } else
            System.out.println("Success trying +100 << 3 == 800");

        System.out.print("Error count = ");
        System.out.println(errorCount);
        if (errorCount > 0) {
            System.out.println("Going to thrrow an Exception next .....");
            throw new Exception("Test case failure !!");
        }
    }
}
