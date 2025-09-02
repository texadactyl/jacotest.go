public class main {

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
        errorCount += Checkers.checker("%2B  true", "TRUE", str);
        str = String.format("%5b", zz);
        errorCount += Checkers.checker("%5b  true", " true", str);
        str = String.format("%b %B", null, null);
        errorCount += Checkers.checker("%b %B  null  null", "false FALSE", str);
        str = String.format("%b %B", ii, dd);
        errorCount += Checkers.checker("%b %B  int double", "true TRUE", str);
        
        // Hashcode (%h|%H)
        str = String.format("%h", dd);
        errorCount += Checkers.checker("%h  double", "8c921001", str);
        str = String.format("%H", ddNeg);
        errorCount += Checkers.checker("%H  double-neg", "C921001", str);
        
        // String (%s|%S)
        str = String.format("%.4s %.4S", mary, mary);
        errorCount += Checkers.checker("%.4s %.4S   mary, mary", "Mary MARY", str);
        
        // char (%c|%C)
        str = String.format("%c %C", cc, cc);
        errorCount += Checkers.checker("%c %C   cc, cc", "d D", str);       
        
        // octal int (%o)
        str = String.format("%o", ii);
        errorCount += Checkers.checker("%o   ii", "30071", str);       
        str = String.format("%o", iiNeg);
        errorCount += Checkers.checker("%o   iiNeg", "37777747707", str);       
        
        // hexidecimal in (%x)
        str = String.format("%06x", ii);
        errorCount += Checkers.checker("%06x   ii", "003039", str);       
        str = String.format("%10X", iiNeg);
        errorCount += Checkers.checker("%10X   iiNeg", "  FFFFCFC7", str);       
        str = String.format("%010X", iiNeg);
        errorCount += Checkers.checker("%010X   iiNeg", "00FFFFCFC7", str);       
        
        // Double (%e|%E|%f)
        str = String.format("%18.6e", dd);
        errorCount += Checkers.checker("%18.6e  123.45", "      1.234500e+02", str);
        str = String.format("%18.6E", dd);
        errorCount += Checkers.checker("%18.6E  123.45", "      1.234500E+02", str);
        str = String.format("%18.6f", dd);
        errorCount += Checkers.checker("%18.6f  123.45", "        123.450000", str);
        
        Checkers.theEnd(errorCount);
    }
}

