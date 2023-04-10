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

### Test Case Results

If a test case fails compilation, execution is not attempted.  The stderr file is left in the ```logs``` subdirectory.  Flow moves on to the next test case.

Assuming success, then execution begins.  If execution fails, then the stderr file is left in the ```logs``` subdirectory.  Whether successful or not, flow moves on to the next test case.

### Jacotest Help

```
Usage:  jacotest  [-c]  [-x]  [-v]  [ -j { java | jacobin } ]

where
	-x : compile and execute all of the tests (includes a pre-clean activity)
	-j : This is the JVM to use in executing test cases.  Default: java
	-c : clean all of the .class files and log files
	-v : verbose logging
```

### Sample Run

```
Version: 1.0
BuildData vcs.revision: 57dd539b5e15ead52a49c74fb5ee83d437c8058c
BuildData vcs.time: 2023-04-09T22:43:09Z
BuildData vcs.modified: true
07:55:01 Compiling JACOBIN-0161-0229-classes / Outsider.java
07:55:01 Compiling JACOBIN-0161-0229-classes / main.java
07:55:02 Executing JACOBIN-0161-0229-classes using jvm=java
07:55:02 Compiling JACOBIN-0206-nbody / main.java
07:55:02 Executing JACOBIN-0206-nbody using jvm=java
07:55:07 Compiling JACOBIN-0212-bit-shifting / main.java
07:55:07 Executing JACOBIN-0212-bit-shifting using jvm=java
07:55:07 Compiling JACOBIN-0219-length / main.java
07:55:08 Executing JACOBIN-0219-length using jvm=java
07:55:08 Compiling JACOBIN-0227-string-array / main.java
07:55:08 Executing JACOBIN-0227-string-array using jvm=java
07:55:08 Compiling arrays_1 / main.java
07:55:09 Executing arrays_1 using jvm=java
07:55:09 Compiling pbcrypto / main.java
07:55:10 Executing pbcrypto using jvm=java
07:55:10 Compiling scimark2 / Constants.java
07:55:10 Compiling scimark2 / FFT.java
07:55:11 Compiling scimark2 / Jacobi.java
07:55:11 Compiling scimark2 / LU.java
07:55:12 Compiling scimark2 / MonteCarlo.java
07:55:12 Compiling scimark2 / Random.java
07:55:13 Compiling scimark2 / SOR.java
07:55:13 Compiling scimark2 / SparseCompRow.java
07:55:14 Compiling scimark2 / Stopwatch.java
07:55:14 Compiling scimark2 / kernel.java
07:55:15 Compiling scimark2 / main.java
07:55:15 Executing scimark2 using jvm=java
07:55:43 Compiling sha3 / FIPS202.java
07:55:44 Compiling sha3 / KeccakShortState.java
07:55:45 Compiling sha3 / KeccakSponge.java
07:55:45 Compiling sha3 / KeccakState.java
07:55:46 Compiling sha3 / KeccakState1600.java
07:55:46 Compiling sha3 / KeccakState200.java
07:55:47 Compiling sha3 / KeccakState400.java
07:55:47 Compiling sha3 / KeccakState800.java
07:55:48 Compiling sha3 / KeccakUtilities.java
07:55:48 Compiling sha3 / main.java
07:55:49 Executing sha3 using jvm=java
07:55:51 Compiling specrel / Formulae.java
07:55:52 Compiling specrel / main.java
07:55:52 Executing specrel using jvm=java
07:55:52 Compiling stats / Library.java
07:55:53 Compiling stats / main.java
07:55:53 Executing stats using jvm=java
07:55:56 Success in 11 tests
07:55:56      JACOBIN-0161-0229-classes
07:55:56      JACOBIN-0206-nbody
07:55:56      JACOBIN-0212-bit-shifting
07:55:56      JACOBIN-0219-length
07:55:56      JACOBIN-0227-string-array
07:55:56      arrays_1
07:55:56      pbcrypto
07:55:56      scimark2
07:55:56      sha3
07:55:56      specrel
07:55:56      stats
07:55:56 No compilation errors
07:55:56 No runner errors
07:55:56 Elapsed time = 55s

```
