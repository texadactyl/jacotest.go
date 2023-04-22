set -e
source ./periodic/common_defs.txt

> $LOG # Make the log nil.
logbegin

logger 'Build and install jacotest .....'
cd src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd src'
fi
go clean -cache
go install 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go build'
fi

logend
