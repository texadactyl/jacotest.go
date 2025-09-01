// Hacked from https://www.w3schools.com/java/java_iterator.asp

import java.util.ArrayList;
import java.util.Iterator;

public class main {
    public static void main(String[] args) {

        System.out.println("Begin ArrayList/iterator tests");
        int errorCount = 0;

        // Make a collection of 8.
        ArrayList<String> cars = new ArrayList<String>();
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        cars.add("Kia");
        cars.add("Jaguar");
        cars.add("Chevrolet");
        cars.add("Toyota");

        // Remove only the Kia.
        for (int ix = 0; ix < cars.size(); ix++) {
            if (cars.get(ix).equals("Kia"))
                cars.remove(ix);
        }

        // Test for size = 7.
        int sz = cars.size();
        if (sz != 7) {
            System.out.printf("*** ERROR, cars.size() expected 7, observed %d\n", sz);
            errorCount += 1;
        }

        // Build Iterator from ArrayList.
        Iterator<String> itr = cars.iterator();

        // Remove only the Ford.
        String ss;
        int ix = 0;
        while (itr.hasNext()) {
            ix += 1;
            ss = itr.next();
            if (ss.equals("Ford")) {
                itr.remove();
                System.out.printf("%d itr.remove ok: %s\n", ix, ss);
            } else {
                System.out.printf("%d itr.next skipping: %s\n", ix, ss);
            }
        }

        // Check that size = 6.
        sz = cars.size();
        if (sz != 6) {
            System.out.printf("*** ERROR, cars.size() expected 6, observed %d\n", sz);
            errorCount += 1;
        }

        // Check first and last.
        String first = cars.get(0);
        String last = cars.get(cars.size() - 1);
        if (!first.equals("Volvo")) {
            System.out.printf(" *** ERROR, first should be Volvo, observed: %s\n", first);
            errorCount += 1;
        }
        if (!last.equals("Toyota")) {
            System.out.printf(" *** ERROR, last should be Toyota, observed: %s\n", last);
            errorCount += 1;
        }

        Checkers.theEnd(errorCount);
    }
} 
