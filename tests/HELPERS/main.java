import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.ProcessHandle;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Unit tests for the HELPERS classes used by the Jacotest cases:
 *
 *     Primes                (isPrime, findLargestTwoPrimes)
 *     Checkers              (checker, fail, pass, withinTolerance, replaceEmptyString,
 *                            execCommand, isWindows, isJvmHotspot, theEnd, rptBigDecimal)
 *     jj                    (_subProcess, _getProgramName)
 *     jjSubProcessObject    (exercised through jj._subProcess)
 *
 * Design notes:
 *  - No third-party dependencies (no JUnit). The test case ends with Checkers.theEnd(),
 *    like every other Jacotest case.
 *  - The Checkers methods are NOT used to verify the Checkers methods. This class has its
 *    own tiny assertion layer (expectXxx) so that a defect in Checkers cannot hide itself.
 *  - Checkers and jj print to System.out / System.err. Each call is wrapped so that the
 *    output is captured and then inspected.
 *
 * Child mode: this class also re-launches itself as a sub-process with a first argument
 * of --exit, --emit, or --theEnd, to test the sub-process and System.exit() paths.
 */
public class main {

    private static final String CLASS_NAME = "main";
    private static final String ALLOC_ENV_VAR = "JACOTEST_ALLOC_FILE";
    private static String tempFileName = "RUBBISH!";

    private static int errorCount = 0;
    private static int assertionCount = 0;
    private static int skipCount = 0;

    private static boolean hotspot = false;
    private static boolean execUsable = false;
    private static boolean childModeUsable = false;

    // Output capture state.
    private static PrintStream savedOut;
    private static PrintStream savedErr;
    private static ByteArrayOutputStream outBuffer;
    private static ByteArrayOutputStream errBuffer;
    private static String lastOut = "";
    private static String lastErr = "";
    private static String lastChildOutput = "";

    private static final String[] TEST_NAMES = {
        "testIsPrimeSmallValues",
        "testIsPrimeAgainstSieve",
        "testIsPrimeKnownValues",
        "testFindLargestTwoPrimesFixed",
        "testFindLargestTwoPrimesAgainstSieve",
        "testCheckerBoolean",
        "testCheckerShortIntLong",
        "testCheckerLongArray",
        "testCheckerString",
        "testCheckerBigInteger",
        "testCheckerObject",
        "testCheckerClass",
        "testFailAndPass",
        "testWithinToleranceLongPercent",
        "testWithinToleranceLongAbsolute",
        "testWithinToleranceDouble",
        "testWithinToleranceFloat",
        "testWithinToleranceBigDecimal",
        "testReplaceEmptyString",
        "testIsWindows",
        "testIsJvmHotspot",
        "testExecCommand",
        "testSubProcess",
        "testGetProgramName",
    };

    // ========================================================================
    // main and test dispatch
    // ========================================================================

    public static void main(String[] args) throws IOException {
    
        if (args.length > 0) {
            // Just run the child mode exercises.
            runChildMode(args);
            return;
        }

        // Set up temp file to hold the Jacotest memory allocation number (Jacotest does this).
        long pid = ProcessHandle.current().pid();
        File tempFile = File.createTempFile("./jacotest_alloc_file.", String.valueOf(pid));
        tempFileName = tempFile.getName();
        System.setProperty(ALLOC_ENV_VAR, tempFileName);
        System.out.printf("main: Temp file: %s\n", tempFileName);

        detectEnvironment();
        System.out.printf("main: hotspot = %b, execUsable = %b, childModeUsable = %b\n",
                hotspot, execUsable, childModeUsable);

        // Run the unit test suite.
        for (int idx = 0; idx < TEST_NAMES.length; idx++) {
            System.out.printf("\n----- %s -----\n", TEST_NAMES[idx]);
            try {
                runTest(idx);
            } catch (Throwable throwable) {
                restoreStreamsIfCaptured();
                reportFail(String.format("%s threw an unexpected exception", TEST_NAMES[idx]), throwable.toString());
                throwable.printStackTrace(System.out);
            }
        }

        // Done. Report counts.
        System.out.println("\n----- main: That's all, folks -----");
        System.out.printf("main: %d assertion(s), %d failure(s), %d skipped\n",
                assertionCount, errorCount, skipCount);
                
        // Delete temp file.        
        tempFile.delete();
        
        // Make sure that no individual tests were skipped.
        assert(skipCount == 0);
        
        // The End with error count.
        Checkers.theEnd(errorCount);
    }

    private static void runTest(int idx) {
        try {
            switch (idx) {
                case 0: testIsPrimeSmallValues(); break;
                case 1: testIsPrimeAgainstSieve(); break;
                case 2: testIsPrimeKnownValues(); break;
                case 3: testFindLargestTwoPrimesFixed(); break;
                case 4: testFindLargestTwoPrimesAgainstSieve(); break;
                case 5: testCheckerBoolean(); break;
                case 6: testCheckerShortIntLong(); break;
                case 7: testCheckerLongArray(); break;
                case 8: testCheckerString(); break;
                case 9: testCheckerBigInteger(); break;
                case 10: testCheckerObject(); break;
                case 11: testCheckerClass(); break;
                case 12: testFailAndPass(); break;
                case 13: testWithinToleranceLongPercent(); break;
                case 14: testWithinToleranceLongAbsolute(); break;
                case 15: testWithinToleranceDouble(); break;
                case 16: testWithinToleranceFloat(); break;
                case 17: testWithinToleranceBigDecimal(); break;
                case 18: testReplaceEmptyString(); break;
                case 19: testIsWindows(); break;
                case 20: testIsJvmHotspot(); break;
                case 21: testExecCommand(); break;
                case 22: testSubProcess(); break;
                case 23: testGetProgramName(); break;
                default:
                    throw new AssertionError(String.format("runTest: no test for index %d", idx));
            }
        } catch (Throwable throwable) {
            System.out.printf("***** runTest: Oh oh, caught a throwable when idx=%d, test name: %s\n", idx, TEST_NAMES[idx]);
            reportFail(String.format("%s threw an unexpected exception", TEST_NAMES[idx]), throwable.toString());
            throwable.printStackTrace(System.out);
            ++errorCount;
        }
    }

    // Child mode: the test re-launches itself to exercise sub-process and System.exit() behavior.
    private static void runChildMode(String[] args) {
        System.out.printf("332(%d):", args.length);
        for (String curArg : args) {
            System.out.printf("  %s", curArg);
        }
        System.out.println();
        
        String mode = args[0];
        if (mode.equals("--exit")) {
            System.exit(Integer.parseInt(args[1]));
        } else if (mode.equals("--emit")) {
            System.out.println("child-out-line");
            System.err.println("child-err-line");
            System.exit(7);
        } else if (mode.equals("--theEnd")) {
            System.out.println("===== runChildMode theEnd will now diagnose 3 errors so don't panic!  (:");
            Checkers.theEnd(Integer.parseInt(args[1]));
        } else {
            System.err.printf("HelperUnitTests: unknown child mode: %s\n", mode);
            System.exit(2);
        }
    }

    private static void detectEnvironment() {
        try {
            hotspot = Checkers.isJvmHotspot();
        } catch (AssertionError err) {
            hotspot = false;
        }
        try {
            String[] command = { javaCommand(), "-version" };
            System.out.print("===== detectEnvironment: command: ");
            System.out.println(command);
            int exitCode = runCommand(command);
            System.out.printf("===== detectEnvironment: exitCode = %d\n", exitCode);
            execUsable = (exitCode == 0);
        } catch (Exception ex) {
            System.out.printf("===== detectEnvironment: Failed to execute command, err: %s\n", ex.getMessage());
            execUsable = false;
        }
        try {
            childModeUsable = (runCommand(childCommandLine("--exit", "0")) == 0);
        } catch (Exception ex) {
            childModeUsable = false;
        }
    }

    // ========================================================================
    // Assertion layer (deliberately independent of Checkers)
    // ========================================================================

    private static void reportOk(String label) {
        assertionCount++;
        System.out.printf("ok %s\n", label);
    }

    private static void reportFail(String label, String detail) {
        assertionCount++;
        errorCount++;
        System.out.printf("***** reportFail: %s, detail: %s\n", label, detail);
    }

    private static void reportSkip(String label, String reason) {
        skipCount++;
        System.out.printf("reportSkip: %s, reason: %s\n", label, reason);
    }

    private static void expectTrue(String label, boolean condition) {
        if (condition) {
            reportOk(label);
        } else {
            reportFail(label, "expectTrue: condition was false");
        }
    }

