import java.util.Locale;
public class main {

	public static void main(String[] args)	{
	    int NLOOPS = 1000;
	    Locale locale1 = Locale.of("en");
	    Locale locale2 = locale1;
	    Locale locale3 = locale1;
	    Locale locale4 = locale1;
	    
	    for (int ii = 0; ii < NLOOPS; ii++) {
		    locale1 = Locale.getDefault();
		    locale2 = Locale.of("English");
		    locale3 = Locale.of("English", "USA");
		    locale4 = Locale.of("English", "USA", "Texas");
		}
		
		System.out.println(locale1);
		System.out.println(locale2);
		System.out.println(locale3);
		System.out.println(locale4);
	}
}
