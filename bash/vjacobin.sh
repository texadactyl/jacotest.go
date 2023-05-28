source ./common_defs.txt
set +e # Don't stop on errors
env|grep JACOBIN 2>&1 | tee -a $LOG

> $LOG # Make the log nil.
logbegin

echo :::::::::::::::: go vet classloader '.....' 2>&1 | tee -a $LOG
cd ${JACOBIN_SRC}/classloader
go vet

echo :::::::::::::::: go vet frames '.....' 2>&1 | tee -a $LOG
cd ${JACOBIN_SRC}/frames
go vet

echo :::::::::::::::: go vet globals '.....' 2>&1 | tee -a $LOG
cd ${JACOBIN_SRC}/globals
go vet

echo :::::::::::::::: go vet jvm '.....' 2>&1 | tee -a $LOG
cd ${JACOBIN_SRC}/jvm
go vet

echo :::::::::::::::: go vet :::::::::::::::: object '.....' 2>&1 | tee -a $LOG
cd ${JACOBIN_SRC}/object
go vet

echo :::::::::::::::: go vet util '.....' 2>&1 | tee -a $LOG
cd ${JACOBIN_SRC}/util
go vet

logend
