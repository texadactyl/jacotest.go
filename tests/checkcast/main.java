import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        int errorCount = 0;

        MyClass objSimple = new MyClass();
        MyClass objArray[] = { objSimple };
        String strSimple = "ABC";
        String strArray[] = {"ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VXYZ"};

        // Upcast to Object first, then downcast  --->  CHECKCAST
        try {
            Object tmp = objSimple;
            MyClass cast1 = (MyClass) tmp;  // CHECKCAST MyClass
            System.out.println("ok Cast Object ---> MyClass");
        } catch (ClassCastException e) {
            System.out.println("*** ERROR, Cast Object ---> MyClass");
            errorCount++;
        }

        try {
            Object tmp = objArray;
            MyClass[] cast3 = (MyClass[]) tmp;  // CHECKCAST [LMyClass;
            System.out.println("ok Cast Object ---> MyClass[]");
        } catch (ClassCastException e) {
            System.out.println("*** ERROR, Cast Object ---> MyClass[]");
            errorCount++;
        }

        try {
            Object tmp = strSimple;
            String cast5 = (String) tmp;  // CHECKCAST java/lang/String
            System.out.println("ok Cast Object ---> String");
        } catch (ClassCastException e) {
            System.out.println("*** ERROR, Cast Object ---> String");
            errorCount++;
        }

        try {
            Object tmp = strArray;
            String[] cast7 = (String[]) tmp;  // CHECKCAST [Ljava/lang/String;
            System.out.println("ok Cast Object ---> String[]");
        } catch (ClassCastException e) {
            System.out.println("*** ERROR, Cast Object ---> String[]");
            errorCount++;
        }

        try {
            ArrayList<String> arrList = new ArrayList<>();
            for (String str : strArray) {
                arrList.add(str);
            }        
            String[] outArray = arrList.toArray(new String[arrList.size()]); // <-------------- Potential CHECKCAST issue
            System.out.println("ok Cast ArrayList.toArray(new String[arrList.size()]) ---> String[]");
        } catch (ClassCastException e) {
            System.out.printf("*** ERROR, Cast arrList.toArray(new String[arrList.size()]) ---> String[] caught:\n\t%s\n", e.getMessage());
            errorCount++;
        }

        // Force a failure
        try {
            Object tmp = strSimple;
            MyClass badCast = (MyClass) tmp;  // CHECKCAST MyClass, throws
            System.out.println("*** ERROR, Cast Object(String) ---> MyClass: failed to throw ClassCastException");
            ++errorCount;
        } catch (ClassCastException e) {
            System.out.println("ok Cast Object(String) ---> MyClass caught ClassCastException as expected");
        }

        Checkers.theEnd(errorCount);
    }
}

class MyClass {
    int iAmNotUsed = 0;
}

