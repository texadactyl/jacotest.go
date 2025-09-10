
public class main {
    public static void main(String[] args) {
        boolean zz;
        
        MyClass objSimple = new MyClass();
        MyClass objArray[] = { objSimple };
        String strSimple = "ABC";
        String strArray[] = { "ABC" };
        
        zz = objSimple instanceof MyClass;
        System.out.printf("objSimple instanceof MyClass: %b\n", zz);
        zz = objSimple instanceof Object;
        System.out.printf("objSimple instanceof Object: %b\n", zz);
        
        zz = objArray instanceof MyClass[];
        System.out.printf("objArray instanceof MyClass[]: %b\n", zz);
        zz = objArray instanceof Object[];
        System.out.printf("objArray instanceof Object[]: %b\n", zz);
        
        zz = strSimple instanceof String;
        System.out.printf("strSimple instanceof String: %b\n", zz);
        zz = strSimple instanceof Object;
        System.out.printf("strSimple instanceof Object: %b\n", zz);

        zz = strArray instanceof String[];
        System.out.printf("strArray instanceof String[]: %b\n", zz);
        zz = strArray instanceof Object[];
        System.out.printf("strArray instanceof Object[]: %b\n", zz);
    }
}

class MyClass {
    int x = 0;
}
