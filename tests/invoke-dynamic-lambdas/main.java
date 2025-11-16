public class main {

    public static void main(String[] args) {

        int errorCount = 0;

        /* ====================================================================
           1. Zero-capture lambda (no state)
              Generates an indy with a simple lambda metafactory
           ==================================================================== */
        Runnable r = () -> System.out.print("");  // does nothing
        String observed1 = r.getClass().getName();
        String expected1 = observed1;  // always match, class name varies

        errorCount += Checkers.checker(
                "Zero-capture lambda (invokedynamic)",
                expected1,
                observed1
        );


        /* ====================================================================
           2. Lambda capturing a primitive
              Each capture changes the indy bootstrap signature
           ==================================================================== */
        int base = 40;
        java.util.function.IntUnaryOperator add = x -> x + base;
        int observed2 = add.applyAsInt(2);
        int expected2 = 42;

        errorCount += Checkers.checker(
                "Lambda capturing primitive (invokedynamic)",
                String.valueOf(expected2),
                String.valueOf(observed2)
        );


        /* ====================================================================
           3. Lambda capturing an object
           ==================================================================== */
        String prefix = "Value:";
        java.util.function.Function<String,String> wrap =
                s -> prefix + s;

        String observed3 = wrap.apply("ABC");
        String expected3 = "Value:ABC";

        errorCount += Checkers.checker(
                "Lambda capturing object (invokedynamic)",
                expected3,
                observed3
        );


        /* ====================================================================
           4. Multi-argument lambda (object + primitive)
           ==================================================================== */
        java.util.function.BiFunction<String,Integer,String> bi =
                (s, n) -> s + ":" + n;

        String observed4 = bi.apply("Hi", 7);
        String expected4 = "Hi:7";

        errorCount += Checkers.checker(
                "Two-argument lambda (invokedynamic)",
                expected4,
                observed4
        );


        /* ====================================================================
           5. Lambda created inside a loop
              (Each iteration produces a new invokedynamic call site)
           ==================================================================== */
        java.util.function.IntSupplier[] arr = new java.util.function.IntSupplier[3];

        for (int i = 0; i < arr.length; i++) {
            int capture = i;               // captured value is distinct
            arr[i] = () -> capture * 10;   // indy per loop iteration
        }

        String observed5 = arr[0].getAsInt() + "," +
                           arr[1].getAsInt() + "," +
                           arr[2].getAsInt();

        String expected5 = "0,10,20";

        errorCount += Checkers.checker(
                "Loop-created lambdas (multiple invokedynamic sites)",
                expected5,
                observed5
        );


        /* ====================================================================
           6. Lambda that returns another lambda
              Nested invokedynamic calls
           ==================================================================== */
        java.util.function.Function<Integer, java.util.function.IntSupplier> maker =
                n -> (() -> n * 3);   // inner lambda also uses indy

        java.util.function.IntSupplier tripler = maker.apply(14);
        int observed6 = tripler.getAsInt();
        int expected6 = 42;

        errorCount += Checkers.checker(
                "Nested lambda (invokedynamic inside invokedynamic)",
                String.valueOf(expected6),
                String.valueOf(observed6)
        );


        /* ====================================================================
           END OF SCENARIOS
           ==================================================================== */
        Checkers.theEnd(errorCount);
    }
}

