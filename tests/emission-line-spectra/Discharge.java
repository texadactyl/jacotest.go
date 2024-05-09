// hacked from http://spiff.rit.edu/classes/phys314/lectures/bohrprob/spectra/ "discharge.java"
// See Physics course too:  http://spiff.rit.edu/classes/phys314/phys314.html

/*
 * Discharge.java   Process emission line spectra from an element table
 *                  where each line contains the wavelength and the strength at that wavelength.
 *
 * INPUT :  filename of element emission line wavelengths/strengths table
 *          and various other applet parameters (see below)
 *
 * OUTPUT:  Hashes of color-encoded spectra simulating a gas discharge plasma
 *
 * Original code by John Talbot "Friday June 13,  1997"
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.awt.Color;

public class Discharge {

    // Wave length and strength arrays
    int countEmLines = 0; // Count of emission lines in an element file
    double [] wavelength = new double[200]; // used length = countEmLines
    double [] strength = new double[200]; // used length = countEmLines

    // Spectra array
    int spectraWidth;
    Color [] spectra = new Color[1280]; // used length = spectraWidth
    int [] hashes = new int[1280]; // used length = spectraWidth

	// Interpolate color array to obtain spectra
	final int RED = 1;
	final int GREEN = 2;
	final int BLUE = 3;
	
    public void warning(String msg) {
        System.out.printf("WARNING,  %s !!!\n", msg);
    }

    // Object instantiation function
    public Discharge(String elementPath,
                     double startWavelength,
                     double endWavelength,
                     double lineWidth,
                     double contrast,
                     double continuum) {

        if (elementPath == null)
            throw new AssertionError("Discharge: elementPath is null");
        System.out.printf("Discharge: elementPath is %s\n", elementPath);

        // Read element emission line file
        // Build wavelength and strength arrays
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(elementPath));
            String line = reader.readLine();

            while (line != null) {
                StringTokenizer tokens = new StringTokenizer(line, " ");
                if (tokens.countTokens() != 2) {
                	String errMsg = String.format("Discharge: File %s line %d mis-formatted", elementPath, countEmLines + 1);
                    throw new AssertionError(errMsg);
                }
                wavelength[countEmLines] = Double.parseDouble(tokens.nextToken());
                strength[countEmLines] = Double.parseDouble(tokens.nextToken());
                if (++countEmLines > 199) {
                    warning("Discharge: spectral line limit of 200 reached; ignoring subsequent lines");
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
		    String msg = String.format("*** ERROR, Discharge: File %s line %d had an IOException", elementPath, countEmLines);
		    throw new AssertionError(msg);
        }

        // Spectra width = count of EMR lines
        spectraWidth = countEmLines;
        System.out.printf("Discharge: spectral width = %d\n", spectraWidth);

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
            int k = 0;
            while (dw > colors[++k][0]) {
            }

            fraction = (dw - colors[k - 1][0]) / (colors[k][0] - colors[k - 1][0]);
            double wavRed = fraction * (colors[k][RED] - colors[k - 1][RED]) + colors[k - 1][RED];
            double wavGreen = fraction * (colors[k][GREEN] - colors[k - 1][GREEN]) + colors[k - 1][GREEN];
            double wavBlue = fraction * (colors[k][BLUE] - colors[k - 1][BLUE]) + colors[k - 1][BLUE];

            double plot = continuum + scale * intensity[ii];
            if (plot > 1) plot = 1;
            spectra[ii] = new Color( (int) (plot * wavRed), (int) (plot * wavGreen), (int) (plot * wavBlue));
            hashes[ii] = spectra[ii].hashCode();
        }

    }

    public int [] getHashes() {
        return hashes;
    }

    public double [] getWavelength() {
        return wavelength;
    }

    public double [] getStrength() {
        return strength;
    }

    public int getEmLineCount() {
        return countEmLines;
    }

}
