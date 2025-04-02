public class main {

    private static int exec(int arg, int nshift, int expected, boolean right) {
        int result;
        String shift;
        if (right) {
            result = arg >> nshift;
            shift = ">>";
        } else {
            result = arg << nshift;
            shift = "<<";
        }
        if (result != expected) {
            System.out.printf("exec *** ERROR, %d %s %d, expected %d, observed %d\n", 
                                arg, shift, nshift, expected, result);
            return 1;
        }
        System.out.printf("exec ok, %d %s %d == %d\n", arg, shift, nshift, expected);
        return 0;
    }

    public static void main(String[] args) throws Exception {

        String msg = "Bit shifting test cases";
        System.out.println(msg);

        int errorCount = 0;

        errorCount += exec(-95, 4, -6, true);
        errorCount += exec(-95, 3, -12, true);
        errorCount += exec(-95, 2, -24, true);
        errorCount += exec(-95, 1, -48, true);
        errorCount += exec(-100, 2, -25, true);
        errorCount += exec(-100, 3, -800, false);
        errorCount += exec(100, 2, 25, true);
        errorCount += exec(100, 3, 800, false);

        System.out.print("Error count = ");
        System.out.println(errorCount);
        assert (errorCount == 0);
        System.out.println("Success!");
    }
}
