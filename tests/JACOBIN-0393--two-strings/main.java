import java.util.Map;
import java.util.HashMap;

public class main {
	public static void main(String[] args) {
		double dd = 42.0d;
		String fmt2s = "%s %s\n";
		String s1 = "ABC";
		String s2 = "DEF";
		System.out.printf(fmt2s, s1, s2);
		String s3 = String.format(fmt2s, s1, s2);
		System.out.print(s3);
		s3 = fmt2s.formatted(s1, s2);
		System.out.print(s3);
	}
}
