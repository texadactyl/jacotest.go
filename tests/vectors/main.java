// Ideas lifted from from https://cr.openjdk.org/~iris/se/17/latestSpec/api/java.base/java/util/Vector.html

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class main {

    public static void printLabeledString(String label, Object value) {
        System.out.print(label);
        System.out.println(value);
    }

    public static int isItTrue(String label, boolean bool) {
        if (bool) {
            printLabeledString("Success :: ", label);
            return 0;
        }
        printLabeledString("*** ERROR :: ", label);
        return 1;
    }

    public static void main(String[] args) throws Exception {
        int errorCount = 0;

        System.out.println("Fun with vectors");

        // Vector of 6 integer values
        Integer[] vintIntegers = {1, 2, 3, 4, 5, 6};
        Vector<Integer> vint = new Vector<Integer>(Arrays.asList(vintIntegers));
        printLabeledString("Vector vint(3): ", vint);
        printLabeledString("Vector vint: ", vint);
        printLabeledString("Vector vint capacity: ", vint.capacity());
        errorCount += isItTrue("vint.capacity() = 6", vint.capacity() == 6);
        System.out.println("Vector vint hashCode: " + vint.hashCode());
        errorCount += isItTrue("vint.hashCode() = 918073252", vint.hashCode() == 918073252);
        System.out.println("Vector vint as a String: " + vint.toString());
        errorCount += isItTrue("vint.toString() = [1, 2, 3, 4, 5, 6]", vint.toString().equals("[1, 2, 3, 4, 5, 6]"));
        Integer sum = vint.stream().reduce(Integer::sum).get();
        System.out.println("Vector vint element sum: " + sum);
        errorCount += isItTrue("sum of vint elements = 21", sum == 21);

        // Elongate the vector to 8 elements
        vint.ensureCapacity(8);
        vint.add(7);
        vint.add(8);
        printLabeledString("Vector vint(8): ", vint);
        errorCount += isItTrue("elongated vint.toString() = [1, 2, 3, 4, 5, 6, 7, 8]", vint.toString().equals("[1, 2, 3, 4, 5, 6, 7, 8]"));
        sum = vint.stream().reduce(Integer::sum).get();
        printLabeledString("Vector vint element sum: ", sum);
        errorCount += isItTrue("sum of elongated vint elements = 36", sum == 36);

        // Remove some elements
        vint.removeElementAt(3);
        sum = vint.stream().reduce(Integer::sum).get();
        printLabeledString("Vector vint element sum after removing one element: ", sum);
        errorCount += isItTrue("sum of elongated vint elements after removing '4' = 32", sum == 32);
        vint.removeAllElements();
        errorCount += isItTrue("After removing all elements, vint.size() = 0", vint.size() == 0);
        errorCount += isItTrue("After removing all elements, vint.isEmpty() is true", vint.isEmpty());

        // Check the error count
        assert (errorCount == 0);
    }
}
