# Jacotest use of the Benchmark Game set of Java Programs

Source URL: https://benchmarksgame-team.pages.debian.net/benchmarksgame/index.html

The sources have been modified to a modest degree by the Jacobin project for compatibility with Jacotest:
* All main program classes are defined as follows: `public class main`.
* The command line is not used to pass parameters. Instead, the default value of a parameter is set as a constant in accordance with its Benchmark Game report.
* Jacotest HELPERS functions are employed for elapsed time measurement/reporting (TimeHelper) and value checking (Checkers).

