// Hacked from https://www.w3schools.com/java/java_hashmap.asp

import java.util.HashMap;

public class main {

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

        errorCount += Checkers.checker("capitalCities.get(UK) == London", "London", capitalCities.get("UK"));
        errorCount += Checkers.checker("capitalCities.get(Norway) == Oslo", "Oslo", capitalCities.get("Norway"));
        errorCount += Checkers.checker("capitalCities.size() == 4", 4, capitalCities.size());
        capitalCities.remove("Norway");
        errorCount += Checkers.checker("capitalCities.size() == 3", 3, capitalCities.size());
        capitalCities.clear();
        errorCount += Checkers.checker("capitalCities.size() == 0", 0, capitalCities.size());

        Checkers.theEnd(errorCount);
    }
}

