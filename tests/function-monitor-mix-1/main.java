/*
 * Comprehensive Synchronization and Invocation Test
 * Tests: invokestatic, invokeinterface, invokespecial, invokevirtual,
 *        monitorenter, monitorexit, wait, notify, notifyAll, interrupt
 
 Expected output something like this:
 
        === Test 1: Producer-Consumer ===
        ok Items produced ::: expected = observed = 30
        ok Items consumed ::: expected = observed = 30
        === Test 2: Thread Interruption ===
        ok Thread was interrupted ::: expected = observed = true
        === Test 3: notifyAll ===
        ok All threads released ::: expected = observed = 5
        === Test 4: Static Sync ===
        ok Static counter ::: expected = observed = 1000
        === Test 5: Synchronized Blocks ===
        ok Block counter ::: expected = observed = 500
        === Test 6: Multiple Lock Objects ===
        ok Counter A ::: expected = observed = 100
        ok Counter B ::: expected = observed = 100

        ========
        Success!
        ========

 */

public class main {
    private static final int PRODUCER_COUNT = 3;
    private static final int CONSUMER_COUNT = 3;
    private static final int ITEMS_TO_PRODUCE = 10;
    
    public static void main(String[] args) throws InterruptedException {
        int errorCount = 0;
        
        // Test 1: Producer-Consumer with wait/notify
        System.out.println("=== Test 1: Producer-Consumer ===");
        SharedQueue queue = new SharedQueue(5);
        Thread[] producers = new Thread[PRODUCER_COUNT];
        Thread[] consumers = new Thread[CONSUMER_COUNT];
        
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            producers[i] = new Producer(queue, i, ITEMS_TO_PRODUCE);
            producers[i].start();
        }
        
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            consumers[i] = new Consumer(queue, i);
            consumers[i].start();
        }
        
        for (Thread p : producers) {
            p.join();
        }
        
        queue.setDone();
        
        for (Thread c : consumers) {
            c.join();
        }
        
        errorCount += Checkers.checker("Items produced", 
            PRODUCER_COUNT * ITEMS_TO_PRODUCE, queue.getTotalProduced());
        errorCount += Checkers.checker("Items consumed", 
            PRODUCER_COUNT * ITEMS_TO_PRODUCE, queue.getTotalConsumed());
        
        // Test 2: Thread interruption
        System.out.println("=== Test 2: Thread Interruption ===");
        WaitingThread waiter = new WaitingThread();
        waiter.start();
        Thread.sleep(100); // Let it start waiting
        waiter.interrupt();
        waiter.join();
        
        errorCount += Checkers.checker("Thread was interrupted", 
            true, waiter.wasInterrupted());
        
        // Test 3: notifyAll with multiple waiters
        System.out.println("=== Test 3: notifyAll ===");
        Barrier barrier = new Barrier(5);
        Thread[] waiters = new Thread[5];
        for (int i = 0; i < 5; i++) {
            waiters[i] = new BarrierThread(barrier, i);
            waiters[i].start();
        }
        
        Thread.sleep(100); // Let them all wait
        barrier.releaseAll();
        
        for (Thread w : waiters) {
            w.join();
        }
        
        errorCount += Checkers.checker("All threads released", 
            5, barrier.getReleasedCount());
        
        // Test 4: Static synchronization
        System.out.println("=== Test 4: Static Sync ===");
        StaticCounter.reset();
        Thread[] staticThreads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            staticThreads[i] = new StaticCounterThread(100);
            staticThreads[i].start();
        }
        
        for (Thread t : staticThreads) {
            t.join();
        }
        
        errorCount += Checkers.checker("Static counter", 
            1000L, StaticCounter.getCount());
        
        // Test 5: Synchronized blocks (monitorenter/monitorexit)
        System.out.println("=== Test 5: Synchronized Blocks ===");
        Object lock = new Object();
        BlockCounter blockCounter = new BlockCounter(lock);
        Thread[] blockThreads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            blockThreads[i] = new BlockRunner(blockCounter, 100);
            blockThreads[i].start();
        }
        
        for (Thread t : blockThreads) {
            t.join();
        }
        
        errorCount += Checkers.checker("Block counter", 
            500, blockCounter.getCount());
        
        // Test 6: Multiple synchronized objects
        System.out.println("=== Test 6: Multiple Lock Objects ===");
        TwoLockCounter twoLockCounter = new TwoLockCounter();
        Thread[] twoLockThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            twoLockThreads[i] = new TwoLockThread(twoLockCounter, 50);
            twoLockThreads[i].start();
        }
        
        for (Thread t : twoLockThreads) {
            t.join();
        }
        
        errorCount += Checkers.checker("Counter A", 100, twoLockCounter.getCountA());
        errorCount += Checkers.checker("Counter B", 100, twoLockCounter.getCountB());
        
        Checkers.theEnd(errorCount);
    }
}

/**
 * Bounded queue for producer-consumer
 * Uses wait/notify for coordination
 */
class SharedQueue {
    private final int[] buffer;
    private int count = 0;
    private int putIndex = 0;
    private int takeIndex = 0;
    private boolean done = false;
    private int totalProduced = 0;
    private int totalConsumed = 0;
    
    public SharedQueue(int capacity) { // invokespecial
        this.buffer = new int[capacity];
    }
    
