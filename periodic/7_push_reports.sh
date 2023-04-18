# ===============
# Run jacotest
# ===============

set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

logger 'git push the reports'
cd ..
git commit -m "periodic push RUN*md" RUN*md
git push

logend

