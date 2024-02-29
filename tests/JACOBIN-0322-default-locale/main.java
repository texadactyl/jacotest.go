import java.util.Locale;
public class main {

	public static void main(String[] args)	{
	    int NLOOPS = 10000;
	    Locale locale1 = new Locale("Elvish");
	    Locale locale2 = new Locale("Elvish");
	    Locale locale3 = new Locale("Elvish");
	    Locale locale4 = new Locale("Elvish");
	    
	    for (int ii = 0; ii < NLOOPS; ii++) {
		    locale1 = Locale.getDefault();
		    locale2 = new Locale("English");
		    locale3 = new Locale("English", "USA");
		    locale4 = new Locale("English", "USA", "Texas");
		}
		
		System.out.println(locale1);
		System.out.println(locale2);
		System.out.println(locale3);
		System.out.println(locale4);
	}
}
