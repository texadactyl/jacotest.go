public class main {

    // TODO: needs qualitative tests

    public static void main(String args[]) {
        String msg = "Special Relativity calculations";
        System.out.println(msg);

        final double v_light = 299792458.0; // m/s
        double v_factorInit = 0.1;
        double v_factorDelta = 0.1;
        double v_factorUpperBound = 0.91;
        double t_deltaAtRest = 10.0; // s
        double x_lengthAtRest = 42.0; // m
        System.out.print("===== Begin, t_deltaAtRest: ");
        System.out.print(t_deltaAtRest);
        System.out.print(", x_lengthAtRest: ");
        System.out.println(x_lengthAtRest);
        for (double v_factor = v_factorInit; v_factor < v_factorUpperBound; v_factor += v_factorDelta) {
            double velocity = v_factor * v_light;
            double lorry = Formulae.lorentzFactor(velocity);
            double stretched = Formulae.timeDilation(t_deltaAtRest, lorry);
            double shrunk = Formulae.lengthContraction(x_lengthAtRest, lorry);
            System.out.print("v: ");
            System.out.print(v_factor);
            System.out.print(" * c, Time(s): ");
            System.out.print(t_deltaAtRest);
            System.out.print(" --> ");
            System.out.println(stretched);
            System.out.print("\tLength(m): ");
            System.out.print(x_lengthAtRest);
            System.out.print(" --> ");
            System.out.println(shrunk);
        }
        System.out.println("===== End");
        
        Checkers.theEnd(0);
    }
}
