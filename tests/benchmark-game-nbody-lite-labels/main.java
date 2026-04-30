public class main {

    public static void main(String[] args) {
    	SSbodies ssbodies = new SSbodies();
    	Checkers.theEnd(0);
    }
}

class SSbodies {

	private final double PI = 3.141592653589793;
	private final double SOLAR_MASS = 4 * PI * PI;
	private final double DAYS_PER_YEAR = 365.24;
	
	private final String[] labels = { "Sun", "Jupiter", "Saturn", "Uranus", "Neptune" };
	
	private final double[] outerSSBodies = {
		//sun begin
		0, 0, 0, 0, 0, 0, SOLAR_MASS, 0,
		//sun end

		//jupiter begin
		4.84143144246472090e+00,//
		-1.16032004402742839e+00,//
		-1.03622044471123109e-01,//
		1.66007664274403694e-03 * DAYS_PER_YEAR,//
		7.69901118419740425e-03 * DAYS_PER_YEAR,//
		-6.90460016972063023e-05 * DAYS_PER_YEAR,//
		9.54791938424326609e-04 * SOLAR_MASS,//
		0,
		//jupiter end

		//saturn begin
		8.34336671824457987e+00,//
		4.12479856412430479e+00,//
		-4.03523417114321381e-01,//
		-2.76742510726862411e-03 * DAYS_PER_YEAR,//
		4.99852801234917238e-03 * DAYS_PER_YEAR,//
		2.30417297573763929e-05 * DAYS_PER_YEAR,//
		2.85885980666130812e-04 * SOLAR_MASS,//
		0,
		//saturn end

		//uranus begin
		1.28943695621391310e+01,//
		-1.51111514016986312e+01,//
		-2.23307578892655734e-01,//
		2.96460137564761618e-03 * DAYS_PER_YEAR,//
		2.37847173959480950e-03 * DAYS_PER_YEAR,//
		-2.96589568540237556e-05 * DAYS_PER_YEAR,//
		4.36624404335156298e-05 * SOLAR_MASS,//
		0,
		//uranus end

		//neptune begin
		1.53796971148509165e+01,//
		-2.59193146099879641e+01,//
		1.79258772950371181e-01,//
		2.68067772490389322e-03 * DAYS_PER_YEAR,//
		1.62824170038242295e-03 * DAYS_PER_YEAR,//
		-9.51592254519715870e-05 * DAYS_PER_YEAR,//
		5.15138902046611451e-05 * SOLAR_MASS, //
		0
		//neptune end
	};

	SSbodies() {
	    for (int ii = 0; ii < 5; ii++) {
	        System.out.println(labels[ii]);
            for (int jj = 0; jj < 8; jj++) {
            	System.out.println(outerSSBodies[jj + ii]);
            }
        }
	}
	
}
