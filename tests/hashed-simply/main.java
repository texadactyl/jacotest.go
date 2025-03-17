import java.util.HashMap;

public class main {

    private static int checker(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("checker ok, %s: expected(%s) = observed(%s)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("checker*** ERROR, %s: expected(%s) != observed(%s)\n", label, expected, observed);
        return 1;
    }

    private static int checker(String label, boolean expected, boolean observed) {
        if (expected == observed) {
            System.out.printf("checker ok, %s: expected = observed\n", label);
            return 0;
        }
        System.out.printf("checker *** ERROR, %s: expected != observed\n", label);
        return 1;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        
        HashMap<Integer, String> hmapA = new HashMap<Integer, String>();
        hmapA.put(1, "apple");
        hmapA.put(2, "pie");
        hmapA.put(42, "the meaning of life");
        errorCount += checker("hmapA.get(2)", "pie", hmapA.get(2));
        String str = hmapA.get(3);
        if (str != null) {
            errorCount += 1;
            System.out.printf("*** ERROR, hmapA.get(3) should have returned null, observed: %s\n", str);
        } else {
            System.out.println("hmapA.get(3) returned null as expected");
        }

        HashMap<Integer, String> hmapB = new HashMap<Integer, String>();
        hmapB.put(10, "ten");
        hmapB.put(6, "six");
        hmapB.put(0, "zilch!");
        
        hmapA.putAll(hmapB);
        errorCount += checker("hmapA.get(6)", "six", hmapA.get(6));
        
        errorCount += checker("hmapA.containsKey(0) true", true, hmapA.containsKey(0));
        errorCount += checker("hmapA.containsKey(86) false", false, hmapA.containsKey(86));
        
        int sz = hmapA.size();
        str = String.format("%d", sz);
        errorCount += checker("hmapA.size()", "6", str);
        
        str = hmapA.remove(2);
        errorCount += checker("hmapA.remove(2)", "pie", str);
        sz = hmapA.size();
        str = String.format("%d", sz);
        errorCount += checker("hmapA.size() after hmapA.remove(2)", "5", str);
        
        hmapA.clear();
        sz = hmapA.size();
        str = String.format("%d", sz);
        errorCount += checker("hmapA.clear()", "0", str);
        
        HashMap<String, String> hmapC = new HashMap<String, String>();
        hmapC.put("one", "Line 1");
        hmapC.put("two", "Line 2");
        hmapC.put("three", "Line 3");
        sz = hmapC.size();
        str = String.format("%d", sz);
        errorCount += checker("hmapC.size()", "3", str);
        
        errorCount += checker("hmapC.get(\"two\")", "Line 2", hmapC.get("two"));
        str = hmapC.get("Not a key");
        if (str != null) {
            errorCount += 1;
            System.out.printf("*** ERROR, hmapA.get(\"Not a key\") should have returned null, observed: %s\n", str);
        } else {
            System.out.println("hmapA.get(\"Not a key\") returned null as expected");
        }
    
        assert errorCount == 0;
        System.out.println("Success!");
    }
}

