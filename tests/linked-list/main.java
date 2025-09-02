// Hacked from https://www.w3schools.com/java/java_linkedlist.asp

import java.util.LinkedList;

public class main {

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
        errorCount += Checkers.checker("cars.size() = 4", 4, wint);
        errorCount += Checkers.checker("cars.getLast() = Mazda", "Mazda", cars.getLast());
        errorCount += Checkers.checker("cars.getFirst() = Volvo", "Volvo", cars.getFirst());

        wstr1 = cars.removeFirst();
        wstr2 = cars.removeLast();
        errorCount += Checkers.checker("cars.removeFirst() = Volvo", "Volvo", wstr1);
        errorCount += Checkers.checker("cars.removeLast() = Mazda", "Mazda", wstr2);
        wint = cars.size();
        errorCount += Checkers.checker("after removing Volvo & Mazda, cars.size() = 2", 2, wint);
        System.out.println("cars = [BMW, Ford]");
        
        cars.add(0, "Pontiac");
        wint = cars.size();
        errorCount += Checkers.checker("after adding Pontiac at index 0, cars.size() = 3", 3, wint);
        System.out.println("cars = [Pontiac, BMW, Ford]");
        
        wint = cars.indexOf("BMW");
        errorCount += Checkers.checker("cars.indexOf(\"BMW\") = 1", 1, wint);
        
        cars.add("Toyota");
        cars.add("BMW");
        cars.add("Studebaker");
        System.out.println("cars = [Pontiac, BMW, Ford, Toyota, BMW, Studebaker]");
        if (! cars.removeFirstOccurrence("BMW"))
            throw new AssertionError("cars.removeFirstOccurrence(\"BMW\") failed");
        System.out.println("cars = [Pontiac, Ford, Toyota, BMW, Studebaker]");
        wint = cars.indexOf("BMW");
        errorCount += Checkers.checker("cars.indexOf(\"BMW\") = 3 after cars.removeFirstOccurrence(\"BMW\")", 3, wint);
        wint = cars.size();
        errorCount += Checkers.checker("cars.size() = 5 after cars.removeFirstOccurrence(\"BMW\")", 5, wint);
        
        wstr = cars.pollLast();
        errorCount += Checkers.checker("cars.pollLast()", "Studebaker", wstr);
        System.out.println("cars = [Pontiac, Ford, Toyota, BMW]");
        wint = cars.size();
        errorCount += Checkers.checker("cars.size() = 4", 4, wint);
        wint = cars.indexOf("BMW");
        errorCount += Checkers.checker("cars.indexOf(\"BMW\") = 3 after cars.reversed()", 3, wint);
        wint = cars.indexOf("Ford");
        errorCount += Checkers.checker("cars.indexOf(\"Ford\") = 1 after cars.reversed()", 1, wint);
        System.out.println(cars);
        wint = cars.indexOf("Honda");
        errorCount += Checkers.checker("cars.indexOf(\"Honda\") = -1", -1, wint);
        
        wstr = cars.set(2, "Mini");
        errorCount += Checkers.checker("cars.set(2, \"Mini\") returns \"Toyota\"", "Toyota", wstr);
        wint = cars.indexOf("Mini");
        errorCount += Checkers.checker("cars.indexOf(\"Mini\") = 2", 2, wint);
        wint = cars.size();
        errorCount += Checkers.checker("cars.size() = 4", 4, wint);
        
        if (cars.isEmpty())
            throw new AssertionError("cars.isEmpty() reports true but cars is not empty");
        else
            System.out.println("ok, cars.isEmpty() reported false");
            
        cars.clear();
        
        wint = cars.size();
        errorCount += Checkers.checker("cars.size() = 0 after clear()", 0, wint);
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

        Checkers.theEnd(errorCount);
    }
}
