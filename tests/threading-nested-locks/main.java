public class main {

    static final int NTHREADS = 8;
    static final long TIMEOUT_MILLIS = 10000L;
    static final long SLEEP_MILLIS = 500L;
    static int proof[] = new int[NTHREADS]; 
    static Object lock = new Object();

    static class Worker extends Thread {
        private final int index;
        private final String name;

        Worker(int argIndex) {
            index = argIndex;
            name = getName();
        }

        @Override
        public void run() {
            synchronized(lock) { 
                synchronized(lock) { 
                    synchronized(lock) { 
                        synchronized(lock) { 
                            synchronized(lock) { 
                                System.out.printf("Worker Thread %d-%s entered run()\n", index, name);
                                proof[index - 1] = 1;
                                try {
                                    Thread.sleep(SLEEP_MILLIS);
                                } catch (InterruptedException e) {
                                    String errMsg = String.format("*** Worker Thread %d-%s interrupted\n", index, name);
                                    throw new AssertionError(errMsg);
                                } catch (Exception e) {
                                    String errMsg = String.format("*** Worker Thread %d-%s exception, errMsg: %s\n", index, name, e.getMessage());
                                    throw new AssertionError(errMsg);
                                }
                                System.out.printf("Worker Thread %d-%s ended\n", index, name);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int errorCount = 0;
        System.out.printf("main: Instantiating %d threads that will nest locks 5 deep .....\n", NTHREADS);
        Thread th[] = new Thread[NTHREADS];
        for (int ix = 0; ix < NTHREADS; ix++) {
            proof[ix] = 0;
            th[ix] = new Worker(ix + 1);
        }

        System.out.printf("main: Starting %d threads .....\n", NTHREADS);
        for (int ix = 0; ix < NTHREADS; ix++) {
            th[ix].start();
        }
        synchronized(lock){ System.out.println("main: All threads started."); }

        try {
            for (int ix = 0; ix < NTHREADS; ix++) {
                th[ix].join(TIMEOUT_MILLIS);
                if (th[ix].isAlive()) {
                    errorCount++;
                    System.out.printf("main: *** ERROR, thread %d timed out.\n", ix + 1);
                } else {
                    System.out.printf("main: Thread %d finished.\n", ix + 1);
                }
                String label = String.format("main: Thread %d run proof", ix + 1);
                errorCount += Checkers.checker(label, 1, proof[ix]);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("main: All threads completed.");
        if (errorCount > 0)
            System.out.println("main: *** ERROR, at least one Worker thread did not execute its run() function");
        Checkers.theEnd(errorCount);
    }
}

