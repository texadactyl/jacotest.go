public class main {

    static byte bite = 19;
    static byte ba[] = {1, 2, 3};
    static int ia[] = {1, 2, 3};
    static String sstr = "Mary had a little lamb";
    static KlassKambing[] skkvec = { new KlassKambing(101, 102.0, "103"), new KlassKambing(201, 202.0, "203"), new KlassKambing(301, 302.0, "303") };
    static KlassKambing skkscalar = new KlassKambing(101, 102.0, "103");

    // Check observed string to expected string.
    private static int checker(String label, String expected, String observed) {
        if (observed.equals("openjdk")) {
            System.out.printf("openjdk :: %s, expected=\"%s\"\n", label, expected);
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
        String subbie = "Ljava/lang/String; [49 48 51]";
        int index;
    
        // --------------------- statics
        
        String str = jj._getStaticString("main", "bite");
        errorCount += checker("static main bite", "13", str);
        if (str.equals("openjdk")) { System.exit(0); }
        
        str = jj._getStaticString("main", "ba");
        errorCount += checker("static main ba", "1,2,3", str);
        
        str = jj._getStaticString("main", "ia");
        errorCount += checker("static main ia", "1,2,3", str);
        
        str = jj._getStaticString("main", "skkvec");
        System.out.printf("static main skkvec: %s\n", str);
        index = str.indexOf(subbie);
        if (index < 0) {
            errorCount += 1;
            System.out.printf("*** ERROR, static main skkvec did not include \"%s\"\n", subbie);
        }
        
        str = jj._getStaticString("main", "skkscalar");
        System.out.printf("static main skkscalar: %s\n", str);
        index = str.indexOf(subbie);
        if (index < 0) {
            errorCount += 1;
            System.out.printf("*** ERROR, static main skkscalar did not include \"%s\"\n", subbie);
        }
        
        // --------------------- fields
        
        KlassKambing fkkvec[] = { new KlassKambing(101, 102.0, "103"), new KlassKambing(2001, 2002.0, "2003"), new KlassKambing(3001, 3002.0, "3003") }; 
        str = jj._getFieldString(fkkvec, "value");
        index = str.indexOf(subbie);
        if (index < 0) {
            errorCount += 1;
            System.out.printf("*** ERROR, field main skkvec did not include \"%s\"\n", subbie);
        }
        
        KlassKambing fkkscalar = new KlassKambing(4001, 4002.0, "4003"); 
        str = jj._getFieldString(fkkscalar, "kki");
        errorCount += checker("static fkkscalar kki", "4001", str);
        
        str = jj._getFieldString(fkkscalar, "kkd");
        errorCount += checker("static fkkscalar kkd", "4002", str);
        
        str = jj._getFieldString(fkkscalar, "kks");
        errorCount += checker("static fkkscalar kks", "4003", str);
        if (!fkkscalar.kks.equals(str)) {
            errorCount += 1;
            System.out.printf("*** ERROR, expected field fkkscalar.kks = \"%s\", observed = \"%s\"\n", str, fkkscalar.kks);
        }
        
        assert(errorCount == 0);
    }
    
}

// Class Kambing, instantiated in the main class.
class KlassKambing {
    int kki;
    double kkd;
    String kks;
    KlassKambing (int argi, double argd, String args) {
        kki = argi;
        kkd = argd;
        kks = args;
    }
}

// Class jj in case we are executed by the OpenJDL JVM.
class jj {

   public static void _dumpStatics(String from, int selection, String className) {
        System.out.printf("J-class function _dumpStatics: from=\"%s\", selection=%d, className=\"%s\": dummy (not Jacobin)\n", from, selection, className);
   }
   
   public static void _dumpObject(Object obj, String title, int indent) {
        System.out.printf("J-class function _dumpObject: title=\"%s\", indent=%d: dummy (not Jacobin)\n", title, indent);
   }
   
   public static String _getStaticString(String className, String fieldName) {
        String str = String.format("J-class function _getStaticString: className=\"%s\", fieldName=%s: dummy (not Jacobin)", className, fieldName);
        return "openjdk";
   }
   
   public static String _getFieldString(Object obj, String fieldName) {
        String str = String.format("J-class function _getFieldString: fieldName=%s: dummy (not Jacobin)", fieldName);
        return "openjdk";
   }
   
}

