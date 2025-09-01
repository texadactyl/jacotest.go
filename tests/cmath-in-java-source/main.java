// hacked from https://introcs.cs.princeton.edu/java/33design/Complex.java.html

public class main {

    // TODO: needs qualitative tests

    private static final double pi = 3.14159265d;

    private static void printComplex(String label, Complex value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.print(value.getReal());
        System.out.print(" + ");
        System.out.print(value.getImag());
        System.out.println("i");
    }

    /* Sample data
     * (1) The 3rd roots of unity i.e. x^3 = 1
     * (2) e^(i * pi) + 1 = 0 // Euler's identity, https://en.wikipedia.org/wiki/Euler%27s_identity
     *
     * Note that by graphing the roots of unity for N>2 on the complex plane,
     * they can be used to generate the vertices of a regular polygon.
     * https://brilliant.org/wiki/roots-of-unity
     */
    public static void main(String[] args) {
        Complex root1 = new Complex(1.0d, 0.0d);
        printComplex("root1", root1);
        Complex powered = root1.raisedBy(3);
        printComplex("root1 ^ 3", powered);

        Complex root2 = new Complex(-0.5d, 0.5 * Math.sqrt(3.0d));
        printComplex("root2", root2);
        powered = root2.raisedBy(3);
        printComplex("root2 ^ 3", powered);

        Complex root3 = new Complex(-0.5d, -0.5 * Math.sqrt(3.0d));
        printComplex("root3", root3);
        powered = root3.raisedBy(3);
        printComplex("root3 ^ 3", powered);

        Complex myPower = new Complex(0.0d, pi);
        Complex euler = myPower.eRaisedToComplex();
        printComplex("Euler", euler);
        
        Checkers.theEnd(0);
    }
}
