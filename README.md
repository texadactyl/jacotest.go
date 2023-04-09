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
17:20:49 Compiling JACOBIN-0161-0229-classes / Outsider.java
17:20:49 Compiling JACOBIN-0161-0229-classes / main.java
17:20:50 Executing JACOBIN-0161-0229-classes using jvm=java
17:20:50 Compiling JACOBIN-0206-nbody / main.java
17:20:50 Executing JACOBIN-0206-nbody using jvm=java
17:20:54 Compiling JACOBIN-0212-bit-shifting / main.java
17:20:55 Executing JACOBIN-0212-bit-shifting using jvm=java
17:20:55 Compiling JACOBIN-0219-length / main.java
17:20:55 Executing JACOBIN-0219-length using jvm=java
17:20:55 Compiling JACOBIN-0227-string-array / main.java
17:20:56 Executing JACOBIN-0227-string-array using jvm=java
17:20:56 Compiling arrays_1 / main.java
17:20:56 Executing arrays_1 using jvm=java
17:20:57 Compiling pbcrypto / main.java
17:20:58 Executing pbcrypto using jvm=java
17:20:58 Compiling scimark2 / Constants.java
17:20:58 Compiling scimark2 / FFT.java
17:20:59 Compiling scimark2 / Jacobi.java
17:20:59 Compiling scimark2 / LU.java
17:21:00 Compiling scimark2 / MonteCarlo.java
17:21:00 Compiling scimark2 / Random.java
17:21:01 Compiling scimark2 / SOR.java
17:21:01 Compiling scimark2 / SparseCompRow.java
17:21:01 Compiling scimark2 / Stopwatch.java
17:21:02 Compiling scimark2 / kernel.java
17:21:02 Compiling scimark2 / main.java
17:21:03 Executing scimark2 using jvm=java
17:21:30 Compiling sha3 / FIPS202.java
17:21:31 Compiling sha3 / KeccakShortState.java
17:21:32 Compiling sha3 / KeccakSponge.java
17:21:32 Compiling sha3 / KeccakState.java
17:21:33 Compiling sha3 / KeccakState1600.java
17:21:33 Compiling sha3 / KeccakState200.java
17:21:34 Compiling sha3 / KeccakState400.java
17:21:34 Compiling sha3 / KeccakState800.java
17:21:35 Compiling sha3 / KeccakUtilities.java
17:21:35 Compiling sha3 / main.java
17:21:36 Executing sha3 using jvm=java
17:21:38 Compiling specrel / Formulae.java
17:21:39 Compiling specrel / main.java
17:21:39 Executing specrel using jvm=java
17:21:39 Compiling stats / Library.java
17:21:40 Compiling stats / main.java
17:21:40 Executing stats using jvm=java
17:21:43 Success in 11 tests
17:21:43      JACOBIN-0161-0229-classes
17:21:43      JACOBIN-0206-nbody
17:21:43      JACOBIN-0212-bit-shifting
17:21:43      JACOBIN-0219-length
17:21:43      JACOBIN-0227-string-array
17:21:43      arrays_1
17:21:43      pbcrypto
17:21:43      scimark2
17:21:43      sha3
17:21:43      specrel
17:21:43      stats
17:21:43 No compilation errors
17:21:43 No runner errors
17:21:43 Elapsed time = 54s
```
