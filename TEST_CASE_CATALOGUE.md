This file is a catalogue of jacotest cases. Entries appear in UTF-8 order.
<br>
<br>
| `Name` | `Description`
| :------------ | :--- |
|<img width=90/>|<img width=600/>|
| HELPERS | Check observed-vs-expected, file utilities, hex dump, prime number utilities |
| array-copy | System.arraycopy using several primitive and object types |
| array-list-iterator | ArrayList and Iterator functions |
| arrays-1 | Access time of array elements of type byte, char, int, float, double, and String |
| arrays-copyof | New arrays by copying elements from the originals |
| atomic-integer | java.util.concurrent.atomic.AtomicInteger functions |
| beetlejuice | Loop stability of byte to String conversions |
| big-decimal* | java.math.BigDecimal tests |
| big-integer* | java.math.BigInteger tests |
| big-rational | BigRational tests |
| bit-shifting* | Shifting bits of integers |
| blockchain | Create a blockchain, amend it, and validate it |
| blocking-queues-2-3-5 | Solve the 2,3,5 problem; blocking queues |
| blum-blum-shub | Blum-Blum-Shub (BBS) Algorithm for PseudoRandom Number Generation |
| bohr-atom | Compute properties of the Bohr model of the atom |
| boolean | Boolean primitive tests |
| cached-thread-pool | Thread pool that scales dynamically, maximizes parallelism |
| casting | Casting between primitive variable types |
| catch-8-survivor | try-catch 8 levels deep |
| charset-encoding | java.nio.charset.Charset, InputStreamReader.getEncoding() |
| checkcast | Exercise JVM CHECKCAST |
| cmath-in-java-source | Complex variables |
| crc | java.util.zip.Adler32, java.util.zip.CRC32, java.util.zip.CRC32C |
| crypto-* | 3fish, salsa, and speck cryptography |
| db-sqlite | SQLITE with a JDBC |
| dedupe-hacked | "Looks for duplicate files based on CRC-32 file sizes and checksums" from @platypusguy |
| desi | DES algorithm in Java |
| desi-reduced | Call functions of an object from outside and inside the object |
| elliptic-* | Elliptic cryptography |
| emission-line-spectra | Emission line spectra from an element table |
| emission-line-spectra-nofuncs | Less complex emission-line-spectra |
| enigma-machine | Alan Turing's Enigma Machine |
| enum-* | Enumerated types |
| ex-catch-assertion-error | Cause and catch an assertion error |
| ex-catch-big-integer | Same as ex-catch-assertion-error but using java.math.BigInteger |
| ex-catch-called-func-parseint | Cause a NumberFormatException inside a called function and catch it in the caller function |
| ex-catch-idiv | Loop stability of catching division by zero |
| ex-catch-multi-frame | Throw and catch several types of exceptions at various function-call levels |
| ex-catch-null-ptr | Cause and catch a null pointer exception |
| ex-catch-parseint | Loop stability of causing and catching a NumberFormatException |
| ex-catch-plus-jj | catch NumberFormatException in a called function + use the JJ technique for dumping statics |
| ex-catch-user-ex | Cause and catch a user-defined exception |
| ex-finally | Cause and catch a NumberFormatException + finally processing |
| ex-finally-2 | Loop stability of causing and catching a NumberFormatException + finally processing |
| ex-multilevel | Causing and catching a NumberFormatException + finally processing at multiple try-catch levels |
| fits | Read and process a FITS file |
| floor-div-mod-mix | For int and long: floor, div, mod using try-catch |
| guitar-string | Simulate the plucking of a guitar string (Physics) |
| harshad-niven | Compute the first 100 Harshad (Niven) numbers |
| hashed-* | java.util.HashMap & HashSet |
| hex-decode-numeric | Loop stability of decoding numeric values |
| hexxed | java.util.HexMap |
| http-client-server | Simple non-secure web client and server |
| https-client-getter | Simple secure web GET |
| iface-lorentz | Lorentz transform using a Java interface (Physics) |
| iface-* | Java interface |
| iinc-iadd-isub | Exercise IINC, IADD, and ISUB |
| imageio-output | Simple writing to a PNG file |
| integer* | java.lang.Integer |
| io_file* | java.io file operations |
| invoke-dynamic-concats | "+" operator, mixed primitives/objects, multi-argument, loops |
| invoke-dynamic-generic-class | Box<T>::new, Pair<A,B>::new; demonstrates type parameter constructor references |
| invoke-dynamic-lambdas | Basic lambdas |
| invoke-dynamic-methref-basic | Method reference exerciser that uses custom functional interfaces |
| invoke-dynamic-methref-diversity-1 | Method reference exerciser that triggers many distinct bootstrap shapes |
| invoke-dynamic-methref-diversity-2 | Method reference exerciser that adds loops and arrays |
| invoke-dynamic-methref-high-arity | Tri-, Quad-, Penta-argument lambdas and method references with custom functional interfaces |
| JACOBIN-0161-0229-classes | Class methods inside and outside the main class |
| JACOBIN-0161-instantiate-class | Class methods in a file separate from the main class |
| JACOBIN-0211-pbcrypto | Password-based cryptography using AES/CBC/PKCS5Padding |
| JACOBIN-0217-multidim-2d | 2D matrices |
| JACOBIN-0217-multidim-3d | 3D matrices |
| JACOBIN-0227-string-array | Simple array of Strings |
| JACOBIN-0231-stats | Statistical mean, standard deviation, and correlation plus a square root algorithm (Babylonian) |
| JACOBIN-0234-0240-0241-array-length | Simple array length calculations |
| JACOBIN-0236-bitwise | Shifting bits of integers part 2 |
| JACOBIN-0236-minus-signs | Algebraic signs of integers |
| JACOBIN-0237-nil-printlns | System.out.println with no arguments |
| JACOBIN-0251-array-type-perf | Loop stability of various types of arrays part 1 |
| JACOBIN-0263-gc | Loop stability of various types of arrays part 2 |
| JACOBIN-0279-simple-switch | Simple integer-based switch |
| JACOBIN-0281-get-property | System.getProperty for several arguments |
| JACOBIN-0288-aastore-field-type | Simple AASTORE test |
| JACOBIN-0290-string-length | String lengths and related functions |
| JACOBIN-0293-drem | DREM test |
| JACOBIN-0310-vector-survivor | Simple vector of Integer objects |
| JACOBIN-0311-for-loop-G-pop | PUSH/POP stability |
| JACOBIN-0312-FCMPG | FCMP test |
| JACOBIN-0314-java-lang-math | java/lang/Math |
| JACOBIN-0314-java-lang-strictmath | java/lang/StrictMath |
| JACOBIN-0314-loop-survival | Stress test with multiple object and primitive types |
| JACOBIN-0319-println-object | System.out.println(object) with several types of objects |
| JACOBIN-0322-default-locale | java.util.Locale |
| JACOBIN-0325-super-* | Objects of superclasses |
| JACOBIN-0329-nonfinals | Finals vs nonfinals |
| JACOBIN-0337-static-inits | Static initializer blocks |
| JACOBIN-0369-simplified-0290 | String lengths and related functions, simplified for analysis |
| JACOBIN-0386-0387-strings-again | Strings and byte arrays |
| JACOBIN-0393-pot-pourri | Formatting simple types |
| JACOBIN-0393-two-strings | Formatting simple types in another way |
| JACOBIN-0433-HexFormat | java.util.HexFormat |
| JACOBIN-0434-short-value | Short values |
| JACOBIN-0435-format-int-as-hex | Format integers in hex |
| JACOBIN-0476-arraycopy | System.arraycopy |
| JACOBIN-0587-statics-survival | Statics |
| JACOBIN-0760-submethod | Methods of a subclass |
| JACOBIN-0800-two-crashes | Crashes when a=b=c is attempted inside a subclass function |
| jar-execute-1 | Create one jar for use in execute mode (jvm -jar jarname.jar). Employ a calculator in the top-level directory (same as main.class) |
| jar-execute-2 | Create one jar for use in execute mode (jvm -jar jarname.jar). Employ a calculator from subdirectory middle/calculator |
| jar-execute-3 | Create two jars: jar#1 in execute mode and jar#2 is part of the Class-Path in the manifest of jar#1 |
| jar-library-1 | Create one jar for use in library mode |
| jar-zip-1 | Like jar-execute-3 except that #2 is a zip file instead of a jar file. |
| java17-enhancements | Java 17 enhancements to the previous release |
| java-logging | java.util.logging |
| jj-* | Special non-Java utility Jacobin gfunctions |
| kalman-filtering | Kalman Filter |
| lambdas-maps | Lambdas, maps, interfaces |
| linked-list* | Lists and linked lists |
| magic-square | Magic Square 13x13 |
| math-context | BigDecimal, RoundingMode, and MathContext |
| math-in-java-source | Math functions in Java language |
| merkletrees | Merkle tree |
| miller-rabin-test | Miller-Rabin primality test |
| mobile-* | Mobile network functions |
| natives-double | Double to long to double conversions |
| natives-float | Float to int to float conversions |
| nbody | N-body simulation execution |
| nbody-lite-labels | N-body simulation starting values with labels |
| nbody-lite-nolabels | N-body simulation starting values without labels |
| nio-charset | java.nio.charset.* |
| nth-root | Nth root, where N=13 |
| numbers-chars-strings | Functions associated with char, double, int, and String |
| packaging-* | Import packages |
| paillier-cryptosystem | Pascal Paillier cryptosystem |
| parse-int | Integer parsing |
| perf-base-instantiate | Stress test of various object instantiations |
| pi-digits-bbp | Pi calculated with the Bailey-Borwein-Plouffe algorithm |
| playfair | Playfair-Wheatstone cipher algorithm |
| print-objects-linked-lists | LinkedList, BigInteger |
| process-handle | ProcessHandle interface |
| properties | java.util.Properties |
| random-1 | java.util.Random |
| rational-polynomial | java.math.BigInteger, user RationalPolynomial, user BigRational |
| recursion | Fibonacci sequence using recursion |
| rounding-mode* | BigDecimal, RoundingMode |
| rsa-mini | RSA Public and Private Key simple cryptography |
| rsa-unrandom | Like rsa-mini but without random key generation |
| salesman | The traveling salesman problem |
| scimark2 | Scimark v2 |
| scimark2-LU-only | Scimark v2 - abbreviated significantly |
| secure-random | java.security.SecureRandom, Provider, SecureRandomSpi, and java.util.HexFormat |
| sha3 | SHA-3 calculator |
| sieve | Sieve of Eratosthenes |
| sockets | Client and server socket pair |
| solitairgraphy | Cryptography with a deck of cards (Bruce Schneier) |
| specrel | Special Relativity calculations |
| stack-walk | Walk and verify a Java stack |
| stat-distros | generate pseudo-random numbers from some statistical distributions |
| stringbuffer-* | StringBuffer functions |
| stringbuilder-* | StringBuilder functions |
| stringer-* | String functions |
| stringtokenizer | String token parsing |
| switcheroo* | JVM TABLESWITCH and LOOKUPSWITCH |
| taylor-series | Taylor Series with rational library functions |
| taylor-series-2 | Taylor series exercise without the rational library functions |
| threading* | Multi-threading, including thread groups |
| tiffy | TIF reading |
| tls-one-way | One-way TLS |
| two-fish | Two-fish cryptography |
| two-pass | Two-pass Assembler |
| tx-student-topzips | File reading, subclass with 2 init functions, bubble sort with 3 keys |
| vector* | Vector functions |
| walker | java.nio.file.Files, Path, and Paths |
| warp-speed | Calculations to/from warp speed (Physics) |
| wide | JVM WIDE |
| zippy | Zip file read |

