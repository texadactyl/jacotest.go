public class main {

    private static int mainClassInt = 1;
    private static final double mainClassDouble = 2.0;
    private static String mainClassString = "three";

    public static void main(String[] args) {
    
        int mainFunInt = 1;
        final double mainFunDouble = 2.0;
        String mainFunString = "three";

        KlassKambing objKambing = new KlassKambing();
        System.out.println("=========================== jj._dumpObject(objKambing...) begin");
        jj._dumpObject(objKambing, "objKambing from KlassKambing", 0);
        System.out.println("=========================== jj._dumpObject(objKambing...) end");
        
        main objMain = new main();
        System.out.println("=========================== jj._dumpObject(objMain...) begin");
        jj._dumpObject(objMain, "Second main object", 0);
        System.out.println("=========================== jj._dumpObject(objMain...) end");

        jj._dumpStatics("Statics Dump", 3, "");
        
    }
    
    private void fun() {
        int funInt = 1;
        double funDouble = 2.0;
        String funString = "three";
    }
}

class jj {
   public static void _dumpStatics(String from, int selection, String className) {
        System.out.printf("J-class function _dumpStatics: from=\"%s\", selection=%d, className=\"%s\": EMPTY (not Jacobin)\n", from, selection, className);
   }
   public static void _dumpObject(Object obj, String title, int indent) {
        System.out.printf("J-class function _dumpObject: title=\"%s\", indent=%d: EMPTY (not Jacobin)\n", title, indent);
   }
}

class KlassKambing {
    int alaska = 1;
    double Abraham = 2.0;
    String aBEL = "three";
}

