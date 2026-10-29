# Synchronise jacobin source code and unit tests

set -ev
set -o pipefail

cd src

# Clean the go cache
go clean -cache
go clean -testcache

# Recompile all of jacotest and install the jacotest executable.
go install .

# Run the jacotest unit test suite.
go test .

