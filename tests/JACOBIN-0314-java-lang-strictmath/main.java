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

        dd = StrictMath.abs(1.0d);
        errorCount += Checkers.withinTolerance("abs(1.0d)", 1.0d, dd);
        dd = StrictMath.abs(0.0d);
        errorCount += Checkers.withinTolerance("abs(0.0d)", 0.0d, dd);
        dd = StrictMath.abs(-1.0d);
        errorCount += Checkers.withinTolerance("abs(-1.0d)", 1.0d, dd);

        ff = StrictMath.abs(1.0f);
        errorCount += Checkers.withinTolerance("abs(1.0f)", 1.0d, (double) ff);
        ff = StrictMath.abs(0.0f);
        errorCount += Checkers.withinTolerance("abs(0.0f)", 0.0d, (double) ff);
        ff = StrictMath.abs(-1.0f);
        errorCount += Checkers.withinTolerance("abs(-1.0f)", 1.0d, (double) ff);

        ii = StrictMath.abs(1);
        errorCount += Checkers.withinTolerance("abs(1)", 1, ii);
        ii = StrictMath.abs(0);
        errorCount += Checkers.withinTolerance("abs(0)", 0, ii);
        ii = StrictMath.abs(-1);
        errorCount += Checkers.withinTolerance("abs(-1)", 1, ii);

        jj = StrictMath.abs(1L);
        errorCount += Checkers.withinTolerance("abs(1l)", 1l, jj);
        jj = StrictMath.abs(0L);
        errorCount += Checkers.withinTolerance("abs(0l)", 0l, jj);
        jj = StrictMath.abs(-1L);
        errorCount += Checkers.withinTolerance("abs(-1l)", 1l, jj);

        ii = StrictMath.absExact(1);
        errorCount += Checkers.withinTolerance("absExact(1)", 1, ii);
        ii = StrictMath.absExact(0);
        errorCount += Checkers.withinTolerance("absExact(0)", 0, ii);
        ii = StrictMath.absExact(-1);
        errorCount += Checkers.withinTolerance("absExact(-1)", 1, ii);

        jj = StrictMath.absExact(1L);
        errorCount += Checkers.withinTolerance("absExact(1l)", 1l, jj);
        jj = StrictMath.absExact(0L);
        errorCount += Checkers.withinTolerance("absExact(0l)", 0l, jj);
        jj = StrictMath.absExact(-1L);
        errorCount += Checkers.withinTolerance("absExact(-1l)", 1l, jj);

        dd = StrictMath.acos(-1.0d);
        errorCount += Checkers.withinTolerance("acos(-1.0d)", 3.141592653589793d, dd);
        dd = StrictMath.acos(0.5d);
        errorCount += Checkers.withinTolerance("acos(0.5d)", 1.047197551d, dd);

        ii = StrictMath.addExact(2, 3);
        errorCount += Checkers.withinTolerance("addExact(2, 3)", 5, ii);
        jj = StrictMath.addExact(2l, 3l);
        errorCount += Checkers.withinTolerance("addExact(2l, 3l)", 5l, jj);
        
        dd = StrictMath.asin(-1.0d);
        errorCount += Checkers.withinTolerance("asin(-1.0d)", -1.5707963267948966d, dd );
        dd = StrictMath.asin(0.5d);
        errorCount += Checkers.withinTolerance("asin(0.5d)", 0.5235987755982989d, dd);

        dd = StrictMath.atan(-1.0d);
        errorCount += Checkers.withinTolerance("atan(-1.0d)", -0.7853981633974483d, dd);
        dd = StrictMath.atan(0.5d);
        errorCount += Checkers.withinTolerance("atan(0.5d)", 0.4636476090008061d, dd);

        dd = StrictMath.atan2(2.0d, 3.0d);
        errorCount += Checkers.withinTolerance("atan2(2.0d, 3.0d)", 0.5880026035475675d, dd);
        dd = StrictMath.atan2(-2.0d, 3.0d);
        errorCount += Checkers.withinTolerance("atan2(-2.0d, 3.0d)", -0.5880026035475675d, dd);
        dd = StrictMath.atan2(2.0d, -3.0d);
        errorCount += Checkers.withinTolerance("atan2(2.0d, -3.0d)", 2.5535900500422257d, dd);

        dd = StrictMath.cbrt(-5.0d);
        errorCount += Checkers.withinTolerance("cbrt(-5.0d)", -1.7099759466766968d, dd);

        dd = StrictMath.ceil(-5.3d);
        errorCount += Checkers.withinTolerance("ceil(-5.3d)", -5.0d, dd);
        dd = StrictMath.ceil(0.001d);
        errorCount += Checkers.withinTolerance("ceil(0.001d)", 1.0d, dd);
        dd = StrictMath.ceil(3.9d);
        errorCount += Checkers.withinTolerance("ceil(3.9d)", 4.0d, dd);

        dd = StrictMath.copySign(-1.0d, 2.3d);
        errorCount += Checkers.withinTolerance("copySign(-1.0d, 2.3d)", 1.0d, dd);
        dd = StrictMath.copySign(-1.0d, -2.3d);
        errorCount += Checkers.withinTolerance("copySign(-1.0d, -2.3d)", -1.0d, dd);
        ff = StrictMath.copySign(-1.0f, 2.3f);
        errorCount += Checkers.withinTolerance("copySign(-1.0f, 2.3f)", 1.0d, (double) ff);
        ff = StrictMath.copySign(-1.0f, -2.3f);
        errorCount += Checkers.withinTolerance("copySign(-1.0f, -2.3f)", -1.0d, (double) ff);

        dd = StrictMath.cos(Pi);
        errorCount += Checkers.withinTolerance("cos(Pi)", -1.0d, dd);
        dd = StrictMath.cos(-0.5);
        errorCount += Checkers.withinTolerance("cos(-0.5)", 0.8775825618903728d, dd);

        dd = StrictMath.cosh(-0.1);
        errorCount += Checkers.withinTolerance("cosh(-0.1)", 1.0050041680558035d, dd);
        dd = StrictMath.cosh(0.5);
        errorCount += Checkers.withinTolerance("cosh(0.5)", 1.1276259652063807d, dd);

        ii = StrictMath.decrementExact(43);
        errorCount += Checkers.withinTolerance("decrementExact(43)", 42, ii);
        jj = StrictMath.decrementExact(43l);
        errorCount += Checkers.withinTolerance("decrementExact(43l)", 42l, jj);

        dd = StrictMath.exp(-0.1);
        errorCount += Checkers.withinTolerance("exp(-0.1)", 0.9048374180359595d, dd);
        dd = StrictMath.expm1(-0.1);
        errorCount += Checkers.withinTolerance("expm1(-0.1)", -0.09516258196404043d, dd);

        dd = StrictMath.floor(-5.3d);
        errorCount += Checkers.withinTolerance("floor(-5.3d)", -6.0d, dd);
        dd = StrictMath.floor(0.001d);
        errorCount += Checkers.withinTolerance("floor(0.001d)", 0.0d, dd);
        dd = StrictMath.floor(3.9d);
        errorCount += Checkers.withinTolerance("floor(3.9d)", 3.0d, dd);

        ii = StrictMath.floorDiv(-5, 2);
        errorCount += Checkers.withinTolerance("floorDiv(-5, 2)", -3, ii);
        jj = StrictMath.floorDiv(-5l, -2);
        errorCount += Checkers.withinTolerance("floorDiv(-5l, -2)", 2l, jj);
        jj = StrictMath.floorDiv(5l, -2l);
        errorCount += Checkers.withinTolerance("floorDiv(5l, -2l)", -3l, jj);

        ii = StrictMath.floorMod(-5, 2);
        errorCount += Checkers.withinTolerance("floorMod(-5, 2)", 1l, ii);
        ii = StrictMath.floorMod(-5l, -2);
        errorCount += Checkers.withinTolerance("floorMod(-5l, -2)", -1, ii);
        jj = StrictMath.floorMod(5l, -2l);
        errorCount += Checkers.withinTolerance("floorMod(5l, -2l)", -1l, jj);

        dd = StrictMath.fma(-5.3d, 2.2d, -1.2d);
        errorCount += Checkers.withinTolerance("fma(-5.3d, 2.2d, -1.2d)", -12.86d, dd);
        ff = StrictMath.fma(-5.3f, 2.2f, -1.2f);
        errorCount += Checkers.withinTolerance("fma(-5.3f, 2.2f, -1.2f)", -12.86d, (double) ff);
        
        ii = StrictMath.getExponent(65537.0d);
        errorCount += Checkers.withinTolerance("getExponent(65537.0d)", 16, ii);
        ii = StrictMath.getExponent(1.01d);
        errorCount += Checkers.withinTolerance("getExponent(1.01d)", 0, ii);
        ii = StrictMath.getExponent(0.0000013d);
        errorCount += Checkers.withinTolerance("getExponent(0.0000013d)", -20, ii);

        dd = StrictMath.hypot(2.0d, 3.0d);
        errorCount += Checkers.withinTolerance("hypot(2.0d, 3.0d)", 3.605551275463989d, dd);

        dd = StrictMath.IEEEremainder(65.4321d, 8.7654321d);
        errorCount += Checkers.withinTolerance("IEEEremainder(65.4321d, 8.7654321d)", 4.0740753d, dd);

        ii = StrictMath.incrementExact(41);
        errorCount += Checkers.withinTolerance("incrementExact(41)", 42, ii);
        jj = StrictMath.incrementExact(41l);
        errorCount += Checkers.withinTolerance("incrementExact(41l)", 42l, jj);

        dd = StrictMath.log(0.1d);
        errorCount += Checkers.withinTolerance("log(0.1d)", -2.3025850929940455d, dd);
        dd = StrictMath.log10(0.123456789d);
        errorCount += Checkers.withinTolerance("log10(0.123456789d)", -0.9084850228307295d, dd);
        dd = StrictMath.log1p(0.123456789d);
        errorCount += Checkers.withinTolerance("log1p(0.123456789d)", 0.11641035084441127d, dd);

        dd = StrictMath.max(65.4321d, 8.7654321d);
        errorCount += Checkers.withinTolerance("max(65.4321d, 8.7654321d)", 65.4321d, dd);
        ff = StrictMath.max(65.4321f, 8.7654321f);
        errorCount += Checkers.withinTolerance("max(65.4321f, 8.7654321f)", 65.4321d, (double) ff);
        ii = StrictMath.max(65, 8);
        errorCount += Checkers.withinTolerance("max(65, 8)", 65, ii);
        jj = StrictMath.max(65l, 8l);
        errorCount += Checkers.withinTolerance("max(65l, 8l)", 65l, jj);

        dd = StrictMath.min(65.4321d, 8.7654321d);
        errorCount += Checkers.withinTolerance("min(65.4321d, 8.7654321d)", 8.7654321d, dd);
        ff = StrictMath.min(65.4321f, 8.7654321f);
        errorCount += Checkers.withinTolerance("min(65.4321f, 8.7654321f)", 8.7654321d, (double) ff);
        ii = StrictMath.min(65, 8);
        errorCount += Checkers.withinTolerance("min(65, 8)", 8, ii);
        jj = StrictMath.min(65l, 8l);
        errorCount += Checkers.withinTolerance("min(65l, 8l)", 8l, jj);

        ii = StrictMath.multiplyExact(3, 2);
        errorCount += Checkers.withinTolerance("multiplyExact(3, 2)", 6, ii);
        jj = StrictMath.multiplyExact(3l, 2);
        errorCount += Checkers.withinTolerance("multiplyExact(3l, 2)", 6l, jj);
        jj = StrictMath.multiplyExact(3l, 2l);
        errorCount += Checkers.withinTolerance("multiplyExact(3l, 2l)", 6l, jj);

        jj = StrictMath.multiplyFull(3, 2);
        errorCount += Checkers.withinTolerance("multiplyFull(3, 2)", 6, jj);
        jj = StrictMath.multiplyFull(Integer.MAX_VALUE, Integer.MAX_VALUE);
        errorCount += Checkers.withinTolerance("multiplyFull(Integer.MAX_VALUE, Integer.MAX_VALUE)", 4611686014132420609l, jj);

        jj1 = 45267356745l;
        jj2 = 45676556735l;
        jj = StrictMath.multiplyHigh(jj1, jj2);
        errorCount += Checkers.withinTolerance("multiplyHigh", 112l, jj);
        jj1 = 9223372036854775801l;
        jj2 = 9223372036854775802l;
        jj = StrictMath.multiplyHigh(jj1, jj2);
        errorCount += Checkers.withinTolerance("multiplyHigh", 4611686018427387897l, jj);
        jj1 = 9223372036854775807l;
        jj2 = 9223372036854775807l;
        jj = StrictMath.multiplyHigh(jj1, jj2);
        errorCount += Checkers.withinTolerance("multiplyHigh", 4611686018427387903l, jj);

        ii = StrictMath.negateExact(65);
        errorCount += Checkers.withinTolerance("negateExact(65)", -65, ii);
        jj = StrictMath.negateExact(65l);
        errorCount += Checkers.withinTolerance("negateExact(65l)", -65l, jj);

        dd = StrictMath.nextAfter(2.03d, 42.0d);
        errorCount += Checkers.withinTolerance("nextAfter(2.03d, 42.0d)", 2.0300000000000002d, dd);
        dd = StrictMath.nextAfter(2.03d, -42.0d);
        errorCount += Checkers.withinTolerance("nextAfter(2.03d, -42.0d)", 2.0299999999999994d, dd);

        ff = StrictMath.nextAfter(2.03f, 42.0f);
        errorCount += Checkers.withinTolerance("nextAfter(2.03f, 42.0f)", 2.0300002d, (double) ff);
        ff = StrictMath.nextAfter(2.03f, -42.0f);
        errorCount += Checkers.withinTolerance("nextAfter(2.03f, -42.0f)", 2.0299997d, (double) ff);

        dd = StrictMath.nextDown(2.03d);
        errorCount += Checkers.withinTolerance("nextDown(2.03d)", 2.0299999999999994d, dd);
        ff = StrictMath.nextDown(2.03f);
        errorCount += Checkers.withinTolerance("nextDown(2.03f)", 2.0299997d, (double) ff);

        dd = StrictMath.nextUp(2.03d);
        errorCount += Checkers.withinTolerance("nextUp(2.03d)", 2.0300000000000002d, dd);
        ff = StrictMath.nextUp(2.03f);
        errorCount += Checkers.withinTolerance("nextUp(2.03f)", 2.0300002d, (double) ff);

        dd = StrictMath.pow(2.03d, 3.01d);
        errorCount += Checkers.withinTolerance("pow(2.03d, 3.01d)", 8.42486739873172d, dd);

        dd = StrictMath.rint(2.49d);
        errorCount += Checkers.withinTolerance("rint(2.49d)", 2.0d, dd);
        dd = StrictMath.rint(2.51d);
        errorCount += Checkers.withinTolerance("rint(2.51d)", 3.0d, dd);
        dd = StrictMath.rint(-2.49d);
        errorCount += Checkers.withinTolerance("rint(-2.49d)", -2.0d, dd);
        dd = StrictMath.rint(-2.51d);
        errorCount += Checkers.withinTolerance("rint(-2.51d)", -3.0d, dd);

        jj = StrictMath.round(2.49d);
        errorCount += Checkers.withinTolerance("round(2.49d)", 2l, jj);
        jj = StrictMath.round(2.51d);
        errorCount += Checkers.withinTolerance("round(2.51d)", 3l, jj);
        ii = StrictMath.round(2.47f);
        errorCount += Checkers.withinTolerance("round(2.47f)", 2, ii);
        ii = StrictMath.round(2.51f);
        errorCount += Checkers.withinTolerance("round(2.51f)", 3, ii);

        jj = StrictMath.round(-2.49d);
        errorCount += Checkers.withinTolerance("round(-2.49d)", -2l, jj);
        jj = StrictMath.round(-2.51d);
        errorCount += Checkers.withinTolerance("round(-2.51d)", -3l, jj);
        ii = StrictMath.round(-2.47f);
        errorCount += Checkers.withinTolerance("round(-2.47f)", -2, ii);
        ii = StrictMath.round(-2.51f);
        errorCount += Checkers.withinTolerance("round(-2.51f)", -3, ii);

        dd = StrictMath.scalb(8d, 3);
        errorCount += Checkers.withinTolerance("scalb(8d, 3)", 64d, dd);
        ff = StrictMath.scalb(8f, 3);
        errorCount += Checkers.withinTolerance("scalb(8f, 3)", 64d, (double) ff);

        dd = StrictMath.signum(0.001d);
        errorCount += Checkers.withinTolerance("signum(0.001d)", 1d, dd);
        ff = StrictMath.signum(0.001f);
        errorCount += Checkers.withinTolerance("signum(0.001f)", 1d, (double) ff);
        dd = StrictMath.signum(-0.001d);
        errorCount += Checkers.withinTolerance("signum(-0.001d)", -1d, dd);
        ff = StrictMath.signum(-0.001f);
        errorCount += Checkers.withinTolerance("signum(-0.001f)", -1f, (double) ff);
        dd = StrictMath.signum(0d);
        errorCount += Checkers.withinTolerance("signum(0d)", 0d, dd);
        ff = StrictMath.signum(0f);
        errorCount += Checkers.withinTolerance("signum(0f)", 0d, (double) ff);

        dd = StrictMath.sin(Pi_4);
        errorCount += Checkers.withinTolerance("sin(Pi_4)", 0.7071067811865475d, dd);
        dd = StrictMath.sin(-Pi_8);
        errorCount += Checkers.withinTolerance("sin(-Pi_8)", -0.3826834323650898d, dd);

        dd = StrictMath.sqrt(Pi_4);
        errorCount += Checkers.withinTolerance("sqrt(Pi_4)", 0.8862269254527579d, dd);

        ii = StrictMath.subtractExact(8, 3);
        errorCount += Checkers.withinTolerance("subtractExact(8, 3)", 5, ii);
        jj = StrictMath.subtractExact(8l, 3l);
        errorCount += Checkers.withinTolerance("subtractExact(8l, 3l)", 5l, jj);
 
 		double theta = 5d * Pi / 16d;
        dd = StrictMath.tan(theta);
        errorCount += Checkers.withinTolerance("tan(5d * Pi / 16d)", 1.496605762665489d, dd);
        dd = StrictMath.tan(1d - theta);
        errorCount += Checkers.withinTolerance("tan(1 - (5d * Pi / 16d))", 0.01825432291826092d, dd);

        dd = StrictMath.tanh(theta);
        errorCount += Checkers.withinTolerance("tanh(5d * Pi / 16d)", 0.7538214764025197d, dd);
        dd = StrictMath.tanh(1d - theta);
        errorCount += Checkers.withinTolerance("tanh(1 - (5d * Pi / 16d))", 0.018250269128320434d, dd);

        dd = StrictMath.toDegrees(Pi_2);
        errorCount += Checkers.withinTolerance("toDegrees(Pi_2)", 90d, dd);
        dd = StrictMath.toDegrees(-Pi_2);
        errorCount += Checkers.withinTolerance("toDegrees(-Pi_2)", -90d, dd);
        
        ii = StrictMath.toIntExact(1l);
        errorCount += Checkers.withinTolerance("toIntExact(1l)", 1, ii);
        ii = StrictMath.toIntExact(-1l);
        errorCount += Checkers.withinTolerance("toIntExact(-1l)", -1, ii);

        dd = StrictMath.toRadians(45d);
        errorCount += Checkers.withinTolerance("toRadians(45d)", Pi_4, dd);
        dd = StrictMath.toRadians(-45d);
        errorCount += Checkers.withinTolerance("toRadians(-45d)", -Pi_4, dd);

        dd = StrictMath.ulp(34.543d);
        errorCount += Checkers.withinTolerance("ulp(34.543d)", 7.105427357601002e-15d, dd);
        dd = StrictMath.ulp(-1178326541d);
        errorCount += Checkers.withinTolerance("ulp(-1178326541d)", 2.384185791015625e-07d, dd);
 
        ff = StrictMath.ulp(123.45f);
        errorCount += Checkers.withinTolerance("ulp(123.45f)", 7.6293945E-6d, ff);
        ff = StrictMath.ulp(0.0f);
        errorCount += Checkers.withinTolerance("ulp(0.0f)", 1.401298e-45, (double) ff);
 
        Checkers.theEnd(errorCount);

    }

}