    public synchronized void put(int value) throws InterruptedException {
        while (count == buffer.length) {
            wait(); // Wait for space
        }
        buffer[putIndex] = value;
        putIndex = (putIndex + 1) % buffer.length;
        count++;
        totalProduced++;
        notifyAll(); // Wake up consumers
    }
    
    public synchronized int take() throws InterruptedException {
        while (count == 0 && !done) {
            wait(); // Wait for items
        }
        if (count == 0 && done) {
            return -1; // Signal end
        }
        int value = buffer[takeIndex];
        takeIndex = (takeIndex + 1) % buffer.length;
        count--;
        totalConsumed++;
        notifyAll(); // Wake up producers
        return value;
    }
    
    public synchronized void setDone() {
        done = true;
        notifyAll(); // Wake up all waiting consumers
    }
    
    public synchronized int getTotalProduced() {
        return totalProduced;
    }
    
    public synchronized int getTotalConsumed() {
        return totalConsumed;
    }
}

class Producer extends Thread {
    private final SharedQueue queue;
    private final int id;
    private final int itemsToProduce;
    
    Producer(SharedQueue queue, int id, int itemsToProduce) {
        this.queue = queue;
        this.id = id;
        this.itemsToProduce = itemsToProduce;
        setName(String.format("Producer-%d", id));
    }
    
    @Override
    public void run() { // invokevirtual override
        try {
            for (int i = 0; i < itemsToProduce; i++) {
                int value = id * 1000 + i;
                queue.put(value); // invokevirtual
                Thread.yield();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer extends Thread {
    private final SharedQueue queue;
    private final int id;
    
    Consumer(SharedQueue queue, int id) {
        this.queue = queue;
        this.id = id;
        setName(String.format("Consumer-%d", id));
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                int value = queue.take();
                if (value == -1) break; // Done signal
                Thread.yield();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Tests thread interruption
 */
class WaitingThread extends Thread {
    private boolean interrupted = false;
    private Object lock = new Object();
    
    @Override
    public void run() {
        synchronized (lock) { // monitorenter/monitorexit
            try {
                lock.wait(10000); // Wait with timeout
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
    }
    
    public boolean wasInterrupted() {
        return interrupted;
    }
}

/**
 * Tests notifyAll with multiple waiters
 */
class Barrier {
    private boolean released = false;
    private int releasedCount = 0;
    private final int expectedCount;
    
    public Barrier(int expectedCount) {
        this.expectedCount = expectedCount;
    }
    
    public synchronized void await() throws InterruptedException {
        while (!released) {
            wait(); // Wait for releaseAll
        }
        releasedCount++;
    }
    
    public synchronized void releaseAll() {
        released = true;
        notifyAll(); // Wake all waiting threads
    }
    
    public synchronized int getReleasedCount() {
        return releasedCount;
    }
}

class BarrierThread extends Thread {
    private final Barrier barrier;
    private final int id;
    
    BarrierThread(Barrier barrier, int id) {
        this.barrier = barrier;
        this.id = id;
        setName(String.format("Barrier-%d", id));
    }
    
    @Override
    public void run() {
        try {
            barrier.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Tests static synchronization
 */
class StaticCounter {
    private static long count = 0;
    
    public static synchronized void increment() { // invokestatic
        count++;
        if ((count & 0xFF) == 0) {
            try {
                StaticCounter.class.wait(1); // Test class object wait
            } catch (InterruptedException ignored) {
            }
        }
    }
    
    public static synchronized long getCount() {
        return count;
    }
    
    public static synchronized void reset() {
        count = 0;
    }
}

class StaticCounterThread extends Thread {
    private final int iterations;
    
    StaticCounterThread(int iterations) {
        this.iterations = iterations;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            StaticCounter.increment(); // invokestatic
        }
    }
}

/**
 * Tests synchronized blocks (monitorenter/monitorexit)
 */
class BlockCounter {
    private final Object lock;
    private int count = 0;
    
    BlockCounter(Object lock) {
        this.lock = lock;
    }
    
    public void increment() {
        synchronized (lock) { // Explicit monitorenter/monitorexit
            count++;
        }
    }
    
    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
}

class BlockRunner extends Thread {
    private final BlockCounter counter;
    private final int iterations;
    
    BlockRunner(BlockCounter counter, int iterations) {
        this.counter = counter;
        this.iterations = iterations;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            counter.increment(); // invokevirtual
        }
    }
}

/**
 * Tests multiple lock objects
 */
class TwoLockCounter {
    private final Object lockA = new Object();
    private final Object lockB = new Object();
    private int countA = 0;
    private int countB = 0;
    
    public void incrementA() {
        synchronized (lockA) {
            countA++;
        }
    }
    
    public void incrementB() {
        synchronized (lockB) {
            countB++;
        }
    }
    
    public int getCountA() {
        synchronized (lockA) {
            return countA;
        }
    }
    
    public int getCountB() {
        synchronized (lockB) {
            return countB;
        }
    }
}

class TwoLockThread extends Thread {
    private final TwoLockCounter counter;
    private final int iterations;
    
    TwoLockThread(TwoLockCounter counter, int iterations) {
        this.counter = counter;
        this.iterations = iterations;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            if (i % 2 == 0) {
                counter.incrementA();
            } else {
                counter.incrementB();
            }
        }
    }
}

