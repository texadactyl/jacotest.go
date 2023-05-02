// Hacked from https://www.w3schools.com/java/java_linkedlist.asp

import java.util.LinkedList;

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
        System.out.println("Testing linked lists");
        int errorCount = 0;
        LinkedList<String> cars = new LinkedList<String>();

        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        System.out.println(cars);

        errorCount += checker("cars.getLast() == Mazda", cars.getLast() == "Mazda");
        errorCount += checker("cars.getFirst() == Volvo", cars.getFirst() == "Volvo");
        cars.removeFirst();
        errorCount += checker("cars.getLast() == BMW", cars.getFirst() == "BMW");
        cars.removeLast();
        errorCount += checker("cars.getLast() == Ford", cars.getLast() == "Ford");

        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            System.out.print("Number of errors = ");
            System.out.println(errorCount);
            System.exit(1);
        }
    }
}
