import java.util.Vector;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        // 1. Constructor
        Vector<String> v = new Vector<>();
        errorCount += Checkers.checker("Constructor: size", 0, v.size());

        // 2. add(E)
        v.add("apple");
        v.add("banana");
        v.add("cherry");
        errorCount += Checkers.checker("add(E) size", 3, v.size());
        errorCount += Checkers.checker("add(E) get(0)", "apple", v.get(0));
        errorCount += Checkers.checker("add(E) get(1)", "banana", v.get(1));
        errorCount += Checkers.checker("add(E) get(2)", "cherry", v.get(2));

        // 3. add(int, E)
        v.add(1, "date");
        errorCount += Checkers.checker("add(int,E) get(1)", "date", v.get(1));
        errorCount += Checkers.checker("add(int,E) get(2)", "banana", v.get(2));

        // 4. set(int, E)
        v.set(2, "blueberry");
        errorCount += Checkers.checker("set(int,E) get(2)", "blueberry", v.get(2));

        // 5. remove(int)
        v.remove(1); // removes "date"
        errorCount += Checkers.checker("remove(int) get(1)", "blueberry", v.get(1));
        errorCount += Checkers.checker("remove(int) size", 3, v.size());

        // 6. remove(Object)
        v.remove("blueberry");
        errorCount += Checkers.checker("remove(Object) get(1)", "cherry", v.get(1));
        errorCount += Checkers.checker("remove(Object) size", 2, v.size());

        // 7. contains(Object)
        errorCount += Checkers.checker("contains apple", true, v.contains("apple"));
        errorCount += Checkers.checker("contains banana", false, v.contains("banana"));

        // 8. isEmpty()
        errorCount += Checkers.checker("isEmpty before clear", false, v.isEmpty());

        // 9. size()
        errorCount += Checkers.checker("size", 2, v.size());

        // 10. clear()
        v.clear();
        errorCount += Checkers.checker("isEmpty after clear", true, v.isEmpty());
        errorCount += Checkers.checker("size after clear", 0, v.size());

        // 11. indexOf / lastIndexOf
        v.add("x");
        v.add("y");
        v.add("z");
        v.add("y");
        errorCount += Checkers.checker("indexOf y", 1, v.indexOf("y"));
        errorCount += Checkers.checker("lastIndexOf y", 3, v.lastIndexOf("y"));

        // 12. toArray
        Object[] arr = v.toArray();
        errorCount += Checkers.checker("toArray length", 4, arr.length);
        errorCount += Checkers.checker("toArray[0]", "x", (String) arr[0]);
        errorCount += Checkers.checker("toArray[3]", "y", (String) arr[3]);

        // Done
        Checkers.theEnd(errorCount);
    }
}

