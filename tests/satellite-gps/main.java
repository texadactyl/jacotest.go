/*
    Given a satellite orbital radius and a ground receiver on Earth (latitude/longitude), 
    compute the relativistic clock difference (elapsed time) between the satellite clock and an Earth-surface clock.
    
    This simplified program is already enough to reproduce the famous ~38.5 μs/day GPS correction and the ~40 μs/day GEO correction.
    
    Missing factors:
    
    * Earth's oblateness (J2 term)
    * eccentric orbit corrections
    * gravitational potential of the Sun and Moon
    * frame-dragging (Lense–Thirring effect, tiny)

    One subtle point in the GPS calculation: 
    The satellite's velocity is not measured relative to the Earth's surface. 
    It is measured relative to an inertial frame centered in the Earth (approximately the Earth-centered inertial frame). 
    The origin of the coordinate system is at the Earth's center of mass (which is extremely close to the geometric center).
    The Earth clock also has a velocity due to Earth's rotation, which is why the GPS correction uses the difference between 
    the satellite's v*v and the ground receiver's v*v.
    
    Hotspot JVM output:
    
    Orbit radius: 26571 km
    Latitude: 40.00 degrees
    Satellite velocity: 3.873 km/s
    Earth surface velocity: 355.889 m/s
    Clock difference after 1.0 days: 38.575 microseconds

    Orbit radius: 42157 km
    Latitude: 40.00 degrees
    Satellite velocity: 3.075 km/s
    Earth surface velocity: 355.889 m/s
    Clock difference after 1.0 days: 46.572 microseconds
*/

public class main {

    static int errorCount = 0;

    // Constants
    static final double G = 6.67430e-11;            // Newton: m^3 kg^-1 s^-2
    static final double M_EARTH = 5.97219e24;       // Mass of Earth in kg
    static final double R_EARTH = 6371000.0;        // Average radius of Earth in meters
    static final double C = 299792458.0;            // Lightspeed in m/s
    static final double TWO_CSQ = 2.0 * C * C;      // Just what it says
    static final double RV_EARTH = 7.2921150e-5;    // Earth rotational velocity in radians/sec
    static final double SECS_PER_DAY = 86400.0;     // Seconds per Earth day

    /**
     * Compute the relativistic clock difference in microseconds.
     *
     * positive = satellite clock runs faster from Earth point of view (expected)
     *
     * @param orbitRadiusMeters distance from Earth's center
     * @param latitudeDegrees latitude of Earth observer
     * @param days elapsed time
     * @param expected clock difference in microseconds
     */
    static void compute(
            double orbitRadiusMeters,
            double latitudeDegrees,
            double days,
            double expected) {

        double totalSeconds = days * SECS_PER_DAY;

        // ----- General Relativity: Compute the gravitational potential difference between the Earth's surface and the satellite.
        // grav = the fractional change in clock rate caused by the difference in gravitational potential between the Earth's surface and the satellite.

        double grav = (G * M_EARTH / (C*C)) * ( ( 1.0 / R_EARTH) - (1.0 / orbitRadiusMeters ) );

        // ----- Satellite circular orbit velocity ----
        
        double satelliteVelocity = Math.sqrt(G * M_EARTH / orbitRadiusMeters);

        // ----- Exact Special Relativity result: deltaT[moving] = deltaT[stationary] * sqrt(1 - (v*v / C*C))
        // where v = velocity of the moving clock.
        // Note that the moving clock runs slower than the stationary clock from Earth's point of view.
        //
        // For satellites, the velocity is only a few km/s, which is tiny compared with the speed of light (~300,000 km/s). 
        // So, we can use a Taylor expansion:: sqrt(1 - x) is roughly 1 - (x / 2) where x = v*v / C*C
        //
        // The fractional difference is therefore: (deltaT[moving] - deltaT[stationary]) / deltaT[stationary] = -(v*v / 2*C*C)
        // Note that the moving clock loses time compared with a clock at rest, hence, negative.
        //
        // We have to compute the fractional difference for both the satellite and the ground receiver.

        // ----- Compute the fractional difference for the satellite.
        
        double fracDiffSatellite = -(satelliteVelocity * satelliteVelocity) / TWO_CSQ;

        // ----- Earth surface velocity ----
        
        double latitudeRadians = Math.toRadians(latitudeDegrees);
        double earthVelocity = RV_EARTH * R_EARTH * Math.cos(latitudeRadians);
        
        // ----- Compute the fractional difference for the ground receiver.
        
        double fracDiffSurface = -(earthVelocity * earthVelocity) / TWO_CSQ;

        // ----- Difference in seconds between satellite and Earth clock, considering GR and SR.

        double totalFraction = grav + (fracDiffSatellite - fracDiffSurface);
        double differenceSeconds = totalFraction * totalSeconds;

        // ----- Report and compare to expected value.
        
        System.out.printf("\nOrbit radius: %.0f km%n", orbitRadiusMeters / 1000.0);
        System.out.printf("Latitude: %.2f degrees%n", latitudeDegrees);
        System.out.printf("Satellite velocity: %.6f km/s%n", satelliteVelocity / 1000.0);
        System.out.printf("Earth surface velocity: %.6f m/s%n", earthVelocity);
        System.out.printf("Clock difference after %.1f days: %.6f microseconds%n", days, differenceSeconds * 1e6);
        errorCount += Checkers.withinTolerance("Clock difference in microseconds", expected, differenceSeconds * 1e6);
    }


    public static void main(String[] args) {

        // GPS satellite (MEO):
        // altitude 20,200 km
        double gpsRadius = R_EARTH + 20200000.0;

        compute(    gpsRadius,
                    40.0,       // New York-ish latitude
                    1.0,        // days
                    38.575);    // expected clock difference in micorseconds


        // GEO satellite:
        // same latitude, altitude 35,786 km

        double geoRadius = R_EARTH + 35786000.0;

        compute(    geoRadius,
                    40.0,       // New York-ish latitude
                    1.0,        // days
                    46.572);    // expected clock difference in micorseconds
    }
}
