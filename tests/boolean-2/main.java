public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        // ===============================
        // Basic Boolean value tests
        // ===============================

        errorCount += Checkers.checker(
            "valueOf(true)",
            Boolean.TRUE,
            Boolean.valueOf(true));

        errorCount += Checkers.checker(
            "valueOf(false)",
            Boolean.FALSE,
            Boolean.valueOf(false));

        errorCount += Checkers.checker(
            "parseBoolean(\"true\")",
            true,
            Boolean.parseBoolean("true"));

        errorCount += Checkers.checker(
            "parseBoolean(\"false\")",
            false,
            Boolean.parseBoolean("false"));

        errorCount += Checkers.checker(
            "parseBoolean(\"junk\")",
            false,
            Boolean.parseBoolean("junk"));

        // ===============================
        // compare / compareTo
        // ===============================

        errorCount += Checkers.checker(
            "compare(false,false)",
            0,
            Boolean.compare(false, false));

        errorCount += Checkers.checker(
            "compare(false,true)",
            -1,
            Boolean.compare(false, true));

        errorCount += Checkers.checker(
            "compare(true,false)",
            1,
            Boolean.compare(true, false));

        errorCount += Checkers.checker(
            "TRUE.compareTo(FALSE)",
            1,
            Boolean.TRUE.compareTo(Boolean.FALSE));

        errorCount += Checkers.checker(
            "FALSE.compareTo(TRUE)",
            -1,
            Boolean.FALSE.compareTo(Boolean.TRUE));

        errorCount += Checkers.checker(
            "TRUE.compareTo(TRUE)",
            0,
            Boolean.TRUE.compareTo(Boolean.TRUE));

        // ===============================
        // Null-safety torture tests
        // ===============================

        try {
            Boolean.TRUE.equals(null);
            errorCount += Checkers.checker(
                "TRUE.equals(null)",
                true,
                true);
        } catch (Throwable t) {
            errorCount += Checkers.fail("TRUE.equals(null) threw");
        }

        try {
            Boolean.TRUE.compareTo(null);
            errorCount += Checkers.fail("TRUE.compareTo(null) did not throw");
        } catch (NullPointerException npe) {
            errorCount += Checkers.checker(
                "TRUE.compareTo(null) throws NPE",
                true,
                true);
        }

        try {
            Boolean.FALSE.compareTo(null);
            errorCount += Checkers.fail("FALSE.compareTo(null) did not throw");
        } catch (NullPointerException npe) {
            errorCount += Checkers.checker(
                "FALSE.compareTo(null) throws NPE",
                true,
                true);
        }

        // ===============================
        // Identity tests: == vs equals
        // ===============================

        Boolean t1 = Boolean.TRUE;
        Boolean t2 = Boolean.valueOf(true);

        Boolean f1 = Boolean.FALSE;
        Boolean f2 = Boolean.valueOf(false);

        errorCount += Checkers.checker(
            "TRUE == valueOf(true)",
            true,
            t1.booleanValue() == t2.booleanValue());

        errorCount += Checkers.checker(
            "FALSE == valueOf(false)",
            true,
            f1.booleanValue() == f2.booleanValue());

        // ===============================
        // Boolean cache invariants stress
        // ===============================

        Boolean trueRef = Boolean.TRUE;
        Boolean falseRef = Boolean.FALSE;

        for (int i = 0; i < 100; i++) {
            if (Boolean.valueOf(true).booleanValue() != trueRef.booleanValue()) {
                errorCount += Checkers.fail(
                    String.format("TRUE cache broken at iteration %d", i));
                break;
            }
            if (Boolean.valueOf(false).booleanValue() != falseRef.booleanValue()) {
                errorCount += Checkers.fail(
                    String.format("FALSE cache broken at iteration %d", i));
                break;
            }
        }

        errorCount += Checkers.checker(
            "TRUE != FALSE (identity)",
            true,
            Boolean.TRUE != Boolean.FALSE);

        // ===============================
        // Final check
        // ===============================

        Checkers.theEnd(errorCount);
    }
}

