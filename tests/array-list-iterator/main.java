// Hacked from https://www.w3schools.com/java/java_iterator.asp

import java.util.ArrayList;
import java.util.Iterator;

public class main {
    public static void main(String[] args) {

        System.out.println("Begin ArrayList/iterator tests");
        int errorCount = 0;

        // Make a collection
        ArrayList<String> cars = new ArrayList<String>();
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");

        Iterator<String> itr = cars.iterator();
        while (itr.hasNext()) {
            String ss = itr.next();
            if (ss == "Ford") {
                itr.remove();
            } else {
                System.out.print(ss);
                System.out.print("  ");
            }
        }
        System.out.println();

        if (cars.size() != 3) {
            System.out.println("cars.size() != 3 *** ERROR");
            errorCount += 1;
        }
        String first = cars.get(0);
        String last = cars.get(cars.size() - 1);
        if (first != "Volvo") {
            System.out.println("first != Volvo *** ERROR");
            errorCount += 1;
        }
        if (last != "Mazda") {
            System.out.println("last != Mazda *** ERROR");
            errorCount += 1;
        }

        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            System.out.print("Number of errors = ");
            System.out.println(errorCount);
            System.exit(1);
        }

    }
} 
