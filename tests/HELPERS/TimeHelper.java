/**
 * Simple elapsed-time reporter.
 * Output forms: hh:mm:ss.d (>= 1 hour), mm:ss.d (>= 1 minute), ss.d (otherwise).
 * d is tenths of a second (truncated, not rounded).
 */
public final class TimeHelper {

    private long startMs;
    private long stopMs;
    private boolean running;

    /** Monotonic milliseconds: immune to system clock changes, same behavior on every OS. */
    private long nowMs() {
        return System.nanoTime() / 1_000_000L;
    }

    public void start() {
        startMs = nowMs();
        running = true;
    }

    public void stop() {
        stopMs = nowMs();
        running = false;
    }

    /** Elapsed milliseconds; if still running, measured up to now. */
    public long elapsedMillis() {
        long end = running ? nowMs() : stopMs;
        return Math.max(0L, end - startMs);
    }

    @Override
    public String toString() {
        return format(elapsedMillis());
    }

    /** Format a millisecond duration as hh:mm:ss.d, mm:ss.d or ss.d. */
    public String format(long ms) {
        if (ms < 0) ms = 0;
        long tenths = (ms / 100) % 10;
        long totalSec = ms / 1000;
        long sec = totalSec % 60;
        long min = (totalSec / 60) % 60;
        long hrs = totalSec / 3600;

        if (hrs > 0) {
            return String.format("%02d:%02d:%02d.%d (hh:mm:ss.d)", hrs, min, sec, tenths);
        } else if (min > 0) {
            return String.format("%02d:%02d.%d (mm:ss.d)", min, sec, tenths);
        } else {
            return String.format("%02d.%d (sec)", sec, tenths);
        }
    }

	/** Standardised report of elapsed time. */    
    public void report() {
		String et = toString();
		System.out.println("\nTimeHelper report: elapsed time = " + et);
    }

}
