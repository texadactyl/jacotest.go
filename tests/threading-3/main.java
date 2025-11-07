public class main {

    // Simple worker thread that sleeps briefly
    static class Worker extends Thread {
        public Worker(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            System.out.printf("[%s] Running in group: %s%n", getName(), getThreadGroup().getName());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ThreadGroup Test ===");

        // Create two thread groups under the main thread group
        ThreadGroup group1 = new ThreadGroup("Group-1");
        ThreadGroup group2 = new ThreadGroup("Group-2");

        // Create threads in each group
        Thread t1a = new Worker(group1, "T1-A");
        Thread t1b = new Worker(group1, "T1-B");
        Thread t2a = new Worker(group2, "T2-A");
        Thread t2b = new Worker(group2, "T2-B");

        // Start all threads
        t1a.start();
        t1b.start();
        t2a.start();
        t2b.start();

        // === Comparison Tests ===
        int errorCount = 0;

        // 1. Verify names
        if (!group1.getName().equals("Group-1")) {
            System.out.println("*** ERROR: group1 name mismatch");
            errorCount++;
        }
        if (!group2.getName().equals("Group-2")) {
            System.out.println("*** ERROR: group2 name mismatch");
            errorCount++;
        }

        // 2. Verify different groups are not equal
        if (group1.equals(group2)) {
            System.out.println("*** ERROR: group1 should not equal group2");
            errorCount++;
        }

        // 3. Verify threads belong to correct groups
        if (t1a.getThreadGroup() != group1) {
            System.out.println("*** ERROR: T1-A not in Group-1");
            errorCount++;
        }
        if (t2b.getThreadGroup() != group2) {
            System.out.println("*** ERROR: T2-B not in Group-2");
            errorCount++;
        }

        // 4. Verify parent group of both is main thread group
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        if (group1.getParent() != mainGroup || group2.getParent() != mainGroup) {
            System.out.println("*** ERROR: Parent of groups should be main thread group");
            errorCount++;
        }

        // Wait for threads to complete
        t1a.join();
        t1b.join();
        t2a.join();
        t2b.join();

        // 5. Verify thread counts are 0 after completion
        if (group1.activeCount() != 0) {
            System.out.println("*** ERROR: Group-1 should have 0 active threads");
            errorCount++;
        }
        if (group2.activeCount() != 0) {
            System.out.println("*** ERROR: Group-2 should have 0 active threads");
            errorCount++;
        }

        // Summary
        Checkers.theEnd(errorCount);
    }
}

