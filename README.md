# jacotest.go

TODO: Add more test cases!

Jacotest is intended to be a user-level testing companion to https://github.com/platypusguy/jacobin. 
While Jacobin has internal component-unit testing, it is useful to have a collection of Java-source tests that can be executed automatically 
at the operator command line or through Github Actions.

All of the jactotest source code is written in Go as the name implies.  See the ```src``` directory under the project root.

Some additional utilities have been developed and reside in the ```utilities``` directory under the project root.

# Installation and Operations

### Preliminaries

In all O/S installations, the following steps must be taken prior to use of ```jacotest```:
* If there is no ```go``` subdirectory under the user's home directory, create it.
* If there is no ```bin``` subdirectory under ```go```, create it.
* The ```GOPATH``` environment variable must be set to the ```go``` subdirectory full path. 
* Ensure that the full path of the ```bin``` subdirectory of ```go``` is an element of the ```PATH``` environment variable.

Install git, go, and java version 17 such that the executables ```git```, ```go```, ```javac```, ```java``` reside in a directory whose absolute path is an element of the ```PATH``` environment variable.

Install ```jacobin``` such that its executable resides in a directory whose absolute path is an element of the ```PATH``` environment variable.  It is recommended that this directory be the ```bin``` subdirectory under ```go```. Suggestion:
* cd jacobin/src
* go install -v ./...

### Gcc

* Browse to https://medium.com/@yaravind/go-sqlite-on-windows-f91ef2dacfe
* Follow the instructions for the combined 32- and 64- bit Windows installation. It references https://jmeubank.github.io/tdm-gcc/
* **Do not use the 32-bit-only installation** ; use this one: **MinGW-w64 based**
* Be sure to put the resultant bin subdirectory in the PATH.

### Sqlite Database Run-time and Database Browser

Jacotest uses an sqlite database to store test case results in addition to logging and summary reports - discussed later on. The following are steps to install the sqlite run-time and the sqlite browser:
* Click on https://www.sqlitetutorial.net/download-install-sqlite/ . Follow the instructions.
* Be sure to put the resultant bin subdirectory in the PATH.
* Go to https://download.sqlitebrowser.org/DB.Browser.for.SQLite-3.12.2-win64.msi and do the usual Windows MSI installation.

### Make PATH Updates Permanent

In order to make sure that all of the PATH updates are effective,
* Logout and re-login afterwards.
* Check the new elements of the PATH variable: all there?
     - go/bin under the home directory with the ```jacobin``` executable present
     - executables ```git```, ```go```, ```javac```, ```java``` reside in a directory whose absolute path is an element of the ```PATH```
     - GCC on Windows: probably, C:\TDM-GCC-64\bin
     - Sqlite3 on Windows: probably, C:\SQLITE\bin

### Installation of jacotest

Open a terminal window / command prompt.

* Assuming that you wish to install jacotest under the home directory,
     - cd $HOME (on Linux, MacOS, or Unix)
     - cd %HOMEPATH% (Windows)
* git clone https://github.com/texadactyl/jacotest.go
* cd jacotest.go/src
* go get github.com/mattn/go-sqlite3
* go install -v ./...
* cd ..

You are now positioned at the ```jacotest``` base and ready to test.  First try this: ```jacotest -h```.  You should see something like this:

```
Usage:  jacotest  [-h]  [-x]  [-N]  [-v]  [-t NSECS]  [ -j { openjdk | jacobin } ]

where
	-h : This display
	-N : No need to recompile the test cases
	-x : Execute all of the tests
	-v : Verbose logging
	-t : This is the timeout value in seconds (deadline) in executing all test cases.  Default: 60
	-j : This is the JVM to use in executing all test cases.  Default: openjdk
jacotest version: 2.5.2
Built with: go1.21
BuildData vcs.revision: 0a2d9a48575b3864d5c2905c0b8e61bb2e56f78f
BuildData vcs.time: 2023-08-09 12:44:09 CDT
BuildData vcs.modified: true
```

### Jacotest Operations

Jacotest can run the test case collection against 2 different JVMs:
* jacobin
* OpenJDK's JVM with an executable named "java"

For example, to test jacobin against the test case collection: 
```jacotest -x```

Note that jacobin is the default JVM.

To run tests against the OpenJDK JVM, 
```jacotest -x -j openjdk```

The reports and logs can be discarded as follows:
```jacotest -c```

If the default timeout value of 60 seconds for each individual case is insufficient, one can use the ```-t``` parameter to specify a different value.  An example: of running with a 2-minute deadline for each test case:
```jacotest -x -t 120 -j jacobin```

The ```-v``` (verbose logging) parameter is intended for jacotest software debugging.

# Test Case Overview

Each test case occupies a directory immediately under the directory ```tests```.  The test case source code follows the following conventions:
* For a given test case, there is a main.java file whose main.class file starts execution for the given test case following a successful compilation.
* Additional source files (helper.java, etc.) can be present for test case modularity.
* Package statements and related subdirectories containing .java source files are limited to specific tests that are testing the JVM's ability to handle Java packaging.

# Test Case Run

Test cases are run in lexical directory name order as the appear under the ```tests``` directory.  For each test case (directory), execution is a multi-step process:
1) Compilation of all *.java files with ```javac```.
2) Assuming that compilation was successful for a given test case, then `javap -v` is run for all of the compiled ```.class``` files. The javap output files are stored in the same directory as the corresponding class file.
3) If compilation is successful, then execution proceeds under the control of one of two JVMs: ```java``` or ```jacobin```.

# Test Case Results and Reports

The following are jacotest output:
* Logs of individual test cases
* Test case summary covering all test cases
* Database holding all of the run summaries

### Logs

At the beginning of each run, the ```logs``` directory is cleaned out. For the current run, the detailed results of each test case is recorded as a single file in the logs directory. Files are prefixed with ```PASSED.``` or ```FAILED```, depending on the outcome.

### Test Case Summary

This is a single file per jacotest run (Summary_YYYY-MM-DD_hh.mm.ss_```<jvm>```.txt) residing in the ```reports``` subdirectory. The results of all test cases for a single run are contained in this file. 

### Database

The sqlite database file resides in the ```database``` directory. If the file or its directory does not yet exist, jacotest will automatically create it.

There is one table called ```history```. Each record contained therein represents the result of running a specific test case with the indicated JVM.

History columns:
* Test case name (E.g. JACOBIN-0329-nonfinals)
* JVM name (jacobin, openjdk, etc.)
* UTC date (YYYY-MM-DD)
* UTC time (hh:mm:ss)
* Result: passed, failed, or timeout
* Failure text if failed; otherwise nil
