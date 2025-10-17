This file is a catalogue of jacotest cases. Entries appear in UTF-8 order.
<br>
<br>
| `Name` | `Description`
| :------------ | :--- |
|<img width=90/>|<img width=600/>|
|  HELPERS  | Check observed-vs-expected, file utilities, hex dump, prime number utilities |
|  array-copy  |  System.arraycopy using several primitive and object types |
|  array-list-iterator  | ArrayList and Iterator functions |
|  arrays-1  | Access time of array elements of type byte, char, int, float, double, and String |
|  arrays-copyof  | New arrays by copying elements from the originals |
|  atomic-integer  | java.util.concurrent.atomic.AtomicInteger functions |
|  beetlejuice  | Loop stability of byte to String conversions |
|  big-decimal*  | java.math.BigDecimal tests |
|  big-integer*  | java.math.BigInteger tests |
|  big-rational  | BigRational tests |
|  bit-shifting*  | Shifting bits of integers |
|  blockchain  | Create a blockchain, amend it, and validate it |
|  blocking-queues-2-3-5 | Solve the 2,3,5 problem; blocking queues |
|  blum-blum-shub | Blum-Blum-Shub (BBS) Algorithm for PseudoRandom Number Generation |
|  bohr-atom  | Compute properties of the Bohr model of the atom  |
|  boolean  | Boolean primitive tests |
|  cached-thread-pool  | Thread pool that scales dynamically, maximizes parallelism |
|  casting  | Casting between primitive variable types |
|  catch-8-survivor  | try-catch 8 levels deep |
|  charset-encoding  | java.nio.charset.Charset, InputStreamReader.getEncoding() |
|  checkcast  | Exercise JVM CHECKCAST |
|  cmath-in-java-source  | Complex variables |
|  crc  | java.util.zip.Adler32, java.util.zip.CRC32, java.util.zip.CRC32C |
|  crypto-* | 3fish, salsa, and speck cryptography |
|  db-sqlite  | SQLITE with a JDBC |
|  dedupe-hacked  | "Looks for duplicate files based on CRC-32 file sizes and checksums" from @platypusguy |
|  desi  | DES algorithm in Java |
|  desi-reduced | Call functions of an object from outside and inside the object |
|  elliptic-*  | Elliptic cryptography |
|  emission-line-spectra  | Emission line spectra from an element table |
|  emission-line-spectra-nofuncs  | Less complex emission-line-spectra |
|  enigma-machine  | Alan Turing's Enigma Machine |
|  enum-*  | Enumerated types |
|  ex-catch-assertion-error  | Cause and catch an assertion error |
|  ex-catch-big-integer  | Same as ex-catch-assertion-error but using java.math.BigInteger |
|  ex-catch-called-func-parseint  | Cause a NumberFormatException inside a called function and catch it in the caller function |
|  ex-catch-idiv  | Loop stability of catching division by zero |
|  ex-catch-multi-frame  | Throw and catch several types of exceptions at various function-call levels |
|  ex-catch-null-ptr  | Cause and catch a null pointer exception |
|  ex-catch-parseint  | Loop stability of causing and catching a NumberFormatException |
|  ex-catch-plus-jj | catch NumberFormatException in a called function + use the JJ technique for dumping statics |
|  ex-catch-user-ex  | Cause and catch a user-defined exception |
|  ex-finally  | Cause and catch a NumberFormatException + finally processing |
|  ex-finally-2  | Loop stability of causing and catching a NumberFormatException + finally processing |
|  ex-multilevel  | Causing and catching a NumberFormatException + finally processing at multiple try-catch levels |
|  fits  | Read and process a FITS file |
|  floor-div-mod-mix  | For int and long: floor, div, mod using try-catch |
|  hashed-*  | java.util.HashMap & HashSet |
|  hex-decode-numeric  | Loop stability of decoding numeric values |
|  hexxed | java.util.HexMap |
|  http-client-server  | Simple non-secure web client and server |
|  https-client-getter  | Simple secure web GET |
|  iface-lorentz | Lorentz transform using a Java interface (Physics) |
|  iface-* | Java interface |
|  iinc-iadd-isub | Exercise IINC, IADD, and ISUB |
|  imageio-output  | Simple writing to a PNG file  |
|  integer*  | java.lang.Integer |
|  io_file*  | java.io file operations |
|  JACOBIN-0161-0229-classes  | Class methods inside and outside the main class |
|  JACOBIN-0161-instantiate-class  | Class methods in a file separate from the main class |
|  JACOBIN-0211-pbcrypto  | Password-based cryptography using AES/CBC/PKCS5Padding |
|  JACOBIN-0217-multidim-2d  | 2D matrices |
|  JACOBIN-0217-multidim-3d  | 3D matrices |
|  JACOBIN-0227-string-array  | Simple array of Strings |
|  JACOBIN-0231-stats  | Statistical mean, standard deviation, and correlation plus a square root algorithm (Babylonian) |
|  JACOBIN-0234-0240-0241-array-length  | Simple array length calculations |
|  JACOBIN-0236-bitwise  | Shifting bits of integers part 2 |
|  JACOBIN-0236-minus-signs  | Algebraic signs of integers |
|  JACOBIN-0237-nil-printlns  | System.out.println with no arguments |
|  JACOBIN-0251-array-type-perf  | Loop stability of various types of arrays part 1 |
|  JACOBIN-0263-gc  | Loop stability of various types of arrays part 2 |
|  JACOBIN-0279-simple-switch  | Simple integer-based switch |
|  JACOBIN-0281-get-property  | System.getProperty for several arguments |
|  JACOBIN-0288-aastore-field-type  | Simple AASTORE test |
|  JACOBIN-0290-string-length  | String lengths and related functions |
|  JACOBIN-0293-drem  | DREM test |
|  JACOBIN-0310-vector-survivor  | Simple vector of Integer objects |
|  JACOBIN-0311-for-loop-G-pop  | PUSH/POP stability |
|  JACOBIN-0312-FCMPG  | FCMP test |
|  JACOBIN-0314-java-lang-math  | java/lang/Math |
|  JACOBIN-0314-java-lang-strictmath  | java/lang/StrictMath |
|  JACOBIN-0314-loop-survival  | Stress test with multiple object and primitive types |
|  JACOBIN-0319-println-object  | System.out.println(object) with several types of objects |
|  JACOBIN-0322-default-locale  | java.util.Locale |
|  JACOBIN-0325-super-*  | Objects of superclasses |
|  JACOBIN-0329-nonfinals  | Finals vs nonfinals |
|  JACOBIN-0337-static-inits  | Static initializer blocks |
|  JACOBIN-0369-simplified-0290  | String lengths and related functions, simplified for analysis |
|  JACOBIN-0386-0387-strings-again  | Strings and byte arrays |
|  JACOBIN-0393-pot-pourri  | Formatting simple types  |
|  JACOBIN-0393-two-strings  | Formatting simple types in another way |
|  JACOBIN-0433-HexFormat  | java.util.HexFormat |
|  JACOBIN-0434-short-value  | Short values |
|  JACOBIN-0435-format-int-as-hex  | Format integers in hex |
|  JACOBIN-0476-arraycopy  | System.arraycopy |
|  JACOBIN-0587-statics-survival  | Statics |
|  JACOBIN-0760-submethod  | Methods of a subclass |
|  jarring  | Create and use functions from a jar |
|  java17-enhancements  | Java 17 enhancements to the previous release |
|  java-logging  | java.util.logging |
|  jj-* | Special non-Java utility Jacobin gfunctions |
|  kalman-filtering  | Kalman Filter |



