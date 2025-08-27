// Hacked from https://www.studytonight.com/java/type-casting-in-java.php

public class main {

    public static void main(String[] args) throws Exception {
    
        System.out.println("String manipulation and member functions - Part 1");
    
        int errorCount = 0;
        Helpers hh = new Helpers();

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
        errorCount += hh.isItTrue("ssa.length() = 63", ssa.length() == 63);
        errorCount += hh.isItTrue("ssa.charAt(5) == '3'", ssa.charAt(5) == '3');
        errorCount += hh.isItTrue("\"abc\".compareTo(\"ABC\") > 0", "abc".compareTo("ABC") > 0);
        errorCount += hh.isItTrue("\"ABC\".compareTo(\"abc\") < 0", "ABC".compareTo("abc") < 0);
        errorCount += hh.isItTrue("ssa.compareToIgnoreCase(ssaLC) == 0", ssa.compareToIgnoreCase(ssaLC) == 0);
        errorCount += hh.isItTrue("ssa.compareToIgnoreCase(ssaUC) == 0", ssa.compareToIgnoreCase(ssaUC) == 0);

        workString = abc.concat(def);
        errorCount += hh.isItTrue("abc.concat(def) == \"abcdef\"", workString.equals("abcdef"));
        errorCount += hh.isItTrue("ssaUC.contains(\"ALICE\")", ssaUC.contains("ALICE"));

        CharSequence ssaCSeq = ssa;
        errorCount += hh.isItTrue("ssa.contentEquals(ssaCSeq)", ssa.contentEquals(ssaCSeq));

        StringBuffer ssaStrbuf = new StringBuffer();
        ssaStrbuf.append(ssa);
        errorCount += hh.isItTrue("ssa.contentEquals(ssaStrbuf)", ssa.contentEquals(ssaStrbuf));

        char [] ssaCArray = ssa.toCharArray();
        workString = String.copyValueOf(ssaCArray, 0, ssaCArray.length);
        errorCount += hh.isItTrue("ssa.equals(workString) after copyValueOf", ssa.equals(workString));
        workString = ssa.toLowerCase();
        errorCount += hh.isItTrue("ssa.equalsIgnoreCase(workString)", ssa.equalsIgnoreCase(workString));
        
        String derriere = "de la Folle Chanson";  
        String sf1 = String.format("Avenue %s", derriere);
        System.out.printf("sf1: \"%s\"\n", sf1);
        String streetName = "Avenue de la Folle Chanson";
        String sf2 = String.format("%f", 123.4567);  
        String sf3 = String.format("%020.12f", 123.4567);
        System.out.printf("streetName: \"%s\"\n", streetName);
        System.out.printf("sf2: \"%s\"\n", sf2);
        System.out.printf("sf3: \"%s\"\n", sf3);
        errorCount += hh.isItTrue("format sf1.equals(streetName)", sf1.equals(streetName));
        errorCount += hh.isItTrue("format sf2.equals(\"123.456700\")", sf2.equals("123.456700"));
        errorCount += hh.checker("format sf3.equals(\"0000123.456700000000\")", "0000123.456700000000", sf3);
        
        byte [] ssaba = ssa.getBytes();
        workString = new String(ssaba);
        errorCount += hh.isItTrue("ssa.equals(workString) after ssa.getBytes", ssa.equals(workString));

        ssaCArray = new char[ssa.length()];        
        ssa.getChars(0, ssa.length(), ssaCArray, 0);
        workString = new String(ssaCArray);
        errorCount += hh.isItTrue("ssa.equals(workString) after ssa.getChars", ssa.equals(workString));
        
        int ssahc = ssa.hashCode();
        System.out.printf("ssa hashCode(): %d\n", ssahc);
        errorCount += hh.isItTrue("ssahc == 1930924976", ssahc == 1930924976);

        int ssaIndexAlice = ssa.indexOf("Alice");
        System.out.printf("ssa.indexOf(\"Alice\"): %d\n", ssaIndexAlice);
        errorCount += hh.isItTrue("ssa.indexOf(\"Alice\") == 7", ssa.indexOf("Alice") == 7);

        errorCount += hh.isItTrue("! ssa.isEmpty()", ! ssa.isEmpty());
        errorCount += hh.isItTrue("\"\".isEmpty()", "".isEmpty());
                
        int ssaIndexLastColon = ssa.lastIndexOf(":");
        System.out.printf("ssa.lastIndexOf(\":\"): %d\n", ssaIndexLastColon);
        errorCount += hh.isItTrue("ssa.lastIndexOf(\":\") == 51", ssaIndexLastColon == 51);
        
//      int ssaoffsetByCodePoints = ssa.offsetByCodePoints(20, 30);
//      System.out.printf("ssa.offsetByCodePoints(20, 30): %d\n", ssaoffsetByCodePoints);
//      errorCount += hh.isItTrue("ssa.offsetByCodePoints(20, 30)", ssaoffsetByCodePoints == 50);

        errorCount += hh.isItTrue("ssaTrim.matches(\"(.*)Alice(.*)\")", ssaTrim.matches("(.*)Alice(.*)"));
        errorCount += hh.isItTrue("! ssaTrim.matches(\"(.*)Alicia(.*)\")", ! ssaTrim.matches("(.*)Alicia(.*)"));

        System.out.printf("ssaTrim.substring(4, 9): %s\n", ssaTrim.substring(4, 9));
        errorCount += hh.isItTrue("ssaTrim.regionMatches(4, \"Alice\", 0, 5)", ssaTrim.regionMatches(4, "Alice", 0, 5));

        errorCount += hh.isItTrue("ssaTrim.length() = 58", ssaTrim.length() == 58);
        errorCount += hh.isItTrue("ssaTrim.substring(4, 9) = Alice", ssaTrim.substring(4, 9).equals("Alice"));

        errorCount += hh.isItTrue("ssaTrim.startsWith(\"123\")", ssaTrim.startsWith("123"));
        errorCount += hh.isItTrue("ssaTrim.endsWith(--!)", ssaTrim.endsWith("--!"));
        errorCount += hh.isItTrue("! ssaTrim.endsWith(--?)", !ssaTrim.endsWith("--?"));
       
        ssaTrim = ssaTrim.replace(':', ' ');
        errorCount += hh.isItTrue("ssaTrim.length() after replace(':', ' ') = 58", ssaTrim.length() == 58);
        String ss3 = ssaTrim.repeat(2);
        System.out.printf("ss3 length: %s\n", String.valueOf(ss3.length()));
        errorCount += hh.isItTrue("ss3.length() = 116", ss3.length() == 116);
        String[] disjoint = ssaTrim.split(" ");
        System.out.printf("disjoint length: %s\n", String.valueOf(disjoint.length));
        for (int ndx = 0; ndx < disjoint.length; ++ndx) {
            System.out.print("\tdisjoint[");
            System.out.print(ndx);
            System.out.print(" = ");
            System.out.println(disjoint[ndx]);
        }
        errorCount += hh.isItTrue("disjoint.length = 6", disjoint.length == 6);

        hh.byebye(errorCount);
    }
}

