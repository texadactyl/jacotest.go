import java.util.Random;

public class main {
	public static void main(String[] args) {
	
		int LOOPS = 10;
		int BOUND = 20;
		int BBSIZE = 8;
		long MYSEED = 42;
		int errorCount = 0;
		
		byte[] bb = new byte[BBSIZE];
		
		boolean abool;
		int ai, aib;
		long aj, ajb;
		float af;
		double ad;
		double ag;
		
		Random rr = new Random();
		for (int xx = 0; xx < LOOPS; xx++) {
			abool = rr.nextBoolean();
			ai = rr.nextInt();
			aib = rr.nextInt(BOUND);
			if (aib < 0 || aib >= BOUND) {
				System.out.printf("*** ERROR, integer out of bounds (%d)\n", aib);
				++errorCount;
			}
			aj = rr.nextLong();
			af = rr.nextFloat();
			if (af < 0.0f || af >= 1.0f) {
				System.out.printf("*** ERROR, float out of bounds (%d)\n", af);
				++errorCount;
			}
			ad = rr.nextDouble();
			if (ad < 0.0 || ad >= 1.0) {
				System.out.printf("*** ERROR, double out of bounds (%d)\n", ad);
				++errorCount;
			}
			ag = rr.nextGaussian();
			rr.nextBytes(bb);
			System.out.print(abool);
			System.out.printf(" %d %d %d %f %f %f; bb:", ai, aib, aj, af, ad, ag);
			for (int yy = 0; yy < BBSIZE; yy++) {
				System.out.printf(" %02x", bb[yy]);
			}
			System.out.println();
		}
		
		System.out.println("\n==========================================\n");
	
		rr = new Random(MYSEED);
		for (int xx = 0; xx < LOOPS; xx++) {
			abool = rr.nextBoolean();
			ai = rr.nextInt();
			aib = rr.nextInt(BOUND);
			if (aib < 0 || aib >= BOUND) {
				System.out.printf("*** ERROR, integer out of bounds (%d)\n", aib);
				++errorCount;
			}
			aj = rr.nextLong();
			af = rr.nextFloat();
			ad = rr.nextDouble();
			if (ad < 0.0 || ad >= 1.0) {
				System.out.printf("*** ERROR, double out of bounds (%d)\n", ad);
				++errorCount;
			}
			ag = rr.nextGaussian();
			rr.nextBytes(bb);
			System.out.print(abool);
			System.out.printf(" %d %d %d %f %f %f; bb:", ai, aib, aj, af, ad, ag);
			for (int yy = 0; yy < BBSIZE; yy++) {
				System.out.printf(" %02x", bb[yy]);
			}
			System.out.println();
		}
		
		Checkers.theEnd(errorCount);
	
	}
}
