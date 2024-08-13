import java.util.Arrays;

public class main {

    private static final double RH = -2.18e-18;     // RHdberg constant (J)
    private static final double c = 299792458.0;    // Speed of light (m/s)
    private static final double h = 6.62607015e-34; // Planck's constant (J*s)

    // Define a class to represent an entry in the EMF table
    static class EMFEntry {
        double frequency;
        String description;

        EMFEntry(double frequency, String description) {
            this.frequency = frequency;
            this.description = description;
        }
    }

    // Create an array of EMFEntry objects
    private static final EMFEntry[] EMF_TABLE = {
            new EMFEntry(30e3, "Radio Very Low Frequency (VLF)"),
            new EMFEntry(300e3, "Radio Low Frequency (LF)"),
            new EMFEntry(3000e3, "Radio Medium Frequency (MF)"),
            new EMFEntry(30e6, "Radio High Frequency (HF)"),
            new EMFEntry(300e6, "Radio Very High Frequency (VHF)"),
            new EMFEntry(1e9, "Radio Ultra High Frequency (UHF)"),
            new EMFEntry(2e9, "Radio NASA Band L"),
            new EMFEntry(4e9, "Radio NASA Band S"),
            new EMFEntry(8e9, "Radio NASA Band C"),
            new EMFEntry(12e9, "Radio NASA Band X"),
            new EMFEntry(18e9, "Radio NASA Band Ku"),
            new EMFEntry(27e9, "Radio NASA Band K"),
            new EMFEntry(40e9, "Radio NASA Band Ka"),
            new EMFEntry(75e9, "Radio NASA Band V"),
            new EMFEntry(110e9, "Radio NASA Band W"),
            new EMFEntry(300e9, "Radio Extremely High Frequency (EHF)"),
            new EMFEntry(4e14, "Infrared (IR)"),
            new EMFEntry(450e12, "Visible Red"),
            new EMFEntry(508e12, "Visible Orange"),
            new EMFEntry(540e12, "Visible Yellow"),
            new EMFEntry(597e12, "Visible Green"),
            new EMFEntry(610e12, "Visible Cyan"),
            new EMFEntry(666e12, "Visible Blue"),
            new EMFEntry(689e12, "Visible Indigo"),
            new EMFEntry(750e12, "Visible Violet"),
            new EMFEntry(3e16, "Ultraviolet (UV)"),
            new EMFEntry(1e19, "X-ray"),
            new EMFEntry(1e21, "Gamma ray")
    };

    public static double deltaE(int ni, int nf) {
        return RH * (1.0 / (nf * nf) - 1.0 / (ni * ni));
    }

    public static String getSeries(int nf) {
        return switch (nf) {
            case 1 -> "Liman (UV)";
            case 2 -> "Balmer (vis)";
            case 3 -> "Paschen (IR)";
            case 4 -> "Bracket (Far IR)";
            case 5 -> "Pfund (IR)";
            case 6 -> "Humphreys (IR)";
            default -> "nf>6 (IR)";
        };
    }

    // ninf2info: 
    // Input:
    // * ni = initial electron shell level
    // * nf = final electron shell level
    // Return 4 strings:
    // * EMR subset name
    // * Wavelength in String format
    // * Change in energy in String format
    // * Series name
    public static String[] ninf2info(int ni, int nf) {
        double E = deltaE(ni, nf);
        String ser = E < 0 ? getSeries(nf) : "n/a";
        double freq = Math.abs(E) / h;
        double wavelength = c * 1e9 / freq;

        for (EMFEntry entry : EMF_TABLE) {
            if (freq < entry.frequency) {
                return new String[]{entry.description, String.format("%.1f", wavelength), String.format("%.5e", E), ser};
            }
        }
        return new String[]{"Gamma", String.format("%.1f", wavelength), String.format("%.5e", E), getSeries(nf)};
    }

    public static double newNf(int ni, double energy) {
        return ni * Math.sqrt(RH / (energy * ni * ni + RH));
    }

    public static void p1() {
        int ni = 4;
        int nf = 2;
        double E = deltaE(ni, nf);
        System.out.printf("1. Energy (J): %e\n", E);
        double freq = Math.abs(E) / h;
        System.out.printf("Frequency (Hz): %.3e\n", freq);
        double wavelength = c / freq;
        System.out.printf("Wavelength (nm): %.1f\n\n", wavelength * 1e9);
    }

    public static void p2() {
        double wavelength = 1283.45 / 1e9;
        int ni = 3;
        double freq = c / wavelength;
        System.out.printf("2. Frequency (Hz): %.3e\n", freq);
        double E = freq * h;
        System.out.printf("Energy (J): %e\n", E);
        double nf = newNf(ni, E);
        System.out.printf("nf: %f\n", nf);
    }

    public static void p3() {
        System.out.printf("A. %e\n", deltaE(2, 5));
        System.out.printf("B. %e\n", deltaE(6, 4));
        System.out.printf("C. %e\n", deltaE(1, 7));
        System.out.printf("D. %e\n", deltaE(3, 1));
        System.out.printf("E. %e\n", deltaE(7, 2));
    }

    public static void p4() {
        System.out.printf("A. %s\n", Arrays.toString(ninf2info(5, 3)));
        System.out.printf("B. %s\n", Arrays.toString(ninf2info(5, 1)));
        System.out.printf("C. %s\n", Arrays.toString(ninf2info(4, 2)));
        System.out.printf("D. %s\n", Arrays.toString(ninf2info(6, 4)));
        System.out.printf("E. %s\n", Arrays.toString(ninf2info(7, 5)));
        System.out.printf("8 to 6. %s\n", Arrays.toString(ninf2info(8, 6)));
        System.out.printf("11 to 6. %s\n", Arrays.toString(ninf2info(11, 6)));
        System.out.printf("8 to 7. %s\n", Arrays.toString(ninf2info(8, 7)));
        System.out.printf("11 to 7. %s\n", Arrays.toString(ninf2info(11, 7)));
        System.out.printf("11 to 10. %s\n", Arrays.toString(ninf2info(11, 10)));
    }
    
    private static void prtNinf2Info(String fmt1, String fmt2, String[] strArray) {
        String fmt = fmt1.concat(fmt2);
        System.out.printf(fmt, strArray[0], strArray[1], strArray[2], strArray[3]); 
    }

    public static void p5() {
        String fmt = "%s, wl=%snm, E=%sJ, Series=%s\n";
        prtNinf2Info("3 >> 2: ", fmt, ninf2info(3, 2));
        prtNinf2Info("4 >> 2: ", fmt, ninf2info(4, 2));
        prtNinf2Info("5 >> 2: ", fmt, ninf2info(5, 2));
        prtNinf2Info("6 >> 2: ", fmt, ninf2info(6, 2));
        prtNinf2Info("2 >> 3: ", fmt, ninf2info(2, 3));
        prtNinf2Info("2 >> 4: ", fmt, ninf2info(2, 4));
        prtNinf2Info("2 >> 5: ", fmt, ninf2info(2, 5));
        prtNinf2Info("2 >> 6: ", fmt, ninf2info(2, 6));
    }

    public static void p6() {
        String fmt = "%s, wl=%snm, E=%sJ, Series=%s\n";
        prtNinf2Info("4 >> 3: ", fmt, ninf2info(4, 3));
        prtNinf2Info("5 >> 3: ", fmt, ninf2info(5, 3));
        prtNinf2Info("6 >> 3: ", fmt, ninf2info(6, 3));
    }

    public static void main(String[] args) {
        p1();
        p2();
        p3();
        p4();
        p5();
        p6();
    }
}

