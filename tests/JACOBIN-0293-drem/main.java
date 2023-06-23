public class main {
    public static void main(String[] args) {
    	double d1, d2, d3;
    	d1 = 233.3;
    	d2 = 33.4;
    	for (int ndx = 0; ndx < 1000; ++ndx) {
    		System.out.println(ndx);
    		d3 = d1 % d2;
    	}
    	System.out.println("DREM Loop - success");
    }
}
