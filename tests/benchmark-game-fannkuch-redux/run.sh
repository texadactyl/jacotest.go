set -e
NLOOPS=100
for ((i=1; i<=NLOOPS; i++)); do
    echo "Iteration $i"
    jacobin main
done
echo "The End"