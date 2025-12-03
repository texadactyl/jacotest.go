public class main {

    static int proof[] = { 0, 0, 0 };
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
                System.out.printf("Worker Thread %d-%s entered run()\n", index, name);
                proof[index - 1] = 1;
                try {
                    Thread.sleep(500);
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

    public static void main(String[] args) {
        int errorCount = 0;
        System.out.println("threading-1: instantiate threads .....");
        Thread t1 = new Worker(1);
        Thread t2 = new Worker(2);
        Thread t3 = new Worker(3);

        t1.start();
        t2.start();
        t3.start();
        synchronized(lock){ System.out.println("All threads started."); }

        try {
            t1.join(5000);
            if (t1.isAlive()) {
                errorCount++;
                System.out.println("*** ERROR, t1 timed out");
            } else {
                System.out.println("t1 finished");
            }
            errorCount += Checkers.checker("t1 run proof", 1, proof[0]);            
            t2.join(5000);
            if (t2.isAlive()) {
                errorCount++;
                System.out.println("*** ERROR, t2 timed out");
            } else {
                System.out.println("t2 finished");
            }
            errorCount += Checkers.checker("t2 run proof", 1, proof[1]);
            t3.join(5000);
            if (t3.isAlive()) {
                errorCount++;
                System.out.println("*** ERROR, t3 timed out");
            } else {
                System.out.println("t3 finished");
            }
            errorCount += Checkers.checker("t3 run proof", 1, proof[2]);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All threads completed.");
        if (errorCount > 0)
            System.out.println("*** ERROR, at least one Worker thread did not execute its run() function");
        Checkers.theEnd(errorCount);
    }
}

