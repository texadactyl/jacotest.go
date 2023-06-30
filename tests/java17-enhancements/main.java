// Hacked from https://www.baeldung.com/java-17-new-features
// and https://www.baeldung.com/java-instantsource
// and https://www.happycoders.eu/java/java-17-features/

import java.util.stream.IntStream;
import java.util.random.RandomGeneratorFactory;
import java.util.random.RandomGenerator;
import java.util.Comparator;
import java.util.Arrays;
import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HexFormat;

public class main {

    static class InstantExample {
        InstantSource instantSource;

        public InstantExample(InstantSource source) {
            this.instantSource = source;
        }

        Instant getCurrentInstantFromInstantSource() {
            return instantSource.instant();
        }
    }

    static String RGF_ALGO_1 = "L64X1024MixRandom";
    static String RGF_ALGO_2 = "SplittableRandom";

    public static void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.println(value);
    }

    public static IntStream getPseudoInts(String algorithm, int streamSize, int upperBound) {
        // returns an IntStream with size @streamSize of random numbers generated using the @algorithm
        // where the lower bound is 0 and the upper is @upperBound (exclusive)
        return RandomGeneratorFactory.of(algorithm)
                .create()
                .ints(streamSize, 0, upperBound);
    }

    public static int compareHexFormat(String label, String expected, String observed) {
        String wstr = "compareHexFormat test " + label + ", expected: " + expected + ", observed: " + observed;
        System.out.println(wstr);
        if (expected.equals(observed)) {
            System.out.println("compareHexFormat :: Success");
            return 0;
        }
        System.out.println("compareHexFormat :: *** ERROR");
        return 1;
    }

    public static int compareHexFormatLong(String label, long expected, long observed) {
        String wstr = "compareHexFormat test " + label + ", expected: " + expected + ", observed: " + observed;
        System.out.println(wstr);
        if (expected == observed) {
            System.out.println("compareHexFormat :: Success");
            return 0;
        }
        System.out.println("compareHexFormat :: *** ERROR");
        return 1;
    }

    public static void main(String[] args) throws Exception {
        int errorCount = 0;

        System.out.println("Some of the Java 17 Enhancements");
        System.out.println("IntStream, RandomGeneratorFactory, InstantSource, HexFormat");

        // RandomGenerator Factory + IntStream
        IntStream iStream = getPseudoInts(RGF_ALGO_1, 8192, 8192);
        int[] intArray = iStream.toArray();
        Arrays.sort(intArray);
        String minnie = String.valueOf(intArray[0]);
        int nints = intArray.length;
        String maxie = String.valueOf(intArray[nints - 1]);
        double meanie = 0.0;
        for (int ndx = 0; ndx < nints; ++ndx) {
            meanie += intArray[ndx];
        }
        meanie /= (double) nints;
        printLabeledString("IntStream min: ", minnie);
        printLabeledString("IntStream max: ", maxie);
        printLabeledString("IntStream mean: ", String.valueOf(meanie));
        if (meanie > 4000.0 && meanie < 4200.0) {
            System.out.println("Success :: IntStream mean close enough to 4096.0");
        } else {
            errorCount += 1;
            printLabeledString("*** ERROR :: IntStream mean out of bounds {4000, 4200}. Observed: ", String.valueOf(meanie));
        }

        // RandomGenerator Factory #2
        RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.all()
                .findFirst()
                .orElse(RandomGeneratorFactory.of(RGF_ALGO_2));
        System.out.println("RandomGeneratorFactory name " + factory.name() + " in group " + factory.group() + " was selected");
        if (factory.name().equals("L32X64MixRandom") && factory.group().equals("LXM")) {
            System.out.println("Success :: RandomGeneratorFactory name and group comparison test");
        } else {
            errorCount += 1;
            System.out.println("*** ERROR :: expected RandomGeneratorFactory name=L32X64MixRandom and group=LXM");
        }

        // InstantSource testing
        LocalDateTime now = LocalDateTime.now();
        InstantSource instantSource = InstantSource.fixed(now.toInstant(ZoneOffset.UTC));
        Instant currentInstant = instantSource.instant();
        printLabeledString("Expected instant String value: ", currentInstant.toString());
        InstantExample testMe = new InstantExample(instantSource);
        Instant returnedInstant = testMe.getCurrentInstantFromInstantSource();
        printLabeledString("Observed instant String value: ", returnedInstant.toString());
        if (currentInstant == returnedInstant) {
            System.out.println("Success :: InstantSource feature test comparison");
        } else {
            errorCount += 1;
            System.out.println("*** ERROR :: InstantSource feature test comparison");
        }

        // Hex format
        HexFormat hexFormat = HexFormat.of();
        errorCount += compareHexFormat("hexFormat.toHexDigits('A')", "0041", hexFormat.toHexDigits('A'));
        errorCount += compareHexFormat("hexFormat.toHexDigits((byte) 10)", "0a", hexFormat.toHexDigits((byte) 10));
        errorCount += compareHexFormat("hexFormat.toHexDigits((short) 1_000)", "03e8", hexFormat.toHexDigits((short) 1_000));
        errorCount += compareHexFormat("hexFormat.toHexDigits(1_000_000)", "000f4240", hexFormat.toHexDigits(1_000_000));
        errorCount += compareHexFormat("hexFormat.toHexDigits(100_000_000_000L)", "000000174876e800", hexFormat.toHexDigits(100_000_000_000L));
        hexFormat = HexFormat.ofDelimiter(" ").withPrefix("0x").withUpperCase();
        errorCount += compareHexFormat("hexFormat.formatHex(new byte[] {1, 2, 3, 60, 126, -1})", "0x01 0x02 0x03 0x3C 0x7E 0xFF", hexFormat.formatHex(new byte[]{1, 2, 3, 60, 126, -1}));
        int ii = HexFormat.fromHexDigits("03E8"); // 1_000
        errorCount += compareHexFormatLong("HexFormat.fromHexDigits(03E8)", 1_000L, (long) HexFormat.fromHexDigits("03E8"));
        errorCount += compareHexFormatLong("HexFormat.fromHexDigitsToLong(174876E800)", 100_000_000_000L, HexFormat.fromHexDigitsToLong("174876E800"));
        hexFormat = HexFormat.ofDelimiter(" ").withPrefix("0h").withLowerCase();
        byte[] bytes = hexFormat.parseHex("0h01 0h02 0h03 0h3c 0h7e 0hff"); // byte[] {1, 2, 3, 60, 126, -1}
        boolean btest = (bytes[0] == 0x01) && (bytes[1] == 0x02) && (bytes[2] == 0x03) && (bytes[3] == 0x3c) && (bytes[4] == 0x7e) && (bytes[5] == -1);
        if (btest) {
            System.out.println("Success :: hexFormat.parseHex(0h01 0h02 0h03 0h3c 0h7e 0hff)");
        } else {
            errorCount += 1;
            System.out.println("*** ERROR :: hexFormat.parseHex(0h01 0h02 0h03 0h3c 0h7e 0hff)");
            System.out.println(Arrays.toString(bytes));
        }

        // Check the error count
        if (errorCount == 0) {
            System.out.println("No errors detected");
        } else {
            printLabeledString("Number of errors = ", String.valueOf(errorCount));
            System.exit(1);
        }
    }
}
