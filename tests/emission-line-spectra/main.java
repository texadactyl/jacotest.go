public class main {

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
            System.out.printf("\t%d) %f %f %d [0x%08x]\n", ++rptLineCounter, obsWavelength[ii], obsStrength[ii], obsHashes[ii], obsHashes[ii]);
            errorCount += Checkers.withinTolerance("wavelength", expWavelength[ii], obsWavelength[ii]);
            errorCount += Checkers.withinTolerance("strength", expStrength[ii], obsStrength[ii]);
            errorCount += Checkers.checker("hash", expHashes[ii], obsHashes[ii]);
        }

        // Exit with good/bad news
        Checkers.theEnd(errorCount);
    }
}
