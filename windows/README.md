# jacotest.go Set-up for Windows

## How-to for User Environment Variables in the Windows Control Panel

Skip this section if you are already proficient with the Windows Control Panel.

#### Open Environment Variables Settings

* Run sysdm.cpl in a command line.
* Click the Advanced tab.
* Click the Environment Variables button at the bottom.

#### Add or Edit a User Environment Variable

Add
* Under User variables for [YourUsername], click New to add a new variable.
* Variable name: Enter the name of the variable.
* Variable value: Enter the value.

Edit
* Select it and click Edit.
* Change the value as need be.

#### Apply and Save
* Click OK to close the Environment Variables window.
* Click OK again to close the System Properties window.
* Logout + login.

## One-time (only) Pre-requisites

The following steps must be taken prior to use of jacotest:
* Ensure that there is a go\bin subdirectory under the user's home directory (%USERPROFILE%).
* Ensure that the %GOPATH% user environment variable = %USERPROFILE%\go.
* Ensure that %USERPROFILE%\go\bin is an element of the user %PATH% environment variable.
* After this is done, logout and then re-login.

## One-time (only) Cleanup

* cd into the jacotest.go directory, wherever you keep it.
* cd src
* Run the following command: ```go clean -cache```.
* cd ..
* Run windows\show_exes.bat to see all the JACOTEST.EXE files.
* Run windows\del_exes.bat will ensure that there will be only one Jacotest on this system after running windows\update.bat (see next section).

## Updating Jacotest

* cd into the jacotest.go directory, wherever you keep it.
* Run windows\update.bat to ```git pull```, ```go get```, ```go install```, and ```jacotest -c```.
