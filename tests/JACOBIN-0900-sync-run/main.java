public class main {
    private final static int threadCount = 64;
    private final static MyThread[] threads = new MyThread[threadCount];

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new MyThread(i);
            threads[i].start();
        }
        System.out.printf("main: %d threads started\n", threadCount);

        Thread.sleep(100); // Give threads time to reach wait()

        // Notify each thread to run.
        for (int i = 0; i < threadCount; i++) {
            synchronized (threads[i]) {
                threads[i].pendingWork = true;
                threads[i].notify();
            }
        }
        System.out.printf("main: %d threads notified\n", threadCount);

        // Wait for each thread to finish.
        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
            System.out.printf("main: Thread-%d joined\n", i);
        }

        Checkers.theEnd(0);
    }

    private static class MyThread extends Thread {
        private final int id;
        boolean pendingWork = false;
        boolean terminate = false;

        MyThread(int id) { this.id = id; }

        public synchronized void run() {
            while (!terminate) {
                while (!pendingWork) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                if (!terminate) {
                    //System.out.printf("Thread-%d: doing work\n", id);
                    terminate = true;  // Done after one unit of work
                    pendingWork = false;
                    notifyAll();
                }
            }
        }
    }
}
