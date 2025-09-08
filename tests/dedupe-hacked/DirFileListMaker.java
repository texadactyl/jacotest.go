/*
 * Looks for duplicate files based on CRC-32 checksumming.
 * Project requires JDK 8 or later.
 *
 * Copyright (c) 2017-20 by Andrew Binstock. All rights reserved.
 * Licensed under the Creative Commons Attribution, Share Alike license
 * (CC BY-SA). Consult: https://creativecommons.org/licenses/by-sa/4.0/
 */

import java.io.File;
import java.security.InvalidParameterException;

/**
 * Accepts a path and walks the entire path (including subdirectories)
 * and creates an array of all files in the path.
 *
 * All exceptions are caught in this class and the user message is emitted here.
 *
 * Rewritten by chatGPT to avoid java.nio, lists, and streams.
 * Hacked by R J Elkins.
 */
class DirFileListMaker {

    String[] go(String dirPath, boolean skipSubDirs, boolean quiet) {
        if (dirPath == null || dirPath.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("*** ERROR, Directory to process is null or empty in ");
            sb.append(this.getClass().getSimpleName());
            throw new AssertionError(sb.toString());
        }

        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            sb.append("*** ERROR, Path is not a valid directory: ");
            sb.append(dirPath);
            throw new AssertionError(sb.toString());
        }

        try {
            int totalFiles = countFiles(dir, skipSubDirs);
            String[] fileSet = new String[totalFiles];
            populateFiles(dir, skipSubDirs, quiet, fileSet, new int[]{0});
            return fileSet;
        } catch (Throwable t) {
            StringBuilder sb = new StringBuilder();
            sb.append("*** ERROR, creating fileset in ");
            sb.append(dirPath);
            sb.append(".");
            throw new AssertionError(sb.toString());
        }
    }

    private int countFiles(File dir, boolean skipSubDirs) {
        int count = 0;
        File[] children = dir.listFiles();
        if (children == null) return 0;

        for (File child : children) {
            if (child.isFile()) count++;
            if (child.isDirectory() && !skipSubDirs) {
                count += countFiles(child, skipSubDirs);
            }
        }
        return count;
    }

    private void populateFiles(File dir, boolean skipSubDirs, boolean quiet, String[] fileSet, int[] index) {
        File[] children = dir.listFiles();
        if (children == null) return;

        for (File child : children) {
            if (child.isFile()) {
                fileSet[index[0]++] = child.getAbsolutePath();
                if (!quiet) {
                    System.out.println(child.getAbsolutePath());
                }
            } else if (child.isDirectory() && !skipSubDirs) {
                populateFiles(child, skipSubDirs, quiet, fileSet, index);
            }
        }
    }
}
