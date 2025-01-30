set -e
set pipefail
MYNAME=`basename $0`

if [ ! -r VERSION.txt ]; then 
	echo '*** '$MYNAME': Wrong current directory!'
	exit 86
fi

# Starting at ./tests,
# 1. Find all occurences of main.java and grep all "class" statements, keeping the file paths (-H) and ignoring case (-i).
# 2. From that, ignore case and throw out (-v) the "class main" statements.
# 3. From that, remove the directory prefix, yielding only the jacotest case  (directory) name.
# 4. From that, sort.
#
# 1 vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv         2 vvvvvvvvvvvvvvvvvvv        3 vvvvvvvvvvvvvvvvvvvvvv   4 vv
echo
echo $MYNAME': Jacotest case files exhibiting subordinate classes (duplicate entries are possible):' 2>&1  | tee $MYNAME.log
echo 2>&1  | tee -a $MYNAME.log
find ./tests -name main.java -exec grep -iH " class " {} \;  2>&1 | grep -iv "class main" 2>&1 | sed -e "s/\.\/tests\///" | sort 2>&1  | tee -a $MYNAME.log
