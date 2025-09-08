/*
 * Looks for duplicate files based on CRC-32 file sizes and checksums.
 * Project requires JDK 8 or later.
 *
 * Copyright (c) 2017-20 by Andrew Binstock. All rights reserved.
 * Licensed under the Creative Commons Attribution, Share Alike license
 * (CC BY-SA). Consult: https://creativecommons.org/licenses/by-sa/4.0/
 */
import java.util.*;

/**
 * The main line. All processing starts here.
 * @author alb (Andrew Binstock @platypusguy)
 * @hacked-by R J Elkins
 */
public class main {

    /**
     * @param args the command-line arguments. They can be:
     *    * one or more directories to scan for duplicate files
     *    * -nosubdirs which says don't check subdirectories
     *    * -quiet which says don't show files that are not duplicates
     *    * -help/-h which prints usage instructions
     */
    public static void main( final String[] args )
    {
        // default is to visit subdirectories
        boolean nosubdirs = false;
        boolean quiet = false;

		System.out.println("FileDedupe was adapted for use with jacotest.go");
        printCopyright();
        
        //if( args.length == 0 ) {
            //showUsage();
            //return;
        //}

        List<String> argList = Arrays.asList(args);
        if( argList.contains( "-h" )  || argList.contains( "-help" ) ||
            argList.contains( "--h" ) || argList.contains( "--help" )) {
            showUsage();
            return;
        }

        String dirs[] = {};
        
        if (args.length == 0) {
            dirs = append(dirs, System.getenv("JAVA_HOME"));
            quiet = true;
        }
        else {
		    for( String arg : args ) {
		        if( !arg.startsWith("-") ) {
		            dirs = append(dirs, arg);
		        }
		        else {
		            if( arg.equalsIgnoreCase( "-nosubdirs" ) ||
		                arg.equalsIgnoreCase( "--nosubdirs")) {
		                nosubdirs = true;
		            }
		            else if( arg.equalsIgnoreCase( "-quiet" ) ||
		                arg.equalsIgnoreCase( "--quiet")) {
		                quiet = true;
		            }
		            else {
		                System.err.println( "Invalid command: " + arg );
		            }
		        }
		    }
        }

        // Create the sizes table, where the file sizes are stored
        LongStringListTable sizesTable = new LongStringListTable();

        // Create the filesize retrieval engine
        FileSizer fileSizer = new FileSizer();

        // Get the file sizes for all files in each specified directory
        if( dirs.length > 0 ) {
            for( String dir : dirs )
                fileSizer.loadFileSizes( dir, nosubdirs, quiet, sizesTable );
        }
        else {  //happens only if a single dash option other than -h is specified
            System.err.println( "Error: no directory specified. Exiting" );
        }

        // Create the dupe table, where file checksums are stored
        LongStringListTable dupesTable = new LongStringListTable();

        // sizesTable now holds all the filenames and the corresponding file sizes
        /*sizesTable.values().stream() // get the lists of files for each size
            .filter( s -> s.size() > 1 )   // filter for lists of more than 1 file for a given size
            .forEach( s -> new FilesChecksum( s, dupesTable ).go() );  // checksum those files
        */
        String[][] allFileArrays = sizesTable.getValuesArray();  // get all arrays of filenames

        for (int i = 0; i < allFileArrays.length; i++) {
            String[] filesForSize = allFileArrays[i];

            if (filesForSize != null && filesForSize.length > 1) {
                // Compute checksums for files with the same size
                FilesChecksum fc = new FilesChecksum(filesForSize, dupesTable);
                fc.go();
            }
        }        
                
        // Scan the dupesTable and print out all duplicates to stdout
        DupesOutput dupesList = new DupesOutput();
        int dupesCount = dupesList.showDupes( dupesTable );

        System.out.println( "Number of duplicates found: " + dupesCount );
        
        Checkers.theEnd(0);
    }


    // Append one string to an existing String array.
    private static String[] append(String[] oldArray, String newString) {
        String[] newArray = new String[oldArray.length + 1];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        newArray[oldArray.length] = newString;
        return newArray;
    }
    
    /**
     * Simply prints the copyright
     */
    private static void printCopyright() {
        System.out.println(
            "FileDedupe v. 2.0 (c) Copyright 2017-20 Andrew Binstock. All rights reserved.\n" );
    }

    /**
     * Explains program usage
     */
    private static void showUsage() {

        System.out.println(
            "FileDedupe finds duplicate files in a diretory or disk drive.\n" +
            "arguments: one or more directories to process;\n" +
            "           -nosubdirs do not check subdirectories (default: checks all subdirs)\n" +
            "           -quiet do not show every file encountered (default: shows every file encountered)\n" +
            "           -h or -help prints this message"
        );
    }
}
