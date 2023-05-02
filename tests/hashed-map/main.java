// Hacked from https://www.w3schools.com/java/java_hashmap.asp

import java.util.HashMap;

public class main {

    public static int checker(String label, boolean bexp) {
        System.out.print("checker ");
        if (bexp) {
            System.out.print(label);
            System.out.println(" ok");
            return 0;
        }
        System.out.print(label);
        System.out.println(" FAILED");
        return 1;
    }

    public static void main(String[] args) {
        System.out.println("Testing a hashed map");
        int errorCount = 0;
        HashMap<String, String> capitalCities = new HashMap<String, String>();

        // Add keys and values (Country, City)
        capitalCities.put("UK", "London");
        capitalCities.put("Germany", "Berlin");
        capitalCities.put("Norway", "Oslo");
        capitalCities.put("USA", "Washington DC");
        System.out.println(capitalCities);

        errorCount += checker("capitalCities.get(UK) == London", capitalCities.get("UK") == "London");
        errorCount += checker("capitalCities.get(Norway) == Oslo", capitalCities.get("Norway") == "Oslo");
        errorCount += checker("capitalCities.size() == 4", capitalCities.size() == 4);
        capitalCities.remove("Norway");
        errorCount += checker("capitalCities.size() == 3", capitalCities.size() == 3);
        capitalCities.clear();
        errorCount += checker("capitalCities.size() == 0", capitalCities.size() == 0);

        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            System.out.print("Number of errors = ");
            System.out.println(errorCount);
            System.exit(1);
        }
    }
}


