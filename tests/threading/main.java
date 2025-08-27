// Hacked from https://www.tutorialspoint.com/java/java_thread_synchronization.htm

public class main {

    public static void main(String args[]) {

        String msg = "Threading tests with a parent thread (main) and 3 child threads";
        System.out.println(msg);

        PrintingSynced PSYNC = new PrintingSynced();

        MyThread T1 = new MyThread("T1", PSYNC);
        MyThread T2 = new MyThread("T2", PSYNC);
        MyThread T3 = new MyThread("T3", PSYNC);

        PSYNC.printNL("main: Threads T1/T2/T3 are runnable; will start them now");
        T1.start();
        T2.start();
        T3.start();

        // wait for threads to end
        PSYNC.printNL("main: Waiting for threads T1/T2/T3 to end");
        try {
            T1.join();
            PSYNC.printNL("main: T1 ended");
            T2.join();
            PSYNC.printNL("main: T2 ended");
            T3.join();
            PSYNC.printNL("main: T3 ended");
        } catch (Exception ex) {
            PSYNC.printNL("\nmain: Interrupted while waiting for threads !!");
            String errMsg = String.format("main: Exception reason: %s", ex.getMessage());
            PSYNC.printNL(errMsg);
        }
        PSYNC.printNL("main: End");
    }
}

class PrintingSynced {

    public synchronized void printNL(String msg) {
        System.out.println(msg);
    }

    public synchronized void printMsg(String name, String msg) {
        System.out.printf("%s: %s\n", name, msg);
    }

    public synchronized void printSomeLines(String name) {
        try {
            for (int ii = 5; ii > 0; ii--) {
                synchronized (this) {
                    System.out.printf("%s: %d\n", name, ii);
                }
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            synchronized (this) {
                System.out.println("\nprintSomeLines: Thread interrupted.");
                System.out.printf("printSomeLines: Exception reason: %s\n", ex.getMessage());
                return;
            }
        }
        
        // Add final newline.
        synchronized (this) {
            System.out.println();
        }
    }

}

class MyThread extends Thread {
    private Thread th;
    private String threadName;
    PrintingSynced PSYNC;

    MyThread(String name, PrintingSynced psync) {
        threadName = name;
        PSYNC = psync;
    }

    public void run() {
        PSYNC.printSomeLines(threadName);
        PSYNC.printMsg(threadName, "exiting");
    }

    public void start() {
        PSYNC.printMsg(threadName, "started");
        th = new Thread(this, threadName);
        th.start();
    }
}

