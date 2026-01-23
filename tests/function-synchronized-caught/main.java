/*
 * Synchronized Stress Test
 * Exercises:
 *  - invokevirtual
 *  - invokeinterface
 *  - invokespecial (constructors)
 *  - ACC_SYNCHRONIZED method entry/exit
 *  - wait()
 *  - caught + uncaught exceptions
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
/* Custom exception                                        */
/* ------------------------------------------------------ */

class Boom extends RuntimeException {
    static final long serialVersionUID = 42;
    Boom(String msg) {
        super(msg);
    }
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

        // Exception thrown and caught inside synchronized method
        if ((value & 0x7F) == 0) {
            try {
                throw new Boom("boom");
            } catch (Boom ignored) {
            }
        }

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

        if ((value & 0x7F) == 0) {
            try {
                throw new Boom("caught boom");
            } catch (Boom ignored) {
            }
        }

        if ((value & 0x1FF) == 0) {
            throw new Boom("uncaught boom");
        }

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
            try {
                counter.increment();        // invokevirtual
                ifaceCounter.increment();   // invokeinterface
            } catch (Boom ignored) {
                // expected: monitor must already be released
            }

            if ((ix & yield_control) == 0) {
                Thread.yield();
            }
        }
    }
}

