// Hacked from https://www.tutorialspoint.com/java/java_thread_synchronization.htm

public class main {

    // TODO: needs qualitative tests
    
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
        PSYNC.printNL("main: Joining threads T1/T2/T3 .....");
        try {
            T1.join();
            PSYNC.printNL("main: T1 ended");
            T2.join();
            PSYNC.printNL("main: T2 ended");
            T3.join();
            PSYNC.printNL("main: T3 ended");
        } catch (InterruptedException ex) {
            String errMsg = String.format("\nmain: Interrupted during join on a thread !!\n%s", ex.getMessage());
            PSYNC.printNL(errMsg);
        } catch (Exception ex) {
            String errMsg = String.format("\nmain: Unexpected exception during join on a thread !!\n%s", ex.getMessage());
            PSYNC.printNL(errMsg);
        }
        PSYNC.printNL("main: Joined threads T1/T2/T3.");
        
        Checkers.theEnd(0);
    }
}

class PrintingSynced {

    public synchronized void printNL(String msg) {
        System.out.println(msg);
    }

    public synchronized void printNamedMsg(String name, String msg) {
        System.out.printf("%s: %s\n", name, msg);
    }

    public synchronized void printSomeLines(String name) {
        for (int ii = 10; ii > 0; ii--) {
            System.out.printf("%s: %d\n", name, ii);
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

    @Override
    public void run() {
        PSYNC.printSomeLines(threadName);
        PSYNC.printNamedMsg(threadName, "exiting");
    }

    public void start() {
        PSYNC.printNamedMsg(threadName, "started");
        th = new Thread(this, threadName);
        th.start();
    }
}