|  lambdas-maps  | xxxxx |
|  linked-list  | xxxxx |
|  math-in-java-source  | xxxxx |
|  merkletrees  | xxxxx |
|  miller-rabin-test  | xxxxx |
|  mobile-5g-aka | xxxxx |
|  mobile-snow-v | xxxxx |
|  natives-double  | xxxxx |
|  natives-float  | xxxxx |
|  nbody  | xxxxx |
|  nbody-lite  | xxxxx |
|  nio-charset  | xxxxx |
|  nth-root  | xxxxx |
|  numbers-chars-strings  | xxxxx |
|  packaging-1  | xxxxx |
|  packaging-2  | xxxxx |
|  paillier-cryptosystem  | xxxxx |
|  parent-child-process  | xxxxx |
|  parse-int  | xxxxx |
|  perf-base-instantiate  | xxxxx |
|  playfair  | xxxxx |
|  random-1  | xxxxx |
|  recursion  | xxxxx |
|  rsa-mini  | xxxxx |
|  rsa-unrandom  | xxxxx |
|  salesman  | xxxxx |
|  scimark2  | xxxxx |
|  sha3  | xxxxx |
|  sieve  | xxxxx |
|  sockets  | xxxxx |
|  solitairgraphy  | xxxxx |
|  specrel  | xxxxx |
|  stack-walk  | xxxxx |
|  stringbuffer-append  | xxxxx |
|  stringbuffer-delete  | xxxxx |
|  stringbuffer-dynamic  | xxxxx |
|  stringbuffer-insert  | xxxxx |
|  stringbuffer-misc  | xxxxx |
|  stringbuffer-perf  | xxxxx |
|  stringbuffer-replace  | xxxxx |
|  stringbuilder-append  | xxxxx |
|  stringbuilder-delete  | xxxxx |
|  stringbuilder-dynamic  | xxxxx |
|  stringbuilder-insert  | xxxxx |
|  stringbuilder-misc  | xxxxx |
|  stringbuilder-replace  | xxxxx |
|  stringer-1  | xxxxx |
|  stringer-2  | xxxxx |
|  stringer-3  | xxxxx |
|  stringer-4  | xxxxx |
|  stringer-5  | xxxxx |
|  stringer-valueof  | xxxxx |
|  stringtokenizer  | xxxxx |
|  switcheroo  | xxxxx |
|  switcheroo-2  | xxxxx |
|  taylor-series  | xxxxx |
|  taylor-series-2  | xxxxx |
|  threading  | xxxxx |
|  tiffy  | xxxxx |
|  tls-one-way  | xxxxx |
|  two-fish  | xxxxx |
|  two-pass  | xxxxx |
|  vectors  | xxxxx |
|  vector-survivor-2  | xxxxx |
|  walker  | xxxxx |
|  wide  | xxxxx |
|  zippy  | xxxxx |

