set -e
set -x
git add RUN*.md
git commit -m "`date`" RUN*.md
git push
