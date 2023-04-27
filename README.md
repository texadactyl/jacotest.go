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

Install ```jacobin``` such that its executable resides in a directory whose absolute path is an element of the ```PATH``` environment variable.  
It is recommended that this directory be the ```bin``` subdirectory under ```go```.

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
My version: 1.0
Built with: go1.20.2
BuildData vcs.revision: f048d3e293bdef65ef0420878f17fc5ba901a86c
BuildData vcs.time: 2023-04-26 09:52:51 CDT
BuildData vcs.modified: false
```

### Jacotest Operations

Jacotest can run the test case collection against 2 different JVMs:
* jacobin
* OpenJDK's jvm with an executable called "java"

For example, to test jacobin against the test case collection: 
```jacotest -x```

Note that jacobin is the default JVM.

To run tests against the OpenJDK JVM, 
```jacotest -x -j openjdk```

The reports and logs can be discarded as follows:
```jacotest -c```

If the default timeout value of 60 seconds for each individual case is insufficient, one can use the ```-t``` parameter to specify a different value.  An example:
```jacotest -x -t 120 -j jacobin```

The ```-v``` (verbose logging) parameter is intended for software debugging.

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

### Test Case Results and Reports

The RUN-REPORT-```<jvm>```.md files are an encapsulation of the test case results associated with a particular batch run of the named jvm.  The report files are implemented as a Markdown table.

Each report has 3 columns:
* ```Test case name```
* ```Result``` - Success, FAILED, or TIMEOUT
* ```Console Output``` - Line by line details of stdout & stderror combined if a result is FAILED or TIMEOUT

It is ununsual that a test case fails compilation.  But, if this happens, execution is not attempted.  There will be a javac .log file is left in the ```logs``` subdirectory.  The report shows a COMP-ERROR in the Results column.  Flow moves on to the next test case.

One of the tests (negtest-comp-error) has 2 deliberate compilation errors. This is not a test of the jvm but a test of whether or not jacotest can detect them and move on to the next test case.

Assuming compilation is successful, then execution begins.  If the execution experiences a FAILED or TIMEOUT result, then 
* A .log file is left in the ```logs``` subdirectory for both FAILED and TIMEOUT results.  
* A stack trace might appear in the ```Console Output``` column if the jvm had trouble in a FAILED case.
* A TIMEOUT result indicates that the test case was overdue and was killed.

Whether the current test is successful or not, flow moves on to the next test case.  Flow concludes when all of the test cases have been attempted.

For example, from a jvm=jacobin report, the following is one result:
	
![rpt_example_result](https://user-images.githubusercontent.com/11318756/233359231-b914b0b7-32ea-43ae-a0c3-4d09e31bc044.png)

The first line "Four bit shifting cases" was emited from a System.out.println(...) statement as soon as the test case started execution. 

The FAILED result indicates that at least one part of the test was unsuccessful. Out of the 4 operations, 2 of them had errors so the test case failed as a whole. 

If you look at the same test case in the jvm=java report, you will see "n/a" in the 3rd column. The test case shows a PASSED status so jacotest discarded the stdout/stderr information.
	
### Sample Jacotest Help

```
Usage:  jacotest  [-h]  [-c]  [-x]  [-v]  [ -j { java | jacobin } ]

where
	-h : This display
	-x : Compile and execute all of the tests
	-v : Verbose logging
	-j : This is the JVM to use in executing all test cases.  Default: java
	-t : This is the timeout value in seconds (deadline) in executing all test cases.  Default: 60
	-c : Clean all of the .class files and .log files

My version: 1.0
Built with: go1.20.2
BuildData vcs.revision: fd571d8e8618fec07ec5a4e73adda9b1787ce6ac
BuildData vcs.time: 2023-04-12T15:51:45Z
BuildData vcs.modified: true

