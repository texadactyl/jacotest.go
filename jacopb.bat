@echo on

REM All set operations are confined to this bat file.
setlocal

REM Establish CLASSPATH.
set CLASSPATH=.;.\tests\HELPERS

REM Get updates.
git pull || exit /b 1

REM Build Jacotest and move it to the Jacotest top.
cd src || exit /b 1
go build || exit /b 1
move jacotest.exe .. || exit /b 1

REM cd to top and execute compilation of the Java test cases.
cd .. || exit /b 1
jacotest -c

