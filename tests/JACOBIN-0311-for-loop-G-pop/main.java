public class main {
	private static final int NLOOPS = 10000;
	private static final double Pi = 3.14159265;
	public static void main(String args[]) {    
    	double xx, yy;
    	for (int ii = 0; ii < NLOOPS; ii++) {
    		xx = Math.random() * Pi;
    		yy = Math.cos(xx);
    		System.out.print(ii);
			System.out.print(") ");
			System.out.println(xx);
		}
		
		Checkers.theEnd(0);
    }
}
