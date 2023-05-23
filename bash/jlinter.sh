source ./common_defs.txt
set +e # Do not stop on errors

logbegin
> $LOG # Make the log nil.

cd ../tests
for DIR in `ls`; do
	echo ':::::::::::::::::::::: '$DIR  2>&1 | tee -a $LOG
	cd $DIR
	javac -Xlint:all *.java  2>&1 | tee -a $LOG
	cd ..
done

logend

