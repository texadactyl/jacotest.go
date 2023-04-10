# jacotest.go

TODO: a lot!

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
	-x : compile and execute all of the tests (includes a pre-clean activity)
	-j : This is the JVM to use in executing test cases.  Default: java
	-c : clean all of the .class files and log files
	-v : verbose logging

Version: 1.0
BuildData vcs.revision: 57dd539b5e15ead52a49c74fb5ee83d437c8058c
BuildData vcs.time: 2023-04-09T22:43:09Z
BuildData vcs.modified: true
```

### Sample Run

```
Version: 1.0
BuildData vcs.revision: 8d5798907fae39ad94e8f7f7007802147b1ddbdc
BuildData vcs.time: 2023-04-10T13:42:57Z
BuildData vcs.modified: true
11:03:14 Compiling JACOBIN-0161-0229-classes / Outsider.java
11:03:15 Compiling JACOBIN-0161-0229-classes / main.java
11:03:15 Executing JACOBIN-0161-0229-classes using jvm=jacobin
11:03:15 *** ERROR, cmd.Run(jacobin main.class) returned: exit status 1
11:03:15 Compiling JACOBIN-0206-nbody / main.java
11:03:16 Executing JACOBIN-0206-nbody using jvm=jacobin
11:03:16 *** ERROR, cmd.Run(jacobin main.class) returned: exit status 1
11:03:16 Compiling JACOBIN-0212-bit-shifting / main.java
11:03:16 Executing JACOBIN-0212-bit-shifting using jvm=jacobin
11:03:16 *** ERROR, cmd.Run(jacobin main.class) returned: exit status 1
11:03:16 Compiling JACOBIN-0219-length / main.java
11:03:17 Executing JACOBIN-0219-length using jvm=jacobin
11:03:17 Compiling JACOBIN-0227-string-array / main.java
11:03:17 Executing JACOBIN-0227-string-array using jvm=jacobin
11:03:17 Compiling arrays_1 / main.java
11:03:18 Executing arrays_1 using jvm=jacobin
11:03:18 *** ERROR, cmd.Run(jacobin main.class) returned: exit status 2
11:03:18 Compiling pbcrypto / main.java
11:03:18 Executing pbcrypto using jvm=jacobin
11:03:18 *** ERROR, cmd.Run(jacobin main.class) returned: exit status 1
11:03:18 Compiling scimark2 / Constants.java
11:03:19 Compiling scimark2 / FFT.java
11:03:19 Compiling scimark2 / Jacobi.java
11:03:20 Compiling scimark2 / LU.java
11:03:20 Compiling scimark2 / MonteCarlo.java
11:03:21 Compiling scimark2 / Random.java
11:03:21 Compiling scimark2 / SOR.java
11:03:21 Compiling scimark2 / SparseCompRow.java
11:03:22 Compiling scimark2 / Stopwatch.java
11:03:22 Compiling scimark2 / kernel.java
11:03:23 Compiling scimark2 / main.java
11:03:23 Executing scimark2 using jvm=jacobin
11:03:23 *** ERROR, cmd.Run(jacobin main.class) returned: exit status 2
11:03:23 Compiling sha3 / FIPS202.java
11:03:24 Compiling sha3 / KeccakShortState.java
11:03:25 Compiling sha3 / KeccakSponge.java
11:03:26 Compiling sha3 / KeccakState.java
11:03:26 Compiling sha3 / KeccakState1600.java
11:03:27 Compiling sha3 / KeccakState200.java
11:03:27 Compiling sha3 / KeccakState400.java
11:03:28 Compiling sha3 / KeccakState800.java
11:03:28 Compiling sha3 / KeccakUtilities.java
11:03:29 Compiling sha3 / main.java
11:03:29 Executing sha3 using jvm=jacobin
11:03:29 *** ERROR, cmd.Run(jacobin main.class) returned: exit status 2
11:03:29 Compiling specrel / Formulae.java
11:03:30 Compiling specrel / main.java
11:03:30 Executing specrel using jvm=jacobin
11:03:30 *** ERROR, cmd.Run(jacobin main.class) returned: exit status 1
11:03:30 Compiling stats / Library.java
11:03:30 Compiling stats / main.java
11:03:31 Executing stats using jvm=jacobin
11:04:31 *** ERROR, cmd.Run(jacobin main.class) returned: signal: killed
11:04:31 Success in 2 tests
11:04:31      JACOBIN-0219-length
11:04:31      JACOBIN-0227-string-array
11:04:31 No compilation errors
11:04:31 runner errors in 9 tests
11:04:31      JACOBIN-0161-0229-classes
11:04:31      JACOBIN-0206-nbody
11:04:31      JACOBIN-0212-bit-shifting
11:04:31      arrays_1
11:04:31      pbcrypto
11:04:31      scimark2
11:04:31      sha3
11:04:31      specrel
11:04:31      stats
11:04:31 Elapsed time = 1m17s

```

### Sample Logs Directory After Run
```
FAILED-arrays_1-main-jacobin.log
FAILED-JACOBIN-0161-0229-classes-main-jacobin.log
FAILED-JACOBIN-0206-nbody-main-jacobin.log
FAILED-JACOBIN-0212-bit-shifting-main-jacobin.log
FAILED-pbcrypto-main-jacobin.log
FAILED-scimark2-main-jacobin.log
FAILED-sha3-main-jacobin.log
FAILED-specrel-main-jacobin.log
PASSED-JACOBIN-0219-length-main-jacobin.log
PASSED-JACOBIN-0227-string-array-main-jacobin.log
TIMEOUT-stats-main-jacobin.log
```

