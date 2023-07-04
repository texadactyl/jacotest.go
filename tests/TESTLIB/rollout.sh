FOLDERS=("sha3" "fits" "scimark2" "kalman-filtering" "emission-line-spectra")
FILE="MathLite.java"
for folder in ${FOLDERS[@]}; do
	target=../$folder
	echo Copying $FILE to $target
  	cp $FILE $target
done
echo End
