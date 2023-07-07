// hacked from http://www.hq.eso.org/~pgrosbol/fits_java/jfits.html
/* @(#)FitsWCS.java     $Revision: 1.6 $    $Date: 2008-04-18 13:10:23 $
 *
 * Copyright (C) 2003 European Southern Observatory 
 * License:  GNU General Public License version 2 or later
 */
//package org.eso.fits;

/** FitsWCS class maps FITS WCS keywords to internal representatios and
 *  provides basic transformations.  The transformations are described in
 *  Greisen & Calabretta (2002, A&A 395, 1061) for the basic representation
 *  and in Calabretta & Greisen (2002, A&A 395, 1077) for transformations.
 *
 *  Note: the current implementation supports only the following non-linear
 *  transformations: TAN, STG, and ARC.
 *
 *  Note: Rotations defined by the CROTAi keyword are not applied!
 *
 *  Warning: The current implementation is experimental and should not be
 *  used for production.  It will fail for none trivial headers.
 *
 *  @version $Revision: 1.6 $ $Date: 2008-04-18 13:10:23 $
 *  @author  P.Grosbol, ESO, <pgrosbol@eso.org>
 */
public class FitsWCS {

    public final static int NOT = 0;
    public final static int LIN = 1;
    public final static int TAN = 2;
    public final static int ARC = 3;
    public final static int STG = 4;

    private final static int MPS = 20;

    protected int        type;
    protected int        nax = 0;
    protected int[]      cproj;
    protected double[]   crpix;
    protected double[]   crval;
    protected double[]   cdelt;
    protected double[]   crota;
    protected double[]   fip;
    protected double[]   thp;
    protected String[]   ctype;
    protected double[][] cdMatrix;
    protected double[][] pcMatrix;
    protected boolean    hasPcMatrix = false;
    protected boolean    hasCdMatrix = false;

    protected double[]   amdx = new double[MPS];
    protected double[]   amdy = new double[MPS];

    /** Default constructor for FitsWCS class.
     */
    public FitsWCS() {
	type = Fits.UNKNOWN;
    }

    /** Constructor for FitsWCS class given a FITS header with 
     *  associated data unit as a file.
     *
     *  @param header  FitsHeader object with the image header
     */
    public FitsWCS(FitsHeader header) {
	this();
	setHeader(header, ' ');
    }

    /** Constructor for FitsWCS class given a FITS header with 
     *  associated data unit as a file.
     *
     *  @param header  FitsHeader object with the image header
     *  @param ver     version of WCS i.e. ' ' or 'A'..'Z'
     */
    public FitsWCS(FitsHeader header, char ver) {
	this();
	setHeader(header, ver);
    }

    /** Constructor for FitsWCS given number of axes
     *
     *  @param nax  Number of axes in data matrix
     */
    public FitsWCS(int nax) {
	this();
	init(nax);
    }

    /** Define FITS header for FitsWCS object.
     *
     *  @param header  FitsHeader object with the image header
     *  @param ver     version of WCS i.e. ' ' or 'A'..'Z'
     */
    public void setHeader(FitsHeader header, char ver) {
	type = header.getType();

        FitsKeyword kw = header.getKeyword("NAXIS");
	nax = (kw == null) ? 0 : kw.getInt();
        kw = header.getKeyword("WCSAXES");
	nax = (kw == null) ? nax : kw.getInt();
 	init(nax);

	String sver = String.valueOf(ver).toUpperCase().trim();
	for (int j=1; j<=nax; j++) {
            kw = header.getKeyword("CRPIX"+j+sver);
            crpix[j-1] = (kw == null) ? 0.0 : kw.getReal();

            kw = header.getKeyword("CRVAL"+j+sver);
            crval[j-1] = (kw == null) ? 0.0 : kw.getReal();
//            kw = header.getKeyword("PV"+j+"1"+sver);
//            fip[j-1] = (kw == null) ? 0.0 : kw.getReal();
//            kw = header.getKeyword("PV"+j+"2"+sver);
//            thp[j-1] = (kw == null) ? 0.0 : kw.getReal();

            kw = header.getKeyword("CDELT"+j+sver);
            cdelt[j-1] = (kw == null) ? 1.0 : kw.getReal();
            cdMatrix[j-1][j-1] = cdelt[j-1];

            kw = header.getKeyword("CTYPE"+j+sver);
	    ctype[j-1] = (kw == null) ? "        " : kw.getString();
	    if (7<ctype[j-1].length()) {
		String wctype = ctype[j-1].substring(5, 8);
		if (wctype.equals("TAN")) {
		    cproj[j-1] = TAN;
		} else if (wctype.equals("ARC")) {
		    cproj[j-1] = ARC;
		} else {
		    cproj[j-1] = NOT;
		}
	    } else {
		cproj[j-1] = LIN;
	    }

            for (int i=1; i<=nax; i++) {
		kw = header.getKeyword("CD"+j+"_"+i+sver);
		if (kw != null) {
		    cdMatrix[j-1][i-1] = kw.getReal();
		    hasCdMatrix = true;
		}
            }

            for (int i=1; i<=nax; i++) {
		kw = header.getKeyword("PC"+j+"_"+i+sver);
		if (kw != null) {
		    pcMatrix[j-1][i-1] = kw.getReal();
		    hasPcMatrix = true;
		}
            }
        }

       for (int j=1; j<MPS; j++) {
	   kw = header.getKeyword("AMDX"+j);
	   amdx[j-1] = (kw == null) ? 0.0 : kw.getReal();
	   kw = header.getKeyword("AMDY"+j);
	   amdy[j-1] = (kw == null) ? 0.0 : kw.getReal();
       }
    }

