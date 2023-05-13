Jacotest version 1.07

Run report using JVM jacobin<br>Case deadline = 30 seconds<br>Date/Time 2023-05-13 07:42:43 CDT<br><br>
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
|||jacobin/classloader.FetchMethodAndCP({0xc01164f060, 0x8}, {0xc01164f068, 0x6}, {0xc01164f070, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011668d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1367 +0x74df
|||jacobin/jvm.runFrame(0xc011668d20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01164ecbc, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0161-instantiate_class | FAILED | Testing one instantiation of a class residing in a separate source file
|||Library lib will be instantiated .....
|||74  // <--------------- coming from line 1410 in run.go
|||panic: runtime error: index out of range [2] with length 2
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(0x50c280?, {0x507940?, 0xc011563860?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011594960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:245 +0xc9b
|||jacobin/jvm.runFrame(0xc011594960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01157ccbc, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.runFrame(0xc01165ea50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:200 +0xa0c
|||jacobin/jvm.runFrame(0xc01165ea50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011642c34, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc0115e5bc0, 0x11}, {0xc011644dc9, 0x7}, 0xc0116778b0, {0xc0115e5bd8, 0x16}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc01165ed20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011644de0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc00b5a0f48, 0x11}, {0xc00b5cc940, 0x8}, 0xc011656050, {0xc00b5cc948, 0x7}, 0x0?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc011642960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runFrame(0xc011642960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runFrame(0xc011642960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011626ce0, 0x4}, 0x60b8e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc00b5ff860, 0x14}, {0xc00b60b949, 0x3}, {0xc00b60b94c, 0x4})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011612960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1439 +0x7945
|||jacobin/jvm.runFrame(0xc011612960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f6d1c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011646c9c, 0x4}, {0xc011646ca0, 0xd}, 0xc0116778b0, {0xc0115e9ab8, 0x17}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc011662a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011646c9c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011593b00, 0x11}, {0xc0115f2cc9, 0x7}, 0xc01161f8b0, {0xc011593b18, 0x16}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc01160c960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f2cf0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.push(0xc011664960?, {0x5071c0?, 0x556120?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011664960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:175 +0x785
|||jacobin/jvm.runFrame(0xc011664960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runFrame(0xc011664960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011648cfc, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc0115c4cb4, 0x4}, {0xc0115c4cd0, 0xa}, 0xc0115f38b0, {0xc011565ae8, 0x18}, 0x10?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc0115dcbd0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c4cb4, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc00b5c07d0, 0xe}, {0xc00b5c0950, 0x8}, 0xc0115ee050, {0xc00b5c0958, 0x7}, 0x0?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc0115d2c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runFrame(0xc0115d2c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runFrame(0xc0115d2c30)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115b8c34, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc00b91cd40, 0x10}, {0xc00b91cce8, 0x6}, 0xc011654d08, {0xc00b91cf48, 0x6}, 0x0?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc011640ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1379 +0x75f8
|||jacobin/jvm.runFrame(0xc011640ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011626c8c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc0115e3ab8, 0x11}, {0xc011640c90, 0x8}, 0xc01166f8b0, {0xc0115e3ad0, 0x15}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc01165c960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011640ca0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc0115cec60, 0x10}, {0xc0115cec38, 0x6}, 0xc0116078b0, {0xc0115daf90, 0x25}, 0xc0115cf010?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1904 +0xa34
|||jacobin/jvm.runFrame(0xc0115e6c60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115cec34, 0x4}, 0x60b8e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc0115ffda0, 0x1e}, {0xc01163ec95, 0xb}, {0xc0115e38b0, 0x46})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc01165a990)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1439 +0x7945
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01163ec78, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.push(0x3fe8000000000000?, {0x506a80?, 0xc011642e78?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc01165ea20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:241 +0xcf1
|||jacobin/jvm.runFrame(0xc01165ea20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011642cd4, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.push(0xc00e6ac948?, {0x507940?, 0xc011664bd0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011664a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:681 +0x3991
|||jacobin/jvm.runFrame(0xc011664a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01164acd0, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/jvm.pop(0xc011479060?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1797 +0xbe
|||jacobin/jvm.createAndInitNewFrame({0xc01164acc8, 0x4}, {0xc01164acf0, 0xd}, 0xc0116878b0, {0xc01163dfc0, 0x32}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1904 +0x7d2
|||jacobin/jvm.runFrame(0xc011666d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01164acc8, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.push(0xc011680a20?, {0x5071c0?, 0x556120?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011680a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:175 +0x785
|||jacobin/jvm.runFrame(0xc011680a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runFrame(0xc011680a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runFrame(0xc011680a20)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0x7654
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011666cbc, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011624c90, 0x10}, {0xc011624ca0, 0x5}, 0xc0116518b0, {0xc011624ca8, 0x4}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1915 +0x45d
|||jacobin/jvm.runFrame(0xc011640960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011624cac, 0x4}, 0x60b8e0)
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
|||jacobin/classloader.FetchMethodAndCP({0xc0115d4c80, 0xd}, {0xc0115d4c90, 0xd}, {0xc0115d4c7a, 0x3})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc0115f0960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1439 +0x7945
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115d4d24, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.push(0xc011616960?, {0x5071c0?, 0x5fd5e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011616960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:226 +0xb86
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115fcca0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.push(0xc0115de960?, {0x5071c0?, 0x5fd5e0?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc0115de960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:226 +0xb86
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115c2ca0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.runFrame(0xc0115f8960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1627 +0x99e5
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115dee3c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.runFrame(0xc0115a2960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1627 +0x99e5
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011586d3c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.runFrame(0xc01160ca50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1318 +0x71af
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f0c98, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.push(0x3fed1eb851eb851f?, {0x506a80?, 0xc0115f9160?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc011612960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:302 +0x11dc
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115f8d1c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011564c8c, 0x4}, {0xc0114ffaa0, 0x12}, 0xc0115938b0, {0xc011570f90, 0x27}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc01157cae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1451 +0x7a58
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011564c8c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.push(0xc0115bec90?, {0x507940?, 0xc0115d6d80?})
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1818 +0xcc
|||jacobin/jvm.runFrame(0xc0115d6960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:349 +0x1a5a
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115becfc, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
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
|||jacobin/classloader.FetchMethodAndCP({0xc01150dd60, 0x1a}, {0xc011550c90, 0xb}, {0xc01155cf90, 0x30})
|||	/home/elkins/BASIS/jacobin/src/classloader/classes.go:231 +0x115
|||jacobin/jvm.runFrame(0xc011568a50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1439 +0x7945
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc011550cec, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc01163ec80, 0xb}, {0xc01163ec08, 0x6}, 0xc01166d938, {0xc0115e1aa0, 0x15}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc01165a960)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1379 +0x75f8
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc01163ecb0, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.runFrame(0xc0115d0ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:422 +0xa8eb
|||jacobin/jvm.runFrame(0xc0115d0ae0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1510 +0x7ab4
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115b6c8c, 0x4}, 0x60b8e0)
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
|||jacobin/jvm.createAndInitNewFrame({0xc011581ad0, 0x17}, {0xc0115e0c08, 0x6}, 0xc011613938, {0xc011581ab8, 0x15}, 0x13?)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1907 +0xa45
|||jacobin/jvm.runFrame(0xc0115fab10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1379 +0x75f8
|||jacobin/jvm.runThread(0x60b440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:91 +0x31
|||jacobin/jvm.StartExec({0xc0115e0c34, 0x4}, 0x60b8e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:81 +0x71c
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x60e
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
