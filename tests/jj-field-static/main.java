//import java.lang.AssertionError;

public class main {

    private static int mainClassInt = 1;
    private static final double mainClassDouble = 2.0;
    private static String mainClassString = "three";
    
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
        errorCount += checker("static main.mainClassInt", "1", jj._getStaticString("main", "mainClassInt"));
        errorCount += checker("static main.mainClassDouble", "2", jj._getStaticString("main", "mainClassDouble"));
        errorCount += checker("static main.mainClassString", "three", jj._getStaticString("main", "mainClassString"));
        System.out.println("=========================== jj._getStaticString end");
        
        System.out.println("\n=========================== objKambing jj._getFieldString begin");
        errorCount += checker("field objKambing.alaska", "1", jj._getFieldString(objKambing, "alaska"));
        errorCount += checker("field objKambing.Abraham", "2", jj._getFieldString(objKambing, "Abraham"));
        errorCount += checker("field objKambing.aBEL", "three", jj._getFieldString(objKambing, "aBEL"));
        System.out.println("=========================== objKambing jj._getFieldString end");
        
        System.out.println("\n=========================== objMain jj._getFieldString begin");
        try {
            errorCount += checker("field objMain.mainFunInt", "1", jj._getFieldString(objMain, "mainFunInt"));
            errorCount += checker("field objMain.mainFunDouble", "2", jj._getFieldString(objMain, "mainFunDouble"));
            errorCount += checker("field objMain.mainFunString", "three", jj._getFieldString(objMain, "mainFunString"));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("=========================== objMain jj._getFieldString end");

        System.out.println();
         if (errorCount == 0) {
            System.exit(0);
        }
        throw new AssertionError("*** At least one error occurred.");
        
    }
    
    private void fun() {
        int funInt = 1;
        double funDouble = 2.0;
        String funString = "three";
    }
}

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

class KlassKambing {
    int alaska = 1;
    double Abraham = 2.0;
    String aBEL = "three";
}

