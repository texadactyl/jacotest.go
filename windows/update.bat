type VERSION.txt
git pull
cd src
go get
go install -v .
cd ..
jacotest -c
