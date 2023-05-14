# jacotest.go

TODO: Add more test cases!

Jacotest is intended to be a user-level testing companion to https://github.com/platypusguy/jacobin. 
While Jacobin has internal component-unit testing, it is useful to have a collection of Java-source tests that can be executed automatically 
at the operator command line or through Github Actions.

All of the jactotest source code is written in Go as the name implies.  See the ```src``` directory under the project root.

# Installation and Operations

### Preliminaries

In all O/S installations, the following steps must be taken prior to use of ```jacotest```:
* If there is no ```go``` subdirectory under the user's home directory, create it.
* If there is no ```bin``` subdirectory under ```go```, create it.
* The ```GOPATH``` environment variable must be set to the ```go``` subdirectory full path. 
* Ensure that the full path of the ```bin``` subdirectory of ```go``` is an element of the ```PATH``` environment variable.

Install git, go, and java version 17 such that the executables ```git```, ```go```, ```javac```, ```java``` reside in a directory whose absolute path is an element of the ```PATH``` environment variable.

Install ```jacobin``` such that its executable resides in a directory whose absolute path is an element of the ```PATH``` environment variable.  It is recommended that this directory be the ```bin``` subdirectory under ```go```.

### Installation of jacotest

Open a terminal window / command prompt.

* Change directory to user's home directory
     - cd $HOME (on Linux, MacOS, or Unix)
     - cd %HOMEPATH% (Windows)
* git clone https://github.com/texadactyl/jacotest.go
* cd jacotest.go/src
* go install
* cd ..

You are now positioned at the ```jacotest``` base and ready to test.  First try this: ```jacotest -h```.  You should see something like this:

```
Usage:  jacotest  [-h]  [-c]  [-x]  [-v]  [-t NSECS]  [ -j { openjdk | jacobin } ]

where
	-h : This display
	-c : Clean all of the .class files and .log files

	-x : Compile and execute all of the tests
	-v : Verbose logging
	-t : This is the timeout value in seconds (deadline) in executing all test cases.  Default: 60
	-j : This is the JVM to use in executing all test cases.  Default: openjdk
jacotest version: 1.02

Built with: go1.20.2
BuildData vcs.revision: a28c14db3637665cbb8e8a2492d1df78cd67f7d9
BuildData vcs.time: 2023-05-02 06:46:35 CDT
BuildData vcs.modified: false
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
* For a given test case, there is a main.java file whose .class file starts execution for the test case following compilation.
* Additional source files (helper.java, etc.) can be present for test case modularity.
* No package statements should appear in any of the source code.

# Test Case Run

Test cases are run in lexical directory name order as the appear under the ```tests``` directory.  For each test case (directory), execution is a two-step process:
1) Compilation of all *.java files with ```javac```.
2) If compilation is successful, then execution proceeds under the control of one of the 2 JVMs: ```java``` or ```jacobin```.

After all test cases are run, a report file is available under the root in the following name format: ```RUN-REPORT-<jvm>.md``` where ```<jvm>``` is either java or jacobin. 

The ```logs``` directory holds the combined stdout and stderr for test cases which experienced either compilation errors or execution errors.  Files here have the following name format: ```<result>-TestCaseDirectoryName-<exec>.log``` where:
* ```<result>``` : FAILED or TIMEOUT
* ```<exec>``` : javac or one of the JVMs (java or jacobin)

# Test Case Results and Reports

The RUN_REPORT_```<date and time stamp>```_```<jvm>```.md files are an encapsulation of the test case results associated with a particular batch run of the named jvm.  The report files are implemented as a Markdown table and reside in the reports subdirectory.

Each report has 3 columns:
* ```Test case name```
* ```Result``` - Success, FAILED, or TIMEOUT
* ```Console Output``` - Line by line details of stdout & stderror combined if a result is FAILED or TIMEOUT

It is ununsual that a test case fails compilation.  But, if this happens, execution is not attempted.  There will be a javac .log file is left in the ```logs``` subdirectory.  The report shows a COMP-ERROR in the Results column.  Flow moves on to the next test case.

One of the tests (negtest-comp-error) has 2 deliberate compilation errors. This is not a test of the JVM but a test of whether or not jacotest can detect them and move on to the next test case.

Assuming compilation is successful, then execution begins.  If the execution experiences a FAILED or TIMEOUT result, then 
* A .log file is left in the ```logs``` subdirectory for both FAILED and TIMEOUT results.  
* A stack trace might appear in the ```Console Output``` column if the JVM had trouble in a FAILED case.
* A TIMEOUT result indicates that the test case was overdue and was killed.

Whether the current test is successful or not, flow moves on to the next test case.  Flow concludes when all of the test cases have been attempted.

For example, from a jvm=jacobin report, the following is one result:
	
![rpt_example_result](https://user-images.githubusercontent.com/11318756/233359231-b914b0b7-32ea-43ae-a0c3-4d09e31bc044.png)

The first line "Four bit shifting cases" was emited from a System.out.println(...) statement as soon as the test case started execution. 

The FAILED result indicates that at least one part of the test was unsuccessful. Out of the 4 operations, 2 of them had errors so the test case failed as a whole. 

If you look at the same test case in the jvm=openjdk report, you will see "n/a" in the 3rd column. The test case shows a PASSED status so jacotest discarded the stdout/stderr information. 
