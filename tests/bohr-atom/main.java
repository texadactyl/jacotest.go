import java.util.ArrayList;
import java.util.List;

public class main {
    // Constants
    private static final double Ry = -2.18e-18; // J
    private static final double c = 299792458; // m/s
    private static final double h = 6.62607015e-34; // J*s

    // EMF Table using a List of Object arrays
    private static final List<Object[]> EMF_TABLE = new ArrayList<>();

    static {
        EMF_TABLE.add(new Object[]{30e3, "Radio Very Low Frequency (VLF)"});
        EMF_TABLE.add(new Object[]{300e3, "Radio Low Frequency (LF)"});
        EMF_TABLE.add(new Object[]{3000e3, "Radio Medium Frequency (MF)"});
        EMF_TABLE.add(new Object[]{30e6, "Radio High Frequency (HF)"});
        EMF_TABLE.add(new Object[]{300e6, "Radio Very High Frequency (VHF)"});
        EMF_TABLE.add(new Object[]{1e9, "Radio Ultra High Frequency (UHF)"});
        EMF_TABLE.add(new Object[]{2e9, "Radio NASA Band L"});
        EMF_TABLE.add(new Object[]{4e9, "Radio NASA Band S"});
        EMF_TABLE.add(new Object[]{8e9, "Radio NASA Band C"});
        EMF_TABLE.add(new Object[]{12e9, "Radio NASA Band X"});
        EMF_TABLE.add(new Object[]{18e9, "Radio NASA Band Ku"});
        EMF_TABLE.add(new Object[]{27e9, "Radio NASA Band K"});
        EMF_TABLE.add(new Object[]{40e9, "Radio NASA Band Ka"});
        EMF_TABLE.add(new Object[]{75e9, "Radio NASA Band V"});
        EMF_TABLE.add(new Object[]{110e9, "Radio NASA Band W"});
        EMF_TABLE.add(new Object[]{300e9, "Radio Extremely High Frequency (EHF)"});
        EMF_TABLE.add(new Object[]{4e14, "Infrared (IR)"});
        EMF_TABLE.add(new Object[]{450e12, "Visible Red"});
        EMF_TABLE.add(new Object[]{508e12, "Visible Orange"});
        EMF_TABLE.add(new Object[]{540e12, "Visible Yellow"});
        EMF_TABLE.add(new Object[]{597e12, "Visible Green"});
        EMF_TABLE.add(new Object[]{610e12, "Visible Cyan"});
        EMF_TABLE.add(new Object[]{666e12, "Visible Blue"});
        EMF_TABLE.add(new Object[]{689e12, "Visible Indigo"});
        EMF_TABLE.add(new Object[]{750e12, "Visible Violet"});
        EMF_TABLE.add(new Object[]{3e16, "Ultraviolet (UV)"});
        EMF_TABLE.add(new Object[]{1e19, "X-ray"});
        EMF_TABLE.add(new Object[]{1e21, "Gamma ray"});
    }

    private static double deltaE(int ni, int nf) {
        return Ry * (1.0 / (ni * ni) - 1.0 / (nf * nf));
    }

    private static long newNf(int ni, double energy) {
        double denom = (1.0 / (ni * ni)) - (energy / Ry);
        return Math.round(ni * Math.sqrt(1.0 / denom));
    }

    private static String getSeries(int ni, int nf, double energy) {
        // Validate input
        if (ni == nf || ni < 1 || nf < 1) {
            return "Invalid transition: ni and nf must be different, and both must be >= 1";
        }

        // Determine the hydrogen series based on nf
        String seriesName = "";
        String transitionType = "";

        if (ni > nf) {
            // Emission: electron falls from ni to nf
            transitionType = "Emission";
        } else {
            // Absorption: electron is excited from ni to nf
            transitionType = "Absorption";
        }

        // Determine series based on nf
        switch (nf) {
            case 1:
                seriesName = "Lyman (UV)";
                break;
            case 2:
                seriesName = "Balmer (Visible)";
                break;
            case 3:
                seriesName = "Paschen (IR)";
                break;
            case 4:
                seriesName = "Brackett (Far IR)";
                break;
            case 5:
                seriesName = "Pfund (Far IR)";
                break;
            case 6:
                seriesName = "Humphreys (Far IR)";
                break;
            default:
                seriesName = "Unknown Series (nf > 6)";
        }

        // Return the series and transition type
        return String.format("%s (%s Transition)", seriesName, transitionType);
    }

