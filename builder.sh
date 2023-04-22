set -e
LOG=`basename $0`.log

> $LOG # Make the log nil.

cd src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd src'
fi
go install 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go install'
fi

