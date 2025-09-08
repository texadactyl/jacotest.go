/*
 * Scans directories (and optionally subdirectories) for all files and looks up their size
 *
 * Copyright (c) 2020 by Andrew Binstock. All rights reserved.
 * Licensed under the Creative Commons Attribution, Share Alike license
 * (CC BY-SA). Consult: https://creativecommons.org/licenses/by-sa/4.0/
 */
 
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Engine that runs through a directory (and optionally subdirectories),
 * extracts all the file names and size of the files and puts both into
 * a table
 *
 * @author alb
 * @hacked-by R J Elkins
*/
public class FileSizer {

    public FileSizer() {}

   public static String[] getAllSubPaths(String dirPath) {
        File root = new File(dirPath);

        if (!root.exists() || !root.isDirectory()) {
            System.err.println("Invalid directory: " + dirPath);
            return new String[0];
        }

        int total = countFiles(root);
        String[] paths = new String[total];
        populatePaths(root, paths, new int[]{0}); // use array to pass index by reference
        return paths;
    }

    private static int countFiles(File dir) {
        int count = 0;
        File[] children = dir.listFiles();
        if (children == null) return 0;

        for (File child : children) {
            count++; // count this file or directory
            if (child.isDirectory()) {
                count += countFiles(child);
            }
        }
        return count;
    }

    private static void populatePaths(File dir, String[] paths, int[] index) {
        File[] children = dir.listFiles();
        if (children == null) return;

        for (File child : children) {
            paths[index[0]++] = child.getAbsolutePath();
            if (child.isDirectory()) {
                populatePaths(child, paths, index);
            }
        }
    }

    /**
     * Creates a lits of all files in a directory (and, optionally its subdirectories),
     * gets the length of the files and puts the length and filename into a table.
     * @param dir  the directory to scan
     * @param nosubdirs  toggle saying whether to visit subdirectories
     * @param sizesTable the table that holds the file sizes and the corresponding filenames
     */
    public void loadFileSizes( String dirPath, boolean nosubdirs, boolean quiet, LongStringListTable sizesTable ) {
    
        String allPaths[] = getAllSubPaths(dirPath);

        for (String fpath : allPaths) {  //for each file get the file length and add it to sizesTable
            File file = new File(fpath);
            if (file.isFile())
                sizesTable.insertEntry( fpath, file.length() );
        }
        //note: we don't worry about an empty directory here.
    }
}
