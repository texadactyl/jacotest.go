// Hacked from https://www.w3schools.com/java/java_hashset.asp

import java.util.HashSet;

public class main {

    public static int checker(String label, boolean bexp) {
        System.out.print("checker ");
        if (bexp) {
            System.out.print(label);
            System.out.println(" ok");
            return 0;
        }
        System.out.print(label);
        System.out.println(" *** ERROR");
        return 1;
    }

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

        errorCount += checker("cars.size() == 4", cars.size() == 4);
        errorCount += checker("cars.contains(Mazda)", cars.contains("Mazda"));
        errorCount += checker("! cars.contains(Chevrolet)", !cars.contains("Chevrolet"));
        System.out.println("cars.remove(Volvo);");
        cars.remove("Volvo");
        errorCount += checker("! cars.contains(Volvo)", !cars.contains("Volvo"));
        errorCount += checker("cars.size() == 3", cars.size() == 3);
        errorCount += checker("! cars.isEmpty()", !cars.isEmpty());

        Object[] carArray = cars.toArray();
        System.out.printf("carArray (%d):\n", carArray.length);
        for (int ix = 0; ix < carArray.length; ix++) {
            wstr = String.valueOf(carArray[ix]);
            System.out.printf("[%d] %s\n", ix+1, wstr);
        }
        errorCount += checker("carArray.length == 3", carArray.length == 3);
        
        cars.clear();
        errorCount += checker("cars.size() == 0", cars.size() == 0);
        errorCount += checker("cars.isEmpty()", cars.isEmpty());

        
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

        errorCount += checker("integers.size() == 7", integers.size() == 7);
        errorCount += checker("integers.contains(200)", integers.contains(200));
        errorCount += checker("! integers.contains(201)", !integers.contains(201));
        System.out.println("integers.remove(400);");
        integers.remove(400);
        integersArray = integers.toArray();
        System.out.printf("integersArray (%d):\n", integersArray.length);
        for (int ix = 0; ix < integersArray.length; ix++) {
            wstr = String.valueOf(integersArray[ix]);
            System.out.printf("[%d] %s\n", ix+1, wstr);
        }
        errorCount += checker("! integers.contains(400)", !integers.contains(400));
        errorCount += checker("integers.size() == 6", integers.size() == 6);
        if (errorCount > 0)
            System.out.printf("integers.size()=%d\n", integers.size());
        errorCount += checker("! integers.isEmpty()", !integers.isEmpty());

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

        errorCount += checker("doubles.size() == 7", doubles.size() == 7);
        errorCount += checker("doubles.contains(200)", doubles.contains(200.0));
        errorCount += checker("! doubles.contains(201)", !doubles.contains(201.0));
        System.out.println("doubles.remove(400.0);");
        doubles.remove(400.0);
        doublesArray = doubles.toArray();
        System.out.printf("doublesArray (%d):\n", doublesArray.length);
        for (int ix = 0; ix < doublesArray.length; ix++) {
            wstr = String.valueOf(doublesArray[ix]);
            System.out.printf("[%d] %s\n", ix+1, wstr);
        }
        errorCount += checker("! doubles.contains(400)", !doubles.contains(400.0));
        errorCount += checker("doubles.size() == 6", doubles.size() == 6);
        if (errorCount > 0)
            System.out.printf("doubles.size()=%d\n", doubles.size());
        errorCount += checker("! doubles.isEmpty()", !doubles.isEmpty());

        assert (errorCount == 0);

    }
}


