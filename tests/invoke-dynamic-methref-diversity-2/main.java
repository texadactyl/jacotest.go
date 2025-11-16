/* ===========================================================
   Extreme Method Reference Diversity
   - Triggers many distinct invokedynamic shapes
   - Includes loops and arrays
   - No java.util.*, no java.lang.invoke.*
   =========================================================== */

@FunctionalInterface interface StrSupplier { String get(); }
@FunctionalInterface interface StrFunction { String apply(String s); }
@FunctionalInterface interface BiStrFunction { String apply(String a, String b); }
@FunctionalInterface interface IntToStr { String apply(int x); }
@FunctionalInterface interface IntUnary { int apply(int x); }
@FunctionalInterface interface IntBinary { int apply(int a, int b); }
@FunctionalInterface interface ObjMaker<T> { T make(); }
@FunctionalInterface interface ObjFunction<T,R> { R apply(T t); }
@FunctionalInterface interface BiObjFunction<A,B,R> { R apply(A a,B b); }
@FunctionalInterface interface TriObjFunction<A,B,C,R> { R apply(A a,B b,C c); }
@FunctionalInterface interface IntArrayMaker { int[] make(int size); }
@FunctionalInterface interface Int2DArrayMaker { int[][] make(int rows,int cols); }
@FunctionalInterface interface StringArrayMaker { String[] make(int size); }
@FunctionalInterface interface String2DArrayMaker { String[][] make(int rows,int cols); }

public class main {

    /* =========================== Static Methods ========================== */
    public static String greet() { return "HI"; }
    public static String join(String a, String b) { return a + "|" + b; }
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
        int value;
        Thing() { this.label = "EMPTY"; this.value = 0; }
        Thing(String l, int v) { this.label = l; this.value = v; }
        public String getLabel() { return label; }
        public int getValue() { return value; }
    }

    public static void main(String[] args) {
        int errorCount = 0;
        main obj = new main();

        /* =================== STATIC METHODS =================== */
        StrSupplier s1 = main::greet;
        errorCount += Checkers.checker("static greet", "HI", s1.get());

        BiStrFunction joiner = main::join;
        errorCount += Checkers.checker("static join", "A|B", joiner.apply("A","B"));

        IntToStr overInt = main::overloaded;
        errorCount += Checkers.checker("static overloaded(int)", "INT:42", overInt.apply(42));

        StrFunction overStr = main::overloaded;
        errorCount += Checkers.checker("static overloaded(String)", "STR:XYZ", overStr.apply("XYZ"));

        IntBinary adder = main::add;
        errorCount += Checkers.checker("static add", "42", ""+adder.apply(40,2));

        /* =================== BOUND INSTANCE METHODS =================== */
        StrSupplier s2 = obj::instanceHello;
        errorCount += Checkers.checker("bound instanceHello", "INSTANCE", s2.get());

        BiStrFunction comb = obj::combine;
        errorCount += Checkers.checker("bound combine", "X<->Y", comb.apply("X","Y"));

        IntUnary doubler = obj::doubleIt;
        errorCount += Checkers.checker("bound doubleIt", "42", ""+doubler.apply(21));

        /* =================== UNBOUND INSTANCE METHODS =================== */
        ObjFunction<main,String> unbound = main::instanceHello;
        errorCount += Checkers.checker("unbound instanceHello", "INSTANCE", unbound.apply(obj));

        TriObjFunction<main,String,String,String> unbound2 = main::combine;
        errorCount += Checkers.checker("unbound combine", "P<->Q", unbound2.apply(obj,"P","Q"));

        /* =================== CONSTRUCTOR REFERENCES =================== */
        ObjMaker<Thing> mk1 = Thing::new;
        Thing t1 = mk1.make();
        errorCount += Checkers.checker("Thing() constructor", "EMPTY", t1.getLabel());

        BiObjFunction<String, Integer, Thing> mk2 = Thing::new;
        Thing t2 = mk2.apply("CAT", 7);
        errorCount += Checkers.checker("Thing(String,int) constructor label", "CAT", t2.getLabel());
        errorCount += Checkers.checker("Thing(String,int) constructor value", "7", ""+t2.getValue());

        /* =================== ARRAY CONSTRUCTOR REFERENCES =================== */
        IntArrayMaker arr1 = int[]::new;
        int[] a = arr1.make(3);
        errorCount += Checkers.checker("int[]::new length", "3", ""+a.length);

        Int2DArrayMaker arr2 = (rows, cols) -> new int[rows][cols];
        int[][] b = arr2.make(2,4);
        errorCount += Checkers.checker("int[][]::new dimensions", "2", ""+b.length);
        errorCount += Checkers.checker("int[][]::new inner length", "4", ""+b[0].length);

        StringArrayMaker arr3 = String[]::new;
        String[] c = arr3.make(5);
        errorCount += Checkers.checker("String[]::new length", "5", ""+c.length);

        String2DArrayMaker arr4 = (rows, cols) -> new String[rows][cols];
        String[][] d = arr4.make(3,2);
        errorCount += Checkers.checker("String[][]::new dimensions", "3", ""+d.length);
        errorCount += Checkers.checker("String[][]::new inner length", "2", ""+d[0].length);

        /* =================== LOOP-BOUND METHOD REFERENCES =================== */
        for (int i=0; i<3; i++) {
            StrSupplier loopRef = obj::instanceHello; // invokedynamic per iteration
            String observed = loopRef.get();
            errorCount += Checkers.checker("loop bound instanceHello iteration "+i, "INSTANCE", observed);
        }

        /* =================== END =================== */
        Checkers.theEnd(errorCount);
    }
}