    /** Initiate internal WCS data structures.
     *
     *  @param   nax  No. of axies of data array
     */
    private void init(int nax) {
        cproj = new int[nax];
        crpix = new double[nax];
        crval = new double[nax];
        cdelt = new double[nax];
        fip   = new double[nax];
        thp   = new double[nax];
        ctype = new String[nax];
        cdMatrix = new double[nax][nax];
        pcMatrix = new double[nax][nax];
        ctype = new String[nax];

	for (int n=0; n<nax; n++) {
	    cproj[n] = LIN;
	    crpix[n] = 0.0;
	    crval[n] = 0.0;
	    fip[n]   = 0.0;
	    thp[n]   = 0.0;
	    cdelt[n] = 1.0;
	    ctype[n] = "        ";
            for (int i=0; i<nax; i++) {
                cdMatrix[n][i] = (i==n) ? 1.0 : 0.0;
                pcMatrix[n][i] = (i==n) ? 1.0 : 0.0;
            }    
	}
    }

    /** get projection type of WCS transformation of given axis.
     *
     *  @param   nax  no. of axis to enquire (0..naxis-1)
     *  @return  type of World Coordinate System
     */
    public int getType(int nax) {
	return cproj[nax];
    }

    /** Compute World Coordinates from pixel coordinates.
     *
     *  @param   pix  Array with pixel coordinates
     *  @return  array with pixel location in world coordinates
     */
    public double[] toWCS(double[] pix) {
	double[] p = new double[nax];
	double[] wcs = new double[nax];

	if (nax < 2) return null;

	for (int j=0; j<nax; j++) p[j] = pix[j] - crpix[j];
	if (hasPcMatrix) {
	    wcs = matrixMult(pcMatrix, p);
	    for (int j=0; j<nax; j++) wcs[j] *= cdelt[j];
	} else if (hasCdMatrix) {
	    wcs = matrixMult(cdMatrix, p);
	} else {
	    for (int j=0; j<nax; j++) wcs[j] = p[j] * cdelt[j];
	}

	double  fi = 0.0;
	double  th = 0.0;
	double  ro = 0.0;
	double  sdp = Math.sin(Math.toRadians(crval[1]));
	double  cdp = Math.cos(Math.toRadians(crval[1]));
	double  sap = Math.sin(Math.toRadians(crval[0]));
	double  cap = Math.cos(Math.toRadians(crval[0]));
	switch (cproj[0]) {
	    case ARC:
			break;
	    case STG:
			break;
	    case TAN:
			ro = Math.sqrt(wcs[0]*wcs[0] + wcs[0]*wcs[0]);
			fi = Math.atan2( -wcs[1], wcs[0]);
			th = Math.atan(1.0/Math.toRadians(ro));
			double ccdfi = Math.cos(th) * 
				Math.cos(fi - Math.toRadians(crval[1]));
			double csdfi = Math.cos(th) * 
				Math.sin(fi - Math.toRadians(crval[1]));
			wcs[0] = Math.atan2(Math.sin(th)*cdp - sdp*ccdfi, -csdfi);
			wcs[0] = Math.toDegrees(wcs[0]) + crval[0];
			wcs[1] = Math.toDegrees(Math.asin(Math.sin(th)*sdp+cdp*ccdfi));
			break;
	    case LIN:
			for (int j=0; j<nax; j++) wcs[j] += crval[j];
	}

	return wcs;
    }

    /** Compute pixel coordinates from a set of World Coordinates.
     *
     *  @param  wcs  Array with World Coordinates
     */
    public double[] toPixel(double[] wcs) {
	double[] pix = new double[nax];
	double[] wc  = new double[nax];

	for (int n=0; n<nax; n++) {
	    pix[n] = (wcs[n]-crval[n])/cdelt[n] + crpix[n];
	}
	return pix;
    }

    private double[] matrixMult(double[][] mtx, double[] vec) {
	int nv = vec.length;
	double[] res = new double[nv];

	for (int j=0; j<nv; j++) {
	    res[j] = 0;
	    for (int i=0; i<nv; i++) {
		res[j] += mtx[j][i] * vec[i];
	    }
	}
	return res;
    }
}





