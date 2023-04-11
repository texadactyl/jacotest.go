import java.lang.Thread;

public class main {

    public static void main(String args[]) throws InterruptedException {
        
        System.out.println("I will timeout!");
        while (true) {
            Thread.sleep(100000);
        }

    }
}
