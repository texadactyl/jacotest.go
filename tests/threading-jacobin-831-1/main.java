public class main {

    public static void main(String[] args) {
    
        int errorCount = 0;

        // Create a new thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Print "hello" to standard output
                System.out.println("Hello from Threddie!");
            }
        });

        // Start the thread
        thread.start();

        // Optionally, you can wait for the thread to finish
        try {
            thread.join(); // Waits for the thread to finish
            System.out.println("That thread is history!");
        } catch (InterruptedException e) {
            System.out.println("*** ERROR, Interrupted!");
            errorCount += 1;
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("*** ERROR, Oy vey!");
            errorCount += 1;
            e.printStackTrace();
        }
        
        // Survival is success.
        Checkers.theEnd(errorCount);

    }

}
