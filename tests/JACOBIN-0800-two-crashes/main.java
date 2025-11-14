public class main {

    public static void main(String[] args) {
        // Triangular pluck at nPluck
        System.out.println("main: begin");
        Subbie1 subbie = new Subbie1(); 
        //Subbie2 subbie = new Subbie2(); 
        subbie.initString();
        System.out.println("main: end");
    }
    
}

// This version crashes. Contains arrays.
class Subbie1 {
    private static final int MAX = 3001;
    private final double[] y = new double[MAX];
    private final double[] yOld = new double[MAX];
    
    private double amplitude = 0.5;
    private int nPluck = 42;
    private double tension = 4.2;
    
    public void initString() {
        // Triangular pluck at nPluck
        System.out.println("DEBUG initString: begin");
        for (int ii = 0; ii < MAX; ii++) {
            y[ii] = yOld[ii] = amplitude * ii / nPluck;
            System.out.printf("DEBUG initString: (%d) %f\n", ii, y[ii]);
        }
    }
}

// This version also crashes. No arrays.
class Subbie2 {
    private static final int MAX = 3001;
    private double y, yOld;
    
    private double amplitude = 0.5;
    private int nPluck = 42;
    private double tension = 4.2;
    
    public void initString() {
        // Triangular pluck at nPluck
        System.out.println("DEBUG initString: begin");
        for (int ii = 0; ii < MAX; ii++) {
            y = yOld = amplitude * ii / nPluck;
            System.out.printf("DEBUG initString: (%d) %f\n", ii, y);
        }
    }
}
