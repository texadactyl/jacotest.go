Jacotest version 1.0
Run report using JVM jacobin
<br>Date/Time 2023-04-12 12:59:49 CDT
<br>
<br>
| Test Case | Result | Error Information |
| :--- | :---: | :--- |
| JACOBIN-0161-0229-classes | FAILED | Insider class will be instantiated .....
|||Error: could not find or load class main$Insider.
|||instantiateClass: LoadClassFromNameOnly failed with class: main$Insider.
|||Error instantiating class: main$Insider
||| |
| JACOBIN-0206-nbody | FAILED | Error: could not find or load class main$NBodySystem.
|||instantiateClass: LoadClassFromNameOnly failed with class: main$NBodySystem.
|||Error instantiating class: main$NBodySystem
||| |
| JACOBIN-0212-bit-shifting | FAILED | -100 >> 2: 39
|||*** Observed 39  but expected -25
|||-100 << 3: 1248
|||*** Observed 1248  but expected -800
|||+100 >> 2: 25
|||+100 << 3: 800
|||Could not find class: java/lang/System
||| |
| JACOBIN-0219-length | PASSED | n/a |
| JACOBIN-0227-string-array | PASSED | n/a |
| JACOBIN-0231-stats | FAILED | Library lib will be instantiated .....
|||instantiateClass: Timeout while waiting on class: Library
|||Error instantiating class: Library
||| |
| JACOBIN-0232-multidim | FAILED | Invalid bytecode found: 197 (0xC5) (MULTIANEWARRAY) at location 8 in method main() of class main
|||
||| |
| arrays_1 | FAILED | panic: interface conversion: interface {} is unsafe.Pointer, not int64
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc00007afc0)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1381 +0xd637
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc000018290, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| instantiate_class | FAILED | Library lib will be instantiated .....
|||Error: could not find or load class Library.
|||instantiateClass: LoadClassFromNameOnly failed with class: Library.
|||Error instantiating class: Library
||| |
| negtest-comp-error | COMP-ERROR | compilation error(s) |
| negtest-runner-failure | FAILED | I will System.exit(86)!
|||Could not find class: java/lang/System
||| |
| negtest-runner-timeout | FAILED | I will timeout!
|||Could not find class: java/lang/Thread
||| |
| pbcrypto | FAILED | Invalid bytecode found: 186 (0xBA) (INVOKEDYNAMIC) at location 22 in method main() of class main
|||
||| |
| scimark2 | FAILED | panic: interface conversion: interface {} is int, not unsafe.Pointer
|||
|||goroutine 1 [running]:
|||jacobin/jvm.runFrame(0xc00007ad50)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1517 +0xccb7
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc00001840c, 0x4}, 0x610900)
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
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:1517 +0xccb7
|||jacobin/jvm.runThread(0x610460)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:85 +0x31
|||jacobin/jvm.StartExec({0xc0000c423c, 0x4}, 0x610900)
|||	/home/elkins/BASIS/jacobin/src/jvm/run.go:75 +0x616
|||jacobin/jvm.JVMrun()
|||	/home/elkins/BASIS/jacobin/src/jvm/jvmStart.go:84 +0x62b
|||main.main()
|||	/home/elkins/BASIS/jacobin/src/main.go:12 +0x17
||| |
| specrel | FAILED | ===== Begin, t_deltaAtRest: 10, x_lengthAtRest: 42
|||Error: could not find or load class Formulae.
|||instantiateClass: LoadClassFromNameOnly failed with class: Formulae.
|||Error instantiating class: Formulae
||| |
