/*
Synchronized Stress Test
(no synchronized static methods)
*/

public class main {

    private static final int THREAD_COUNT = 64;
    private static final int ITERATIONS = 1000;
    private static final int YIELD_CONTROL = 0xF;

    public static void main(String[] args) throws InterruptedException {
        int errorCount = 0;

        Counter counter = new Counter();

        Worker[] workers = new Worker[THREAD_COUNT];

        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            workers[i] = new Worker(counter, i, ITERATIONS, YIELD_CONTROL);
            workers[i].start();
        }
        for (Worker w : workers) {
            w.join();
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("Elapsed time (ms): %d\n", elapsed);

        errorCount += Checkers.checker(
            "Expected instance count",
            (long) (THREAD_COUNT * ITERATIONS),
            counter.get()
        );

        Checkers.theEnd(errorCount);
    }
}

/**
 * Shared object with synchronized instance methods
 */
class Counter {
    private long value = 0;

    public synchronized void increment() {
        value++;

        if ((value & 0xFF) == 0) {
            try {
                wait(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public synchronized long get() {
        return value;
    }
}

/**
 * Thread subclass
 */
class Worker extends Thread {

    private final Counter counter;
    private final int iterations;
    private final int yield_control;

    Worker(Counter counter,
           int id,
           int iterations,
           int yield_control) {

        this.counter = counter;
        this.iterations = iterations;
        this.yield_control = yield_control;

        setName(String.format("Worker-%d", id));
    }

    @Override
    public void run() {
        for (int ix = 0; ix < iterations; ix++) {
            counter.increment();          // invokevirtual (ACC_SYNCHRONIZED)
 
            if ((ix & yield_control) == 0) {
                Thread.yield();
            }
        }
    }
}

