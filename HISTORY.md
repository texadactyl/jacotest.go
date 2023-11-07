This file is a version history of jacotest amendments.  Entries appear in version descending order (newest first, oldest last).
<br>
<br>
| `Date` | `Version` | `Contents` |
| :------------: | :---: | :--- |
|<img width=90/>|<img width=60/>|<img width=600/>|
| 2023-11-06 | 2.6.19 | New test case: JACOBIN-0393-pot-pourri |
| 2023-11-06 | 2.6.18 | Expanded test case JACOBIN-0393-two-strings |
| 2023-11-05 | 2.6.17 | New test case: JACOBIN-0393-two-strings |
| 2023-10-31 | 2.6.16 | New test case: JACOBIN-0386-0387-strings-again. |
| 2023-10-28 | 2.6.15 | Remove "UTF-8" from string.getBytes() calls. |
| 2023-10-24 | 2.6.14 | Get past INVOKEDYNAMIC in two-fish". |
| 2023-10-22 | 2.6.13 | New test case: cmath-in-java-source. |
| 2023-10-17 | 2.6.12 | JACOBIN-379. |
| 2023-10-09 | 2.6.11 | Fix double grep hit caused by "AssertionError". |
| 2023-09-30 | 2.6.10 | New test cases: nio-charset, JACOBIN-0369-simplified-0290. |
| 2023-09-28 | 2.6.9 | New test case: JACOBIN-0369-getbytes. |
| 2023-09-26 | 2.6.8 | New failure category: INVOKEVIRTUAL: Native method requested. |
| 2023-09-24 | 2.6.7 | New failure category: stack overflow. |
| | | Re-worded failure category pertaining to interface conversion. |
| 2023-09-21 | 2.6.6 | New failure category: stack underflow. |
| | | New test case: rsa-unrandom. |
| 2023-09-20 | 2.6.5 | ':' added by Unka Andoo. |
| | | Upgraded to golang 1.21.1 |
| 2023-09-19 | 2.6.4 | New summary report category: "PUTSTATIC type unrecognized". |
| | | Catch discrepancies between total error executions and the total from log-greps. |
| 2023-09-17 | 2.6.3 | New test case: rsa-mini. |
| 2023-09-17 | 2.6.2 | Complement jacobin PR #71 - JACOBIN-354. |
| | | New test case: catch-assertion-error |
| | | New test case: nth-root |
| 2023-09-12 | 2.6.1 | Implement issue #18: Retry on SQL INSERT duplicate key errors. |
| 2023-09-09 | 2.6.0 | Implement issue #16: display on console which test case results have changed. |
| 2023-09-07 | 2.5.8 | Implement issue #15: show the JVM version in the summary report. |
| 2023-09-07 | 2.5.7 | Fix issue #14: test case JACOBIN-0263-gc. |
| 2023-08-19 | 2.5.6 | New test case: JACOBIN-0337-static-inits. |
| 2023-08-18 | 2.5.5 | New test case: parse-int. |
| 2023-08-16 | 2.5.4 | New test case: charset-encoding. |
| 2023-08-15 | 2.5.3 | New test cases: desi (DES cryptography) and two-pass (a simple Assembler). |
| 2023-08-11 | 2.5.2 | New test case: playfair (cryptography). |
| | | Adopted go version 1.21. |
| 2023-08-09 | 2.5.1 | Implement sqlite3 database. |
| 2023-08-08 | 2.4.2 | Moved the grape functions from helpers.go into a new source file (grape.go). |
| | | Added the JVM -ea option to jacobin and openjdk-java invocations. |
| | | Added the -Xlint:all option to javac invocations. |
| | | Identified the O/S type and machine architecture in the summary report. |
| | | Note whether or not compilations will occur in the summary report. |
| 2023-08-02 | 2.4.1 | All test cases with failure exits now use assert or throw new AssertionError. |
| 2023-08-02 | 2.3.16 | Fix WalkDir logic for javap. |
| 2023-08-02 | 2.3.15 | New test case: enigma-machine (Alan Turing). |
| 2023-08-02 | 2.3.14 | New test case: JACOBIN-0329-nonfinals. |
| 2023-08-01 | 2.3.13 | Change capture category "is nil" to "data.Class" in summary report to avoid duplicate entries. |
| 2023-07-31 | 2.3.12 | Capture "is nil" category in summary report. |
| 2023-07-24 | 2.3.11 | JACOBIN-0326: Ensure that `javap` is run on every compiled .class file. |
| | | Deleted the bash and windows subdirectories. The tools are no longer used. |
| 2023-07-24 | 2.3.10 | New test cases: JACOBIN-0325-super-1 and JACOBIN-0325-super-2. |
| 2023-07-22 | 2.3.9 | New test case: JACOBIN-0322-default-locale. |
| 2023-07-21 | 2.3.8 |  Support error category 'PUTFIELD: invalid attempt'. |
| | | New test case: JACOBIN-0322-throw-null. |
| 2023-07-16 | 2.3.7 | Amended test cases to use Math class: JACOBIN-0206-nbody, blockchain, specrel. |
| | | Amended math-in-java-source to instantiate other classes as static final. |
| | | New test case: JACOBIN-0319-println-object. |
| 2023-07-13 | 2.3.6 | New test case: JACOBIN-0314-loop-survival. |
| 2023-07-12 | 2.3.5 | Amended test case: JACOBIN-0314-java-lang-math. |
| | | New test case: JACOBIN-0314-java-lang-strictmath. |
| 2023-07-11 | 2.3.4 | New test case: JACOBIN-0312-FCMPG. |
| | | New test case: java-lang-math, using Math/StrictMath function calls, |
| | | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;thus, leveraging jacobin classloader/javaLangMath.go.
| | | Deleted test case: trig-simple because it is a subset of math-in-java-source. |
| 2023-07-10 | 2.3.3 | Amended test case: JACOBIN-0311-for-loop-G-pop. |
| | | Renamed test case: java-math --> math-in-java-source. |
| 2023-07-08 | 2.3.2 | New test case: JACOBIN-0310-vector-survivor. |
| | | New test case: vector-survivor-2. |
| | | New test case: JACOBIN-0311-for-loop-G-pop. |
| 2023-07-07 | 2.3.1 | New option: -N. When specified, it is assumed that compiles are unnecessary. |
| | | Removed MathLite from all test cases where it was used. |
| | | New test case: java-math. |
| 2023-07-05 | 2.2.3 | Guard against empty test directories. Bypass them. |
| 2023-07-04 | 2.2.2 | Replaced Java Math from test cases with MathLite. |
| 2023-07-02 | 2.2.1 | Fixed output of JACOBIN-0281-get-property. |
| | | New test case: trig-simple. |
| | | Revamp of scimark2 to avoid Math library and INVOKEDYNAMIC. |
| | | Revamp of solitairgraphy to avoid Math library and INVOKEDYNAMIC. |
| 2023-07-01 | 2.2.0 | "Failure Summary" --> "Summary". |
| | | All result lists are now in the Summary_YYYY-MM-DD_hh.mm.ss file in reports directory. |
| | | Console output no longer includes result lists. |
| | | No change to the RUN_REPORT file. |
| 2023-06-30 | 2.1.5 | Revised test case specrel to compute abs() and sqrt() in Java instead of using library functions. |
| | | Minor revisions to test cases kalman-filtering, linked-list, numbers-chars-strings, vectors. |
| | | For consistency, revised some test cases to use the string "*** ERROR" when an error is detected. |
| | | Test case JACOBIN-0231-stats amended to get closer to passing; avoiding as error reported elsewhere. |
| | | Revised test case JACOBIN-0206-nbody to compute abs() and sqrt() in Java instead of using library functions. |
| | | Split JACOBIN-0206-nbody into 2 files. |
| 2023-06-27 | 2.1.4 | Added new categories to failure summary. |
| 2023-06-26 | 2.1.3 | Ensure that every test case made a System.out.println(...) announcement. |
| | | Added test case: JACOBIN-0301-putstatic. |
| | | Added test case: JACOBIN-0303-securerandom. |
| 2023-06-26 | 2.1.2 | Removed negative tests (useful 6 months ago) and shortened JACOBIN-0263-gc. |
| 2023-06-25 | 2.1.1 | Generate `javap -v` logs for all test case main.class files. |
| 2023-06-25 | 2.0.22 | Fixed logging to be clearer in a few tests. |
| 2023-06-23 | 2.0.21 | Added test case: JACOBIN-0296-new-fileinputstream. |
| | | Renamed test case JACOBIN-0235-system-exit to JACOBIN-0294-system-exit. |
| | | Added test case: JACOBIN-0293-drem. |
| 2023-06-22 | 2.0.20 | Added test case: stringer-valueof. |
| | | Renamed test case JACOBIN-0234-string-length to JACOBIN-0290-string-length. |
| 2023-06-21 | 2.0.19 | Added test case: JACOBIN-0288-aastore-field-type. |
| | | Added test case: try-throw-catch. |
| | | Added test case: finally. |
| | | Added test case: JACOBIN-0289-checkcast. |
| | | Amended test case JACOBIN-0281-get-property. |
| | | Prefixed test case names with JACOBIN-n. |
| 2023-06-20 | 2.0.18 | Added test case: stringer-2. |
| | | Renamed test case stringer --> stringer-1. |
| 2023-06-19 | 2.0.18 | printf, begone! |
| 2023-06-19 | 2.0.17 | Added test case: imageio-output. |
| 2023-06-16 | 2.0.16 | Added test case: perf-base-instantiate. |
|  |  | Added test case: simple-switch. |
|  |  | Added test case: string-writer. |
|  |  | Added Java application error detection to failure report. |
|  |  | Bug fixes: utility genGobFile. |
| 2023-06-15 | 2.0.15 | Added test case: get-property. |
|  |  | Added test case: merkletrees. |
| 2023-06-13 | 2.0.14 | Added test case: jarring. |
| 2023-06-11 | 2.0.13 | Added additional failure report categories. |
| 2023-06-06 | 2.0.12 | Bug fixes: utility genGobFile. |
| 2023-06-03 | 2.0.11 | Added utility: genGobFile. |
| 2023-06-03 | 2.0.10 | Rename mapJmods to genJmodsCsv. |
| 2023-06-02 | 2.0.9  | Added utility: mapJmods. |
| 2023-06-02 | 2.0.8  | Added utility: szJmodBase. |
| 2023-05-30 | 2.0.7  | Added test case: taylor-series. |
| 2023-05-30 | 2.0.6  | Vastly improved test case: stringer. |
| 2023-05-29 | 2.0.5  | Added test case: JACOBIN-0251-array-type-perf. |
| 2023-05-29 | 2.0.4  | Added test case: JACOBIN-0263-gc. |
| 2023-05-28 | 2.0.3  | Added test case: emission-line-spectra. |
| 2023-05-26 | 2.0.2  | Added test case: kalman-filtering. |
| 2023-05-23 | 2.0.1  | Cleanup of bash scripts and store in a bash subdirectory. |
| 2023-05-21 | 2.0.0  | Implemented the 2nd report: Failure Summary. |
| 2023-05-19 | 1.20  | Spelling corrections for the test case names - use hyphens consistently. |
|            |       | Make log file names consistent: RESULT.test-case-name.COMMAND.log |
|            |       | RESULT = PASSED, FAILED, or TIMEOUT |
|            |       | test-case-name = hyphenated name of directory holding the specified test case |
|            |       | COMMAND = jacobin, openjdk, or javac |
| 2023-05-18 | 1.12  | Added test case: solitaire cryptography. |
| 2023-05-17 | 1.11  | Added test case: Two-fish cryptography. |
| 2023-05-15 | 1.10  | Added test case: read & verify FITS file header fields. |
| 2023-05-14 | 1.09  | Issue #12: A reports subdirectory was implemented. |
| 2023-05-13 | 1.08  | Added date and time to RUN_REPORT names. |
| 2023-05-10 | 1.07  | Added test case: recursion. |
| 2023-05-06 | 1.06  | Added test case: hacked version of Andrew's dedupe. |
| 2023-05-04 | 1.05  | Added test cases: tls-one-way (low-level TLS) and zippy. |
| 2023-05-03 | 1.04  | Added test case: sockets (multithreading client/server socket applications). |
| 2023-05-02 | 1.03  | Added test case: blockchain. |
| 2023-05-01 | 1.02  | Added test cases: casting, stringer. |
| 2023-04-30 | 1.01  | Externalized version to file VERSION.txt. |
|  |  | Using ```go install``` instead of ```go build```. |
|  |  | Cleaned up go source files by running ```gofmt -w```. |
|  |  | Split test case JACOBIN-0217-multidim into 2 cases: 2d and 3d. |
|  |  | Renamed test case: negtest-runner-throw-exception to catch-exception. |
|  |  | Added test case: elliptic (public key cryptography). |
|  |  | Added test case: java17-enhancements. |
|  |  | Added test cases: vectors, lambdas-maps, hashed-set, and threading. |
| 2023-04-01 | 1.0  | First version. |

