# Jacotest use of the Benchmark Game set of Java Programs

Main web page URL: https://benchmarksgame-team.pages.debian.net/benchmarksgame/index.html
Java-only source code URL: https://benchmarksgame-team.pages.debian.net/benchmarksgame/fastest/java.html

The sources have been modified to a modest degree by the Jacobin project for compatibility with Jacotest:
* All main program classes are defined as follows: `public class main`.
* The command line is not used to pass parameters. Instead, the default value of a parameter is set as a constant in accordance with its Benchmark Game report.
* Jacotest HELPERS functions are employed for elapsed time measurement/reporting (TimeHelper), file input to byte-slice, and value checking (Checkers).
* No third-party objects will be used. This may necessitate passing up on the fastest source file version for a somewhat older version.

