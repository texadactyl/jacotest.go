// hacked from scimark2, kernal.java

public class main {

    private static double maxRunTime = 3; // seconds

    public static void main(String[] args) throws InterruptedException {

        long cycles = 1;
        Stopwatch Q = new Stopwatch();
        double et; // seconds

        while (true) {
            Q.resume();
            for (int ix = 0; ix < cycles; ix++) {
                Thread.sleep(3);
            }
            Q.stop();
            et = Q.stop();
            if (et >= maxRunTime)
                break;

            cycles *= 2;
        }

        et = Q.read();
        double cps = cycles / et;
        System.out.printf("cycles: %d, elapsed time (s): %f, cycles/second: %f\n", cycles, et, cps);
        
        Checkers.theEnd(0);
    }

}
