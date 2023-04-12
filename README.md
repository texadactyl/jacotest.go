# jacotest.go

TODO: Add more test cases!

Jacotest is intended to be a top-down user-level testing companion to https://github.com/platypusguy/jacobin 
While Jacobin has internal component-unit testing, it would be useful to have a collection of Java-source tests that can be executed automatically 
at the operator command line or through Github Actions.

All of the test harness code is written in Go as the name implies.

### Test Case Overview

Each test case occupies a directory immediately under the project subdirectory (```tests```).  By convention,
* For a given test case, there is a main.java file which starts execution for the test case.
* Additional source files (helper.java, etc.) can be present for test case modularity.
* No package statements should appear in any of the source code.

### Test Case Run

Test cases are run in lexical directory name order.  For each test case, execution is a two-step process:
1) Compilation with ```javac```.
2) If compilation is successful, then execution proceeds under the control of a JVM (```java``` or ```jacobin```).

The ```logs``` subdirectory holds the results for each test case.  This consists of combined stdout and stderr in a single file with a .log extension.

### Test Case Results

If a test case fails compilation, execution is not attempted.  There will be a javac .log file is left in the ```logs``` subdirectory.  Flow moves on to the next test case.

Assuming compilation success, then execution begins.  If the execution fails, then a jvm .log file is left in the ```logs``` subdirectory.  Whether the current test is successful or not, flow moves on to the next test case.  Flow stops when all test cases have been attempted.

### Jacotest Help

```
Usage:  jacotest  [-c]  [-x]  [-v]  [ -j { java | jacobin } ]

where
	-x : Compile and execute all of the tests
	-v : Verbose logging
	-j : This is the JVM to use in executing all test cases.  Default: java
	-t : This is the timeout value in seconds (deadline) in executing all test cases.  Default: 60
	-c : Clean all of the .class files and .log files

Version: 1.0
BuildData vcs.revision: 5a58527f676fa2eaf2eee68ca5f1d809aa074e2a
BuildData vcs.time: 2023-04-10T17:21:51Z
BuildData vcs.modified: true
```

### Reports

After all test cases are run, a report file is available in the following name format: ```RUN-REPORT-<jvm>.md``` where ```<jvm>``` is either java or jacobin.

### Sample Console Output

```
Version: 1.0
BuildData vcs.revision: 25eb7fe70af1e617d0174615d9aa0ac6f08ca8b6
BuildData vcs.time: 2023-04-11T14:30:38Z
BuildData vcs.modified: true
08:12:09 Deadline: 60 seconds
08:12:09 Compiling JACOBIN-0161-0229-classes / Outsider.java
08:12:10 Compiling JACOBIN-0161-0229-classes / main.java
08:12:10 Executing JACOBIN-0161-0229-classes using jvm=java
08:12:10 Compiling JACOBIN-0206-nbody / main.java
08:12:11 Executing JACOBIN-0206-nbody using jvm=java
08:12:15 Compiling JACOBIN-0212-bit-shifting / main.java
08:12:16 Executing JACOBIN-0212-bit-shifting using jvm=java
08:12:16 Compiling JACOBIN-0219-length / main.java
08:12:16 Executing JACOBIN-0219-length using jvm=java
08:12:16 Compiling JACOBIN-0227-string-array / main.java
08:12:17 Executing JACOBIN-0227-string-array using jvm=java
08:12:17 Compiling JACOBIN-0231-stats / Library.java
08:12:17 Compiling JACOBIN-0231-stats / main.java
08:12:18 Executing JACOBIN-0231-stats using jvm=java
08:12:18 Compiling arrays_1 / main.java
08:12:18 Executing arrays_1 using jvm=java
08:12:19 Compiling instantiate_class / Library.java
08:12:19 Compiling instantiate_class / main.java
08:12:20 Executing instantiate_class using jvm=java
08:12:20 Compiling negtest-comp-error / erroneous.java
08:12:20 *** ERROR, runner: cmd.Run(javac erroneous.java) returned: erroneous.java:3: error: '{' expected
public class erroneous JUNK {
                      ^
1 error

08:12:20 Compiling negtest-comp-error / even_worse.java
08:12:21 *** ERROR, runner: cmd.Run(javac even_worse.java) returned: even_worse.java:3: error: '{' expected
public class even_worse JUNK {
                       ^
1 error

08:12:21 Compiling negtest-runner-failure / main.java
08:12:21 Executing negtest-runner-failure using jvm=java
08:12:21 *** ERROR, runner: cmd.Run(java main) returned: I will System.exit(86)!

08:12:21 Compiling negtest-runner-timeout / main.java
08:12:21 Executing negtest-runner-timeout using jvm=java
08:13:22 *** TIMEOUT, runner: cmd.Run(java main) returned: I will timeout!

08:13:22 Compiling pbcrypto / main.java
08:13:22 Executing pbcrypto using jvm=java
08:13:22 Compiling scimark2 / Constants.java
08:13:23 Compiling scimark2 / FFT.java
08:13:23 Compiling scimark2 / Jacobi.java
08:13:24 Compiling scimark2 / LU.java
08:13:24 Compiling scimark2 / MonteCarlo.java
08:13:25 Compiling scimark2 / Random.java
08:13:25 Compiling scimark2 / SOR.java
08:13:26 Compiling scimark2 / SparseCompRow.java
08:13:26 Compiling scimark2 / Stopwatch.java
08:13:27 Compiling scimark2 / kernel.java
08:13:27 Compiling scimark2 / main.java
08:13:28 Executing scimark2 using jvm=java
08:13:55 Compiling sha3 / FIPS202.java
08:13:56 Compiling sha3 / KeccakShortState.java
08:13:57 Compiling sha3 / KeccakSponge.java
08:13:58 Compiling sha3 / KeccakState.java
08:13:58 Compiling sha3 / KeccakState1600.java
08:13:59 Compiling sha3 / KeccakState200.java
08:13:59 Compiling sha3 / KeccakState400.java
08:14:00 Compiling sha3 / KeccakState800.java
08:14:00 Compiling sha3 / KeccakUtilities.java
08:14:01 Compiling sha3 / main.java
08:14:01 Executing sha3 using jvm=java
08:14:04 Compiling specrel / Formulae.java
08:14:04 Compiling specrel / main.java
08:14:04 Executing specrel using jvm=java
08:14:05 Success in 12 tests
08:14:05      JACOBIN-0161-0229-classes
08:14:05      JACOBIN-0206-nbody
08:14:05      JACOBIN-0212-bit-shifting
08:14:05      JACOBIN-0219-length
08:14:05      JACOBIN-0227-string-array
08:14:05      JACOBIN-0231-stats
08:14:05      arrays_1
08:14:05      instantiate_class
08:14:05      pbcrypto
08:14:05      scimark2
08:14:05      sha3
08:14:05      specrel
08:14:05 compilation errors in 1 test
08:14:05      negtest-comp-error
08:14:05 runner failure errors in 1 test
08:14:05      negtest-runner-failure
08:14:05 runner timeout errors in 1 test
08:14:05      negtest-runner-timeout
08:14:05 Elapsed time = 1m55s

```
### Sample logs directory after the above run
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
