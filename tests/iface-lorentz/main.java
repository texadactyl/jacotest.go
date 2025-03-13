public class main implements IfaceLorentz {

    public static void main(String[] args) {
        main instance = new main();
        double t1 = 100.0;
        double v = IfaceLorentz.c * 0.5;
        double t2 = t1 * instance.lorentzFunc(v);
        System.out.printf("t1: %.2f years, v: %.2f Km/s, t2 (dilated t1): %.2f years\n", t1, v, t2); 
        assert(t2 > 115.0);
        System.out.println("Success!");
    }

}

interface IfaceLorentz {
    double c = 3.0e5; // roughly, the speed of light in Km/s
    // Lorentz factor used to measure time dilation and other related Relativity phenomena.
    default double lorentzFunc(double v) {
        if (v > c)
            throw new IllegalArgumentException("Lorentz factor is not defined for velocities greater than the speed of light.");
        return 1.0 / Math.sqrt(1.0 - ((v * v) / (c * c)));
    }
}

