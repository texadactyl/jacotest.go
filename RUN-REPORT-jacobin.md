Jacotest version 1.0
Run report using JVM jacobin
<br>Date/Time 2023-04-26 04:03:17 CDT
<br>
<br>
| Test Case | Result | Console Output |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.
|||Testing polymorphism, abstract classes, abstract methods, and interfaces.
|||
|||Insider class will now be instantiated .....
|||fatal error: concurrent map read and map write
|||
|||goroutine 1 [running]:
|||jacobin/classloader.LoadClassFromNameOnly({0xc000018224, 0xc})
|||	/home/elkins/BASIS/jacobin/src/classloader/classloader.go:273 +0x3e
|||jacobin/jvm.instantiateClass({0xc000018224, 0xc})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:30 +0x10c
|||jacobin/jvm.runFrame(0xc00007f110)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00001823c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
|||
|||goroutine 6 [runnable]:
|||jacobin/classloader.insert({0xc0000182e6, 0xa}, {0xe6?, {0x0?, 0x0?}, 0x0?})
|||	/home/elkins/BASIS/jacobin/src/classloader/classloader.go:401 +0x74
|||jacobin/classloader.LoadFromLoaderChannel(0x0?)
|||	/home/elkins/BASIS/jacobin/src/classloader/classloader.go:266 +0x85
|||created by jacobin/classloader.LoadReferencedClasses
|||	/home/elkins/BASIS/jacobin/src/classloader/classloader.go:247 +0x19a
||| |
| JACOBIN-0161-instantiate_class | FAILED | Testing one instantiation of a class residing in a separate source file
|||Library lib will be instantiated .....
|||Error: could not find or load class Library.
|||Error loading class: Library. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000181f7, 0x7})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018230, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0206-nbody | FAILED | Model the orbits of the Jovian planets, using the Symplectic integration Package
|||URL: https://janus.astro.umd.edu/HNBody/
|||Error: could not find or load class main$NBodySystem.
|||Error loading class: main$NBodySystem. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc000018210, 0x10})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ee40)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000181ac, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0211-pbcrypto | FAILED | Exercise Password-based Encryption/Decryption
|||Could not find class: java/lang/Integer
||| |
| JACOBIN-0212-bit-shifting | FAILED | Four bit shifting test cases
|||FAILED trying -100 >> 2. Expected -25. Observed 39
|||FAILED trying -100 << 3. Expected -800. Observed 1248
|||Success trying +100 >> 2 == 25
|||Success trying +100 << 3 == 800
|||Error count = 2
|||Going to thrrow an Exception next .....
|||fatal error: concurrent map read and map write
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/lang/Exception.class.
|||Error loading class: java/lang/Exception. Exiting.
|||
|||goroutine 6 [running]:
|||jacobin/classloader.LoadFromLoaderChannel(0x0?)
|||	/home/elkins/BASIS/jacobin/src/classloader/classloader.go:255 +0x65
|||created by jacobin/classloader.LoadReferencedClasses
|||	/home/elkins/BASIS/jacobin/src/classloader/classloader.go:247 +0x19a
|||
|||goroutine 1 [running]:
|||	goroutine running on other thread; stack unavailable
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc00001e0d8, 0x13})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018220, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0217-multidim | FAILED | Testing multidimensional arrays of type int, float, double, and String
|||Invalid bytecode found: 197 (0xC5) (MULTIANEWARRAY) at location 20 in method main() of class main
|||
||| |
| JACOBIN-0227-string-array | PASSED | n/a |
| JACOBIN-0231-stats | TIMEOUT | Testing basic statistical functions and a square root algorithm
|||Library lib will be instantiated .....
||| |
| JACOBIN-0234-0240-0241-array-length | FAILED | Exercise array lengths for type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc00010ee10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xd757
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0001361ac, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0234-string-length | FAILED | Try to get run.go to barf at line 1388
|||password constructed
|||originalString constructed
|||Could not find class: java/lang/Integer
||| |
| JACOBIN-0235-system-exit | PASSED | n/a |
| JACOBIN-0236-bitwise | TIMEOUT | Perform various integer operations
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
||| |
| JACOBIN-0236-minus-signs | TIMEOUT | Test the use of minus signs in integer operations
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
||| |
| JACOBIN-0237-nil-printlns | PASSED | n/a |
| array-list-iterator | FAILED | Begin ArrayList/iterator tests
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/util/ArrayList.class.
|||Error loading class: java/util/ArrayList. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc00001e0d8, 0x13})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc000100c00)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018274, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| arrays_1 | FAILED | Testing accessibility of array elements of type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0000a6fc0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xd757
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c61c4, 0x4}, 0x6108e0)
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
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/util/HashMap.class.
|||Error loading class: java/util/HashMap. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000d20a8, 0x11})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc0000a0e10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c41e4, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| hashed-set | FAILED | Testing a hashed set
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/util/HashSet.class.
|||Error loading class: java/util/HashSet. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc00001e0d8, 0x11})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ee10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018250, 0x4}, 0x6108e0)
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
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/util/LinkedList.class.
|||Error loading class: java/util/LinkedList. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0001420c0, 0x14})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00010ee10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0001341cc, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| negtest-comp-error | COMP-ERROR | compilation error(s)
 | | | See logs/FAILED-*-javac.log files |
| negtest-runner-throw-exception | FAILED | I will catch a NumberFormatException
|||Could not find class: java/lang/Integer
||| |
| negtest-runner-timeout | FAILED | I will timeout!
|||Could not find class: java/lang/Thread
||| |
| numbers-chars-strings | FAILED | Member function tests for Character, Double, Integer, and String
|||Could not find class: ProcCharacter
||| |
| packaging-1 | FAILED | Testing the use of import pkgcalc.Calculator
|||Error: could not find or load class pkgcalc/Calculator.
|||Error loading class: pkgcalc/Calculator. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc00001e0d8, 0x12})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018218, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-2 | FAILED | Testing the use of import pkgcalc.Calculator
|||Error: could not find or load class middle/pkgcalc/Calculator.
|||Error loading class: middle/pkgcalc/Calculator. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000200a0, 0x19})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018218, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| scimark2 | FAILED | SciMark2: Benchmark measuring performance	of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc00007ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1520 +0xcdd7
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000183bc, 0x4}, 0x6108e0)
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
|||jacobin/jvm.runFrame(0xc00007ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1520 +0xcdd7
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000182bc, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| specrel | FAILED | Special Relativity calculations
|||===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||Error: could not find or load class Formulae.
|||Error loading class: Formulae. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc000018230, 0x8})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00001829c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| threading | FAILED | Threading tests with a parent thread (main) and 3 child threads
|||Error: could not find or load class PrintingSynced.
|||Error loading class: PrintingSynced. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc000134190, 0xe})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00010ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00013420c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| user-defined-exception | FAILED | Error: could not find or load class MyException.
|||Error loading class: MyException. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e91ef]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000181c0, 0xb})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1441 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018230, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
