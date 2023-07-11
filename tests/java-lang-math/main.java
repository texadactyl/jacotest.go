public class main {

	private static final double Pi = 3.14159265358979323846;
	private static final double epsilon = 1.0e-6;

    public static void main(String[] args) {
    
        int errorCount = 0;
        double dd;
        float ff;
        int ii;
        long jj;
        Helpers hh = new Helpers();

        dd = Math.abs(1.0d);
        errorCount += hh.checker("abs", 1.0d, dd, 1.0d);
        dd = Math.abs(0.0d);
        errorCount += hh.checker("abs", 0.0d, dd, 0.0d);
        dd = Math.abs(-1.0d);
        errorCount += hh.checker("abs", -1.0d, dd, 1.0d);

        ff = Math.abs(1.0f);
        errorCount += hh.checker("abs", 1.0f, ff, 1.0f);
        ff = Math.abs(0.0f);
        errorCount += hh.checker("abs", 0.0f, ff, 0.0f);
        ff = Math.abs(-1.0f);
        errorCount += hh.checker("abs", -1.0f, ff, 1.0f);

        ii = Math.abs(1);
        errorCount += hh.checker("abs", 1, ii, 1);
        ii = Math.abs(0);
        errorCount += hh.checker("abs", 0, ii, 0);
        ii = Math.abs(-1);
        errorCount += hh.checker("abs", -1, ii, 1);

        jj = Math.abs(1L);
        errorCount += hh.checker("abs", 1l, jj, 1L);
        jj = Math.abs(0L);
        errorCount += hh.checker("abs", 0l, jj, 0L);
        jj = Math.abs(-1L);
        errorCount += hh.checker("abs", -1l, jj, 1L);

        ii = Math.absExact(1);
        errorCount += hh.checker("absExact", 1, ii, 1);
        ii = Math.absExact(0);
        errorCount += hh.checker("absExact", 0, ii, 0);
        ii = Math.absExact(-1);
        errorCount += hh.checker("absExact", -1, ii, 1);

        jj = Math.absExact(1L);
        errorCount += hh.checker("absExact", 1l, jj, 1L);
        jj = Math.absExact(0L);
        errorCount += hh.checker("absExact", 0l, jj, 0L);
        jj = Math.absExact(-1L);
        errorCount += hh.checker("absExact", -1l, jj, 1L);

        dd = Math.acos(-1.0d);
        errorCount += hh.checker("acos", -1.0d, dd, 3.141592653589793d);
        dd = Math.acos(0.5d);
        errorCount += hh.checker("acos", 0.5d, dd, 1.047197551d);

        ii = Math.addExact(2, 3);
        errorCount += hh.checker("addExact", 2, 3, ii, 5);
        jj = Math.addExact(2l, 3l);
        errorCount += hh.checker("addExact", 2l, 3l, jj, 5l);
 
        dd = Math.asin(-1.0d);
        errorCount += hh.checker("asin", -1.0d, dd, -1.5707963267948966d);
        dd = Math.asin(0.5d);
        errorCount += hh.checker("asin", 0.5d, dd, 0.5235987755982989d);

        dd = Math.atan(-1.0d);
        errorCount += hh.checker("atan", -1.0d, dd, -0.7853981633974483d);
        dd = Math.atan(0.5d);
        errorCount += hh.checker("atan", 0.5d, dd, 0.4636476090008061d);

        dd = Math.atan2(2.0d, 3.0d);
        errorCount += hh.checker("atan2", 2.0d, 3.0d, dd, 0.5880026035475675d);
        dd = Math.atan2(-2.0d, 3.0d);
        errorCount += hh.checker("atan2", -2.0d, 3.0d, dd, -0.5880026035475675d);
        dd = Math.atan2(2.0d, -3.0d);
        errorCount += hh.checker("atan2", 2.0d, -3.0d, dd, 2.5535900500422257d);

        dd = Math.cbrt(-5.0d);
        errorCount += hh.checker("cbrt", -5.0d, dd, -1.7099759466766968d);

        dd = Math.ceil(-5.3d);
        errorCount += hh.checker("ceil", -5.3d, dd, -5.0d);
        dd = Math.ceil(0.001d);
        errorCount += hh.checker("ceil", 0.001d, dd, 1.0d);
        dd = Math.ceil(3.9d);
        errorCount += hh.checker("ceil", 3.9d, dd, 4.0d);

        dd = Math.copySign(-1.0d, 2.3d);
        errorCount += hh.checker("copySign", -1.0d, 2.3d, dd, 1.0d);
        dd = Math.copySign(-1.0d, -2.3d);
        errorCount += hh.checker("copySign", -1.0d, -2.3d, dd, -1.0d);
        ff = Math.copySign(-1.0f, 2.3f);
        errorCount += hh.checker("copySign", -1.0f, 2.3f, ff, 1.0f);
        ff = Math.copySign(-1.0f, -2.3f);
        errorCount += hh.checker("copySign", -1.0f, -2.3f, ff, -1.0f);

        dd = Math.cos(Pi);
        errorCount += hh.checker("cos", Pi, dd, -1.0d);
        dd = Math.cos(-0.5);
        errorCount += hh.checker("cos", -0.5, dd, 0.8775825618903728d);

        dd = Math.cosh(-0.1);
        errorCount += hh.checker("cosh", -0.1, dd, 1.0050041680558035d);
        dd = Math.cosh(0.5);
        errorCount += hh.checker("cosh", 0.5, dd, 1.1276259652063807d);

        ii = Math.decrementExact(43);
        errorCount += hh.checker("decrementExact", 43, ii, 42);
        jj = Math.decrementExact(43l);
        errorCount += hh.checker("decrementExact", 43l, jj, 42l);

        dd = Math.exp(-0.1);
        errorCount += hh.checker("exp", -0.1, dd, 0.9048374180359595d);
        dd = Math.expm1(-0.1);
        errorCount += hh.checker("exp", -0.1, dd, -0.09516258196404043d);

        dd = Math.floor(-5.3d);
        errorCount += hh.checker("floor", -5.3d, dd, -6.0d);
        dd = Math.floor(0.001d);
        errorCount += hh.checker("floor", 0.001d, dd, 0.0d);
        dd = Math.floor(3.9d);
        errorCount += hh.checker("floor", 3.9d, dd, 3.0d);

        ii = Math.floorDiv(-5, 2);
        errorCount += hh.checker("floorDiv", -5, 2, ii, -3);
        jj = Math.floorDiv(-5l, -2);
        errorCount += hh.checker("floorDiv", 5l, -2, jj, 2l);
        jj = Math.floorDiv(5l, -2l);
        errorCount += hh.checker("floorDiv", 5l, -2l, jj, -3l);

        ii = Math.floorMod(-5, 2);
        errorCount += hh.checker("floorMod", -5, 2, ii, 1l);
        ii = Math.floorMod(-5l, -2);
        errorCount += hh.checker("floorMod", 5l, -2, ii, -1);
        jj = Math.floorMod(5l, -2l);
        errorCount += hh.checker("floorMod", 5l, -2l, jj, -1l);

        dd = Math.fma(-5.3d, 2.2d, -1.2d);
        errorCount += hh.checker("fma", -5.3d, 2.2d, -1.2d, dd, -12.86d);
        ff = Math.fma(-5.3f, 2.2f, -1.2f);
        errorCount += hh.checker("fma", -5.3f, 2.2f, -1.2f, ff, -12.86f);
        
        ii = Math.getExponent(65537.0d);
        System.out.println("1 Math.getExponent returned to me");
        errorCount += hh.checker("getExponent", 65537.0d, ii, 16);
        System.out.println("2 checker returned to me");
        ii = Math.getExponent(1.01d);
        errorCount += hh.checker("getExponent", 1.01d, ii, 0);
        ii = Math.getExponent(0.0000013d);
        errorCount += hh.checker("getExponent", 0.0000013d, ii, -20);

        hh.byebye(errorCount);

    }

}
