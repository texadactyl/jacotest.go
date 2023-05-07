Jacotest version 1.06

Run report using JVM jacobin<br>Case deadline = 30 seconds<br>Date/Time 2023-05-07 16:10:10 CDT<br><br>
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
|||jacobin/jvm.runFrame(0xc011650d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011638cbc, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc0115dc960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1365 +0xe56d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115c4cbc, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011658a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1430 +0xe4e5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01163ec34, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc0115d8d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115c2de0, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc01160e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runFrame(0xc01160e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1458 +0xde2e
|||jacobin/jvm.runFrame(0xc01160e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1458 +0xde2e
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f6ce0, 0x4}, 0x6128e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc00b5d9860, 0x14}, {0xc00b5e3949, 0x3}, {0xc00b5e394c, 0x4})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0115ee960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1396 +0xb7a5
|||jacobin/jvm.runFrame(0xc0115ee960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1458 +0xde2e
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115d4d1c, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011562a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01154cc9c, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc01158e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011578cf0, 0x4}, 0x6128e0)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1758
|||jacobin/jvm.runFrame(0xc0115dc960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:238 +0x106c5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115c6cfc, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011656bd0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01163ecb4, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc01165cc30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runFrame(0xc01165cc30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1458 +0xde2e
|||jacobin/jvm.runFrame(0xc01165cc30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1458 +0xde2e
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011644c34, 0x4}, 0x6128e0)
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
|||Error: could not find or load class classes/java/lang/NumberFormatException.class.
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01160c960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f4ca0, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011652c60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011638c34, 0x4}, 0x6128e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc0115b5dc0, 0x1e}, {0xc0115f8c95, 0xb}, {0xc011599950, 0x46})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011610990)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1396 +0xb7a5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f8c78, 0x4}, 0x6128e0)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1758
|||jacobin/jvm.runFrame(0xc0115a2a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:238 +0x106c5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011588cd4, 0x4}, 0x6128e0)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1758
|||jacobin/jvm.runFrame(0xc01167aa20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:238 +0x106c5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011660cd0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
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
|||jacobin/jvm.pop(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1745
|||jacobin/jvm.runFrame(0xc01158cd50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe485
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011576cc8, 0x4}, 0x6128e0)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1758
|||jacobin/jvm.runFrame(0xc01161ea20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:238 +0x106c5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011604cbc, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| negtest-comp-error | COMP-ERROR | compilation error(s)
 | | | See logs/FAILED-*-javac.log files |
| negtest-runner-timeout | FAILED | I will timeout!
|||Error: could not find or load class classes/java/lang/InterruptedException.class.
|||panic: runtime error: index out of range [0] with length 0
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011610960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0xded7
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f8cac, 0x4}, 0x6128e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc0115f8c80, 0xd}, {0xc0115f8c90, 0xd}, {0xc0115f8c7a, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011610960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1396 +0xb7a5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f8d24, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011634960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1365 +0xe56d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01161cca0, 0x4}, 0x6128e0)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1365 +0xe56d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115c4ca0, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011626960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1575 +0xdafa
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01160ee3c, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc01165e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1575 +0xdafa
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1758
|||jacobin/jvm.runFrame(0xc0115cca50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1311 +0xe605
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115b4c98, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011658960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1364 +0xe575
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011640d1c, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011654ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1443 +0xe477
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01163ac8c, 0x4}, 0x6128e0)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1758
|||jacobin/jvm.runFrame(0xc011614960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:343 +0x1029f
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115fccfc, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| tls-one-way | FAILED | main: Make cryptographically strong random number generator
|||Error: could not find or load class classes/java/security/SecureRandom.class.
|||Error: could not find or load class classes/javax/net/ssl/SSLContext.class.
|||Error: could not find or load class classes/javax/net/ssl/TrustManagerFactory.class.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d40d5]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc01159fd80, 0x1a}, {0xc0115e2c90, 0xb}, {0xc0115ecf90, 0x30})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0115faa50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1396 +0xb7a5
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115e2cec, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc011654960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1361 +0xe57d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc01163ccb0, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc01165aae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:416 +0x10170
|||jacobin/jvm.runFrame(0xc01165aae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1458 +0xde2e
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc011640c8c, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc01160eb10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1361 +0xe57d
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:86 +0x31
|||jacobin/jvm.StartExec({0xc0115f6c34, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:76 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
