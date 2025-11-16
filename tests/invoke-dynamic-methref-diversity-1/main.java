/* =====================================================================
   Method Reference Diversity Exerciser
   Triggers many distinct invokedynamic bootstrap shapes.
   No java.util.*, no java.lang.invoke.*, only custom interfaces.
   ===================================================================== */

@FunctionalInterface interface StrSupplier { String get(); }
@FunctionalInterface interface StrFunction { String apply(String s); }
@FunctionalInterface interface BiStrFunction { String apply(String a, String b); }

@FunctionalInterface interface IntSupplier { int get(); }
@FunctionalInterface interface IntUnary { int apply(int x); }
@FunctionalInterface interface IntBinary { int apply(int a, int b); }

@FunctionalInterface interface ObjMaker<T> { T make(); }
@FunctionalInterface interface ObjFunction<T,R> { R apply(T t); }
@FunctionalInterface interface TriObjFunction<A,B,C,R> { R apply(A a,B b,C c); }

@FunctionalInterface interface IntArrayMaker { int[] make(int size); }
@FunctionalInterface interface StringArrayMaker { String[] make(int size); }

@FunctionalInterface interface IntToStr { String apply(int x); }

public class main {

    /* =========================== Static Methods ========================== */
    public static String greet() { return "HI"; }
    public static String join2(String a, String b) { return a + "|" + b; }
    public static String overloaded(int x) { return "INT:" + x; }
    public static String overloaded(String s) { return "STR:" + s; }
    public static int add(int a, int b) { return a + b; }

    /* =========================== Instance Methods ========================== */
    public String instanceHello() { return "INSTANCE"; }
    public String combine(String a, String b) { return a + "<->" + b; }
    public int doubleIt(int x) { return x * 2; }

    /* =========================== Test Class Constructor ========================== */
    static class Thing {
        String label;
        Thing() { this.label = "EMPTY"; }
        Thing(String l) { this.label = l; }
        public String get() { return label; }
    }

    public static void main(String[] args) {
        int errorCount = 0;
        main obj = new main();

        /* 1. Static no-arg */
        StrSupplier s1 = main::greet;
        errorCount += Checkers.checker("static greet()", "HI", s1.get());

        /* 2. Static two-arg */
        BiStrFunction joiner = main::join2;
        errorCount += Checkers.checker("static join2", "A|B", joiner.apply("A","B"));

        /* 3. Static overloaded (String) */
        StrFunction overStr = main::overloaded;
        errorCount += Checkers.checker("static overloaded(String)", "STR:ZZZ", overStr.apply("ZZZ"));

        /* 4. Static overloaded (int) → use StrFunction since it returns String */
        IntToStr overInt = main::overloaded;
        errorCount += Checkers.checker("static overloaded(int)", "INT:42", overInt.apply(42));

        /* 5. Static int binary */
        IntBinary adder = main::add;
        errorCount += Checkers.checker("static add", "42", ""+adder.apply(40,2));

        /* 6. Bound instance no-arg */
        StrSupplier s2 = obj::instanceHello;
        errorCount += Checkers.checker("bound instanceHello", "INSTANCE", s2.get());

        /* 7. Bound instance two-arg */
        BiStrFunction comb = obj::combine;
        errorCount += Checkers.checker("bound combine", "A<->B", comb.apply("A","B"));

        /* 8. Bound instance int unary */
        IntUnary doubler = obj::doubleIt;
        errorCount += Checkers.checker("bound doubleIt", "42", ""+doubler.apply(21));

        /* 9. Unbound instance no-arg */
        ObjFunction<main,String> unbound = main::instanceHello;
        errorCount += Checkers.checker("unbound instanceHello", "INSTANCE", unbound.apply(obj));

        /* 10. Unbound instance with 2 args → use TriObjFunction */
        TriObjFunction<main,String,String,String> unbound2 = main::combine;
        errorCount += Checkers.checker("unbound combine", "X<->Y", unbound2.apply(obj,"X","Y"));

        /* 11. Constructor no-arg */
        ObjMaker<Thing> mk1 = Thing::new;
        Thing t1 = mk1.make();
        errorCount += Checkers.checker("Thing() constructor", "EMPTY", t1.get());

        /* 12. Constructor with arg */
        ObjFunction<String,Thing> mk2 = Thing::new;
        Thing t2 = mk2.apply("CAT");
        errorCount += Checkers.checker("Thing(String) constructor", "CAT", t2.get());

        /* 13. Array int[] constructor */
        IntArrayMaker arr1 = int[]::new;
        int[] a = arr1.make(3);
        errorCount += Checkers.checker("int[]::new length", "3", ""+a.length);

        /* 14. Array String[] constructor */
        StringArrayMaker arr2 = String[]::new;
        String[] b = arr2.make(5);
        errorCount += Checkers.checker("String[]::new length", "5", ""+b.length);

        /* =========================== END ========================== */
        Checkers.theEnd(errorCount);
    }
}

