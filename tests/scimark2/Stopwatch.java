

/**
 * Provides a stopwatch to measure elapsed time.
 *
 * <p>
 * <DL>
 * <DT><B>Example of use:</B></DT>
 * <DD>
 * <p>
 * <pre>
 * Stopwatch Q = new Stopwatch;
 * <p>
 * Q.start();
 * //
 * // code to be timed here ...
 * //
 * Q.stop();
 * System.out.println("elapsed time was: " + Q.read() + " seconds.");
 * </pre>
 *
 * @author Roldan Pozo
 * @version 14 October 1997, revised 1999-04-24
 */
public class Stopwatch {
    private boolean running;
    private double last_time;
    private double total;

    public Stopwatch() {
        this.running = false;
        this.last_time = 0.0;
        this.total = 0.0;
    }

    /**
     * Return system time (in seconds)
     */
    public static double seconds() {
        return (System.currentTimeMillis() * 0.001);
    }

    /**
     * Start (and reset) timer
     */
    public void start() {
        if (!running) {
            running = true;
            total = 0.0;
            last_time = seconds();
        }
    }

    /**
     * Stop timer
     */
    public double stop() {
        if (running) {
            total += seconds() - last_time;
            running = false;
        }
        return total;
    }

    /**
     * Display the elapsed time (in seconds)
     */
    public double read() {
        if (running) {
            total += seconds() - last_time;
            last_time = seconds();
        }
        return total;
    }

}

    

            
