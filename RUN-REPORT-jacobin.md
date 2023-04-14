Jacotest version 1.0
Run report using JVM jacobin
<br>Date/Time 2023-04-14 12:23:23 CDT
<br>
<br>
| Test Case | Result | Error Information |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Testing subclasses that are embedded in the main class, parallel to main, and resident in a separate file.
|||Testing polymorphism, abstract classes, abstract methods, and interfaces.
|||
|||Insider class will now be instantiated .....
|||Error: could not find or load class main$Insider.
|||instantiateClass: LoadClassFromNameOnly(main$Insider) failed. Exiting.
|||Error instantiating class: main$Insider
||| |
| JACOBIN-0206-nbody | FAILED | Error: could not find or load class main$NBodySystem.
|||instantiateClass: LoadClassFromNameOnly(main$NBodySystem) failed. Exiting.
|||Error instantiating class: main$NBodySystem
||| |
| JACOBIN-0212-bit-shifting | FAILED | FAILED trying -100 >> 2. Expected -25. Observed 39
|||FAILED trying -100 << 3. Expected -800. Observed 1248
|||Success trying +100 >> 2 == 25
|||Success trying +100 << 3 == 800
|||Error count = 2
|||Going to thrrow an Exception next .....
|||error creating field in java/lang/Exception
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 199 in method main() of class main
|||
||| |
| JACOBIN-0217-multidim | FAILED | Invalid bytecode found: 197 (0xC5) (MULTIANEWARRAY) at location 8 in method main() of class main
|||
||| |
| JACOBIN-0219-length | PASSED | n/a |
| JACOBIN-0227-string-array | PASSED | n/a |
| JACOBIN-0231-stats | FAILED | Library lib will be instantiated .....
|||instantiateClass: Status is still 'I' waiting for class: Library. Overdue!
|||Error instantiating class: Library
||| |
| JACOBIN-0233-thread_stack | FAILED | panic: runtime error: index out of range [-1]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.pop(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1580
|||jacobin/jvm.runFrame(0xc01164c9f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:480 +0xede5
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011632d08, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0234-panic | FAILED | panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01164eab0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1381 +0xd637
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011636c48, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0235-system-exit | FAILED | I will System.exit(0)!
|||Invalid bytecode found: 176 (0xB0) (ARETURN) at location 3 in method getRuntime() of class java/lang/Runtime
|||
||| |
| JACOBIN-0236-minus-signs | FAILED | a: 60
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
|||error creating field in java/lang/Exception
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 263 in method main() of class main
|||
||| |
| JACOBIN-0237-nil-printlns | FAILED | panic: runtime error: index out of range [1] with length 1
|||
|||goroutine 1 [running]:
|||jacobin/jvm.push(...)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1593
|||jacobin/jvm.runFrame(0xc0116149f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1242 +0xd790
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115fcc70, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| arrays_1 | FAILED | panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc011606c60)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1381 +0xd637
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115eecb4, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| bitwise | FAILED | a=10 and b=25
|||Success trying a + b == 35
|||FAILED trying a - b. Expected -15. Observed -15
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
|||error creating field in java/lang/Exception
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 641 in method main() of class main
|||
||| |
| instantiate_class | FAILED | Library lib will be instantiated .....
|||Error: could not find or load class Library.
|||instantiateClass: LoadClassFromNameOnly(Library) failed. Exiting.
|||Error instantiating class: Library
||| |
| negtest-comp-error | COMP-ERROR | compilation error(s)
 | | | See logs/FAILED-*-javac.log files |
| negtest-runner-throw-exception | FAILED | I will throw an Exception!
|||error creating field in java/lang/Exception
|||Invalid bytecode found: 183 (0xB7) (INVOKESPECIAL) at location 14 in method main() of class main
|||
||| |
| negtest-runner-timeout | FAILED | I will timeout!
|||panic: runtime error: index out of range [0] with length 0
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116209f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1389 +0xd097
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011608cac, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| pbcrypto | FAILED | Invalid bytecode found: 186 (0xBA) (INVOKEDYNAMIC) at location 22 in method main() of class main
|||
||| |
| scimark2 | FAILED | panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0116169f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1517 +0xccb7
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0115fee2c, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sha3 | FAILED | panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc01162e9f0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1517 +0xccb7
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc011616d2c, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| specrel | FAILED | ===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||Error: could not find or load class Formulae.
|||instantiateClass: LoadClassFromNameOnly(Formulae) failed. Exiting.
|||Error instantiating class: Formulae
||| |
