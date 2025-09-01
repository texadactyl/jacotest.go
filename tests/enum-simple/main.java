public class main {

    // TODO: needs qualitative tests
    
	enum Color {
		RED,
		GREEN,
		BLUE;
	}
	
    public static void main(String[] args)
    {
        Color red = Color.RED;
        String str = String.valueOf(red);
        assert ( str.equals("RED") );
        System.out.println(str);
        
        Checkers.theEnd(0);
    }
}
