public class main {

	private static final double Pi = 3.14159265358979323846;
	private static final double Pi_2 = Pi * 0.5;
	private static final double Pi_4 = Pi * 0.25;
	private static final double Pi_8 = Pi * 0.125;

    public static void main(String[] args) {
    
        int errorCount = 0;
        double dd;
        float ff;
        int ii;
        long jj, jj1, jj2;
        Helpers hh = new Helpers();

        dd = StrictMath.abs(1.0d);
        errorCount += hh.checker("abs", 1.0d, dd, 1.0d);
        dd = StrictMath.abs(0.0d);
        errorCount += hh.checker("abs", 0.0d, dd, 0.0d);
        dd = StrictMath.abs(-1.0d);
        errorCount += hh.checker("abs", -1.0d, dd, 1.0d);

        ff = StrictMath.abs(1.0f);
        errorCount += hh.checker("abs", 1.0f, ff, 1.0f);
        ff = StrictMath.abs(0.0f);
        errorCount += hh.checker("abs", 0.0f, ff, 0.0f);
        ff = StrictMath.abs(-1.0f);
        errorCount += hh.checker("abs", -1.0f, ff, 1.0f);

        ii = StrictMath.abs(1);
        errorCount += hh.checker("abs", 1, ii, 1);
        ii = StrictMath.abs(0);
        errorCount += hh.checker("abs", 0, ii, 0);
        ii = StrictMath.abs(-1);
        errorCount += hh.checker("abs", -1, ii, 1);

        jj = StrictMath.abs(1L);
        errorCount += hh.checker("abs", 1l, jj, 1L);
        jj = StrictMath.abs(0L);
        errorCount += hh.checker("abs", 0l, jj, 0L);
        jj = StrictMath.abs(-1L);
        errorCount += hh.checker("abs", -1l, jj, 1L);

        ii = StrictMath.absExact(1);
        errorCount += hh.checker("absExact", 1, ii, 1);
        ii = StrictMath.absExact(0);
        errorCount += hh.checker("absExact", 0, ii, 0);
        ii = StrictMath.absExact(-1);
        errorCount += hh.checker("absExact", -1, ii, 1);

        jj = StrictMath.absExact(1L);
        errorCount += hh.checker("absExact", 1l, jj, 1L);
        jj = StrictMath.absExact(0L);
        errorCount += hh.checker("absExact", 0l, jj, 0L);
        jj = StrictMath.absExact(-1L);
        errorCount += hh.checker("absExact", -1l, jj, 1L);

        dd = StrictMath.acos(-1.0d);
        errorCount += hh.checker("acos", -1.0d, dd, 3.141592653589793d);
        dd = StrictMath.acos(0.5d);
        errorCount += hh.checker("acos", 0.5d, dd, 1.047197551d);

        ii = StrictMath.addExact(2, 3);
        errorCount += hh.checker("addExact", 2, 3, ii, 5);
        jj = StrictMath.addExact(2l, 3l);
        errorCount += hh.checker("addExact", 2l, 3l, jj, 5l);
 
        dd = StrictMath.asin(-1.0d);
        errorCount += hh.checker("asin", -1.0d, dd, -1.5707963267948966d);
        dd = StrictMath.asin(0.5d);
        errorCount += hh.checker("asin", 0.5d, dd, 0.5235987755982989d);

        dd = StrictMath.atan(-1.0d);
        errorCount += hh.checker("atan", -1.0d, dd, -0.7853981633974483d);
        dd = StrictMath.atan(0.5d);
        errorCount += hh.checker("atan", 0.5d, dd, 0.4636476090008061d);

        dd = StrictMath.atan2(2.0d, 3.0d);
        errorCount += hh.checker("atan2", 2.0d, 3.0d, dd, 0.5880026035475675d);
        dd = StrictMath.atan2(-2.0d, 3.0d);
        errorCount += hh.checker("atan2", -2.0d, 3.0d, dd, -0.5880026035475675d);
        dd = StrictMath.atan2(2.0d, -3.0d);
        errorCount += hh.checker("atan2", 2.0d, -3.0d, dd, 2.5535900500422257d);

        dd = StrictMath.cbrt(-5.0d);
        errorCount += hh.checker("cbrt", -5.0d, dd, -1.7099759466766968d);

        dd = StrictMath.ceil(-5.3d);
        errorCount += hh.checker("ceil", -5.3d, dd, -5.0d);
        dd = StrictMath.ceil(0.001d);
        errorCount += hh.checker("ceil", 0.001d, dd, 1.0d);
        dd = StrictMath.ceil(3.9d);
        errorCount += hh.checker("ceil", 3.9d, dd, 4.0d);

        dd = StrictMath.copySign(-1.0d, 2.3d);
        errorCount += hh.checker("copySign", -1.0d, 2.3d, dd, 1.0d);
        dd = StrictMath.copySign(-1.0d, -2.3d);
        errorCount += hh.checker("copySign", -1.0d, -2.3d, dd, -1.0d);
        ff = StrictMath.copySign(-1.0f, 2.3f);
        errorCount += hh.checker("copySign", -1.0f, 2.3f, ff, 1.0f);
        ff = StrictMath.copySign(-1.0f, -2.3f);
        errorCount += hh.checker("copySign", -1.0f, -2.3f, ff, -1.0f);

        dd = StrictMath.cos(Pi);
        errorCount += hh.checker("cos", Pi, dd, -1.0d);
        dd = StrictMath.cos(-0.5);
        errorCount += hh.checker("cos", -0.5, dd, 0.8775825618903728d);

        dd = StrictMath.cosh(-0.1);
        errorCount += hh.checker("cosh", -0.1, dd, 1.0050041680558035d);
        dd = StrictMath.cosh(0.5);
        errorCount += hh.checker("cosh", 0.5, dd, 1.1276259652063807d);

        ii = StrictMath.decrementExact(43);
        errorCount += hh.checker("decrementExact", 43, ii, 42);
        jj = StrictMath.decrementExact(43l);
        errorCount += hh.checker("decrementExact", 43l, jj, 42l);

        dd = StrictMath.exp(-0.1);
        errorCount += hh.checker("exp", -0.1, dd, 0.9048374180359595d);
        dd = StrictMath.expm1(-0.1);
        errorCount += hh.checker("exp", -0.1, dd, -0.09516258196404043d);

        dd = StrictMath.floor(-5.3d);
        errorCount += hh.checker("floor", -5.3d, dd, -6.0d);
        dd = StrictMath.floor(0.001d);
        errorCount += hh.checker("floor", 0.001d, dd, 0.0d);
        dd = StrictMath.floor(3.9d);
        errorCount += hh.checker("floor", 3.9d, dd, 3.0d);

        ii = StrictMath.floorDiv(-5, 2);
        errorCount += hh.checker("floorDiv", -5, 2, ii, -3);
        jj = StrictMath.floorDiv(-5l, -2);
        errorCount += hh.checker("floorDiv", 5l, -2, jj, 2l);
        jj = StrictMath.floorDiv(5l, -2l);
        errorCount += hh.checker("floorDiv", 5l, -2l, jj, -3l);

        ii = StrictMath.floorMod(-5, 2);
        errorCount += hh.checker("floorMod", -5, 2, ii, 1l);
        ii = StrictMath.floorMod(-5l, -2);
        errorCount += hh.checker("floorMod", 5l, -2, ii, -1);
        jj = StrictMath.floorMod(5l, -2l);
        errorCount += hh.checker("floorMod", 5l, -2l, jj, -1l);

        dd = StrictMath.fma(-5.3d, 2.2d, -1.2d);
        errorCount += hh.checker("fma", -5.3d, 2.2d, -1.2d, dd, -12.86d);
        ff = StrictMath.fma(-5.3f, 2.2f, -1.2f);
        errorCount += hh.checker("fma", -5.3f, 2.2f, -1.2f, ff, -12.86f);
        
        ii = StrictMath.getExponent(65537.0d);
        errorCount += hh.checker("getExponent", 65537.0d, ii, 16);
        ii = StrictMath.getExponent(1.01d);
        errorCount += hh.checker("getExponent", 1.01d, ii, 0);
        ii = StrictMath.getExponent(0.0000013d);
        errorCount += hh.checker("getExponent", 0.0000013d, ii, -20);

        dd = StrictMath.hypot(2.0d, 3.0d);
        errorCount += hh.checker("hypot", 2.0d, 3.0d, dd, 3.605551275463989d);

        dd = StrictMath.IEEEremainder(65.4321d, 8.7654321d);
        errorCount += hh.checker("IEEEremainder", 65.4321d, 8.7654321d, dd, 4.0740753d);

        ii = StrictMath.incrementExact(41);
        errorCount += hh.checker("incrementExact", 41, ii, 42);
        jj = StrictMath.incrementExact(41l);
        errorCount += hh.checker("incrementExact", 41l, jj, 42l);

        dd = StrictMath.log(0.1d);
        errorCount += hh.checker("log", 0.1d, dd, -2.3025850929940455d);
        dd = StrictMath.log10(0.123456789d);
        errorCount += hh.checker("log10", 0.123456789d, dd, -0.9084850228307295d);
        dd = StrictMath.log1p(0.123456789d);
        errorCount += hh.checker("log1p", 0.123456789d, dd, 0.11641035084441127d);

        dd = StrictMath.max(65.4321d, 8.7654321d);
        errorCount += hh.checker("max", 65.4321d, 8.7654321d, dd, 65.4321d);
        ff = StrictMath.max(65.4321f, 8.7654321f);
        errorCount += hh.checker("max", 65.4321f, 8.7654321f, ff, 65.4321f);
        ii = StrictMath.max(65, 8);
        errorCount += hh.checker("max", 65, 8, ii, 65);
        jj = StrictMath.max(65l, 8l);
        errorCount += hh.checker("max", 65l, 8l, jj, 65l);

        dd = StrictMath.min(65.4321d, 8.7654321d);
        errorCount += hh.checker("min", 65.4321d, 8.7654321d, dd, 8.7654321d);
        ff = StrictMath.min(65.4321f, 8.7654321f);
        errorCount += hh.checker("min", 65.4321f, 8.7654321f, ff, 8.7654321f);
        ii = StrictMath.min(65, 8);
        errorCount += hh.checker("min", 65, 8, ii, 8);
        jj = StrictMath.min(65l, 8l);
        errorCount += hh.checker("min", 65l, 8l, jj, 8l);

        ii = StrictMath.multiplyExact(3, 2);
        errorCount += hh.checker("multiplyExact", 3, 2, ii, 6);
        jj = StrictMath.multiplyExact(3l, 2);
        errorCount += hh.checker("multiplyExact", 3l, 2, jj, 6);
        jj = StrictMath.multiplyExact(3l, 2l);
        errorCount += hh.checker("multiplyExact", 3l, 2l, jj, 6);

        jj = StrictMath.multiplyFull(3, 2);
        errorCount += hh.checker("multiplyFull", 3, 2, jj, 6);
        jj = StrictMath.multiplyFull(Integer.MAX_VALUE, Integer.MAX_VALUE);
        errorCount += hh.checker("multiplyFull", Integer.MAX_VALUE, Integer.MAX_VALUE, jj, 4611686014132420609l);

        jj1 = 45267356745l;
        jj2 = 45676556735l;
        jj = StrictMath.multiplyHigh(jj1, jj2);
        errorCount += hh.checker("multiplyHigh", jj1, jj2, jj, 112l);
        jj1 = 9223372036854775801l;
        jj2 = 9223372036854775802l;
        jj = StrictMath.multiplyHigh(jj1, jj2);
        errorCount += hh.checker("multiplyHigh", jj1, jj2, jj, 4611686018427387897l);
        jj1 = 9223372036854775807l;
        jj2 = 9223372036854775807l;
        jj = StrictMath.multiplyHigh(jj1, jj2);
        errorCount += hh.checker("multiplyHigh", jj1, jj2, jj, 4611686018427387903l);

        ii = StrictMath.negateExact(65);
        errorCount += hh.checker("negateExact", 65, ii, -65);
        jj = StrictMath.negateExact(65l);
        errorCount += hh.checker("negateExact", 65l, jj, -65l);

        dd = StrictMath.nextAfter(2.03d, 42.0d);
        errorCount += hh.checker("nextAfter", 2.03d, 42.0d, dd, 2.0300000000000002d);
        dd = StrictMath.nextAfter(2.03d, -42.0d);
        errorCount += hh.checker("nextAfter", 2.03d, -42.0d, dd, 2.0299999999999994d);

        ff = StrictMath.nextAfter(2.03f, 42.0f);
        errorCount += hh.checker("nextAfter", 2.03f, 42.0f, ff, 2.0300002f);
        ff = StrictMath.nextAfter(2.03f, -42.0f);
        errorCount += hh.checker("nextAfter", 2.03f, -42.0f, ff, 2.0299997f);

        dd = StrictMath.nextDown(2.03d);
        errorCount += hh.checker("nextDown", 2.03d, dd, 2.0299999999999994d);
        ff = StrictMath.nextDown(2.03f);
        errorCount += hh.checker("nextDown", 2.03f, ff, 2.0299997f);

        dd = StrictMath.nextUp(2.03d);
        errorCount += hh.checker("nextUp", 2.03d, dd, 2.0300000000000002d);
        ff = StrictMath.nextUp(2.03f);
        errorCount += hh.checker("nextUp", 2.03f, ff, 2.0300002f);

        dd = StrictMath.pow(2.03d, 3.01d);
        errorCount += hh.checker("pow", 2.03d, 3.01d, dd, 8.42486739873172d);

		for (int ix = 0; ix < 10; ++ix) {
		    dd = StrictMath.random();
		    errorCount += hh.checkerRange("random", dd, 0.0, 1.0);
		}
		
        dd = StrictMath.rint(2.49d);
        errorCount += hh.checker("rint", 2.49d, dd, 2.0d);
        dd = StrictMath.rint(2.51d);
        errorCount += hh.checker("rint", 2.51d, dd, 3.0d);
        dd = StrictMath.rint(-2.49d);
        errorCount += hh.checker("rint", -2.49d, dd, -2.0d);
        dd = StrictMath.rint(-2.51d);
        errorCount += hh.checker("rint", -2.51d, dd, -3.0d);

        jj = StrictMath.round(2.49d);
        errorCount += hh.checker("round", 2.49d, jj, 2l);
        jj = StrictMath.round(2.51d);
        errorCount += hh.checker("round", 2.51d, jj, 3l);
        ii = StrictMath.round(2.47f);
        errorCount += hh.checker("round", 2.47f, ii, 2);
        ii = StrictMath.round(2.51f);
        errorCount += hh.checker("round", 2.51f, ii, 3);

        jj = StrictMath.round(-2.49d);
        errorCount += hh.checker("round", -2.49d, jj, -2l);
        jj = StrictMath.round(-2.51d);
        errorCount += hh.checker("round", -2.51d, jj, -3l);
        ii = StrictMath.round(-2.47f);
        errorCount += hh.checker("round", -2.47f, ii, -2);
        ii = StrictMath.round(-2.51f);
        errorCount += hh.checker("round", -2.51f, ii, -3);

        dd = StrictMath.scalb(8d, 3);
        errorCount += hh.checker("scalb", 8d, 3, dd, 64d);
        ff = StrictMath.scalb(8f, 3);
        errorCount += hh.checker("scalb", 8f, 3, ff, 64f);

        dd = StrictMath.signum(0.001d);
        errorCount += hh.checker("signum", 0.001d, dd, 1d);
        ff = StrictMath.signum(0.001f);
        errorCount += hh.checker("signum", 0.001f, ff, 1f);
        dd = StrictMath.signum(-0.001d);
        errorCount += hh.checker("signum", -0.001d, dd, -1d);
        ff = StrictMath.signum(-0.001f);
        errorCount += hh.checker("signum", -0.001f, ff, -1f);
        dd = StrictMath.signum(0d);
        errorCount += hh.checker("signum", 0d, dd, 0d);
        ff = StrictMath.signum(0f);
        errorCount += hh.checker("signum", 0f, ff, 0f);

        dd = StrictMath.sin(Pi_4);
        errorCount += hh.checker("sin", Pi_4, dd, 0.7071067811865475d);
        dd = StrictMath.sin(-Pi_8);
        errorCount += hh.checker("sin", -Pi_8, dd, -0.3826834323650898d);

        dd = StrictMath.sqrt(Pi_4);
        errorCount += hh.checker("sin", Pi_4, dd, 0.8862269254527579d);

        ii = StrictMath.subtractExact(8, 3);
        errorCount += hh.checker("subtractExact", 8, 3, ii, 5);
        jj = StrictMath.subtractExact(8l, 3l);
        errorCount += hh.checker("subtractExact", 8l, 3l, jj, 5l);
 
 		double theta = 5d * Pi / 16d;
        dd = StrictMath.tan(theta);
        errorCount += hh.checker("tan", theta, dd, 1.496605762665489d);
        dd = StrictMath.tan(1d - theta);
        errorCount += hh.checker("tan", 1d - theta, dd, 0.01825432291826092d);

        dd = StrictMath.tanh(theta);
        errorCount += hh.checker("tanh", theta, dd, 0.7538214764025197d);
        dd = StrictMath.tanh(1d - theta);
        errorCount += hh.checker("tanh", 1d - theta, dd, 0.018250269128320434d);

        dd = StrictMath.toDegrees(Pi_2);
        errorCount += hh.checker("toDegrees", Pi_2, dd, 90d);
        dd = StrictMath.toDegrees(-Pi_2);
        errorCount += hh.checker("toDegrees", -Pi_2, dd, -90d);
        
        ii = StrictMath.toIntExact(1l);
        errorCount += hh.checker("toIntExact", 1l, ii, 1);
        ii = StrictMath.toIntExact(-1l);
        errorCount += hh.checker("toIntExact", -1l, ii, -1);

        dd = StrictMath.toRadians(45d);
        errorCount += hh.checker("toRadians", 45d, dd, Pi_4);
        dd = StrictMath.toRadians(-45d);
        errorCount += hh.checker("toRadians", 45d, dd, -Pi_4);

        dd = StrictMath.ulp(34.543d);
        errorCount += hh.checker("ulp", 34.543d, dd, 7.105427357601002e-15d);
        dd = StrictMath.ulp(-1178326541d);
        errorCount += hh.checker("ulp", -1178326541d, dd, 2.384185791015625e-07d);
 
        ff = StrictMath.ulp(123.45f);
        errorCount += hh.checker("ulp", 123.45f, ff, 7.6293945E-6f);
        ff = StrictMath.ulp(0.0f);
        errorCount += hh.checker("ulp", 0.0f, ff, 1.4E-45f);
 
        assert(errorCount == 0);

    }

}
