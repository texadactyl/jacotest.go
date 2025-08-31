This file is a version history of Jacotest amendments.  Entries appear in version descending order (newest first, oldest last).
<br>
<br>
| `Date` | `Version` | `Contents` |
| :------------: | :---: | :--- |
|<img width=90/>|<img width=60/>|<img width=600/>|
| 2025-08-31 | 5.0.0 | Implemented test helpers (tests/HELPERS). |
| | | Migrated to using the Jacobin classpath option (mandatory for jacotest). |
| | | Will migrate individual test cases. First: array-copy (done). |
| 2025-08-31 | 4.3.16 | New test case: math-context. |
| | | Improved test cases rounding-mode, sha3. |
| 2025-08-30 | 4.3.15 | New test cases: string-formatter-basic, string-formatter-biggies, string-formatter-other-objects. |
| 2025-08-28 | 4.3.14 | New test case: warp-speed. |
| 2025-08-27 | 4.3.13 | Added "Empty code segment" to ERROR_CATEGORIES.txt. |
| | | Made test case threading more realistic. |
| 2025-08-07 | 4.3.12 | New random number generation design for test case stat-distros. |
| 2025-08-06 | 4.3.11 | Fixed print formatting in test case stat-distros. |
| 2025-08-04 | 4.3.10 | New test case: JACOBIN-0760-submethod. |
| 2025-08-02 | 4.3.9 | New test case: arrays-copyof. |
| 2025-08-01 | 4.3.8 | New test case: array-copy. |
| 2025-07-29 | 4.3.7 | New test cases: rounding-mode & vector-rm-elem. |
| 2025-07-18 | 4.3.6 | Removed redundant test case: parent-child-process. |
| | | Updated test case db-sqlite. |
| | | Updated test case jarring. |
| | | Updated test case jj-subprocess-1. |
| 2025-07-17 | 4.3.5 | New test case: jj-subprocess-1. |
| | | Update test case process-handle. |
| 2025-07-07 | 4.3.4 | New test case: crypto-3fish. |
| 2025-07-06 | 4.3.3 | New test case: crypto-speck. |
| 2025-07-02 | 4.3.2 | New test case: stat-distros. |
| 2025-05-14 | 4.3.1 | New test case: magic-square. |
| 2025-05-02 | 4.3.0 | Galt-mode does not update the database. |
| | | Move program flags to global for consistency. Simplify function calls. |
| 2025-04-28 | 4.2.1 | -j accepts a specification of "galt" (jacobin -JJ:galt). |
| 2025-04-22 | 4.2.0 | -j accepts a specification of "galt" (jacobin -JJ=galt). |
| 2025-04-17 | 4.1.18 | Updated test case vectors. |
| | | Correct spelling error in src/phases2and3.go. |
| 2025-04-14 | 4.1.17 | In big-decimal-2, make the BigDecimal comparison realistic. |
| 2025-04-13 | 4.1.16 | Still amending test cases big-decimal-1, big-decimal-2. |
| 2025-04-12 | 4.1.15 | New test cases: big-decimal-1, big-decimal-2. |
| 2025-04-09 | 4.1.14 | Updated test case print-objects-linked-lists. |
| 2025-04-08 | 4.1.13 | New test case: print-objects-linked-lists. |
| 2025-04-06 | 4.1.12 | Updated test case JACOBIN-0281-get-property to get properties in a different order. |
| | | Passes on local Ubuntu, GA MacOS, and GA Windows. Failing on GA Ubuntu. |
| | | Updated enigma-machine to moved it along to avoid a bug that is reported elsewhere. |
| | | Updated test case JACOBIN-0281-get-property to work-around null property values. |
| | | Updated test case JACOBIN-0319-println-object to check for null and empty boundary cases in Object & String. |
| 2025-04-06 | 4.1.11 | Updated test case two-pass. New test case list-linked-list-hybrid. |
| 2025-04-05 | 4.1.10 | Updated test case linked-list. |
| 2025-04-04 | 4.1.9 | Make the changed results console output easier to read. |
| | | Test case Integer-all was split into integer-1 and integer-2. |
| | | Added test case integer-3. |
| 2025-04-03 | 4.1.8 | Updated several test cases to avoid library function Arrays.equals(). DIY! |
| | | Added "Ljava/lang/Class;" to ERROR_CATEGORIES.txt. |
| 2025-04-02 | 4.1.7 | Split test case JACOBIN-0212-bit-shifting into bit-shifting-signed & bit-shifting-unsigned. |
| 2025-04-02 | 4.1.6 | Updated test case scimark2 to validate calculations and much more. |
| 2025-04-01 | 4.1.5 | Updated test case ex-catch-multi-frame to include an IREM try-catch. |
| 2025-03-31 | 4.1.4 | Re-instate attribute final on constants in test cases jj-field-static and jj-field-static-2. |
| 2025-03-30 | 4.1.3 | Added test case process-handle; updated test case blum-blum-shub. |
| 2025-03-29 | 4.1.2 | Added test case scimark2-LU-only. |
| | | Updated test cases hashed-set and sha3. |
| 2025-03-27 | 4.1.1 | Added test cases fft-simple, fft-stopwatch, fft-fake, vector-deep-copy. |
| | | Replaced matrix row ref copy with deep copy in test cases desi, scimark2. |
| 2025-03-26 | 4.1.0 | 1st attempt at windows installation instructions. |
| 2025-03-25 | 4.0.18 | Placed "Native method" ahead of "UnsupportedOperationException" in ERROR_CATEGORIES.txt. |
| 2025-03-24 | 4.0.17 | Split nbody-lite into nbody-lite-labels and nbody-lite-nolabels. |
| | | New option -s: Remove the database records of obsolete test cases. |
| 2025-03-21 | 4.0.16 | Remove date and time from -r 3 report so we can diff. |
| | | Reduce maximum loops in several test cases. |
| 2025-03-20 | 4.0.15 | New test case: properties. |
| | | Amended salesman to avoid arrays.deepToString() and now it passes. |
| 2025-03-19 | 4.0.14 | New test cases: base64-mime, base64-std, base64-url (all with and w/o padding). |
| 2025-03-17 | 4.0.13 | New test case: hashed-simply. |
| 2025-03-16 | 4.0.12 | Updated test case: bohr-atom. |
| 2025-03-14 | 4.0.11 | New test case: pi-digits. |
| 2025-03-13 | 4.0.10 | New test cases: iface-lorentz, iface-process-handle. |
| 2025-03-02 | 4.0.9 | Fall forward to Go 1.24. |
| 2025-02-28 | 4.0.8 | Move up UnsupportedOperationException in ERROR_CATEGORIES.txt. |
| 2025-02-22 | 4.0.7 | JACOBIN-671 updated hex formatting in Jacotest cases final-static, jj-field-static, jj-field-static-2. |
| 2025-02-22 | 4.0.6 | JACOBIN-670 added Jacotest case final-static. |
| 2025-02-19 | 4.0.5 | JACOBIN-668 added Jacotest case boolean. |
| 2025-02-19 | 4.0.4 | JACOBIN-659 fixed: test case rsa-mini. |
| 2025-02-18 | 4.0.3 | JACOBIN-664 fixed: phase2and3.go, helpers.go. |
| 2025-02-17 | 4.0.2 | JACOBIN-663 Phase 2: code cleanup and error reporting consolidation. |
| | | * Transaction bracketing on each insert/update/delete. |
| | | * Insure that DBClose() is always called. |
| | | * Handle O/S interrupts by calling DBClose and os.Exit(1). |
| | | * Github Actions new workflow: run_mini.yml, on demand only. |
| | | * Jacotest -r 3 :: Print the last record for each test case. |
| | | * Database insert for uncategorized errors. The failed_text column = UNCATEGORIZED. |
| 2025-02-16 | 4.0.1 | JACOBIN-663 Phase 1: Database & interrupt handling improvements. |
| 2025-02-15 | 3.5.9 | New test case: secure-random. |
| 2025-02-11 | 3.5.8 | New test cases: big-rational, rational-polynomial (extracts from taylor-series). |
| | | Amended rsa-mini to avoid INVOKEDYNAMIC and now it passes. |
| 2025-02-06 | 3.5.7 | New test case: enum-simple. |
| 2025-02-04 | 3.5.6 | Updated test case: playfair, to move on to INVOKEDYNAMIC. |
| 2025-01-30 | 3.5.5 | Updated ERROR_CATEGORIES.txt to prioritize reporting stack {under,over}flows. |
| | | Added show_subclassing.sh to gather subclassing information. |
| | | Updated Github Actions workflow to run show_subclassing.sh on ubuntu only. |
| 2025-01-21 | 3.5.4 | Added more functions to test case atomic-integer. |
| 2025-01-16 | 3.5.3 | New test case: blocking-queues-2-3-5. |
| 2025-01-09 | 3.5.2 | New test case: iface-with-default-func. |
| 2024-12-30 | 3.5.1 | JACOBIN-634: report fix. |
| 2024-12-29 | 3.4.38 | New test case: crypto-salsa must throw new AssertionError. |
| 2024-12-28 | 3.4.37 | New test case: crypto-salsa. |
| 2024-12-19 | 3.4.36 | New ERROR_CATEGORIES.txt entry: GetMethInfoFromCPmethref. |
| | | Added test case iinc-iadd-isub. |
| 2024-12-17 | 3.4.35 | New test case: blum-blum-shub. |
| 2024-12-15 | 3.4.34 | New test case: desi-reduced. |
| 2024-12-12 | 3.4.33 | Make "Bytecode exceeds CheckTable" stand out in error reporting. |
| | | Added test cases mobile-snow-v and mobile-5g-aka. |
| 2024-12-12 | 3.4.32 | Make INVOKEINTERFACE and INVOKEDYNAMIC stand out in error reporting. |
| 2024-12-07 | 3.4.31 | New test case: jj-field-static-2. |
| 2024-12-06 | 3.4.30 | Fixed calculations in test case bohr-atom. |
| 2024-12-04 | 3.4.29 | Updated test case jj-field-static to include more static variable types. |
| 2024-12-03 | 3.4.28 | Updated test case jj-field-static. |
| 2024-12-01 | 3.4.27 | Updated and renamed test case JACOBIN-0626-DumpObject-DumpStatics to jj-field-static. |
| 2024-11-28 | 3.4.26 | Updated test case: JACOBIN-0626-DumpObject-DumpStatics. |
| 2024-11-27 | 3.4.25 | New test case: JACOBIN-0626-DumpObject-DumpStatics. |
| 2024-11-23 | 3.4.24 | New test case: ex-catch-plus-jj. |
| 2024-11-22 | 3.4.23 | New test cases: elliptic-1 and elliptic-2. |
| | | Deleted test case elliptic - which is now elliptic-1 enhanced. |
| 2024-11-21 | 3.4.22 | Highlight "TRAP:". Fiddled with test case io_filewriter. |
| 2024-11-20 | 3.4.21 | Avoid timeout on Windows in test case JACOBIN-0251-array-type-perf. |
| 2024-11-19 | 3.4.20 | Enhanced test case ex-finally. |
| 2024-11-18 | 3.4.19 | New test case: hexxed. |
| 2024-11-17 | 3.4.18 | Enhanced test case JACOBIN-0433-HexFormat. |
| 2024-11-15 | 3.4.17 | Deleted trivial test JACOBIN-0280. |
| | | Modified JACOBIN-0263 to loop less to avoid time-outs. |
| | | Print hex.toString() instead of the generic object print in test case JACOBIN-0433-HexFormat |
| 2024-11-10 | 3.4.16 | Stop JACOBIN-0251-array-type-perf from timing out. |
| 2024-11-09 | 3.4.15 | fixes c/o golangci-lint. |
| 2024-10-07 | 3.4.14 | New test case: crc for CRC32, CRC32C, and Adler32. |
| 2024-09-26 | 3.4.13 | New test case: JACOBIN-0586-statics-survival. |
| 2024-09-23 | 3.4.12 | New test cases: stringer-4 and stringer-5. |
| 2024-09-22 | 3.4.11 | New test case: stringer-3. |
| 2024-08-19 | 3.4.10 | New test case: catch-8-survivor. |
| 2024-08-17 | 3.4.9 | New error category: null class name is invalid. |
| 2024-08-13 | 3.4.8 | Avoid INVOKEDYNAMIC in test case bohr-atom. |
| 2024-08-09 | 3.4.7 | Fix issue #24: test case JACOBIN-0263-gc. |
| 2024-08-07 | 3.4.6 | New test cases: stringbuffer-append, stringbuffer-delete, stringbuffer-dynamic, stringbuffer-insert, stringbuffer-misc, stringbuffer-perf. |
| | | New test cases: stringbuilder-replace, stringbuffer-replace. |
| 2024-08-07 | 3.4.5 | New test case: stringbuilder-misc. |
| | | Updated test case solitairgraphy. |
| 2024-08-06 | 3.4.4 | New test cases: stringbuilder-insert, stringbuilder-delete. |
| 2024-08-05 | 3.4.3 | New test cases: stringbuilder-dynamic, stringbuilder-append. |
| 2024-07-21 | 3.4.2 | Updated ERROR_CATEGORIES.txt. |
| | | Changed trimming tactic (it probably does not matter). |
| 2024-07-19 | 3.4.1 | Updated ERROR_CATEGORIES.txt. |
| 2024-07-15 | 3.4.0 | New feature: delete the most recent pass/fail record for each case using option -z. |
| | | Fix issue #23: -x command line option. |
| 2024-07-10 | 3.3.6 | Fixed stringer-1 whitespace issue. |
| 2024-07-05 | 3.3.5 | Removed utilities directory as they are no longer in use. |
| | | Ditto for directories py and misc.java. |
| 2024-07-02 | 3.3.4 | Update test case two-fish to avoid not yet supported classes. |
| 2024-07-01 | 3.3.3 | Fix issue #22 (protect log files from unnecessary deletion). |
| 2024-06-23 | 3.3.2 | Updated test cases ex-catch-idiv, ex-catch-parseint, and ex-multilevel. |
| 2024-06-22 | 3.3.1 | Added test case ex-catch-multiframe. |
| 2024-06-20 | 3.3.0 | Added a report to show failure test cases that previously passed. |
| | | Updated random-1 by removing calls to an undocumented function. |
| 2024-06-19 | 3.2.19 | Get blockchain past distractions. |
| 2024-06-18 | 3.2.18 | New test cases: big-integer-3, paillier-cryptosystem. |
| | | Got rid of INVOKEDYNAMIC in test case miller-rabin-test. |
| 2024-06-15 | 3.2.17 | New test case: ex-catch-called-func-parseint |
| 2024-06-08 | 3.2.16 | New test case: ex-catch-big-integer. |
| 2024-06-06 | 3.2.15 | New test case: big-integer-2. |
| 2024-06-03 | 3.2.14 | New test case: big-integer. |
| 2024-05-31 | 3.2.13 | New test cases: bohr-atom, switcheroo-2. |
| 2024-05-30 | 3.2.12 | Fix issue #20: No need to scan passed test logs for error fragments. |
| | | Fix issue #21: Promote "Invalid bytecode" in ERROR_CATEGORIES.txt. |
| | | Other ERROR_CATEGORIES.txt edits and additions. |
| 2024-05-29 | 3.2.11 | ERROR_CATEGORIES.txt additions. |
| 2024-05-27 | 3.2.10 | ERROR_CATEGORIES.txt edits and re-organisation. |
| 2024-05-24 | 3.2.9 | Removed test case: jframe-1. |
| 2024-05-23 | 3.2.8 | New test case: emission-line-spectra-nofuncs. |
| 2024-05-17 | 3.2.7 | ERROR_CATEGORIES.txt: added NullPointerException. |
| | | New test case: ex-multilevel. |
| 2024-05-14 | 3.2.6 | Added WrongMethodTypeException to error categories. |
| | | Added "lacks a FilePath field" to error categories. |
| 2024-05-06 | 3.2.5 | New test case: atomic-integer. |
| | | Amended emission-spectra to avoid INVOKEDYNAMIC. |
| 2024-05-06 | 3.2.4 | New test case: stringtokenizer. |
| 2024-05-05 | 3.2.3 | New test case: random-1. |
| 2024-05-04 | 3.2.2 | New test cases: natives-double and natives-float. |
| 2024-05-03 | 3.2.1 | Implement compiler option -Werror (treat warnings as errors). |
| 2024-04-27 | 3.1.14 | Updated test case: charset-encoding. |
| 2024-04-22 | 3.1.13 | New test cases: JACOBIN-0476-arraycopy and io_file_create_delete. |
| 2024-04-12 | 3.1.12 | New test case: tiffy. |
| | | Amend io_* test cases to delete temp file created during execution. |
| 2024-04-10 | 3.1.11 | New io_* tests. |
| 2024-04-05 | 3.1.10 | New io_* tests. |
| 2024-04-03 | 3.1.9 | Renamed test case JACOBIN-0296-new-fileinputstream to fileinputstream. |
| 2024-03-31 | 3.1.8 | ERROR_CATEGORIES.txt updated: "Native method requested". |
| | | New test case: wide. |
| 2024-03-27 | 3.1.7 | Updated test case: switcheroo. |
| 2024-03-26 | 3.1.6 | New test case: taylor-series-2. |
| 2024-03-19 | 3.1.5 | New test case: jframe-1. |
| 2024-03-17 | 3.1.4 | Added new error category for mismatched types. |
| 2024-03-15 | 3.1.3 | New test case: Integer-all. |
| 2024-03-14 (Pi Day) | 3.1.2 | Consolidate error category: go panic because of interface conversion. |
| | | Some minor source fixes. Recompile, please. |
| | | nbody and kalman-filtering number of steps shortened to avoid timeout. |
| 2024-03-11 | 3.1.1 | Replace failed case text instances of apostrophe and double-quote with asterisk. |
| 2024-03-10 | 3.1.0 | If database repeated insert fails, report and carry on. |
| 2024-03-06 | 3.0.15 | New test case: switcheroo (compact and lookup switches). |
| | | New test cases: enum-inside and enum-outside. |
| | | Renamed test case JACOBIN-0206 to nbody. |
| | | New test case: nbody-lite. |
| 2024-02-29 | 3.0.14 | Fixed test cases: JACOBIN-0322-default-locale, JACOBIN-0325-super-1, stringer-2. |
| 2024-02-28 | 3.0.13 | Renamed test case: JACOBIN-0435-format-neg-int-as-hex --> JACOBIN-0435-format-int-as-hex. |
| | | Split interface conversion errors in the report. |
| | | Stop testing type char in numbers-chars-strings. |
| | | Stop testing type char in JACOBIN-0393-pot-pourri. |
| 2024-02-23 | 3.0.12 | New test case: beetlejuice. |
| 2024-02-22 | 3.0.11 | Updated the error categories for ARRAYLENGTH anomalies. |
| 2024-02-21 | 3.0.10 | Fixed test case JACOBIN-0337-static-inits. |
| 2024-02-20 | 3.0.9 | Fixed test case JACOBIN-0435-format-neg-int-as-hex. |
| 2024-02-19 | 3.0.8 | Fixed test case parseint. |
| | | Add genstatics.py to new folder "py". |
| 2024-02-15 | 3.0.7 | Fixed & looped test cases ex-catch-parseint and hex-decode-numeric. |
| 2024-02-15 | 3.0.6 | Added utility jtsync - automates git pull, go install, Jacotest -c. |
| 2024-02-15 | 3.0.5 | Updated test case ex-finally-2 to measure success. |
| 2024-02-14 | 3.0.4 | New test case: ex-finally-2. |
| 2024-02-10 | 3.0.3 | New test case: floor-div-mod-mix. |
| | | Added loops to test case ex-catch-idiv. |
| 2024-01-31 | 3.0.2 | New test case: ex-catch-idiv. |
| | | Renamed exception-oriented test cases: |
| | | * `catch-assertion-error ----> ex-catch-assertion-error` |
| | | * `catch-exception ----------> ex-catch-parseint` |
| | | * `JACOBIN-0322-throw-null --> ex-catch-null-ptr` |
| | | * `finally ------------------> ex-finally` |
| | | * `user-defined-exception ---> ex-catch-user-ex` |
| 2024-01-28 | 3.0.1 | New test case: salesman. |
| 2024-01-22 | 3.0.0 | Revamp of command-line parameters (JACOBIN-441). |
| 2024-01-20 | 2.9.5 | New error category: should not be used to create. |
| 2024-01-19 | 2.9.4 | New test case: db-sqlite. |
| 2024-01-18 | 2.9.3 | New test case: sieve. |
| 2024-01-16 | 2.9.2 | New test case: miller-rabin-test. |
| 2024-01-15 | 2.9.1 | New test cases: |
| | | JACOBIN-0433-HexFormat |
| | | JACOBIN-0434-short-value |
| | | JACOBIN-0435-format-neg-int-as-hex |
| 2024-01-13 | 2.9.0 | To get a github MD file, specify option -M. |
| 2024-01-13 | 2.8.4 | Cleanup test case source code. Will not be the last! |
| 2024-01-10 | 2.8.3 | New error category: has no exception table. |
| | | Add cleaner function to log and report output. |
| | | Call cleaner function inside runner. |
| 2023-12-29 | 2.8.2 | New test case: hex-decode-numeric. |
| 2023-12-15 | 2.8.1 | New test cases: https-client-getter, http-client-server, and walker. |
| 2023-12-14 | 2.8.0 | Changed database toolset from mattn (cgo required) to modernc (no cgo). |
| 2023-12-13 | 2.7.6 | Code clean-up. No functional changes. |
| 2023-12-09 | 2.7.5 | Renamed test case JACOBIN-0294-system-exit to parent-child-process. |
| 2023-11-25 | 2.7.4 | Made utility cleansrc more generic. |
| | | Cleansed Java source files of non-printables. |
| 2023-11-24 | 2.7.3 | New utility: cleansrc. |
| 2023-11-23 | 2.7.2 | New test case: java-logging. |
| 2023-11-21 | 2.7.1 | New test case: stack-walk. |
| 2023-11-20 | 2.7.0 | Resolved JACOBIN-405. |
| | | Implemented file ERROR_CATEGORIES.txt. |
| 2023-11-19 | 2.6.22 | Work-around until JACOBIN-405 is addressed. |
| 2023-11-11 | 2.6.21 | Test case stringer-1 fixed and expanded. |
| | | Fixed test case playfair. |
| | | Fixed JACOBIN-0319-println-object. |
| | | Jacobin PUTSTATIC field name adjustment. |
| 2023-11-10 | 2.6.20 | Test case JACOBIN-0322-default-locale expanded. |
| 2023-11-08 | 2.6.19 | New test case: JACOBIN-0393-pot-pourri |
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
| 2023-09-17 | 2.6.2 | Complement Jacobin PR #71 - JACOBIN-354. |
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
| | | Added the JVM -ea option to Jacobin and openjdk-java invocations. |
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
| | | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;thus, leveraging Jacobin classloader/javaLangMath.go.
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
|            |       | COMMAND = Jacobin, openjdk, or javac |
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

