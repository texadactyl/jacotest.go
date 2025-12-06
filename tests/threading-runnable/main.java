// from AI Claude

public class main {
    
    // Example 1: Implementing Runnable with a separate class
    static class CounterTask implements Runnable {
        private String name;
        private int count;
        
        public CounterTask(String name, int count) {
            this.name = name;
            this.count = count;
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= count; i++) {
                System.out.println(name + ": " + i);
                try {
                    Thread.sleep(500); // Sleep for 500ms
                } catch (InterruptedException e) {
                    System.out.println(name + " was interrupted");
                    return;
                }
            }
            System.out.println(name + " finished!");
        }
    }
    
    // Example 2: Shared resource with synchronization
    static class BankAccount {
        private int balance = 1000;
        
        public synchronized void withdraw(String person, int amount) {
            if (balance >= amount) {
                System.out.println(person + " is withdrawing " + amount);
                try {
                    Thread.sleep(100); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance -= amount;
                System.out.println(person + " withdrew " + amount + 
                                   ". Balance: " + balance);
            } else {
                System.out.println(person + " cannot withdraw " + amount + 
                                   ". Insufficient funds. Balance: " + balance);
            }
        }
        
        public int getBalance() {
            return balance;
        }
    }
    
    // Example 3: Named inner class for withdrawal
    static class WithdrawalTask implements Runnable {
        private BankAccount account;
        private String person;
        private int amount;
        
        public WithdrawalTask(BankAccount account, String person, int amount) {
            this.account = account;
            this.person = person;
            this.amount = amount;
        }
        
        @Override
        public void run() {
            account.withdraw(person, amount);
        }
    }
    
    // Example 4: Simple message printer
    static class MessagePrinter implements Runnable {
        private String message;
        private int times;
        
        public MessagePrinter(String message, int times) {
            this.message = message;
            this.times = times;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                System.out.println(message + ": " + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Threading ===\n");
        
        // Create concrete Runnable instances (not interface references)
        CounterTask task1 = new CounterTask("Thread-1", 5);
        CounterTask task2 = new CounterTask("Thread-2", 5);
        
        // Create Thread objects with concrete Runnable classes
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        
        // Start threads
        thread1.start();
        thread2.start();
        
        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== Example 2: Message Printer ===\n");
        
        // Using concrete class directly
        MessagePrinter printer1 = new MessagePrinter("Printer-A", 3);
        MessagePrinter printer2 = new MessagePrinter("Printer-B", 3);
        
        Thread thread3 = new Thread(printer1);
        Thread thread4 = new Thread(printer2);
        
        thread3.start();
        thread4.start();
        
        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== Example 3: Shared Resource with Synchronization ===\n");
        
        // Shared bank account
        BankAccount account = new BankAccount();
        
        // Multiple threads trying to withdraw using concrete classes
        WithdrawalTask withdraw1 = new WithdrawalTask(account, "Person-1", 600);
        WithdrawalTask withdraw2 = new WithdrawalTask(account, "Person-2", 500);
        WithdrawalTask withdraw3 = new WithdrawalTask(account, "Person-3", 400);
        
        Thread t1 = new Thread(withdraw1);
        Thread t2 = new Thread(withdraw2);
        Thread t3 = new Thread(withdraw3);
        
        t1.start();
        t2.start();
        t3.start();
        
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nFinal balance: " + account.getBalance());
        System.out.println("\n=== All examples completed ===");
        
        Checkers.theEnd(0);
    }
}
