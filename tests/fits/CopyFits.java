// hacked from http://www.hq.eso.org/~pgrosbol/fits_java/jfits.html
/* @(#)CopyFits.java   $Revision: 1.2 $    $Date: 2004-01-12 13:13:23 $
 *
 * Copyright (C) 2002 European Southern Observatory 
 * License:  GNU General Public License version 2 or later
 */
//package org.eso.fits;

import java.io.*;

/** CopyFits class provides a static main method to test writing
 *  of FITS files by copying an existing FITS file.
 *
 *  @version $Revision: 1.2 $ $Date: 2004-01-12 13:13:23 $
 *  @author  P.Grosbol, ESO, <pgrosbol@eso.org>
 */
public class CopyFits {
    /** Static method for testing the FITS class library.
     *
     *  @param argv   array of arguments i.e. options of FITS files
     */
    public static void main(String[] argv) {
		System.out.println("Start CopyFits");
		if (argv.length != 2) {
				throw new AssertionError("*** ERROR, must have 2 arguments");
		}

		FitsFile file = null;
		try {
			file = new FitsFile(argv[0]);
		} catch (FitsException e) {
			System.err.println("Error: is not a FITS file >" + argv[0] + "<");
			System.exit(-1);
		}  catch (IOException e) {
			System.err.println("Error: cannot open file >" + argv[0] + "<");
			System.exit(-1);
		}

		int noHDU = file.getNoHDUnits();
		System.out.println("FITS file has " + noHDU + " HDUnits");

		try {
			file.writeFile(argv[1]);
		} catch (FitsException e) {
			String msg = String.format("*** ERROR, FitsException, cannot open file [%s] for output", argv[1]);
			throw new AssertionError(msg);
		} catch (IOException e) {
			String msg = String.format("*** ERROR, IOException, cannot open file [%s] for output", argv[1]);
			throw new AssertionError(msg);
		}

		System.out.println("Finish CopyFits");
    }
}
