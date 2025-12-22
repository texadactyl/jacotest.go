import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class main {

    // ---- Table-driven velocity tests ----
    static final BigDecimal[] TEST_VELOCITIES = {
            new BigDecimal("0.001"),
            new BigDecimal("0.943"),
            new BigDecimal("0.5"),
            new BigDecimal("0.1"),
            new BigDecimal("-0.5"),
            
            new BigDecimal("-1.2"),  // expected failure (negative too fast)
            new BigDecimal("1.0"),   // expected failure
            new BigDecimal("1.5")    // expected failure
    };

    // ---- Math configuration ----
    static final MathContext MC = new MathContext(20, RoundingMode.HALF_UP);
    static final int SCALE = 12;
    static final RoundingMode RM = RoundingMode.HALF_UP;

    // ---- Constants ----
    static final BigDecimal C   = BigDecimal.ONE;           // speed of light in LY/year
    static final BigDecimal CSQ = C.multiply(C, MC);        // c^2
    static final BigDecimal HR_PER_YEAR =
            new BigDecimal("365.2425").multiply(new BigDecimal("24"), MC);
    static final BigDecimal MAX_PERCENT =
            new BigDecimal("0.000001"); // tolerance for comparisons

    static int errorCount = 0;

    // ---- Observer (frame of reference) ----
    static class Observer {
        String name;
        BigDecimal thisClockAtStar;
        BigDecimal thatClockAtStar;

        Observer(String name,
                 BigDecimal velocity,
                 BigDecimal distanceToStar,
                 boolean isStarMoving) {

            this.name = name;

            BigDecimal gamma = velocityToGamma(velocity);

            // ---- Effective distance ----
            BigDecimal effectiveDistance = distanceToStar;
            if (isStarMoving) {
                System.out.printf("%n%s: The star is moving.%n", name);
                effectiveDistance = distanceToStar.divide(gamma, MC);
            } else {
                System.out.printf("%n%s: The star is NOT moving.%n", name);
            }

            // Negative velocities: reverse distance
            if (velocity.signum() < 0) {
                effectiveDistance = effectiveDistance.negate();
            }

            System.out.printf("%s: velocity = %s LY/year, gamma = %s%n",
                    name, velocity.toPlainString(), gamma.toPlainString());
            System.out.printf("%s: Effective distance to star = %s LY%n",
                    name, effectiveDistance.toPlainString());

            // ---- Clock readings ----
            thisClockAtStar = effectiveDistance.divide(velocity, MC);
            System.out.printf("%s: This clock at star = %s years%n",
                    name, thisClockAtStar.toPlainString());

            thatClockAtStar = thisClockAtStar.divide(gamma, MC);
            System.out.printf("%s: That clock at star = %s years%n",
                    name, thatClockAtStar.toPlainString());

            BigDecimal diffYr = thisClockAtStar.subtract(thatClockAtStar, MC).abs();
            BigDecimal diffHr = diffYr.multiply(HR_PER_YEAR, MC);

            System.out.printf("%s: Clock difference = %s years = %s hr%n",
                    name, diffYr.toPlainString(), diffHr.toPlainString());
        }

        // ---- Gamma factor calculation ----
        BigDecimal velocityToGamma(BigDecimal velocity) {
            BigDecimal vSquared = velocity.multiply(velocity, MC);
            BigDecimal fraction = vSquared.divide(CSQ, MC);
            BigDecimal inside = BigDecimal.ONE.subtract(fraction, MC);
            BigDecimal sqrtInside = sqrt(inside, MC);
            return BigDecimal.ONE.divide(sqrtInside, MC);
        }
    }

    // ---- Test runner ----
    static void tryVelocity(BigDecimal velocity) {
        System.out.printf("%nTry velocity = %s LY/year%n", velocity.toPlainString());

        // Pre-check for invalid velocities (diagnosed expected failures)
        if (velocity.abs().compareTo(C) >= 0) {
            String direction = velocity.signum() < 0 ? "unphysical negative" : "too fast positive";
            String msg = String.format("*** Expected failure diagnosed: %s velocity %s exceeds speed of light",
                    direction, velocity.toPlainString());
            System.out.println(msg);
            return; // skip further processing, do NOT increment errorCount
        }

        BigDecimal distanceToStar = new BigDecimal("5.0");

        Observer earth  = new Observer("Earth",  velocity, distanceToStar, false);
        Observer rocket = new Observer("Rocket", velocity, distanceToStar, true);

        // Leading clock lag (absolute value for reporting)
        BigDecimal lagYrRaw = distanceToStar.multiply(velocity, MC).divide(CSQ, MC);
        BigDecimal lagYr = lagYrRaw.abs();
        BigDecimal lagHr = lagYr.multiply(HR_PER_YEAR, MC);

        String lagDirection = velocity.signum() < 0 ? "approaching" : "receding";
        System.out.printf("%nLeading clock lag (%s) = %s years = %s hr%n",
                lagDirection, lagYr.toPlainString(), lagHr.toPlainString()
        );

        // ---- Correct signed lag for comparison ----
        BigDecimal signedLag = velocity.signum() < 0 ? lagYrRaw.negate() : lagYrRaw;

        BigDecimal expected = earth.thisClockAtStar.setScale(SCALE, RM);
        BigDecimal observed = rocket.thatClockAtStar.add(signedLag, MC).setScale(SCALE, RM);

        String label = String.format("Clock@Star Earth vs Rocket (%s, velocity=%s)",
                lagDirection, velocity.toPlainString());

        errorCount += Checkers.withinTolerance(label, expected, observed, MAX_PERCENT);

        System.out.println("-".repeat(64));
    }

    // ---- BigDecimal square root (Newton-Raphson) ----
    static BigDecimal sqrt(BigDecimal arg, MathContext mc) {
        BigDecimal guess = new BigDecimal(Math.sqrt(arg.doubleValue()), mc);
        BigDecimal TWO = new BigDecimal("2");

        for (int i = 0; i < 10; i++) {
            guess = guess.add(arg.divide(guess, mc)).divide(TWO, mc);
        }
        return guess;
    }

    // ---- Main ----
    public static void main(String[] args) {
        for (BigDecimal v : TEST_VELOCITIES) {
            tryVelocity(v);
        }

        Checkers.theEnd(errorCount);
    }
}

