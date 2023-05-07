Jacotest version 1.06

Run report using JVM jacobin<br>Case deadline = 30 seconds<br>Date/Time 2023-05-07 16:47:48 CDT<br><br>
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
|||jacobin/jvm.runFrame(0xc01165ad20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011642cbc, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc0115d4960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1366 +0xe56d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115becbc, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc0115f8a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1431 +0xe4e5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115e0c34, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0211-pbcrypto | FAILED | Exercise Password-based Encryption/Decryption
|||Error: could not find or load class classes/javax/crypto/SecretKeyFactory.class.
|||Error: could not find or load class classes/javax/crypto/spec/PBEKeySpec.class.
|||Error: could not find or load class classes/javax/crypto/spec/SecretKeySpec.class.
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011578d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011562de0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc011652960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runFrame(0xc011652960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1459 +0xde2e
|||jacobin/jvm.runFrame(0xc011652960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1459 +0xde2e
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01163ace0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d40d5]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc00b647860, 0x14}, {0xc00b653949, 0x3}, {0xc00b65394c, 0x4})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011664960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1397 +0xb7a5
|||jacobin/jvm.runFrame(0xc011664960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1459 +0xde2e
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01164cd1c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0234-0240-0241-array-length | FAILED | Exercise array lengths for type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011598a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011580c9c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0234-string-length | FAILED | Try to get run.go to barf at line 1388
|||password constructed
|||originalString constructed
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011652960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011638cf0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.push(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1759
|||jacobin/jvm.runFrame(0xc011616960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:239 +0x106c5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115fccfc, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| arrays_1 | FAILED | Testing accessibility of array elements of type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01160cbd0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f2cb4, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| blockchain | FAILED | Blockchain exercise: create, series of adds, and chain verify
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011648c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runFrame(0xc011648c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1459 +0xde2e
|||jacobin/jvm.runFrame(0xc011648c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1459 +0xde2e
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01162ec34, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc011654960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01163cca0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc01164ec60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011634c34, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| elliptic | FAILED | Elliptic cryptography exercise
|||Error: could not find or load class classes/java/security/KeyPairGenerator.class.
|||Error: could not find or load class classes/java/security/spec/ECGenParameterSpec.class.
|||Error: could not find or load class classes/java/security/KeyPair.class.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d40d5]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc011535dc0, 0x1e}, {0xc01157ac95, 0xb}, {0xc01151b950, 0x46})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011590990)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1397 +0xb7a5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01157ac78, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.push(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1759
|||jacobin/jvm.runFrame(0xc01153ca20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:239 +0x106c5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011526cd4, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.push(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1759
|||jacobin/jvm.runFrame(0xc011574a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:239 +0x106c5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01155acd0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| java17-enhancements | FAILED | Some of the Java 17 Enhancements
|||IntStream, RandomGeneratorFactory, InstantSource, HexFormat
|||Error: could not find or load class classes/java/util/random/RandomGeneratorFactory.class.
|||panic: runtime error: index out of range [-1]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.pop(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1746
|||jacobin/jvm.runFrame(0xc01167ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe485
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011666cc8, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.push(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1759
|||jacobin/jvm.runFrame(0xc011532a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:239 +0x106c5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01151acbc, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc01165a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1452 +0xded7
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011640cac, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| numbers-chars-strings | FAILED | Member function tests for Character, Double, Integer, and String
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d40d5]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc0115f6c80, 0xd}, {0xc0115f6c90, 0xd}, {0xc0115f6c7a, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01160e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1397 +0xb7a5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f6d24, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-1 | FAILED | Testing the use of import pkgcalc.Calculator
|||panic: runtime error: index out of range [26] with length 26
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011658960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1366 +0xe56d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011640ca0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-2 | FAILED | Testing the use of import pkgcalc.Calculator
|||panic: runtime error: index out of range [26] with length 26
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115dc960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1366 +0xe56d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115c6ca0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| scimark2 | FAILED | SciMark2: Benchmark measuring performance	of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01164a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1576 +0xdafa
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011632e3c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sha3 | FAILED | SHA-3 hashing tests
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011660960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1576 +0xdafa
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011646d3c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.push(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1759
|||jacobin/jvm.runFrame(0xc01165ca50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1312 +0xe605
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011642c98, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc011612960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1365 +0xe575
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115fad1c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| stringer | FAILED | String method tests
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01165cae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1444 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011644c8c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.push(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1759
|||jacobin/jvm.runFrame(0xc011590960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:344 +0x1029f
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01157acfc, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| tls-one-way | FAILED | main: Make cryptographically strong random number generator
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d40d5]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc011599d80, 0x1a}, {0xc0115dac90, 0xb}, {0xc0115e6f90, 0x30})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0115f4a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1397 +0xb7a5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115dacec, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| user-defined-exception | FAILED | Throw a user-defined exception
|||panic: runtime error: index out of range [26] with length 6
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115e4960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1362 +0xe57d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115cecb0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc01160eae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:417 +0x10170
|||jacobin/jvm.runFrame(0xc01160eae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1459 +0xde2e
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f6c8c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.runFrame(0xc011612b10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1362 +0xe57d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115fac34, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
