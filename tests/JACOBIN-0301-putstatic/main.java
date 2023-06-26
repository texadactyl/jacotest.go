public class main {

	static double secs_overall;

	public static void main(String[] args) {
		System.out.println("Will PUTSTATIC work?");
		long t1_overall = 0;
		long t2_overall = System.currentTimeMillis();
		secs_overall = (double) (t2_overall - t1_overall) / 1000.0;
		System.out.println("Yes, it did!");

	}

}
