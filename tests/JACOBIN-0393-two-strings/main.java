public class main {
	public static void main(String[] args) {
		double dd = 42.0d;
		String fmtPrintf_1 = "================= printf(\"This is a String variable format string\") --> %s %s";
		String fmtPrintf_2 = "================= printf(\"This is a String variable format string without any objects to printf";
		String fmtFormat = "================= String.format --> %s %s";
		String fmtFormatter = "================= format.formatter --> %s %s";
		String s1 = "ABC";
		String s2 = "DEF";

		System.out.printf(fmtPrintf_1, s1, s2);
		System.out.println("\n");
		
		System.out.printf(fmtPrintf_2);
		System.out.println("\n");
		
		System.out.printf("================= printf(\"This is a literal format string\") --> %s %s", s1, s2);
		System.out.println("\n");

		String s3 = String.format(fmtFormat, s1, s2);
		System.out.print(s3);
		System.out.println("\n");

		s3 = fmtFormatter.formatted(s1, s2);
		System.out.print(s3);
		System.out.println("\n");
		
		System.out.print("================= print(\"This is a literal\")");
		System.out.println("\n");
		
		String s4 = "================= This String is s4";
		System.out.print(s4);
		System.out.println("\n");
	}
}
