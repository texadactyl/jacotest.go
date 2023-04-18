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
go build 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go build'
fi

logger 'Move jacobin executable to the appropriate bin directory'
mv ./jacobin $JACOBIN_BIN
if [ $? -ne 0 ]; then
    oops 'FAILED: mv ./jacobin '$JACOBIN_BIN
fi

logger 'Show jacobin version .....'
jacobin -version  2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: jacobin -version'
fi

logend
