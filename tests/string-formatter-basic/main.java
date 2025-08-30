public class main {

    private static int checker(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("ok, %s: expected(%s) = observed(%s)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("*** ERROR, %s: expected(%s) != observed(%s)\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        String str;

        boolean zz = true;
        double dd = 123.45;
        double ddNeg = -dd;
        int ii = 12345;
        int iiNeg = -ii;
        String mary = "Mary had a little lamb";
        char cc = 'd';
        
        // Boolean (%b|%B)
        str = String.format("%2B", zz);
        errorCount += checker("%2B  true", "TRUE", str);
        str = String.format("%5b", zz);
        errorCount += checker("%5b  true", " true", str);
        str = String.format("%b %B", null, null);
        errorCount += checker("%b %B  null  null", "false FALSE", str);
        str = String.format("%b %B", ii, dd);
        errorCount += checker("%b %B  int double", "true TRUE", str);
        
        // Hashcode (%h|%H)
        str = String.format("%h", dd);
        errorCount += checker("%h  double", "8c921001", str);
        str = String.format("%H", ddNeg);
        errorCount += checker("%H  double-neg", "C921001", str);
        
        // String (%s|%S)
        str = String.format("%.4s %.4S", mary, mary);
        errorCount += checker("%.4s %.4S   mary, mary", "Mary MARY", str);
        
        // char (%c|%C)
        str = String.format("%c %C", cc, cc);
        errorCount += checker("%c %C   cc, cc", "d D", str);       
        
        // octal int (%o)
        str = String.format("%o", ii);
        errorCount += checker("%o   ii", "30071", str);       
        str = String.format("%o", iiNeg);
        errorCount += checker("%o   iiNeg", "37777747707", str);       
        
        // hexidecimal in (%x)
        str = String.format("%06x", ii);
        errorCount += checker("%06x   ii", "003039", str);       
        str = String.format("%10X", iiNeg);
        errorCount += checker("%10X   iiNeg", "  FFFFCFC7", str);       
        str = String.format("%010X", iiNeg);
        errorCount += checker("%010X   iiNeg", "00FFFFCFC7", str);       
        
        // Double (%e|%E|%f)
        str = String.format("%18.6e", dd);
        errorCount += checker("%18.6e  123.45", "      1.234500e+02", str);
        str = String.format("%18.6E", dd);
        errorCount += checker("%18.6E  123.45", "      1.234500E+02", str);
        str = String.format("%18.6f", dd);
        errorCount += checker("%18.6f  123.45", "        123.450000", str);
        
        assert(errorCount == 0);
        System.out.println("Success!");
    }
}

