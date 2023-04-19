set -e
source ./periodic/common_defs.txt

> $LOG # Make the log nil.
logbegin

logger 'Build jacotest .....'
cd src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd src'
fi
go clean -cache
go build -o $JACOBIN_BIN/jacotest 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go build'
fi

logend
