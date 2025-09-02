// Converted from Richard's Python program.

public class main {

    // Constants
    private static final double C = 299792458.0;     // m/s
    private static final double AU = 1.495978707e11; // m
    private static final double MAX_PERCENT = 0.01;

    // Warp factor to m/s
    public static double wf2mps(double wf) {
        return Math.pow(wf, 10.0 / 3.0) * C;
    }

    // Warp factor to AU/s
    public static double wf2aups(double wf) {
        return wf2mps(wf) / AU;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        
        // Warp factors list
        double[] wfList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9.9};

        // Print table header
        System.out.printf("%-8s %18s %18s %18s %18s%n", 
                "Warp", "Speed (m/s)", "Speed (km/s)", "multiple of c", "Speed (AU/s)");
        System.out.println("--------------------------------------------------------------------------------------");

        // Print rows with aligned decimals and highlight warp factors > 9
        for (double wf : wfList) {
            double mPerSec = wf2mps(wf);
            double kmPerSec = mPerSec / 1000.0;
            double multipleOfC = mPerSec / C;
            double auPerSec = wf2aups(wf);
            String warpLabel = wf > 9.0 ? String.format("%.1f*", wf) : String.format("%.1f", wf);
            if (wf > 9.0) {
                warpLabel = String.format("%.1f*", wf);
                errorCount += Checkers.withinTolerance("mPerSec", 6.246039e+11, mPerSec);
                errorCount += Checkers.withinTolerance("kmPerSec", 6.246039e+08, kmPerSec);
                errorCount += Checkers.withinTolerance("multipleOfC", 2.083454e+03, multipleOfC);
                errorCount += Checkers.withinTolerance("auPerSec", 4.175219e+00, auPerSec);
            } else {
                warpLabel = String.format("%.1f", wf);
            }
            System.out.printf("%-8s %18.3e %18.3e %18.6f %18.6f%n", 
                    warpLabel, mPerSec, kmPerSec, multipleOfC, auPerSec);
        }
        
        Checkers.theEnd(errorCount);
    }
}

