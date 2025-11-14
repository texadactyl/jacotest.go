/**
    Simulates a perfectly flexible, slightly damped guitar string.

    Based on N. Giordano’s model (2004), with minimal modifications for smoother bridge force output.

    Original C code: https://www.physics.purdue.edu/~hisao/book/www/examples/chap11/guitar.c

    Converted to Java by chatGPT.

    Refined to be more realistic of plucked-string behaviour:
    * r2 = 0.9 instead of 1.0 for smoother wave propagation
    * Added small damping (damp = 0.0008)
    * Increased spatial resolution (nString = 1000)
    * Extended simulation time (tEnd = 10 / frequency)
*/

public class main {

    private static boolean saveYplots = false;
    
    public static void main(String[] args) {
        System.out.println("Starting guitar string simulation...");
        GuitarString gstring = new GuitarString();
        long t1, t2;
        
        t1 = System.currentTimeMillis();
        double finalForce = gstring.pluck(saveYplots);
        t2 = System.currentTimeMillis();
        double seconds = (double) (t2 - t1) / 1000.0;
        System.out.printf("Simulation complete after %.1f seconds. Final force = %.10f N\n", seconds, finalForce);
        
        int errorCount = Checkers.checker("Final force almost zero?", true, finalForce < 0.0003);
        Checkers.theEnd(errorCount);
    }
    
}

