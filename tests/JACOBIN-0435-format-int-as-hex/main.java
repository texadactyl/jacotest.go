public class main {

    public static String cvtIntToHex(int arg) {
        byte[] bytes = new byte[] {
                (byte)((arg & 0xff000000) >> 24),
                (byte)((arg & 0x00ff0000) >> 16),
                (byte)((arg & 0x0000ff00) >> 8),
                (byte) (arg & 0x000000ff),
        };
        String str = String.format("%02x%02x%02x%02x", bytes[0], bytes[1], bytes[2], bytes[3]);
        return str;
    }
    
    public static int reporter(int arg, String expValue) {
        String obsValue = cvtIntToHex(arg);
        System.out.printf("%d:\t\tExpected value: ", arg);
        System.out.print(expValue);
        System.out.print(", observed value: ");
        System.out.print(obsValue);
        if (obsValue.equals(expValue)) {
            System.out.println(" -- ok");
            return 0;
        } else {
            System.out.println(" *** ERROR, mismatch");
            return 1;
        }
    }
    
    public static void main(String[] args) {
        int errCount = 0;
        errCount += reporter(+65535,    "0000ffff");
        errCount += reporter(+32767,    "00007fff");
        errCount += reporter(+64,       "00000040");
        errCount += reporter(0,         "00000000");
        errCount += reporter(-1,        "ffffffff");
        errCount += reporter(-2,        "fffffffe");
        errCount += reporter(-64,       "ffffffc0");
        errCount += reporter(-32767,    "ffff8001");
        errCount += reporter(-65535,    "ffff0001");
        assert (errCount == 0);
    }
}  
