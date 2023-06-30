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
        HashSet<String> cars = new HashSet<String>();

        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("BMW"); // duplicate
        cars.add("Mazda");
        System.out.println(cars);

        errorCount += checker("cars.size() == 4", cars.size() == 4);
        errorCount += checker("cars.contains(Mazda)", cars.contains("Mazda"));
        errorCount += checker("! cars.contains(Chevrolet)", !cars.contains("Chevrolet"));
        cars.remove("Volvo");
        errorCount += checker("cars.size() == 3", cars.size() == 3);
        errorCount += checker("! cars.contains(Volvo)", !cars.contains("Volvo"));
        cars.clear();
        errorCount += checker("cars.size() == 0", cars.size() == 0);

        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            System.out.print("Number of errors = ");
            System.out.println(errorCount);
            System.exit(1);
        }

    }
}


