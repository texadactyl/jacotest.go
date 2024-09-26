
public class sub1 {

    /*
        As-is, the main program succeeds.
        Uncomment the line that defines i3 (<=====) to see the following failure:
        
        1234567890123456
        com.sun.jdi.InternalException: locateExceptionFrame: Method sub1.<clinit> not found in MTable
         
        goroutine 1 [running]:
        jacobin/exceptions.minimalAbort(0x2e, {0xc004713f40, 0x3e})
	        /home/elkins/BASIS/jacobin/src/exceptions/throw.go:282 +0x26
        jacobin/exceptions.locateExceptionFrame(0xc0024b5810, {0xc004445e40, 0x20}, 0x0)
	        /home/elkins/BASIS/jacobin/src/exceptions/catchFrame.go:67 +0x125
        jacobin/exceptions.FindCatchFrame(0xc00386b590, {0xc004445e40?, 0x1?}, 0x2?)
	        /home/elkins/BASIS/jacobin/src/exceptions/catchFrame.go:44 +0xad
        jacobin/jvm.runFrame(0xc00386b590)
	        /home/elkins/BASIS/jacobin/src/jvm/run.go:419 +0x308d
        jacobin/jvm.runThread(0x7544b0)
	        /home/elkins/BASIS/jacobin/src/jvm/run.go:139 +0x87
        jacobin/jvm.StartExec({0xc00405fbd8, 0x4}, 0x7544b0, 0x754ec0)
	        /home/elkins/BASIS/jacobin/src/jvm/run.go:108 +0xa93
        jacobin/jvm.JVMrun()
	        /home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:142 +0x5a9
        main.main()
	        /home/elkins/BASIS/jacobin/src/main.go:12 +0xf

        ===== DumpStatics BEGIN
        main.$assertionsDisabled     {I 0}
        sub1.HEX_DIGITS     {[C <nil>}
        sub1.i1     {I 0}
        sub1.i3     {I 0}
        ===== DumpStatics END
        Version: 0.6.012 Build 3059, OS: linux
        
    */    
    
    static final int i1 = 42;

    //static final int i3 = (i1 == 42) ? sub2.get43() : 0;   // <====================================
    
    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static String toString(byte[] ba) {
        return toString(ba, 0, ba.length);
    }

    public static String toString(byte[] ba, int offset, int length) {
        char[] buf = new char[length * 2];
        for (int i = offset, j = 0, k; i < offset + length; ) {
            k = ba[i++];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[k & 0x0F];
        }
        String ss = new String(buf);
        return ss;
    }
}
