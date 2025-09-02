public class main {

    static final byte bite = 19;
    static final byte ba[] = {1, 2, 3};
    static final int ia[] = {1, 2, 3};
    static final String sstr = "Mary had a little lamb";
    static final KlassKambing[] skkvec = { new KlassKambing(101, 102.0, "103"), new KlassKambing(201, 202.0, "203"), new KlassKambing(301, 302.0, "303") };
    static final KlassKambing skkscalar = new KlassKambing(101, 102.0, "103");

    public static void main(String[] args) {
    
        int errorCount = 0;
        String subbie = "Ljava/lang/String; [49 48 51]";
        int index;
    
        // --------------------- statics

        String str = jj._getStaticString("main", "bite");
        errorCount += Checkers.checker("jj._getStaticString(\"main\", \"bite\")", "0x13", str);
        if (str.equals("openjdk")) { System.exit(0); }
        
        str = jj._getStaticString("main", "ba");
        errorCount += Checkers.checker("jj._getStaticString(\"main\", \"ba\")", "1,2,3", str);
        
        str = jj._getStaticString("main", "ia");
        errorCount += Checkers.checker("jj._getStaticString(\"main\", \"ia\")", "1,2,3", str);
        
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
        errorCount += Checkers.checker("jj._getFieldString(fkkscalar, \"kki\")", "4001", str);
        
        str = jj._getFieldString(fkkscalar, "kkd");
        errorCount += Checkers.checker("jj._getFieldString(fkkscalar, \"kkd\")", "4002", str);
        
        str = jj._getFieldString(fkkscalar, "kks");
        errorCount += Checkers.checker("jj._getFieldString(fkkscalar, \"kks\")s", "4003", str);
        if (!fkkscalar.kks.equals(str)) {
            errorCount += 1;
            System.out.printf("*** ERROR, expected field fkkscalar.kks = \"%s\", observed = \"%s\"\n", str, fkkscalar.kks);
        }
        
        Checkers.theEnd(errorCount);
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

