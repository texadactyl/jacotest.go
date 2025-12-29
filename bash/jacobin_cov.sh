
LOGDIR=$HOME/Downloads

set -e

cd $JACOBIN_TOP
go test -covermode=count -coverprofile=$LOGDIR/cov_profile.out ./...
go tool cover -func=$LOGDIR/cov_profile.out | awk 'NR>1 {print $3, $1, $2}' | sort -n | awk '{print $2, $3, $1}' > $LOGDIR/cov_per_function.out

