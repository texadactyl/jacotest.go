public class main {

    /*
        Compute Lagrange Points for Earth and Mars with respect to the Sun
        ==================================================================

        Even though Mars is 50% farther from the Sun than the Earth, 
        it's mass is only 11% that of the Earth. 
        So, while the distances to Earth's Lagrange points are 
        about 1% of the distance to the Sun for Earth, 
        those of Mars are only about 0.5% of the distance to the Sun for Mars.

        The values for the distance from the planets to the Sun and to 
        their Sun-associated L1 and L2 points look like this.

        a_Earth:     149598023  km (semi-major axis)
        Sun-Earth L1:  1491524  km
        Sun-Earth L2:  1501504  km
        Earth r_Hill:  1496531  km

        a_Mars:      227939200  km (semi-major axis)
        Sun-Mars L1:   1082311  km
        Sun-Mars L2:   1085748  km
        Mars r_Hill:   1084032  km
    */
    
    private static final double ONE_THIRD = 1.0 / 3.0;
    
    // Interface for functions to be used with Brent's method
    interface Function {
        double evaluate(double x, double R, double M1, double M2);
    }
    
    public static void main(String[] args) {
        int errorCount = 0;
        System.out.println("Compute the L1, L2 points + hill radius for Earth and Mars");
        
        double aEarth = 149598023.0;   // Earth's semi-major axis (km)
        double aMars = 227939200.0;    // Mars' semi-major axis (km)
        double rLow = 1000000.0;       // 1.0 million km (lower guess)
        double rHigh = 1600000.0;      // 1.6 million km (upper guess)
        
        double mSun = 1.9886E+30;      // approximate mass (kg)
        double mEarth = 5.9724E+24;    // approximate mass (kg)
        double mMars = 6.4171E+23;     // approximate mass (kg)
        
        // Calculate Hill radii
        double rHillEarth = rHill(aEarth, mSun, mEarth);
        double rHillMars = rHill(aMars, mSun, mMars);
        
        // Define function references
        Function l1Function = main::solveL1;
        Function l2Function = main::solveL2;
        
        // For Earth:
        double rL1Earth = brentq(l1Function, rLow, rHigh, aEarth, mSun, mEarth);
        double rL2Earth = brentq(l2Function, rLow, rHigh, aEarth, mSun, mEarth);
        
        // For Mars:
        double rL1Mars = brentq(l1Function, rLow, rHigh, aMars, mSun, mMars);
        double rL2Mars = brentq(l2Function, rLow, rHigh, aMars, mSun, mMars);
        
        // Check results.
        errorCount += Checkers.withinTolerance("Sun-Earth L1 (km)", 1491524.0, rL1Earth);
        errorCount += Checkers.withinTolerance("Sun-Earth L2 (km)", 1501504.0, rL2Earth);
        errorCount += Checkers.withinTolerance("Sun-Earth r_hill (km)", 1496531.0, rHillEarth);
        errorCount += Checkers.withinTolerance("Sun-Mars L1 (km)", 1082311.0, rL1Mars);
        errorCount += Checkers.withinTolerance("Sun-Mars L2 (km)", 1085748.0, rL2Mars);
        errorCount += Checkers.withinTolerance("Sun-Mars r_hill (km)", 1084032.0, rHillMars);
        
        Checkers.theEnd(errorCount);
    }

    /**
     * Brent's method to find a root of the function f on the interval [a, b]
     * 
     * Brent's method is a hybrid root-finding algorithm that combines the reliability
     * of bisection with the speed of inverse quadratic interpolation and the secant method.
     * It guarantees convergence and is generally considered one of the best general-purpose
     * root-finding algorithms.
     * 
     * The method requires that f(a) and f(b) have opposite signs (bracket a root).
     * 
     * @param f The function to find the root of. Must be continuous on [a, b] and
     *          f(a) * f(b) < 0 (the function values at the endpoints must have opposite signs)
     * @param a Lower bound of the bracketing interval
     * @param b Upper bound of the bracketing interval
     * @param R Semi-major axis: distance from planet to Sun (km)
     * @param M1 Mass of the Sun (kg)
     * @param M2 Mass of the planet (kg)
     * @param tol Tolerance for convergence - algorithm stops when |b - a| < tol (default: 1e-12)
     * @param maxIter Maximum number of iterations to prevent infinite loops (default: 100)
     * @return The approximate root x where f(x) ≈ 0, accurate to within tolerance
     * @throws IllegalArgumentException if f(a) and f(b) have the same sign (no root is bracketed)
     */
    public static double brentq(Function f, double a, double b, double R, 
                                double M1, double M2, double tol, int maxIter) {
        double fa = f.evaluate(a, R, M1, M2);
        double fb = f.evaluate(b, R, M1, M2);
        
        if (fa * fb > 0) {
            throw new IllegalArgumentException("Function must have opposite signs at a and b");
        }
        
        if (Math.abs(fa) < Math.abs(fb)) {
            double temp = a; a = b; b = temp;
            temp = fa; fa = fb; fb = temp;
        }
        
        double c = a;
        double fc = fa;
        boolean mflag = true;
        double s = 0;
        double d = 0;
        
        for (int iter = 0; iter < maxIter; iter++) {
            if (Math.abs(b - a) < tol) {
                return b;
            }
            
            if (fa != fc && fb != fc) {
                // Inverse quadratic interpolation
                s = a * fb * fc / ((fa - fb) * (fa - fc))
                  + b * fa * fc / ((fb - fa) * (fb - fc))
                  + c * fa * fb / ((fc - fa) * (fc - fb));
            } else {
                // Secant method
                s = b - fb * (b - a) / (fb - fa);
            }
            
            double tmp2 = (3 * a + b) / 4;
            boolean condition1 = !((s > Math.min(tmp2, b) && s < Math.max(tmp2, b)) ||
                                   (s < Math.min(tmp2, b) && s > Math.max(tmp2, b)));
            boolean condition2 = mflag && Math.abs(s - b) >= Math.abs(b - c) / 2;
            boolean condition3 = !mflag && Math.abs(s - b) >= Math.abs(c - d) / 2;
            boolean condition4 = mflag && Math.abs(b - c) < tol;
            boolean condition5 = !mflag && Math.abs(c - d) < tol;
            
            if (condition1 || condition2 || condition3 || condition4 || condition5) {
                // Bisection method
                s = (a + b) / 2;
                mflag = true;
            } else {
                mflag = false;
            }
            
            double fs = f.evaluate(s, R, M1, M2);
            d = c;
            c = b;
            fc = fb;
            
            if (fa * fs < 0) {
                b = s;
                fb = fs;
            } else {
                a = s;
                fa = fs;
            }
            
            if (Math.abs(fa) < Math.abs(fb)) {
                double temp = a; a = b; b = temp;
                temp = fa; fa = fb; fb = temp;
            }
        }
        
        return b;
    }
    
    // Overloaded version with default tolerance and max iterations
    public static double brentq(Function f, double a, double b, double R, double M1, double M2) {
        return brentq(f, a, b, R, M1, M2, 1e-12, 100);
    }
    
/**
     * Equation for finding the L1 Lagrange point (between the Sun and planet)
     * 
     * The L1 point is where a small object experiences zero net gravitational force
     * in the rotating reference frame. It lies between the Sun and the planet.
     * This function returns the net force at distance d from the planet toward the Sun.
     * The root of this function (where it equals zero) gives the L1 distance.
     * 
     * @param d Distance from the planet toward the Sun (km)
     * @param R Semi-major axis: distance from planet to Sun (km)
     * @param M1 Mass of the Sun (kg)
     * @param M2 Mass of the planet (kg)
     * @return Net gravitational force at distance d (in rotating frame). Zero at L1.
     */
    public static double solveL1(double d, double R, double M1, double M2) {
        return M2 / (d * d) + M1 / (R * R) - d * (M1 + M2) / (R * R * R) 
               - M1 / Math.pow(R - d, 2);
    }
    
    /**
     * Equation for finding the L2 Lagrange point (beyond the planet from the Sun)
     * 
     * The L2 point is where a small object experiences zero net gravitational force
     * in the rotating reference frame. It lies on the opposite side of the planet 
     * from the Sun, farther from the Sun than the planet.
     * This function returns the net force at distance d from the planet away from the Sun.
     * The root of this function (where it equals zero) gives the L2 distance.
     * 
     * @param d Distance from the planet away from the Sun (km)
     * @param R Semi-major axis: distance from planet to Sun (km)
     * @param M1 Mass of the Sun (kg)
     * @param M2 Mass of the planet (kg)
     * @return Net gravitational force at distance d (in rotating frame). Zero at L2.
     */
    public static double solveL2(double d, double R, double M1, double M2) {
        return M1 / (R * R) + d * (M1 + M2) / (R * R * R) 
               - M1 / Math.pow(R + d, 2) - M2 / (d * d);
    }
    
    /**
     * Compute the planet's Hill radius (sphere of gravitational influence)
     * 
     * The Hill radius defines the region around a planet where the planet's gravity
     * dominates over the Sun's tidal forces. Within this radius, the planet can
     * maintain stable orbits of satellites or captured objects. It approximates
     * the distance to the L1 and L2 Lagrange points.
     * 
     * Formula: R * (M2 / (3*M1))^(1/3)
     * 
     * @param R Semi-major axis: distance from planet to Sun (km)
     * @param M1 Mass of the Sun (kg)
     * @param M2 Mass of the planet (kg)
     * @return Hill radius (km) - the extent of the planet's gravitational dominance
     */
    public static double rHill(double R, double M1, double M2) {
        return R * Math.pow(M2 / (3.0 * M1), ONE_THIRD);
    }
        
}
