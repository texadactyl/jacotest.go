public class main {
    private static double d1 = 1.0;
    private static final double d2 = 2.0;

    // Check observed string to expected string.
    private static int checker(String label, String expected, String observed) {
        if (observed.equals("ignore")) {
            System.out.printf("Ignore :: %s, expected=\"%s\"\n", label, expected);
            return 0;
        }
        if (observed.equals(expected)) {
            System.out.printf("Success :: %s\n", label);
            return 0;
        }
        System.out.printf("*** ERROR :: %s, expected=\"%s\", observed=\"%s\"\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
        int errorCount = 0;
        System.out.println(d1);
        System.out.println(d2);       
        errorCount += checker("static main.d1", "1", jj._getStaticString("main", "d1"));
        errorCount += checker("static main.d2", "2", jj._getStaticString("main", "d2"));
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

