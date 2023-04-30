# ===============
# Build jacobin
# ===============


set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

cd $JACOBIN_HOME/src
if [ $? -ne 0 ]; then
    oops 'FAILED: cd $JACOBIN_HOME/src'
fi

logger 'Build jacobin .....'
go clean -cache  2>&1 | tee -a $LOG
go install 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go install'
fi

logger 'Show jacobin version .....'
jacobin -version  2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: jacobin -version'
fi

logend
