set -e
set -x
git commit -m 'latest RUN reports' RUN*.md
git push
