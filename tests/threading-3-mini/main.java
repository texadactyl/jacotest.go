public class main {

    static int thCounter = 0;
    static Object lock = new Object();

    // Simple worker thread that sleeps briefly
    static class Worker extends Thread {
    
        public Worker(ThreadGroup argGroup, String argName) {
            super(argGroup, argName); // assign to the specified ThreadGroup
        }

        @Override
        public void run() {
            int myCounter;
            synchronized(lock) { 
                myCounter = ++thCounter;           
                System.out.printf("[%s - %d] Running in group: %s%n", getName(), myCounter, getThreadGroup().getName()); 
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    String errMsg = String.format("[%s - %d] Interrupted in group: %s%n", getName(), myCounter, getThreadGroup().getName());
                    throw new AssertionError(errMsg);
                 } catch (Exception e) {
                    String errMsg = String.format("[%s - %d] Exception in group: %s, errMsg: %s%n", getName(), myCounter, getThreadGroup().getName(), e.getMessage());
                    throw new AssertionError(errMsg);
                }
                System.out.printf("[%s - %d] Ended in group: %s%n", getName(), myCounter, getThreadGroup().getName());
            }
        }
        
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Boiled-down ThreadGroup Test (from threading-3) ===");
        System.out.println("=== One thread group, one thread ===");

        // Create two thread groups under the main thread group
        ThreadGroup group1 = new ThreadGroup("Group-1");

        // Create threads in each group
        Thread t1a = new Worker(group1, "T1-A");

        // Start all threads
        t1a.start();

        // === Comparison Tests ===
        int errorCount = 0;

        // Wait for threads to complete
        t1a.join(5000);

        // Verify thread counts are 0 after completion
        if (group1.activeCount() != 0) {
            System.out.println("*** ERROR: Group-1 should have 0 active threads");
            errorCount++;
        }

        // Summary
        Checkers.theEnd(errorCount);
    }
}

