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
            PSYNC.printNL("main: T1 joined");
            T2.join();
            PSYNC.printNL("main: T2 joined");
            T3.join();
            PSYNC.printNL("main: T3 joined");
        } catch (Exception ex) {
            PSYNC.printNL("main: Interrupted !!");
        }
        PSYNC.printNL("main: End");
    }
}

class PrintingSynced {

    public void printNL(String msg) {
        synchronized (PrintingSynced.class) {
            System.out.println(msg);
        }
    }

    public void printSomeLines(String name) {
        try {
            for (int ii = 5; ii > 0; ii--) {
                synchronized (PrintingSynced.class) {
                    System.out.print(name);
                    System.out.print(" printSomeLines ");
                    System.out.println(ii);
                }
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            System.out.println("\nprintSomeLines: Thread interrupted.");
        }
        System.out.println();
    }

    public void printMsg(String name, String msg) {
        synchronized (PrintingSynced.class) {
            System.out.print(name);
            System.out.print(": ");
            System.out.println(msg);
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

