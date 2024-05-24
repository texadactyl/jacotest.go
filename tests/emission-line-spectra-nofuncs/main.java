import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.awt.Color;

public class main {

    // Wave length and strength arrays
    static int countEmLines = 0; // Count of emission lines in an element file
    static double [] wavelength = new double[200];
    static double [] strength = new double[200];

    // Spectra array
    static int spectraWidth;
    static Color [] spectra = new Color[1280]; // used length = spectraWidth
    static int [] hashes = new int[1280]; // used length = spectraWidth

	// Interpolate color array to obtain spectra
	static final int RED = 1;
	static final int GREEN = 2;
	static final int BLUE = 3;
	
	// Other parameters:
	static String elementPath = "hydrogen.txt";
	static double startWavelength = 4000.0;
	static double endWavelength = 7000.0;
	static double lineWidth = 1.0;
	static double contrast = 10.0;
	static double continuum = 0.3;

    public static void main(String args[]) {

        if (elementPath == null)
            throw new AssertionError("elementPath is null");
        System.out.printf("elementPath is %s\n", elementPath);

        // Read element emission line file
        // Build wavelength and strength arrays
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(elementPath));
            String line = reader.readLine();

            while (line != null) {
                StringTokenizer tokens = new StringTokenizer(line, " ");
                if (tokens.countTokens() != 2) {
                	String errMsg = String.format("File %s line %d mis-formatted", elementPath, countEmLines + 1);
                    throw new AssertionError(errMsg);
                }
                System.out.printf("File %s line %d: %s\n", elementPath, countEmLines + 1, line);
                wavelength[countEmLines] = Double.parseDouble(tokens.nextToken());
                strength[countEmLines] = Double.parseDouble(tokens.nextToken());
                if (++countEmLines > 199) {
                    System.out.printf("*** Discharge WARNING: spectral line limit of 200 reached; ignoring subsequent lines");
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
		    String msg = String.format("*** ERROR, File %s line %d had an IOException", elementPath, countEmLines);
		    throw new AssertionError(msg);
        }

        // Spectra width = count of EMR lines
        spectraWidth = countEmLines;
        System.out.printf("spectral width = %d\n", spectraWidth);

        // Compute spectra intensity array elements
        double [] intensity = new double[spectraWidth];
        double dwave = (endWavelength - startWavelength) / spectraWidth;
        double lineWidth2 = lineWidth * lineWidth;
        double wave, sum, delta;
        double maxIntensity = -1e23;
        for (int ii = 0; ii < spectraWidth; ii++) {
            wave = ii * dwave + startWavelength;
            sum = 0.0;
            // Sum the gaussian emission line profile for all lines
            for (int jj = 0; jj < countEmLines; jj++) {
                delta = wavelength[jj] - wave;
                sum = sum + strength[jj] * Math.exp(-delta * delta / lineWidth2);
            }
            // The intensity of this spectra element = sum
            intensity[ii] = sum;
            if (sum > maxIntensity) maxIntensity = sum;
        }

        if (maxIntensity == 0.0) {
            maxIntensity = 1.0;
        }
        double scale = (1 - continuum) * contrast / maxIntensity;
        System.out.printf("scale = %f\n", scale);


        // chosen points in the color array spectra
        double [][] colors = {{0.0 / 255, 0, 0, 8},
                {48.0 / 255, 0, 0, 255},
                {96.0 / 255, 0, 255, 255},
                {128.0 / 255, 0, 255, 0},
                {160.0 / 255, 255, 255, 0},
                {207.0 / 255, 255, 0, 0},
                {255.0000001 / 255, 8, 0, 0}};
        double dw, fraction;
        for (int ii = 0; ii < spectraWidth; ii++) {
            wave = ii * dwave + startWavelength;
            dw = (wave - 4000) / (7000 - 4000);
            if (dw < 0)
                dw = 0;
            else if (dw > 1)
                dw = 1;
            int kk = 0;
            while (dw > colors[++kk][0]) {
            }

            fraction = (dw - colors[kk - 1][0]) / (colors[kk][0] - colors[kk - 1][0]);
            double wavRed = fraction * (colors[kk][RED] - colors[kk - 1][RED]) + colors[kk - 1][RED];
            double wavGreen = fraction * (colors[kk][GREEN] - colors[kk - 1][GREEN]) + colors[kk - 1][GREEN];
            double wavBlue = fraction * (colors[kk][BLUE] - colors[kk - 1][BLUE]) + colors[kk - 1][BLUE];

            double plot = continuum + scale * intensity[ii];
            if (plot > 1) plot = 1;
            System.out.printf("ii = %d, kk = %d, fraction = %f, wavRed = %f, wavGreen = %f, wavBlue = %f, plot = %f\n",
                    ii, kk, fraction, wavRed, wavGreen, wavBlue, plot);
            spectra[ii] = new Color( (int) (plot * wavRed), (int) (plot * wavGreen), (int) (plot * wavBlue));
            hashes[ii] = spectra[ii].hashCode();
        }

    }

}

