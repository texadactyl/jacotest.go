source ./common_defs.txt
env|grep JACOBIN 2>&1 | tee -a $LOG

> $LOG # Make the log nil.
logbegin

cd ${JACOBIN_PARENT}
if [ -d jacobin ]; then
	echo 'Removing old copy of jacobin .....' 2>&1 | tee -a $LOG
    rm -rf jacobin 2>&1 | tee -a $LOG
else
	echo 'No pre-existing copy of jacobin present.' 2>&1 | tee -a $LOG
fi

echo 'git clone jacobin from URL='$URL', branch='$BRANCH' .....' 2>&1 | tee -a $LOG
git clone -b $BRANCH $URL 2>&1 | tee -a $LOG

echo cd into the top of the jacobin src tree '.....' 2>&1 | tee -a $LOG
cd $JACOBIN_HOME/src

echo Clean the go cache '.....' 2>&1 | tee -a $LOG
go clean -i -cache 2>&1 | tee -a $LOG

echo Build and install jacobin '.....' 2>&1 | tee -a $LOG
go install 2>&1 | tee -a $LOG

echo 'Show jacobin version .....' 2>&1 | tee -a $LOG
jacobin -version 2>&1 | tee -a $LOG

logend
