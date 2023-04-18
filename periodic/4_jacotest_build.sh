# ===============
# Build jacotest
# ===============

set -e
source ./common_defs.txt
SRCDIR=../src

> $LOG # Make the log nil.
logbegin

logger 'Build jacotest'
go build -C $SRCDIR 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: go build'
fi

logger 'Move jacotest executable to the appropriate bin directory'
mv $SRCDIR/jacotest $JACOBIN_BIN
if [ $? -ne 0 ]; then
    oops 'FAILED: mv ./jacotest '$JACOBIN_BIN
fi

logend

