public class main {

	static MathLite ml = new MathLite();
	static Helpers hh = new Helpers();
	
	public static void printSine(double radians) {
		double dd = ml.sin(radians);
		hh.printLabeledObjects("printSine", radians, dd);
	}

	public static void printCosine(double radians) {
		double dd = ml.cos(radians);
		hh.printLabeledObjects("printCosine", radians, dd);
	}

	public static void main(String[] args){
    
    	int errorCount = 0;
		double tolerance = 0.00001;
		double tan30 = 0.577350269;
		double sin30 = 0.5;
		double cos30 = 0.866025404;
		double sqrt145 = 12.041594579;
		double atan2_90_15 = 0.16514867741462683;

		int ipos = 42;
		errorCount += hh.isItClose("abs(>0)", ipos, ml.abs(ipos));
		int ineg = -ipos;
		errorCount += hh.isItClose("abs(<0)", ipos, ml.abs(ineg));
		
		double dpos = 42.0;
		errorCount += hh.isItClose("abs(>0)", dpos, ml.abs(dpos));
		double dneg = -dpos;
		errorCount += hh.isItClose("abs(<0)", dpos, ml.abs(dneg));
		
		double deg1 = 30.0;
		double rad1 = ml.toRadians(deg1);
		double deg2 = ml.toDegrees(rad1);
		errorCount += hh.isItClose("deg to rad to deg", deg1, deg2);
		
		double sin1 = ml.sin(rad1);
		hh.printBracketedObject("sin1", sin1);
		errorCount += hh.isItClose("sin 30", sin1, sin30, tolerance);

		double cos1 = ml.cos(rad1);
		errorCount += hh.isItClose("cos 30", cos1, cos30, tolerance);

		double tan1 = sin1 / cos1;
		double tan2 = ml.tan(rad1);
		errorCount += hh.isItClose("tan 30 deg 2 ways", tan1, tan2);
		errorCount += hh.isItClose("tan 30 deg value", tan1, tan30, tolerance);

		double rad2 = ml.atan(0.577350269);
		deg1 = ml.toDegrees(rad2);
		errorCount += hh.isItClose("atan(tan1) --> 30", deg1, 30.0, tolerance);
		
		double rad3 = ml.asin(sin1);
		double deg3 = ml.toDegrees(rad3);
		errorCount += hh.isItClose("asin(sin1) --> 30", deg3, deg1, tolerance);

		double rad4 = ml.acos(cos1);
		double deg4 = ml.toDegrees(rad4);
		errorCount += hh.isItClose("acos(cos1) --> 30", deg4, deg1, tolerance);

		double x = 90.0;
		double y = 15.0;
		double theta = ml.atan2(x, y);
		errorCount += hh.isItClose("atan2(x/y)", theta, atan2_90_15, tolerance);

		double dd = ml.sqrt(145.0);
		errorCount += hh.isItClose("sqrt(145)", dd, sqrt145, tolerance);

		printSine(0.);
		printSine(4.0 * ml.PI);
		printSine(400.0 * ml.PI);
		printSine((400.0 * ml.PI) % (2.0 * ml.PI));

		printCosine(0.);
		printCosine(4.0 * ml.PI);
		printCosine(400.0 * ml.PI);
		printCosine((400.0 * ml.PI) % (2.0 * ml.PI));

		assert(errorCount == 0);
		
    }

}

