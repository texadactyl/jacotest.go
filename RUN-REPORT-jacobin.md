Jacotest version 1.06

Run report using JVM jacobin<br>Case deadline = 30 seconds<br>Date/Time 2023-05-08 13:05:37 CDT<br><br>
| Test Case | Result | Console Output |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.
|||Testing polymorphism, abstract classes, abstract methods, and interfaces.
|||
|||Insider class will now be instantiated .....
|||lucretiaInsider class was instantiated*** checkStrUnequal: equal but should not be !! 
|||Insider class was instantiated
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011592d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01157acbc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0161-instantiate_class | FAILED | Testing one instantiation of a class residing in a separate source file
|||Library lib will be instantiated .....
|||panic: runtime error: index out of range [32] with length 24
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011602960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1366 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115e8cbc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0206-nbody | FAILED | Model the orbits of the Jovian planets, using the Symplectic integration Package
|||URL: https://janus.astro.umd.edu/HNBody/
|||Codeadvance(D)V
|||panic: interface conversion: interface {} is unsafe.Pointer, not float64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115e2a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1429 +0x9e46
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115ccc34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0211-pbcrypto | FAILED | Exercise Password-based Encryption/Decryption
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011578d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011560de0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0212-bit-shifting | PASSED | n/a |
| JACOBIN-0217-multidim-2d | PASSED | n/a |
| JACOBIN-0217-multidim-3d | FAILED | Testing 2D and 3D arrays of type int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01160e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runFrame(0xc01160e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1457 +0x9865
|||jacobin/jvm.runFrame(0xc01160e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1457 +0x9865
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f6ce0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0227-string-array | PASSED | n/a |
| JACOBIN-0231-stats | FAILED | Testing basic statistical functions and a square root algorithm
|||Library lib will be instantiated .....
|||valueOfx-stdev: %f
|||y-mean: %f
|||
|||Library lib was instantiated
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc00b63d860, 0x14}, {0xc00b649949, 0x3}, {0xc00b64994c, 0x4})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01165a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1397 +0x77a5
|||jacobin/jvm.runFrame(0xc01165a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1457 +0x9865
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011642d1c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0234-0240-0241-array-length | FAILED | Exercise array lengths for type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011602a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115ecc9c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0234-string-length | FAILED | Try to get run.go to crash in INVOKDESTATIC
|||password constructed
|||originalString constructed
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01168a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011672cf0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0235-system-exit | PASSED | n/a |
| JACOBIN-0236-bitwise | PASSED | n/a |
| JACOBIN-0236-minus-signs | PASSED | n/a |
| JACOBIN-0237-nil-printlns | PASSED | n/a |
| array-list-iterator | FAILED | Begin ArrayList/iterator tests
|||()Zsize()I
|||panic: runtime error: index out of range [3] with length 3
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc01151a8e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1769 +0xe6
|||jacobin/jvm.runFrame(0xc011518960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:240 +0xcdb
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011500cfc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| arrays_1 | FAILED | Testing accessibility of array elements of type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01165abd0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011642cb4, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| blockchain | FAILED | Blockchain exercise: create, series of adds, and chain verify
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011616c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runFrame(0xc011616c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1457 +0x9865
|||jacobin/jvm.runFrame(0xc011616c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1457 +0x9865
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fcc34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| casting | FAILED | Widening and Narrowing Casting
|||Invalid bytecode found: 186 (0xBA) (INVOKEDYNAMIC) at location 17 in method main() of class main
|||
||| |
| catch-exception | FAILED | I will catch a NumberFormatException
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011594960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01157eca0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| dedupe-hacked | FAILED | FileDedupe v. 2.0 (c) Copyright 2017-20 Andrew Binstock. All rights reserved.
|||
|||panic: interface conversion: interface {} is int, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011654c60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01163cc34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| elliptic | FAILED | Elliptic cryptography exercise
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc0115fddc0, 0x1e}, {0xc011640c95, 0xb}, {0xc0115e1900, 0x46})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011658990)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1397 +0x77a5
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011640c78, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| hashed-map | FAILED | Testing a hashed map
|||checker(Ljava/lang/Object;)VBerlin
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc011654d30?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1769 +0xe6
|||jacobin/jvm.runFrame(0xc011660a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:240 +0xcdb
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011648cd4, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| hashed-set | FAILED | Testing a hashed set
|||! cars.contains(Chevrolet)mainMazda
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc0115e2930?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1769 +0xe6
|||jacobin/jvm.runFrame(0xc0115e0a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:240 +0xcdb
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115cacd0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| java17-enhancements | FAILED | Some of the Java 17 Enhancements
|||IntStream, RandomGeneratorFactory, InstantSource, HexFormat
|||panic: runtime error: index out of range [-1]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.pop(0xc0113cd060?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1748 +0xb4
|||jacobin/jvm.runFrame(0xc0115b8d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9b26
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115a2cc8, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| lambdas_maps | FAILED | Lambdas and Maps
|||Invalid bytecode found: 186 (0xBA) (INVOKEDYNAMIC) at location 10 in method main() of class main
|||
||| |
| linked-list | FAILED | Testing linked lists
|||removeFirstmainMazda
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc011650d30?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1769 +0xe6
|||jacobin/jvm.runFrame(0xc01165ca20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:240 +0xcdb
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011642cbc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| negtest-comp-error | COMP-ERROR | compilation error(s)
 | | | See logs/FAILED-*-javac.log files |
| negtest-runner-timeout | FAILED | I will timeout!
|||panic: runtime error: index out of range [0] with length 0
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01167e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1450 +0x9911
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011666cac, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| numbers-chars-strings | FAILED | Member function tests for Character, Double, Integer, and String
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc01163ac80, 0xd}, {0xc01163ac90, 0xd}, {0xc01163ac7a, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011654960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1397 +0x77a5
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01163ad24, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-1 | FAILED | Testing the use of import pkgcalc.Calculator
|||panic: runtime error: index out of range [26] with length 26
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115dc960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1366 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c4ca0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-2 | FAILED | Testing the use of import pkgcalc.Calculator
|||panic: runtime error: index out of range [26] with length 26
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115a2960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1366 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011588ca0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| scimark2 | FAILED | SciMark2: Benchmark measuring performance	of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011618960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1574 +0x9585
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fee3c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sha3 | FAILED | SHA-3 hashing tests
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115a0960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1574 +0x9585
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011588d3c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sockets | FAILED | Socket tests with a parent thread (main) and 2 child threads (server, client)
|||printLabeledMsgjava/lang/ExceptionInterrupted !!
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b280?, {0x5061c0?, 0x5fbfb0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1769 +0xe6
|||jacobin/jvm.runFrame(0xc011612a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1312 +0x71cf
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f8c98, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| specrel | FAILED | Special Relativity calculations
|||===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||panic: runtime error: index out of range [41] with length 34
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011566960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1365 +0x9f05
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01154ed1c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| stringer | FAILED | String method tests
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115deae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1442 +0x9e65
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c4c8c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| threading | FAILED | Threading tests with a parent thread (main) and 3 child threads
|||main: T1 joinedout([Ljava/lang/String;)V
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc011630c90?, {0x506940?, 0xc011648d80?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1769 +0xe6
|||jacobin/jvm.runFrame(0xc011648960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:344 +0x1a9a
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011630cfc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| tls-one-way | FAILED | main: Make cryptographically strong random number generator
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc01153dd80, 0x1a}, {0xc011580c90, 0xb}, {0xc01158cfc0, 0x30})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011598a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1397 +0x77a5
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011580cec, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| user-defined-exception | FAILED | Throw a user-defined exception
|||panic: runtime error: index out of range [26] with length 6
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01159a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1362 +0x9f0d
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011582cb0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| vectors | FAILED | Fun with vectors
|||Error: could not find or load class classes/java/util/Vector.class.
|||panic: interface conversion: interface {} is int64, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011596ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:417 +0xad0b
|||jacobin/jvm.runFrame(0xc011596ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1457 +0x9865
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011580c8c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| zippy | FAILED | Directory of the zip is as follows:
|||Error: could not find or load class classes/java/util/zip/ZipInputStream.class.
|||panic: runtime error: index out of range [48] with length 25
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115d4b10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1362 +0x9f0d
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115bec34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