```

# Sample Console Output

```
11:40:27 Jacotest version 1.0
11:40:27 Test case deadline: 60 seconds
11:40:27 Compiling JACOBIN-0161-0229-classes / Outsider.java
11:40:28 Compiling JACOBIN-0161-0229-classes / main.java
11:40:28 Executing JACOBIN-0161-0229-classes using jvm=java
11:40:28 Compiling JACOBIN-0206-nbody / main.java
11:40:29 Executing JACOBIN-0206-nbody using jvm=java
11:40:33 Compiling JACOBIN-0212-bit-shifting / main.java
11:40:33 Executing JACOBIN-0212-bit-shifting using jvm=java
11:40:33 Compiling JACOBIN-0219-length / main.java
11:40:34 Executing JACOBIN-0219-length using jvm=java
11:40:34 Compiling JACOBIN-0227-string-array / main.java
11:40:34 Executing JACOBIN-0227-string-array using jvm=java
11:40:35 Compiling JACOBIN-0231-stats / Library.java
11:40:35 Compiling JACOBIN-0231-stats / main.java
11:40:36 Executing JACOBIN-0231-stats using jvm=java
11:40:36 Compiling arrays_1 / main.java
11:40:36 Executing arrays_1 using jvm=java
11:40:37 Compiling instantiate_class / Library.java
11:40:37 Compiling instantiate_class / main.java
11:40:38 Executing instantiate_class using jvm=java
11:40:38 Compiling negtest-comp-error / erroneous.java
11:40:38 *** ERROR, runner: cmd.Run(javac erroneous.java) returned: erroneous.java:3: error: '{' expected
public class erroneous JUNK {
                      ^
1 error

11:40:38 Compiling negtest-comp-error / even_worse.java
11:40:38 *** ERROR, runner: cmd.Run(javac even_worse.java) returned: even_worse.java:3: error: '{' expected
public class even_worse JUNK {
                       ^
1 error

11:40:38 Compiling negtest-runner-failure / main.java
11:40:39 Executing negtest-runner-failure using jvm=java
11:40:39 *** ERROR, runner: cmd.Run(java main) returned: I will System.exit(86)!

11:40:39 Compiling negtest-runner-timeout / main.java
11:40:39 Executing negtest-runner-timeout using jvm=java
11:41:39 *** TIMEOUT, runner: cmd.Run(java main) returned: I will timeout!

11:41:39 Compiling pbcrypto / main.java
11:41:40 Executing pbcrypto using jvm=java
11:41:40 Compiling scimark2 / Constants.java
11:41:41 Compiling scimark2 / FFT.java
11:41:41 Compiling scimark2 / Jacobi.java
11:41:42 Compiling scimark2 / LU.java
11:41:42 Compiling scimark2 / MonteCarlo.java
11:41:43 Compiling scimark2 / Random.java
11:41:43 Compiling scimark2 / SOR.java
11:41:43 Compiling scimark2 / SparseCompRow.java
11:41:44 Compiling scimark2 / Stopwatch.java
11:41:44 Compiling scimark2 / kernel.java
11:41:45 Compiling scimark2 / main.java
11:41:45 Executing scimark2 using jvm=java
11:42:11 Compiling sha3 / FIPS202.java
11:42:12 Compiling sha3 / KeccakShortState.java
11:42:13 Compiling sha3 / KeccakSponge.java
11:42:14 Compiling sha3 / KeccakState.java
11:42:14 Compiling sha3 / KeccakState1600.java
11:42:15 Compiling sha3 / KeccakState200.java
11:42:15 Compiling sha3 / KeccakState400.java
11:42:16 Compiling sha3 / KeccakState800.java
11:42:16 Compiling sha3 / KeccakUtilities.java
11:42:17 Compiling sha3 / main.java
11:42:17 Executing sha3 using jvm=java
11:42:20 Compiling specrel / Formulae.java
11:42:20 Compiling specrel / main.java
11:42:21 Executing specrel using jvm=java
11:42:21 Success in 12 tests
11:42:21      JACOBIN-0161-0229-classes
11:42:21      JACOBIN-0206-nbody
11:42:21      JACOBIN-0212-bit-shifting
11:42:21      JACOBIN-0219-length
11:42:21      JACOBIN-0227-string-array
11:42:21      JACOBIN-0231-stats
11:42:21      arrays_1
11:42:21      instantiate_class
11:42:21      pbcrypto
11:42:21      scimark2
11:42:21      sha3
11:42:21      specrel
11:42:21 compilation errors in 1 test
11:42:21      negtest-comp-error
11:42:21 runner failure errors in 1 test
11:42:21      negtest-runner-failure
11:42:21 runner timeout errors in 1 test
11:42:21      negtest-runner-timeout
11:42:21 Elapsed time = 1m54s


```
# Sample logs directory after the above run
```
FAILED-negtest-comp-error-erroneous-javac.log
FAILED-negtest-comp-error-even_worse-javac.log
FAILED-negtest-runner-failure-main-java.log
PASSED-arrays_1-main-java.log
PASSED-instantiate_class-main-java.log
PASSED-JACOBIN-0161-0229-classes-main-java.log
PASSED-JACOBIN-0206-nbody-main-java.log
PASSED-JACOBIN-0212-bit-shifting-main-java.log
PASSED-JACOBIN-0219-length-main-java.log
PASSED-JACOBIN-0227-string-array-main-java.log
PASSED-JACOBIN-0231-stats-main-java.log
PASSED-pbcrypto-main-java.log
PASSED-scimark2-main-java.log
PASSED-sha3-main-java.log
PASSED-specrel-main-java.log
TIMEOUT-negtest-runner-timeout-main-java.log
```
 
