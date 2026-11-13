public class main {
	public static void main(String[] args) {
		int errorCount = 0;
        String hello= "Hello";
        String world= "World";
        errorCount += Checkers.checker("Concat A+B", "HelloWorld", hello + world);
        
        String threeSome = hello + "|" + world;
        String expected = "Hello|World";
        String suffix1 = "[suffix1]";
        String suffix2 = "[suffix2]";
        errorCount += Checkers.checker("Concat A+B+C+D+E", String.format("%s%s%s", expected, suffix1, suffix2), threeSome + suffix1 + suffix2);
        Checkers.theEnd(errorCount);
	}
}
