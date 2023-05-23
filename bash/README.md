## Instructions

1. Edit file ```common_defs.txt``` to be appropriate for the local environment.  The version on github works fine on this author's laptop.
2. To execute a script, position into the ```bash``` subdirectory first. Then, ```bash scriptname.sh```.

## Common Definitions

The file ```common_defs.txt``` is used by each script (```bash source```) to set up its processing.

## Scripts

* builder.sh - Build, vet, and install the jacotest executable.
* jlinter.sh - Lint all of the Java test source files.
* ujacobin.sh - Replace existing jacobin with that from github and then re-install the executable.
