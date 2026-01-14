// Extract from threading-runnable

public class main {
    
    // Example 1: Implementing Runnable with a separate class
    static class CounterTask implements Runnable {
        private String name;
        private int count;
        
        public CounterTask(String argName, int argCount) {
            this.name = argName;
            this.count = argCount;
            System.out.printf("CounterTask <init> this.name=%s, this.count=%d\n", this.name, this.count);
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= this.count; i++) {
                System.out.printf("%s: %d of %d\n", this.name, i, this.count);
                try {
                    Thread.sleep(500); // Sleep for 500ms
                } catch (InterruptedException e) {
                    System.out.printf("%s was interrupted\n", this.name);
                    return;
                }
            }
            System.out.printf("%s is finished!\n", this.name);
        }
    }
    
    public static void main(String[] args) {
        // Create concrete Runnable instances.
        CounterTask task1 = new CounterTask("Thread-1", 2);
        CounterTask task2 = new CounterTask("Thread-2", 2);
        
        // Create Thread objects with concrete Runnable classes.
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        
        // Start threads.
        thread1.start();
        thread2.start();
        
        // Wait for threads to complete.
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
                
        // Survival is success.
        Checkers.theEnd(0);
    }
}
