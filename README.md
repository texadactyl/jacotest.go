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
* The ```GOPATH``` environment variable must be set to the ```go``` subdirectory full path. After this is done, logout and then re-login.
* Ensure that the full path of the ```bin``` subdirectory of ```go``` is an element of the ```PATH``` environment variable.

Install git, go, and java version 21 such that the executables ```git```, ```go```, ```javac```, ```java``` reside in a directory whose absolute path is an element of the ```PATH``` environment variable.

Install ```jacobin``` such that its executable resides in a directory whose absolute path is an element of the ```PATH``` environment variable.  It is recommended that this directory be the ```bin``` subdirectory under ```go```. Suggestion:
* cd jacobin/src
* go install -v ./...

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
* go install -v ./...
* cd ..
* jacotest -c
* cd ..

You are now positioned at the ```jacotest``` base and ready to test.  First try this: ```jacotest -h```.  You should see something like this:

```
jacotest version: v5.1.0

Built with: go1.24.0
BuildData vcs.revision: aa1866b990a14bb4195c0fb19e7a3e804d8c5301
BuildData vcs.time: 2025-10-18 10:22:58 CDT
BuildData vcs.modified: true

Usage:  jacotest  [ options ]

where
	-h : This display.
	-c : Compile all the test cases.
	-r 1 : For each test case, print the last two results if there was a changed result.
	-r 2 : For each failed test case, print this result if it passed sometime previously.
	-r 3 : For each test case, print the last result.
	-s : Delete database records of non-existing test cases (they were deleted).
	-t N : This is the timeout value of N seconds in executing each test case.  Default: 60.
	-j name : This is the JVM to use in executing all test cases. Default: jacobin.
	     Other JVM names recognized:
	     * openjdk : OpenJDK (aka Hotspot) JVM
	     * galt : Run jacobin in G-alternate mode
	     Note that specifying -j implies parameters -x and -r 1.
	-u : User-defined options to pass to the JVM when -x is specified.
	-v : Verbose logging.
	-x : Execute all test cases.
	     Specifying -x implies parameter -r 1.
	-z : Remove the most recent result for all test cases.
	-M : Generate a run report suitable for viewing on github (normally, not produced).
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

If the default timeout value of 60 seconds for each individual case is insufficient, one can use the ```-t``` parameter to specify a different value.  An example: of running with a 2-minute deadline for each test case:
```jacotest -x -t 120 -j jacobin```

The ```-v``` (verbose logging) parameter is intended for jacotest software debugging.

### Test Case Overview

Each test case occupies a directory immediately under the ```tests``` directory.  The test case source code follows the following conventions:
* For a given test case, there is a main.java file whose main.class file starts execution for the given test case.
* Additional source files (MyClass.java, etc.) can be present for test case modularity.
* Package statements and related subdirectories containing .java source files are limited to specific tests that are testing the JVM's ability to handle Java packaging.

### Command-line options Supplied Automatically by Jacotest to the Java Compiler and the JVM

* -ea : Process assertion statements.
* -classpath : This is the classpath to use for searching for source files and classes. Currently, it has only two location elements:
	* Test case directory of main.java and main.class
 	* HELPERS directory with utilities used by the test case

### Compiling all Test Cases (jacotest -c)

Test cases are compiled in lexical directory name order as the appear under the ```tests``` directory.  For each test case directory tree, the following is performed at the highest level and in the subdirectories if there are any:
* All javap log files are deleted.
* All resident source code is compiled.
* `javap -v` is run for all of the compiled ```.class``` files. The javap output files are stored in the same directory as the corresponding class file.

### Running all Test Cases (jacotest -x)

Test cases are run in lexical directory name order as the appear under the ```tests``` directory.  For each test case (directory), jacotest performs the process described in the "Running a Single Test Case" section.

### User-specified JVM Options (jacotest -x -u)

The command-line option -u allows the user to supply options for JVM execution. When accompanying -x, this has the effect of passing the specified parameters to the JVM execution of every test case in addition to -the normal -ea and -classpath parameters.

Make sure that the particular JVM understands the option specified!

### Running a Single Test Case (manual)

To run an indivdual test case with either JVM,
* Position in the test case top-level directory as the current directory.
* Re-compile the Java source files if needed.
* Execute one of the following two POSIX command lines:
	* ```java -cp .:../HELPERS main```
	* ```jacobin -cp .:../HELPERS main.class```
* On the Windows command line, the syntax is slightly different:
	* ```java -cp .;..\HELPERS main```
	* ```jacobin -cp .;..\HELPERS main.class```

An example of supplying a user-specified JVM Option on a POSIX O/S to jacobin: 
	* ```jacobin -u -732 -cp .:../HELPERS main.class```

### Test Case Results and Reports (jacotest -x)

The following are jacotest -x output:
* Logs of individual test cases
* Test case summary covering all test cases
* Optional run report suitable for viewing on github (only produced if -M is specified on the command line)
* Database updates of the run summaries

### Logs (jacotest -x)

At the beginning of each ```jacotest -x``` run, the ```logs``` directory is cleaned out. For the current run, the detailed results of each test case is recorded as a single file in the logs directory. Files are prefixed with ```PASSED.``` or ```FAILED```, depending on the outcome.

### Test Case Summary

This is a single file per jacotest run (Summary_YYYY-MM-DD_hh.mm.ss_```<jvm>```.txt) residing in the ```reports``` subdirectory. The results of all test cases for a single run are contained in this file. 

### Database

The sqlite database file resides in the ```database``` directory. If the file or its directory does not yet exist, jacotest will automatically create it.

There is one table in the database: ```history```. Each record contained therein represents the result of running a specific test case at the given date and time with the indicated JVM.

History columns:
* test_case: Test case name (directory name under ```tests```)
* jvm: JVM name ("jacobin" or "openjdk")
* date_utc: UTC date (YYYY-MM-DD)
* time_utc: UTC time (hh:mm:ss)
* result: "passed", "failed", or "timeout"
* fail_text: Reason for failure if failed; otherwise SQL "NULL"
