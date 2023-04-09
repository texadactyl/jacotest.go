source ./config.defs.txt

> $LOG # Make the log nil.

logger 'Build jacotest .....'
cd src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd src'
fi
go clean -cache
go build -o $MYBIN/jacotest 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go build'
fi

echo 2>&1 | tee -a $LOG
echo ======== 2>&1 | tee -a $LOG
echo FINISHED 2>&1 | tee -a $LOG
echo ======== 2>&1 | tee -a $LOG
echo There is a log of this session in $LOG

