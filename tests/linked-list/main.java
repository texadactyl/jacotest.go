// Hacked from https://www.w3schools.com/java/java_linkedlist.asp

import java.util.LinkedList;

public class main {

    public static int checker(String label, String observed, String expected) {
        System.out.print("checker ");
        if (observed.equals(expected)) {
            System.out.print(label);
            System.out.printf(" ok, observed = expected = %s\n", expected);
            return 0;
        }
        System.out.printf("%s *** ERROR, expected %s, observed %s\n", label, expected, observed);
        return 1;
    }

    public static int checker(String label, int observed, int expected) {
        System.out.print("checker ");
        if (observed == expected) {
            System.out.print(label);
            System.out.printf(" ok, observed = expected = %d\n", expected);
            return 0;
        }
        System.out.printf("%s *** ERROR, expected %d, observed %d\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        System.out.println("Testing linked lists");
        int errorCount = 0;
        int wint;
        boolean wbool;
        String wstr, wstr1, wstr2;
        LinkedList<String> cars = new LinkedList<String>();

        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        wint = cars.size();
        errorCount += checker("cars.size() = 4", wint, 4);
        errorCount += checker("cars.getLast() = Mazda", cars.getLast(), "Mazda");
        errorCount += checker("cars.getFirst() = Volvo", cars.getFirst(), "Volvo");

        wstr1 = cars.removeFirst();
        wstr2 = cars.removeLast();
        errorCount += checker("cars.removeFirst() = Volvo", wstr1, "Volvo");
        errorCount += checker("cars.removeLast() = Mazda", wstr2, "Mazda");
        wint = cars.size();
        errorCount += checker("after removing Volvo & Mazda, cars.size() = 2", wint, 2);
        System.out.println("cars = [BMW, Ford]");
        
        cars.add(0, "Pontiac");
        wint = cars.size();
        errorCount += checker("after adding Pontiac at index 0, cars.size() = 3", wint, 3);
        System.out.println("cars = [Pontiac, BMW, Ford]");
        
        wint = cars.indexOf("BMW");
        errorCount += checker("cars.indexOf(\"BMW\") = 1", wint, 1);
        
        cars.add("Toyota");
        cars.add("BMW");
        cars.add("Studebaker");
        System.out.println("cars = [Pontiac, BMW, Ford, Toyota, BMW, Studebaker]");
        if (! cars.removeFirstOccurrence("BMW"))
            throw new AssertionError("cars.removeFirstOccurrence(\"BMW\") failed");
        System.out.println("cars = [Pontiac, Ford, Toyota, BMW, Studebaker]");
        wint = cars.indexOf("BMW");
        errorCount += checker("cars.indexOf(\"BMW\") = 3 after cars.removeFirstOccurrence(\"BMW\")", wint, 3);
        wint = cars.size();
        errorCount += checker("cars.size() = 5 after cars.removeFirstOccurrence(\"BMW\")", wint, 5);
        
        wstr = cars.pollLast();
        errorCount += checker("cars.pollLast()", wstr, "Studebaker");
        System.out.println("cars = [Pontiac, Ford, Toyota, BMW]");
        wint = cars.size();
        errorCount += checker("cars.size() = 4", wint, 4);
        wint = cars.indexOf("BMW");
        errorCount += checker("cars.indexOf(\"BMW\") = 3 after cars.reversed()", wint, 3);
        wint = cars.indexOf("Ford");
        errorCount += checker("cars.indexOf(\"Ford\") = 1 after cars.reversed()", wint, 1);
        System.out.println(cars);
        wint = cars.indexOf("Honda");
        errorCount += checker("cars.indexOf(\"Honda\") = -1", wint, -1);
        
        wstr = cars.set(2, "Mini");
        errorCount += checker("cars.set(2, \"Mini\") returns \"Toyota\"", wstr, "Toyota");
        wint = cars.indexOf("Mini");
        errorCount += checker("cars.indexOf(\"Mini\") = 2", wint, 2);
        wint = cars.size();
        errorCount += checker("cars.size() = 4", wint, 4);
        
        if (cars.isEmpty())
            throw new AssertionError("cars.isEmpty() reports true but cars is not empty");
        else
            System.out.println("ok, cars.isEmpty() reported false");
            
        cars.clear();
        
        wint = cars.size();
        errorCount += checker("cars.size() = 0 after clear()", wint, 0);
        if (!cars.isEmpty())
            throw new AssertionError("cars.isEmpty() reports false but cars is empty");
        else
            System.out.println("ok, cars.isEmpty() reported true after clear()");
            
        cars.addLast("Volvo");
        cars.addLast("BMW");
        cars.addFirst("Ford");
        cars.addFirst("Mazda");
        System.out.println(cars);
        if (!cars.contains("Volvo"))
           throw new AssertionError("cars.contains(\"Volvo\") reports false but this is true");
        else
            System.out.println("ok, cars.contains(\"Volvo\") reports true");
        if (cars.contains("Zebra"))
           throw new AssertionError("cars.contains(\"Zebra\") reports true but this is not true");
        else
            System.out.println("ok, cars.contains(\"Zebra\") reports false");

        assert (errorCount == 0);
        System.out.println("Success!");
    }
}
