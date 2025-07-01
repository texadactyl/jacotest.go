// Ideas lifted from from https://cr.openjdk.org/~iris/se/17/latestSpec/api/java.base/java/util/Vector.html

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class main {

    public static int isItTrue(String label, int observed, int expected) {
        if (expected == observed) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR: %s, expected = %d, observed = %d\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) throws Exception {
        int errorCount = 0;

        System.out.println("Fun with vectors");

        // Vector of 6 integer values
        Integer[] vintIntegers = {1, 2, 3, 4, 5, 6};
        Vector<Integer> vint = new Vector<Integer>();
        for (Integer II : vintIntegers) 
            vint.add(II);
        System.out.printf("Vector vint(6) as a String: %s\n", vint.toString());
        errorCount += isItTrue("vint.capacity() = 10", vint.capacity(), 10);
        errorCount += isItTrue("vint.size() = 6", vint.size(), 6);

        // Elongate the vector to 8 elements
        vint.ensureCapacity(10);
        errorCount += isItTrue("vint.capacity() = 10", vint.capacity(), 10);
        vint.add(7);
        vint.add(8);
        int sum = 0;
        for (int elem : vint) {
            sum += elem;
            System.out.printf(">> elem: %d, sum: %d\n", elem, sum);
        }
        errorCount += isItTrue("sum of vint elements BEFORE removing '4' = 36", sum, 36);

        // Remove some elements
        vint.removeElementAt(3);
        sum = 0;
        for (int elem : vint) {
            sum += elem;
            System.out.printf(">> elem: %d, sum: %d\n", elem, sum);
        }
        errorCount += isItTrue("sum of vint elements AFTER removing '4' = 32", sum, 32);
        vint.removeAllElements();
        errorCount += isItTrue("After removing all elements, vint.size() = 0", vint.size(), 0);
        
        if (vint.isEmpty()) {
            System.out.println("Success :: After removing all elements, vint.isEmpty() is true");
        } else {
            errorCount += 1;
            System.out.println("*** ERROR: After removing all elements, vint.isEmpty() is false");
        }

        // Check the error count
        System.out.printf("Check assertion using errorCount, %d .....\n", errorCount);
        assert (errorCount == 0);
    }
}
