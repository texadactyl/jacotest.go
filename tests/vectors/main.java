// Ideas lifted from from https://cr.openjdk.org/~iris/se/17/latestSpec/api/java.base/java/util/Vector.html

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class main {

    public static void main(String[] args) throws Exception {
        int errorCount = 0;

        System.out.println("Fun with vectors");

        // Vector of 6 integer values
        Integer[] vintIntegers = {1, 2, 3, 4, 5, 6};
        Vector<Integer> vint = new Vector<Integer>();
        for (Integer II : vintIntegers) 
            vint.add(II);
        System.out.printf("Vector vint(6) as a String: %s\n", vint.toString());
        
        errorCount += Checkers.checker("vint.capacity() = 10", 10, vint.capacity());
        errorCount += Checkers.checker("vint.size() = 6", 6, vint.size());

        // Elongate the vector to 8 elements
        vint.ensureCapacity(10);
        errorCount += Checkers.checker("vint.capacity() = 10", 10, vint.capacity());
        vint.add(7);
        vint.add(8);
        int sum = 0;
        for (int elem : vint) {
            sum += elem;
            System.out.printf(">> elem: %d, sum: %d\n", elem, sum);
        }
        errorCount += Checkers.checker("sum of vint elements BEFORE removing '4' = 36", 36, sum);

        // Remove some elements
        vint.removeElementAt(3);
        sum = 0;
        for (int elem : vint) {
            sum += elem;
            System.out.printf(">> elem: %d, sum: %d\n", elem, sum);
        }
        errorCount += Checkers.checker("sum of vint elements AFTER removing '4' = 32", 32, sum);
        vint.removeAllElements();
        errorCount += Checkers.checker("After removing all elements, vint.size() = 0", 0, vint.size());
        errorCount += Checkers.checker("After removing all elements, vint.isEmpty() = true", true, vint.isEmpty());
        
        Checkers.theEnd(errorCount);
    }
}
