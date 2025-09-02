// Hacked from https://www.studytonight.com/java/type-casting-in-java.php

public class main {

    public static void main(String[] args) throws Exception {
    
        System.out.println("String manipulation and member functions - Part 1");
    
        int errorCount = 0;

        System.out.println("Helpers initialised");

        String ssa = "   123:Alice456Bob7890Charley's Angels:Greece:Italy:France--!  ";
        String workString;
        System.out.println("before ssa.toLowerCase()");
        String ssaLC = ssa.toLowerCase();
        System.out.println("after ssa.toLowerCase()");
        String ssaUC = ssa.toUpperCase();
        String ssaTrim = ssa.trim();

        System.out.printf("ssaLC = ssa in lower case: \"%s\"\n", ssaLC);
        System.out.printf("ssaUC = ssa in upper case: \"%s\"\n", ssaUC);
        System.out.printf("ssaTrim = ssa trimmed: \"%s\"\n", ssaTrim);

        String abc = "abc";
        String def = "def";

        System.out.printf("ssa: \"%s\"\n", ssa);
        errorCount += Checkers.checker("ssa.length() = 63", 63, ssa.length());
        errorCount += Checkers.checker("ssa.charAt(5) == '3'", '3', ssa.charAt(5));
        errorCount += Checkers.checker("\"abc\".compareTo(\"ABC\") > 0", true, "abc".compareTo("ABC") > 0);
        errorCount += Checkers.checker("\"ABC\".compareTo(\"abc\") < 0", true, "ABC".compareTo("abc") < 0);
        errorCount += Checkers.checker("ssa.compareToIgnoreCase(ssaLC) == 0", 0, ssa.compareToIgnoreCase(ssaLC));
        errorCount += Checkers.checker("ssa.compareToIgnoreCase(ssaUC) == 0", 0, ssa.compareToIgnoreCase(ssaUC));

        workString = abc.concat(def);
        errorCount += Checkers.checker("abc.concat(def) == \"abcdef\"", "abcdef", workString);
        errorCount += Checkers.checker("ssaUC.contains(\"ALICE\")", true, ssaUC.contains("ALICE"));

        CharSequence ssaCSeq = ssa;
        errorCount += Checkers.checker("ssa.contentEquals(ssaCSeq)", true, ssa.contentEquals(ssaCSeq));

        StringBuffer ssaStrbuf = new StringBuffer();
        ssaStrbuf.append(ssa);
        errorCount += Checkers.checker("ssa.contentEquals(ssaStrbuf)", true, ssa.contentEquals(ssaStrbuf));

        char [] ssaCArray = ssa.toCharArray();
        workString = String.copyValueOf(ssaCArray, 0, ssaCArray.length);
        errorCount += Checkers.checker("ssa.equals(workString) after copyValueOf", true, ssa.equals(workString));
        workString = ssa.toLowerCase();
        errorCount += Checkers.checker("ssa.equalsIgnoreCase(workString)", true, ssa.equalsIgnoreCase(workString));
        
        String derriere = "de la Folle Chanson";  
        String sf1 = String.format("Avenue %s", derriere);
        System.out.printf("sf1: \"%s\"\n", sf1);
        String streetName = "Avenue de la Folle Chanson";
        String sf2 = String.format("%f", 123.4567);  
        String sf3 = String.format("%020.12f", 123.4567);
        System.out.printf("streetName: \"%s\"\n", streetName);
        System.out.printf("sf2: \"%s\"\n", sf2);
        System.out.printf("sf3: \"%s\"\n", sf3);
        errorCount += Checkers.checker("format sf1.equals(streetName)", true, sf1.equals(streetName));
        errorCount += Checkers.checker("format sf2.equals(\"123.456700\")", "123.456700", sf2);
        errorCount += Checkers.checker("format sf3.equals(\"0000123.456700000000\")", "0000123.456700000000", sf3);
        
        byte [] ssaba = ssa.getBytes();
        workString = new String(ssaba);
        errorCount += Checkers.checker("ssa.equals(workString) after ssa.getBytes", true, ssa.equals(workString));

        ssaCArray = new char[ssa.length()];        
        ssa.getChars(0, ssa.length(), ssaCArray, 0);
        workString = new String(ssaCArray);
        errorCount += Checkers.checker("ssa.equals(workString) after ssa.getChars", true, ssa.equals(workString));
        
        int ssahc = ssa.hashCode();
        System.out.printf("ssa hashCode(): %d\n", ssahc);
        errorCount += Checkers.checker("ssahc == 1930924976", 1930924976, ssahc);

        int ssaIndexAlice = ssa.indexOf("Alice");
        System.out.printf("ssa.indexOf(\"Alice\"): %d\n", ssaIndexAlice);
        errorCount += Checkers.checker("ssa.indexOf(\"Alice\") == 7", 7, ssa.indexOf("Alice"));

        errorCount += Checkers.checker("! ssa.isEmpty()", true, ! ssa.isEmpty());
        errorCount += Checkers.checker("\"\".isEmpty()", true, "".isEmpty());
                
        int ssaIndexLastColon = ssa.lastIndexOf(":");
        System.out.printf("ssa.lastIndexOf(\":\"): %d\n", ssaIndexLastColon);
        errorCount += Checkers.checker("ssa.lastIndexOf(\":\") == 51", 51, ssaIndexLastColon);
        
        errorCount += Checkers.checker("ssaTrim.matches(\"(.*)Alice(.*)\")", true, ssaTrim.matches("(.*)Alice(.*)"));
        errorCount += Checkers.checker("! ssaTrim.matches(\"(.*)Alicia(.*)\")", true, ! ssaTrim.matches("(.*)Alicia(.*)"));

        System.out.printf("ssaTrim.substring(4, 9): %s\n", ssaTrim.substring(4, 9));
        errorCount += Checkers.checker("ssaTrim.regionMatches(4, \"Alice\", 0, 5)", true, ssaTrim.regionMatches(4, "Alice", 0, 5));

        errorCount += Checkers.checker("ssaTrim.length() = 58", 58, ssaTrim.length());
        errorCount += Checkers.checker("ssaTrim.substring(4, 9) = Alice", "Alice", ssaTrim.substring(4, 9));

        errorCount += Checkers.checker("ssaTrim.startsWith(\"123\")", true, ssaTrim.startsWith("123"));
        errorCount += Checkers.checker("ssaTrim.endsWith(--!)", true, ssaTrim.endsWith("--!"));
        errorCount += Checkers.checker("! ssaTrim.endsWith(--?)", true, !ssaTrim.endsWith("--?"));
       
        ssaTrim = ssaTrim.replace(':', ' ');
        errorCount += Checkers.checker("ssaTrim.length() after replace(':', ' ') = 58", 58, ssaTrim.length());
        String ss3 = ssaTrim.repeat(2);
        System.out.printf("ss3 length: %s\n", String.valueOf(ss3.length()));
        errorCount += Checkers.checker("ss3.length() = 116", 116, ss3.length());
        String[] disjoint = ssaTrim.split(" ");
        System.out.printf("disjoint length: %s\n", String.valueOf(disjoint.length));
        for (int ndx = 0; ndx < disjoint.length; ++ndx) {
            System.out.print("\tdisjoint[");
            System.out.print(ndx);
            System.out.print(" = ");
            System.out.println(disjoint[ndx]);
        }
        errorCount += Checkers.checker("disjoint.length = 6", 6, disjoint.length);

        Checkers.theEnd(errorCount);
    }
}

