public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        // compare (signed) — HotSpot returns difference
        errorCount += Checkers.checker(
                "compare(123, -123)",
                246,
                Short.compare((short) 123, (short) -123)
        );

        errorCount += Checkers.checker(
                "compare(-123, 123)",
                -246,
                Short.compare((short) -123, (short) 123)
        );

        errorCount += Checkers.checker(
                "compare(100, 100)",
                0,
                Short.compare((short) 100, (short) 100)
        );

        // compareUnsigned — operands widened to unsigned short, then subtracted
        errorCount += Checkers.checker(
                "compareUnsigned(-256, 123)",
                65157,
                Short.compareUnsigned((short) -256, (short) 123)
        );

        errorCount += Checkers.checker(
                "compareUnsigned(123, -256)",
                -65157,
                Short.compareUnsigned((short) 123, (short) -256)
        );

        errorCount += Checkers.checker(
                "compareUnsigned(0, 0)",
                0,
                Short.compareUnsigned((short) 0, (short) 0)
        );

        // value conversions
        Short s = Short.valueOf((short) 12345);

        errorCount += Checkers.checker(
                "byteValue()",
                (byte) 57,              // 12345 & 0xff
                s.byteValue()
        );

        errorCount += Checkers.checker(
                "shortValue()",
                (short) 12345,
                s.shortValue()
        );

        errorCount += Checkers.checker(
                "intValue()",
                12345,
                s.intValue()
        );

        errorCount += Checkers.checker(
                "longValue()",
                12345L,
                s.longValue()
        );

        errorCount += Checkers.withinTolerance(
                "floatValue()",
                12345.0f,
                s.floatValue()
        );

        errorCount += Checkers.withinTolerance(
                "doubleValue()",
                12345.0d,
                s.doubleValue()
        );

        // equals
        errorCount += Checkers.checker(
                "equals(same value)",
                true,
                s.equals(Short.valueOf((short) 12345))
        );

        errorCount += Checkers.checker(
                "equals(different value)",
                false,
                s.equals(Short.valueOf((short) 1))
        );

        // toString
        errorCount += Checkers.checker(
                "toString()",
                "12345",
                s.toString()
        );

        errorCount += Checkers.checker(
                "toString(short)",
                "-1",
                Short.toString((short) -1)
        );

        // parseShort
        errorCount += Checkers.checker(
                "parseShort(\"123\")",
                (short) 123,
                Short.parseShort("123")
        );

        errorCount += Checkers.checker(
                "parseShort(\"7B\", 16)",
                (short) 123,
                Short.parseShort("7B", 16)
        );

        // reverseBytes
        errorCount += Checkers.checker(
                "reverseBytes(0x1234)",
                (short) 0x3412,
                Short.reverseBytes((short) 0x1234)
        );

        // unsigned conversions
        errorCount += Checkers.checker(
                "toUnsignedInt(-1)",
                65535,
                Short.toUnsignedInt((short) -1)
        );

        errorCount += Checkers.checker(
                "toUnsignedLong(-1)",
                65535L,
                Short.toUnsignedLong((short) -1)
        );

        Checkers.theEnd(errorCount);
    }
}

