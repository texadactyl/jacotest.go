public class main {

    public static int checkObsExp(String label, double observed, double expected) {
        if (observed == expected) {
            System.out.printf("checkObsExp: %s ok, observed: %f, expected: %f\n", label, observed, expected);
            return 0;
        }
        System.out.printf("checkObsExp: %s *** ERROR, expected: ", label);
        System.out.print(expected);
        System.out.print(", observed: ");
        System.out.println(observed);
        return 1;
    }

    public static int checkObsExp(String label, int observed, int expected) {
        if (observed == expected) {
            System.out.printf("checkObsExp: %s ok, observed: %d, expected: %d\n", label, observed, expected);
            return 0;
        }
        System.out.printf("checkObsExp: %s *** ERROR, expected: ", label);
        System.out.print(expected);
        System.out.print(", observed: ");
        System.out.println(observed);
        return 1;
    }

    public static void main(String[] args) {
        System.out.println("Process emission line spectra from an element table");
        System.out.println("where each line contains the wavelength and the strength at that wavelength");
        int errorCount = 0;

        // File: Hydrogen
        String elementPath = "hydrogen.txt";

        // Discharge parameters
        double startWavelength = 4000.0;
        double endWavelength = 7000.0;
        double lineWidth = 1.0;
        double contrast = 10.0;
        double continuum = 0.3;

        // Expected values
        double [] expWavelength = {3970.07, 4101.74, 4340.47, 4861.33, 6562.72, 6562.85};
        double [] expStrength = {8.0, 15.0, 30.0, 80.0, 120.0, 180.0};
        int [] expHashes = {-16777214, -16777148, -16762292, -16757759, -11781120, -12320768};

        // Perform discharge computations
        Discharge discharge = new Discharge(elementPath,
                                            startWavelength,
                                            endWavelength,
                                            lineWidth,
                                            contrast,
                                            continuum);

        // Get results
        int countEmLines = discharge.getEmLineCount();
        double [] obsWavelength = discharge.getWavelength();
        double [] obsStrength = discharge.getStrength();
        int [] obsHashes = discharge.getHashes();

        // Report heading
        System.out.printf("Input file: %s\n", elementPath);
        System.out.printf("Count of emission lines: %d\n", countEmLines);
        if (countEmLines != 6) {
            System.out.println("***ERROR, expected 6 emission lines for Hydrogen");
            ++errorCount;
        }

        // Report details
        int rptLineCounter = 0;
        System.out.println("Wavelength, Strength, and Colour hash values per line:");
        for (int ii = 0; ii < countEmLines; ++ii) {
            System.out.printf("\t%d) %f %f %d\n", ++rptLineCounter, obsWavelength[ii], obsStrength[ii], obsHashes[ii]);
            errorCount += checkObsExp("wavelength", obsWavelength[ii], expWavelength[ii]);
            errorCount += checkObsExp("strength", obsStrength[ii], expStrength[ii]);
            errorCount += checkObsExp("hash", obsHashes[ii], expHashes[ii]);
        }

        // Exit with good/bad news
        assert (errorCount == 0);
        System.out.println("Success!");
    }
}
