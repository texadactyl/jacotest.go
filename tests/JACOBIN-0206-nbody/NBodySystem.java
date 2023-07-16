public class NBodySystem {

  private final double PI = 3.141592653589793;
  private final double SOLAR_MASS = 4 * PI * PI;
  private final double DAYS_PER_YEAR = 365.24;
  private final int BODY_SIZE = 8;
  private final int BODY_COUNT = 5;

  private final int x = 0;
  private final int y = 1;
  private final int z = 2;
  private final int vx = 3;
  private final int vy = 4;
  private final int vz = 5;
  private final int mass = 6;
  private final double EPSILON = 1e-15;    // relative error tolerance

  private final double[] outerSSBodies = {
        //sun begin
        0, 0, 0, 0, 0, 0, SOLAR_MASS, 0,
        //sun end

        //jupiter begin
        4.84143144246472090e+00,//
        -1.16032004402742839e+00,//
        -1.03622044471123109e-01,//
        1.66007664274403694e-03 * DAYS_PER_YEAR,//
        7.69901118419740425e-03 * DAYS_PER_YEAR,//
        -6.90460016972063023e-05 * DAYS_PER_YEAR,//
        9.54791938424326609e-04 * SOLAR_MASS,//
        0,
        //jupiter end

        //saturn begin
        8.34336671824457987e+00,//
        4.12479856412430479e+00,//
        -4.03523417114321381e-01,//
        -2.76742510726862411e-03 * DAYS_PER_YEAR,//
        4.99852801234917238e-03 * DAYS_PER_YEAR,//
        2.30417297573763929e-05 * DAYS_PER_YEAR,//
        2.85885980666130812e-04 * SOLAR_MASS,//
        0,
        //saturn end

        //uranus begin
        1.28943695621391310e+01,//
        -1.51111514016986312e+01,//
        -2.23307578892655734e-01,//
        2.96460137564761618e-03 * DAYS_PER_YEAR,//
        2.37847173959480950e-03 * DAYS_PER_YEAR,//
        -2.96589568540237556e-05 * DAYS_PER_YEAR,//
        4.36624404335156298e-05 * SOLAR_MASS,//
        0,
        //uranus end

        //neptune begin
        1.53796971148509165e+01,//
        -2.59193146099879641e+01,//
        1.79258772950371181e-01,//
        2.68067772490389322e-03 * DAYS_PER_YEAR,//
        1.62824170038242295e-03 * DAYS_PER_YEAR,//
        -9.51592254519715870e-05 * DAYS_PER_YEAR,//
        5.15138902046611451e-05 * SOLAR_MASS, //
        0
        //neptune end
  };

  public NBodySystem() {
     double px = 0.0;
     double py = 0.0;
     double pz = 0.0;
     int ioffset;
     double imass;

     for (int i = 0; i < BODY_COUNT; ++i) {
        ioffset = BODY_SIZE * i;
        imass = outerSSBodies[ioffset + mass];

        px += outerSSBodies[ioffset + vx] * imass;
        py += outerSSBodies[ioffset + vy] * imass;
        pz += outerSSBodies[ioffset + vz] * imass;
     }

     outerSSBodies[vx] = -px / SOLAR_MASS;
     outerSSBodies[vy] = -py / SOLAR_MASS;
     outerSSBodies[vz] = -pz / SOLAR_MASS;
  }

	public double absValue(double arg) {
		if (arg < 0.0)
			return -arg;
		return arg;
	}

  public void advance(double dt) {
     final double[] bodies = outerSSBodies;

     for (int i = 0; i < BODY_COUNT; ++i) {
        final int offset = BODY_SIZE * i;

        for (int j = i + 1; j < BODY_COUNT; ++j) {
           final int ioffset = offset;
           final int joffset = BODY_SIZE * j;

           final double dx = bodies[ioffset + x] - bodies[joffset + x];
           final double dy = bodies[ioffset + y] - bodies[joffset + y];
           final double dz = bodies[ioffset + z] - bodies[joffset + z];

           final double dSquared = dx * dx + dy * dy + dz * dz;
           final double distance = Math.sqrt(dSquared);
           final double mag = dt / (dSquared * distance);

           final double jmass = bodies[joffset + mass];

           bodies[ioffset + vx] -= dx * jmass * mag;
           bodies[ioffset + vy] -= dy * jmass * mag;
           bodies[ioffset + vz] -= dz * jmass * mag;

           final double imass = bodies[ioffset + mass];
           bodies[joffset + vx] += dx * imass * mag;
           bodies[joffset + vy] += dy * imass * mag;
           bodies[joffset + vz] += dz * imass * mag;
        }
     }

     for (int i = 0; i < BODY_COUNT; ++i) {
        final int ioffset = BODY_SIZE * i;

        bodies[ioffset + x] += dt * bodies[ioffset + vx];
        bodies[ioffset + y] += dt * bodies[ioffset + vy];
        bodies[ioffset + z] += dt * bodies[ioffset + vz];
     }
  }

  public double energy() {
     final double[] bodies = outerSSBodies;

     double dx, dy, dz, distance;
     double e = 0.0;

     for (int i = 0; i < BODY_COUNT; ++i) {
        final int offset = BODY_SIZE * i;

        final double ivx = bodies[offset + vx];
        final double ivy = bodies[offset + vy];
        final double ivz = bodies[offset + vz];
        final double imass = bodies[offset + mass];

        e += 0.5 * imass * (ivx * ivx + ivy * ivy + ivz * ivz);

        for (int j = i + 1; j < BODY_COUNT; ++j) {
           final int ioffset = offset;
           final int joffset = BODY_SIZE * j;

           final double ix = bodies[ioffset + x];
           final double iy = bodies[ioffset + y];
           final double iz = bodies[ioffset + z];

           dx = ix - bodies[joffset + x];
           dy = iy - bodies[joffset + y];
           dz = iz - bodies[joffset + z];

           distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
           e -= (imass * bodies[joffset + mass]) / distance;
        }
     }

     return e;
  }

}

