source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

# Update jacotest.go from github
cd ..
git pull 2>&1 | tee -a $LOG

# Position into src directory
cd src

# Remove all old stuff
echo Clean the go cache '.....' 2>&1 | tee -a $LOG
go clean -i -cache 2>&1 | tee -a $LOG

# Build and install jacotest
echo Build and install jacotest '.....' 2>&1 | tee -a $LOG
go install 2>&1 | tee -a $LOG

# Vet jacotest
echo go-vet jacotest '.....' 2>&1 | tee -a $LOG
go vet 2>&1 | tee -a $LOG

logend
