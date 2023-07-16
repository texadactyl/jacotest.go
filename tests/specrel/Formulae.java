public class Formulae {


    public static final double v_light = 299792458.0; // m/s;
    public static final double epsilon = 1e-15;    // relative error tolerance
    
    public static double lorentzFactor(double v_rel) {
        return 1.0 / Math.sqrt(1.0 - v_rel * v_rel / (v_light * v_light));
    }

    public static double timeDilation(double delta_t_rest, double lorry) {
        return lorry * delta_t_rest;
    }

    public static double lengthContraction(double len_rest, double lorry) {
        return len_rest / lorry;
    }
}
