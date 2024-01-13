// hacked from http://www.hq.eso.org/~pgrosbol/fits_java/jfits.html
/*
 * @(#)TestWCS.java   $Revision: 1.1 $  $Date: 2008-04-18 13:10:58 $
 *
 * Copyright (C) 2007 European Southern Observatory 
 * License:  GNU General Public License version 2 or later
 */
//package org.eso.fits;

import java.util.*;
import java.io.*;

/** TestWCS class provides a static main method for testing World Coordinate
 *  System transformations for FITS files.  It also shows a typical usage of
 *  the WCS related classes.
 *
 *  @version $Id: TestWCS.java,v 1.1 2008-04-18 13:10:58 pgrosbol Exp $
 *  @author  P.Grosbol, ESO, <pgrosbol@eso.org>
 */
public class TestWCS{
    /** Static method for testing the WCS transformation.
     *
     *  @param argv   array of arguments: 1) FITS file,
     *                                    2) pixel coordinates (default 0,0),
     *                                    3) version (default ' ')
     */
    public static void main(String[] argv) {
		if (argv.length < 1) {
			throw new AssertionError("*** ERROR, must have at least one argument");
		}

		System.out.println("-- Test WCS for FITS file --------");

		FitsFile file = null;         // read name of FITS file
		try {
			file = new FitsFile(argv[0]);
		} catch (FitsException e) {
			System.out.println("Error: is not a FITS file >" + argv[0] + "<");
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Error: cannot open file >" + argv[0] + "<");
			System.exit(-1);
		}

		int ntok = 0;
		double[] coor = null;         // get pixel coordinates to test
		if (1 < argv.length) {
			StringTokenizer tok = new StringTokenizer(argv[1],",");
			ntok = tok.countTokens();
			coor = new double[ntok];
			for (int n=0; n<ntok; n++) {
			try {
				coor[n] = Double.parseDouble(tok.nextToken());
			} catch (Exception e) { coor[n] = 0.0; }
			}
		}

		char ver = ' ';               // get WCS version
		if (2 < argv.length) {
			ver = argv[2].charAt(0);
		}

		int noHDU = file.getNoHDUnits();
		System.out.print("  FITS file: " + argv[0]);
		System.out.print(" with " + noHDU + " HDUnits");
		System.out.println(", WCS version >" + ver + "<");

		for (int i=0; i<noHDU; i++) {
			FitsHDUnit hdu = file.getHDUnit(i);
			FitsHeader hdr = hdu.getHeader();
			int noKw = hdr.getNoKeywords();
			int type = hdr.getType();
			int size = (int) hdr.getDataSize();
			System.out.println("  " + i + ": >" + hdr.getName() 
					   + "< of type >" + Fits.getType(type)
					   + "< with " + noKw + " keywords"
					   + " and " + size + " bytes of data");

			if (type == Fits.IMAGE) {
			System.out.print("     Perform WCS transformation - ");

			FitsMatrix dm = (FitsMatrix) hdu.getData();
			int naxis[] = dm.getNaxis();
			double crval[] = dm.getCrval();
			double crpix[] = dm.getCrpix();
			double cdelt[] = dm.getCdelt();
			
			System.out.println("Dimension of matrix: " + naxis.length);
			for (int n=0; n<naxis.length; n++) 
				System.out.println("     Axis " + n + " [" + naxis[n]
						   + "] crpix " + crpix[n] + ", crval "
						   + crval[n] + ", cdelt " + cdelt[n]);

			double[] pix = new double[naxis.length];
			for (int n=0; n<naxis.length; n++) {
				if (n < ntok) {
				pix[n] = coor[n];
				} else {
				pix[n] = 0.0;
				}
			}


			FitsWCS wcs = new FitsWCS(hdr, ver);
			double[] wc = wcs.toWCS(pix);
			double[] pc = wcs.toPixel(wc);

			for (int n=0; n<naxis.length; n++) {
				System.out.println("     Axis " + n + "["+ wcs.getType(n) +
						   "]: Pixel " + pix[n] +
						   ", WCS: " + wc[n] + ", Pix: " + pc[n]);
			}
			System.out.print("\n");
			} else if (type==Fits.BTABLE || type==Fits.ATABLE) {
			System.out.println("\n  Warning: No WCS test for tables");
			}
		}
		System.out.println("-- Test finished -----------------");

    }
}
