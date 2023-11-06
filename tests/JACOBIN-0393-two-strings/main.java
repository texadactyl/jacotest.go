import java.util.Map;
import java.util.HashMap;

public class main {
	public static void main(String[] args) {
		double dd = 42.0d;
		String fmtPrintf = "================= 1a. printf(\"This is a String variable format string\") --> %s %s";
		String fmtFormat = "================= 2. String.format --> %s %s";
		String fmtFormatter = "================= 3. format.formatter --> %s %s";
		String s1 = "ABC";
		String s2 = "DEF";

		System.out.printf(fmtPrintf, s1, s2);
		System.out.println("\n");
		
		System.out.printf("================= 1b. printf(\"This is a literal format string\") --> %s %s", s1, s2);
		System.out.println("\n");

		String s3 = String.format(fmtFormat, s1, s2);
		System.out.print(s3);
		System.out.println("\n");

		s3 = fmtFormatter.formatted(s1, s2);
		System.out.print(s3);
		System.out.println("\n");
		
		System.out.print("================= 4. print(\"This is a literal\")");
		System.out.println("\n");
	}
}
