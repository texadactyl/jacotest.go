

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
    private double lastTime;
    private double total;

    public Stopwatch() {
        this.running = false;
        this.lastTime = 0.0;
        this.total = 0.0;
    }

    /**
     * Return system time (in seconds)
     */
    public double seconds() {
        return (System.currentTimeMillis() * 0.001);
    }

    /**
     * Return system time (in seconds)
     */
    public void reset() {
        running = false;
        lastTime = 0.0;
        total = 0.0;
    }

    /**
     * Start (and reset) timer
     */
    public void start() {
        if (!running) {
            running = true;
            total = 0.0;
            lastTime = seconds();
        }
    }

    /**
     * Resume timing, after stopping.  (Does not wipe out
     * accumulated times.)
     */
    public void resume() {
        if (!running) {
            lastTime = seconds();
            running = true;
        }
    }


    /**
     * Stop timer
     */
    public double stop() {
        if (running) {
            total += seconds() - lastTime;
            running = false;
        }
        return total;
    }


    /**
     * Return the elapsed time (in seconds)
     */
    public double read() {
        if (running) {
            total += seconds() - lastTime;
            lastTime = seconds();
        }
        return total;
    }

}

    

            
