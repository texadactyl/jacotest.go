public class HexDump {

    public static final int COLUMN_SIZE = 16;

    /*
        Dump a byte array, formatted as hex on the left, ASCII on the right.
        The finished String is can be displayed with printf or written to a file.
    */
    public static String dumpBytes(String label, byte[] buffer, int numBytes, int columnSize) {
        String output = String.format("%s (%d bytes)\n", label, buffer.length);
        
        int offset = 0;
        while (offset < numBytes) {
            int remaining = numBytes - offset;
            int lineSize = Math.min(remaining, columnSize);
            output = output.concat(_dumpOneLine(buffer, offset, lineSize, columnSize));
            offset += lineSize;
        }
        
        return output;
    }

    /*
        Helper for dumpBuffer.
    */
    private static String _dumpOneLine(byte[] buffer, int offset, int lineSize, int columnSize) {
        String hexPortion = "";
        String asciiPortion = "";

        for (int i = 0; i < lineSize; i++) {
            byte b = buffer[offset + i];
            hexPortion = hexPortion.concat(String.format("%02x ", b));
            if (b >= 32 && b <= 126) {
                asciiPortion = asciiPortion.concat(String.valueOf((char) b));
            } else {
                asciiPortion = asciiPortion.concat(".");
            }
        }

        // Pad the hex part if the line is not full
        for (int i = lineSize; i < columnSize; i++) {
            hexPortion = hexPortion.concat("   ");
        }

        // Ensure the hex part is padded correctly based on column size
        int hexPortionWidth = columnSize * 3;
        String fmt = String.format("\t%s%d%s", "%-", hexPortionWidth, "s %s%n");
        return String.format(fmt, hexPortion, asciiPortion);
    }

    /*
        Simple output of hex representation of a byte array.
    */
    public static String bytesToHex(byte[] argArray) {
        String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        String hexString = "";
        int wint, high, low;

        for (byte bite : argArray) {
            wint = bite & 0xff; // Convert to unsigned
            high = wint >>> 4;
            low = wint & 0x0f;
            hexString = hexString.concat(hexDigits[high]);
            hexString = hexString.concat(hexDigits[low]);
        }

        return hexString;
    }

    /*
        Simple output of hex representation of a byte array for a maximum length.
    */
    public static String bytesToHex(byte[] bytes, int maxLen) {
        StringBuilder sb = new StringBuilder();
        int len = Math.min(bytes.length, maxLen);
        for (int i = 0; i < len; i++) {
            sb.append(String.format("%02x", bytes[i]));
        }
        if (bytes.length > maxLen) {
            sb.append("...");
        }
        return sb.toString();
    }

}

