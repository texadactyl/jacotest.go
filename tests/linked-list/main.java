// Hacked from https://www.w3schools.com/java/java_linkedlist.asp

import java.util.LinkedList;

public class main {

    public static int checker(String label, String observed, String expected) {
        System.out.print("checker ");
        if (observed.equals(expected)) {
            System.out.print(label);
            System.out.println(" ok");
            return 0;
        }
        System.out.printf("*** ERROR, expected %s, observed %s\n", expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        System.out.println("Testing linked lists");
        int errorCount = 0;
        LinkedList<String> cars = new LinkedList<String>();

        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        int carCount = cars.size();
        System.out.printf("cars(%d): ", carCount);
        System.out.println(cars);
        assert (carCount != 0);

        errorCount += checker("cars.getLast() == Mazda", cars.getLast(), "Mazda");
        errorCount += checker("cars.getFirst() == Volvo", cars.getFirst(), "Volvo");
        cars.removeFirst();
        errorCount += checker("cars.getLast() == BMW", cars.getFirst(), "BMW");
        cars.removeLast();
        errorCount += checker("cars.getLast() == Ford", cars.getLast(), "Ford");

        assert (errorCount == 0);
    }
}
