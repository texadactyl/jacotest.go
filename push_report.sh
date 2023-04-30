set -e
set -x
git commit -m "`date`" RUN*.md
git push
