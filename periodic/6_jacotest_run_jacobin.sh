# =============================
# Run jacotest with jvm=jacobin
# =============================

set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

logger 'Run jacotest with jvm=jacobin'cd ..
cd ..
jacotest -x -t 120 -j jacobin 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: jacotest -x -j jacobin'
fi

logend

