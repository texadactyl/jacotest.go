// hacked from http://www.hq.eso.org/~pgrosbol/fits_java/jfits.html
/*
 * @(#)ReadFits.java   $Revision: 1.5 $  $Date: 2007-01-04 09:32:23 $
 *
 * Copyright (C) 1999 European Southern Observatory
 * License:  GNU General Public License version 2 or later
 */
//package org.eso.fits;

import java.util.*;
import java.io.*;

/**
 * TestFits class provides a static main method for testing the
 * FITS class library.  It also shows a typical usage of the
 * classes.
 *
 * @author P.Grosbol, ESO, <pgrosbol@eso.org>
 * @version $Id: TestFits.java,v 1.5 2007-01-04 09:32:23 pgrosbol Exp $
 */
public class main { // formerly called TestFits
    /**
     * Static method for testing the FITS class library.
     *
     * @param argv array of arguments i.e.  FITS files
     */
    
    public static void main(String[] argv) {
        String[] argList = {"test.fits"};
        int errorCount = 0;

        System.out.println("-- Test FITS files --------");
        FitsFile file = null;
        for (int na = 0; na < argList.length; na++) {
            try {
                file = new FitsFile(argList[na]);
            } catch (FitsException e) {
                System.out.println("Error: is not a FITS file >"
                        + argList[na] + "<");
                continue;
            } catch (IOException e) {
                System.out.println("Error: cannot open file >" + argList[na] + "<");
                continue;
            }

            int noHDU = file.getNoHDUnits();
            System.out.println("FITS file has " + noHDU + " HDUnits");

            for (int i = 0; i < noHDU; i++) {
                FitsHDUnit hdu = file.getHDUnit(i);
                FitsHeader hdr = hdu.getHeader();
                int noKw = hdr.getNoKeywords();
                int type = hdr.getType();
                int size = (int) hdr.getDataSize();
                System.out.println("  " + i + ": >" + hdr.getName()
                        + "< of type >" + Fits.getType(type)
                        + "< with " + noKw + " keywords"
                        + " and " + size + " bytes of data");
                System.out.println("   Keywords:");
                ListIterator <FitsKeyword> itr = hdr.getKeywords();
                while (itr.hasNext()) {
                    FitsKeyword kw = itr.next();
                    String kwName = kw.getName();
                    System.out.print("     " + kwName);
                    switch (kw.getType()) {
                        case FitsKeyword.COMMENT:
                            System.out.print("(C) " + kw.getComment());
                            break;
                        case FitsKeyword.STRING:
                            String wstring = kw.getString();
                            System.out.print("(S)= '" + wstring + "'");
                            if (kwName.equals("ORIGIN")) {
                                if (! wstring.equals("STScI/MAST")) {
                                    System.out.print("\n\t*** ERROR, expected ORIGIN == 'STScI/MAST'");
                                    errorCount += 1;
                                }
                            }
                            if (kw.getName().equals("CREATOR")) {
                                if (! wstring.equals("astrocut")) {
                                    System.out.print("\n\t*** ERROR, expected CREATOR == 'astrocut'");
                                    errorCount += 1;
                                }
                            }
                            break;
                        case FitsKeyword.BOOLEAN:
                            System.out.print("(B)= " + kw.getBool());
                            break;
                        case FitsKeyword.INTEGER:
                        	int wint = kw.getInt();
                            System.out.print("(I)= " + wint);
                            if (kwName.equals("SCCONFIG")) {
                                if (wint != 121) {
                                    System.out.print("\n\t*** ERROR, expected SCCONFIG == 121");
                                    errorCount += 1;
                                }
                            }
                            break;
                        case FitsKeyword.REAL:
                        	double wreal = kw.getReal();
                            System.out.print("(R)= " + wreal);
                            if (kwName.equals("EQUINOX")) {
                                if (wreal != 2000.0) {
                                    System.out.print("\n\t*** ERROR, expected EQUINOX == 2000.0");
                                    errorCount += 1;
                                }
                            }
                            break;
                        case FitsKeyword.DATE:
                        	String wdate = kw.getString();
                            System.out.print("(D)= " + wdate);
                            if (kwName.equals("DATE-END")) {
                                if (! wdate.equals("2018-09-19T23:58:59")) {
                                    System.out.print("\n\t*** ERROR, expected DATE-END == 2018-09-19T23:58:59");
                                    errorCount += 1;
                                }
                            }
                            break;
                        default:
                            System.out.print("\n\t*** ERROR, switch (kw.getType()) (default), value= " + kw.getString());
                            errorCount += 1;
                    }
                    if (0 < kw.getComment().length()
                            && (kw.getType() != FitsKeyword.COMMENT)) {
                        System.out.print(" / " + kw.getComment());
                    }
                    System.out.println();
                }

                if (type == Fits.IMAGE) {
                    System.out.println("\n  Check data matrix "
                            + "- compute mean and rms");
                    FitsMatrix dm = (FitsMatrix) hdu.getData();
                    int naxis[] = dm.getNaxis();
                    double crval[] = dm.getCrval();
                    double crpix[] = dm.getCrpix();
                    double cdelt[] = dm.getCdelt();

                    System.out.println("  Dimension of matrix: "
                            + naxis.length);
                    for (int n = 0; n < naxis.length; n++)
                        System.out.println("   Axis " + n + ": " + naxis[n]
                                + ",  " + crpix[n] + ",  "
                                + crval[n] + ",  " + cdelt[n]);
                    System.out.println("\n");

                    int nv, off, npix;
                    int nval = dm.getNoValues();
                    if (0 < nval) {
                        int ncol = naxis[0];
                        int nrow = nval / ncol;
                        System.out.println(" Npixel,row,col: " + nval
                                + ", " + nrow + ", " + ncol);
                        float data[] = new float[ncol];
                        double mean, rms, val;

                        off = nv = npix = 0;
                        mean = rms = 0.0;
                        long time = System.currentTimeMillis();
                        for (int nr = 0; nr < nrow; nr++) {
                            try {
                                dm.getFloatValues(off, ncol, data);
                                for (int n = 0; n < ncol; n++) {
                                    val = data[n];
                                    npix++;
                                    mean += val;
                                    rms += val * val;
                                }
                            } catch (FitsException e) {
                            }

                            off += ncol;
                        }
                        mean = mean / npix;
                        rms = rms / npix - mean * mean;
                        rms = ((0.0 < rms) ? Math.sqrt(rms) : 0.0);
                        float dtime =
                                (float) (1000.0 * (System.currentTimeMillis() - time) /
                                        ((double) nval));
                        System.out.println("  Mean: " + (float) mean +
                                ", rms: " + (float) rms +
                                ", Time: " + dtime
                                + " S/Mp, Pixels: " + npix);
                    }
                } else if (type == Fits.BTABLE || type == Fits.ATABLE) {
                    System.out.println("\n  Check table data - list columns");
                    FitsTable dm = (FitsTable) hdu.getData();
                    int nrow = dm.getNoRows();
                    int ncol = dm.getNoColumns();
                    FitsColumn col[] = new FitsColumn[ncol];
                    System.out.println("  Columns: " + ncol
                            + ", Rows: " + nrow);
                    for (int n = 0; n < ncol; n++) {
                        col[n] = dm.getColumn(n);
                        System.out.print("  " + n + " >"
                                + col[n].getLabel() + "<, ");
                        System.out.print(col[n].getRepeat() + " ");
                        System.out.print(col[n].getDataType() + ", >");
                        System.out.print(col[n].getDisplay() + "<, >");
                        System.out.println(col[n].getUnit() + "<");

                        if (col[n].getDataType() == 'F'
                                || col[n].getDataType() == 'E'
                                || col[n].getDataType() == 'D') {
                            int npix = 0;
                            double mean, rms, val;
                            mean = rms = 0.0;
                            long time = System.currentTimeMillis();
                            for (int nr = 0; nr < nrow; nr++) {
                                val = col[n].getReal(nr);
                                if (Double.isNaN(val)) continue;
                                npix++;
                                mean += val;
                                rms += val * val;
                            }
                            float dtime =
                                    (float) (1000.0 * (System.currentTimeMillis()
                                            - time) / ((double) nrow));
                            mean = mean / npix;
                            rms = rms / npix - mean * mean;
                            rms = ((0.0 < rms) ? Math.sqrt(rms) : 0.0);
                            System.out.println("      no,mean,rms: " + npix
                                    + ", " + (float) mean + ", "
                                    + (float) rms + "; "
                                    + dtime + " S/Mp");
                        } else if (col[n].getDataType() == 'I'
                                || col[n].getDataType() == 'J'
                                || col[n].getDataType() == 'B') {
                            int npix = 0;
                            double mean, rms, val;
                            mean = rms = 0.0;
                            long time = System.currentTimeMillis();
                            for (int nr = 0; nr < nrow; nr++) {
                                val = col[n].getInt(nr);
                                if (val == Long.MIN_VALUE) continue;
                                npix++;
                                mean += val;
                                rms += val * val;
                            }
                            float dtime =
                                    (float) (1000.0 * (System.currentTimeMillis()
                                            - time) / ((double) nrow));
                            mean = mean / npix;
                            rms = rms / npix - mean * mean;
                            rms = ((0.0 < rms) ? Math.sqrt(rms) : 0.0);
                            System.out.println("      no,mean,rms: " + npix
                                    + ", " + (float) mean + ", "
                                    + (float) rms + "; "
                                    + dtime + " S/Mp");
                        }
                    }
                }
            }
        }

        Checkers.theEnd(errorCount);
    }
}
