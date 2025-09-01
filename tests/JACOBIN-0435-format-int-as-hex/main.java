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
        int errorCount = 0;
        errorCount += reporter(+0x1234567812345678L,	"1234567812345678");
        errorCount += reporter(+0x8234567812345678L,	"8234567812345678");
        errorCount += reporter(+65535L,    	"000000000000ffff");
        errorCount += reporter(+32767L,    	"0000000000007fff");
        errorCount += reporter(+64L,       	"0000000000000040");
        errorCount += reporter(0L,         	"0000000000000000");
        errorCount += reporter(-1L,        	"ffffffffffffffff");
        errorCount += reporter(-2L,        	"fffffffffffffffe");
        errorCount += reporter(-64L,       	"ffffffffffffffc0");
        errorCount += reporter(-32767L,    	"ffffffffffff8001");
        errorCount += reporter(-65535L,    	"ffffffffffff0001");
        
        Checkers.theEnd(errorCount);
    }
}  
