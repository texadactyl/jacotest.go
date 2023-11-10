import java.util.Locale;
public class main {

	public static void main(String[] args)	{
		Locale locale = Locale.getDefault();
		System.out.printf("Default locale: %s\n", locale);
		locale = new Locale("English");
		System.out.printf("Locale based on language: %s\n", locale);
		locale = new Locale("English", "USA");
		System.out.printf("Locale based on language, country: %s\n", locale);
		locale = new Locale("English", "USA", "Texas");
		System.out.printf("Locale based on language, country, variant: %s\n", locale);
	}
}