    private static void expectBool(String label, boolean expected, boolean observed) {
        if (expected == observed) {
            reportOk(label);
        } else {
            reportFail(label, String.format("expectBool: expected = %b, observed = %b", expected, observed));
        }
    }

    private static void expectInt(String label, int expected, int observed) {
        if (expected == observed) {
            reportOk(label);
        } else {
            reportFail(label, String.format("expectInt: expected = %d, observed = %d", expected, observed));
        }
    }

    private static void expectLong(String label, long expected, long observed) {
        if (expected == observed) {
            reportOk(label);
        } else {
            reportFail(label, String.format("expectLong: expected = %d, observed = %d", expected, observed));
        }
    }

    private static void expectString(String label, String expected, String observed) {
        boolean same = (expected == null) ? (observed == null) : expected.equals(observed);
        if (same) {
            reportOk(label);
        } else {
            reportFail(label, String.format("expectString: expected = [%s], observed = [%s]", expected, observed));
        }
    }

    private static void expectContains(String label, String haystack, String needle) {
        if (haystack != null && haystack.contains(needle)) {
            reportOk(label);
        } else {
            reportFail(label, String.format("expectContains: expected to find [%s] in [%s]", needle, haystack));
        }
    }

    private static void expectNotContains(String label, String haystack, String needle) {
        if (haystack == null || !haystack.contains(needle)) {
            reportOk(label);
        } else {
            reportFail(label, String.format("expectNotContains: did not expect to find [%s] in [%s]", needle, haystack));
        }
    }

    private static void expectLongArray(String label, long[] expected, long[] observed) {
        String expectedText = arrayToString(expected);
        String observedText = arrayToString(observed);
        if (expectedText.equals(observedText)) {
            reportOk(label);
        } else {
            reportFail(label, String.format("expectLongArray: expected = %s, observed = %s", expectedText, observedText));
        }
    }

    private static String arrayToString(long[] array) {
        if (array == null) {
            return "null";
        }
        StringBuilder text = new StringBuilder("[");
        for (int ix = 0; ix < array.length; ix++) {
            if (ix > 0) {
                text.append(", ");
            }
            text.append(array[ix]);
        }
        return text.append("]").toString();
    }

    // ========================================================================
    // Output capture
    // ========================================================================

    private static boolean capturing = false;

