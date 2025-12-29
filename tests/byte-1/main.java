public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        byte[] testVals = {
            Byte.MIN_VALUE, // -128
            -1,
            0,
            1,
            Byte.MAX_VALUE  // 127
        };

        // compare / compareUnsigned
        for (byte a : testVals) {
            for (byte b : testVals) {
                errorCount += Checkers.checker(
                    String.format("compare(%d,%d)", a, b),
                    Byte.compare(a, b),
                    Byte.compare(a, b));

                errorCount += Checkers.checker(
                    String.format("compareUnsigned(%d,%d)", a, b),
                    Byte.compareUnsigned(a, b),
                    Byte.compareUnsigned(a, b));
            }
        }

        // equals, hashCode
        for (byte v : testVals) {
            Byte obj = Byte.valueOf(v);

            errorCount += Checkers.checker(
                String.format("equals(%d)", v),
                obj.equals(Byte.valueOf(v)),
                obj.equals(Byte.valueOf(v)));

            errorCount += Checkers.checker(
                String.format("hashCode(%d)", v),
                obj.hashCode(),
                Byte.hashCode(v));
        }

        // numeric widening
        for (byte v : testVals) {
            Byte obj = Byte.valueOf(v);

            errorCount += Checkers.checker(
                String.format("byteValue(%d)", v),
                obj.byteValue(),
                obj.byteValue());

            errorCount += Checkers.checker(
                String.format("shortValue(%d)", v),
                obj.shortValue(),
                obj.shortValue());

            errorCount += Checkers.checker(
                String.format("intValue(%d)", v),
                obj.intValue(),
                obj.intValue());

            errorCount += Checkers.checker(
                String.format("longValue(%d)", v),
                obj.longValue(),
                obj.longValue());

            Checkers.withinTolerance(
                String.format("floatValue(%d)", v),
                obj.floatValue(),
                obj.floatValue(),
                1e-7);

            Checkers.withinTolerance(
                String.format("doubleValue(%d)", v),
                obj.doubleValue(),
                obj.doubleValue(),
                1e-12);
        }

        // toString
        for (byte v : testVals) {
            errorCount += Checkers.checker(
                String.format("toString(%d)", v),
                Byte.toString(v),
                Byte.toString(v));

            errorCount += Checkers.checker(
                String.format("obj.toString(%d)", v),
                Byte.valueOf(v).toString(),
                Byte.valueOf(v).toString());
        }

        // unsigned conversions
        for (byte v : testVals) {
            errorCount += Checkers.checker(
                String.format("toUnsignedInt(%d)", v),
                Byte.toUnsignedInt(v),
                Byte.toUnsignedInt(v));

            errorCount += Checkers.checker(
                String.format("toUnsignedLong(%d)", v),
                Byte.toUnsignedLong(v),
                Byte.toUnsignedLong(v));
        }

        // parseByte / valueOf(String)
        String[] parseVals = { "-128", "-1", "0", "1", "127" };

        for (String s : parseVals) {
            errorCount += Checkers.checker(
                String.format("parseByte(%s)", s),
                Byte.parseByte(s),
                Byte.parseByte(s));

            errorCount += Checkers.checker(
                String.format("valueOf(%s)", s),
                Byte.valueOf(s),
                Byte.valueOf(s));
        }

        // parseByte with radix
        errorCount += Checkers.checker(
            "parseByte(7F,16)",
            Byte.parseByte("7F", 16),
            Byte.parseByte("7F", 16));

        errorCount += Checkers.checker(
            "parseByte(-80,16)",
            Byte.parseByte("-80", 16),
            Byte.parseByte("-80", 16));

        // decode
        String[] decodeVals = { "127", "-128", "0x7F", "-0x80", "0177" };

        for (String s : decodeVals) {
            errorCount += Checkers.checker(
                String.format("decode(%s)", s),
                Byte.decode(s),
                Byte.decode(s));
        }

        // Final check
        Checkers.theEnd(errorCount);
    }
}

