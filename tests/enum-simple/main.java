public class main {
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
    }
}
