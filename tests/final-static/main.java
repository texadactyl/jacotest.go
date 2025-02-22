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

    // Check observed string to expected string.
    private static int checker(String label, String expected, String observed) {
        if (observed.equals("ignore")) {
            System.out.printf("Ignore :: %s, expected=\"%s\"\n", label, expected);
            return 0;
        }
        if (observed.equals(expected)) {
            System.out.printf("Success :: %s = %s\n", label, observed);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=\"%s\", observed=\"%s\"\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        int errorCount = 0;

        errorCount += checker("static main.b1", "0x41", jj._getStaticString("main", "b1"));
        errorCount += checker("static main.b2", "0x42", jj._getStaticString("main", "b2"));
        errorCount += checker("static main.c1", "A", jj._getStaticString("main", "c1"));
        errorCount += checker("static main.c2", "B", jj._getStaticString("main", "c2"));
        errorCount += checker("static main.d1", "1", jj._getStaticString("main", "d1"));
        errorCount += checker("static main.d2", "2", jj._getStaticString("main", "d2"));
        errorCount += checker("static main.f1", "1", jj._getStaticString("main", "f1"));
        errorCount += checker("static main.f2", "2", jj._getStaticString("main", "f2"));
        errorCount += checker("static main.i1", "1", jj._getStaticString("main", "i1"));
        errorCount += checker("static main.i2", "2", jj._getStaticString("main", "i2"));
        errorCount += checker("static main.j1", "1", jj._getStaticString("main", "j1"));
        errorCount += checker("static main.j2", "2", jj._getStaticString("main", "j2"));
        errorCount += checker("static main.s1", "1", jj._getStaticString("main", "s1"));
        errorCount += checker("static main.s2", "2", jj._getStaticString("main", "s2"));
        errorCount += checker("static main.str1", "1", jj._getStaticString("main", "str1"));
        errorCount += checker("static main.str2", "2", jj._getStaticString("main", "str2"));

        assert(errorCount == 0);
    }
}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

   public static String _getStaticString(String className, String fieldName) {
        String str = String.format("J-class function _getStaticString: className=\"%s\", fieldName=%s: dummy (not Jacobin)", className, fieldName);
        return "ignore";
   }
}

