public class main {

    private static byte b1 = 0x41;
    private final static byte b2 = 0x42;
    private static byte ba1[] = {0x41, 0x42};
    private final static byte[] ba2 = {0x43, 0x44};
    private static char c1 = 'A';
    private final static char c2 = 'B';
    private static float f1 = 1.0f;
    private final static float f2 = 2.0f;
    private static double d1 = 1.0;
    private final static double d2 = 2.0;
    private static int i1 = 1;
    private final static int i2 = 2;
    private static long j1 = 1;
    private final static long j2 = 2;
    private static short s1 = 1;
    private final static short s2 = 2;
    private static String str1 = "1";
    private final static String str2 = "2";

    public static void main(String[] args) {
        int errorCount = 0;

        errorCount += Checkers.checker("static main.b1", "0x41", jj._getStaticString("main", "b1"));
        errorCount += Checkers.checker("static main.b2", "0x42", jj._getStaticString("main", "b2"));
        errorCount += Checkers.checker("static main.c1", "A", jj._getStaticString("main", "c1"));
        errorCount += Checkers.checker("static main.c2", "B", jj._getStaticString("main", "c2"));
        errorCount += Checkers.checker("static main.d1", "1", jj._getStaticString("main", "d1"));
        errorCount += Checkers.checker("static main.d2", "2", jj._getStaticString("main", "d2"));
        errorCount += Checkers.checker("static main.f1", "1", jj._getStaticString("main", "f1"));
        errorCount += Checkers.checker("static main.f2", "2", jj._getStaticString("main", "f2"));
        errorCount += Checkers.checker("static main.i1", "1", jj._getStaticString("main", "i1"));
        errorCount += Checkers.checker("static main.i2", "2", jj._getStaticString("main", "i2"));
        errorCount += Checkers.checker("static main.j1", "1", jj._getStaticString("main", "j1"));
        errorCount += Checkers.checker("static main.j2", "2", jj._getStaticString("main", "j2"));
        errorCount += Checkers.checker("static main.s1", "1", jj._getStaticString("main", "s1"));
        errorCount += Checkers.checker("static main.s2", "2", jj._getStaticString("main", "s2"));
        errorCount += Checkers.checker("static main.str1", "1", jj._getStaticString("main", "str1"));
        errorCount += Checkers.checker("static main.str2", "2", jj._getStaticString("main", "str2"));

        Checkers.theEnd(errorCount);
    }
}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

   public static String _getStaticString(String className, String fieldName) {
        String str = String.format("J-class function _getStaticString: className=\"%s\", fieldName=%s: dummy (not Jacobin)", className, fieldName);
        switch(fieldName) {
            case "b1": return "0x41";
            case "b2": return "0x42";
            case "c1": return "A";
            case "c2": return "B";
            case "d1": return "1";
            case "d2": return "2";
            case "f1": return "1";
            case "f2": return "2";
            case "i1": return "1";
            case "i2": return "2";
            case "j1": return "1";
            case "j2": return "2";
            case "s1": return "1";
            case "s2": return "2";
            case "str1": return "1";
            case "str2": return "2";
            default: return "rubbish";
        }
   }
}

