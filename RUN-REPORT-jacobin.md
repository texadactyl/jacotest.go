Jacotest version 1.0
Run report using JVM jacobin
<br>Date/Time 2023-04-19 04:02:13 CDT
<br>
<br>
| Test Case | Result | Error Information |
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
|||jacobin/jvm.instantiateClass({0xc000018224, 0xc})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007f200)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1434 +0xb78d
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00001823c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0206-nbody | FAILED | Error: could not find or load class main$NBodySystem.
|||Error loading class: main$NBodySystem. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000c6170, 0x10})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc0000a0e40)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1434 +0xb78d
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c6144, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0212-bit-shifting | FAILED | FAILED trying -100 >> 2. Expected -25. Observed 39
|||FAILED trying -100 << 3. Expected -800. Observed 1248
|||Success trying +100 >> 2 == 25
|||Success trying +100 << 3 == 800
|||Error count = 2
|||Going to thrrow an Exception next .....
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/lang/Exception.class.
|||Error loading class: java/lang/Exception. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000d00a8, 0x13})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc0000a0d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1434 +0xb78d
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c41b0, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0217-multidim | FAILED | Invalid bytecode found: 197 (0xC5) (MULTIANEWARRAY) at location 8 in method main() of class main
|||
||| |
| JACOBIN-0219-length | PASSED | n/a |
| JACOBIN-0227-string-array | PASSED | n/a |
| JACOBIN-0231-stats | TIMEOUT | Library lib will be instantiated .....
||| |
| JACOBIN-0233-thread_stack | FAILED | Could not find class: java/lang/Thread
||| |
| JACOBIN-0234-panic | FAILED | panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc00007ee10)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1381 +0xd5f7
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000181d0, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| JACOBIN-0235-system-exit | FAILED | I will System.exit(0)!
|||Could not find class: java/lang/System
||| |
| JACOBIN-0236-minus-signs | TIMEOUT | a: 60
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
| arrays_1 | FAILED | panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0000a4fc0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1381 +0xd5f7
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c61c4, 0x4}, 0x6108e0)
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
|||Error: could not find or load class /home/elkins/BASIS//jacobinclasses/java/lang/Exception.class.
|||Error loading class: java/lang/Exception. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc00001e0f0, 0x13})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007cd50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1434 +0xb78d
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00001823c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| instantiate_class | FAILED | Library lib will be instantiated .....
|||Error: could not find or load class Library.
|||Error loading class: Library. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc0000181f0, 0x7})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007cd50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1434 +0xb78d
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00001822c, 0x4}, 0x6108e0)
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
| numbers-chars-strings | FAILED | Could not find class: ProcCharacter
||| |
| pbcrypto | FAILED | Invalid bytecode found: 186 (0xBA) (INVOKEDYNAMIC) at location 22 in method main() of class main
|||
||| |
| scimark2 | FAILED | panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc00010ed50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1513 +0xcc77
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00013433c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| sha3 | FAILED | panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc0000a0d50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1513 +0xcc77
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c423c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| specrel | FAILED | ===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||Error: could not find or load class Formulae.
|||Error loading class: Formulae. Exiting.
|||panic: runtime error: invalid memory address or nil pointer dereference
|||[signal SIGSEGV: segmentation violation code=0x1 addr=0x60 pc=0x4e910f]
|||
|||goroutine 1 [running]:
|||jacobin/jvm.instantiateClass({0xc000018230, 0x8})
|||	/home/elkins/BASIS/jacobin/src/jvm/instantiate.go:47 +0x22f
|||jacobin/jvm.runFrame(0xc00007cd50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1434 +0xb78d
|||jacobin/jvm.runThread(0x610440)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00001829c, 0x4}, 0x6108e0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
