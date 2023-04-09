public class main
{
	public static void main(String args[])
	{
	    final double v_light = 299792458.0; // m/s
	    double v_factorInit = 0.1;
	    double v_factorDelta = 0.1;
	    double v_factorUpperBound = 0.91;
	    double t_deltaAtRest = 10.0; // s
	    double x_lengthAtRest = 42.0; // m
	    System.out.println("===== Begin, t_deltaAtRest: " + String.valueOf(t_deltaAtRest) + ", x_lengthAtRest: " + String.valueOf(x_lengthAtRest));
	    Formulae lib = new Formulae();
	    for(double v_factor = v_factorInit; v_factor < v_factorUpperBound; v_factor += v_factorDelta)
	    {
	        double velocity = v_factor * v_light; 
	        double lorry = lib.lorentzFactor(velocity);
	        double stretched = lib.timeDilation(t_deltaAtRest, lorry);
	        double shrunk = lib.lengthContraction(x_lengthAtRest, lorry);
	        System.out.println("v: " + String.valueOf(v_factor) + " * c, Time(s): " + String.valueOf(t_deltaAtRest) + " --> " + String.valueOf(stretched));
	        System.out.println("\tLength(m): " + String.valueOf(x_lengthAtRest) + " --> " + String.valueOf(shrunk));
	    }
	    System.out.println("===== End");
    }
}
