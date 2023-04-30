Jacotest version 1.0
Run report using JVM jacobin, deadline = 30 seconds
Date/Time 2023-04-29 20:13:20 CDT
<br>
<br>
| Test Case | Result | Console Output |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.
|||Testing polymorphism, abstract classes, abstract methods, and interfaces.
|||
|||Insider class will now be instantiated .....
|||Error: could not find or load class main$Insider.
|||instantiateClass: LoadClassFromNameOnly(main$Insider) failed. Exiting.
||| |
| JACOBIN-0161-instantiate_class | FAILED | Testing one instantiation of a class residing in a separate source file
|||Library lib will be instantiated .....
|||Error: could not find or load class Library.
|||instantiateClass: LoadClassFromNameOnly(Library) failed. Exiting.
||| |
| JACOBIN-0206-nbody | FAILED | Model the orbits of the Jovian planets, using the Symplectic integration Package
|||URL: https://janus.astro.umd.edu/HNBody/
|||Error: could not find or load class main$NBodySystem.
|||instantiateClass: LoadClassFromNameOnly(main$NBodySystem) failed. Exiting.
||| |
| JACOBIN-0211-pbcrypto | FAILED | Exercise Password-based Encryption/Decryption
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011618db0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xdf97
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115fede0, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0212-bit-shifting | FAILED | Four bit shifting test cases
|||FAILED trying -100 >> 2. Expected -25. Observed 39
|||FAILED trying -100 << 3. Expected -800. Observed 1248
|||Success trying +100 >> 2 == 25
|||Success trying +100 << 3 == 800
|||Error count = 2
|||Going to thrrow an Exception next .....
|||error creating field in: java/lang/Exception
|||Class Format Error: invalid field type
|||  detected by file: classloader.go, line: 180
||| |
| JACOBIN-0217-multidim | FAILED | Testing multidimensional arrays of type int, float, double, and String
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0xb pc=0x4ee7bb]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116489f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:381 +0x2e7b
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011630ce0, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0227-string-array | PASSED | n/a |
| JACOBIN-0231-stats | FAILED | Testing basic statistical functions and a square root algorithm
|||Library lib will be instantiated .....
|||instantiateClass: Status is still 'I' waiting for class: Library. Overdue!
||| |
| JACOBIN-0234-0240-0241-array-length | FAILED | Exercise array lengths for type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01167eab0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xdf97
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc01166ac9c, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0234-string-length | FAILED | Try to get run.go to barf at line 1388
|||password constructed
|||originalString constructed
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116169f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xdf97
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115fecf0, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0235-system-exit | PASSED | n/a |
| JACOBIN-0236-bitwise | FAILED | Perform various integer operations
|||a=10 and b=25
|||Success trying a + b == 35
|||c = a - b: -15
|||FAILED if(c != -15). Expected false. Observed true
|||Success trying b / a == 2
|||Success trying b % a == 5
|||a=60 and b=13
|||Success trying b % a == 12
|||Success trying b | a == 61
|||Success trying b ^ a == 49
|||c = ~a : -61
|||Success trying ~a == -61
|||c = ~60 : 195
|||FAILED trying ~60. Expected -61. Observed 195
|||Success trying a>>>2 == 15
|||A=true and B=false
|||Success trying A && B == false
|||Success trying A || B == true
|||Success trying c = (a == 42) ? 1001: 1002 ==>> 1002
|||Error count = 2
|||Going to thrrow an Exception next .....
|||error creating field in: java/lang/Exception
|||Class Format Error: invalid field type
|||  detected by file: classloader.go, line: 180
||| |
| JACOBIN-0236-minus-signs | FAILED | Test the use of minus signs in integer operations
|||a: 60
|||b: 13
|||c should be -47 : 209
|||FAILED Expected c < 0. Observed 209
|||
|||c should be 13 - 60 = -47 : -47
|||FAILED Expected b - a == -47. Observed -47
|||
|||c = ~60 ==> 195
|||FAILED trying c = ~60. Expected -61. Observed 195
|||
|||Error count = 3
|||Going to thrrow an Exception next .....
|||error creating field in: java/lang/Exception
|||Class Format Error: invalid field type
|||  detected by file: classloader.go, line: 180
||| |
| JACOBIN-0237-nil-printlns | PASSED | n/a |
| array-list-iterator | FAILED | Begin ArrayList/iterator tests
|||error creating field in: java/util/ArrayList Invalid type: (Ljava/util/List;II)Z
|||Class Format Error: invalid field type
|||  detected by file: classloader.go, line: 180
||| |
| arrays_1 | FAILED | Testing accessibility of array elements of type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01166ec60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xdf97
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011656cb4, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| elliptic | FAILED | Elliptic cryptography exercise
|||Could not find class: java/security/KeyPairGenerator
||| |
| hashed-map | FAILED | Testing a hashed map
|||error creating field in: java/util/HashMap
|||Class Format Error: invalid field type
|||  detected by file: classloader.go, line: 180
||| |
| hashed-set | FAILED | Testing a hashed set
|||error creating field in: java/util/HashSet
|||Class Format Error: invalid field type
|||  detected by file: classloader.go, line: 180
||| |
| java17-enhancements | FAILED | Some of the Java 17 Enhancements
|||panic: runtime error: index out of range [-1]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.pop(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1677
|||jacobin/jvm.runFrame(0xc01161ade0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xdfa5
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011602cc8, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| lambdas_maps | FAILED | Invalid bytecode found: 186 (0xBA) (INVOKEDYNAMIC) at location 2 in method main() of class main
|||
||| |
| linked-list | FAILED | Testing linked lists
|||error creating field in: java/util/LinkedList
|||Class Format Error: invalid field type
|||  detected by file: classloader.go, line: 180
||| |
| negtest-comp-error | COMP-ERROR | compilation error(s)
 | | | See logs/FAILED-*-javac.log files |
| negtest-runner-throw-exception | FAILED | I will catch a NumberFormatException
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01161c9f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xdf97
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011604ca0, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| negtest-runner-timeout | FAILED | I will timeout!
|||panic: runtime error: index out of range [0] with length 0
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116929f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1396 +0xd9f7
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc01167acac, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| numbers-chars-strings | FAILED | Member function tests for Character, Double, Integer, and String
|||Could not find class: ProcCharacter
||| |
| packaging-1 | FAILED | Testing the use of import pkgcalc.Calculator
|||Error: could not find or load class pkgcalc/Calculator.
|||instantiateClass: LoadClassFromNameOnly(pkgcalc/Calculator) failed. Exiting.
||| |
| packaging-2 | FAILED | Testing the use of import pkgcalc.Calculator
|||Error: could not find or load class middle/pkgcalc/Calculator.
|||instantiateClass: LoadClassFromNameOnly(middle/pkgcalc/Calculator) failed. Exiting.
||| |
| scimark2 | FAILED | SciMark2: Benchmark measuring performance	of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116a49f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1520 +0xd611
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc01168ce3c, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sha3 | FAILED | SHA-3 hashing tests
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116b89f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1520 +0xd611
|||jacobin/jvm.runThread(0x612460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0116a0d3c, 0x4}, 0x612900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| specrel | FAILED | Special Relativity calculations
|||===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||Error: could not find or load class Formulae.
|||instantiateClass: LoadClassFromNameOnly(Formulae) failed. Exiting.
||| |
| threading | FAILED | Threading tests with a parent thread (main) and 3 child threads
|||Error: could not find or load class PrintingSynced.
|||instantiateClass: LoadClassFromNameOnly(PrintingSynced) failed. Exiting.
||| |
| user-defined-exception | FAILED | Error: could not find or load class MyException.
|||instantiateClass: LoadClassFromNameOnly(MyException) failed. Exiting.
||| |
| vectors | FAILED | Fun with vectors
|||error creating field in: java/lang/Integer
|||Class Format Error: invalid field type
|||  detected by file: classloader.go, line: 180
||| |
