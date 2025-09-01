// Hacked from https://www.w3schools.com/java/java_hashset.asp

import java.util.HashSet;

public class main {

    public static void main(String[] args) {
        System.out.println("Testing a hashed set");
        int errorCount = 0;
        String wstr;
        
        // String
        
        HashSet<String> cars = new HashSet<>();

        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("BMW"); // duplicate
        cars.add("Mazda");

        errorCount += Checkers.checker("cars.size() == 4", 4, cars.size());
        errorCount += Checkers.checker("cars.contains(Mazda)", true, cars.contains("Mazda"));
        errorCount += Checkers.checker("! cars.contains(Chevrolet)", false, cars.contains("Chevrolet"));
        System.out.println("cars.remove(Volvo);");
        cars.remove("Volvo");
        errorCount += Checkers.checker("! cars.contains(Volvo)", false, cars.contains("Volvo"));
        errorCount += Checkers.checker("cars.size() == 3", 3, cars.size());
        errorCount += Checkers.checker("! cars.isEmpty()", false, cars.isEmpty());

        Object[] carArray = cars.toArray();
        System.out.printf("carArray (%d):\n", carArray.length);
        for (int ix = 0; ix < carArray.length; ix++) {
            wstr = String.valueOf(carArray[ix]);
            System.out.printf("[%d] %s\n", ix+1, wstr);
        }
        errorCount += Checkers.checker("carArray.length == 3", 3, carArray.length);
        
        cars.clear();
        errorCount += Checkers.checker("cars.size() == 0", 0, cars.size());
        errorCount += Checkers.checker("cars.isEmpty()", true, cars.isEmpty());

        
        // Integer
        
        HashSet<Integer> integers = new HashSet<Integer>();
        Integer[] integerValues = {
                25,
                50,
                100,
                200,
                400,
                800,
                1600
        };
        for (int ix = 0; ix < integerValues.length; ix++)
            integers.add(integerValues[ix]);
        Object[] integersArray = integers.toArray();
        System.out.printf("integersArray (%d):\n", integersArray.length);
        for (int ix = 0; ix < integersArray.length; ix++) {
            wstr = String.valueOf(integersArray[ix]);
            System.out.printf("[%d] %s\n", ix+1, wstr);
        }

        errorCount += Checkers.checker("integers.size() == 7", 7, integers.size());
        errorCount += Checkers.checker("integers.contains(200)", true, integers.contains(200));
        errorCount += Checkers.checker("! integers.contains(201)", false, integers.contains(201));
        System.out.println("integers.remove(400);");
        integers.remove(400);
        integersArray = integers.toArray();
        System.out.printf("integersArray (%d):\n", integersArray.length);
        for (int ix = 0; ix < integersArray.length; ix++) {
            wstr = String.valueOf(integersArray[ix]);
            System.out.printf("[%d] %s\n", ix+1, wstr);
        }
        errorCount += Checkers.checker("! integers.contains(400)", false, integers.contains(400));
        errorCount += Checkers.checker("integers.size() == 6", 6, integers.size());
        if (errorCount > 0)
            System.out.printf("integers.size()=%d\n", integers.size());
        errorCount += Checkers.checker("! integers.isEmpty()", false, integers.isEmpty());

        // Double
        
        HashSet<Double> doubles = new HashSet<Double>();
        Double[] doubleValues = {
                25.0,
                50.0,
                100.0,
                200.0,
                400.0,
                800.0,
                1600.0
        };
        for (int ix = 0; ix < doubleValues.length; ix++)
            doubles.add(doubleValues[ix]);
        Object[] doublesArray = doubles.toArray();
        System.out.printf("doublesArray (%d):\n", doublesArray.length);
        for (int ix = 0; ix < doublesArray.length; ix++) {
            wstr = String.valueOf(doublesArray[ix]);
            System.out.printf("[%d] %s\n", ix+1, wstr);
        }

        errorCount += Checkers.checker("doubles.size() == 7", 7, doubles.size());
        errorCount += Checkers.checker("doubles.contains(200)", true, doubles.contains(200.0));
        errorCount += Checkers.checker("! doubles.contains(201)", false, doubles.contains(201.0));
        System.out.println("doubles.remove(400.0);");
        doubles.remove(400.0);
        doublesArray = doubles.toArray();
        System.out.printf("doublesArray (%d):\n", doublesArray.length);
        for (int ix = 0; ix < doublesArray.length; ix++) {
            wstr = String.valueOf(doublesArray[ix]);
            System.out.printf("[%d] %s\n", ix+1, wstr);
        }
        errorCount += Checkers.checker("! doubles.contains(400)", false, doubles.contains(400.0));
        errorCount += Checkers.checker("doubles.size() == 6", 6, doubles.size());
        if (errorCount > 0)
            System.out.printf("doubles.size()=%d\n", doubles.size());
        errorCount += Checkers.checker("! doubles.isEmpty()", false, doubles.isEmpty());

        Checkers.theEnd(errorCount);

    }
}


