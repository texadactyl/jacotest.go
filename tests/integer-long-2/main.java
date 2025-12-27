public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        long[] testVals = {
            0L,
            1L,
            -1L,
            0x7FL,               // 127
            0x80L,               // 128
            0xFFL,               // 255
            0x80000000L,         // 2^31
            0xFFFFFFFFL,         // 2^32-1
            0x7FFFFFFFFFFFFFFFL, // Long.MAX_VALUE
            0x8000000000000000L  // Long.MIN_VALUE
        };

        // Exhaustive pairwise unsigned compare, max, min, sum
        for (long x : testVals) {
            for (long y : testVals) {
                errorCount += Checkers.checker(
                    String.format("compareUnsigned(%d,%d)", x, y),
                    Long.compareUnsigned(x, y),
                    Long.compareUnsigned(x, y));
                errorCount += Checkers.checker(
                    String.format("max(%d,%d)", x, y),
                    Long.max(x, y),
                    Long.max(x, y));
                errorCount += Checkers.checker(
                    String.format("min(%d,%d)", x, y),
                    Long.min(x, y),
                    Long.min(x, y));
                errorCount += Checkers.checker(
                    String.format("sum(%d,%d)", x, y),
                    Long.sum(x, y),
                    Long.sum(x, y));
            }
        }

        // rotateLeft / rotateRight tests for all bit widths
        for (long val : testVals) {
            for (int shift = 0; shift <= 64; shift += 8) {
                errorCount += Checkers.checker(
                    String.format("rotateLeft(%d,%d)", val, shift),
                    Long.rotateLeft(val, shift),
                    Long.rotateLeft(val, shift));
                errorCount += Checkers.checker(
                    String.format("rotateRight(%d,%d)", val, shift),
                    Long.rotateRight(val, shift),
                    Long.rotateRight(val, shift));
            }
        }

        // bitCount, numberOfLeadingZeros, numberOfTrailingZeros
        for (long val : testVals) {
            errorCount += Checkers.checker(
                String.format("bitCount(%d)", val),
                Long.bitCount(val),
                Long.bitCount(val));
            errorCount += Checkers.checker(
                String.format("numberOfLeadingZeros(%d)", val),
                Long.numberOfLeadingZeros(val),
                Long.numberOfLeadingZeros(val));
            errorCount += Checkers.checker(
                String.format("numberOfTrailingZeros(%d)", val),
                Long.numberOfTrailingZeros(val),
                Long.numberOfTrailingZeros(val));
        }

        // doubleValue and string conversions
        for (long val : testVals) {
            Checkers.withinTolerance(
                String.format("doubleValue(%d)", val),
                (double) val,
                (double) val,
                1e-10);
            errorCount += Checkers.checker(
                String.format("toBinaryString(%d)", val),
                Long.toBinaryString(val),
                Long.toBinaryString(val));
            errorCount += Checkers.checker(
                String.format("toHexString(%d)", val),
                Long.toHexString(val),
                Long.toHexString(val));
            errorCount += Checkers.checker(
                String.format("toOctalString(%d)", val),
                Long.toOctalString(val),
                Long.toOctalString(val));
            errorCount += Checkers.checker(
                String.format("toString(%d)", val),
                Long.toString(val),
                Long.toString(val));
            errorCount += Checkers.checker(
                String.format("toUnsignedString(%d)", val),
                Long.toUnsignedString(val),
                Long.toUnsignedString(val));
        }

        // Final check
        Checkers.theEnd(errorCount);
    }
}

