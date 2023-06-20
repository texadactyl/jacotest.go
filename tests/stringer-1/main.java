// Hacked from https://www.studytonight.com/java/type-casting-in-java.php

public class main {

    public static void main(String[] args) throws Exception {
    
        int errorCount = 0;
        Helpers hh = new Helpers();

        String ssa = "\n  123:Alice456Bob7890Charley's Angels:Greece:Italy:France--!  ";
        String workString;
        String ssaLC = ssa.toLowerCase();
        String ssaUC = ssa.toUpperCase();
        String ssaTrim = ssa.trim();

        hh.printLabeledString("ssaLC = ssa in lower case: ", ssaLC);
        hh.printLabeledString("ssaUC = ssa in upper case: ", ssaUC);
        hh.printLabeledString("ssaTrim = ssa trimmed: ", ssaTrim);

		String abc = "abc";
		String def = "def";

        System.out.println("String method tests");

        hh.printLabeledString("ssa: ", ssa);
        errorCount += hh.isItTrue("ssa.length() = 63", ssa.length() == 63);
		errorCount += hh.isItTrue("ssa.charAt(5) == '3'", ssa.charAt(5) == '3');
		errorCount += hh.isItTrue("ssa.codePointAt(5) == 33", ssa.codePointAt(5) == 0x33);
		errorCount += hh.isItTrue("ssa.codePointBefore(6) == 33", ssa.codePointBefore(6) == 0x33);
		errorCount += hh.isItTrue("ssa.codePointCount(3, 6) == 3", ssa.codePointCount(3, 6) == 3);
		errorCount += hh.isItTrue("ssa.compareTo(\"ABC\") < 0", ssa.compareTo("ABC") < 0);
		errorCount += hh.isItTrue("\"ABC\".compareTo(ssa) > 0", "ABC".compareTo(ssa) > 0);
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
		String streetName = "Avenue de la Folle Chanson";
		String sf2 = String.format("%f", 123.4567);  
		String sf3 = String.format("%020.12f", 123.4567);
		hh.printBracketedObject("streetName", streetName);
		hh.printBracketedObject("sf2", sf2);
		hh.printBracketedObject("sf3", sf3);
        errorCount += hh.isItTrue("format sf1.equals(streetName)", sf1.equals(streetName));
        errorCount += hh.isItTrue("format sf2.equals(\"123.456700\")", sf2.equals("123.456700"));
        errorCount += hh.isItTrue("format sf3.equals(\"0000123.456700000000\")", sf3.equals("0000123.456700000000"));
        
        byte [] ssaba = ssa.getBytes();
        workString = new String(ssaba);
        errorCount += hh.isItTrue("ssa.equals(workString) after ssa.getBytes", ssa.equals(workString));

		ssaCArray = new char[ssa.length()];        
        ssa.getChars(0, ssa.length(), ssaCArray, 0);
        workString = new String(ssaCArray);
        errorCount += hh.isItTrue("ssa.equals(workString) after ssa.getChars", ssa.equals(workString));
        
        int ssahc = ssa.hashCode();
        hh.printBracketedObject("ssa hashCode()", ssahc);
        errorCount += hh.isItTrue("ssahc == -2067646950", ssahc == -2067646950);

		int ssaIndexAlice = ssa.indexOf("Alice");
		hh.printBracketedObject("ssa.indexOf(\"Alice\")", ssaIndexAlice);
        errorCount += hh.isItTrue("ssa.indexOf(\"Alice\") == 7", ssa.indexOf("Alice") == 7);

        errorCount += hh.isItTrue("! ssa.isEmpty()", ! ssa.isEmpty());
        errorCount += hh.isItTrue("\"\".isEmpty()", "".isEmpty());
		        
		int ssaIndexLastColon = ssa.lastIndexOf(":");
		hh.printBracketedObject("ssa.lastIndexOf(\":\")", ssaIndexLastColon);
        errorCount += hh.isItTrue("ssa.lastIndexOf(\":\") == 51", ssaIndexLastColon == 51);
        
		int ssaoffsetByCodePoints = ssa.offsetByCodePoints(20, 30);
		hh.printBracketedObject("ssa.offsetByCodePoints(20, 30)", ssaoffsetByCodePoints);
        errorCount += hh.isItTrue("ssa.offsetByCodePoints(20, 30)", ssaoffsetByCodePoints == 50);
        

        errorCount += hh.isItTrue("ssaTrim.matches(\"(.*)Alice(.*)\")", ssaTrim.matches("(.*)Alice(.*)"));
        errorCount += hh.isItTrue("! ssaTrim.matches(\"(.*)Alicia(.*)\")", ! ssaTrim.matches("(.*)Alicia(.*)"));

		hh.printBracketedObject("ssaTrim.substring(4, 9)", ssaTrim.substring(4, 9));
        errorCount += hh.isItTrue("ssaTrim.regionMatches(4, \"Alice\", 0, 5)", ssaTrim.regionMatches(4, "Alice", 0, 5));

        errorCount += hh.isItTrue("ssaTrim.length() = 58", ssaTrim.length() == 58);
        errorCount += hh.isItTrue("ssaTrim.substring(4, 9) = Alice", ssaTrim.substring(4, 9).equals("Alice"));

        errorCount += hh.isItTrue("ssaTrim.startsWith(\"123\")", ssaTrim.startsWith("123"));
        
        ssaTrim = ssaTrim.replace(':', ' ');
        errorCount += hh.isItTrue("ssaTrim.length() after replace(':', ' ') = 58", ssaTrim.length() == 58);
        errorCount += hh.isItTrue("ssaTrim.endsWith(--!)", ssaTrim.endsWith("--!"));
        errorCount += hh.isItTrue("! ssaTrim.endsWith(--?)", !ssaTrim.endsWith("--?"));
        String ss3 = ssaTrim.repeat(2);
        hh.printLabeledString("ss3 length: ", String.valueOf(ss3.length()));
        errorCount += hh.isItTrue("ss3.length() = 116", ss3.length() == 116);
        String[] disjoint = ssaTrim.split(" ");
        hh.printLabeledString("disjoint length: ", String.valueOf(disjoint.length));
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

