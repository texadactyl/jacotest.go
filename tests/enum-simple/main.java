public class main {

	enum Color {
		RED,
		GREEN,
		BLUE;
	}
	
    public static void main(String[] args)
    {
        int errorCount = 0;
        Color red = Color.RED;
        String str = String.valueOf(red);
        errorCount += Checkers.checker("RED", "RED", str);
        
        Checkers.theEnd(errorCount);
    }
}
