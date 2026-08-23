import java.util.HexFormat;

public class main {
    public static void main(String[] args) {
        int NLOOPS = 10000000;
        System.out.printf("Number of loops: %d\n", NLOOPS);
        
        final long t1 = System.currentTimeMillis();
        for (int ix = 0; ix < NLOOPS; ix++) {
            nothingBurger();
        }
        final long t2 = System.currentTimeMillis();
        
        double elapsedSeconds = (double)(t2 - t1) / 1000.0;
        System.out.printf("Elapsed time (seconds): %.3f\n", elapsedSeconds);
        
        Checkers.theEnd(0);
    }
    
    private static void nothingBurger() {
    }

}

