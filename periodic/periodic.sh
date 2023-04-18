# ====================================================
# Periodically, run this script under nix cron control
# ====================================================

set -e
source ./common_defs.txt

> $LOG # Make the log nil.
logbegin

bash ./1*sh
bash ./2*sh
bash ./3*sh
bash ./4*sh
bash ./5*sh

logend
