public class main {
    public static void main(String[] args) {
    
        MyClass myObject = new MyClass();
        jj._dumpObject(myObject, "My Title", 0);     
        jj._dumpStatics("Statics Dump", 3, "");
 
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

class MyClass {
    int alaska = 1;
    int Abraham = 2;
    int aBEL = 3;
}