    private static Object[] ninf2info(int ni, int nf) {
        double E = deltaE(ni, nf); // Energy in J
        String series = getSeries(ni, nf, E);
        double freq = Math.abs(E) / h; // Frequency in Hz
        double wavelength = c * 1e9 / freq; // Wavelength in nm

        for (Object[] row : EMF_TABLE) {
            double rowFreq = (double) row[0];
            String rowDesc = (String) row[1];
            if (freq < rowFreq) {
                return new Object[] {rowDesc, wavelength, E, series};
            }
        }
        return new Object[] {"Gamma", wavelength, E, series};
    }

    private static void p1() {
        System.out.println("\n1. Given ni=4 and nf=2, calculate E gained by the emitted photon, F, and Lambda. Blue light emission.");
        int ni = 4, nf = 2;
        double E = deltaE(ni, nf); // Energy in J
        System.out.println("\tE: " + E);
        double freq = Math.abs(E) / h; // Frequency in Hz
        System.out.printf("\tF: %.3e\n", freq);
        double lambdaNm = (c / freq) * 1e9; // Wavelength in nm
        System.out.printf("\tLambda: %.1f\n", lambdaNm);
    }

    private static void p2() {
        System.out.println("\n2. Calculate the electron energy difference between ni and nf. Electron absorption: photon E < 0, emission: E > 0.");
        System.out.println("\t1 to 7 (E loss): " + deltaE(1, 7));
        System.out.println("\t2 to 5 (E loss): " + deltaE(2, 5));
        System.out.println("\t3 to 1 (E gain): " + deltaE(3, 1));
        System.out.println("\t6 to 4 (E gain): " + deltaE(6, 4));
        System.out.println("\t7 to 2 (E gain): " + deltaE(7, 2));
    }

    private static void p3() {
        System.out.println("\n3. Photon emission info after electron collision (band, Lambda, E, series:");
        System.out.println("\t5 to 3: " + String.join(", ", toStringArray(ninf2info(5, 3))));
        System.out.println("\t5 to 1: " + String.join(", ", toStringArray(ninf2info(5, 1))));
        System.out.println("\t4 to 2: " + String.join(", ", toStringArray(ninf2info(4, 2))));
        System.out.println("\t6 to 4: " + String.join(", ", toStringArray(ninf2info(6, 4))));
        System.out.println("\t7 to 5: " + String.join(", ", toStringArray(ninf2info(7, 5))));
        System.out.println("\t7 to 1: " + String.join(", ", toStringArray(ninf2info(7, 1))));
    }

    private static void p4() {
        System.out.println("\n4. Photon absorption info after electron collision (band, Lambda, E, series:");
        System.out.println("\t3 to 5: " + String.join(", ", toStringArray(ninf2info(3, 5))));
        System.out.println("\t1 to 5: " + String.join(", ", toStringArray(ninf2info(1, 5))));
        System.out.println("\t2 to 4: " + String.join(", ", toStringArray(ninf2info(2, 4))));
        System.out.println("\t4 to 6: " + String.join(", ", toStringArray(ninf2info(4, 6))));
        System.out.println("\t5 to 7: " + String.join(", ", toStringArray(ninf2info(5, 7))));
        System.out.println("\t1 to 7: " + String.join(", ", toStringArray(ninf2info(1, 7))));
    }
    
    private static String[] toStringArray(Object[] array) {
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].toString();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("In the upcoming exercises, the following symbols are used in reporting:");
        System.out.println("ni - initial electron energy level");
        System.out.println("nf - final electron energy level");
        System.out.println("E - energy in joules");
        System.out.println("F - frequency in Hz");
        System.out.println("Lambda - wavelength in nm");
        p1();
        p2();
        p3();
        p4();
    }
}

