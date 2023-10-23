// hacked from https://introcs.cs.princeton.edu/java/33design/Complex.java.html

/******************************************************************************
 *  Complex number object using a polar representation.
 ******************************************************************************/

public final class Complex {

    // Polar coordinates
    private double distance;    // distance or radius (no units)
    private double theta;       // angle in radians

    // Constructor that maps Cartesian coordinates to Polar coordinates.
    public Complex(double re, double im) {
        distance = Math.sqrt(re * re + im * im);
        theta = Math.atan2(im, re);
    }

    // Accessor methods to the Cartesian coordinates.
    public double getReal() { return distance * Math.cos(theta); }
    public double getImag() { return distance * Math.sin(theta); }

    // Return this Complex number multiplied by the complex argument.
    public Complex multipliedBy(Complex arg) {
        Complex wc = new Complex(0.0d, 0.0d);
        wc.distance = this.distance * arg.distance;
        wc.theta = this.theta + arg.theta;
        return wc;
    }

    // Return this Complex number raised to the argument power.
    public Complex raisedBy(int arg) {
        Complex wc = this;
        for (int ii = 0; ii < (arg - 1); ++ii) {
            wc = wc.multipliedBy(this);
        }
        return wc;
    }

    // Compute e^(a + bi) = e^a * (cos(b) + i sin(b))
    public Complex eRaisedToComplex() {
        double aa = this.getReal();
        double bb = this.getImag();
        double re = Math.exp(aa) * Math.cos(bb);
        double im = Math.exp(aa) * Math.sin(bb);
        return new Complex(re, im);
    }


}
