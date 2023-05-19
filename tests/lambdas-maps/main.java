// Hacked from https://hevodata.com/learn/java-lambda-expressions/

import java.util.ArrayList;
import java.util.*;

interface Sayable {
    public String say(String name);
}

interface Addable {
    int add(int a, int b);
}

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

        int errorCount = 0;
        System.out.println("Lambdas and Maps");

        Sayable greeting = (name) -> {
        	String msg = "Hello, " + name;
            return msg;
        };
        String wstr = greeting.say("Theodore");
        errorCount += checker("greeting.say(Theodore) == Hello, Theodore", wstr.equals("Hello, Theodore"));

        // Multiple parameters in lambda expression  
        Addable adder = (a, b) -> (a + b);
        errorCount += checker("adder.add(10,20)) == 30", adder.add(10, 20) == 30);

        // Multiple parameters with data type in lambda expression  
        Addable typedAdder = (int a, int b) -> (a + b);
        errorCount += checker("typedAdder.add(10,20)) == 30", typedAdder.add(10, 20) == 30);

        Sayable multi = (message) -> {
            String str1 = "Mary had ";
            String str2 = str1 + message;
            return str2;
        };
        wstr = multi.say("a little lamb");
        errorCount += checker("multi.say", wstr.equals("Mary had a little lamb"));

        Map<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(1, "alpha");
        hm.put(2, "beta");
        hm.put(3, "gamma");
        hm.put(4, "delta");
        hm.put(5, "epsilon");
        int counter = 0;
        for (Map.Entry<Integer, String> entry : hm.entrySet()) {
            ++counter;
            System.out.print("Key : ");
            System.out.print(entry.getKey());
            System.out.print(", Value : ");
            System.out.println(entry.getValue());
        }
        errorCount += checker("map with 3 entries", counter == 5);
        errorCount += checker("Map get(4) == delta", hm.get(4) == "delta");

        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            System.out.print("Number of errors = ");
            System.out.println(errorCount);
            System.exit(1);
        }
    }
}  

