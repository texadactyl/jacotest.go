set "GOPATH=%USERPROFILE%\go" || EXIT /B 1
set "GOBIN=%GOPATH%\bin" || EXIT /B 1
setx PATH "%GOBIN%";"%PATH%" || EXIT /B 1

cd ..  || EXIT /B 1
git pull || EXIT /B 1

cd src || EXIT /B 1
go install || EXIT /B 1

go vet || EXIT /B 1

cd ..  || EXIT /B 1
jacotest -h || EXIT /B 1

