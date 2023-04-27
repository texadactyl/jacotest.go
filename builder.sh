set -e
source ./periodic/common_defs.txt

> $LOG # Make the log nil.
logbegin

# Golang bin directory:
export GOPATH=$HOME/go
export GOBIN=$GOPATH/bin

# Position into src directory
cd src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd src'
fi

# Remove all old stuff
go clean -i -cache

# Build and install jacotest
go install 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go install'
fi

logend
