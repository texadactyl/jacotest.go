public class main {

    private static int exec(int arg, int nshift, int expected) {
        int observed;
        String shift;
        observed = arg >>> nshift;
        if (observed != expected) {
            System.out.printf("exec *** ERROR, %d >>> %d, expected=%d, observed=%d\n",
                                arg, nshift, expected, observed);
            return 1;
        }
        System.out.printf("exec ok, %d >>> %d = %d\n", arg, nshift, expected);
        return 0;
    }

    public static void main(String[] args) throws Exception {

        String msg = "Bit shifting test cases";
        System.out.println(msg);

        int errorCount = 0;

        errorCount += exec(-95, 4, 268435450);
        errorCount += exec(-95, 3, 536870900);
        errorCount += exec(-95, 2, 1073741800);
        errorCount += exec(-95, 1, 2147483600);
        errorCount += exec(95, 4, 5);
        errorCount += exec(95, 3, 11);
        errorCount += exec(95, 2, 23);
        errorCount += exec(95, 1, 47);
        errorCount += exec(-100, 4, 268435449);
        errorCount += exec(-100, 3, 536870899);
        errorCount += exec(-100, 2, 1073741799);
        errorCount += exec(-100, 1, 2147483598);
        errorCount += exec(-1, 4, 268435455);
        errorCount += exec(-1, 3, 536870911);
        errorCount += exec(1, 1, 0);
        errorCount += exec(100, 2, 25);
        errorCount += exec(100, 2 + 16384, 25);

        Checkers.theEnd(errorCount);
    }
}
