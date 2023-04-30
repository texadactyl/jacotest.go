# ===============
# Build jacotest
# ===============

set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

cd $JACOTEST_HOME/src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd $JACOTEST_HOME/src'
fi

logger 'Build jacotest'
go install 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go install'
fi

logend

