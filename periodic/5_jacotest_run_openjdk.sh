# =============================
# Run jacotest with jvm=openjdk
# =============================

set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

logger 'Run jacotest with jvm=openjdk'
cd ..
jacotest -x -j openjdk 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: jacotest -x -j openjdk'
fi

logend

