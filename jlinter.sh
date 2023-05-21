set -e
HERE=`pwd`
LOG=${HERE}/`basename $0`.log
> $LOG
cd tests
for DIR in `ls`; do
	echo '===================== '$DIR  2>&1 | tee -a $LOG
	cd $DIR
	javac -Xlint:all *.java  2>&1 | tee -a $LOG
	cd ..
done
