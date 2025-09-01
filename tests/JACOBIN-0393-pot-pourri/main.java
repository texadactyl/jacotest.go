public class main {

    // TODO: needs qualitative tests

	public static void main(String[] args) {
	
		boolean tryFormatting = true;
	
		String fmtString = "All together now! %% String:%s byte:0x%02X double:'%e' float:'%5.2f' int:%d long:%d short:%d\n";
		String fmt = "%f\n";

		String beetlejuice = "Beetlejuice";
		byte bb = 0x02;
		double dd = 3.0d;
		float ff = 5.0f;
		int ii = 7;
		long jj = 11;
		short ss = 13;
		boolean zz = true;
		String string;
		
		if (tryFormatting) {
			System.out.printf("Hello!\n");
			System.out.printf(fmtString, beetlejuice, bb, dd, ff, ii, jj, ss);

			string = String.format(fmtString, beetlejuice, bb, dd, ff, ii, jj, ss);
			System.out.print(string);

			string = fmtString.formatted(beetlejuice, bb, dd, ff, ii, jj, ss);
			System.out.print(string);
		}
		
		Checkers.theEnd(0);
	}
}
