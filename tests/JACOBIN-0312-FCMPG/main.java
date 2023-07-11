public class main {

	public final static float  EpsilonF = 1.0e-6f;
	public final static float xx = 1.01f;
	public final static float yy = 1.02f;
	  
	public static void main(String yys[]) {  
        float diff = xx - yy;
        diff = diff < 0.0 ? -diff : diff;
        float divisor = xx < 0.0 ? -xx : xx;
        if (divisor < EpsilonF)
        	System.out.println("Divisor is near zero");
        float quotient = diff / divisor;
        System.out.print("Quotient: ");
        System.out.println(quotient);
    }
}
