/*
 * Looks for duplicate files based on CRC-32 checksumming.
 * Project requires JDK 8 or later.
 *
 * Copyright (c) 20 by Andrew Binstock. All rights reserved.
 * Licensed under the Creative Commons Attribution, Share Alike license
 * (CC BY-SA). Consult: https://creativecommons.org/licenses/by-sa/4.0/
 */

import java.io.IOException;
import java.util.Objects;

/**
 * Simple class that takes an array of filenames,
 * runs a checksum on the files, and puts the result into
 * a table.
 *
 * @author alb
 * @recoded by chatGPT.
 * @hacked-by R J Elkins
 */
public class FilesChecksum {

    private final LongStringListTable checksumTable;
    private final String[] filenames;

    /**
     * @param files The files to be checksummed
     * @param table The table where the filenames and checksums are inserted
     */
    public FilesChecksum(String[] files, LongStringListTable table) {
        filenames = Objects.requireNonNull(files);
        checksumTable = Objects.requireNonNull(table);
    }

    /**
     * Calls the checksum calculation routine for each file in the array of filenames
     * and inserts the checksum and the filename into the checksumTable.
     */
    public void go() {
        for (int i = 0; i < filenames.length; i++) {
            String file = filenames[i];
            try {
                checksumTable.insertEntry(file, new FileChecksum(file).calculate());
            } catch (IOException ioe) {
                // error messages have already been shown to the user; continue with the loop
            }
        }
    }
}

