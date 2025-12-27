import java.util.HashMap;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        HashMap<String, Integer> hm = new HashMap<>();

        // isEmpty initially
        errorCount += Checkers.checker("isEmpty() initially", true, hm.isEmpty());

        // put entries
        Integer prev;

        prev = hm.put("apple", 1);
        errorCount += Checkers.checker("put(apple,1)", -1L, prev == null ? -1L : prev.longValue());

        prev = hm.put("banana", 2);
        errorCount += Checkers.checker("put(banana,2)", -1L, prev == null ? -1L : prev.longValue());

        prev = hm.put("cherry", 3);
        errorCount += Checkers.checker("put(cherry,3)", -1L, prev == null ? -1L : prev.longValue());

        // size after puts
        errorCount += Checkers.checker("size() after puts", 3, hm.size());

        // isEmpty after puts
        errorCount += Checkers.checker("isEmpty() after puts", false, hm.isEmpty());

        // get entries
        Integer val;

        val = hm.get("apple");
        errorCount += Checkers.checker("get(apple)", 1L, val == null ? -1L : val.longValue());

        val = hm.get("banana");
        errorCount += Checkers.checker("get(banana)", 2L, val == null ? -1L : val.longValue());

        val = hm.get("date");
        errorCount += Checkers.checker("get(date)", -1L, val == null ? -1L : val.longValue());

        // overwrite put
        prev = hm.put("apple", 10);
        errorCount += Checkers.checker("put(apple,10)", 1L, prev == null ? -1L : prev.longValue());

        val = hm.get("apple");
        errorCount += Checkers.checker("get(apple) after overwrite", 10L, val == null ? -1L : val.longValue());

        // remove entries
        prev = hm.remove("banana");
        errorCount += Checkers.checker("remove(banana)", 2L, prev == null ? -1L : prev.longValue());

        prev = hm.remove("banana");
        errorCount += Checkers.checker("remove(banana) again", -1L, prev == null ? -1L : prev.longValue());

        // putAll (simulate with separate puts)
        prev = hm.put("date", 4);
        errorCount += Checkers.checker("put(date) after putAll", -1L, prev == null ? -1L : prev.longValue());

        prev = hm.put("elderberry", 5);
        errorCount += Checkers.checker("put(elderberry) after putAll", -1L, prev == null ? -1L : prev.longValue());

        // get after putAll
        val = hm.get("date");
        errorCount += Checkers.checker("get(date) after putAll", 4L, val == null ? -1L : val.longValue());

        val = hm.get("elderberry");
        errorCount += Checkers.checker("get(elderberry) after putAll", 5L, val == null ? -1L : val.longValue());

        Checkers.theEnd(errorCount);
    }
}

