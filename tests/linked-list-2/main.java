import java.util.LinkedList;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        LinkedList<String> ll = new LinkedList<>();

        ll.add("apple");
        ll.add("banana");
        ll.add("cherry");
        ll.add("date");
        ll.add("elderberry");

        errorCount += Checkers.checker("size()", 5, ll.size());
        errorCount += Checkers.checker("getFirst()", "apple", ll.getFirst());
        errorCount += Checkers.checker("getLast()", "elderberry", ll.getLast());

        errorCount += Checkers.checker("contains(cherry)", true, ll.contains("cherry"));
        errorCount += Checkers.checker("indexOf(banana)", 1, ll.indexOf("banana"));

        // poll()
        // HOTSPOT CONFIRMED:
        // Returns and removes the head element.
        errorCount += Checkers.checker("poll()", "apple", ll.poll());

        errorCount += Checkers.checker("size() after poll()", 4, ll.size());

        // peekLast()
        // HOTSPOT CONFIRMED:
        // Returns tail element without removal.
        errorCount += Checkers.checker("peekLast()", "elderberry", ll.peekLast());

        // pollLast()
        // HOTSPOT CONFIRMED:
        // Returns and removes tail element.
        errorCount += Checkers.checker("pollLast()", "elderberry", ll.pollLast());

        errorCount += Checkers.checker("size() after pollLast()", 3, ll.size());

        // remove(int)
        // HOTSPOT CONFIRMED:
        // Current list is: [banana, cherry, date]
        errorCount += Checkers.checker("remove(1)", "cherry", ll.remove(1));

        errorCount += Checkers.checker("size() after remove(1)", 2, ll.size());

        // remove(Object)
        errorCount += Checkers.checker("remove(Object)", true, ll.remove("banana"));
        errorCount += Checkers.checker("size() after remove(Object)", 1, ll.size());

        errorCount += Checkers.checker("isEmpty()", false, ll.isEmpty());

        ll.clear();
        errorCount += Checkers.checker("size() after clear()", 0, ll.size());
        errorCount += Checkers.checker("isEmpty() after clear()", true, ll.isEmpty());

        Checkers.theEnd(errorCount);
    }
}

