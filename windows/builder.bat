set GOPATH=%USERPROFILE%\go
set GOBIN=%GOPATH%\bin
set PATH=%GOBIN%";"%PATH%

git pull

go clean -i -cache

cd src

go install

go vet

jacotest -h

