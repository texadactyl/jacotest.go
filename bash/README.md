This directory is essentially for those who are developing or maintaining jacotest.

## Set Up Instructions

* Edit file ```common_defs.txt``` to be appropriate for the local environment.  This version of the directory on github works fine for this author's laptop but other users will undoubtedly need to edit the file.
* Make sure that $HOME/go/bin is in the executable search $PATH.

## Common Definitions

The file ```common_defs.txt``` is used by each script to set up its processing.

## Scripts

* builder.sh - Build, vet, and install the jacotest executable into the $HOME/go/bin directory.
* jlinter.sh - Lint all of the Java test source files.
* ujacobin.sh - Install (or replace an existing) jacobin with github contents. Then, re-install the jacobin executable into the $HOME/go/bin directory.

## Command-line Operations

To execute a script,
* Position into the ```bash``` subdirectory.
* Run: ```bash <script name>.sh```.
