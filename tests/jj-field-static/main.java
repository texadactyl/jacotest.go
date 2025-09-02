//import java.lang.AssertionError;

public class main {

    // nain class statics
    private static final boolean mainStaticBoolean = true;
    private static final byte mainStaticByte = 7;
    private static final char mainStaticChar = '8';
    private static final double mainStaticDouble = 2.0;
    private static final float mainStaticFloat = 4.0f;
    private static final int mainStaticInt = 1;
    private static final long mainStaticLong = 5;
    private static final short mainStaticShort = 6;
    private static final String mainStaticString = "three";

    // main class fields
    private int mainFieldInt = 1;
    private final double mainFieldDouble = 2.0;
    private String mainFieldString = "three";
    
    // Main entry point.
    public static void main(String[] args) {
    
        int errorCount = 0;
    
        int mainFunInt = 1;
        final double mainFunDouble = 2.0;
        String mainFunString = "three";

        KlassKambing objKambing = new KlassKambing();
        System.out.println("=========================== jj._dumpObject(objKambing...) begin");
        jj._dumpObject(objKambing, "objKambing from KlassKambing", 0);
        System.out.println("=========================== jj._dumpObject(objKambing...) end");
        
        main objMain = new main();
        System.out.println("=========================== jj._dumpObject(objMain...) begin");
        jj._dumpObject(objMain, "objMain from main", 0);
        System.out.println("=========================== jj._dumpObject(objMain...) end");

        jj._dumpStatics("Statics Dump", 3, "");
        
        System.out.println("\n=========================== jj._getStaticString begin");
        errorCount += Checkers.checker("static main.mainStaticBoolean", "true", jj._getStaticString("main", "mainStaticBoolean"));
        errorCount += Checkers.checker("static main.mainStaticByte", "0x07", jj._getStaticString("main", "mainStaticByte"));
        errorCount += Checkers.checker("static main.mainStaticChar", "8", jj._getStaticString("main", "mainStaticChar"));
        errorCount += Checkers.checker("static main.mainStaticDouble", "2", jj._getStaticString("main", "mainStaticDouble"));
        errorCount += Checkers.checker("static main.mainStaticFloat", "4", jj._getStaticString("main", "mainStaticFloat"));
        errorCount += Checkers.checker("static main.mainStaticInt", "1", jj._getStaticString("main", "mainStaticInt"));
        errorCount += Checkers.checker("static main.mainStaticLong", "5", jj._getStaticString("main", "mainStaticLong"));
        errorCount += Checkers.checker("static main.mainStaticShort", "6", jj._getStaticString("main", "mainStaticShort"));
        errorCount += Checkers.checker("static main.mainStaticString", "three", jj._getStaticString("main", "mainStaticString"));
        System.out.println("=========================== jj._getStaticString end");
        
        System.out.println("\n=========================== objKambing jj._getFieldString begin");
        errorCount += Checkers.checker("field objKambing.alaska", "1", jj._getFieldString(objKambing, "alaska"));
        errorCount += Checkers.checker("field objKambing.Abraham", "2", jj._getFieldString(objKambing, "Abraham"));
        errorCount += Checkers.checker("field objKambing.aBEL", "three", jj._getFieldString(objKambing, "aBEL"));
        System.out.println("=========================== objKambing jj._getFieldString end");
        
        System.out.println("\n=========================== objMain jj._getFieldString begin");
        try {
            errorCount += Checkers.checker("field objMain.mainFieldInt", "1", jj._getFieldString(objMain, "mainFieldInt"));
            errorCount += Checkers.checker("field objMain.mainFieldDouble", "2", jj._getFieldString(objMain, "mainFieldDouble"));
            errorCount += Checkers.checker("field objMain.mainFieldString", "three", jj._getFieldString(objMain, "mainFieldString"));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("=========================== objMain jj._getFieldString end");

        Checkers.theEnd(errorCount);
        
    }
    
    private void fun() {
        int funInt = 1;
        double funDouble = 2.0;
        String funString = "three";
    }
}

// Class Kambing, instantiated in the main class.
class KlassKambing {
    int alaska = 1;
    double Abraham = 2.0;
    String aBEL = "three";
}

// Class jj in case we are executed by the OpenJDK JVM.
class jj {

   public static void _dumpStatics(String from, int selection, String className) {
        System.out.printf("J-class function _dumpStatics: from=\"%s\", selection=%d, className=\"%s\": dummy (not Jacobin)\n", from, selection, className);
   }
   
   public static void _dumpObject(Object obj, String title, int indent) {
        System.out.printf("J-class function _dumpObject: title=\"%s\", indent=%d: dummy (not Jacobin)\n", title, indent);
   }
   
   public static String _getStaticString(String className, String fieldName) {
        String str = String.format("J-class function _getStaticString: className=\"%s\", fieldName=%s: dummy (not Jacobin)", className, fieldName);
        return "ignore";
   }
   
   public static String _getFieldString(Object obj, String fieldName) {
        String str = String.format("J-class function _getFieldString: fieldName=%s: dummy (not Jacobin)", fieldName);
        return "ignore";
   }
   
}

