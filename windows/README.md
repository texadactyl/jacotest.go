# jacotest.go Windows scripts

## One-time (only) pre-requisites:

The following steps must be taken prior to use of jacotest:

* If there is no go subdirectory under the user's home directory, create it.
* If there is no bin subdirectory under go, create it.
* The GOPATH environment variable must be set to the go subdirectory full path. After this is done, logout and then re-login.
* Ensure that the full path of the bin subdirectory of go is an element of the PATH environment variable.

## One-time (only) scripts

* show_exes.bat - Starting in the user's home directory, show all *.EXE files.
* del_exes.bat - Starting in the user's home directory, delete all *.EXE files. BE CAREFUL! Alternatively, if you ran show_exes.bat, you can manually delete the *.EXE files based on the screen output.
* Execute this command line from the jacobin or jacotest.go src directory: go clean -cache

## When updating Jacotest

* cd jacotest.go whever you keep it.
* builder.bat

