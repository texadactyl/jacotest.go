@echo off

REM All set operations are confined to this bat file.
setlocal

REM Establish CLASSPATH.
set "CLASSPATH=.;.\tests\HELPERS"

REM Execute Jacotest cases.
jacotest -x

