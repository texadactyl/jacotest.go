public class main {

	public static void main(String[] args) {
	
		boolean tryFormatting = true;
	
		String fmtString = "All together now! %% String:%s byte:0x%02X char:%c double:'%e' float:'%5.2f' int:%d long:%d short:%d boolean:%T %t\n";
		String fmt = "%f\n";

		String beetlejuice = "Beetlejuice";
		byte bb = 0x02;
		char cc = 'A';
		double dd = 3.0d;
		float ff = 5.0f;
		int ii = 7;
		long jj = 11;
		short ss = 13;
		boolean zz = true;
		String string;
		
		if (tryFormatting) {
			System.out.printf("Hello!\n");
			System.out.printf(fmtString, beetlejuice, bb, cc, dd, ff, ii, jj, ss, zz, zz);

			string = String.format(fmtString, beetlejuice, bb, cc, dd, ff, ii, jj, ss, zz, zz);
			System.out.print(string);

			string = fmtString.formatted(beetlejuice, bb, cc, dd, ff, ii, jj, ss, zz, zz);
			System.out.print(string);
		}		
	}
}
