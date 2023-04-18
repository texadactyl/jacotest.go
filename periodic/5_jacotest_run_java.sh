# ==========================
# Run jacotest with jvm=java
# ==========================

set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

logger 'Run jacotest with jvm=java'
cd ..
jacotest -x -j java 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: jacotest -x -j java'
fi

logend

