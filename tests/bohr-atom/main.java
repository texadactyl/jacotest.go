import java.util.ArrayList;
import java.util.List;

public class main {
    // Constants
    private static final double RH = 1.09677583e7; // 1/m, Hydrogen only
    private static final double c = 299792458; // m/s
    private static final double h = 6.62607015e-34; // J*s
	private static final double TOLERANCE = 0.001;
	private static int errorCount = 0;

    // EMF Table using a List of Object arrays
    private static final List<NasaInfo> nasaTable = new ArrayList<>();

    static {
        nasaTable.add(new NasaInfo(30e3, "Radio Very Low Frequency (VLF)"));
        nasaTable.add(new NasaInfo(300e3, "Radio Low Frequency (LF)"));
        nasaTable.add(new NasaInfo(3000e3, "Radio Medium Frequency (MF)"));
        nasaTable.add(new NasaInfo(30e6, "Radio High Frequency (HF)"));
        nasaTable.add(new NasaInfo(300e6, "Radio Very High Frequency (VHF)"));
        nasaTable.add(new NasaInfo(1e9, "Radio Ultra High Frequency (UHF)"));
        nasaTable.add(new NasaInfo(2e9, "Radio NASA Band L"));
        nasaTable.add(new NasaInfo(4e9, "Radio NASA Band S"));
        nasaTable.add(new NasaInfo(8e9, "Radio NASA Band C"));
        nasaTable.add(new NasaInfo(12e9, "Radio NASA Band X"));
        nasaTable.add(new NasaInfo(18e9, "Radio NASA Band Ku"));
        nasaTable.add(new NasaInfo(27e9, "Radio NASA Band K"));
        nasaTable.add(new NasaInfo(40e9, "Radio NASA Band Ka"));
        nasaTable.add(new NasaInfo(75e9, "Radio NASA Band V"));
        nasaTable.add(new NasaInfo(110e9, "Radio NASA Band W"));
        nasaTable.add(new NasaInfo(300e9, "Radio Extremely High Frequency (EHF)"));
        nasaTable.add(new NasaInfo(4e14, "Infrared (IR)"));
        nasaTable.add(new NasaInfo(450e12, "Visible Red"));
        nasaTable.add(new NasaInfo(508e12, "Visible Orange"));
        nasaTable.add(new NasaInfo(540e12, "Visible Yellow"));
        nasaTable.add(new NasaInfo(597e12, "Visible Green"));
        nasaTable.add(new NasaInfo(610e12, "Visible Cyan"));
        nasaTable.add(new NasaInfo(666e12, "Visible Blue"));
        nasaTable.add(new NasaInfo(689e12, "Visible Indigo"));
        nasaTable.add(new NasaInfo(750e12, "Visible Violet"));
        nasaTable.add(new NasaInfo(3e16, "Ultraviolet (UV)"));
        nasaTable.add(new NasaInfo(1e19, "X-ray"));
        nasaTable.add(new NasaInfo(1e21, "Gamma ray"));
    }

    public static void main(String[] args) {
        System.out.println("The Bohr model for Hydrogen");
        p1();
        p2();
        p3();
        p4();
        assert errorCount == 0;
        System.out.println("Success!");
    }

	// Is the observed value close enough to the expected value?
    private static int isItClose(String label, double observed, double expected, double tolerance) { 	
        if (Math.abs(expected) < tolerance && Math.abs(observed) < tolerance) {
            System.out.printf("isItClose? %s: Success (close enough): observed=%f vs expected=%f\n",
                    label, observed, expected);
            return 0; // Consider them equal
        }
        double difference = Math.abs(expected - observed);
    	System.out.printf("isItClose? %s: ", label);
        if (difference < tolerance) {
            System.out.printf("isItClose? %s: Success (close enough): observed=%f vs expected=%f\n",
                    label, observed, expected);
            return 0;
        }
        System.out.printf(": *** ERROR, not close enough: observed=%f vs expected=%f\n", observed, expected);
        return 1;
    }

    // Convert joules to electron volts.
    private static double joulesToEv(double arg) {
        return arg / 1.602176634e-19;
    }

    // Convert meters to nanometers.
    private static double metersToNm(double arg) {
        return arg * 1e9;
    }

    // Given the initial and final energy levels of an electron, return its atomic series name.
    private static String getSeries(int ni, int nf) {
        // Validate input
        if (ni == nf || ni < 1 || nf < 1) {
            return "Invalid transition: ni and nf must be different, and both must be >= 1";
        }

        // Determine the hydrogen series based on nf
        String seriesName;
        String transitionType;

        if (ni > nf) {
            // Emission: electron falls from ni to nf
            transitionType = "Emission";
        } else {
            // Absorption: electron is excited from ni to nf
            transitionType = "Absorption";
        }

        // Determine series based on nf
        seriesName = switch (nf) {
            case 1 -> "Lyman (UV)";
            case 2 -> "Balmer (Visible)";
            case 3 -> "Paschen (IR)";
            case 4 -> "Brackett (Far IR)";
            case 5 -> "Pfund (Far IR)";
            case 6 -> "Humphreys (Far IR)";
            default -> "Beyond Humphreys (nf > 6)";
        };

        // Return the series and transition type
        return String.format("%s (%s Transition)", seriesName, transitionType);
    }

    // Given the initial and final energy levels of an electron, compute the energy lost or gained by an electron (J):
    private static double deltaE(int ni, int nf) {

        // Get wavelength in meters.
        double wlM = Math.abs(1.0 / (RH * ((1.0 / ((double) nf * (double) nf)) - (1.0 / ((double) ni * (double) ni)))));
        
        // Get frequency in Hz.
        double freqHZ = c / wlM;
        
        // Compute energy in Joules.
        return freqHZ * h;
    }

