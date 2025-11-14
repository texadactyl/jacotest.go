import java.io.FileWriter;
import java.io.IOException;

public class GuitarString {

    private static final int MAX = 3001;
    private static final double PI = 3.14159265;

    private final double[] yNew = new double[MAX];
    private final double[] y = new double[MAX];
    private final double[] yOld = new double[MAX];

    // Physical parameters
    private int nString;        
    private double length;      
    private double c;           
    private double tension;     
    private double radius;      
    private double rho;         
    private double mu;          

    // Numerical parameters
    private double t;
    private double dt;          
    private double dx;          
    private double r2;          

    // Simulation configuration
    private double frequency;   
    private double beta;        
    private int nPluck;         
    private double amplitude;   
    private double tPlot;       
    private double tEnd;        
    private int nSmooth;        

    private final String yStringFileBase = "y_string.";

    // Added damping for more realistic bridge force
    private final double damp = 0.0008;  

    public GuitarString() {
        initDefaults();
        initString();
    }

    private void initDefaults() {
        t = 0.0;
        nSmooth = 0; 
        nString = 1000;
        length = 0.65;          
        rho = 8000;             
        radius = 0.24 / 2.0 / 1000.0; 
        frequency = 247.0;      
        beta = 1.0 / 5.0;       
        amplitude = 0.005;      

        c = 2.0 * length * frequency;       
        mu = PI * radius * radius * rho;    
        tension = c * c * mu;               
        dx = length / nString;                  

        // Courant ratio less than 1 for smoother propagation
        r2 = 0.9;                                      
        dt = Math.sqrt(r2) * dx / c;
        
        nPluck = (int) (beta * nString);
        tPlot = 0.05 / frequency;   
        tEnd = 10.0 / frequency;    // run for 5 periods
        
        System.out.printf("c = %f m/s\n", c);
        System.out.printf("mu = %f kg/m\n", mu);
        System.out.printf("tension = %f N\n", tension);
        System.out.printf("dx = %f m\n", dx);
        System.out.printf("dt = %f s\n", dt);
        System.out.printf("nPluck = %d bits\n", nPluck);
        System.out.printf("tPlot = %f\n", tPlot);
        System.out.printf("tEnd = %f s\n", tEnd);

        assert nString < MAX;
        assert nPluck < MAX;
        assert (nString - nSmooth) < MAX;
    }

    private void initString() {
        double amp = amplitude;
        double slope;

        // Triangular pluck at nPluck
        for (int ii = 0; ii <= nString; ii++) {
            if (ii <= nPluck) {
                yOld[ii] = amplitude * ii / nPluck;
                y[ii] = yOld[ii];
            } else {
                yOld[ii] = amplitude * (nString - ii) / (nString - nPluck);
                y[ii] = yOld[ii];
            }
        }
        double force = tension * (y[1] - y[0]);
        System.out.printf("Initial force = %.10f N\n", force);
    }

    public double pluck(boolean saveYplots) {
        double tNextPlot = tPlot;
        int iPlot = 1;
        double force = 0.0;

        try (FileWriter forceFile = new FileWriter("bridge_f")) {
            while (t < tEnd) {
                propagate();
                update();

                // Write force on the bridge
                force = tension * (y[1] - y[0]);
                String line = String.format("%8.6f\t%.10f%n", 1000.0 * t, force);
                forceFile.write(line, 0, line.length());

                t += dt;
                tNextPlot -= dt;

                if (tNextPlot < 0.0) {
                    if (saveYplots)
                        saveProfile(iPlot);
                    iPlot++;
                    tNextPlot = tPlot;
                }
            }
        } catch (IOException e) {
            System.err.printf("*** ERROR writing bridge_f: %s\n", e.getMessage());
            System.exit(1);
        }
        
        return force;
    }

    private void propagate() {
        for (int ii = 1; ii < nString; ii++) {
            double lap = y[ii + 1] + y[ii - 1] - 2.0 * y[ii];
            yNew[ii] = (2.0 - damp) * y[ii] - (1.0 - damp) * yOld[ii] + r2 * lap;
        }
    }

    private void update() {
        for (int ii = 0; ii <= nString; ii++) {
            yOld[ii] = y[ii];
            y[ii] = yNew[ii];
        }
    }

    private void saveProfile(int suffix) {
        String name = yStringFileBase + suffix;
        double offset = amplitude * suffix / 10.0;

        try (FileWriter fp = new FileWriter(name)) {
            for (int ii = 0; ii <= nString; ii++) {
                String line = String.format("%g\t%g%n", ii * dx, y[ii] - offset);
                fp.write(line, 0, line.length());
            }
        } catch (IOException e) {
            System.err.printf("*** ERROR writing %s: %s\n", name, e.getMessage());
            System.exit(1);
        }
    }

    public void smoothString() {
        for (int jj = nSmooth; jj < nString - nSmooth; jj++) {
            double val = 0.0;
            int count = 0;
            for (int ii = -nSmooth; ii <= nSmooth; ii++) {
                val += y[jj + ii];
                count++;
            }
            y[jj] = val / count;
        }
    }

    public double[] getY() { return y.clone(); }
    public double getT() { return t; }
    public double getDx() { return dx; }
    public int getNString() { return nString; }

}

