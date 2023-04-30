# ====================================================
# Periodically, run this script under nix cron control
# ====================================================

set -e

# Could have been invoked a few different ways:
# bash /a/b/c/periodic.sh          # absolute path reference
# cd /a/b/c/; bash ./periodic.sh   # relative path reference
# cd /a/b/; bash ./c/periodic.sh   # relative path reference
# Set WD = the absolute path of the desired working directory.
ABS=$(realpath "$0")
WD=`dirname $ABS`

# Now, reposition to WD.
cd $WD

# Get common definitions
source ./common_defs.txt

# Start logging
> $LOG # Make the log nil.
logbegin

logger "Current directory: `pwd`"

export PATH=$JACOBIN_BIN:$GOBIN:$PATH
bash ./1*sh
bash ./2*sh
bash ./3*sh
bash ./4*sh
bash ./5*sh
bash ./6*sh

logend
