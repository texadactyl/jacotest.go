# jacotest.go Windows scripts

## Setting User Environment Variables in the Windows Control Panel (How-to)

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

## One-time (only) Pre-requisites:

The following steps must be taken prior to use of jacotest:

* Ensure that there is a go\bin subdirectory under the user's home directory (%USERPROFILE%).
* Ensure that the %GOPATH% user environment variable = %USERPROFILE%\go.
* Ensure that %USERPROFILE%\go\bin is an element of the user %PATH% environment variable.
* After this is done, logout and then re-login.

## One-time (only) Scripts

* windows\show_exes.bat - Starting in the user's home directory, show all JACOTEST.EXE files.
* windows\del_exes.bat - Starting in the user's home directory, delete all JACOTEST.EXE files. Alternatively, if you ran show_exes.bat, you can manually delete the JACOTEST.EXE files based on the screen output.
* Execute this command line from the jacobin or jacotest.go src directory: go clean -cache

## Updating Jacotest

* cd into the jacotest.go directory, whever you keep it.
* windows\update.bat
