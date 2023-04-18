# ===============
# Install jacobin
# ===============

set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

logger 'Position in the parent directory of jacobin'
cd $JACOBIN_PARENT
if [ $? -ne 0 ]; then
    oops "FAILED: cd $JACOBIN_PARENT"
fi

logger 'Remove old copy of jacobin if present'
if [ -d jacobin ]; then
	logger 'Removing old copy of jacobin .....'
    rm -rf jacobin 2>&1 | tee -a $LOG
else
	logger 'No pre-existing copy of jacobin present.'
fi

logger 'git clone jacobin from URL='$URL', branch='$BRANCH' .....'
git clone -b $BRANCH $URL 2>&1 | tee -a $LOG
if [ $? -ne 0 ]; then
    oops 'FAILED: git clone'
fi

logend

