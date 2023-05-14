Jacotest version 1.07

Run report using JVM jacobin<br>Case deadline = 30 seconds<br>Date/Time 2023-05-13 21:23:08 CDT<br><br>
| Test Case | Result | Console Output |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.
|||Testing polymorphism, abstract classes, abstract methods, and interfaces.
|||
|||Insider class will now be instantiated .....
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc011643060, 0x8}, {0xc011643068, 0x6}, {0xc011643070, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01165ed20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1367 +0x74df
|||jacobin/jvm.runFrame(0xc01165ed20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011642cbc, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0161-instantiate_class | FAILED | Testing one instantiation of a class residing in a separate source file
|||Library lib will be instantiated .....
|||74
|||panic: runtime error: index out of range [2] with length 2
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50c280?, {0x507940?, 0xc011561860?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011592960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runFrame(0xc011592960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011578cbc, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0206-nbody | FAILED | Model the orbits of the Jovian planets, using the Symplectic integration Package
|||URL: https://janus.astro.umd.edu/HNBody/
|||74
|||panic: runtime error: index out of range [6] with length 6
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc000100960?, {0x506a80?, 0x556120?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011652a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:200 +0xa0c
|||jacobin/jvm.runFrame(0xc011652a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011636c34, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0211-pbcrypto | FAILED | Exercise Password-based Encryption/Decryption
|||Error: could not find or load class classes/javax/crypto/SecretKeyFactory.class.
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.createAndInitNewFrame({0xc0115efbc0, 0x11}, {0xc01164cdc9, 0x7}, 0xc0116878b0, {0xc0115efbd8, 0x16}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc011668d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01164cde0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc00b578f48, 0x11}, {0xc00b5a4940, 0x8}, 0xc011622050, {0xc00b5a4948, 0x7}, 0x0?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc011610960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runFrame(0xc011610960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runFrame(0xc011610960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f6ce0, 0x4}, 0x60b8e0)
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
|||74
|||Library lib was instantiated
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc00b643860, 0x14}, {0xc00b64d949, 0x3}, {0xc00b64d94c, 0x4})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01165e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1439 +0x7945
|||jacobin/jvm.runFrame(0xc01165e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644d1c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc0115f2c9c, 0x4}, {0xc0115f2ca0, 0xd}, 0xc0116218b0, {0xc011593ab8, 0x17}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc01160ca20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f2c9c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc01159db00, 0x11}, {0xc0115fccc9, 0x7}, 0xc0116298b0, {0xc01159db18, 0x16}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc011616960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fccf0, 0x4}, 0x60b8e0)
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
|||74
|||panic: runtime error: index out of range [2] with length 2
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc011618960?, {0x5071c0?, 0x556120?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011618960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:175 +0x785
|||jacobin/jvm.runFrame(0xc011618960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runFrame(0xc011618960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fecfc, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011584cb4, 0x4}, {0xc011584cd0, 0xa}, 0xc0115b38b0, {0xc011525ae8, 0x18}, 0x10?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc01159ebd0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011584cb4, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc00b59a7d0, 0xe}, {0xc00b59a950, 0x8}, 0xc0115c2050, {0xc00b59a958, 0x7}, 0x0?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc0115a6c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runFrame(0xc0115a6c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runFrame(0xc0115a6c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01158ac34, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| casting | FAILED | Widening and Narrowing Casting
|||Byte value 126
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.createAndInitNewFrame({0xc00b92ad40, 0x10}, {0xc00b92ace8, 0x6}, 0xc011666d08, {0xc00b92af48, 0x6}, 0x0?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc011652ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1379 +0x75f8
|||jacobin/jvm.runFrame(0xc011652ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011638c8c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011583ab8, 0x11}, {0xc0115e0c90, 0x8}, 0xc01160f8b0, {0xc011583ad0, 0x15}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc0115fc960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115e0ca0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011582c60, 0x10}, {0xc011582c38, 0x6}, 0xc0115b58b0, {0xc01158efc0, 0x25}, 0xc011583010?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1904 +0xa34
|||jacobin/jvm.runFrame(0xc01159cc60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011582c34, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x0 pc=0x4d4095]
|||
|||goroutine 1 [running]:
|||jacobin/classloader.FetchMethodAndCP({0xc01157dda0, 0x1e}, {0xc0115bec95, 0xb}, {0xc0115618b0, 0x46})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0115d8990)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1439 +0x7945
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115bec78, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| hashed-map | FAILED | Testing a hashed map
|||74
|||74
|||panic: runtime error: index out of range [2] with length 2
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x3fe8000000000000?, {0x506a80?, 0xc0115fee78?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011618a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:241 +0xcf1
|||jacobin/jvm.runFrame(0xc011618a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fecd4, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| hashed-set | FAILED | Testing a hashed set
|||74
|||74
|||74
|||panic: runtime error: index out of range [3] with length 3
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc00e56c948?, {0x507940?, 0xc011522bd0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011522a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:681 +0x3991
|||jacobin/jvm.runFrame(0xc011522a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01150acd0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.pop(0xc0113ef060?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1797 +0xbe
|||jacobin/jvm.createAndInitNewFrame({0xc0115c2cc8, 0x4}, {0xc0115c2cf0, 0xd}, 0xc0115f98b0, {0xc0115b3fc0, 0x32}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1904 +0x7d2
|||jacobin/jvm.runFrame(0xc0115dad50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c2cc8, 0x4}, 0x60b8e0)
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
|||74
|||panic: runtime error: index out of range [2] with length 2
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc0115eaa20?, {0x5071c0?, 0x556120?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc0115eaa20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:175 +0x785
|||jacobin/jvm.runFrame(0xc0115eaa20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runFrame(0xc0115eaa20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runFrame(0xc0115eaa20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115d2cbc, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/jvm.createAndInitNewFrame({0xc0115bcc90, 0x10}, {0xc0115bcca0, 0x5}, 0xc0115e78b0, {0xc0115bcca8, 0x4}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1915 +0x45d
|||jacobin/jvm.runFrame(0xc0115d4960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115bccac, 0x4}, 0x60b8e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc011644c80, 0xd}, {0xc011644c90, 0xd}, {0xc011644c7a, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01165e960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1439 +0x7945
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644d24, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-1 | FAILED | Testing the use of import pkgcalc.Calculator
|||74
|||74
|||panic: runtime error: index out of range [3] with length 3
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc0115e8960?, {0x5071c0?, 0x5fd5e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc0115e8960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:226 +0xb86
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115d0ca0, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| packaging-2 | FAILED | Testing the use of import pkgcalc.Calculator
|||74
|||74
|||panic: runtime error: index out of range [3] with length 3
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc011662960?, {0x5071c0?, 0x5fd5e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011662960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:226 +0xb86
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011648ca0, 0x4}, 0x60b8e0)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1627 +0x99e5
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c4e3c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.runFrame(0xc0116c2960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1627 +0x99e5
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0116a8d3c, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sockets | FAILED | Socket tests with a parent thread (main) and 2 child threads (server, client)
|||74
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50c280?, {0x5071c0?, 0x5fcfb0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc0115e6a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1318 +0x71af
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115ccc98, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| specrel | FAILED | Special Relativity calculations
|||===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||74
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x3fed1eb851eb851f?, {0x506a80?, 0xc0115db160?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc0115f4960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:302 +0x11dc
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115dad1c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011580c8c, 0x4}, {0xc011521aa0, 0x12}, 0xc0115b18b0, {0xc01158cfc0, 0x27}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc011598ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011580c8c, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| threading | FAILED | Threading tests with a parent thread (main) and 3 child threads
|||74
|||74
|||panic: runtime error: index out of range [4] with length 4
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0xc0115d2c90?, {0x507940?, 0xc0115ead80?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc0115ea960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:349 +0x1a5a
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115d2cfc, 0x4}, 0x60b8e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc0115f3d60, 0x1a}, {0xc011632c90, 0xb}, {0xc011640fc0, 0x30})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01164ea50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1439 +0x7945
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011632cec, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| user-defined-exception | FAILED | Throw a user-defined exception
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.createAndInitNewFrame({0xc0115bcc80, 0xb}, {0xc0115bcc08, 0x6}, 0xc0115e9938, {0xc01155daa0, 0x15}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc0115d6960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1379 +0x75f8
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115bccb0, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/jvm.runFrame(0xc0115d6ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:422 +0xa8eb
|||jacobin/jvm.runFrame(0xc0115d6ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115bcc8c, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| zippy | FAILED | Directory of the zip is as follows:
|||Error: could not find or load class classes/java/util/zip/ZipInputStream.class.
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.createAndInitNewFrame({0xc011531ad0, 0x17}, {0xc011590c08, 0x6}, 0xc0115c3938, {0xc011531ab8, 0x15}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc0115aab10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1379 +0x75f8
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011590c34, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
