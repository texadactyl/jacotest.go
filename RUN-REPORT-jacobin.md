Jacotest version 1.05

Run report using JVM jacobin<br>Case deadline = 120 seconds<br>Date/Time 2023-05-05 07:21:31 CDT<br><br>
| Test Case | Result | Console Output |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.
|||Testing polymorphism, abstract classes, abstract methods, and interfaces.
|||
|||Insider class will now be instantiated .....
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 30 in method main() of class main
|||
||| |
| JACOBIN-0161-instantiate_class | FAILED | Testing one instantiation of a class residing in a separate source file
|||Library lib will be instantiated .....
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 22 in method main() of class main
|||
||| |
| JACOBIN-0206-nbody | FAILED | Model the orbits of the Jovian planets, using the Symplectic integration Package
|||URL: https://janus.astro.umd.edu/HNBody/
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 33 in method main() of class main
|||
||| |
| JACOBIN-0211-pbcrypto | FAILED | Exercise Password-based Encryption/Decryption
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011618db0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe137
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011600de0, 0x4}, 0x6128e0)
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
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 221 in method main() of class main
|||
||| |
| JACOBIN-0217-multidim-2d | PASSED | n/a |
| JACOBIN-0217-multidim-3d | FAILED | Testing 2D and 3D arrays of type int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116929f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe137
|||jacobin/jvm.runFrame(0xc0116929f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1403 +0xdaee
|||jacobin/jvm.runFrame(0xc0116929f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1403 +0xdaee
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc01167cce0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0227-string-array | PASSED | n/a |
| JACOBIN-0231-stats | FAILED | Testing basic statistical functions and a square root algorithm
|||Library lib will be instantiated .....
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 39 in method main() of class main
|||
||| |
| JACOBIN-0234-0240-0241-array-length | FAILED | Exercise array lengths for type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011696ab0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe137
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011680c9c, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc0115e29f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe137
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115cacf0, 0x4}, 0x6128e0)
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
|||c = unary bitwise complement operator on a
|||Success trying ~a == -61
|||c = unary bitwise complement operator on 60
|||FAILED trying ~60. Expected -61. Observed 195
|||Success trying a>>>2 == 15
|||A=true and B=false
|||Success trying A && B == false
|||Success trying A || B == true
|||Success trying c = (a == 42) ? 1001: 1002 ==>> 1002
|||Error count = 2
|||Going to thrrow an Exception next .....
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 652 in method main() of class main
|||
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
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 276 in method main() of class main
|||
||| |
| JACOBIN-0237-nil-printlns | PASSED | n/a |
| array-list-iterator | FAILED | Begin ArrayList/iterator tests
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 14 in method main() of class main
|||
||| |
| arrays_1 | FAILED | Testing accessibility of array elements of type byte, char, int, float, double, and String
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01163ec60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe137
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011624cb4, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| blockchain | FAILED | Blockchain exercise: create, series of adds, and chain verify
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011696cc0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe137
|||jacobin/jvm.runFrame(0xc011696cc0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1403 +0xdaee
|||jacobin/jvm.runFrame(0xc011696cc0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1403 +0xdaee
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc01167ec34, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
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
|||jacobin/jvm.runFrame(0xc0115d29f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe137
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115baca0, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| elliptic | FAILED | Elliptic cryptography exercise
|||Could not find class: java/security/KeyPairGenerator
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/security/KeyPairGenerator.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/security/spec/ECGenParameterSpec.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/security/KeyPair.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/security/Signature.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/security/PrivateKey.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/security/PublicKey.class.
||| |
| hashed-map | FAILED | Testing a hashed map
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 14 in method main() of class main
|||
||| |
| hashed-set | FAILED | Testing a hashed set
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 14 in method main() of class main
|||
||| |
| java17-enhancements | FAILED | Some of the Java 17 Enhancements
|||IntStream, RandomGeneratorFactory, InstantSource, HexFormat
|||panic: runtime error: index out of range [-1]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.pop(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1690
|||jacobin/jvm.runFrame(0xc0115b2de0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe145
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc01159acc8, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| lambdas_maps | FAILED | Lambdas and Maps
|||Invalid bytecode found: 186 (0xBA) (INVOKEDYNAMIC) at location 10 in method main() of class main
|||
||| |
| linked-list | FAILED | Testing linked lists
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 14 in method main() of class main
|||
||| |
| negtest-comp-error | COMP-ERROR | compilation error(s)
 | | | See logs/FAILED-*-javac.log files |
| negtest-runner-timeout | FAILED | I will timeout!
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/lang/InterruptedException.class.
|||panic: runtime error: index out of range [0] with length 0
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116109f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1396 +0xdb97
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115f6cac, 0x4}, 0x6128e0)
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
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 12 in method main() of class main
|||
||| |
| packaging-2 | FAILED | Testing the use of import pkgcalc.Calculator
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 12 in method main() of class main
|||
||| |
| scimark2 | FAILED | SciMark2: Benchmark measuring performance	of computational kernels for FFTs, Monte Carlo simulation, sparse matrix computations, Jacobi SOR, and dense LU matrix factorizations.
|||panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01169c9f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1520 +0xd7ba
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011686e3c, 0x4}, 0x6128e0)
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
|||jacobin/jvm.runFrame(0xc01158a9f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1520 +0xd7ba
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011574d3c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sockets | FAILED | Socket tests with a parent thread (main) and 2 child threads (server, client)
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 14 in method main() of class main
|||
||| |
| specrel | FAILED | Special Relativity calculations
|||===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 70 in method main() of class main
|||
||| |
| stringer | FAILED | String method tests
|||panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0115fcb70)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1388 +0xe137
|||jacobin/jvm.runThread(0x612440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115e4c8c, 0x4}, 0x6128e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| threading | FAILED | Threading tests with a parent thread (main) and 3 child threads
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 14 in method main() of class main
|||
||| |
| tls-one-way | FAILED | main: Make cryptographically strong random number generator
|||Could not find class: java/security/SecureRandom
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/security/SecureRandom.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/javax/net/ssl/SSLContext.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/javax/net/ssl/TrustManagerFactory.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/security/KeyStore.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/javax/net/ssl/SSLSocketFactory.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/javax/net/ssl/SSLSocket.class.
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/lang/InterruptedException.class.
||| |
| user-defined-exception | FAILED | Throw a user-defined exception
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 14 in method main() of class main
|||
||| |
| vectors | FAILED | Fun with vectors
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 28 in method valueOf() of class java/lang/Integer
|||
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/util/Vector.class.
||| |
| zippy | FAILED | Files in the zip are as follows: 
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 23 in method main() of class main
|||
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/util/zip/ZipInputStream.class.
||| |
