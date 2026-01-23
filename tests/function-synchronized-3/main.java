/*
 * Synchronized Stress Test
 * Exercises:
 *  - invokevirtual
 *  - invokeinterface
 *  - invokespecial (constructors)
 *  - ACC_SYNCHRONIZED method entry/exit
 */

public class main {

    private static final int THREAD_COUNT = 64;
    private static final int ITERATIONS = 1000;
    private static final int YIELD_CONTROL = 0xF;

    public static void main(String[] args) throws InterruptedException {
        int errorCount = 0;

        ICounter counter = new Counter();
        ICounter interfaceCounter = new InterfaceCounter();

        Worker[] workers = new Worker[THREAD_COUNT];

        long start = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            workers[i] = new Worker(counter, interfaceCounter, i, ITERATIONS, YIELD_CONTROL);
            workers[i].start();
        }

        for (Worker w : workers) {
            w.join();
        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("Elapsed time (ms): %d%n", elapsed);

        long expected = (long) THREAD_COUNT * ITERATIONS;

        errorCount += Checkers.checker("Counter value", expected, counter.get());
        errorCount += Checkers.checker("InterfaceCounter value", expected, interfaceCounter.get());

        Checkers.theEnd(errorCount);
    }
}

/* ------------------------------------------------------ */
/* Interface                                               */
/* ------------------------------------------------------ */

interface ICounter {
    void increment();
    long get();
}

/* ------------------------------------------------------ */
/* Concrete synchronized implementations                   */
/* ------------------------------------------------------ */

class Counter implements ICounter {

    private long value = 0;

    public Counter() {
        // invokespecial
    }

    @Override
    public synchronized void increment() {
        value++;

        if ((value & 0xFF) == 0) {
            try {
                wait(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public synchronized long get() {
        return value;
    }
}

class InterfaceCounter implements ICounter {

    private long value = 0;

    public InterfaceCounter() {
        // invokespecial
    }

    @Override
    public synchronized void increment() {
        value++;

        if ((value & 0xFF) == 0) {
            try {
                wait(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public synchronized long get() {
        return value;
    }
}

/* ------------------------------------------------------ */
/* Worker thread                                           */
/* ------------------------------------------------------ */

class Worker extends Thread {

    private final ICounter counter;
    private final ICounter ifaceCounter;
    private final int iterations;
    private final int yield_control;

    Worker(ICounter counter,
           ICounter ifaceCounter,
           int id,
           int iterations,
           int yield_control) {

        this.counter = counter;
        this.ifaceCounter = ifaceCounter;
        this.iterations = iterations;
        this.yield_control = yield_control;

        setName(String.format("Worker-%d", id));
    }

    @Override
    public void run() {
        for (int ix = 0; ix < iterations; ix++) {
            counter.increment();        // invokevirtual
            ifaceCounter.increment();   // invokeinterface

            if ((ix & yield_control) == 0) {
                Thread.yield();
            }
        }
    }
}

