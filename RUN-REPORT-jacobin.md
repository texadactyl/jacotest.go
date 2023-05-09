Jacotest version 1.06

Run report using JVM jacobin<br>Case deadline = 30 seconds<br>Date/Time 2023-05-09 09:17:11 CDT<br><br>
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
|||jacobin/jvm.runFrame(0xc011674d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01165acbc, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc0115f8960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1372 +0x9edd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115decbc, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc01160ea50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1435 +0x9e26
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f6c34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
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
|||jacobin/jvm.runFrame(0xc0115fad20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115e2de0, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc0115d6960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runFrame(0xc0115d6960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9845
|||jacobin/jvm.runFrame(0xc0115d6960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9845
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115bece0, 0x4}, 0x60a8e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc00b5bb860, 0x14}, {0xc00b5c5949, 0x3}, {0xc00b5c594c, 0x4})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0115d6960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1403 +0x7785
|||jacobin/jvm.runFrame(0xc0115d6960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9845
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115bed1c, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011618a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fec9c, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011666960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01164ecf0, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc0115928e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1775 +0xe6
|||jacobin/jvm.runFrame(0xc011590960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011578cfc, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011568bd0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011552cb4, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc0115e8c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runFrame(0xc0115e8c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9845
|||jacobin/jvm.runFrame(0xc0115e8c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9845
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115d2c34, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc0115e0960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c6ca0, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc01165ec60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011646c34, 0x4}, 0x60a8e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc01159ddc0, 0x1e}, {0xc0115e2c95, 0xb}, {0xc011583900, 0x46})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0115f8990)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1403 +0x7785
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115e2c78, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc0115ecd30?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1775 +0xe6
|||jacobin/jvm.runFrame(0xc0115f8a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115e0cd4, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc0115e0930?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1775 +0xe6
|||jacobin/jvm.runFrame(0xc0115dea20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c8cd0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
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
|||jacobin/jvm.pop(0xc011409060?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1754 +0xb4
|||jacobin/jvm.runFrame(0xc0115f6d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9b06
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115dccc8, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc01160ad30?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1775 +0xe6
|||jacobin/jvm.runFrame(0xc011616a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fecbc, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc01165c960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1456 +0x98f1
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644cac, 0x4}, 0x60a8e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc01162ac80, 0xd}, {0xc01162ac90, 0xd}, {0xc01162ac7a, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011642960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1403 +0x7785
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01162ad24, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011616960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1372 +0x9edd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115feca0, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc01165c960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1372 +0x9edd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644ca0, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011652960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1580 +0x9565
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01163ae3c, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011616960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1580 +0x9565
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fcd3c, 0x4}, 0x60a8e0)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1775 +0xe6
|||jacobin/jvm.runFrame(0xc01165ca50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1318 +0x71af
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644c98, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011578960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1371 +0x9ee5
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011562d1c, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011654ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9e45
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01163cc8c, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.push(0xc01160ac90?, {0x506940?, 0xc011622d80?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1775 +0xe6
|||jacobin/jvm.runFrame(0xc011622960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:349 +0x1a5a
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01160acfc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
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
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc011601d80, 0x1a}, {0xc011644c90, 0xb}, {0xc01164efc0, 0x30})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01165ca50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1403 +0x7785
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644cec, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc01157e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1368 +0x9eed
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01156ccb0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| vectors | FAILED | Fun with vectors
|||panic: interface conversion: interface {} is int64, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01165cae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:422 +0xaceb
|||jacobin/jvm.runFrame(0xc01165cae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9845
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644c8c, 0x4}, 0x60a8e0)
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
|||jacobin/jvm.runFrame(0xc011610b10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1368 +0x9eed
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f8c34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x6e7
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
