public class main {

    static final int NTHREADS = 16;
    static final long TIMEOUT_MILLIS = 10000L;
    static final long SLEEP_MILLIS = 20L;
    static int upticker = 0;

    static class Worker extends Thread {
        private final int index;
        private final String name;
        private Object lock = new Object();

        Worker(int argIndex) {
            index = argIndex;
            name = getName();
        }

        @Override
        public void run() {
            synchronized (lock) {
                //System.out.printf("worker: Thread %d saw upticker: %d.\n", index, upticker);
                ++upticker; 
            }
            try {
                Thread.sleep(SLEEP_MILLIS);
            } catch (InterruptedException e) {
                String errMsg = String.format("*** Worker Thread %d-%s interrupted\n", index, name);
                throw new AssertionError(errMsg);
            } catch (Exception e) {
                String errMsg = String.format("*** Worker Thread %d-%s exception, errMsg: %s\n", index, name, e.getMessage());
                throw new AssertionError(errMsg);
            }
        }
    }

    public static void main(String[] args) {
        int errorCount = 0;
        System.out.printf("main: Instantiating and starting %d threads .....\n", NTHREADS);
        Thread th[] = new Thread[NTHREADS];
        for (int ix = 0; ix < NTHREADS; ix++) {
            th[ix] = new Worker(ix + 1);
        }
        for (int ix = 0; ix < NTHREADS; ix++) {
            th[ix].start();
        }

        System.out.println("All threads started.");

        try {
            for (int ix = 0; ix < NTHREADS; ix++) {
                th[ix].join(TIMEOUT_MILLIS);
                if (th[ix].isAlive()) {
                    errorCount++;
                    System.out.printf("main: *** ERROR, thread %d timed out.\n", ix + 1);
                } else {
                    System.out.printf("main: Thread %d finished.\n", ix + 1);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Checkers.theEnd(errorCount);
        }

        errorCount += Checkers.checker("upticker", NTHREADS, upticker);
        if (errorCount == 0)
            System.out.println("All threads completed successfully.");
        Checkers.theEnd(errorCount);
    }
}

