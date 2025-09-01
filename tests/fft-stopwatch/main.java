// hacked from scimark2, kernal.java

public class main {

    private static int defaultSampleSize = 16;
    private static double maxRunTime = 10.0; // seconds

    public static void main(String[] args) throws InterruptedException {
        measureFFT(defaultSampleSize);
    }

    public static void measureFFT(int N) throws InterruptedException {
        // initialize FFT data as complex (N real/img pairs)

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
        System.out.printf("measureFFT: cycles: %d, elapsed time (s): %f, cycles/second: %f\n", cycles, et, cps);
        
        Checkers.theEnd(0);

    }

}
