set -e
source ./periodic/common_defs.txt

> $LOG # Make the log nil.
logbegin

cd src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd src'
fi

go install 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go install'
fi

logend
