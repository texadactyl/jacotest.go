# =======================
# Regression Test jacobin
# =======================

set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

logger 'Build jacobin .....'
cd $JACOBIN_HOME/src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd $JACOBIN_HOME/src'
fi

logger 'Run the regression tests .....'
go test -v ./... 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go test'
fi

logend

