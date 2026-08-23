public class main {

    public static void main(String[] args) {
        int NLOOPS = 10_000_000;
        System.out.printf("Number of loops: %d\n", NLOOPS);
        var vf = new virtfunc();

        long t1 = System.currentTimeMillis();
        for (int i = 1; i <= NLOOPS; i++) {
            vf.foo();
        }
        long t2 = System.currentTimeMillis();

        double elapsedSeconds = (double)(t2 - t1) / 1000.0;
        System.out.printf("Elapsed time (seconds): %.3f\n", elapsedSeconds);
        
        Checkers.theEnd(0);
    }
}

class virtfunc {
    public void foo() {
        return;
    }
}
