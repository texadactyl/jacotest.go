public class main {
	public static void main(String[] args) {
	
		int ii = Integer.decode("#12");
		String ss1 = String.format("%d", ii);
		System.out.printf("Integer.decode(\"#12\"): |%s|\n", ss1);
		
		Byte BB = Byte.decode("#12");
		String ss2 = String.format("%s", BB.toString());
		System.out.printf("Byte.decode(\"#12\").toString(): |%s|\n", ss2);
		
		assert (ss1.equals(ss2));
	}
}
