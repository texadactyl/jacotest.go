/* ================================================================
   Method Reference Exerciser (invokedynamic)
   Uses only custom functional interfaces — no java.util classes.
   ================================================================ */

@FunctionalInterface
interface StrSupplier {
    String get();
}

@FunctionalInterface
interface StrFunction {
    String apply(String s);
}

@FunctionalInterface
interface IntFunction {
    int apply(int x);
}

public class main {

    /* Some static methods for testing */
    public static String staticGreet() {
        return "HELLO";
    }

    public static String staticEcho(String s) {
        return "ECHO:" + s;
    }

    public static int staticAdd10(int x) {
        return x + 10;
    }

    /* Instance methods for testing */
    public String instanceHello() {
        return "INSTANCE-HELLO";
    }

    public String instanceWrap(String s) {
        return "[" + s + "]";
    }

    public int instanceDouble(int x) {
        return x * 2;
    }

    public static void main(String[] args) {

        int errorCount = 0;
        main obj = new main();  // for instance method refs

        /* ================================================================
           1. Static no-arg method reference
           ================================================================ */
        StrSupplier sup1 = main::staticGreet;
        String observed1 = sup1.get();
        String expected1 = "HELLO";

        errorCount += Checkers.checker(
                "Static no-arg method reference",
                expected1,
                observed1
        );

        /* ================================================================
           2. Static 1-arg method reference
           ================================================================ */
        StrFunction func1 = main::staticEcho;
        String observed2 = func1.apply("XYZ");
        String expected2 = "ECHO:XYZ";

        errorCount += Checkers.checker(
                "Static 1-arg method reference",
                expected2,
                observed2
        );

        /* ================================================================
           3. Static int method reference
           ================================================================ */
        IntFunction add10 = main::staticAdd10;
        int observed3 = add10.apply(32);
        int expected3 = 42;

        errorCount += Checkers.checker(
                "Static int method reference (add10)",
                "" + expected3,
                "" + observed3
        );

        /* ================================================================
           4. Instance no-arg method reference
           ================================================================ */
        StrSupplier sup2 = obj::instanceHello;
        String observed4 = sup2.get();
        String expected4 = "INSTANCE-HELLO";

        errorCount += Checkers.checker(
                "Instance no-arg method reference",
                expected4,
                observed4
        );

        /* ================================================================
           5. Instance method reference with parameter
           ================================================================ */
        StrFunction wrap = obj::instanceWrap;
        String observed5 = wrap.apply("ABCD");
        String expected5 = "[ABCD]";

        errorCount += Checkers.checker(
                "Instance 1-arg method reference",
                expected5,
                observed5
        );

        /* ================================================================
           6. Instance int method reference
           ================================================================ */
        IntFunction doubler = obj::instanceDouble;
        int observed6 = doubler.apply(21);
        int expected6 = 42;

        errorCount += Checkers.checker(
                "Instance int method reference",
                "" + expected6,
                "" + observed6
        );

        /* ================================================================
           END — FINISH UP
           ================================================================ */
        Checkers.theEnd(errorCount);
    }
}

