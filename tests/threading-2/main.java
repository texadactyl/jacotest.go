public class main {

    static class MyThread extends Thread {
        public Object tryClone() throws CloneNotSupportedException {
            return super.clone();  // will throw CloneNotSupportedException
        }
    }
    
    static final boolean earlyExit = false;

    public static void main(String args[]) {

        System.out.println("Threading tests of functions operating on the current thread (main)");

        Thread cth = Thread.currentThread();
        
        int errorCount = 0;

        int activeCount = Thread.activeCount();
        errorCount += Checkers.checker("Current thread activeCount", 1, activeCount);

        try {
            MyThread dummy = new MyThread();
            Object obj = dummy.tryClone();  // should throw CloneNotSupportedException
            errorCount += 1;
            System.out.println("*** ERROR, failed to throw CloneNotSupportedException");
        } catch (CloneNotSupportedException ex) {
            System.out.println("ok, successfully threw CloneNotSupportedException");
        } catch (Exception ex) {
            errorCount += 1;
            System.out.println("*** ERROR, should have thrown CloneNotSupportedException");
            System.out.printf("Exception message: %s\n", ex.getMessage());
        }

        System.out.println("=== Begin dumpStack output for current thread .....");
        Thread.dumpStack();
        System.out.println("=== End dumpStack output for current thread.");
        
        /*
        Not deprecated but omited due to this API comment:
        "Due to the inherent race condition in this method, it is recommended that the method only be used for debugging and monitoring purposes."
        
        Thread[] tarray = new Thread[42];
        int sizeTarray = Thread.enumerate(tarray);
        errorCount += Checkers.checker("Current thread sizeTarray", 1, sizeTarray);
        */
        
        // Skipped: getAllStackTraces
        
        // Skipped: getContextClassLoader
        
        // Skipped: getDefaultUncaughtExceptionHandler
        
        String tName = cth.getName();
        errorCount += Checkers.checker("Specified thread's name", "main", tName);
        
        int tPriority = cth.getPriority();
        errorCount += Checkers.checker("Specified thread's priority", Thread.NORM_PRIORITY, tPriority);
        
        StackTraceElement[] steArray = cth.getStackTrace();
        System.out.printf("Specified steArray size: %d\n", steArray.length);
        if (steArray.length < 1) {
            errorCount += 1;
            System.out.println("*** ERROR, Specified steArray size should be > 0");
        }
        for (int ix = 0; ix < steArray.length; ++ix) {
            System.out.printf("[%d of %d]: ", ix+1, steArray.length);
            System.out.println(steArray[ix]);
        }
        
        if (earlyExit) {
            Checkers.theEnd(errorCount);
        }
        
        Thread.State cthState = cth.getState();
        errorCount += Checkers.checker("Specified thread's state", "RUNNABLE", cthState.toString());
        
        ThreadGroup tGroup = cth.getThreadGroup();
        errorCount += Checkers.checker("Specified thread's ThreadGroup", "main", tGroup.getName());
        
        // Skipped: getUncaughtExceptionHandler
        
        /* Skipped: 
            Object semaphore = new Object();
            synchronized(semaphore) {
                errorCount += Checkers.checker("Current thread holds a lock", true, Thread.holdsLock(semaphore));
            }
            errorCount += Checkers.checker("Current thread does not hold a lock", true, ! Thread.holdsLock(semaphore));
        */
        
        // Skipped: interrupt

        errorCount += Checkers.checker("Current thread interrupted?", false, cth.isInterrupted());
        
        errorCount += Checkers.checker("Specified thread is alive?", true, cth.isAlive());
        
        // Skipped: errorCount += Checkers.checker("Specified thread is a daemon?", false, cth.isDaemon());
        
        errorCount += Checkers.checker("Specified thread is virtual?", true, cth.isVirtual());
        
        // Skipped: join (4 functions)
        
        // Skipped: ofPlatform
        
        // Skipped: ofVirtual
        
        // Skipped: onSpinWait
        
        // Skipped: run
        
        // Skipped: setContextClassLoader
        
        // Skipped: setDaemon
        
        // Skipped: setDefaultUncaughtExceptionHandler
        
        String newName = "NewName";
        cth.setName(newName);
        errorCount += Checkers.checker("Specified thread's new name", newName, cth.getName());
        
        cth.setPriority(Thread.MIN_PRIORITY);
        errorCount += Checkers.checker("Specified thread's new priority (MIN_PRIORITY)", Thread.MIN_PRIORITY, cth.getPriority());
        cth.setPriority(Thread.MAX_PRIORITY);
        errorCount += Checkers.checker("Specified thread's new priority (MAX_PRIORITY)", Thread.MAX_PRIORITY, cth.getPriority());
        
        // Skipped: setUncaughtExceptionHandler
        
        // Skipped: sleep (3 functions)
        
        // Skipped: start
        
        // Skipped: startVirtualThread
        
        long tId = cth.threadId();
        System.out.printf("Specified thread's ID: %d\n", tId);
        if (tId < 1) {
            errorCount += 1;
            System.out.println("*** ERROR, Specified tId should be > 0");
        }
        
        System.out.printf("Specified thread's String representation: %s\n", cth.toString());
        
        Checkers.theEnd(errorCount);
    }
}