    // Given the initial and final energy levels of an electron, compute a JumpToInfo object:
    //      Nasa descriptive text
    //      wavelength (nm)
    //      energy (eV)
    //      series text (Lyman, Balmer, etc.)
    private static JumpToInfo computeJumpToInfo(int ni, int nf) {
        double energy = deltaE(ni, nf); // Energy in J
        String series = getSeries(ni, nf);
        double freq = Math.abs(energy) / h; // Frequency in Hz
        double wavelength = c / freq; // Wavelength in m

        for (NasaInfo nasaInfo : nasaTable) {
            if (freq < nasaInfo.freqUB) {
                return new JumpToInfo( nasaInfo.desc, metersToNm(wavelength), joulesToEv(energy), series );
            }
        }
        return new JumpToInfo( "Gamma", metersToNm(wavelength), joulesToEv(energy), series );
    }

    // Testing phase 1.
    private static void p1() {
        System.out.println("\np1. Given ni=4 and nf=2, calculate the energy gained by the emitted photon, frequency, and wavelength. Blue light emission.");
        int ni = 4, nf = 2;
        JumpToInfo jti = computeJumpToInfo(ni, nf);
        System.out.printf("\tEnergy: %.3f eV\n", jti.energy);
        double frequencyHz = c / (jti.waveLength / 1e9);
        System.out.printf("\tFrequency: %e Hz\n", frequencyHz);
        System.out.printf("\tLambda: %.1f nm\n", jti.waveLength);
    }

    // Testing phase 2.
    private static void p2() {
        System.out.println("\np2. Calculate the electron energy difference between ni and nf:");
        System.out.printf("\t1 to 7 (electron +, photon -): %.3f eV\n", joulesToEv(deltaE(1, 7)));
        System.out.printf("\t2 to 5 (electron +, photon -): %.3f eV\n", joulesToEv(deltaE(2, 5)));
        System.out.printf("\t3 to 1 (electron -, photon +): %.3f eV\n", joulesToEv(deltaE(3, 1)));
        System.out.printf("\t6 to 4 (electron -, photon +): %.3f eV\n", joulesToEv(deltaE(6, 4)));
        System.out.printf("\t7 to 2 (electron -, photon +): %.3f eV\n", joulesToEv(deltaE(7, 2)));
        errorCount += isItClose("joulesToEv(deltaE(2, 5)", joulesToEv(deltaE(2, 5)), 2.856, TOLERANCE);
        errorCount += isItClose("joulesToEv(deltaE(6, 4)", joulesToEv(deltaE(6, 4)), 0.472, TOLERANCE);
    }


    // Testing phase 3.
    private static void p3() {
        System.out.println("\np3. Photon emission info after electron collision (band, Lambda, E, series:");
        System.out.printf("\t5 to 3: %s\n", computeJumpToInfo(5, 3).toString());
        System.out.printf("\t5 to 1: %s\n", computeJumpToInfo(5, 1).toString());
        System.out.printf("\t4 to 2: %s\n", computeJumpToInfo(4, 2).toString());
        System.out.printf("\t6 to 4: %s\n", computeJumpToInfo(6, 4).toString());
        System.out.printf("\t7 to 5: %s\n", computeJumpToInfo(7, 5).toString());
        System.out.printf("\t7 to 1: %s\n", computeJumpToInfo(7, 1).toString());
        JumpToInfo jti = computeJumpToInfo(7, 5);
        errorCount += isItClose("computeJumpToInfo(7, 5).waveLength(nm)", jti.waveLength, 4653.792, TOLERANCE);
        errorCount += isItClose("computeJumpToInfo(7, 5).energy(eV)", jti.energy, 0.266, TOLERANCE);
    }

    // Testing phase 4.
    private static void p4() {
        System.out.println("\np4. Photon absorption info after electron collision (band, Lambda, E, series:");
        System.out.printf("\t3 to 5: %s\n", computeJumpToInfo(3, 5).toString());
        System.out.printf("\t1 to 5: %s\n", computeJumpToInfo(1, 5).toString());
        System.out.printf("\t2 to 4: %s\n", computeJumpToInfo(2, 4).toString());
        System.out.printf("\t4 to 6: %s\n", computeJumpToInfo(4, 6).toString());
        System.out.printf("\t5 to 7: %s\n", computeJumpToInfo(5, 7).toString());
        System.out.printf("\t1 to 7: %s\n", computeJumpToInfo(1, 7).toString());
        JumpToInfo jti = computeJumpToInfo(1, 7);
        errorCount += isItClose("computeJumpToInfo(1, 7).waveLength(nm)", jti.waveLength, 93.076, TOLERANCE);
        errorCount += isItClose("computeJumpToInfo(1, 7).energy(eV)", jti.energy, 13.321, TOLERANCE);
        
    }
    
}

class NasaInfo {
    double freqUB;  // Frequency upper boundary of an EMR band
    String desc;    // NASA band description
    
    public NasaInfo(double argFreqUB, String argDesc) {
        this.freqUB = argFreqUB;
        this.desc = argDesc;
    }
}

class JumpToInfo {
    String name;        // EMR category (common name)
    double waveLength;  // nm
    double energy;      // eV
    String series;      // Lyman (UV), etc.
    
    public JumpToInfo(String argName, double argWaveLength, double argEnergy, String argSeries) {
        this.name = argName;
        this.waveLength = argWaveLength;
        this.energy = argEnergy;
        this.series = argSeries;
    }

    public String toString() {
        return String.format("%s, %.3f nm, %.3f eV, %s", this.name, this.waveLength, this.energy, this.series);
    }
}

