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
        errorCount += hh.checker("getExponent", 65537.0d, ii, 16);
        ii = Math.getExponent(1.01d);
        errorCount += hh.checker("getExponent", 1.01d, ii, 0);
        ii = Math.getExponent(0.0000013d);
        errorCount += hh.checker("getExponent", 0.0000013d, ii, -20);

        dd = Math.hypot(2.0d, 3.0d);
        errorCount += hh.checker("hypot", 2.0d, 3.0d, dd, 3.605551275463989d);

        dd = Math.IEEEremainder(65.4321d, 8.7654321d);
        errorCount += hh.checker("IEEEremainder", 65.4321d, 8.7654321d, dd, 4.0740753d);

        ii = Math.incrementExact(41);
        errorCount += hh.checker("incrementExact", 41, ii, 42);
        jj = Math.incrementExact(41l);
        errorCount += hh.checker("incrementExact", 41l, jj, 42l);

        dd = Math.log(0.1d);
        errorCount += hh.checker("log", 0.1d, dd, -2.3025850929940455d);
        dd = Math.log10(0.123456789d);
        errorCount += hh.checker("log10", 0.123456789d, dd, -0.9084850228307295d);
        dd = Math.log1p(0.123456789d);
        errorCount += hh.checker("log1p", 0.123456789d, dd, 0.11641035084441127d);

        dd = Math.max(65.4321d, 8.7654321d);
        errorCount += hh.checker("max", 65.4321d, 8.7654321d, dd, 65.4321d);
        ff = Math.max(65.4321f, 8.7654321f);
        errorCount += hh.checker("max", 65.4321f, 8.7654321f, ff, 65.4321f);
        ii = Math.max(65, 8);
        errorCount += hh.checker("max", 65, 8, ii, 65);
        jj = Math.max(65l, 8l);
        errorCount += hh.checker("max", 65l, 8l, jj, 65l);

        dd = Math.min(65.4321d, 8.7654321d);
        errorCount += hh.checker("min", 65.4321d, 8.7654321d, dd, 8.7654321d);
        ff = Math.min(65.4321f, 8.7654321f);
        errorCount += hh.checker("min", 65.4321f, 8.7654321f, ff, 8.7654321f);
        ii = Math.min(65, 8);
        errorCount += hh.checker("min", 65, 8, ii, 8);
        jj = Math.min(65l, 8l);
        errorCount += hh.checker("min", 65l, 8l, jj, 8l);

        ii = Math.multiplyExact(3, 2);
        errorCount += hh.checker("multiplyExact", 3, 2, ii, 6);
        jj = Math.multiplyExact(3l, 2);
        errorCount += hh.checker("multiplyExact", 3l, 2, jj, 6);
        jj = Math.multiplyExact(3l, 2l);
        errorCount += hh.checker("multiplyExact", 3l, 2l, jj, 6);

        jj = Math.multiplyFull(3, 2);
        errorCount += hh.checker("multiplyFull", 3, 2, jj, 6);
        jj = Math.multiplyFull(Integer.MAX_VALUE, Integer.MAX_VALUE);
        errorCount += hh.checker("multiplyFull", Integer.MAX_VALUE, Integer.MAX_VALUE, jj, 4611686014132420609l);

        jj1 = 45267356745l;
        jj2 = 45676556735l;
        jj = Math.multiplyHigh(jj1, jj2);
        errorCount += hh.checker("multiplyHigh", jj1, jj2, jj, 112l);
        jj1 = 9223372036854775801l;
        jj2 = 9223372036854775802l;
        jj = Math.multiplyHigh(jj1, jj2);
        errorCount += hh.checker("multiplyHigh", jj1, jj2, jj, 4611686018427387897l);
        jj1 = 9223372036854775807l;
        jj2 = 9223372036854775807l;
        jj = Math.multiplyHigh(jj1, jj2);
        errorCount += hh.checker("multiplyHigh", jj1, jj2, jj, 4611686018427387903l);

        ii = Math.negateExact(65);
        errorCount += hh.checker("negateExact", 65, ii, -65);
        jj = Math.negateExact(65l);
        errorCount += hh.checker("negateExact", 65l, jj, -65l);

        dd = Math.nextAfter(2.03d, 42.0d);
        errorCount += hh.checker("nextAfter", 2.03d, 42.0d, dd, 2.0300000000000002d);
        dd = Math.nextAfter(2.03d, -42.0d);
        errorCount += hh.checker("nextAfter", 2.03d, -42.0d, dd, 2.0299999999999994d);

        ff = Math.nextAfter(2.03f, 42.0f);
        errorCount += hh.checker("nextAfter", 2.03f, 42.0f, ff, 2.0300002f);
        ff = Math.nextAfter(2.03f, -42.0f);
        errorCount += hh.checker("nextAfter", 2.03f, -42.0f, ff, 2.0299997f);

        dd = Math.nextDown(2.03d);
        errorCount += hh.checker("nextDown", 2.03d, dd, 2.0299999999999994d);
        ff = Math.nextDown(2.03f);
        errorCount += hh.checker("nextDown", 2.03f, ff, 2.0299997f);

        dd = Math.nextUp(2.03d);
        errorCount += hh.checker("nextUp", 2.03d, dd, 2.0300000000000002d);
        ff = Math.nextUp(2.03f);
        errorCount += hh.checker("nextUp", 2.03f, ff, 2.0300002f);

        dd = Math.pow(2.03d, 3.01d);
        errorCount += hh.checker("pow", 2.03d, 3.01d, dd, 8.42486739873172d);

		for (int ix = 0; ix < 10; ++ix) {
		    dd = Math.random();
		    errorCount += hh.checkerRange("random", dd, 0.0, 1.0);
		}
		
        dd = Math.rint(2.49d);
        errorCount += hh.checker("rint", 2.49d, dd, 2.0d);
        dd = Math.rint(2.51d);
        errorCount += hh.checker("rint", 2.51d, dd, 3.0d);
        dd = Math.rint(-2.49d);
        errorCount += hh.checker("rint", -2.49d, dd, -2.0d);
        dd = Math.rint(-2.51d);
        errorCount += hh.checker("rint", -2.51d, dd, -3.0d);

        jj = Math.round(2.49d);
        errorCount += hh.checker("round", 2.49d, jj, 2l);
        jj = Math.round(2.51d);
        errorCount += hh.checker("round", 2.51d, jj, 3l);
        ii = Math.round(2.47f);
        errorCount += hh.checker("round", 2.47f, ii, 2);
        ii = Math.round(2.51f);
        errorCount += hh.checker("round", 2.51f, ii, 3);

        jj = Math.round(-2.49d);
        errorCount += hh.checker("round", -2.49d, jj, -2l);
        jj = Math.round(-2.51d);
        errorCount += hh.checker("round", -2.51d, jj, -3l);
        ii = Math.round(-2.47f);
        errorCount += hh.checker("round", -2.47f, ii, -2);
        ii = Math.round(-2.51f);
        errorCount += hh.checker("round", -2.51f, ii, -3);

        dd = Math.scalb(8d, 3);
        errorCount += hh.checker("scalb", 8d, 3, dd, 64d);
        ff = Math.scalb(8f, 3);
        errorCount += hh.checker("scalb", 8f, 3, ff, 64f);

        dd = Math.signum(0.001d);
        errorCount += hh.checker("signum", 0.001d, dd, 1d);
        ff = Math.signum(0.001f);
        errorCount += hh.checker("signum", 0.001f, ff, 1f);
        dd = Math.signum(-0.001d);
        errorCount += hh.checker("signum", -0.001d, dd, -1d);
        ff = Math.signum(-0.001f);
        errorCount += hh.checker("signum", -0.001f, ff, -1f);
        dd = Math.signum(0d);
        errorCount += hh.checker("signum", 0d, dd, 0d);
        ff = Math.signum(0f);
        errorCount += hh.checker("signum", 0f, ff, 0f);

        dd = Math.sin(Pi_4);
        errorCount += hh.checker("sin", Pi_4, dd, 0.7071067811865475d);
        dd = Math.sin(-Pi_8);
        errorCount += hh.checker("sin", -Pi_8, dd, -0.3826834323650898d);

        dd = Math.sqrt(Pi_4);
        errorCount += hh.checker("sin", Pi_4, dd, 0.8862269254527579d);

        ii = Math.subtractExact(8, 3);
        errorCount += hh.checker("subtractExact", 8, 3, ii, 5);
        jj = Math.subtractExact(8l, 3l);
        errorCount += hh.checker("subtractExact", 8l, 3l, jj, 5l);
 
 		double theta = 5d * Pi / 16d;
        dd = Math.tan(theta);
        errorCount += hh.checker("tan", theta, dd, 1.496605762665489d);
        dd = Math.tan(1d - theta);
        errorCount += hh.checker("tan", 1d - theta, dd, 0.01825432291826092d);

        dd = Math.tanh(theta);
        errorCount += hh.checker("tanh", theta, dd, 0.7538214764025197d);
        dd = Math.tanh(1d - theta);
        errorCount += hh.checker("tanh", 1d - theta, dd, 0.018250269128320434d);

        dd = Math.toDegrees(Pi_2);
        errorCount += hh.checker("toDegrees", Pi_2, dd, 90d);
        dd = Math.toDegrees(-Pi_2);
        errorCount += hh.checker("toDegrees", -Pi_2, dd, -90d);
        
        ii = Math.toIntExact(1l);
        errorCount += hh.checker("toIntExact", 1l, ii, 1);
        ii = Math.toIntExact(-1l);
        errorCount += hh.checker("toIntExact", -1l, ii, -1);

        dd = Math.toRadians(45d);
        errorCount += hh.checker("toRadians", 45d, dd, Pi_4);
        dd = Math.toRadians(-45d);
        errorCount += hh.checker("toRadians", 45d, dd, -Pi_4);

        dd = Math.ulp(34.543d);
        errorCount += hh.checker("ulp", 34.543d, dd, 7.105427357601002e-15d);
        dd = Math.ulp(-1178326541d);
        errorCount += hh.checker("ulp", -1178326541d, dd, 2.384185791015625e-07d);
 
        ff = Math.ulp(123.45f);
        errorCount += hh.checker("ulp", 123.45f, ff, 7.6293945E-6f);
        ff = Math.ulp(0.0f);
        errorCount += hh.checker("ulp", 0.0f, ff, 1.4E-45f);
 
        hh.byebye(errorCount);

    }

}
