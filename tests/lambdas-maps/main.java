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

    public static void main(String[] args) {

        int errorCount = 0;
        System.out.println("Lambdas and Maps");

        Sayable greeting = (name) -> {
        	String prefix = "Hello, ";
        	String msg = prefix.concat(name);
            return msg;
        };
        String wstr = greeting.say("Theodore");
        errorCount += Checkers.checker("greeting.say(Theodore) == Hello, Theodore", "Hello, Theodore", wstr);

        // Multiple parameters in lambda expression  
        Addable adder = (a, b) -> (a + b);
        errorCount += Checkers.checker("adder.add(10,20)) == 30", 30, adder.add(10, 20));

        // Multiple parameters with data type in lambda expression  
        Addable typedAdder = (int a, int b) -> (a + b);
        errorCount += Checkers.checker("typedAdder.add(10,20)) == 30", 30, typedAdder.add(10, 20));

        Sayable multi = (message) -> {
            String str1 = "Mary had ";
            String str2 = str1 + message;
            return str2;
        };
        wstr = multi.say("a little lamb");
        errorCount += Checkers.checker("multi.say", "Mary had a little lamb", wstr);

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
        errorCount += Checkers.checker("map with 3 entries", 5, counter);
        errorCount += Checkers.checker("Map get(4) == delta", "delta", hm.get(4));

        Checkers.theEnd(errorCount);
    }
}  

