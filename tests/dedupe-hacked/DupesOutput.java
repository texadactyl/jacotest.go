/*
 * Looks for duplicate files based on CRC-32 checksumming.
 *
 * Copyright (c) 2020 by Andrew Binstock. All rights reserved.
 * Licensed under the Creative Commons Attribution, Share Alike license
 * (CC BY-SA). Consult: https://creativecommons.org/licenses/by-sa/4.0/
 */

/**
 * Class to print the duplicates to stdout
 * Rewritten to avoid Collection, Set, ArrayList, lambdas, and invokedynamic string concatenation
 * Works with LongStringListTable that uses arrays for values.
 *
 * @author alb
 * @Recoded by chatGPT.
 * @hacked-by R J Elkins
 */
public class DupesOutput {

    public DupesOutput() {}

    /**
     * The class that does the work
     * @param dupes the table of file checksums, which shows the duplicate files
     * @return the number of duplicates found
     */
    public int showDupes(LongStringListTable dupes) {

        int dupesCount = 0;

        // get an array of all keys
        long[] keys = dupes.getKeyArray();

        // go down the list looking for checksums with more than one associated file
        for (int k = 0; k < keys.length; k++) {
            long key = keys[k];
            String[] paths = dupes.getEntry(key);
            if (paths != null && paths.length > 1) {
                dupesCount += paths.length;

                System.out.println("These files are the same:");

                // simple insertion sort for strings (natural ascending order)
                for (int i = 1; i < paths.length; i++) {
                    String temp = paths[i];
                    int j = i - 1;
                    while (j >= 0 && paths[j].compareTo(temp) > 0) {
                        paths[j + 1] = paths[j];
                        j--;
                    }
                    paths[j + 1] = temp;
                }

                for (int i = 0; i < paths.length; i++) {
                    System.out.print("\t");
                    System.out.println(paths[i]);
                }

                System.out.println();
            }
        }

        return dupesCount;
    }
}

