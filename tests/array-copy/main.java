public class main {

    private static int checker(String label, String expected, String observed) {
        if (expected.equals(observed)) {
            System.out.printf("ok, %s: expected(%s) = observed(%s)\n", label, expected, observed);
            return 0;
        }
        System.out.printf("*** ERROR, %s: expected(%s) != observed(%s)\n", label, expected, observed);
        return 1;
    }

    public static void main(String[] args) {
    
        int errorCount = 0;
        String str;
    
        // Boolean arrays
        boolean[] srcBool = new boolean[] {false, true, true};
        boolean[] dstBool = new boolean[] {false, false, false, false, false};
        System.arraycopy(srcBool, 1, dstBool, 3, 2);
        str = "";
        for (int ix = 0; ix < dstBool.length; ix++) {
            if (dstBool[ix])
                str = str.concat("true");
            else
                str = str.concat("false");
        }
        errorCount += checker("dest boolean[]", "falsefalsefalsetruetrue", str);
        
        // Byte arrays
        byte[] srcByte = new byte[] {0x41, 0x42, 0x43}; // ABC
        byte[] dstByte = new byte[] {0x3A, 0x3A, 0x3A, 0x3A, 0x3A}; // 5 colons
        System.arraycopy(srcByte, 1, dstByte, 3, 2);
        str = new String(dstByte);
        errorCount += checker("dest byte[]", ":::BC", str);
        
        // Character arrays
        char[] srcChar = new char[] {'A', 'B', 'C'};
        char[] dstChar = new char[] {':', ':', ':', ':', ':'};
        System.arraycopy(srcChar, 1, dstChar, 3, 2);
        str = String.valueOf(dstChar);
        errorCount += checker("dest char[]", ":::BC", str);
        
        // Long arrays
        long[] srcLong = new long[] {1l, 2l, 3l};
        long[] dstLong = new long[] {0l, 0l, 0l, 0l, 0l};
        System.arraycopy(srcLong, 1, dstLong, 3, 2);
        str = "";
        for (int ix = 0; ix < dstLong.length; ix++)
            str = str.concat(String.valueOf(dstLong[ix]));
        errorCount += checker("dest long[]", "00023", str);
        
        // Double arrays
        double[] srcDouble = new double[] {1.1, 2.2, 3.3};
        double[] dstDouble = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
        System.arraycopy(srcDouble, 1, dstDouble, 3, 2);
        str = "";
        for (int ix = 0; ix < dstDouble.length; ix++)
            str = str.concat(String.format("%1.1f", dstDouble[ix]));
        errorCount += checker("dest double[]", "0.00.00.02.23.3", str);
        
        // User object copy
        klassy[] srcKlassy = new klassy[] { new klassy(1), new klassy(2), new klassy(3)};
        klassy[] dstKlassy = new klassy[] { new klassy(0), new klassy(0), new klassy(0), new klassy(0), new klassy(0)};
        System.arraycopy(srcKlassy, 1, dstKlassy, 3, 2);
        str = "";
        for (int ix = 0; ix < dstKlassy.length; ix++)
            str = str.concat(String.format("%d", dstKlassy[ix].ii));
        errorCount += checker("dest klassy[]", "00023", str);
        
        assert(errorCount == 0);
        System.out.println("Success!");
        
    }
}

class klassy {
    int ii;
    klassy(int arg) {
        ii = arg;
    }
}

