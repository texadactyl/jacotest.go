import java.lang.Integer;
import java.lang.Long;

public class main {

    public static String cvtLongToHex(long arg) {
        String str = Long.toHexString(arg);
        int strlen = str.length();
    	int padCount = 16 - strlen;
    	for (int ii = 0; ii < padCount; ii++) {
    		str = String.format("0%s", str);
    	}
        return str;
    }
    
    public static int reporter(long arg, String expValue) {
        String obsValue = cvtLongToHex(arg);
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
        errCount += reporter(+0x1234567812345678L,	"1234567812345678");
        errCount += reporter(+0x8234567812345678L,	"8234567812345678");
        errCount += reporter(+65535L,    	"000000000000ffff");
        errCount += reporter(+32767L,    	"0000000000007fff");
        errCount += reporter(+64L,       	"0000000000000040");
        errCount += reporter(0L,         	"0000000000000000");
        errCount += reporter(-1L,        	"ffffffffffffffff");
        errCount += reporter(-2L,        	"fffffffffffffffe");
        errCount += reporter(-64L,       	"ffffffffffffffc0");
        errCount += reporter(-32767L,    	"ffffffffffff8001");
        errCount += reporter(-65535L,    	"ffffffffffff0001");
        
        assert (errCount == 0);
    }
}  
