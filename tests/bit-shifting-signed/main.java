public class main {

    private static int exec(int arg, int nshift, int expected, boolean shiftRight) {
        int observed;
        String shift;
        if (shiftRight) {
            observed = arg >> nshift;
            shift = ">>";
        } else {
            observed = arg << nshift;
            shift = "<<";
        }
        if (observed != expected) {
            System.out.printf("exec *** ERROR, %d %s %d, expected=%d, observed=%d\n",
                                arg, shift, nshift, expected, observed);
            return 1;
        }
        System.out.printf("exec ok, %d %s %d = %d\n", arg, shift, nshift, expected);
        return 0;
    }

    public static void main(String[] args) throws Exception {

        String msg = "Bit shifting test cases";
        System.out.println(msg);

        int errorCount = 0;

        // Shift right.
        errorCount += exec(-95, 4, -6, true);
        errorCount += exec(-95, 3, -12, true);
        errorCount += exec(-95, 2, -24, true);
        errorCount += exec(-95, 1, -48, true);
        errorCount += exec(95, 4, 5, true);
        errorCount += exec(95, 3, 11, true);
        errorCount += exec(95, 2, 23, true);
        errorCount += exec(95, 1, 47, true);
        errorCount += exec(-100, 4, -7, true);
        errorCount += exec(-100, 3, -13, true);
        errorCount += exec(-100, 2, -25, true);
        errorCount += exec(-100, 1, -50, true);
        errorCount += exec(-1, 4, -1, true);
        errorCount += exec(-1, 3, -1, true);
        errorCount += exec(1, 1, 0, true);
        errorCount += exec(100, 2, 25, true);
        errorCount += exec(100, 2 + 16384, 25, true);

        // Shift left.
        errorCount += exec(-95, 4, -1520, false);
        errorCount += exec(-95, 3, -760, false);
        errorCount += exec(-95, 2, -380, false);
        errorCount += exec(-95, 1, -190, false);
        errorCount += exec(95, 4, 1520, false);
        errorCount += exec(95, 3, 760, false);
        errorCount += exec(95, 2, 380, false);
        errorCount += exec(95, 1, 190, false);
        errorCount += exec(-100, 4, -1600, false);
        errorCount += exec(-100, 3, -800, false);
        errorCount += exec(-100, 2, -400, false);
        errorCount += exec(-100, 1, -200, false);
        errorCount += exec(-1, 4, -16, false);
        errorCount += exec(-1, 3, -8, false);
        errorCount += exec(1, 1, 2, false);
        errorCount += exec(100, 2, 400, false);
        errorCount += exec(100, 2 + 16384, 400, false);

        Checkers.theEnd(errorCount);
    }
}
