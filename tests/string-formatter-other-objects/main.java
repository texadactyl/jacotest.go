public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        String str;

        Boolean zz = Boolean.valueOf(true);
        Byte bb = Byte.valueOf("42");
        Character cc = Character.valueOf('d');
        Double dd = Double.valueOf(123.45);
        Double ddNeg = Double.valueOf(-123.45);
        Float ff = Float.valueOf(123.45f);
        Float ffNeg = Float.valueOf(-123.45f);
        Integer ii = Integer.valueOf(12345);
        Integer iiNeg = Integer.valueOf(-12345);
        Long jj = Long.valueOf(12345);
        Long jjNeg = Long.valueOf(-12345);
        Short sh = Short.valueOf("123");
        Short shNeg = Short.valueOf("-123");
        
        // Boolean (%b|%B)
        str = String.format("%2B", zz);
        errorCount += Checkers.checker("%2B  true", "TRUE", str);
        str = String.format("%5b", zz);
        errorCount += Checkers.checker("%5b  true", " true", str);
        str = String.format("%b %B", ii, dd);
        errorCount += Checkers.checker("%b %B  int Double", "true TRUE", str);
        
        // Hashcode (%h|%H)
        str = String.format("%h", zz);
        errorCount += Checkers.checker("%h  Boolean-true", "4cf", str);
        str = String.format("%h", bb);
        errorCount += Checkers.checker("%h  Byte", "2a", str);
        str = String.format("%h", cc);
        errorCount += Checkers.checker("%h  Character", "64", str);
        str = String.format("%h", dd);
        errorCount += Checkers.checker("%h  Double", "8c921001", str);
        str = String.format("%H", ddNeg);
        errorCount += Checkers.checker("%H  Double-neg", "C921001", str);
        str = String.format("%h", ff);
        errorCount += Checkers.checker("%h  Float", "42f6e666", str);
        str = String.format("%H", ffNeg);
        errorCount += Checkers.checker("%H  Float-neg", "C2F6E666", str);
        str = String.format("%h", ii);
        errorCount += Checkers.checker("%h  Integer", "3039", str);
        str = String.format("%H", iiNeg);
        errorCount += Checkers.checker("%H  Integer-neg", "FFFFCFC7", str);
        str = String.format("%h", jj);
        errorCount += Checkers.checker("%h  Long", "3039", str);
        str = String.format("%H", jjNeg);
        errorCount += Checkers.checker("%H  Long-neg", "3038", str);
        str = String.format("%h", sh);
        errorCount += Checkers.checker("%h  Short", "7b", str);
        str = String.format("%H", shNeg);
        errorCount += Checkers.checker("%H  Short-neg", "FFFFFF85", str);
        
        // Character (%c|%C)
        str = String.format("%c %C", cc, cc);
        errorCount += Checkers.checker("%c %C   cc, cc", "d D", str);       
        
        // Integer decimal int (%o)
        str = String.format("%d", ii);
        errorCount += Checkers.checker("Integer %d   ii", "12345", str);       
        str = String.format("%d", iiNeg);
        errorCount += Checkers.checker("Integer %d   iiNeg", "-12345", str);       
        
        // Integer octal int (%o)
        str = String.format("%o", ii);
        errorCount += Checkers.checker("Integer %o   ii", "30071", str);       
        str = String.format("%o", iiNeg);
        errorCount += Checkers.checker("Integer %o   iiNeg", "37777747707", str);       
        
        // Integer hexidecimal in (%x)
        str = String.format("%06x", ii);
        errorCount += Checkers.checker("Integer %06x   ii", "003039", str);       
        str = String.format("%10X", iiNeg);
        errorCount += Checkers.checker("Integer %10X   iiNeg", "  FFFFCFC7", str);       
        str = String.format("%010X", iiNeg);
        errorCount += Checkers.checker("Integer %010X   iiNeg", "00FFFFCFC7", str);       
        
        // Long decimal int (%o)
        str = String.format("%d", jj);
        errorCount += Checkers.checker("Long %d   jj", "12345", str);       
        str = String.format("%d", jjNeg);
        errorCount += Checkers.checker("Long %d   jjNeg", "-12345", str);       
        
        // Long octal int (%o)
        str = String.format("%o", jj);
        errorCount += Checkers.checker("Long %o   jj", "30071", str);       
        str = String.format("%o", jjNeg);
        errorCount += Checkers.checker("Long %o   jjNeg", "1777777777777777747707", str);       
        
        // Long hexidecimal in (%x)
        str = String.format("%06x", jj);
        errorCount += Checkers.checker("Long %06x   jj", "003039", str);       
        str = String.format("%10X", jjNeg);
        errorCount += Checkers.checker("Long %10X   jjNeg", "FFFFFFFFFFFFCFC7", str);       
        str = String.format("%010X", jjNeg);
        errorCount += Checkers.checker("Long %010X   jjNeg", "FFFFFFFFFFFFCFC7", str);       

        // Double (%e|%E|%f)
        str = String.format("%18.6e", dd);
        errorCount += Checkers.checker("%18.6e  123.45", "      1.234500e+02", str);
        str = String.format("%18.6E", dd);
        errorCount += Checkers.checker("%18.6E  123.45", "      1.234500E+02", str);
        str = String.format("%18.6f", dd);
        errorCount += Checkers.checker("%18.6f  123.45", "        123.450000", str);
        
        // Float (%e|%E|%f)
        str = String.format("%18.6e", dd);
        errorCount += Checkers.checker("Float %18.6e  123.45", "      1.234500e+02", str);
        str = String.format("%18.6E", dd);
        errorCount += Checkers.checker("Float %18.6E  123.45", "      1.234500E+02", str);
        str = String.format("%18.6f", dd);
        errorCount += Checkers.checker("Float %18.6f  123.45", "        123.450000", str);
        
        // String (%h)
        String mary = "Mary had a little lamb!";
        str = String.format("%h", mary);
        errorCount += Checkers.checker("String %h", "5f897ec6", str);
        
        /***************
        
        COMMENTED OUT:
        chatGPT:

            "When you first call obj.hashCode(), the JVM checks the mark word:
            If the mark word already contains a hash code, return it.
            Otherwise, generate a new hash code, store it into the mark word (if possible), and return it.
            The hash code is a pseudo-random value, not the raw memory address.

            On 64-bit HotSpot, it’s typically generated from a combination of the object’s allocation address, thread-local counters, and some mixing function.
            The exact algorithm varies between JVM builds and flags, but the goal is to avoid collisions and to remain stable for the lifetime of the object."

        
        // A data-only object (%h)
        MyDataObject objData = new MyDataObject();
        str = String.format("%h", objData);
        errorCount += Checkers.checker("MyDataObject %h", "2f0e140b", str);
        
        // A full object (%H)
        MyFullObject objFull = new MyFullObject();
        str = String.format("%h", objFull);
        errorCount += Checkers.checker("MyFullObject %H", "49476842", str);
        
        ****************/
        
        Checkers.theEnd(errorCount);
    }
}

class MyDataObject {
        boolean myzz = true;
        double mydd = 123.45;
        double myddNeg = -123.45;
        int myii = 12345;
        int myiiNeg = -12345;
        String mymary = "Mary had a little lamb";
        char mycc = 'd';
}

class MyFullObject {
        boolean myzz = true;
        double mydd = 123.45;
        double myddNeg = -123.45;
        int myii = 12345;
        int myiiNeg = -12345;
        String mymary = "Mary had a little lamb";
        char mycc = 'd';
        
        public MyFullObject() {
            System.out.println("Hello from MyFullObject!");
        }
}