    private static void beginCapture() {
        savedOut = System.out;
        savedErr = System.err;
        outBuffer = new ByteArrayOutputStream();
        errBuffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outBuffer, true));
        System.setErr(new PrintStream(errBuffer, true));
        capturing = true;
    }

    private static void endCapture() {
        System.out.flush();
        System.err.flush();
        System.setOut(savedOut);
        System.setErr(savedErr);
        capturing = false;
        lastOut = chopNewline(outBuffer.toString());
        lastErr = chopNewline(errBuffer.toString());
    }
    
    private static String chopNewline(String arg) {
        String str = arg;
        if (str != null) {
            if (str.endsWith("\r\n") || str.endsWith("\n\r")) {
                str = str.substring(0, str.length() - 2);
            } else if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            } else if (str.endsWith("\r")) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    private static void restoreStreamsIfCaptured() {
        if (capturing) {
            endCapture();
        }
    }

    // ------------------------------------------------------------------------
    // Capturing wrappers around Checkers. Each returns the Checkers return code and
    // leaves the captured output in lastOut / lastErr.
    // ------------------------------------------------------------------------

    private static int cBool(String label, boolean expected, boolean observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int cShort(String label, short expected, short observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int cInt(String label, int expected, int observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int cLong(String label, long expected, long observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int cLongArray(String label, long[] expected, long[] observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int cString(String label, String expected, String observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int cBigInteger(String label, BigInteger expected, BigInteger observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int cObject(String label, Object expected, Object observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int cClass(String label, Class<?> expected, Class<?> observed) {
        beginCapture();
        try {
            return Checkers.checker(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int tolLong(String label, long expected, long observed) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int tolLongPercent(String label, long expected, long observed, double percent) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed, percent);
        } finally {
            endCapture();
        }
    }

    private static int tolLongAbsolute(String label, long expected, long observed, long tolerance) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed, tolerance);
        } finally {
            endCapture();
        }
    }

    private static int tolDouble(String label, double expected, double observed) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int tolDoubleTol(String label, double expected, double observed, double tolerance) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed, tolerance);
        } finally {
            endCapture();
        }
    }

    private static int tolFloat(String label, float expected, float observed) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed);
        } finally {
            endCapture();
        }
    }

    private static int tolFloatTol(String label, float expected, float observed, float tolerance) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed, tolerance);
        } finally {
            endCapture();
        }
    }

    private static int tolBigDecimalDouble(String label, BigDecimal expected, BigDecimal observed, double maxPercent) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed, maxPercent);
        } finally {
            endCapture();
        }
    }

    private static int tolBigDecimal(String label, BigDecimal expected, BigDecimal observed, BigDecimal maxPercent) {
        beginCapture();
        try {
            return Checkers.withinTolerance(label, expected, observed, maxPercent);
        } finally {
            endCapture();
        }
    }

    // ========================================================================
    // Sub-process helpers
    // ========================================================================

    // The java executable matching the running JVM when that can be found (and has no spaces,
    // because Checkers.execCommand splits on a single space), else "java" from the PATH.
    private static String javaCommand() {
        String javaHome = System.getProperty("java.home");
        if (javaHome != null && javaHome.indexOf(' ') < 0) {
            String sep = System.getProperty("file.separator");
            String candidate = String.format("%s%sbin%sjava", javaHome, sep, sep);
            //System.out.printf("===== javaCommand: %s\n", candidate);
            if (new File(candidate).canExecute()) {
                return candidate;
            }
            String winCandidate = String.format("%s.exe", candidate);
            //System.out.printf("===== javaCommand: %s\n", winCandidate);
            if (new File(winCandidate).canExecute()) {
                return winCandidate;
            }
        }
        return "java";
    }

    private static String[] childCommandLine(String... extraArgs) {
        String[] command = new String[4 + extraArgs.length];
        command[0] = javaCommand();
        command[1] = "-cp";
        command[2] = System.getProperty("java.class.path");
        command[3] = CLASS_NAME;
        System.arraycopy(extraArgs, 0, command, 4, extraArgs.length);
        return command;
    }

    // Run a command; stdout and stderr are merged into lastChildOutput. Returns the exit code.
    private static int runCommand(String[] command) throws IOException, InterruptedException {
        System.out.printf("===== runCommand(%d):", command.length);
        for (String curArg : command) {
            System.out.printf("  %s", curArg);
        }
        System.out.println();
        
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.environment().put(ALLOC_ENV_VAR, tempFileName);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder text = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            text.append(line).append('\n');
        }
        int exitCode = process.waitFor();
        lastChildOutput = text.toString();
        if (exitCode != 0)
            System.out.printf("***** runCommand exitCode=%d, lastChildOutput=%s\n", exitCode, lastChildOutput);
        return exitCode;
    }

    // Capturing wrappers for the jj class and Checkers.execCommand.
    private static int callSubProcess(jjSubProcessObject obj) {
        beginCapture();
        try {
            return jj._subProcess(obj);
        } finally {
            endCapture();
        }
    }

    private static int callExecCommand(String text) {
        beginCapture();
        try {
            return Checkers.execCommand(text);
        } finally {
            endCapture();
        }
    }

    private static jjSubProcessObject makeSubProcessObject(String... commandLine) {
        jjSubProcessObject obj = new jjSubProcessObject();
        obj.commandLine = commandLine;
        obj.classpath = new String[0];
        return obj;
    }

    // ========================================================================
    // Primes.isPrime
    // ========================================================================

    private static boolean[] buildPrimeFlags(int limit) {
        boolean[] flags = new boolean[limit + 1];
        for (int ix = 2; ix <= limit; ix++) {
            flags[ix] = true;
        }
        for (int ix = 2; (long) ix * ix <= limit; ix++) {
            if (flags[ix]) {
                for (int multiple = ix * ix; multiple <= limit; multiple += ix) {
                    flags[multiple] = false;
                }
            }
        }
        return flags;
    }

    private static void testIsPrimeSmallValues() {
        long[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 97, 101 };
        long[] nonPrimes = { Long.MIN_VALUE, -101, -7, -3, -2, -1, 0, 1, 4, 6, 8, 9, 10, 15, 21, 25, 27, 49, 91, 121, 169 };
        for (long value : primes) {
            expectBool(String.format("isPrime(%d) is true", value), true, Primes.isPrime(value));
        }
        for (long value : nonPrimes) {
            expectBool(String.format("isPrime(%d) is false", value), false, Primes.isPrime(value));
        }
    }

    private static void testIsPrimeAgainstSieve() {
        int limit = 10000;
        boolean[] flags = buildPrimeFlags(limit);
        int mismatches = 0;
        for (int ix = 0; ix <= limit; ix++) {
            if (Primes.isPrime(ix) != flags[ix]) {
                mismatches++;
                reportFail(String.format("isPrime(%d) vs sieve", ix),
                        String.format("expected = %b, observed = %b", flags[ix], !flags[ix]));
            }
        }
        expectInt(String.format("isPrime agrees with a sieve for 0..%d", limit), 0, mismatches);
    }

    private static void testIsPrimeKnownValues() {
        long[] primes = {
            7919L,           // 1000th prime
            104729L,         // 10000th prime
            1299709L,        // 100000th prime
            1000003L,
            1000000007L,
            1000000009L,
            2147483647L,     // 2^31 - 1 (Mersenne)
            4294967291L,     // largest prime below 2^32
            999999999989L    // largest prime below 10^12
        };
        long[] composites = {
            561L,            // Carmichael number
            1105L,           // Carmichael number
            1729L,           // Carmichael number (7 * 13 * 19)
            9409L,           // 97^2
            1000000L,        // even
            2147483649L,     // 2^31 + 1 = 3 * 715827883
            3215031751L,     // strong pseudoprime to bases 2, 3, 5, 7 = 151 * 751 * 28351
            4294967297L,     // 2^32 + 1 = 641 * 6700417 (Fermat F5)
            4295098369L,     // 65537^2: exercises the jj * jj <= arg boundary
            600851475143L,   // 71 * 839 * 1471 * 6857
            1099511627776L   // 2^40
        };
        for (long value : primes) {
            expectBool(String.format("isPrime(%d) is true", value), true, Primes.isPrime(value));
        }
        for (long value : composites) {
            expectBool(String.format("isPrime(%d) is false", value), false, Primes.isPrime(value));
        }
    }

    // ========================================================================
    // Primes.findLargestTwoPrimes
    // ========================================================================

    private static void testFindLargestTwoPrimesFixed() {
        long[][] cases = {
            // arg, first, second
            { Long.MIN_VALUE, -1, -1 },
            { -100, -1, -1 },
            { -1, -1, -1 },
            { 0, -1, -1 },
            { 1, -1, -1 },
            { 2, 2, -1 },       // only one prime exists at or below 2
            { 3, 3, 2 },
            { 4, 3, 2 },
            { 5, 5, 3 },
            { 6, 5, 3 },
            { 7, 7, 5 },
            { 10, 7, 5 },
            { 11, 11, 7 },
            { 12, 11, 7 },
            { 13, 13, 11 },
            { 97, 97, 89 },     // arg is itself prime
            { 98, 97, 89 },
            { 100, 97, 89 },
            { 101, 101, 97 },
            { 1000, 997, 991 },
            { 10000, 9973, 9967 },
            { 1000000, 999983, 999979 }
        };
        for (long[] row : cases) {
            long[] expected = { row[1], row[2] };
            expectLongArray(String.format("findLargestTwoPrimes(%d)", row[0]), expected, Primes.findLargestTwoPrimes(row[0]));
        }

        // The result always has exactly two elements, and each call returns a fresh array.
        long[] firstCall = Primes.findLargestTwoPrimes(50);
        long[] secondCall = Primes.findLargestTwoPrimes(50);
        expectInt("findLargestTwoPrimes(50) result length", 2, firstCall.length);
        expectTrue("findLargestTwoPrimes returns a new array on each call", firstCall != secondCall);
        expectLongArray("findLargestTwoPrimes(50) repeatable", firstCall, secondCall);
    }

    private static void testFindLargestTwoPrimesAgainstSieve() {
        int limit = 3000;
        boolean[] flags = buildPrimeFlags(limit);
        int mismatches = 0;
        for (int arg = 0; arg <= limit; arg++) {
            long first = -1;
            long second = -1;
            for (int ix = arg; ix >= 2; ix--) {
                if (flags[ix]) {
                    if (first == -1) {
                        first = ix;
                    } else {
                        second = ix;
                        break;
                    }
                }
            }
            long[] expected = { first, second };
            long[] observed = Primes.findLargestTwoPrimes(arg);
            if (!arrayToString(expected).equals(arrayToString(observed))) {
                mismatches++;
                reportFail(String.format("findLargestTwoPrimes(%d) vs sieve", arg),
                        String.format("expected = %s, observed = %s", arrayToString(expected), arrayToString(observed)));
            }
        }
        expectInt(String.format("findLargestTwoPrimes agrees with a sieve for 0..%d", limit), 0, mismatches);

        // Invariants for a few larger arguments.
        long[] args = { 123456L, 999999L, 1000001L, 5000000L };
        for (long arg : args) {
            long[] result = Primes.findLargestTwoPrimes(arg);
            String label = String.format("findLargestTwoPrimes(%d)", arg);
            expectTrue(String.format("%s: first <= arg", label), result[0] <= arg);
            expectTrue(String.format("%s: second < first", label), result[1] < result[0]);
            expectBool(String.format("%s: first is prime", label), true, Primes.isPrime(result[0]));
            expectBool(String.format("%s: second is prime", label), true, Primes.isPrime(result[1]));
            boolean gapIsPrimeFree = true;
            for (long between = result[0] + 1; between <= arg; between++) {
                if (Primes.isPrime(between)) {
                    gapIsPrimeFree = false;
                }
            }
            for (long between = result[1] + 1; between < result[0]; between++) {
                if (Primes.isPrime(between)) {
                    gapIsPrimeFree = false;
                }
            }
            expectBool(String.format("%s: no prime was skipped", label), true, gapIsPrimeFree);
        }
    }

    // ========================================================================
    // Checkers.checker - boolean, short, int, long
    // ========================================================================

    private static void testCheckerBoolean() {
        expectInt("testCheckerBoolean: true/true returns 0", 0, cBool("bool-tt", true, true));
        expectContains("testCheckerBoolean: true/true prints ok", lastOut, "ok bool-tt ::: expected = observed = true");
        expectInt("testCheckerBoolean: false/false returns 0", 0, cBool("bool-ff", false, false));
        expectContains("testCheckerBoolean: false/false prints ok", lastOut, "ok bool-ff ::: expected = observed = false");
        expectInt("checker(boolean) true/false returns 1", 1, cBool("bool-tf", true, false));
        expectContains("checker(boolean) true/false prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(bool-tf) ::: expected = true, observed = false");
        expectInt("checker(boolean) false/true returns 1", 1, cBool("bool-ft", false, true));
        expectContains("checker(boolean) false/true prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(bool-ft) ::: expected = false, observed = true");
        expectString("checker(boolean) writes nothing to stderr", "", lastErr);
    }

    private static void testCheckerShortIntLong() {
        // short
        expectInt("checker(short) equal returns 0", 0, cShort("short-eq", (short) 5, (short) 5));
        expectContains("checker(short) equal prints ok", lastOut, "ok short-eq ::: expected = observed = 5");
        expectInt("checker(short) MAX_VALUE equal", 0, cShort("short-max", Short.MAX_VALUE, Short.MAX_VALUE));
        expectInt("checker(short) MIN_VALUE equal", 0, cShort("short-min", Short.MIN_VALUE, Short.MIN_VALUE));
        expectInt("checker(short) negative equal", 0, cShort("short-neg", (short) -1, (short) -1));
        expectInt("checker(short) differ returns 1", 1, cShort("short-ne", (short) 1, (short) 2));
        expectContains("checker(short) differ prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(short-ne) ::: expected = 1, observed = 2");
        expectInt("checker(short) sign differs", 1, cShort("short-sign", (short) 1, (short) -1));
        expectInt("checker(short) MAX vs MIN", 1, cShort("short-extremes", Short.MAX_VALUE, Short.MIN_VALUE));

        // int
        expectInt("checker(int) equal returns 0", 0, cInt("int-eq", 123456, 123456));
        expectContains("checker(int) equal prints ok", lastOut, "ok int-eq ::: expected = observed = 123456");
        expectInt("checker(int) zero equal", 0, cInt("int-zero", 0, 0));
        expectInt("checker(int) MAX_VALUE equal", 0, cInt("int-max", Integer.MAX_VALUE, Integer.MAX_VALUE));
        expectInt("checker(int) MIN_VALUE equal", 0, cInt("int-min", Integer.MIN_VALUE, Integer.MIN_VALUE));
        expectInt("checker(int) differ returns 1", 1, cInt("int-ne", 10, 11));
        expectContains("checker(int) differ prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(int-ne) ::: expected = 10, observed = 11");
        expectInt("checker(int) MAX vs MIN", 1, cInt("int-extremes", Integer.MAX_VALUE, Integer.MIN_VALUE));
        expectInt("checker(int) negative vs positive", 1, cInt("int-sign", -7, 7));

        // long
        expectInt("checker(long) equal returns 0", 0, cLong("long-eq", 9876543210L, 9876543210L));
        expectContains("checker(long) equal prints ok", lastOut, "ok long-eq ::: expected = observed = 9876543210");
        expectInt("checker(long) MAX_VALUE equal", 0, cLong("long-max", Long.MAX_VALUE, Long.MAX_VALUE));
        expectInt("checker(long) MIN_VALUE equal", 0, cLong("long-min", Long.MIN_VALUE, Long.MIN_VALUE));
        expectInt("checker(long) differ returns 1", 1, cLong("long-ne", 1L, 2L));
        expectContains("checker(long) differ prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(long-ne) ::: expected = 1, observed = 2");
        expectInt("checker(long) values equal only in the low 32 bits differ", 1, cLong("long-high-bits", 0x100000000L, 0L));
        expectInt("checker(long) MAX vs MIN", 1, cLong("long-extremes", Long.MAX_VALUE, Long.MIN_VALUE));
        expectString("checker(long) writes nothing to stderr", "", lastErr);
    }

    // ========================================================================
    // Checkers.checker - long[]
    // ========================================================================

    private static void testCheckerLongArray() {
        long[] base = { 1L, 2L, 3L };
        expectInt("checker(long[]) equal returns 0", 0, cLongArray("arr-eq", base, new long[] { 1L, 2L, 3L }));
        expectContains("checker(long[]) equal prints ok", lastOut, "ok arr-eq ::: expected = observed");
        expectInt("checker(long[]) same instance returns 0", 0, cLongArray("arr-same", base, base));
        expectInt("checker(long[]) both empty returns 0", 0, cLongArray("arr-empty", new long[0], new long[0]));
        expectInt("checker(long[]) extreme values equal", 0,
                cLongArray("arr-extreme", new long[] { Long.MIN_VALUE, Long.MAX_VALUE }, new long[] { Long.MIN_VALUE, Long.MAX_VALUE }));

        expectInt("checker(long[]) observed longer returns 1", 1, cLongArray("arr-longer", new long[] { 1L, 2L }, new long[] { 1L, 2L, 3L }));
        expectContains("checker(long[]) observed longer reports lengths", lastOut,
                "*** DISCREPANCY detected in checker(arr-longer) ::: expected length = 2, observed length = 3");
        expectInt("checker(long[]) observed shorter returns 1", 1, cLongArray("arr-shorter", new long[] { 1L, 2L, 3L }, new long[] { 1L }));
        expectContains("checker(long[]) observed shorter reports lengths", lastOut, "expected length = 3, observed length = 1");
        expectInt("checker(long[]) empty vs non-empty returns 1", 1, cLongArray("arr-empty-ne", new long[0], new long[] { 0L }));

        expectInt("checker(long[]) first element differs returns 1", 1, cLongArray("arr-first", base, new long[] { 9L, 2L, 3L }));
        expectContains("checker(long[]) first element differs reports index 0", lastOut,
                "*** DISCREPANCY at index 0 detected in checker(arr-first) ::: expected = 1, observed = 9");
        expectInt("checker(long[]) last element differs returns 1", 1, cLongArray("arr-last", base, new long[] { 1L, 2L, 9L }));
        expectContains("checker(long[]) last element differs reports index 2", lastOut,
                "*** DISCREPANCY at index 2 detected in checker(arr-last) ::: expected = 3, observed = 9");

        // Several differences: still 1, and only the first one is reported.
        expectInt("checker(long[]) multiple differences returns 1", 1, cLongArray("arr-multi", base, new long[] { 1L, 8L, 9L }));
        expectContains("checker(long[]) multiple differences reports index 1", lastOut, "at index 1");
        expectNotContains("checker(long[]) multiple differences stops at the first", lastOut, "at index 2");
        expectInt("checker(long[]) high bits differ returns 1", 1,
                cLongArray("arr-high-bits", new long[] { 0L }, new long[] { 0x100000000L }));
    }

    // ========================================================================
    // Checkers.checker - String
    // ========================================================================

    private static void testCheckerString() {
        expectInt("checker(String) equal returns 0", 0, cString("str-eq", "abc", "abc"));
        expectContains("checker(String) equal prints ok", lastOut, "ok str-eq ::: expected = observed = abc");
        expectInt("checker(String) both empty returns 0", 0, cString("str-empty", "", ""));
        expectInt("checker(String) equal with whitespace and newline returns 0", 0, cString("str-ws", " a b\tc\n", " a b\tc\n"));
        expectInt("checker(String) equal non-ASCII returns 0", 0, cString("str-unicode", "caf\u00e9", "caf\u00e9"));

        expectInt("checker(String) differ returns 1", 1, cString("str-ne", "abc", "abd"));
        expectContains("checker(String) differ prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(str-ne) ::: expected = abc, observed = abd");
        expectInt("checker(String) comparison is case sensitive", 1, cString("str-case", "ABC", "abc"));
        expectInt("checker(String) trailing blank matters", 1, cString("str-trail", "abc", "abc "));
        expectInt("checker(String) empty vs non-empty returns 1", 1, cString("str-empty-ne", "", "x"));

        // The observed value "ignore" (any case) always matches.
        expectInt("checker(String) observed ignore returns 0", 0, cString("str-ign", "whatever", "ignore"));
        expectContains("checker(String) observed ignore prints ok", lastOut, "ok str-ign ::: observed = ignore");
        expectInt("checker(String) observed IGNORE returns 0", 0, cString("str-ign-upper", "whatever", "IGNORE"));
        expectInt("checker(String) observed Ignore returns 0", 0, cString("str-ign-mixed", "whatever", "Ignore"));
        expectInt("checker(String) expected empty, observed ignore returns 0", 0, cString("str-ign-empty", "", "ignore"));

        // But "ignore" is not special as the expected value, nor with extra characters.
        expectInt("checker(String) expected ignore, observed other returns 1", 1, cString("str-ign-expected", "ignore", "other"));
        expectInt("checker(String) observed ' ignore' returns 1", 1, cString("str-ign-blank", "x", " ignore"));
        expectInt("checker(String) observed 'ignored' returns 1", 1, cString("str-ign-suffix", "x", "ignored"));
        expectInt("checker(String) expected ignore, observed ignore returns 0", 0, cString("str-ign-both", "ignore", "ignore"));
    }

    // ========================================================================
    // Checkers.checker - BigInteger
    // ========================================================================

    private static void testCheckerBigInteger() {
        BigInteger huge = BigInteger.ONE.shiftLeft(100);
        expectInt("checker(BigInteger) equal returns 0", 0, cBigInteger("bi-eq", new BigInteger("12345"), new BigInteger("12345")));
        expectContains("checker(BigInteger) equal prints ok", lastOut, "ok bi-eq ::: expected = observed = 12345");
        expectInt("checker(BigInteger) zero equal", 0, cBigInteger("bi-zero", BigInteger.ZERO, BigInteger.ZERO));
        expectInt("checker(BigInteger) negative equal", 0, cBigInteger("bi-neg", new BigInteger("-99"), new BigInteger("-99")));
        expectInt("checker(BigInteger) beyond long range equal", 0, cBigInteger("bi-huge", huge, BigInteger.ONE.shiftLeft(100)));
        expectInt("checker(BigInteger) differ returns 1", 1, cBigInteger("bi-ne", BigInteger.ONE, BigInteger.TWO));
        expectContains("checker(BigInteger) differ prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(bi-ne) ::: expected = 1, observed = 2");
        expectInt("checker(BigInteger) differ only in the lowest bit returns 1", 1, cBigInteger("bi-low-bit", huge, huge.add(BigInteger.ONE)));
        expectInt("checker(BigInteger) sign differs returns 1", 1, cBigInteger("bi-sign", BigInteger.TEN, BigInteger.TEN.negate()));
    }

    // ========================================================================
    // Checkers.checker - Object
    // ========================================================================

    private static void testCheckerObject() {
        // null handling
        expectInt("checker(Object) null/null returns 0", 0, cObject("obj-nn", null, null));
        expectContains("checker(Object) null/null prints ok", lastOut, "ok obj-nn ::: expected = observed = null");
        expectInt("checker(Object) expected null returns 1", 1, cObject("obj-n-x", null, "abc"));
        expectContains("checker(Object) expected null prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(obj-n-x) ::: expected = null, observed = abc");
        expectInt("checker(Object) observed null returns 1", 1, cObject("obj-x-n", "abc", null));
        expectContains("checker(Object) observed null prints discrepancy", lastOut,
                "*** DISCREPANCY detected in checker(obj-x-n) ::: expected = abc, observed = null");

        // integral numbers of any width compare by value
        expectInt("checker(Object) Integer/Integer equal", 0, cObject("obj-ii", Integer.valueOf(5), Integer.valueOf(5)));
        expectContains("checker(Object) Integer/Integer prints ok", lastOut, "ok obj-ii ::: expected = observed = 5");
        expectInt("checker(Object) Integer/Long equal", 0, cObject("obj-il", Integer.valueOf(5), Long.valueOf(5L)));
        expectInt("checker(Object) Short/Byte equal", 0, cObject("obj-sb", Short.valueOf((short) 7), Byte.valueOf((byte) 7)));
        expectInt("checker(Object) Long/Long equal at the extremes", 0, cObject("obj-ll", Long.valueOf(Long.MAX_VALUE), Long.valueOf(Long.MAX_VALUE)));
        expectInt("checker(Object) Integer/Integer differ", 1, cObject("obj-ii-ne", Integer.valueOf(5), Integer.valueOf(6)));
        expectContains("checker(Object) integral discrepancy names the types", lastOut,
                "*** DISCREPANCY detected in checker(obj-ii-ne) ::: expected (long) = 5, observed (long) = 6");
        expectInt("checker(Object) Long values equal only in the low 32 bits differ", 1,
                cObject("obj-ll-high", Long.valueOf(0x100000000L), Long.valueOf(0L)));

        // floating point
        expectInt("checker(Object) Double/Double equal", 0, cObject("obj-dd", Double.valueOf(1.5), Double.valueOf(1.5)));
        expectContains("checker(Object) Double/Double prints ok", lastOut, "ok obj-dd ::: expected = observed = ");
        expectInt("checker(Object) Float/Double equal", 0, cObject("obj-fd", Float.valueOf(1.5f), Double.valueOf(1.5)));
        expectInt("checker(Object) Integer/Double equal", 0, cObject("obj-id", Integer.valueOf(5), Double.valueOf(5.0)));
        expectInt("checker(Object) Double/Double differ", 1, cObject("obj-dd-ne", Double.valueOf(1.5), Double.valueOf(2.5)));
        expectContains("checker(Object) double discrepancy names the types", lastOut, "expected (double) = ");
        expectContains("checker(Object) double discrepancy names the observed type", lastOut, ", observed (double) = ");
        expectInt("checker(Object) Integer/Double differ", 1, cObject("obj-id-ne", Integer.valueOf(5), Double.valueOf(5.5)));
        expectContains("checker(Object) mixed discrepancy names the types", lastOut, "expected (long) = ");
        expectContains("checker(Object) mixed discrepancy names the observed type", lastOut, ", observed (double) = ");
        expectInt("checker(Object) NaN/NaN equal", 0, cObject("obj-nan", Double.valueOf(Double.NaN), Double.valueOf(Double.NaN)));
        expectInt("checker(Object) +Infinity/+Infinity equal", 0,
                cObject("obj-inf", Double.valueOf(Double.POSITIVE_INFINITY), Double.valueOf(Double.POSITIVE_INFINITY)));
        expectInt("checker(Object) +Infinity/-Infinity differ", 1,
                cObject("obj-inf-ne", Double.valueOf(Double.POSITIVE_INFINITY), Double.valueOf(Double.NEGATIVE_INFINITY)));
        expectInt("checker(Object) 0.0 and -0.0 are distinguished", 1, cObject("obj-zero", Double.valueOf(0.0), Double.valueOf(-0.0)));

        // booleans (equal case only
        expectInt("checker(Object) Boolean true/true equal", 0, cObject("obj-bool-t", Boolean.TRUE, Boolean.TRUE));
        expectContains("checker(Object) Boolean true/true prints ok", lastOut, "ok obj-bool-t ::: expected = observed = true");
        expectInt("checker(Object) Boolean false/false equal", 0, cObject("obj-bool-f", Boolean.FALSE, Boolean.FALSE));
        expectContains("checker(Object) Boolean false/false prints ok", lastOut, "ok obj-bool-f ::: expected = observed = false");

        // mixed kinds never match
        expectInt("checker(Object) Number/Boolean differ", 1, cObject("obj-nb", Integer.valueOf(1), Boolean.TRUE));
        expectContains("checker(Object) Number/Boolean discrepancy names the types", lastOut,
                "*** DISCREPANCY detected in checker(obj-nb) ::: expected (long) = 1, observed (boolean) = true");
        expectInt("checker(Object) Number/String differ", 1, cObject("obj-ns", Integer.valueOf(1), "1"));
        expectContains("checker(Object) Number/String discrepancy names the types", lastOut,
                "expected (long) = 1, observed (Object) = 1");
        expectInt("checker(Object) String/Number differ", 1, cObject("obj-sn", "abc", Integer.valueOf(5)));
        expectContains("checker(Object) String/Number discrepancy names the types", lastOut,
                "expected (Object) = abc, observed (long) = 5");
    }

    // ========================================================================
    // Checkers.checker - Class
    // ========================================================================

    private static void testCheckerClass() {
        return;
        /*
        expectInt("checker(Class) equal returns 0", 0, cClass("cls-eq", String.class, String.class));
        expectContains("checker(Class) equal prints ok", lastOut, "ok [cls-eq]");
        expectString("checker(Class) equal writes nothing to stderr", "", lastErr);

        expectInt("checker(Class) differ returns 1", 1, cClass("cls-ne", String.class, Integer.class));
        expectContains("checker(Class) differ reports on stderr", lastErr,
                "FAIL [cls-ne] Expected: java.lang.String | Actual: java.lang.Integer");
        expectNotContains("checker(Class) differ prints nothing ok on stdout", lastOut, "ok [");

        expectInt("checker(Class) primitive vs wrapper differ", 1, cClass("cls-prim", int.class, Integer.class));
        expectContains("checker(Class) primitive vs wrapper reports names", lastErr, "Expected: int | Actual: java.lang.Integer");
        expectInt("checker(Class) super type vs sub type differ", 1, cClass("cls-super", Number.class, Integer.class));
        expectInt("checker(Class) array classes equal", 0, cClass("cls-array", long[].class, long[].class));
        expectInt("checker(Class) array vs element class differ", 1, cClass("cls-array-ne", long[].class, long.class));
        */
    }

    // ========================================================================
    // Checkers.fail and Checkers.pass
    // ========================================================================

    private static void testFailAndPass() {
        beginCapture();
        int failResult;
        try {
            failResult = Checkers.fail("unconditional-fail");
        } finally {
            endCapture();
        }
        expectInt("fail returns 1", 1, failResult);
        expectContains("fail prints a discrepancy", lastOut, "*** DISCREPANCY unconditional-fail");

        beginCapture();
        try {
            Checkers.pass("unconditional-pass");
        } finally {
            endCapture();
        }
        expectContains("pass prints ok", lastOut, "ok unconditional-pass");
        expectNotContains("pass does not print a discrepancy", lastOut, "DISCREPANCY");
    }

    // ========================================================================
    // Checkers.withinTolerance - long
    // ========================================================================

    private static void testWithinToleranceLongPercent() {
        // Default percent tolerance (MAX_PERCENT, 0.0001 percent).
        expectInt("withinTolerance(long) equal", 0, tolLong("lp-eq", 1000000L, 1000000L));
        expectInt("withinTolerance(long) within default percent", 0, tolLong("lp-near", 100000000L, 100000050L));
        expectContains("withinTolerance(long) within default percent prints ok", lastOut,
                "lp-near, expected = 100000000, observed = 100000050");
        expectInt("withinTolerance(long) outside default percent", 1, tolLong("lp-far", 1000000L, 1000010L));
        expectContains("withinTolerance(long) outside default percent prints discrepancy", lastOut,
                "lp-far ::: expected = 1000000, observed = 1000010, diffPct = ");
        expectInt("withinTolerance(long) expected zero, observed zero", 0, tolLong("lp-zero-zero", 0L, 0L));
        expectContains("withinTolerance(long) expected zero, observed zero prints ok", lastOut, "lp-zero-zero, expected = observed = 0");
        expectInt("withinTolerance(long) expected zero, observed non-zero", 1, tolLong("lp-zero-nz", 0L, 5L));
        expectContains("withinTolerance(long) expected zero, observed non-zero prints discrepancy", lastOut,
                "lp-zero-nz ::: expected = 0, observed = 5");

        // Explicit percent tolerance.
        expectInt("withinTolerance(long, 10%) observed 10% high", 0, tolLongPercent("lp10-high", 1000L, 1100L, 10.0));
        expectInt("withinTolerance(long, 10%) observed just over 10% high", 1, tolLongPercent("lp10-over", 1000L, 1101L, 10.0));
        expectInt("withinTolerance(long, 10%) observed 10% low", 0, tolLongPercent("lp10-low", 1000L, 900L, 10.0));
        expectInt("withinTolerance(long, 10%) observed just over 10% low", 1, tolLongPercent("lp10-under", 1000L, 899L, 10.0));
        expectInt("withinTolerance(long, 10%) negative expected", 0, tolLongPercent("lp10-neg", -1000L, -1100L, 10.0));
        expectInt("withinTolerance(long, 10%) negative expected, too far", 1, tolLongPercent("lp10-neg-far", -1000L, -1101L, 10.0));
        expectInt("withinTolerance(long, 0%) equal", 0, tolLongPercent("lp0-eq", 12345L, 12345L, 0.0));
        expectInt("withinTolerance(long, 0%) differ by one", 1, tolLongPercent("lp0-ne", 12345L, 12346L, 0.0));
        expectInt("withinTolerance(long, 100%) observed double", 0, tolLongPercent("lp100", 10L, 20L, 100.0));
        expectInt("withinTolerance(long, 100%) observed more than double", 1, tolLongPercent("lp100-over", 10L, 21L, 100.0));
        expectInt("withinTolerance(long, pct) expected zero, observed zero", 0, tolLongPercent("lpx-zero-zero", 0L, 0L, 50.0));
        expectInt("withinTolerance(long, pct) expected zero, observed non-zero", 1, tolLongPercent("lpx-zero-nz", 0L, 1L, 50.0));
    }

    private static void testWithinToleranceLongAbsolute() {
        // withinTolerance(String, long, long, long) is an absolute tolerance.
        expectInt("withinTolerance(long, abs) within tolerance above", 0, tolLongAbsolute("la-above", 100L, 105L, 5L));
        expectContains("withinTolerance(long, abs) prints ok", lastOut, "ok la-above within tolerance");
        expectInt("withinTolerance(long, abs) within tolerance below", 0, tolLongAbsolute("la-below", 100L, 95L, 5L));
        expectInt("withinTolerance(long, abs) just outside tolerance", 1, tolLongAbsolute("la-out", 100L, 106L, 5L));
        expectContains("withinTolerance(long, abs) outside prints discrepancy", lastOut,
                "*** DISCREPANCY detected in withinTolerance(la-out) ::: expected = 100, observed = 106, diff = 6, diffPct = ");
        expectInt("withinTolerance(long, abs) zero tolerance equal", 0, tolLongAbsolute("la-zero-eq", 42L, 42L, 0L));
        expectInt("withinTolerance(long, abs) zero tolerance differ", 1, tolLongAbsolute("la-zero-ne", 42L, 43L, 0L));
        expectInt("withinTolerance(long, abs) expected zero within tolerance", 0, tolLongAbsolute("la-exp-zero", 0L, 3L, 5L));
        expectInt("withinTolerance(long, abs) expected zero outside tolerance", 1, tolLongAbsolute("la-exp-zero-out", 0L, 10L, 5L));
        expectInt("withinTolerance(long, abs) negative values", 0, tolLongAbsolute("la-neg", -100L, -104L, 5L));
        expectInt("withinTolerance(long, abs) across zero", 0, tolLongAbsolute("la-cross", -2L, 3L, 5L));
        expectInt("withinTolerance(long, abs) across zero, too far", 1, tolLongAbsolute("la-cross-out", -3L, 3L, 5L));
        expectInt("withinTolerance(long, abs) large values", 0,
                tolLongAbsolute("la-large", Long.MAX_VALUE - 1L, Long.MAX_VALUE, 1L));
    }

    // ========================================================================
    // Checkers.withinTolerance - double
    // ========================================================================

    private static void testWithinToleranceDouble() {
        // Default tolerance: relative 0.0001 (a fraction, not a percent).
        expectInt("withinTolerance(double) equal", 0, tolDouble("d-eq", 1.0, 1.0));
        expectInt("withinTolerance(double) within relative default", 0, tolDouble("d-near", 1.0, 1.00005));
        expectContains("withinTolerance(double) within relative default prints ok", lastOut, "ok d-near ::: within relative tolerance ::: ");
        expectInt("withinTolerance(double) outside relative default", 1, tolDouble("d-far", 1.0, 1.001));
        expectContains("withinTolerance(double) outside prints discrepancy", lastOut,
                "*** DISCREPANCY detected in withinTolerance(d-far) ::: expected = ");
        expectInt("withinTolerance(double) large values scale the tolerance", 0, tolDouble("d-big-near", 1000000.0, 1000050.0));
        expectInt("withinTolerance(double) large values outside the scaled tolerance", 1, tolDouble("d-big-far", 1000000.0, 1000200.0));
        expectInt("withinTolerance(double) negative values", 0, tolDouble("d-neg", -1.0, -1.00005));
        expectInt("withinTolerance(double) opposite signs", 1, tolDouble("d-sign", 1.0, -1.0));

        // Near zero the tolerance is absolute.
        expectInt("withinTolerance(double) zero vs tiny", 0, tolDouble("d-zero-tiny", 0.0, 0.00005));
        expectContains("withinTolerance(double) near zero prints absolute", lastOut, "ok d-zero-tiny ::: within absolute tolerance ::: ");
        expectInt("withinTolerance(double) zero vs not tiny", 1, tolDouble("d-zero-big", 0.0, 0.001));
        expectInt("withinTolerance(double) tiny expected, tiny observed", 0, tolDouble("d-tiny-tiny", 0.00001, 0.00005));
        expectInt("withinTolerance(double) tiny expected, not tiny observed", 1, tolDouble("d-tiny-far", 0.00001, 0.001));
        expectInt("withinTolerance(double) zero vs negative zero", 0, tolDouble("d-negzero", 0.0, -0.0));

        // Explicit relative tolerance.
        expectInt("withinTolerance(double, 0.02) within", 0, tolDoubleTol("dt-in", 100.0, 101.0, 0.02));
        expectInt("withinTolerance(double, 0.02) outside", 1, tolDoubleTol("dt-out", 100.0, 103.0, 0.02));
        expectInt("withinTolerance(double, 0.5) exactly on the boundary", 0, tolDoubleTol("dt-edge", 1.0, 1.5, 0.5));
        expectInt("withinTolerance(double, 1.0) large tolerance", 0, tolDoubleTol("dt-large", 10.0, 15.0, 1.0));
        expectInt("withinTolerance(double, 0.0) equal", 0, tolDoubleTol("dt-zero-eq", 5.0, 5.0, 0.0));
        expectInt("withinTolerance(double, 0.0) expected zero, equal", 0, tolDoubleTol("dt-zero-zero", 0.0, 0.0, 0.0));
        expectInt("withinTolerance(double, 0.0) differ", 1, tolDoubleTol("dt-zero-ne", 5.0, 5.0000001, 0.0));
        expectInt("withinTolerance(double) MAX_VALUE equal", 0, tolDoubleTol("dt-max", Double.MAX_VALUE, Double.MAX_VALUE, 0.001));
        expectInt("withinTolerance(double) MIN_VALUE vs zero", 0, tolDoubleTol("dt-min", 0.0, Double.MIN_VALUE, 0.001));

        // Negative tolerance is an error.
        expectInt("withinTolerance(double) negative tolerance returns 1", 1, tolDoubleTol("dt-neg", 1.0, 1.0, -1.0e-9));
        expectContains("withinTolerance(double) negative tolerance reports an error", lastOut,
                "*** ERROR, test label dt-neg passed a negative tolerance");

        // NaN and infinity.
        expectInt("withinTolerance(double) NaN/NaN", 0, tolDoubleTol("dt-nan", Double.NaN, Double.NaN, 0.001));
        expectContains("withinTolerance(double) NaN/NaN prints ok", lastOut, "ok dt-nan ::: both values are NaN");
        expectInt("withinTolerance(double) NaN expected, number observed", 1, tolDoubleTol("dt-nan-exp", Double.NaN, 1.0, 0.001));
        expectContains("withinTolerance(double) NaN expected prints discrepancy", lastOut,
                "*** DISCREPANCY detected in withinTolerance(dt-nan-exp) ::: expected = NaN, observed = 1");
        expectInt("withinTolerance(double) number expected, NaN observed", 1, tolDoubleTol("dt-nan-obs", 1.0, Double.NaN, 0.001));
        expectInt("withinTolerance(double) +Infinity/+Infinity", 0,
                tolDoubleTol("dt-inf", Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.001));
        expectInt("withinTolerance(double) -Infinity/-Infinity", 0,
                tolDoubleTol("dt-ninf", Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.001));
        expectInt("withinTolerance(double) +Infinity/-Infinity", 1,
                tolDoubleTol("dt-inf-ne", Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.001));
        expectInt("withinTolerance(double) Infinity expected, finite observed", 1,
                tolDoubleTol("dt-inf-fin", Double.POSITIVE_INFINITY, Double.MAX_VALUE, 0.001));
        expectInt("withinTolerance(double) finite expected, Infinity observed", 1,
                tolDoubleTol("dt-fin-inf", Double.MAX_VALUE, Double.POSITIVE_INFINITY, 0.001));
    }

    // ========================================================================
    // Checkers.withinTolerance - float
    // ========================================================================

    private static void testWithinToleranceFloat() {
        // Explicit float tolerance is an absolute difference.
        expectInt("withinTolerance(float, 0.1) within", 0, tolFloatTol("ft-in", 1.0f, 1.05f, 0.1f));
        expectContains("withinTolerance(float) within prints ok", lastOut, "ok ft-in within tolerance");
        expectInt("withinTolerance(float, 0.1) outside", 1, tolFloatTol("ft-out", 1.0f, 1.2f, 0.1f));
        expectContains("withinTolerance(float) outside prints discrepancy", lastOut,
                "*** DISCREPANCY detected in withinTolerance(ft-out) ::: expected = ");
        expectInt("withinTolerance(float, 0.1) below expected", 0, tolFloatTol("ft-below", 1.0f, 0.95f, 0.1f));
        expectInt("withinTolerance(float, 0.1) large values stay absolute (within)", 0, tolFloatTol("ft-big-in", 1000.0f, 1000.05f, 0.1f));
        expectInt("withinTolerance(float, 0.1) large values stay absolute (outside)", 1, tolFloatTol("ft-big-out", 1000.0f, 1000.5f, 0.1f));
        expectInt("withinTolerance(float, 0.0) equal", 0, tolFloatTol("ft-zero-eq", 2.5f, 2.5f, 0.0f));
        expectInt("withinTolerance(float, 0.0) differ", 1, tolFloatTol("ft-zero-ne", 2.5f, 2.6f, 0.0f));
        expectInt("withinTolerance(float) negative values", 0, tolFloatTol("ft-neg", -1.0f, -1.05f, 0.1f));
        expectInt("withinTolerance(float) negative tolerance returns 1", 1, tolFloatTol("ft-negtol", 1.0f, 1.0f, -0.1f));
        expectContains("withinTolerance(float) negative tolerance reports an error", lastOut,
                "*** ERROR, test label ft-negtol passed a negative error tolerance");

        // Default float tolerance (about 0.0001).
        expectInt("withinTolerance(float) default equal", 0, tolFloat("f-eq", 1.0f, 1.0f));
        expectInt("withinTolerance(float) default within", 0, tolFloat("f-near", 1.0f, 1.00005f));
        expectInt("withinTolerance(float) default outside", 1, tolFloat("f-far", 1.0f, 1.001f));
        expectInt("withinTolerance(float) default zero vs tiny", 0, tolFloat("f-zero-tiny", 0.0f, 0.00005f));
        expectInt("withinTolerance(float) default zero vs not tiny", 1, tolFloat("f-zero-far", 0.0f, 0.001f));
    }

    // ========================================================================
    // Checkers.withinTolerance - BigDecimal
    // ========================================================================

    private static void testWithinToleranceBigDecimal() {
        // Relative tolerance as a double (0.01 means one percent as a fraction).
        expectInt("withinTolerance(BigDecimal, 0.01) within", 0,
                tolBigDecimalDouble("bd-in", new BigDecimal("100"), new BigDecimal("100.5"), 0.01));
        expectContains("withinTolerance(BigDecimal) within prints ok", lastOut,
                "ok withinTolerance(0.01) bd-in ::: expected = 100, observed = 100.5, relDiff = 0.005");
        expectInt("withinTolerance(BigDecimal, 0.01) outside", 1,
                tolBigDecimalDouble("bd-out", new BigDecimal("100"), new BigDecimal("102"), 0.01));
        expectContains("withinTolerance(BigDecimal) outside prints discrepancy", lastOut,
                "*** DISCREPANCY withinTolerance(0.01) bd-out ::: expected = 100, observed = 102, relDiff = 0.02");
        expectInt("withinTolerance(BigDecimal, 0.01) exactly on the boundary", 0,
                tolBigDecimalDouble("bd-edge", new BigDecimal("100"), new BigDecimal("101"), 0.01));
        expectInt("withinTolerance(BigDecimal, 0.01) observed below expected", 0,
                tolBigDecimalDouble("bd-below", new BigDecimal("100"), new BigDecimal("99.5"), 0.01));
        expectInt("withinTolerance(BigDecimal, 0.01) negative expected", 0,
                tolBigDecimalDouble("bd-neg", new BigDecimal("-100"), new BigDecimal("-100.5"), 0.01));
        expectInt("withinTolerance(BigDecimal, 0.01) opposite signs", 1,
                tolBigDecimalDouble("bd-sign", new BigDecimal("100"), new BigDecimal("-100"), 0.01));

        // Relative tolerance as a BigDecimal.
        BigDecimal onePercent = new BigDecimal("0.01");
        expectInt("withinTolerance(BigDecimal, BigDecimal) equal", 0,
                tolBigDecimal("bb-eq", new BigDecimal("3.14159"), new BigDecimal("3.14159"), onePercent));
        expectInt("withinTolerance(BigDecimal, BigDecimal) equal values, different scales", 0,
                tolBigDecimal("bb-scale", new BigDecimal("2.0"), new BigDecimal("2.00"), onePercent));
        expectInt("withinTolerance(BigDecimal, BigDecimal) on the boundary", 0,
                tolBigDecimal("bb-edge", new BigDecimal("200"), new BigDecimal("202"), onePercent));
        expectInt("withinTolerance(BigDecimal, BigDecimal) just outside", 1,
                tolBigDecimal("bb-out", new BigDecimal("200"), new BigDecimal("202.01"), onePercent));
        expectInt("withinTolerance(BigDecimal, BigDecimal) zero tolerance, equal", 0,
                tolBigDecimal("bb-zero-eq", new BigDecimal("5"), new BigDecimal("5"), BigDecimal.ZERO));
        expectInt("withinTolerance(BigDecimal, BigDecimal) zero tolerance, differ", 1,
                tolBigDecimal("bb-zero-ne", new BigDecimal("1"), new BigDecimal("1.0000001"), BigDecimal.ZERO));
        expectInt("withinTolerance(BigDecimal, BigDecimal) beyond double precision", 0,
                tolBigDecimal("bb-precise", new BigDecimal("12345678901234567890.123456789"),
                        new BigDecimal("12345678901234567891.123456789"), new BigDecimal("0.000000000000001")));

        // Small expected values use an absolute comparison against maxPercent.
        BigDecimal tenThousandth = new BigDecimal("0.0001");
        expectInt("withinTolerance(BigDecimal) small expected, within absolute", 0,
                tolBigDecimal("bs-in", new BigDecimal("0.00001"), new BigDecimal("0.00005"), tenThousandth));
        expectContains("withinTolerance(BigDecimal) small expected prints ok", lastOut,
                "ok withinTolerance(0.0001) bs-in ::: ok expected = 0.00001, observed = 0.00005, absDiff = 0.00004");
        expectInt("withinTolerance(BigDecimal) small expected, outside absolute", 1,
                tolBigDecimal("bs-out", new BigDecimal("0.00001"), new BigDecimal("0.0002"), tenThousandth));
        expectContains("withinTolerance(BigDecimal) small expected prints discrepancy", lastOut,
                "*** DISCREPANCY withinTolerance(0.0001) ::: bs-out expected = 0.00001, observed = 0.0002, absDiff = 0.00019");
        expectInt("withinTolerance(BigDecimal) zero expected, zero observed", 0,
                tolBigDecimal("bs-zero-zero", BigDecimal.ZERO, BigDecimal.ZERO, tenThousandth));
        expectInt("withinTolerance(BigDecimal) zero expected, observed on the absolute boundary", 0,
                tolBigDecimal("bs-zero-edge", BigDecimal.ZERO, new BigDecimal("0.0001"), tenThousandth));
        expectInt("withinTolerance(BigDecimal) zero expected, observed beyond the boundary", 1,
                tolBigDecimal("bs-zero-out", BigDecimal.ZERO, new BigDecimal("0.00011"), tenThousandth));
    }

    // ========================================================================
    // Checkers.replaceEmptyString
    // ========================================================================

    private static void testReplaceEmptyString() {
        String[][] cases = {
            // input, expected
            { null, "null" },
            { "", "null" },
            { " ", "null" },
            { "     ", "null" },
            { "\t", "null" },
            { "\n", "null" },
            { "\r\n", "null" },
            { " \t\r\n ", "null" },
            { "\0", "null" },
            { "abc", "abc" },
            { "  abc", "abc" },
            { "abc  ", "abc" },
            { "  abc  ", "abc" },
            { "abc\n", "abc" },
            { "\n\nabc\n\n", "abc" },
            { "a b", "a b" },
            { "  a   b  ", "a   b" },
            { "line1\nline2\n", "line1\nline2" },
            { "null", "null" },
            { "0", "0" },
            { "caf\u00e9 ", "caf\u00e9" }
        };
        for (int idx = 0; idx < cases.length; idx++) {
            expectString(String.format("replaceEmptyString case %d", idx), cases[idx][1], Checkers.replaceEmptyString(cases[idx][0]));
        }
    }

    // ========================================================================
    // Checkers.isWindows and Checkers.isJvmHotspot
    // ========================================================================

    private static void testIsWindows() {
        String original = System.getProperty("os.name");
        String[] windowsNames = { "Windows 10", "Windows 11", "Windows Server 2019", "windows", "My Windows Variant" };
        String[] otherNames = { "Linux", "Mac OS X", "Darwin", "SunOS", "FreeBSD", "AIX" };
        try {
            for (String name : windowsNames) {
                System.setProperty("os.name", name);
                expectBool(String.format("isWindows with os.name [%s]", name), true, Checkers.isWindows());
            }
            for (String name : otherNames) {
                System.setProperty("os.name", name);
                expectBool(String.format("isWindows with os.name [%s]", name), false, Checkers.isWindows());
            }
        } finally {
            System.setProperty("os.name", original);
        }
        expectString("os.name is restored after the test", original, System.getProperty("os.name"));
        expectBool("isWindows with the real os.name", original.toLowerCase().contains("windows"), Checkers.isWindows());
    }

    private static void testIsJvmHotspot() {
        boolean observed;
        try {
            observed = Checkers.isJvmHotspot();
        } catch (AssertionError err) {
            // The documented failure mode: the command path or separator cannot be determined.
            reportFail("testIsJvmHotspot: isJvmHotspot failure is an AssertionError with a diagnostic", err.getMessage());
            return;
        }

        // Independent re-derivation: the executable name of this process starts with "java".
        String cmdPath = ProcessHandle.current().info().command().orElse("?");
        String separator = System.getProperty("file.separator");
        String exeName = cmdPath.substring(cmdPath.lastIndexOf(separator) + 1);
        expectBool("isJvmHotspot agrees with the executable name prefix", exeName.startsWith("java"), observed);
        expectBool("isJvmHotspot is repeatable", observed, Checkers.isJvmHotspot());

        // The java.vm.name property is a second, independent signal on a real HotSpot.
        String vmName = System.getProperty("java.vm.name", "");
        if (hotspot) {
            if (vmName.contains("OpenJDK")) {
                expectBool("isJvmHotspot is true when java.vm.name mentions OpenJDK", true, observed);
            } else {
                reportSkip("isJvmHotspot vs java.vm.name", String.format("java.vm.name = [%s]", vmName));
            }
        }
    }

    // ========================================================================
    // Checkers.execCommand
    // ========================================================================

    private static void testExecCommand() {
        System.out.println("===== testExecCommand (Jacobin only) will produce ERROR messages so don't panic!  (:");
        String missing = "no-such-program-for-helperunittests --flag";
        expectInt("execCommand of a missing program returns 86", 86, callExecCommand(missing));

        // An empty command line is split into one empty word, which cannot be started either.
        expectInt("execCommand of an empty string returns 86", 86, callExecCommand(""));

        if (!execUsable) {
            reportSkip("execCommand sub-process tests", "no usable java executable for sub-processes");
            return;
        }

        String javaCmd = javaCommand();
        if (javaCmd.indexOf(' ') >= 0) {
            reportSkip("execCommand sub-process tests", "java executable path contains a space");
            return;
        }

        // Success.
        String successLine = String.format("%s -version", javaCmd);
        expectInt("execCommand of 'java -version' returns 0", 0, callExecCommand(successLine));
        expectContains("execCommand success echoes the command line", lastOut, String.format("execCommand: Command line: %s", successLine));
        expectContains("execCommand success reports exit code zero", lastOut, "execCommand: Exit code zero");
        expectContains("execCommand success prints Stdout label", lastOut, "execCommand: Stdout: ");
        expectContains("execCommand success prints Stderr label", lastOut, "execCommand: Stderr: ");
        expectNotContains("execCommand success is not flagged as an error", lastOut, "(***ERROR)");

    }

    // ========================================================================
    // jj._subProcess
    // ========================================================================

    private static void testSubProcess() {
        System.out.println("===== testSubProcess (Jacobin only) will produce ERROR messages so don't panic!  (:");
        jjSubProcessObject missing = makeSubProcessObject("no-such-program-for-helperunittests");
        int exitCode = callSubProcess(missing);
        expectTrue("jj._subProcess of no-such-program-for-helperunittests returns non-zero", exitCode != 0);

        if (!execUsable) {
            reportSkip("_subProcess sub-process tests", "no usable java executable for sub-processes");
            return;
        }
        String javaCmd = javaCommand();

        // Success and arguments (an array, so arguments containing spaces survive intact).
        jjSubProcessObject versionObj = makeSubProcessObject(javaCmd, "-version");
        expectInt("_subProcess of 'java -version' returns 0", 0, callSubProcess(versionObj));
        if (hotspot) {
            expectContains("_subProcess success reports the exit code", lastOut, "_subProcess: J function _subProcess: Exit Code = 0");
            expectContains("_subProcess success prints the stdout banner", lastOut, "_subProcess: J function _subProcess: Standard Output:");
            expectContains("_subProcess success prints the stderr banner", lastOut, "_subProcess: J function _subProcess: Standard Error:");
        }
        jjSubProcessObject spacesObj = makeSubProcessObject(javaCmd, "-Dhelper.test=a b c", "-version");
        expectInt("_subProcess keeps an argument that contains spaces", 0, callSubProcess(spacesObj));

        // Non-zero exit of a JVM that starts but fails.
        jjSubProcessObject failObj = makeSubProcessObject(javaCmd, "-cp", ".", "NoSuchClassForHelperUnitTests");
        int failExit = callSubProcess(failObj);
        expectTrue("_subProcess of a failing JVM returns non-zero", failExit != 0);
        expectTrue("_subProcess of a failing JVM does not return the start-failure code", failExit != 86);

        // The exit code of the child is propagated unchanged.
        if (childModeUsable) {
            int[] exitCodes = { 0, 1, 3, 42, 255 };
            for (int expectedExit : exitCodes) {
                jjSubProcessObject obj = makeSubProcessObject(childCommandLine("--exit", Integer.toString(expectedExit)));
                expectInt(String.format("_subProcess propagates child exit code %d", expectedExit), expectedExit, callSubProcess(obj));
            }
            jjSubProcessObject emitObj = makeSubProcessObject(childCommandLine("--emit"));
            expectInt("_subProcess of a child that writes to both streams returns 7", 7, callSubProcess(emitObj));
        } else {
            reportSkip("_subProcess exit code propagation", "requires a usable child process");
        }
    }

    // ========================================================================
    // jj._getProgramName
    // ========================================================================

    private static void testGetProgramName() {
        beginCapture();
        String name;
        try {
            name = jj._getProgramName();
        } finally {
            endCapture();
        }
        expectTrue("_getProgramName returns a non-empty name", name != null && !name.isEmpty());
        if (hotspot) {
            expectString("_getProgramName returns java under HotSpot", "java", name);
            expectContains("_getProgramName announces itself", lastOut, "_getProgramName: J-class function _getProgramName (not Jacobin)");
        } else {
            expectString("_getProgramName returns jacobin", "jacobin", name);
        }
    }

}
