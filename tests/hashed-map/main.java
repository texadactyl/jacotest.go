// Hacked from https://www.w3schools.com/java/java_hashmap.asp

import java.util.HashMap;

public class main {

    public static int checkerString(String label, String observed, String expected) {
        System.out.print("checker ");
        if (expected.equals(observed)) {
            System.out.printf("%s: ok\n", label);
            return 0;
        }
        System.out.printf("%s: *** ERROR, expected=%s, observed=%s\n", label, expected, observed);
        return 1;
    }

    public static int checkerInt(String label, int observed, int expected) {
        System.out.print("checker ");
        if (expected == observed) {
            System.out.printf("%s: ok\n", label);
            return 0;
        }
        System.out.printf("%s: *** ERROR, expected=%d, observed=%d\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        System.out.println("Testing a hashed map");
        int errorCount = 0;
        HashMap<String, String> capitalCities = new HashMap<String, String>();
        System.out.println("HashMap init ok");

        // Add keys and values (Country, City)
        capitalCities.put("UK", "London");
        System.out.printf("put ok:%s %s\n", "UK", "London");
        capitalCities.put("Germany", "Berlin");
        System.out.printf("put ok:%s %s\n", "Germany", "Berlin");
        capitalCities.put("Norway", "Oslo");
        System.out.printf("put ok:%s %s\n", "Norway", "Oslo");
        capitalCities.put("USA", "Washington DC");
        System.out.printf("put ok:%s %s\n", "USA", "Washington DC");
        System.out.println(capitalCities);

        errorCount += checkerString("capitalCities.get(UK) == London", capitalCities.get("UK"), "London");
        errorCount += checkerString("capitalCities.get(Norway) == Oslo", capitalCities.get("Norway"), "Oslo");
        errorCount += checkerInt("capitalCities.size() == 4", capitalCities.size(), 4);
        capitalCities.remove("Norway");
        errorCount += checkerInt("capitalCities.size() == 3", capitalCities.size(), 3);
        capitalCities.clear();
        errorCount += checkerInt("capitalCities.size() == 0", capitalCities.size(), 0);

        assert (errorCount == 0);
    }
}

