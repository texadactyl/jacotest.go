Jacotest version 1.0
Run report using JVM jacobin
<br>Date/Time 2023-04-20 04:02:12 CDT
<br>
<br>
| Test Case | Result | Console Output |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.
|||Testing polymorphism, abstract classes, abstract methods, and interfaces.
|||
|||Insider class will now be instantiated .....
|||Error: could not find or load class main$Insider.
|||Error loading class: main$Insider. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0001341b4, 0xc})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00010f200)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1436 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0001341cc, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0161-instantiate_class | FAILED | Testing one instantiation of a class residing in a separate source file
|||Library lib will be instantiated .....
|||Error: could not find or load class Library.
|||Error loading class: Library. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000c41a0, 0x7})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc0000a0d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1436 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c41cc, 0x4}, 0x6108e0)
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
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc000018210, 0x10})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007ee40)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1436 +0xb7ed
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
|||Invalid bytecode found: 186 (0xBA) (INVOKEDYNAMIC) at location 33 in method main() of class main
|||
||| |
| JACOBIN-0212-bit-shifting | TIMEOUT | Four bit shifting test cases
|||FAILED trying -100 >> 2. Expected -25. Observed 39
|||FAILED trying -100 << 3. Expected -800. Observed 1248
|||Success trying +100 >> 2 == 25
|||Success trying +100 << 3 == 800
|||Error count = 2
|||Going to thrrow an Exception next .....
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
|||jacobin/jvm.runFrame(0xc0000a0f00)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1383 +0xd657
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c41ac, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0235-system-exit | FAILED | Perform a System.exit(0)
|||Could not find class: java/lang/System
||| |
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
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/lang/Exception.class.
|||Error loading class: java/lang/Exception. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000d00c0, 0x13})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc0000a0d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1436 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c41dc, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
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
| arrays_1 | FAILED | Testing accessibility of array elements of type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc00007cfc0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1383 +0xd657
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018230, 0x4}, 0x6108e0)
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
| scimark2 | FAILED | SciMark2: Benchmark measuring performance	of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc00010ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1515 +0xccd7
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00013434c, 0x4}, 0x6108e0)
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
|||jacobin/jvm.runFrame(0xc00007cd50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1515 +0xccd7
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
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000c41c8, 0x8})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc0000a0d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1436 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c422c, 0x4}, 0x6108e0)
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
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc000018200, 0xe})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007cd50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1436 +0xb7ed
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00001827c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
