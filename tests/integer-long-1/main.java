public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        long a = 11L;
        long b = -123L;
        long c = Long.MAX_VALUE;
        long d = Long.MIN_VALUE;

        // valueOf
        errorCount += Checkers.checker("valueOf(1234)", 1234L, Long.valueOf(1234L).longValue());
        errorCount += Checkers.checker("valueOf(-5678)", -5678L, Long.valueOf(-5678L).longValue());

        // compare
        errorCount += Checkers.checker("compare(a,b)", 1L, Long.compare(a, b));
        errorCount += Checkers.checker("compare(b,a)", -1L, Long.compare(b, a));
        errorCount += Checkers.checker("compare(c,d)", 1L, Long.compare(c, d));
        errorCount += Checkers.checker("compare(d,c)", -1L, Long.compare(d, c));

        // compareUnsigned
        errorCount += Checkers.checker("compareUnsigned(a,b)", -1L, Long.compareUnsigned(a, b));
        errorCount += Checkers.checker("compareUnsigned(b,a)", 1L, Long.compareUnsigned(b, a));
        errorCount += Checkers.checker("compareUnsigned(c,d)", -1L, Long.compareUnsigned(c, d));
        errorCount += Checkers.checker("compareUnsigned(d,c)", 1L, Long.compareUnsigned(d, c));
        errorCount += Checkers.checker("compareUnsigned(Long.MIN_VALUE, Long.MAX_VALUE)", 1L, Long.compareUnsigned(d, c));
        errorCount += Checkers.checker("compareUnsigned(Long.MAX_VALUE, Long.MIN_VALUE)", -1L, Long.compareUnsigned(c, d));

        // bitCount
        errorCount += Checkers.checker("bitCount(a)", 3L, Long.bitCount(a));
        errorCount += Checkers.checker("bitCount(b)", 59L, Long.bitCount(b));
        errorCount += Checkers.checker("bitCount(c)", 63L, Long.bitCount(c));
        errorCount += Checkers.checker("bitCount(d)", 1L, Long.bitCount(d));

        // numberOfLeadingZeros
        errorCount += Checkers.checker("numberOfLeadingZeros(a)", 60L, Long.numberOfLeadingZeros(a));
        errorCount += Checkers.checker("numberOfLeadingZeros(b)", 0L, Long.numberOfLeadingZeros(b));
        errorCount += Checkers.checker("numberOfLeadingZeros(c)", 1L, Long.numberOfLeadingZeros(c));
        errorCount += Checkers.checker("numberOfLeadingZeros(d)", 0L, Long.numberOfLeadingZeros(d));
        errorCount += Checkers.checker("numberOfLeadingZeros(1L)", 63L, Long.numberOfLeadingZeros(1L));
        errorCount += Checkers.checker("numberOfLeadingZeros(0L)", 64L, Long.numberOfLeadingZeros(0L));

        // numberOfTrailingZeros
        errorCount += Checkers.checker("numberOfTrailingZeros(a)", 0L, Long.numberOfTrailingZeros(a));
        errorCount += Checkers.checker("numberOfTrailingZeros(b)", 0L, Long.numberOfTrailingZeros(b));
        errorCount += Checkers.checker("numberOfTrailingZeros(c)", 0L, Long.numberOfTrailingZeros(c));
        errorCount += Checkers.checker("numberOfTrailingZeros(d)", 63L, Long.numberOfTrailingZeros(d));
        errorCount += Checkers.checker("numberOfTrailingZeros(0L)", 64L, Long.numberOfTrailingZeros(0L));

        // max / min
        errorCount += Checkers.checker("max(a,b)", 11L, Long.max(a, b));
        errorCount += Checkers.checker("min(a,b)", -123L, Long.min(a, b));
        errorCount += Checkers.checker("max(c,d)", Long.MAX_VALUE, Long.max(c, d));
        errorCount += Checkers.checker("min(c,d)", Long.MIN_VALUE, Long.min(c, d));
        errorCount += Checkers.checker("max(a,a)", 11L, Long.max(a, a));
        errorCount += Checkers.checker("min(a,a)", 11L, Long.min(a, a));

        // sum
        errorCount += Checkers.checker("sum(a,b)", -112L, Long.sum(a, b));
        errorCount += Checkers.checker("sum(c,d)", -1L, Long.sum(c, d));
        errorCount += Checkers.checker("sum(a,0)", 11L, Long.sum(a, 0L));
        errorCount += Checkers.checker("sum(d,0)", Long.MIN_VALUE, Long.sum(d, 0L));

        // rotateLeft / rotateRight edge cases
        errorCount += Checkers.checker("rotateLeft(a,1)", 22L, Long.rotateLeft(a, 1));
        errorCount += Checkers.checker("rotateRight(a,1)", -9223372036854775803L, Long.rotateRight(a, 1));
        errorCount += Checkers.checker("rotateLeft(a,64)", a, Long.rotateLeft(a, 64));
        errorCount += Checkers.checker("rotateRight(a,64)", a, Long.rotateRight(a, 64));
        errorCount += Checkers.checker("rotateLeft(a,0)", a, Long.rotateLeft(a, 0));
        errorCount += Checkers.checker("rotateRight(a,0)", a, Long.rotateRight(a, 0));
        errorCount += Checkers.checker("rotateLeft(a,128)", a, Long.rotateLeft(a, 128));
        errorCount += Checkers.checker("rotateRight(a,128)", a, Long.rotateRight(a, 128));

        // reverse / reverseBytes
        errorCount += Checkers.checker("reverse(a)", -3458764513820540928L, Long.reverse(a));
        errorCount += Checkers.checker("reverseBytes(a)", 792633534417207296L, Long.reverseBytes(a));
        errorCount += Checkers.checker("reverse(Long.MIN_VALUE)", 1, Long.reverse(Long.MIN_VALUE));
        errorCount += Checkers.checker("reverseBytes(Long.MIN_VALUE)", 128, Long.reverseBytes(Long.MIN_VALUE));

        // signum
        errorCount += Checkers.checker("signum(a)", 1L, Long.signum(a));
        errorCount += Checkers.checker("signum(b)", -1L, Long.signum(b));
        errorCount += Checkers.checker("signum(0)", 0L, Long.signum(0L));

        // string conversions
        errorCount += Checkers.checker("toBinaryString(a)", "1011", Long.toBinaryString(a));
        errorCount += Checkers.checker("toHexString(a)", "b", Long.toHexString(a));
        errorCount += Checkers.checker("toOctalString(a)", "13", Long.toOctalString(a));
        errorCount += Checkers.checker("toString(a)", "11", Long.toString(a));
        errorCount += Checkers.checker("toUnsignedString(a)", "11", Long.toUnsignedString(a));
        errorCount += Checkers.checker("toUnsignedString(a,16)", "b", Long.toUnsignedString(a, 16));

        // doubleValue with tolerance
        Checkers.withinTolerance("doubleValue(a)", 11.0, (double) Long.valueOf(a), 1e-6);

        // divideUnsigned / remainderUnsigned edge cases
        errorCount += Checkers.checker("divideUnsigned(10,3)", 3L, Long.divideUnsigned(10L, 3L));
        errorCount += Checkers.checker("divideUnsigned(0,5)", 0L, Long.divideUnsigned(0L, 5L));
        errorCount += Checkers.checker("divideUnsigned(Long.MAX_VALUE,1)", Long.MAX_VALUE, Long.divideUnsigned(Long.MAX_VALUE, 1L));
        errorCount += Checkers.checker("divideUnsigned(Long.MIN_VALUE,2)", 4611686018427387904L, Long.divideUnsigned(Long.MIN_VALUE, 2L));
        errorCount += Checkers.checker("remainderUnsigned(10,3)", 1L, Long.remainderUnsigned(10L, 3L));
        errorCount += Checkers.checker("remainderUnsigned(0,5)", 0L, Long.remainderUnsigned(0L, 5L));
        errorCount += Checkers.checker("remainderUnsigned(Long.MAX_VALUE,1)", 0L, Long.remainderUnsigned(Long.MAX_VALUE, 1L));
        errorCount += Checkers.checker("remainderUnsigned(Long.MIN_VALUE,2)", 0L, Long.remainderUnsigned(Long.MIN_VALUE, 2L));

        // compress / expand roundtrip
        long testValue = 0x12345678L;
        long compressed = Long.compress(testValue, 16);
        long expanded = Long.expand(compressed, 16);
        errorCount += Checkers.checker("compress/expand roundtrip", 16, expanded);

        // compress/expand edge
        long edgeValue = 0xFFFFFFFFL;
        long compressedEdge = Long.compress(edgeValue, 32);
        long expandedEdge = Long.expand(compressedEdge, 32);
        errorCount += Checkers.checker("compress/expand edge", 32, expandedEdge);

        // Final check
        Checkers.theEnd(errorCount);
    }
}

