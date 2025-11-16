public class main {

    public static void main(String[] args) {
    
        int errorCount = 0;

        /* ====================================================================
           1. Simple concat (String + String)
           ==================================================================== */
        String hello= "Hello";
        String world= "World";
        String observed1 = hello + " " + world;
        String expected1 = "Hello World";
        errorCount += Checkers.checker(
                "Simple concat (invokedynamic)",
                expected1,
                observed1
        );

        /* ====================================================================
           2. Mixed primitives and objects
              This triggers the "makeConcatWithConstants" bootstrap
           ==================================================================== */
        int ix = 40;
        int iy = 2;
        Object tag = "VALUE";
        String observed2 = "Ans=" + (ix + iy) + ", Tag=" + tag;
        String expected2 = "Ans=42, Tag=VALUE";
        errorCount += Checkers.checker(
                "Primitive + Object concat (invokedynamic)",
                expected2,
                observed2
        );

        /* ====================================================================
           3. Many arguments (String + int + long + char + boolean)
              This forces a multi-argument invokedynamic site.
           ==================================================================== */
        long ilong = 123456789L;
        char ch = 'Z';
        boolean flag = true;

        String observed3 =
                "Start:" + hello + ":" + ix + ":" + ilong + ":" + ch + ":" + flag + ":End";

        String expected3 =
                "Start:Hello:40:123456789:Z:true:End";

        errorCount += Checkers.checker(
                "Large multi-argument concat (invokedynamic)",
                expected3,
                observed3
        );

        /* ====================================================================
           4. Concat with constants that javac cannot fold
              (object.toString forces invokedynamic)
           ==================================================================== */
        Object obj = new java.util.Date(0); // always epoch
        String observed4 = "Date=" + obj;
        String expected4 = "Date=" + new java.util.Date(0);
        errorCount += Checkers.checker(
                "Constant prefix + object.toString concat (invokedynamic)",
                expected4,
                observed4
        );

        /* ====================================================================
           5. Concat in a loop (classic idiom)
              This emits an invokedynamic on *each* iteration.
           ==================================================================== */
        String str = "";
        for (int ii = 0; ii < 4; ii++) {
            str = str + "X"; // guaranteed invokedynamic
        }
        String expected5 = "XXXX";

        errorCount += Checkers.checker(
                "Loop concat (invokedynamic every iteration)",
                expected5,
                str
        );

        /* ====================================================================
           END OF SCENARIOS
           ==================================================================== */
        Checkers.theEnd(errorCount);
    }
}

