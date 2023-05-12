Jacotest version 1.07

Run report using JVM jacobin<br>Case deadline = 30 seconds<br>Date/Time 2023-05-12 08:06:30 CDT<br><br>
| Test Case | Result | Console Output |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.
|||Testing polymorphism, abstract classes, abstract methods, and interfaces.
|||
|||Insider class will now be instantiated .....
|||74
|||Insider class was instantiated
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01165ad20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011640cbc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0161-instantiate_class | FAILED | Testing one instantiation of a class residing in a separate source file
|||Library lib will be instantiated .....
|||74
|||Library lib was instantiated
|||Absolute value of -1.0: panic: runtime error: index out of range [3] with length 3
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x3ff0000000000000?, {0x505a80?, 0xc011505028?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc011520960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:302 +0x11dc
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011504cbc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0206-nbody | FAILED | Model the orbits of the Jovian planets, using the Symplectic integration Package
|||URL: https://janus.astro.umd.edu/HNBody/
|||74
|||panic: interface conversion: interface {} is unsafe.Pointer, not float64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01165ea50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1432 +0x9f0c
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644c34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/jvm.runFrame(0xc011642d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011628de0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/jvm.runFrame(0xc01167a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runFrame(0xc01167a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9825
|||jacobin/jvm.runFrame(0xc01167a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9825
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011660ce0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0227-string-array | PASSED | n/a |
| JACOBIN-0231-stats | FAILED | Testing basic statistical functions and a square root algorithm
|||Library lib will be instantiated .....
|||74
|||Library lib was instantiated
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc00b565860, 0x14}, {0xc00b571949, 0x3}, {0xc00b57194c, 0x4})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01157c960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1400 +0x76c5
|||jacobin/jvm.runFrame(0xc01157c960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9825
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011560d1c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0234-0240-0241-array-length | FAILED | Exercise array lengths for type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116e2a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0116c6c9c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/jvm.runFrame(0xc011610960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f6cf0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||74
|||panic: runtime error: index out of range [3] with length 3
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc0115dc8e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc0115da960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c2cfc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| arrays_1 | FAILED | Testing accessibility of array elements of type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011612bd0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f6cb4, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| blockchain | FAILED | Blockchain exercise: create, series of adds, and chain verify
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011638c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runFrame(0xc011638c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9825
|||jacobin/jvm.runFrame(0xc011638c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9825
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01161cc34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| casting | FAILED | Widening and Narrowing Casting
|||Byte value 126
|||74
|||panic: interface conversion: interface {} is int64, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01157aae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1253 +0xa07b
|||jacobin/jvm.runFrame(0xc01157aae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9825
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011560c8c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| catch-exception | FAILED | I will catch a NumberFormatException
|||Error: could not find or load class classes/java/lang/NumberFormatException.class.
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115a2960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011588ca0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| dedupe-hacked | FAILED | FileDedupe v. 2.0 (c) Copyright 2017-20 Andrew Binstock. All rights reserved.
|||
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01159cc60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1445 +0x9eee
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011582c34, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/classloader.FetchMethodAndCP({0xc01157bda0, 0x1e}, {0xc0115bcc95, 0xb}, {0xc0115618b0, 0x46})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0115d6990)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1400 +0x76c5
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115bcc78, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| hashed-map | FAILED | Testing a hashed map
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc011600d30?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc01160ca20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f2cd4, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| hashed-set | FAILED | Testing a hashed set
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc011638d30?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc011644a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01162acd0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/jvm.pop(0xc0113f9060?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1750 +0xbe
|||jacobin/jvm.runFrame(0xc0115e4d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1445 +0x9c7e
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115cacc8, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b340?, {0x506940?, 0xc011598930?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc011596a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01157ccbc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/jvm.runFrame(0xc01161c960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1456 +0x98d1
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011602cac, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/classloader.FetchMethodAndCP({0xc01157ec80, 0xd}, {0xc01157ec90, 0xd}, {0xc01157ec7a, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01159a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1400 +0x76c5
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01157ed24, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-1 | FAILED | Testing the use of import pkgcalc.Calculator
|||74
|||panic: runtime error: index out of range [3] with length 3
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc0114bfab8?, {0x5061c0?, 0x5fc5e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc011536960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:226 +0xb86
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01151eca0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-2 | FAILED | Testing the use of import pkgcalc.Calculator
|||74
|||panic: runtime error: index out of range [3] with length 3
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc01153dd40?, {0x5061c0?, 0x5fc5e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc011598960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:226 +0xb86
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01157cca0, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| recursion | PASSED | n/a |
| scimark2 | FAILED | SciMark2: Benchmark measuring performance	of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115dc960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1580 +0x9545
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c2e3c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sha3 | FAILED | SHA-3 hashing tests
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011652960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1580 +0x9545
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011638d3c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sockets | FAILED | Socket tests with a parent thread (main) and 2 child threads (server, client)
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50b280?, {0x5061c0?, 0x5fbfb0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc0115c8a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1318 +0x71af
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115aec98, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| specrel | FAILED | Special Relativity calculations
|||===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x3fed1eb851eb851f?, {0x505a80?, 0xc01164b150?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc011664960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:302 +0x11dc
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01164ad1c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| stringer | FAILED | String method tests
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011666ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1448 +0x9efd
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01164cc8c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| threading | FAILED | Threading tests with a parent thread (main) and 3 child threads
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc011636c90?, {0x506940?, 0xc011650d80?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1771 +0xcc
|||jacobin/jvm.runFrame(0xc011650960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:349 +0x1a5a
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011636cfc, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/classloader.FetchMethodAndCP({0xc01167fd60, 0x1a}, {0xc0116c0c90, 0xb}, {0xc0116ccf90, 0x30})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0116daa50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1400 +0x76c5
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0116c0cec, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| user-defined-exception | FAILED | Throw a user-defined exception
|||74
|||Invalid bytecode found: 191 (0xBF) (ATHROW) at location 17 in method main() of class main
|||
||| |
| vectors | FAILED | Fun with vectors
|||Error: could not find or load class classes/java/util/Vector.class.
|||panic: interface conversion: interface {} is int64, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011554ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:422 +0xad8b
|||jacobin/jvm.runFrame(0xc011554ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1463 +0x9825
|||jacobin/jvm.runThread(0x60a440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01153cc8c, 0x4}, 0x60a8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| zippy | FAILED | Directory of the zip is as follows:
|||74
|||Error: could not find or load class classes/java/util/zip/ZipInputStream.class.
|||instantiateClass: LoadClassFromNameOnly(java/util/zip/ZipInputStream) failed. Exiting.
||| |
