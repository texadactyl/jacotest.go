public class main {

    static int proof[] = { 0, 0, 0 };
    static Object lock = new Object();

    static class Worker extends Thread {
        private final int id;

        Worker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            synchronized(lock) { System.out.printf("Worker Thread %d started\n", id); }
            proof[id - 1] = 1;
            try {
                Thread.sleep(2000);   // Sleep 5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            synchronized(lock) { System.out.printf("Worker Thread %d ended\n", id); }
        }
    }

    public static void main(String[] args) {
        int errorCount = 0;
        Thread t1 = new Worker(1);
        Thread t2 = new Worker(2);
        Thread t3 = new Worker(3);

        t1.start();
        t2.start();
        t3.start();
        synchronized(lock){ System.out.println("All threads started."); }

        try {
            t1.join();
            errorCount += Checkers.checker("T1 run proof", 1, proof[0]);            
            t2.join();
            errorCount += Checkers.checker("T2 run proof", 1, proof[1]);
            t3.join();
            errorCount += Checkers.checker("T3 run proof", 1, proof[2]);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All threads completed.");
        if (errorCount > 0)
            System.out.println("*** ERROR, at least one Worker thread did not execute its run() function");
        Checkers.theEnd(errorCount);
    }
}

